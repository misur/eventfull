package domain;

import java.net.URL;



import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;
/*Klasa Location je tipa Place nasledjuje klasu Thing
 * i ima atribute :
 * url
 * address
 * map
 * 
 * */
@Namespace(Constants.SCHEMA)
@RdfType("Place")
public class Location extends Thing {

	@RdfProperty(Constants.SCHEMA + "url")
	private String url;

	@RdfProperty(Constants.SCHEMA + "address")
	private Address address;

	@RdfProperty(Constants.SCHEMA + "map")
	private String  map;



	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Location [name=" + getName() + ", url=" + url + ", address="
				+ address + ", map=" + map + "]";
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
