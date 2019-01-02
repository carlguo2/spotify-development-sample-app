package com.example.carlg.mysampleapp;

// Json Requested Data
public class RequestedData {
    private String href;
    private PlayHistoryObject[] items;
    private int limit;
    private String next;
    private CursorObject cursors;
    private int total;

    /** Create Accessor methods to be able to retrieve data after JSON conversion */

    public String getHref() {
        return href;
    }

    public PlayHistoryObject[] getItems() {
        return items;
    }

    public int getLimit() {
        return limit;
    }

    public String getNext() {
        return next;
    }

    public CursorObject getCursors() {
        return cursors;
    }

    public int getTotal() {
        return total;
    }
}
