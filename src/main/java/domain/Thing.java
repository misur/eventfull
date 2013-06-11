package domain;

import java.net.URI;
import java.net.URL;

import thewebsemantic.Id;
import thewebsemantic.RdfProperty;
import util.Constants;

/* Klasa Thing je osnovna klasa koju nasledjuj sve ostale klase i ona sadrzi sve bitne atribute
 * uri
 * name 
 * description
 * image
 * */
public class Thing {
	@RdfProperty(Constants.SCHEMA + "name")
	private String name;

	@RdfProperty(Constants.SCHEMA + "description")
	private String description;

	@RdfProperty(Constants.SCHEMA + "image")
	private String image;

	@Id
	private URI uri;
	
	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
	
}
