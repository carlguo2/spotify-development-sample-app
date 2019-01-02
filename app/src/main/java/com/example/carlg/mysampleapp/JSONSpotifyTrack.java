package com.example.carlg.mysampleapp;

class JSONSpotifyTrack {
    private JSONSpotifyArtists[] artists;
    private String[] available_markets;
    private int disc_number;
    private int duration_ms;
    private boolean explicit;
    private ExternalURL external_urls;
    private String href;
    private String id;
    private boolean is_playable;
    private LinkedTrackObj linked_from;
    private String name;
    private String preview_url;
    private int track_number;
    private String type;
    private String uri;

    /* Create accessor methods, good lord there's a lot */

    public JSONSpotifyArtists[] getArtists() {
        return artists;
    }

    public String[] getAvailableMarkets() {
        return available_markets;
    }

    public int getDiscNumber() {
        return disc_number;
    }

    public int getDurationms() {
        return duration_ms;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public ExternalURL getExternalUrls() {
        return external_urls;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public boolean isPlayable() {
        return is_playable;
    }

    public LinkedTrackObj getLinkedFrom() {
        return linked_from;
    }

    public String getName() {
        return name;
    }

    public String getPreviewUrl() {
        return preview_url;
    }

    public int getTrackNumber() {
        return track_number;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
