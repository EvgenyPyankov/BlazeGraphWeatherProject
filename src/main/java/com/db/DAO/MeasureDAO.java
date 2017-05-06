package com.db.DAO;

import com.db.Entity.DayMeasure;
import com.db.Entity.YearMeasure;

import java.util.Date;
import java.util.List;

public interface MeasureDAO {
    List<YearMeasure> getMeanTempByYears(String station) throws Exception;
    DayMeasure getDayMeasure(String station, Date date) throws Exception;
}
