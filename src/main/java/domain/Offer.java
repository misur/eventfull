package domain;

import java.net.URL;



import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;
/* Klasa Offer je tipa offer nasldjuje klasu Thing i ima 
 * atribute url 
 */
@Namespace(Constants.SCHEMA)
@RdfType("Offer")
public class Offer extends Thing{
	@RdfProperty(Constants.SCHEMA + "url")
	private URL url;
	

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}


	@Override
	public String toString() {
		return "Offer [url=" + url + ", name=" + getName() + "]";
	}
	
	

	
	
	
}
