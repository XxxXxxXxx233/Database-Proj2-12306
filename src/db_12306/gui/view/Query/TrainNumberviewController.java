package db_12306.gui.view.Query;

import java.sql.SQLException;

import db_12306.db_operation_update.DatabaseOperation;
import db_12306.gui.model.TrainDetailInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TrainNumberviewController {
	@FXML
	private TableView<TrainDetailInformation> TrainDetailInformationTable;
	@FXML 
    private TableColumn<TrainDetailInformation, String> StationOrderColumn;
    @FXML
    private TableColumn<TrainDetailInformation, String> StationNameColumn;
    @FXML
    private TableColumn<TrainDetailInformation, String> ArriveTimeColumn;
    @FXML 
    private TableColumn<TrainDetailInformation, String> LeaveTimeColumn;
    @FXML
    private TableColumn<TrainDetailInformation, String> TimeConsumeColumn;
    @FXML
    private TableColumn<TrainDetailInformation, String> DistanceColumn;
    @FXML
    private TableColumn<TrainDetailInformation, String> LastStationColumn;
    @FXML
    private TableColumn<TrainDetailInformation, String> NextStationColumn;
    @FXML
    private TextField TrainNumberField;
    @FXML
    private Label TrainNumberLabel;
    @FXML
    private Label TrainTypeLabel;
    @FXML
    private Label StartStationLabel;
    @FXML
    private Label DestiStationLabel;
    @FXML
    private Label TotalTimeLabel;
    @FXML
    private Label TotalLengthLabel;
    
    private Stage dialogStage;
    private DatabaseOperation d;
    
    public TrainNumberviewController() {}     
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        StationOrderColumn.setCellValueFactory(
                cellData -> cellData.getValue().StationOrderProperty());
        StationNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().StationNameProperty());
        ArriveTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().ArriveTimeProperty());
        LeaveTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().LeaveTimeProperty());
        TimeConsumeColumn.setCellValueFactory(
                cellData -> cellData.getValue().TimeConsumeProperty());
        DistanceColumn.setCellValueFactory(
                cellData -> cellData.getValue().DistanceProperty());
        LastStationColumn.setCellValueFactory(
                cellData -> cellData.getValue().LastStationProperty());
        NextStationColumn.setCellValueFactory(
                cellData -> cellData.getValue().NextStationProperty());
        
        showTrainBasicInfor("");
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    private void showTrainBasicInfor(String a) {
    	if(a.equals("")) {
    		TrainNumberLabel.setText("");
    		TrainTypeLabel.setText("");
    	    StartStationLabel.setText("");
    	    DestiStationLabel.setText("");
    	    TotalTimeLabel.setText("");
    	    TotalLengthLabel.setText("");
    	}else {
    		String[] b=a.split("\t");
    		TrainNumberLabel.setText(b[0]);
    		TrainTypeLabel.setText(b[1]);
    	    StartStationLabel.setText(b[2]);
    	    DestiStationLabel.setText(b[3]);
    	    TotalTimeLabel.setText(b[4]);
    	    TotalLengthLabel.setText(b[5]);
    	}
    }
    
    @FXML
    private void handleTrainNumberQuery() throws SQLException{
    	String TrainNumber=TrainNumberField.getText();
    	showTrainBasicInfor(d.searchTrainBasicInformation(TrainNumber));
    	
    	//A arraylist of Station for StationQuery
        ObservableList<TrainDetailInformation> TrainData=FXCollections.observableArrayList();
    	
        String information=d.searchTrainDetailInformation(TrainNumber);
        if(information.equals(""))
        {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("");
            alert.setHeaderText(TrainNumber+"次列车不存在或未被录入！");
            alert.setContentText("");
            alert.showAndWait();
        }else {
        	String[] traininf=information.split("\t");
        	int station_num=traininf.length/8;
        	
        	for(int i=0;i<station_num;i++)
        	{
        		TrainDetailInformation station=new TrainDetailInformation(traininf[i*8],traininf[i*8+1],traininf[i*8+2],traininf[i*8+3],traininf[i*8+4],traininf[i*8+5],traininf[i*8+6],traininf[i*8+7]);
        		TrainData.add(station);
        	}
        	TrainDetailInformationTable.setItems(TrainData);
        }
    }
}
