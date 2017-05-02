package com.db.DAOImpl;

import com.constants.Queries;
import com.db.DAO.MeasureDAO;
import com.db.Entity.YearMeasure;
import com.db.QueryProcessing;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MeasureDAOImpl implements MeasureDAO {
    public List<YearMeasure> getMeanTempByYears(String station) throws Exception {
        TupleQueryResult result = QueryProcessing.processQueryWithOwnPrefix(Queries.PREFIX, String.format(Queries.GET_MEAN_TEMP_BY_YEARS_QUERY, station));

        List<YearMeasure> measures = new ArrayList<YearMeasure>();

            while (result.hasNext()) {
                BindingSet bs = result.next();
                int year = Integer.parseInt(bs.getValue("year").stringValue());
                double temperature = Double.parseDouble(bs.getValue("average").stringValue());
                YearMeasure measure = new YearMeasure(temperature, year);
                measures.add(measure);
            }

        return measures;
    }
}
