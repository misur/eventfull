package sparql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Constants;

import com.hp.hpl.jena.rdf.model.Model;

public class QueryService {
	private Model rdfGraph;
	private QueryExecutor queryExecutor = new QueryExecutor();

	public QueryService(Model m) {
		rdfGraph = m;
	}

	public List<String> getEventNameByStreetAddress(String streetAddres) {
		String query = "PREFIX schema: <" + Constants.SCHEMA + "> \n"
				+ "SELECT  ?name \n" + "WHERE { \n"
				+ "?event a schema:Event; \n" + "schema:name ?name; \n"
				+ "schema:location ?location .\n"
				+ "?location a schema:Place; \n"
				+ "schema:address ?address .\n"
				+ "?address  a schema:PostalAddress; \n"
				+ "schema:streetAddress " + "\"" + streetAddres + "\"" + ". \n"
				+ "}";

		System.out.println(query);

		List<String> result = queryExecutor
				.executeOneVariableSelectSparqlQuery(query, "name", rdfGraph);

		if (result != null)
			return result;

		return new ArrayList<String>();
	}

	public List<String> getEventNameByDate() {
		String query = 
				"PREFIX schema: <" + Constants.SCHEMA + "> \n"+
				"PREFIX xsd: <" + Constants.XSD + "> \n"+
				"SELECT  ?name \n" +
				"WHERE { \n"+ 
				"?event a schema:Event; \n" + 
				"schema:name ?name; \n"+ 
				"schema:startDate ?start . \n"+
				"FILTER(?start = \"September 1, 2013 Sunday 7:00 PM\") \n"
				+ "}";

		System.out.println(query);

		List<String> result = queryExecutor
				.executeOneVariableSelectSparqlQuery(query, "name", rdfGraph);

		if (result != null)
			return result;

		return new ArrayList<String>();
	}
}
