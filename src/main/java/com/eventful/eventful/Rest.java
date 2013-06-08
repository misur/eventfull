package com.eventful.eventful;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import util.Utility;



public class Rest {
	
	private static final String RDFDISTILLER_URL = "http://rdf.greggkellogg.net/distiller";
	private static final String JAVA_RDFA_EXTRACTOR_URL = "http://rdf-in-html.appspot.com/translate/";
	
	public static InputStream distillRDF(String urlString, String inputFormat, String outputFormat) throws Exception {
	    
		if (inputFormat == null || inputFormat.equals("") )
			inputFormat = "rdfagraph";
		if ( outputFormat == null || outputFormat.equals("") )
			outputFormat = "rdfxml";
		
		//building url of the following form:
		//http://rdf.greggkellogg.net/distiller?format=rdfxml&rdfagraph=&uri=http://www.example.com/
		StringBuilder sb = new StringBuilder();
		sb.append(RDFDISTILLER_URL);
		sb.append("?format=").append( outputFormat + "&" + inputFormat );
	    sb.append("&uri=").append( Utility.encode(urlString) );
	    
	    URL requestURL = new URL( sb.toString() );
	    HttpURLConnection conn = (HttpURLConnection)requestURL.openConnection();
        conn.setRequestMethod("GET");
        
        return conn.getInputStream();
        
	}
	
	public static InputStream extractRDFa(String urlString, String format) throws Exception {
	    
		if (format == null || (!format.equalsIgnoreCase("html") & !format.equalsIgnoreCase("xhtml") ) )
			format = "HTML";
		
		//building url of the following form:
		//http://rdf-in-html.appspot.com/translate/?uri=http://www.example.com/&parser=HTML
		StringBuilder sb = new StringBuilder();
		sb.append(JAVA_RDFA_EXTRACTOR_URL);
		sb.append("?uri=").append( Utility.encode(urlString) );
	    sb.append("&parser=").append( format.toUpperCase() );
	    	    
	    URL requestURL = new URL( sb.toString() );
	    HttpURLConnection conn = (HttpURLConnection)requestURL.openConnection();
        conn.setRequestMethod("GET");
        
        return conn.getInputStream();
        
	  }

}
