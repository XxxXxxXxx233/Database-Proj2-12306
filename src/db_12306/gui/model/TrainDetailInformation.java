package db_12306.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TrainDetailInformation {
	private final StringProperty StationOrder;
	private final StringProperty StationName;
	private final StringProperty ArriveTime;
	private final StringProperty LeaveTime;
	private final StringProperty TimeConsume;
	private final StringProperty Distance;
	private final StringProperty LastStation;
	private final StringProperty NextStation;
	
	public TrainDetailInformation() {
		this(null,null,null,null,null,null,null,null);
	}

	public TrainDetailInformation(String StationOrder, String StationName, String ArriveTime,String LeaveTime,String TimeConsume,String Distance,String LastStation,String NextStation) {
		this.StationOrder=new SimpleStringProperty(StationOrder);
		this.StationName=new SimpleStringProperty(StationName);
		this.ArriveTime=new SimpleStringProperty(ArriveTime);
		this.LeaveTime=new SimpleStringProperty(LeaveTime);
		this.TimeConsume=new SimpleStringProperty(TimeConsume);
		this.Distance=new SimpleStringProperty(Distance);
		this.LastStation=new SimpleStringProperty(LastStation);
		this.NextStation=new SimpleStringProperty(NextStation);
	}
	
	public String getStationOrder() {
		return StationOrder.get();
	}

	public void setStationOrder(String StationOrder) {
		this.StationOrder.set(StationOrder);
	}
	
	public StringProperty StationOrderProperty() {
		return StationOrder;
	}
	
	public String getStationName() {
		return StationName.get();
	}

	public void setStationName(String StationName) {
		this.StationName.set(StationName);
	}
	
	public StringProperty StationNameProperty() {
		return StationName;
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
	
	public String getLeaveTime() {
		return LeaveTime.get();
	}

	public void setLeaveTime(String LeaveTime) {
		this.LeaveTime.set(LeaveTime);
	}
	
	public StringProperty LeaveTimeProperty() {
		return LeaveTime;
	}
	
	public String getTimeConsume() {
		return TimeConsume.get();
	}

	public void setTimeConsume(String TimeConsume) {
		this.TimeConsume.set(TimeConsume);
	}
	
	public StringProperty TimeConsumeProperty() {
		return TimeConsume;
	}
	
	public String getDistance() {
		return Distance.get();
	}

	public void setDistance(String Distance) {
		this.Distance.set(Distance);
	}
	
	public StringProperty DistanceProperty() {
		return Distance;
	}
	
	public String getLastStation() {
		return LastStation.get();
	}

	public void setLastStation(String LastStation) {
		this.LastStation.set(LastStation);
	}
	
	public StringProperty LastStationProperty() {
		return LastStation;
	}
	
	public String getNextStation() {
		return NextStation.get();
	}

	public void setNextStation(String NextStation) {
		this.NextStation.set(NextStation);
	}
	
	public StringProperty NextStationProperty() {
		return NextStation;
	}
	
	
}
