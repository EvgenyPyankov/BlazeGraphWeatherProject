package com.db;

import com.bigdata.rdf.sail.webapp.client.RemoteRepository;
import com.bigdata.rdf.sail.webapp.client.RemoteRepositoryManager;
import com.controller.Constants;

public class DBUtils {
    private static final String sparqlEndPoint = "http://192.168.159.128:9999/blazegraph";
    private static final RemoteRepositoryManager remoteRepositoryManager;
    private static final RemoteRepository remoteRepository;

    static {
        try {
            remoteRepositoryManager = new RemoteRepositoryManager(
                    sparqlEndPoint, false);
            remoteRepository = remoteRepositoryManager.getRepositoryForNamespace(Constants.CURRENT_NAMESPACE);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static RemoteRepository getRepository() {
        return remoteRepository;
    }

}