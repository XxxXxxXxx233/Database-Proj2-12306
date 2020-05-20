package db_12306.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class OrderDetailInfo {
	private final StringProperty Order;
	private final StringProperty TrainNumber;
	private final StringProperty TrainType;
	private final StringProperty fromStation;
	private final StringProperty departTime;
	private final StringProperty toStation;
	private final StringProperty arriveTime;
	private final StringProperty SeatType;
	private final StringProperty Price;
	private final StringProperty PassengerName;
	private final StringProperty CarriageNum;
	private final StringProperty SeatNum;
	private final StringProperty Status;

	public OrderDetailInfo() {
		this(null,null,null,null,null,null,null,null,null,null,null,null,null);
	}

	public OrderDetailInfo(String Order,String TrainNumber,String TrainType,String fromStation,String departTime,String toStation,String arriveTime,String SeatType,String Price,String PassengerName,String CarriageNum,String SeatNum,String Status) {	
	this.Order=new SimpleStringProperty(Order);
	this.TrainNumber=new SimpleStringProperty(TrainNumber);
	this.TrainType=new SimpleStringProperty(TrainType);
	this.fromStation=new SimpleStringProperty(fromStation);
	this.departTime=new SimpleStringProperty(departTime);
	this.toStation=new SimpleStringProperty(toStation);
	this.arriveTime=new SimpleStringProperty(arriveTime);
	this.SeatType=new SimpleStringProperty(SeatType);
	this.Price=new SimpleStringProperty(Price);
	this.PassengerName=new SimpleStringProperty(PassengerName);
	this.CarriageNum=new SimpleStringProperty(CarriageNum);
	this.SeatNum=new SimpleStringProperty(SeatNum);
	this.Status=new SimpleStringProperty(Status);
}
public String getOrder() {
		return Order.get();
	}

	public void setOrder(String Order) {
		this.Order.set(Order);
	}
	
	public StringProperty OrderProperty() {
		return Order;
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
public String getdepartTime() {
		return departTime.get();
	}

	public void setdepartTime(String departTime) {
		this.departTime.set(departTime);
	}
	
	public StringProperty departTimeProperty() {
		return departTime;
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
public String getarriveTime() {
		return arriveTime.get();
	}

	public void setarriveTime(String arriveTime) {
		this.arriveTime.set(arriveTime);
	}
	
	public StringProperty arriveTimeProperty() {
		return arriveTime;
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
public String getPrice() {
		return Price.get();
	}

	public void setPrice(String Price) {
		this.Price.set(Price);
	}
	
	public StringProperty PriceProperty() {
		return Price;
	}
public String getPassengerName() {
		return PassengerName.get();
	}

	public void setPassengerName(String PassengerName) {
		this.PassengerName.set(PassengerName);
	}
	
	public StringProperty PassengerNameProperty() {
		return PassengerName;
	}
public String getCarriageNum() {
		return CarriageNum.get();
	}

	public void setCarriageNum(String CarriageNum) {
		this.CarriageNum.set(CarriageNum);
	}
	
	public StringProperty CarriageNumProperty() {
		return CarriageNum;
	}
public String getSeatNum() {
		return SeatNum.get();
	}

	public void setSeatNum(String SeatNum) {
		this.SeatNum.set(SeatNum);
	}
	
	public StringProperty SeatNumProperty() {
		return SeatNum;
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
}

