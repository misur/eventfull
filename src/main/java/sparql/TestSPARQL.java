package sparql;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;



import com.eventful.eventful.RDFModel;
import com.eventful.eventful.Rest;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TestSPARQL {
	/*
	 * Klasa TestSPARQL ucitava preko metode load Model i posle izvrsava sparql upite nad modelom 
	 * 
	 */
public Model loadEvent(List<String> events) throws Exception {
		
		Model rdfGraph = ModelFactory.createDefaultModel();
		RDFModel.getInstance().save(rdfGraph); 
		for (String event : events) {
			InputStream dataStream = Rest.distillRDF(event, "microdata", "rdfxml");
			rdfGraph.read(dataStream, null, "RDF/XML");
		} 
		
		return rdfGraph;
	}
	
	public void testSparql(){
		List<String> list  = new LinkedList<String>();
		try {
			BufferedReader br =new  BufferedReader(new FileReader("links.txt"));
			String line;
			
			try {
				while ((line=br.readLine())!=null) {
					list.add(line);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Set<String> duplicate = new HashSet<String>(list);
		List<String> lists = new ArrayList<String>(duplicate);
		
		TestSPARQL test = new TestSPARQL();
		try {
			Model m = test.loadEvent(lists);
//			m.write(System.out, "RDF/XML");
		    m.write(new FileOutputStream("event.rdf"), "RDF/XML");
		    m.write(new FileOutputStream("event.ttl"), "TURTLE");
			Model m1 = ModelFactory.createDefaultModel();
			m1.read(new FileInputStream("myRDF.rdf"), null);
			
			QueryService qs = new QueryService(m1);
			
			List<String> resultList = qs.getEventNameByStreetAddress("982 Market Street");
			for (String result : resultList) {
				System.out.println("- " + result);
			}
			qs.getEventNameByDate1("2013-06-14T14:56:58.788Z");
			qs.getEventByLocality("San Francisco");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
