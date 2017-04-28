package com.controller;

import com.bigdata.rdf.sail.webapp.client.RemoteRepositoryManager;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloWorldService {

    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg) throws Exception{
        final RemoteRepositoryManager repo = new RemoteRepositoryManager(
                "http://localhost:9999/blazegraph", false /* useLBS */);
        String buf = "hey ";
        //SELECT * {?s ?p ?o} LIMIT 100
        TupleQueryResult result = repo.getRepositoryForNamespace("test")
                .prepareTupleQuery("SELECT * {?s ?p ?o} LIMIT 100")
                .evaluate();
        try {
            while (result.hasNext()) {
                BindingSet bs = result.next();
                buf+=bs;
            }
        } finally {
            result.close();
        }
        String output = "Jersey say : " + msg;

        return Response.status(200).entity(buf).build();

    }

}