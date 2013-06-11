package domain;

import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;
/* Klasa Event je tipa event nasledjuje klasu Thing i sadrzi atribute
 * startDate
 * location
 * offers
 * performer
 * */
@Namespace(Constants.SCHEMA)
@RdfType("Event")
public class Event extends Thing {
	

	@RdfProperty(Constants.SCHEMA + "startDate")
	private Date startDate;

	@RdfProperty(Constants.SCHEMA + "location")
	private Location location;

	@RdfProperty(Constants.SCHEMA + "offers")
	private Offer offer;

	@RdfProperty(Constants.SCHEMA + "offers")
	private AggregateOffer aggregateOffer;

	@RdfProperty(Constants.SCHEMA + "performer")
	private Person performer;


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void show() {
		System.err.println(toString());

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
		return "Event [name=" + getName() + ", description=" + getDescription()
				+ ", image=" + getImage() + ", startDate=" + startDate
				+ ", location=" + location + ", offer=" + offer
				+ ", aggregateOffer=" + aggregateOffer + ", performer="
				+ performer + "]";
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
