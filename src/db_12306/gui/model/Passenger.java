package db_12306.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Passenger {
	public final StringProperty Name;
	private final StringProperty Idnumber;
	
	public Passenger() {
		this(null,null);
	}

	public Passenger(String name, String idnumber) {
		this.Name=new SimpleStringProperty(name);
		this.Idnumber=new SimpleStringProperty(idnumber);
	}
	
	public String getName() {
		return Name.get();
	}

	public void setName(String Name) {
		this.Name.set(Name);
	}
	
	public StringProperty NameProperty() {
		return Name;
	}
	
	public String getIdnumber() {
		return Idnumber.get();
	}

	public void setIdnumber(String Idnumber) {
		this.Idnumber.set(Idnumber);
	}
	
	public StringProperty IdnumberProperty() {
		return Idnumber;
	}
}
