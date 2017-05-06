package com.db;

import com.DAOFactory;
import com.constants.Queries;
import com.db.Entity.DayMeasure;
import com.db.Entity.Station;
import com.db.Entity.YearMeasure;
import org.apache.log4j.Logger;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DBController {
    final static Logger log = Logger.getLogger(DBController.class);

    public List<Station> getAllStations() throws Exception {
        return DAOFactory.getStationDAO().getAllStations();
    }

    public String getMeanYearTemp(int year) throws Exception {
        String QUERY = "prefix geo:   <http://www.w3.org/2003/01/geo/wgs84_pos#> \n" +
                "prefix dbo:   <http://dbpedia.org/ontology/> \n" +
                "prefix dbr:   <http://dbpedia.org/resource/> \n" +
                "prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                "prefix mt:    <http://example.org/meteo_ru_data/> \n" +
                "prefix xsd:   <http://www.w3.org/2001/XMLSchema#> \n" +
                "prefix mto:   <http://example.org/meteo_ru_data/ontology/> \n" +
                "prefix mtr:   <http://example.org/meteo_ru_data/resource/> \n" +
                "prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "prefix schema:  <http://schema.org/> \n" +
                "prefix ispra: <http://dati.isprambiente.it/ontology/core#> \n" +
                "prefix dcterms: <http://purl.org/dc/terms/> \n" +
                "select(avg( ?o) as ?average)\n " +
                "where{\n" +
                " ?s mto:tmean ?o.\n" +
                "  ?s mto:datem ?date.\n" +
                "filter(?o >\"-100\"^^xsd:float).\n" +
                "FILTER ( ?date >= \"%d-01-01\"^^xsd:date && ?date <= \"%d-12-31\"^^xsd:date )}";


        TupleQueryResult result = QueryProcessing.processQuery(String.format(QUERY, year, year));

        String value = null;
        //result processing
        try {
            while (result.hasNext()) {
                BindingSet bs = result.next();

                //System.err.println(bs);
                value = (bs.getValue("average").stringValue());
            }
        } finally {
            result.close();
        }
        return value;
    }

    public String getMeanMonthTemp(int year, int month) throws Exception {
        String QUERY = "prefix geo:   <http://www.w3.org/2003/01/geo/wgs84_pos#> \n" +
                "prefix dbo:   <http://dbpedia.org/ontology/> \n" +
                "prefix dbr:   <http://dbpedia.org/resource/> \n" +
                "prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                "prefix mt:    <http://example.org/meteo_ru_data/> \n" +
                "prefix xsd:   <http://www.w3.org/2001/XMLSchema#> \n" +
                "prefix mto:   <http://example.org/meteo_ru_data/ontology/> \n" +
                "prefix mtr:   <http://example.org/meteo_ru_data/resource/> \n" +
                "prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "prefix schema:  <http://schema.org/> \n" +
                "prefix ispra: <http://dati.isprambiente.it/ontology/core#> \n" +
                "select (avg(?o) as ?average)\n" +
                "where{\n" +
                " ?s mto:tmean ?o.\n" +
                "  ?s mto:datem ?date.\n" +
                "  ?s mto:st_measure <http://example.org/meteo_ru_data/resource/stat_37099>.\n" +
                "filter(?o >\"-100\"^^xsd:float).\n" +
                "FILTER ( ?date >= \"%d-%d-01\"^^xsd:date && ?date <= \"%d-%d-28\"^^xsd:date )}";


        TupleQueryResult result = QueryProcessing.processQuery(String.format(QUERY, year, month, year, month));

        String value = null;
        //result processing
        try {
            while (result.hasNext()) {
                BindingSet bs = result.next();

                //System.err.println(bs);
                value = (bs.getValue("average").stringValue());
            }
        } finally {
            result.close();
        }
        return value;
    }

    public HashMap<Integer, String> getMeanYearTempByMonths(int year) throws Exception {


        TupleQueryResult result = QueryProcessing.processQuery(String.format(Queries.GET_MEAN_TEMP_BY_MONTHS_QUERY, year));


        HashMap map = new HashMap<Integer, String>();
        try {
            while (result.hasNext()) {
                BindingSet bs = result.next();

                //System.err.println(bs);
                map.put(Integer.parseInt(bs.getValue("mon").stringValue()), bs.getValue("average").stringValue());
                //value = (bs.getValue("average").stringValue());
            }
        } finally {
            result.close();
        }
        return map;
    }

    public DayMeasure getDayMeasure(String station, Date date) throws Exception{
        return DAOFactory.getMeasureDAO().getDayMeasure(station, date);
    }

    public HashMap<Integer, String> getMeanYearTempByMonths(String station, int year) throws Exception {
        String QUERY =
                "select ?mon (avg(?o) as ?average)\n" +
                "where{\n" +
                " ?s mto:tmean ?o.\n" +
                "  ?s mto:datem ?date.\n" +
                "  ?s mto:st_measure <%s>.\n" +
                "filter(?o >\"-100\"^^xsd:float).\n" +
                "  filter(year(?date) = %d).\n" +
                "  }\n" +
                "GROUP BY (month(?date) AS ?mon)\n" +
                "Order by (?mon)";


        TupleQueryResult result = QueryProcessing.processQuery(String.format(QUERY, station, year));


        HashMap map = new HashMap<Integer, String>();
        try {
            while (result.hasNext()) {
                BindingSet bs = result.next();

                //System.err.println(bs);
                map.put(Integer.parseInt(bs.getValue("mon").stringValue()), bs.getValue("average").stringValue());
                //value = (bs.getValue("average").stringValue());
            }
        } finally {
            result.close();
        }
        return map;
    }

    public List<YearMeasure> getMeanTempByYears(String station) throws Exception {
        return DAOFactory.getMeasureDAO().getMeanTempByYears(station);
    }

//    public HashMap<Integer, String> getMeanTempByYears(String station) throws Exception {
//        log.debug("getMeanTempByYears starts");
//        log.debug("station: "+station);
//
//        TupleQueryResult result = QueryProcessing.processQueryWithOwnPrefix(Queries.PREFIX, String.format(Queries.GET_MEAN_TEMP_BY_YEARS_QUERY, station));
//        log.debug("result = "+result.toString());
//
//
//        HashMap map = new HashMap<Integer, String>();
//        try {
//            while (result.hasNext()) {
//                BindingSet bs = result.next();
//
//                //System.err.println(bs);
//                map.put(Integer.parseInt(bs.getValue("year").stringValue()), bs.getValue("average").stringValue());
//                //value = (bs.getValue("average").stringValue());
//            }
//        } finally {
//            result.close();
//        }
//        log.debug("Map size = " + map.size());
//        return map;
//    }
}
