package domain;


import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;
/*Klasa Addressa je tipa postalAddressa nasledjuje klasu Thing
 * sadrzi atribute :
 * -streetAddress
 * -addressLocality
 * -addressRegion
 * -postalCode
 * */
@Namespace(Constants.SCHEMA)
@RdfType("PostalAddress")
public class Address extends Thing{
	
	@RdfProperty(Constants.SCHEMA+  "streetAddress")
	private String streetAddress;
	@RdfProperty(Constants.SCHEMA+  "addressLocality")
	private String addressLocality;
	@RdfProperty(Constants.SCHEMA+  "addressRegion")
	private String addressRegion;
	@RdfProperty(Constants.SCHEMA+  "postalCode")
	private String postalCode;
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getAddressLocality() {
		return addressLocality;
	}
	public void setAddressLocality(String addressLocality) {
		this.addressLocality = addressLocality;
	}
	public String getAddressRegion() {
		return addressRegion;
	}
	public void setAddressRegion(String addressRegion) {
		this.addressRegion = addressRegion;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	@Override
	public String toString() {
		return "Address [streetAddress=" + streetAddress + ", addressLocality="
				+ addressLocality + ", addressRegion=" + addressRegion
				+ ", postalCode=" + postalCode + "]";
	}
	
	
	

}
