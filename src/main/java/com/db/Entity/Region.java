package com.db.Entity;

public class Region {
    private String id;
    private String label;
    private double area;

    public Region(String id, String label, double area) {
        this.id = id;
        this.label = label;
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
