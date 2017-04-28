package com.db.Entity;

public class Station {
    private String Label;
    private String lat;
    //TODO: lng vs lon
    private String lon;
    private String alt;
    private String id;

    public Station(String label, String lat, String lng, String alt, String id) {
        Label = label;
        this.lat = lat;
        this.lon = lng;
        this.alt = alt;
        this.id = id;
    }

    public String getLabel() {
        return Label;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lon;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }
}
