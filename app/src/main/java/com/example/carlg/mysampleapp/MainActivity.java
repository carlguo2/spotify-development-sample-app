package com.example.carlg.mysampleapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // use the volley library to do GET requests of 50 most played tracks
    // then use GSON to parse those JSONObject returned into HashMap data
    // then find a way to chart that data in some way
    private RequestedData data = null;
    private String authToken;
    private boolean isGraphMade = false;
    private int prevLimit = 0;

    // add a TextWatcher so the button field depends on the EditText
    // from https://stackoverflow.com/questions/
    //              15002963/in-android-how-to-make-login-button-disable-with-respect-to-edittext
    // by user MoDev
    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkIfValid();
        }
    };

    private void checkIfValid() {
        EditText limit = findViewById(R.id.limit_param);
        Button graphData = findViewById(R.id.graph_data);
        if (TextUtils.isEmpty(limit.getText())) {
            graphData.setEnabled(false);
            return;
        }
        // if not empty, check if valid limit
        int limitAmt = Integer.parseInt(limit.getText().toString());

        if (limitAmt < 1 || limitAmt > 50) {
            graphData.setEnabled(false);
        } else  {
            graphData.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // set up EditText
        EditText limitParam = findViewById(R.id.limit_param);
        limitParam.addTextChangedListener(mTextWatcher);
        // set up the button
        final Button retrieveData = findViewById(R.id.graph_data);
        // run once to disable button if empty
        checkIfValid();

        retrieveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // disable button after pressed
                retrieveData.setEnabled(false);

                // fill the RequestedData component with REST API data
                String recentHistoryUrl = "https://api.spotify.com/v1/me/player/recently-played";
                Log.d("onClick", "clicked");
                RequestedData historyData = getData(recentHistoryUrl, authToken);

                // organize data here
                if (historyData != null) {
                    // graph data
                    graphData(historyData);
                    isGraphMade = true;
                }

                // set button to enable again
                retrieveData.setEnabled(true);
            }
        });
    }

    /**
     * Use REST API GET request to retrieve current history data
     *
     * @param requestUrl url we are using to get the data
     * @param token authentication token we are using to get data from account
     * @return data that we are using
     */
    private RequestedData getData(String requestUrl, final String token) {
        // do JSONRequest here
        RequestQueue rQueue;
        rQueue = Volley.newRequestQueue(getApplicationContext());

        // get the limit parameter from editText
        EditText limitParam = findViewById(R.id.limit_param);
        int limAmt = Integer.parseInt(limitParam.getText().toString());

        JsonObjectRequest objRequest = new JsonObjectRequest(
                Request.Method.GET,
                requestUrl + "?limit=" + limAmt, // use this so limAmt items get queried
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
        };

        rQueue.add(objRequest);

        return data;
    }

    private void graphData(RequestedData data) {
        // if the limit parameters from previous and now are the same, don't draw anything
        if (prevLimit == data.getLimit()) {
            return;
        }

        HashMap<String , Integer> organizedData = organizeData(data);
        // use AnyChart library to create the graph
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        // check if we need to dispose of graph first
        if (isGraphMade) {
            anyChartView.clear();
        }

        Pie pie = AnyChart.pie();
        // get list of keys
        List<String> keyList = new ArrayList<>(organizedData.keySet());
        List<DataEntry> dataList = new ArrayList<>();
        // convert keys to data entry
        for (String key : keyList) {
            dataList.add(new ValueDataEntry(key, organizedData.get(key)));
        }
        // set everything into the pie object
        pie.data(dataList);

        // set title to pie chart
        pie.title("Artists From " + data.getLimit() + " Most Recently Listened to Tracks");
        // set title for the legend
        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Artists")
                .padding(0d, 0d, 10d, 0d);
        // set legend title at the center
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);

        // reset the previous limit parameter
        prevLimit = data.getLimit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Takes in the RequestedData object and organizes the artists into HashMap
     *
     * @param data RequestedData we received from the REST API call
     * @return HashMap of artists (key) and frequency (value) listened to
     */
    private HashMap<String, Integer> organizeData(RequestedData data) {
        HashMap<String, Integer> sortedData = new HashMap<>(50);

        // loop through the array of play history objects
        for (PlayHistoryObject pho : data.getItems()) {
            JSONSpotifyArtists primaryArtist = pho.getTrack().getArtists()[0];
            // get the primary artist of the track
            if (sortedData.containsKey(primaryArtist.getName())) {
                // replace value in map by incrementing it
                Integer newFreq = sortedData.get(primaryArtist.getName()) + 1;
                sortedData.put(primaryArtist.getName(), newFreq);
            } else {
                // insert key value pair into the map
                sortedData.put(primaryArtist.getName(), 1);
            }
        }

        return sortedData;
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
