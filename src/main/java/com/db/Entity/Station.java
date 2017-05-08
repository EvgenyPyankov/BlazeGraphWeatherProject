package com.db.Entity;

public class Station {
    private String Label;
    private String lat;
    //TODO: lng vs lon
    private String lon;
    private String alt;
    private String id;
    private Region region;

    public Station(String label, String lat, String lng, String alt, String id, Region region) {
        Label = label;
        this.lat = lat;
        this.lon = lng;
        this.alt = alt;
        this.id = id;
        this.region = region;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Station{" +
                "Label='" + Label + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                ", region=" + region +
                '}';
    }
}
