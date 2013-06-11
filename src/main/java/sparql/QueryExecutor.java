package sparql;

import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
/*
 * QueryExecutor klasa sadrzi metodu executeOneVariableSelectSparqlQuery koja je u sustini
 * ista ona metoda koju smo radili na predavanjima i koja kao ulazne parametre ima upit,
 * variablu koju ce upit da vrati i model nad kojim se vrsi upit i vraca listu stringova.
 */
public class QueryExecutor {

	public List<String> executeOneVariableSelectSparqlQuery(String query,
			String variable, Model model) {

		List<String> results = new LinkedList<String>();

		Query q = QueryFactory.create(query);
		QueryExecution qe = QueryExecutionFactory.create(q, model);
		ResultSet resultSet = qe.execSelect();

		while (resultSet.hasNext()) {
			QuerySolution solution = resultSet.nextSolution();
			RDFNode value = solution.get(variable);

			if (value.isLiteral())
				results.add(((Literal) value).getLexicalForm());
			else
				results.add(((Resource) value).getURI());
		}

		qe.close();

		return results;
	}

	public Model executeDescribeSparqlQuery(String queryString, Model model) {

		Query query = QueryFactory.create(queryString);

		QueryExecution qe = QueryExecutionFactory.create(query, model);
		Model resultModel = qe.execDescribe();

		qe.close();

		return resultModel;
	}

}
