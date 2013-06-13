package crawler;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
/* MyCrawler klasa sadrzi atribut FILTERS koji filtrira sve url-ove
 * takodje i podesava koje url-ove bi morao da posjeti i te url-ove cuva u fajl 
 * takodje sadrzi i metodu edit koja filtrira sve url */
public class MyCrawler extends WebCrawler {
	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return FILTERS.matcher(href).matches()
				&& href.startsWith("http://eventful.com/sanfrancisco_ca/events");
	}

	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			List<WebURL> links = htmlParseData.getOutgoingUrls();
//			List<WebURL> pom =new  ArrayList<WebURL>(links);
			List<WebURL> pom = editURL(links);
			try {
				BufferedWriter bf = new BufferedWriter(new  FileWriter("links.txt"));
				for (int i = 0; i < pom.size(); i++) {
					bf.write(pom.get(i).toString());
					bf.newLine();
				}
				bf.flush();
				bf.close();
				
			} catch (Exception e) {
			}
			for (int i = 0; i < pom.size(); i++) {
				System.out.println(i+" - "+pom.get(i).toString());
			}
		}
		
	}

	private List<WebURL> editURL(List<WebURL> list) {
		// TODO Auto-generated method stub
		List<WebURL> pom = new ArrayList<WebURL>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).toString().startsWith("http://eventful.com/sanfrancisco_ca/events")){
				pom.add(list.get(i));
				
			}
		}
		return pom;
	}
	

}
