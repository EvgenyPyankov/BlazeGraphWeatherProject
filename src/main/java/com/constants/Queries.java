package com.constants;

public class Queries {

//    public static final String GET_ALL_STATIONS_WITH_DATA_QUERY = "select ?label ?lat ?lng ?alt ?station\n" +
//            "where{\n" +
//            "  ?station rdf:type ispra:MeasureStation.\n" +
//            "  ?station schema:location ?location.\n" +
//            "  ?location rdfs:label ?label.\n" +
//            "  ?location schema:geo [schema:latitude ?lat; schema:longitude ?lng; geo:alt ?alt].\n" +
//            "  filter (exists{?s mto:st_measure ?station.}).\n" +
//            "  }\n";
    public static final String GET_ALL_STATIONS_WITH_DATA_QUERY="select ?label ?lat ?lng ?alt ?station ?regionLabel ?area ?region\n" +
        "where{\n" +
        "  ?station rdf:type ispra:MeasureStation.\n" +
        "  ?station schema:location ?location.\n" +
        "  ?location rdfs:label ?label.\n" +
        "  ?location schema:geo [schema:latitude ?lat; schema:longitude ?lng; geo:alt ?alt].\n" +
        "  ?location <http://example.org/meteo_ru_data/ontology/federalSubject> ?region \n" +
        "  filter (exists{?s mto:st_measure ?station.}).\n" +
        "  service <http://dbpedia.org/sparql>\n" +
        "    {\n" +
        "    ?region rdfs:label ?regionLabel .\n" +
        "    ?region dbp:areaKm ?area .\n" +
        "    }\n" +
        "    filter(lang(?regionLabel) = 'en')\n" +
        " }";
    public static final String PREFIX = "prefix geo:   <http://www.w3.org/2003/01/geo/wgs84_pos#> \n" +
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
            "prefix dbp: <http://dbpedia.org/property/> \n";

    public static final String GET_MEAN_TEMP_BY_MONTHS_QUERY = "prefix geo:   <http://www.w3.org/2003/01/geo/wgs84_pos#> \n" +
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
            "select ?mon (avg(?o) as ?average)\n" +
            "where{\n" +
            " ?s mto:tmean ?o.\n" +
            "  ?s mto:datem ?date.\n" +
            "  ?s mto:st_measure <http://example.org/meteo_ru_data/resource/stat_37099>.\n" +
            "filter(?o >\"-100\"^^xsd:float).\n" +
            "  filter(year(?date) = %d).\n" +
            "  }\n" +
            "GROUP BY (month(?date) AS ?mon)\n" +
            "Order by (?mon)";

    public static final String GET_MEAN_TEMP_BY_YEARS_QUERY = "select ?year (avg(?o) as ?average)\n" +
            "where{\n" +
            " ?s mto:tmean ?o.\n" +
            "  ?s mto:datem ?date.\n" +
            "  ?s mto:st_measure <%s>.\n" +
            "filter(?o >\"-100\"^^xsd:float).\n" +
            "  }\n" +
            "group by (year(?date) as ?year)" +
            "order by (?year)";

    public static final String GET_DAY_MEASURE="select ?mean ?max ?min ?date\n" +
            "where{\n" +
            "  ?s mto:tmean ?mean.\n" +
            "  ?s mto:tmax ?max.\n" +
            "  ?s mto:tmin ?min.\n" +
            "  ?s mto:datem ?date.\n" +
            "  ?s mto:st_measure <%s>.\n" +
            "  filter (?date = \"%s\"^^xsd:date).\n" +
            "}\n";

    public static final String GET_MEAN_TEMP_BY_DAYS_OF_THE_MONTH="select ?date ?mean\n" +
            "where{\n" +
            "  ?measure mto:tmean ?mean.\n" +
            "  ?measure mto:datem ?date.\n" +
            "  ?measure mto:st_measure <%s>.\n" +
            "  filter(?mean >\"-100\"^^xsd:float).\n" +
            "  filter(year(?date) = %d && month(?date)= %d).\n" +
            "}\n";
}
