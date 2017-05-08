package com.db.DAOImpl;

import com.constants.Queries;
import com.db.DAO.StationDAO;
import com.db.Entity.Region;
import com.db.Entity.Station;
import com.db.QueryProcessing;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDAOImpl implements StationDAO{
    public List<Station> getAllStations() throws Exception{
        TupleQueryResult result = QueryProcessing.processQuery(Queries.GET_ALL_STATIONS_WITH_DATA_QUERY);
        List<Station> stations = new ArrayList<Station>();
        while (result.hasNext()) {
            BindingSet bs = result.next();
            String label = bs.getValue("label").stringValue();
            String lon = bs.getValue("lng").stringValue();
            String alt = bs.getValue("alt").stringValue();
            String lat = bs.getValue("lat").stringValue();
            String id = bs.getValue("station").stringValue();
            String regionId = bs.getValue("region").stringValue();
            String regionLabel = bs.getValue("regionLabel").stringValue();
            Double regionArea = Double.parseDouble(bs.getValue("area").stringValue());
            Region region = new Region(regionId,regionLabel,regionArea);
            Station station = new Station(label, lat, lon, alt, id, region);
            stations.add(station);
        }
        return stations;
    }
}
