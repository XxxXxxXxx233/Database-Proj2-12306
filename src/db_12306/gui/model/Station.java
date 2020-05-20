package db_12306.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Station {
	private final StringProperty Name;
	private final StringProperty Province;
	private final StringProperty City;
	
	public Station() {
		this(null,null,null);
	}

	public Station(String name, String province, String city) {
		this.Name=new SimpleStringProperty(name);
		this.Province=new SimpleStringProperty(province);
		this.City=new SimpleStringProperty(city);
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
	
	public String getProvince() {
		return Province.get();
	}

	public void setProvince(String Province) {
		this.Province.set(Province);
	}
	
	public StringProperty ProvinceProperty() {
		return Province;
	}
	
	public String getCity() {
		return City.get();
	}

	public void setCity(String City) {
		this.City.set(City);
	}
	
	public StringProperty CityProperty() {
		return City;
	}
	
}
