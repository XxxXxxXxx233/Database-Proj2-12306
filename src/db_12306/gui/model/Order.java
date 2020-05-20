package db_12306.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
	private final StringProperty Orderorder;
	private final StringProperty CreateTime;
	private final StringProperty Status;
	private final StringProperty fromCity;
	private final StringProperty toCity;
	private final StringProperty TicketNum;
	
	public Order() {
		this(null,null,null,null,null,null);
	}

	public Order(String Orderorder, String CreateTime, String Status,String fromCity,String toCity,String TicketNum) {
		this.Orderorder=new SimpleStringProperty(Orderorder);
		this.CreateTime=new SimpleStringProperty(CreateTime);
		this.Status=new SimpleStringProperty(Status);
		this.fromCity=new SimpleStringProperty(fromCity);
		this.toCity=new SimpleStringProperty(toCity);
		this.TicketNum=new SimpleStringProperty(TicketNum);
		
	}
	
	public String getOrderorder() {
		return Orderorder.get();
	}

	public void setOrderorder(String Orderorder) {
		this.Orderorder.set(Orderorder);
	}
	
	public StringProperty OrderorderProperty() {
		return Orderorder;
	}
	
	public String getCreateTime() {
		return CreateTime.get();
	}

	public void setCreateTime(String CreateTime) {
		this.CreateTime.set(CreateTime);
	}
	
	public StringProperty CreateTimeProperty() {
		return CreateTime;
	}
	
	public String getStatus() {
		return Status.get();
	}

	public void setStatus(String Status) {
		this.Status.set(Status);
	}
	
	public StringProperty StatusProperty() {
		return Status;
	}
	
	
	public String getfromCity() {
		return fromCity.get();
	}

	public void setfromCity(String fromCity) {
		this.fromCity.set(fromCity);
	}
	
	public StringProperty fromCityProperty() {
		return fromCity;
	}
	
	public String gettoCity() {
		return toCity.get();
	}

	public void settoCity(String toCity) {
		this.toCity.set(toCity);
	}
	
	public StringProperty toCityProperty() {
		return toCity;
	}
	
	public String getTicketNum() {
		return TicketNum.get();
	}

	public void setTicketNum(String TicketNum) {
		this.TicketNum.set(TicketNum);
	}
	
	public StringProperty TicketNumProperty() {
		return TicketNum;
	}
}
