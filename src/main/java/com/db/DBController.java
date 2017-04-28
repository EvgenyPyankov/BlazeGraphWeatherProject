package com.db;

import com.db.Entity.Station;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface DBController {
    List<Station> getAllStations() throws Exception;
    String getMeanYearTemp(int year) throws Exception;
    String getMeanMonthTemp(int year, int month) throws Exception;
    HashMap<Integer, String> getMeanYearTempByMonths(int year) throws Exception;
    HashMap<Integer, String> getMeanYearTempByMonths(String station, int year) throws Exception;
    HashMap<Integer, String>getMeanTempByYears(String station) throws Exception;
}
