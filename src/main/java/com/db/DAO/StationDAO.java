package com.db.DAO;

import com.db.Entity.Station;

import java.sql.SQLException;
import java.util.List;

public interface StationDAO {
    List<Station> getAllStations() throws Exception;
}