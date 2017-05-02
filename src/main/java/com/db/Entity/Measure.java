package com.db.Entity;

public abstract class Measure {
    private double temperature;
    private double humidity;

    public Measure(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Measure(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
