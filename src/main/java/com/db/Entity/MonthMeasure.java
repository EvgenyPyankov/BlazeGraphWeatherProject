package com.db.Entity;

import java.time.Month;
import java.time.Year;

public class MonthMeasure extends Measure {
    private Month month;

    public MonthMeasure(double temperature, double humidity, Month month) {
        super(temperature, humidity);
        this.month = month;
    }
}
