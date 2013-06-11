package domain;


import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;
/*Klasa Person je tipa person  nasledjuje Thing
 * 
 * 
 * */
@Namespace(Constants.SCHEMA)
@RdfType("Person")
public class Person extends Thing{
	

	@Override
	public String toString() {
		return "Person [name=" + getName() + "]";
	}
	

}
