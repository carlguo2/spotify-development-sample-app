package com.example.carlg.mysampleapp;

class JSONSpotifyArtists {
    private ExternalURL external_urls;
    private FollowersObject followers;
    private String[] genres;
    private String href;
    private String id;
    private ImageObject[] images;
    private String name;
    private int popularity;
    private String type;
    private String uri;

    public ExternalURL getExternalUrls() {
        return external_urls;
    }

    public FollowersObject getFollowers() {
        return followers;
    }

    public String[] getGenres() {
        return genres;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public ImageObject[] getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
