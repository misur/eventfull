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
	 public void start() throws Exception{
         String crawlStorageFolder = "D:/Fakultet/Inteligentni sistemi/eventful";
         int numberOfCrawlers = 1;
         CrawlConfig config = new CrawlConfig();
//         config.setMaxPagesToFetch(5);
//         config.set
         config.setMaxOutgoingLinksToFollow(500);
         config.setCrawlStorageFolder(crawlStorageFolder);
         PageFetcher pageFetcher = new PageFetcher(config);
         RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
         RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
         CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
         controller.addSeed("http://eventful.com/sanfrancisco_ca/events/tracy-morgan-/E0-001-054691728-9");
//         controller.addSeed("http://eventful.com/sanfrancisco_ca/events/categories/festivals_parades");
//         controller.addSeed("http://eventful.com/sanfrancisco_ca/events/categories/family_fun_kids");
//         controller.addSeed("http://eventful.com/sanfrancisco_ca/events/categories/singles_social");
         controller.start(MyCrawler.class, numberOfCrawlers); 
 }
}
