package com.controller;

import com.bigdata.rdf.sail.webapp.client.RemoteRepositoryManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import org.apache.log4j.Logger;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

@Path("/queries")
public class QueryProcessing {
    private static Logger logger;

    static {
        try {

            logger = Logger.getLogger(QueryProcessing.class);

        }
        catch (Exception e){

        }
    }

    @POST
    @Path("/query")
    @Consumes(MediaType.APPLICATION_JSON)
    public String process(String body) throws Exception{
        JSONObject jsonObject = (JSONObject) JSONValue.parse(body);
        logger.info("here");
        logger.info("Contenct: "+jsonObject.get("content"));


        final RemoteRepositoryManager repo = new RemoteRepositoryManager(
                "http://localhost:9999/blazegraph", false /* useLBS */);
        String buf = "";
        //SELECT * {?s ?p ?o} LIMIT 100
        TupleQueryResult result = repo.getRepositoryForNamespace("test")
                .prepareTupleQuery(jsonObject.get("content").toString())
                .evaluate();
        try {
            while (result.hasNext()) {
                BindingSet bs = result.next();
                buf+=bs;
            }
        } finally {
            result.close();
        }
        logger.info(buf);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("result", "200");
        jsonObject1.put("content",buf);
        return jsonObject1.toString();
        //return Response.status(200).entity(buf).build();
    }
}
