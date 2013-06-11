package com.eventful.eventful;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;
import util.Constants;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;


/*Klasa RDFModel nam sluzi za kreiranje  RDFModela , 
 * za cuvanje objekata u RDFModel prikazivanja i  cuvanje u fajl
 * 
 * */
public class RDFModel {
	
	Model graph;
	Bean2RDF writer;
	RDF2Bean reader;
	
	private static RDFModel INSTANCE;
	//konsturktor
	private RDFModel (){
		graph = ModelFactory.createDefaultModel();
		graph.setNsPrefix("schema", Constants.SCHEMA);
		graph.setNsPrefix("xsd", Constants.XSD);
		graph.setNsPrefix("dc", Constants.DC);
		
		writer = new Bean2RDF(graph);
		reader = new RDF2Bean(graph);
	}
	//kreiranje instance klase RDFModel
	public static RDFModel getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new RDFModel();
		}
		
		return INSTANCE;
	}
	//Cuvanje objekata u model
	public void save(Object o) {
		writer.save(o);
	}
	
	//Ucitavanje objekta preko zadatog uri-ja
	public Object load(String uri) {
		return reader.load(uri);
	}
	//prikazivanje  grafa u zadatoj turtle formi
	public void printOut(){
		graph.write(System.out, "TURTLE");
	}
	//cuvanje  RDFModela u zadati fajl sa odgovarjucim tipom zapisa
	public void saveAsFile(String fileName,String type){
		try {
			FileOutputStream fos = new FileOutputStream(new File(fileName));
			
			
			graph.write(fos,type);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
