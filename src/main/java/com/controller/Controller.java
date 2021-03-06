package com.controller;

import com.db.DBController;
import com.db.Entity.Measure;
import com.db.Entity.Station;
//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.*;


@Path("/controller")
public class Controller {
    final static Logger log = LoggerFactory.getLogger(Controller.class);
    Calendar calendar = new GregorianCalendar();
    DBController dbController = new DBController();
    @GET
    @Path("/stations")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response getAllStations() throws Exception{

        log.debug("getAllStations starts");
            List<Station> stations = dbController.getAllStations();
        log.debug("Got number of stations: "+stations.size());



        JSONArray jsonArray = new JSONArray();
        for (Station station : stations)
        {
            log.debug("Station: "+station.toString());
            jsonArray.add(stationToJSON(station));
        }
        log.debug("JSON Array: "+jsonArray);

        return Response.status(200).entity(jsonArray).build();
    }

    private JSONObject stationToJSON(Station station)
    {
        JSONObject curStation = new JSONObject();
        curStation.put("label", station.getLabel());
        curStation.put("lng", station.getLng());
        curStation.put("lat", station.getLat());
        curStation.put("alt", station.getAlt());
        curStation.put("id",station.getId());
        curStation.put("regionLabel", station.getRegion().getLabel());
        curStation.put("regionArea", station.getRegion().getArea());
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

    //TODO:error codes
    @POST
    @Path("/tempDelta")
   // @Produces(MediaType.APPLICATION_JSON)
    public Response calculateTempDelta(String body){
        JSONObject json = (JSONObject) JSONValue.parse(body);
        try {
            String id = (String) json.get("id");
            int fromFrom = Integer.parseInt((String)json.get("fromFrom"));
            int fromTo = Integer.parseInt((String)json.get("fromTo"));
            int toFrom = Integer.parseInt((String)json.get("toFrom"));
            int toTo = Integer.parseInt((String)json.get("toTo"));


            log.debug("id: {}, fromFrom: {}, fromTo: {}, toFrom: {}, toTo: {}", new Object[]{id, fromFrom, fromTo, toFrom, toTo});

            int[] years = new int[4];
            years[0] = fromFrom;
            years[1] = fromTo;
            years[2] = toFrom;
            years[3] = toTo;


            double[] values = null;
            JSONObject jsonObject = new JSONObject();

        try {
            values = dbController.getTempDelta(id, years);
        }
        catch (Exception e){
            log.debug("Exception: "+e);
            return Response.status(500).build();
        }


        jsonObject.put("from", values[0]);
        jsonObject.put("to", values[1]);
        jsonObject.put("avg", values[2]);



        return Response.status(200).entity(jsonObject).build();
        //return Response.status(404).build();
        //return Response.status(200).entity(jsonArray).build();

        }
        catch (Exception e){
            log.debug("Exception e: "+e);
        }
        return Response.status(200).build();
    }

    @POST
    @Path("/meanTempByYears")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMeanTempByYears(String body) throws Exception{
        JSONObject jsonStation = (JSONObject) JSONValue.parse(body);
        String station = (String) jsonStation.get("id");
        List<Measure> measures = dbController.getMeanTempByYears(station);
        JSONArray jsonArray = new JSONArray();
        for(Measure measure: measures){
            JSONObject jsonObject = new JSONObject();
            Date year = measure.getDate();
            calendar.setTime(year);
            double temperature = measure.getMean();
            jsonObject.put("year", calendar.get(Calendar.YEAR));
            jsonObject.put("val", Double.toString(temperature));
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    @POST
    @Path("/meanTempByYearsForRegion")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMeanTempByYearsForRegion(String body) throws Exception{
        JSONObject jsonStation = (JSONObject) JSONValue.parse(body);
        String region = (String) jsonStation.get("id");
        log.debug("Got region from json: "+region);
        List<Measure> measures = dbController.getMeanTempByYearsForRegion(region);
        log.debug("Measures size: "+measures.size());
        JSONArray jsonArray = new JSONArray();
        for(Measure measure: measures){
            JSONObject jsonObject = new JSONObject();
            Date year = measure.getDate();
            calendar.setTime(year);
            double temperature = measure.getMean();
            jsonObject.put("year", calendar.get(Calendar.YEAR));
            jsonObject.put("val", Double.toString(temperature));
            jsonArray.add(jsonObject);
        }
        log.debug("Sending json: "+jsonArray.toString());
        return jsonArray.toString();
    }

    @POST
    @Path("/dayMeasure")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDayMeasure(String body) throws Exception{
        log.debug("Got json: "+body);
        JSONObject json = (JSONObject) JSONValue.parse(body);
        String format = (String)json.get("format");
        String station = (String) json.get("station");
        String dateStr = (String) json.get("date");
        Date date = new SimpleDateFormat(format).parse(dateStr);
        log.debug("Params: format = {}, station = {}, dateStr = {}, date = {}", new Object[]{format, station, dateStr, date.toString()});
        Measure measure = dbController.getDayMeasure(station,date);
        JSONObject measureJson = new JSONObject();
//        measureJson.put("date", measure.getMean());
        measureJson.put("mean", measure.getMean());
        measureJson.put("max", measure.getMax());
        measureJson.put("min", measure.getMin());
        measureJson.put("date", measure.getDate());
        log.debug("Sending json object: {}", measureJson.toString());
        measureJson.put("result", 200);
        return measureJson.toString();
    }

    @POST
    @Path("/meanTempByDaysOfMonth")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMeanTempByDaysOfMonth(String body) throws Exception{
        log.debug("Got json: "+body);
        JSONObject json = (JSONObject) JSONValue.parse(body);
        String format = (String)json.get("format");
        String station = (String) json.get("station");
        String dateStr = (String) json.get("date");
        Date date = new SimpleDateFormat(format).parse(dateStr);
        log.debug("Params: format = {}, station = {}, dateStr = {}, date = {}", new Object[]{format, station, dateStr, date.toString()});
        List<Measure> measures = dbController.getMeanTempByDaysOfMonth(station,date);
        JSONObject measureJson = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for(Measure measure: measures){
            JSONObject jsonObject = new JSONObject();
            Date year = measure.getDate();
            calendar.setTime(year);
            double temperature = measure.getMean();
            jsonObject.put("day", calendar.get(Calendar.DAY_OF_MONTH));
            jsonObject.put("val", Double.toString(temperature));
            jsonArray.add(jsonObject);
        }
        log.debug("Sending json object: {}", jsonArray.toString());
        return jsonArray.toString();
    }
}
