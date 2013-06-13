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
import java.util.Arrays;
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
/*Klasa CreateModel je klasa koja iz RDF/XML prevodi u objektno orjentisan model
 * Metoda events prima rdf file koji analizira i onda pravi objekte takodje 
 * ona i poziva ostale metoda :
 *  -private Date editDate(String textContent) koja uredjuje string i vraca
 *  -public String findIdForPerformer(String id) pronalazi performera po id 
 *  -public Person person(String textContent) pravi i vraca performera po zadatom id
 *  -public Location location(String textContent) kreira location 
 *  -public Address address(String id)  kreira addressu
 *  -public Offer offer(String id) kreira offera
 *  -public AggregateOffer aggregateOffer(String id) kreira aggregateOffer
 * */
public class CreateModel {


	public void events(File rdf) {

		 
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(rdf);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("rdf:Description");
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
									Date d = editDate(nl.item(i)
											.getTextContent());
									e.setStartDate(d);
								}
								if (nl.item(i).getNodeName()
										.equals("schema:performer")) {
									String id = findIdForPerformer(nl.item(i)
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
									// Object o =offer(nl.item(i)
									// .getAttributes().item(0)
									// .getTextContent());
									// if(o instanceof AggregateOffer ){
									// e.setAggregateOffer((AggregateOffer)o);
									// }else if(o instanceof Offer){
									// e.setOffer((Offer)o);
									// }
								}
								if (nl.item(i).getNodeName()
										.equals("schema:image")) {
									e.setImage(nl.item(i)
											.getAttributes().item(0)
											.getTextContent());
								}
							}

							RDFModel.getInstance().save(e);
//							e.show();
							RDFModel.getInstance().saveAsFile("myRDF.rdf","RDF/XML");
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Date editDate(String textContent) {
		// TODO Auto-generated method stub
		List<String> months = Arrays.asList("January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December");
		GregorianCalendar g = new GregorianCalendar();
		StringBuffer sb = new StringBuffer(textContent);
		String s = sb.substring(0, sb.indexOf(",") + 6);
		String[] arr = s.split(" ");
		ArrayList<String> pom = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null && arr[i].length() != 0 && arr[i].length() != 1) {
				pom.add(arr[i]);
			}
		}
		for (int i = 0; i < months.size(); i++) {
			if (pom.get(0).equals(months.get(i))) {
				int pomy = Integer.parseInt(pom.get(2));
				int pomi = Integer.parseInt(pom.get(1).substring(0,
						pom.get(1).length() - 1));
				g.set(pomy, i, pomi);
			}
		}
		Date d = g.getTime();
		return d;
	}

	public String findIdForPerformer(String id) {
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

					if (nl.item(0).getParentNode().getAttributes().item(0)
							.getTextContent().equals(id)) {
						for (int i = 0; i < nl.getLength(); i++) {
							if (nl.item(i).getNodeName().equals("rdf:first")) {
								return nl.item(i).getAttributes().item(0)
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
		Person p = new Person();
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
										p.setName(nl.item(i).getAttributes()
												.item(0).getTextContent());
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
										l.setUrl(nl.item(i)
												.getAttributes().item(0)
												.getTextContent());
									}
									if (nl.item(i).getNodeName()
											.equals("schema:name")) {
										l.setName(nl.item(i).getTextContent());
									}
									if (nl.item(i).getNodeName()
											.equals("schema:maps")) {
										l.setMap(nl.item(i)
												.getAttributes().item(0)
												.getTextContent());
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
			}
			RDFModel.getInstance().save(a);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object choose(String id) {
		if (offer(id) != null) {
			return offer(id);
		} else if (aggregateOffer(id) != null) {
			return aggregateOffer(id);
		}
		return null;
	}

	public Offer offer(String id) {
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
										o.setName(nl.item(i).getTextContent());
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

	public AggregateOffer aggregateOffer(String id) {
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
								// a.setAvailability(availability)
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
