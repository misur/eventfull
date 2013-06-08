package domain;

import java.net.URL;



import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;

@Namespace(Constants.SCHEMA)
@RdfType("Place")
public class Location extends Thing {
	@RdfProperty(Constants.SCHEMA + "name")
	private String name;

	@RdfProperty(Constants.SCHEMA + "url")
	private URL url;

	@RdfProperty(Constants.SCHEMA + "address")
	private Address address;

	@RdfProperty(Constants.SCHEMA + "map")
	private URL map;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public URL getMap() {
		return map;
	}

	public void setMap(URL map) {
		this.map = map;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Location [name=" + name + ", url=" + url + ", address="
				+ address + ", map=" + map + "]";
	}

}
