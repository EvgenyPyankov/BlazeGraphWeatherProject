package com.controller;

import com.db.DBController;
import com.db.Entity.Station;
import com.db.Entity.YearMeasure;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/controller")
public class Controller {
    //final static Logger log = Logger.getLogger(Controller.class);
    DBController dbController = new DBController();
    @GET
    @Path("/stations")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllStations() throws Exception{
       List<Station> stations = dbController.getAllStations();


        JSONArray jsonArray = new JSONArray();
        for (Station test : stations)
        {
            jsonArray.add(stationToJSON(test));
        }
        return jsonArray.toString();
    }

    private JSONObject stationToJSON(Station station)
    {
        JSONObject curStation = new JSONObject();
        curStation.put("label", station.getLabel());
        curStation.put("lng", station.getLng());
        curStation.put("lat", station.getLat());
        curStation.put("alt", station.getAlt());
        curStation.put("id",station.getId());
        return curStation;
    }

//    @GET
//    @Path("/mean_year_temp")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getMeanYearTemp() throws Exception{
//        String temperature = dbController.getMeanYearTemp(2012);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("val", temperature);
//        return jsonObject.toString();
//    }

    @GET
    @Path("/meanYearTemp")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMeanYearTemp2() throws Exception{
        String temperature = dbController.getMeanYearTemp(1951);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("val", temperature);
        return jsonObject.toString();
    }



    @POST
    @Path("/meanYearTempByMonths")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMeanYearTempByMonths(String body) throws Exception{
        JSONObject jsonStation = (JSONObject) JSONValue.parse(body);
        String station = (String) jsonStation.get("station");
        int year = Integer.parseInt((String)jsonStation.get("year"));

        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "January");
        map.put(2, "February");
        map.put(3, "March");
        map.put(4, "April");
        map.put(5, "May");
        map.put(6, "June");
        map.put(7, "July");
        map.put(8, "August");
        map.put(9, "September");
        map.put(10, "October");
        map.put(11, "November");
        map.put(12, "December");



        HashMap<Integer, String> values = dbController.getMeanYearTempByMonths(station, year);

        JSONArray jsonArray = new JSONArray();

        for (Map.Entry<Integer, String> entry : values.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("month", map.get(entry.getKey()));
            jsonObject.put("val", entry.getValue());
            jsonArray.add(jsonObject);
        }

        return jsonArray.toString();

    }

    @POST
    @Path("/meanTempByYears")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMeanTempByYears(String body) throws Exception{
//        log.debug("getMeanTempByYears start");
//        JSONObject jsonStation = (JSONObject) JSONValue.parse(body);
//        String station = (String) jsonStation.get("station");
//        log.debug("station: "+station);
//
//
//        HashMap<Integer, String> values = dbController.getMeanTempByYears(station);
//
//        JSONArray jsonArray = new JSONArray();
//
//        for (Map.Entry<Integer, String> entry : values.entrySet()) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("year", entry.getKey());
//            jsonObject.put("val", entry.getValue());
//            jsonArray.add(jsonObject);
//        }
//        log.debug("Array: "+jsonArray.toString());
//        return jsonArray.toString();

        JSONObject jsonStation = (JSONObject) JSONValue.parse(body);
        String station = (String) jsonStation.get("station");
        List<YearMeasure> measures = dbController.getMeanTempByYears(station);
        JSONArray jsonArray = new JSONArray();
        for(YearMeasure measure: measures){
            JSONObject jsonObject = new JSONObject();
            int year = measure.getYear();
            double temperature = measure.getTemperature();
            jsonObject.put("year", year);
            jsonObject.put("val", Double.toString(temperature));
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
}
