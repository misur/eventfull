package com.eventful.eventful;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import sparql.TestSPARQL;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import crawler.Controller;

public class Main {

	/**Main klasa pokrece citav program 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//pravimo instacu klase Controler
		Controller c = new Controller();
		// metodom start pokrecemo crawler  i podesavamo nejgove konfiguracije
		c.start();
		//pravimo instancu klase TestSparql
		TestSPARQL test = new TestSPARQL();
		//pozivamo metodu test sparql u kojoj ucitavam rdf model nad kojim izvrsavamo upite
		test.testSparql();
		//Uzimamo event.rdf fajl
		File rdf = new File("event.rdf");
		
		CreateModel m = new CreateModel();
		m.events(rdf);
	}

	

}
