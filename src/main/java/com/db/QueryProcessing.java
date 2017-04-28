package com.db;

import org.openrdf.query.TupleQueryResult;

public class QueryProcessing {

    public static TupleQueryResult processQuery(String query) throws Exception {
        TupleQueryResult result = DBUtils.getRepository()
                .prepareTupleQuery(Queries.PREFIX + query)
                .evaluate();
        return result;
    }

    public static TupleQueryResult processQueryWithOwnPrefix(String prefix, String query) throws Exception {
        TupleQueryResult result = DBUtils.getRepository()
                .prepareTupleQuery(prefix + query)
                .evaluate();
        return result;
    }

}
