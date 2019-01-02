package com.example.carlg.mysampleapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // use the volley library to do GET requests of 50 most played tracks
    // then use GSON to parse those JSONObject returned into HashMap data
    // then find a way to chart that data in some way
    private RequestedData data = null;
    private String authToken;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set up the TextView
        status = findViewById(R.id.track_1);
        // logged in snackbar in here
        Snackbar loginSnackbar = Snackbar.make(findViewById(R.id.main_layout),
                                                R.string.auth_success,
                                                Snackbar.LENGTH_LONG);
        // show snackbar message
        loginSnackbar.show();

        // get access token from intent
        String authTokenKey = "authToken";
        Intent fromLoginIntent = getIntent();
        authToken = fromLoginIntent.getExtras().getString(authTokenKey);

        // set up the button
        final Button retrieveData = findViewById(R.id.get_data);
        retrieveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // disable button after pressed
                retrieveData.setEnabled(false);

                // fill the RequestedData component with REST API data
                String recentHistoryUrl = "https://api.spotify.com/v1/me/player/recently-played";
                Log.d("onClick", "clicked");
                RequestedData historyData = getData(recentHistoryUrl, authToken);

                // set button to enable again
                retrieveData.setEnabled(true);

                // organize data here
                if (historyData != null) {
                    String str = Integer.toString(historyData.getItems().length);
                    status.setText(str);
                } else {
                    status.setText("Null");
                }
            }
        });
    }

    private RequestedData getData(String requestUrl, final String token) {
        // do JSONRequest here
        RequestQueue rQueue;
        rQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest objRequest = new JsonObjectRequest(
                Request.Method.GET,
                requestUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do GSON conversion here
                        Gson gson = new Gson();
                        data = gson.fromJson(response.toString(), RequestedData.class);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

            @Override
            public Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("limit", "50");
                return params;
            }
        };

        rQueue.add(objRequest);

        return data;
    }

//    public void onClickRequestData(View view) {
//        // fill the RequestedData component with REST API data
//        String recentHistoryUrl = "https://api.spotify.com/v1/me/player/recently-played";
//        Log.d("onClick", "clicked");
//        RequestedData historyData = getData(recentHistoryUrl, authToken);
//        // organize data here
//        if (historyData != null) {
//            String str = Integer.toString(historyData.getItems().length);
//            status.setText(str);
//        } else {
//            status.setText("Null");
//        }
//    }

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
