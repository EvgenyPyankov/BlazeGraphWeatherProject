package com.db.Entity;

import java.util.Date;

public class DayMeasure extends Measure {
    private Date day;

    public DayMeasure(double temperature, double humidity, Date day) {
        super(temperature, humidity);
        this.day = day;
    }

    public DayMeasure(){}
}
