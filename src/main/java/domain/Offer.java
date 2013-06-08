package domain;

import java.net.URL;



import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;

@Namespace(Constants.SCHEMA)
@RdfType("Offer")
public class Offer extends Thing{
	@RdfProperty(Constants.SCHEMA + "url")
	private URL url;
	
	@RdfProperty(Constants.SCHEMA + "name")
	private String name;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Offer [url=" + url + ", name=" + name + "]";
	}
	
	

	
	
	
}
