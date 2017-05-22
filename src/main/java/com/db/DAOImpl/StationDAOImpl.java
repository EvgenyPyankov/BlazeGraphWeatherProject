package com.db.DAOImpl;

import com.bigdata.bop.Bind;
import com.bigdata.btree.Tuple;
import com.constants.Queries;
import com.db.DAO.StationDAO;
import com.db.Entity.Region;
import com.db.Entity.Station;
import com.db.QueryProcessing;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDAOImpl implements StationDAO{
    final static Logger log = LoggerFactory.getLogger(StationDAOImpl.class);
    public List<Station> getAllStations() throws Exception{
        TupleQueryResult result = QueryProcessing.processQuery(Queries.GET_ALL_STATIONS_WITH_DATA_QUERY);
       List<Station> stations = toList(result);

        return stations;
    }

    public List<Station> getStationsByRegion(String region) throws Exception {
        log.debug("Region: "+region);
        TupleQueryResult result = QueryProcessing.processQuery(String.format(Queries.GET_STATIONS_BY_REGION, region));
        if (!result.hasNext()) throw new RuntimeException("Query returned no rows! ");
        log.debug("Query: "+String.format(Queries.GET_STATIONS_BY_REGION, region));
        List<Station> stations = toList(result);




        return stations;
    }

    private List<Station> toList(TupleQueryResult result) throws Exception {
        List<Station> stations = new ArrayList<Station>();
        while (result.hasNext()) {
            log.debug("Next");
            BindingSet bs = result.next();
            Value label = bs.getValue("label");
            Value lon = bs.getValue("lng");
            Value alt = bs.getValue("alt");
            Value lat = bs.getValue("lat");
            Value id = bs.getValue("station");
            Value regionId = bs.getValue("region");
            Value regionLabel = bs.getValue("regionLabel");
            Value regionArea = bs.getValue("area");

            String labelStr = null;
            String lonStr = null;
            String altStr = null;
            String latStr = null;
            String idStr = null;
            String regionIdStr = null;
            String regionLabelStr = null;
            Double regionAreaDbl = -100.0;

            if (label != null) labelStr = label.stringValue();
            if (lon != null) lonStr = lon.stringValue();
            if (alt != null) altStr = alt.stringValue();
            if (lat != null) latStr = lat.stringValue();
            if (id != null) idStr = id.stringValue();
            if (regionId != null) regionIdStr = regionId.stringValue();
            if (regionLabel != null) regionLabelStr = regionLabel.stringValue();
            if (regionArea != null) regionAreaDbl = Double.parseDouble(regionArea.stringValue());

//            String label = bs.getValue("label").stringValue();
//            String lon = bs.getValue("lng").stringValue();
//            String alt = bs.getValue("alt").stringValue();
//            String lat = bs.getValue("lat").stringValue();
//            String id = bs.getValue("station").stringValue();
//            String regionId = bs.getValue("region").stringValue();
//            String regionLabel = bs.getValue("regionLabel").stringValue();
//            Double regionArea = Double.parseDouble(bs.getValue("area").stringValue());
            Region region = new Region(regionIdStr, regionLabelStr, regionAreaDbl);
            Station station = new Station(labelStr, latStr, lonStr, altStr, idStr, region);
            stations.add(station);
        }
        return stations;
    }
}
