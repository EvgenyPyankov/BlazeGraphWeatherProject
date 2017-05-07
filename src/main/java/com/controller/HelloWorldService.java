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
    public Response getMsg(@PathParam("param") String msg) {

        String output = "Jersey say : " + msg;

        return Response.status(200).entity(output).build();

    }

}