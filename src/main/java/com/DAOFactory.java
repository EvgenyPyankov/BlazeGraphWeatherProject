package com;

import com.db.DAO.MeasureDAO;
import com.db.DAO.StationDAO;
import com.db.DAOImpl.MeasureDAOImpl;
import com.db.DAOImpl.StationDAOImpl;

public class DAOFactory {
    private static StationDAO stationDAO = null;
    private static MeasureDAO measureDAO = null;

    public static StationDAO getStationDAO(){
        if (stationDAO == null){
            stationDAO = new StationDAOImpl();
        }
        return stationDAO;
    }

    public static MeasureDAO getMeasureDAO(){
        if (measureDAO == null){
            measureDAO = new MeasureDAOImpl();
        }
        return measureDAO;
    }
}
