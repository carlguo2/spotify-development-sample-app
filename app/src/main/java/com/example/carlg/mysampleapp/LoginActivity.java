package com.example.carlg.mysampleapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;


public class LoginActivity extends AppCompatActivity {
    private static final int AUTH_REQUEST_CODE = 1337;    // can use any integer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void login(View view) {

        // create AuthenticationRequest using builder
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(this.getString(R.string.client_id),
                        AuthenticationResponse.Type.TOKEN,
                        this.getString(R.string.redirect_uri));

        // set scope of what we want to access
        builder.setScopes(new String[] {"user-read-recently-played"});
        // build the AuthenticationRequest
        AuthenticationRequest request = builder.build();
        // open the LoginActivity from Authentication library
        AuthenticationClient.openLoginActivity(this, AUTH_REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // check if result comes from correct activity
        if (requestCode == AUTH_REQUEST_CODE) {
            // get the response from authenticator after sending AuthenticationRequest
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            // use snackbar to indicate login status
            Snackbar loginSnackbar = Snackbar.make(findViewById(R.id.login_layout),
                                                    response.getType().toString(),
                                                    Snackbar.LENGTH_LONG);

            switch (response.getType()) {
                // Case where response was successful and auth token contained
                case TOKEN:
                    // move to the next Activity since we have our token
                    String authTokenKey = "authToken";
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    loginIntent.putExtra(authTokenKey, response.getAccessToken());
                    startActivity(loginIntent);
                    break;

                case ERROR:
                    // handle error response
                    loginSnackbar = Snackbar.make(findViewById(R.id.login_layout),
                                                    R.string.auth_error,
                                                    Snackbar.LENGTH_LONG);
                    break;

                default:
                    // most likely auth code was cancelled
                    // handle other cases
            }
            loginSnackbar.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
