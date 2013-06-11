package domain;

import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;
/*Klasa Aggregaoffer je tipa aggregatoffer nasledjuje klasu Thing i sadrizi atribut
 * -availability
 * 
 * 
 * 
 * */
@Namespace(Constants.SCHEMA)
@RdfType("AggregateOffer")
public class AggregateOffer extends Thing {
	@RdfProperty(Constants.SCHEMA + "availability")
	private String availability;

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return "AggregateOffer [availability=" + availability + "]";
	}
	
	
	

}
