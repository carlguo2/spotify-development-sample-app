package com.example.carlg.mysampleapp;

class JSONSpotifyContext {
    private String type;
    private String href;
    private ExternalURL external_urls;
    private String uri;

    /** Create accessor methods */
    public String getType() {
        return type;
    }

    public String getHref() {
        return href;
    }

    public ExternalURL getExternalUrls() {
        return external_urls;
    }

    public String getUri() {
        return uri;
    }
}
