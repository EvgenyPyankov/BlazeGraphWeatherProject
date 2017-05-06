package com.db.DAO;

import com.db.Entity.Measure;

import java.util.Date;
import java.util.List;

public interface MeasureDAO {
    List<Measure> getMeanTempByYears(String station) throws Exception;
    Measure getDayMeasure(String station, Date date) throws Exception;
    List<Measure> getMeanTempByDaysOfMonth(String station, Date date) throws Exception;
}
