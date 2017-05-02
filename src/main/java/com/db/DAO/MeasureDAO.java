package com.db.DAO;

import com.db.Entity.YearMeasure;

import java.util.List;

public interface MeasureDAO {
    List<YearMeasure> getMeanTempByYears(String station) throws Exception;
}
