package com.eventful.eventful;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.UriGenerator;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import domain.Address;
import domain.AggregateOffer;
import domain.Event;
import domain.Location;
import domain.Offer;
import domain.Person;

public class TestModel {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File rdf = new File("event.rdf");
		TestModel m = new TestModel();
		 m.events(rdf);

			RDFModel.getInstance().saveAsFile("myRDF.ttl","TURTLE");
		
		
	}
	
	public  void events(File rdf) {

		try {

			
			
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(rdf);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("rdf:Description");
			System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					Event e;
					NodeList nl = eElement.getChildNodes();
					if (nl.item(nl.getLength() - 2).hasAttributes()) {

						if (nl.item(nl.getLength() - 2).getAttributes().item(0)
								.getTextContent()
								.equals("http://schema.org/Event")) {
							e = new Event();
							e.setUri(UriGenerator.generate(e));
							for (int i = 0; i < nl.getLength(); i++) {
								if (nl.item(i).getNodeName()
										.equals("schema:startDate")) {
									// e.setStartDate(startDate)
								}
								if (nl.item(i).getNodeName()
										.equals("schema:performer")) {
									String id =findIdForPerformer(nl.item(i)
											.getAttributes().item(0)
											.getTextContent());
									
									e.setPerformer(person(id));
								}
								if (nl.item(i).getNodeName()
										.equals("schema:location")) {
									Location l = location(nl.item(i)
											.getAttributes().item(0)
											.getTextContent());
									e.setLocation(l);
								}
								if (nl.item(i).getNodeName()
										.equals("schema:name")) {
									e.setName(nl.item(i).getTextContent());
								}
								if (nl.item(i).getNodeName()
										.equals("schema:description")) {
									e.setDescription(nl.item(i)
											.getTextContent());
								}
								
								
								if (nl.item(i).getNodeName()
										.equals("schema:offers")) {
//									Object o =offer(nl.item(i)
//											.getAttributes().item(0)
//											.getTextContent());
//									if(o instanceof AggregateOffer ){
//										e.setAggregateOffer((AggregateOffer)o);
//									}else if(o instanceof Offer){
//										e.setOffer((Offer)o);
//									}
								}
								if (nl.item(i).getNodeName()
										.equals("schema:image")) {
									URL url = new URL(nl.item(i)
											.getAttributes().item(0)
											.getTextContent());
									e.setImage(url);
								}
							}

							RDFModel.getInstance().save(e);
							e.show();

							
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String findIdForPerformer(String id){
		try {
			File rdf = new File("event.rdf");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = null;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(rdf);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("rdf:Description");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList nl = eElement.getChildNodes();


							if (nl.item(0).getParentNode().getAttributes()
									.item(0).getTextContent()
									.equals(id)) {
								for (int i = 0; i < nl.getLength(); i++) {
									if (nl.item(i).getNodeName()
											.equals("rdf:first")) {
										return nl.item(i)
												.getAttributes().item(0)
												.getTextContent();
									}
									}

								}

							}

						}
					
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Person person(String textContent) {
		Person p=  new Person();
		try {
			File rdf = new File("event.rdf");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = null;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(rdf);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("rdf:Description");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList nl = eElement.getChildNodes();

					if (nl.item(nl.getLength() - 2).hasAttributes()) {
						if (nl.item(nl.getLength() - 2).getAttributes().item(0)
								.getTextContent()
								.equals("http://schema.org/People")) {

							if (nl.item(0).getParentNode().getAttributes()
									.item(0).getTextContent()
									.equals(textContent)) {
								p.setUri(UriGenerator.generate(p));
								for (int i = 0; i < nl.getLength(); i++) {
									
									if (nl.item(i).getNodeName()
											.equals("schema:name")) {
										p.setName(nl.item(i)
												.getAttributes().item(0)
												.getTextContent());
									}
									

								}

							}

						}
					}
					
				}
			}
			RDFModel.getInstance().save(p);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Location location(String textContent) {
		Location l = new Location();
		try {
			File rdf = new File("event.rdf");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = null;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(rdf);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("rdf:Description");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList nl = eElement.getChildNodes();

					if (nl.item(nl.getLength() - 2).hasAttributes()) {
						if (nl.item(nl.getLength() - 2).getAttributes().item(0)
								.getTextContent()
								.equals("http://schema.org/Place")) {

							if (nl.item(0).getParentNode().getAttributes()
									.item(0).getTextContent()
									.equals(textContent)) {
								l.setUri(UriGenerator.generate(l));
								for (int i = 0; i < nl.getLength(); i++) {
									if (nl.item(i).getNodeName()
											.equals("schema:url")) {
										URL url = new URL(nl.item(i)
												.getAttributes().item(0)
												.getTextContent());
										l.setUrl(url);
									}
									if (nl.item(i).getNodeName()
											.equals("schema:name")) {
										l.setName(nl.item(i).getTextContent());
									}
									if (nl.item(i).getNodeName()
											.equals("schema:maps")) {
										URL url = new URL(nl.item(i)
												.getAttributes().item(0)
												.getTextContent());
										l.setMap(url);
									}
									if (nl.item(i).getNodeName()
											.equals("schema:address")) {
										l.setAddress(address(nl.item(i)
												.getAttributes().item(0)
												.getTextContent()));
									}

								}

							}

						}
					}
					
				}
			}
			RDFModel.getInstance().save(l);
			return l;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Address address(String id) {
		Address a = new Address();
		try {
			File rdf = new File("event.rdf");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = null;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(rdf);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("rdf:Description");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList nl = eElement.getChildNodes();
					if (nl.item(nl.getLength() - 2).hasAttributes()) {
						if (nl.item(nl.getLength() - 2).getAttributes().item(0)
								.getTextContent()
								.equals("http://schema.org/PostalAddress")) {
							if (nl.item(0).getParentNode().getAttributes()
									.item(0).getTextContent().equals(id)) {
								
								a = new Address();
								a.setUri(UriGenerator.generate(a));
								for (int i = 0; i < nl.getLength(); i++) {
									if (nl.item(i).getNodeName()
											.equals("schema:streetAddress")) {
										a.setStreetAddress(nl.item(i)
												.getTextContent());
									}
									if (nl.item(i).getNodeName()
											.equals("schema:postalCode")) {
										a.setPostalCode(nl.item(i)
												.getTextContent());
									}
									if (nl.item(i).getNodeName()
											.equals("schema:addressRegion")) {
										a.setAddressRegion(nl.item(i)
												.getTextContent());
									}
									if (nl.item(i).getNodeName()
											.equals("schema:addressLocality")) {
										a.setAddressLocality(nl.item(i)
												.getTextContent());
									}

								}

							}

						}

					}
				}
			}RDFModel.getInstance().save(a);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object choose(String id){
			if(offer(id)!=null){
				return offer(id);
			}else if(aggregateOffer(id)!=null){
				return aggregateOffer(id);
			}
			return null;
	}
	
	public Offer offer(String id){
		Offer o = new Offer();
		try {
			File rdf = new File("event.rdf");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = null;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(rdf);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("rdf:Description");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList nl = eElement.getChildNodes();
					if (nl.item(nl.getLength() - 2).hasAttributes()) {
						if (nl.item(nl.getLength() - 2).getAttributes().item(0)
								.getTextContent()
								.equals("http://schema.org/Offer")) {
							if (nl.item(0).getParentNode().getAttributes()
									.item(0).getTextContent().equals(id)) {
								o.setUri(UriGenerator.generate(o));
								for (int i = 0; i < nl.getLength(); i++) {
									if (nl.item(i).getNodeName()
											.equals("schema:name")) {
										o.setName(nl.item(i)
												.getTextContent());
									}
									if (nl.item(i).getNodeName()
											.equals("schema:url")) {
										URL url = new URL(nl.item(i)
												.getAttributes().item(0)
												.getTextContent());
										o.setUrl(url);
									}
									

								}

							}

						}

					}
				}
			}
			RDFModel.getInstance().save(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AggregateOffer aggregateOffer(String id){
		AggregateOffer a = new AggregateOffer();
		try {
			File rdf = new File("event.rdf");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = null;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(rdf);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("rdf:Description");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList nl = eElement.getChildNodes();
					if (nl.item(nl.getLength() - 2).hasAttributes()) {
						if (nl.item(nl.getLength() - 2).getAttributes().item(0)
								.getTextContent()
								.equals("http://schema.org/AggregateOffer")) {
							if (nl.item(0).getParentNode().getAttributes()
									.item(0).getTextContent().equals(id)) {
								a.setUri(UriGenerator.generate(a));
//								a.setAvailability(availability)
								for (int i = 0; i < nl.getLength(); i++) {
									if (nl.item(i).getNodeName()
											.equals("schema:availability")) {
										a.setAvailability(nl.item(i)
												.getAttributes().item(0)
												.getTextContent());
									}
								}
							}
						}
					}
				}
			}

			RDFModel.getInstance().save(a);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
