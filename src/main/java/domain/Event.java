package domain;

import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;

@Namespace(Constants.SCHEMA)
@RdfType("Event")
public class Event extends Thing {
	@RdfProperty(Constants.SCHEMA + "name")
	private String name;
	
	@RdfProperty(Constants.SCHEMA + "description")
	private String description;

	@RdfProperty(Constants.SCHEMA + "image")
	private URL image;

	@RdfProperty(Constants.SCHEMA + "startDate")
	private GregorianCalendar startDate;

	@RdfProperty(Constants.SCHEMA + "location")
	private Location location;

	@RdfProperty(Constants.SCHEMA + "offers")
	private Offer offer;

	@RdfProperty(Constants.SCHEMA + "offers")
	private AggregateOffer aggregateOffer;
	
	@RdfProperty(Constants.SCHEMA + "performer")
	private Person performer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URL getImage() {
		return image;
	}

	public void setImage(URL image) {
		this.image = image;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}




	public GregorianCalendar getStartDate() {
		return startDate;
	}

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}

	
	public void show(){
		System.err.println(toString());
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public AggregateOffer getAggregateOffer() {
		return aggregateOffer;
	}

	public void setAggregateOffer(AggregateOffer aggregateOffer) {
		this.aggregateOffer = aggregateOffer;
	}

	public Person getPerformer() {
		return performer;
	}

	public void setPerformer(Person performer) {
		this.performer = performer;
	}

	@Override
	public String toString() {
		return "Event [name=" + name + ", description=" + description
				+ ", image=" + image + ", startDate=" + startDate
				+ ", location=" + location + ", offer=" + offer
				+ ", aggregateOffer=" + aggregateOffer + ", performer="
				+ performer + "]";
	}

	

}
