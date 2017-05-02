package com.db.Entity;

import java.time.Year;

public class YearMeasure extends Measure {
    private int year;

    public YearMeasure(double temperature, double humidity, int year) {
        super(temperature, humidity);
        this.year = year;
    }

    public YearMeasure(double temperature, int year) {
        super(temperature);
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
