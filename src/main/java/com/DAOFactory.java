package com;

import com.db.DAO.StationDAO;
import com.db.DAOImpl.StationDAOImpl;

public class DAOFactory {
    private static StationDAO stationDAO = null;

    public static StationDAO getStationDAO(){
        if (stationDAO == null){
            stationDAO = new StationDAOImpl();
        }
        return stationDAO;
    }
}
