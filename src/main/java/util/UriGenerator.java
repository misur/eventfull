package util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
/*Klasa uriGenerator nam sluzi sa generisanje uri-ja
 * 
 * 
 * */



public class UriGenerator {
	
	public static URI generate(Object resource) throws URISyntaxException{
		String uri = Constants.EVENTFULL + 
						resource.getClass().getSimpleName() + 
						"/" + UUID.randomUUID();
		return new URI(uri);
	}

}
