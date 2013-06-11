package sparql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Constants;

import com.hp.hpl.jena.graph.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
/*Klasa  QueryService je klasa u kojoj se kreiraju upiti(sparql)
 * 
 * 
 * */
public class QueryService {
	private Model rdfGraph;
	private QueryExecutor queryExecutor = new QueryExecutor();

	public QueryService(Model m) {
		rdfGraph = m;
	}

	public List<String> getEventNameByStreetAddress(String streetAddress) {
		String query = "PREFIX schema: <" + Constants.SCHEMA + "> \n"
				+ "SELECT  ?name  \n" + "WHERE { \n"
				+ "?event a schema:Event; \n" + "schema:name ?name; \n"
				+ "schema:location ?location .\n"
				+ "?location a schema:Place; \n"
				+ "schema:address ?address .\n"
				+ "?address  a schema:PostalAddress; \n"
				+ "schema:streetAddress " + "\"" + streetAddress + "\""
				+ ". \n" + "}";

		System.out.println(query);

		List<String> result = queryExecutor
				.executeOneVariableSelectSparqlQuery(query, "name", rdfGraph);

		if (result != null)
			return result;

		return new ArrayList<String>();
	}


	public void getEventNameByDate1(String date) {
		String query = "PREFIX schema: <" + Constants.SCHEMA + "> \n"
				+ "PREFIX xsd: <" + Constants.XSD + "> \n"
				+ "SELECT  ?name ?startdate ?description\n"
				+"WHERE { \n"
				+ "?event a schema:Event; \n"
				+ "schema:name ?name; \n"
				+ "schema:description ?description;\n"
				+ "schema:startDate ?startdate . \n"
				+ "FILTER(xsd:dateTime(?startdate) = \"" + date
				+ "\"^^xsd:dateTime" + ").\n" + "}";
		com.hp.hpl.jena.query.Query q = QueryFactory.create(query);
		QueryExecution qe = QueryExecutionFactory.create(q, rdfGraph);
		ResultSet resultSet = qe.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution solution = resultSet.nextSolution();
			String name = solution.getLiteral("name").getLexicalForm();
			System.out.println("\n Event name \"" + name + "\"");
			System.out.println("start date:"
					+ solution.getLiteral("startdate").getLexicalForm());
			System.out.println("description  "
					+ solution.getLiteral("description").getLexicalForm());
		}

		qe.close();
	}

	 public void getEventByLocality(String locality){
		 String query = "PREFIX schema: <" + Constants.SCHEMA + "> \n"
					+ "SELECT  ?name ?description ?streetAddress ?addressRegion \n"
					+ "WHERE { \n"
					+ "?event a schema:Event; \n"
					+ "schema:name ?name; \n"
					+ "schema:description ?description;\n"
					+ "schema:location ?location .\n"
					+ "?location a schema:Place; \n"
					+ "schema:address ?address .\n"
					+ "?address  a schema:PostalAddress; \n"
					+ "schema:addressLocality " + "\"" + locality + "\""+ "; \n"
					+ "schema:streetAddress ?streetAddress; \n"
					+ "schema:addressRegion ?addressRegion. \n"
					+ "}";
		 
		 com.hp.hpl.jena.query.Query q = QueryFactory.create(query);
			QueryExecution qe = QueryExecutionFactory.create(q, rdfGraph);
			ResultSet resultSet = qe.execSelect();

			while (resultSet.hasNext()) {
				QuerySolution solution = resultSet.nextSolution();
				String name = solution.getLiteral("name").getLexicalForm();
				System.out.println("\n Event name \"" + name + "\"");
				System.out.println("description  "
						+ solution.getLiteral("description").getLexicalForm());
				System.out.println("address Region:"
						+ solution.getLiteral("addressRegion").getLexicalForm());
				System.out.println("street Address:"
						+ solution.getLiteral("streetAddress").getLexicalForm());
			}

			qe.close();
	 }
}
