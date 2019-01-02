package com.example.carlg.mysampleapp;

class PlayHistoryObject {
    private JSONSpotifyTrack track;
    private String played_at;
    private JSONSpotifyContext context;


    /** Create Accessor methods to be able to retrieve data after JSON conversion  */

    public JSONSpotifyTrack getTrack() {
        return track;
    }

    public JSONSpotifyContext getContext() {
        return context;
    }

    public String getPlayedAt() {
        return played_at;
    }
}
