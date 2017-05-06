package com.db.Entity;

import java.util.Date;

public class Measure {
    private double mean;
    private double humidity;
    private double max;
    private double min;
    private Date date;

    public Measure(double temperature, double humidity) {
        this.mean = temperature;
        this.humidity = humidity;
    }

    public Measure(){}

    public Measure(double temperature) {
        this.mean = temperature;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "mean=" + mean +
                ", humidity=" + humidity +
                ", max=" + max +
                ", min=" + min +
                ", date=" + date +
                '}';
    }
}
