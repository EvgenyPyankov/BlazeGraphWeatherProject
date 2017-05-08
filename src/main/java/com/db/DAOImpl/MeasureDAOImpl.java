package com.db.DAOImpl;

import com.DAOFactory;
import com.constants.Queries;
import com.controller.Controller;
import com.db.DAO.MeasureDAO;
import com.db.Entity.Measure;
import com.db.Entity.Station;
import com.db.QueryProcessing;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class MeasureDAOImpl implements MeasureDAO {
    final static Logger log = LoggerFactory.getLogger(MeasureDAOImpl.class);
    Calendar calendar = new GregorianCalendar();

    public List<Measure> getMeanTempByYears(String station) throws Exception {
        TupleQueryResult result = QueryProcessing.processQueryWithOwnPrefix(Queries.PREFIX, String.format(Queries.GET_MEAN_TEMP_BY_YEARS_QUERY, "<"+station+">"));

        List<Measure> measures = new ArrayList<Measure>();

        while (result.hasNext()) {
            BindingSet bs = result.next();
            Date year = new SimpleDateFormat("yyyy").parse(bs.getValue("year").stringValue());
            double temperature = Double.parseDouble(bs.getValue("average").stringValue());
            Measure measure = new Measure();
            measure.setDate(year);
            measure.setMean(temperature);
            measures.add(measure);
        }

        return measures;
    }

    public List<Measure> getMeanTempByYearsForRegion(String region) throws Exception {
        log.debug("Region: "+region);
        List<Station> stations = DAOFactory.getStationDAO().getStationsByRegion(region);

        log.debug("Stations size: "+stations.size());
        String stationsStr = "";
        for (Station station:stations){
            stationsStr+="<"+station.getId()+">";
            stationsStr+="\n";
        }
        log.debug("Stations: "+stationsStr);
        TupleQueryResult result = QueryProcessing.processQueryWithOwnPrefix(Queries.PREFIX, String.format(Queries.GET_MEAN_TEMP_BY_YEARS_QUERY, stationsStr));

        List<Measure> measures = new ArrayList<Measure>();

        while (result.hasNext()) {
            BindingSet bs = result.next();
            Date year = new SimpleDateFormat("yyyy").parse(bs.getValue("year").stringValue());
            double temperature = Double.parseDouble(bs.getValue("average").stringValue());
            Measure measure = new Measure();
            measure.setDate(year);
            measure.setMean(temperature);
            measures.add(measure);
            log.debug("Measure: "+measure.toString());
        }

        return measures;
    }


    public Measure getDayMeasure(String station, Date date) throws Exception {
        String dateStr=String.format("%1$tY-%1$tm-%1$td", date);
        TupleQueryResult result = QueryProcessing.processQuery(String.format(Queries.GET_DAY_MEASURE, station, dateStr));

        //List<YearMeasure> measures = new ArrayList<YearMeasure>();

        BindingSet bs = result.next();
        Double mean = Double.parseDouble(bs.getValue("mean").stringValue());
        Double max =  Double.parseDouble(bs.getValue("max").stringValue());
        Double min =  Double.parseDouble(bs.getValue("min").stringValue());
        Measure measure = new Measure();
        measure.setMean(mean);
        measure.setMax(max);
        measure.setMin(min);
        measure.setDate(date);


        return measure;
    }

    public List<Measure> getMeanTempByDaysOfMonth(String station, Date date) throws Exception {
        log.debug("Got params: station: {}, date: {}", station, date);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;

        log.debug("Year = {}, month = {}", year, month);

        TupleQueryResult result = QueryProcessing.processQuery(String.format(Queries.GET_MEAN_TEMP_BY_DAYS_OF_THE_MONTH, station, year, month));

        List<Measure> measures = new ArrayList<Measure>();

        while (result.hasNext()) {
            BindingSet bs = result.next();
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(bs.getValue("date").stringValue());
            double temperature = Double.parseDouble(bs.getValue("mean").stringValue());
            Measure measure = new Measure();
            measure.setDate(d);
            measure.setMean(temperature);
            measures.add(measure);
        }

        log.debug("Sending object: {}", measures.toArray().toString());

        return measures;
    }
}
