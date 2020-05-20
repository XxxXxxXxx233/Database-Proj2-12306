package db_12306.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ticket {
	private final StringProperty TicketOrder;
	private final StringProperty TrainNumber;
	private final StringProperty TrainType;
	private final StringProperty fromStation;
	private final StringProperty DepartTime;
	private final StringProperty toStation;
	private final StringProperty ArriveTime;
	private final StringProperty SeatType;
	private final StringProperty TicketRestNum;
	private final StringProperty Price;
	
	
	
	public Ticket() {
		this(null,null,null,null,null,null,null,null,null,null);
	}

	public Ticket(String TicketOrder, String TrainNumber,String TrainType,String fromStation,String DepartTime,String toStation,String ArriveTime,String SeatType,String TicketRestNum,String Price) {
		this.TicketOrder=new SimpleStringProperty(TicketOrder);
		this.TrainNumber=new SimpleStringProperty(TrainNumber);
		this.TrainType=new SimpleStringProperty(TrainType);
		this.fromStation=new SimpleStringProperty(fromStation);
		this.DepartTime=new SimpleStringProperty(DepartTime);
		this.toStation=new SimpleStringProperty(toStation);
		this.ArriveTime=new SimpleStringProperty(ArriveTime);
		this.SeatType=new SimpleStringProperty(SeatType);
		this.TicketRestNum=new SimpleStringProperty(TicketRestNum);
		this.Price=new SimpleStringProperty(Price);
	}
	
	public String getTicketOrder() {
		return TicketOrder.get();
	}

	public void setTicketOrder(String TicketOrder) {
		this.TicketOrder.set(TicketOrder);
	}
	
	public StringProperty TicketOrderProperty() {
		return TicketOrder;
	}
	
	public String getTrainNumber() {
		return TrainNumber.get();
	}

	public void setTrainNumber(String TrainNumber) {
		this.TrainNumber.set(TrainNumber);
	}
	
	public StringProperty TrainNumberProperty() {
		return TrainNumber;
	}
	
	public String getTrainType() {
		return TrainType.get();
	}

	public void setTrainType(String TrainType) {
		this.TrainType.set(TrainType);
	}
	
	public StringProperty TrainTypeProperty() {
		return TrainType;
	}
	
	public String getfromStation() {
		return fromStation.get();
	}

	public void setfromStation(String fromStation) {
		this.fromStation.set(fromStation);
	}
	
	public StringProperty fromStationProperty() {
		return fromStation;
	}
	
	public String getDepartTime() {
		return DepartTime.get();
	}

	public void setDepartTime(String DepartTime) {
		this.DepartTime.set(DepartTime);
	}
	
	public StringProperty DepartTimeProperty() {
		return DepartTime;
	}
	
	public String gettoStation() {
		return toStation.get();
	}

	public void settoStation(String toStation) {
		this.toStation.set(toStation);
	}
	
	public StringProperty toStationProperty() {
		return toStation;
	}
	
	public String getArriveTime() {
		return ArriveTime.get();
	}

	public void setArriveTime(String ArriveTime) {
		this.ArriveTime.set(ArriveTime);
	}
	
	public StringProperty ArriveTimeProperty() {
		return ArriveTime;
	}
	
	public String getSeatType() {
		return SeatType.get();
	}

	public void setSeatType(String SeatType) {
		this.SeatType.set(SeatType);
	}
	
	public StringProperty SeatTypeProperty() {
		return SeatType;
	}
	
	public String getTicketRestNum() {
		return TicketRestNum.get();
	}

	public void setTicketRestNum(String TicketRestNum) {
		this.TicketRestNum.set(TicketRestNum);
	}
	
	public StringProperty TicketRestNumProperty() {
		return TicketRestNum;
	}
	
	public String getPrice() {
		return Price.get();
	}

	public void setPrice(String Price) {
		this.Price.set(Price);
	}
	
	public StringProperty PriceProperty() {
		return Price;
	}
}
