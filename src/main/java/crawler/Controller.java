package crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
/*Klasa Contorller nam sluzi za kreiranje instance klase MyCrawler i startovanje tog crawlera takodje i dodavanje seeda  
 * 
 * */
public class Controller {
	 public static void main(String[] args) throws Exception {
         String crawlStorageFolder = "/data/crawl/root";
         int numberOfCrawlers = 1;
         CrawlConfig config = new CrawlConfig();
         config.setCrawlStorageFolder(crawlStorageFolder);
         PageFetcher pageFetcher = new PageFetcher(config);
         RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
         RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
         CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
         controller.addSeed("http://eventful.com/sanfrancisco_ca/events/sting-back-bass-/E0-001-054672705-7");
         controller.start(MyCrawler.class, numberOfCrawlers);    
 }
}
