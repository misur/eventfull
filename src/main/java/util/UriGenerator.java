package util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;




public class UriGenerator {
	
	public static URI generate(Object resource) throws URISyntaxException{
		String uri = Constants.SCHEMA + 
						resource.getClass().getSimpleName() + 
						"/" + UUID.randomUUID();
		return new URI(uri);
	}

}
