package db_12306.gui.view.Query;

import java.sql.SQLException;

import db_12306.db_operation_update.DatabaseOperation;
import db_12306.gui.model.TrainStopStationInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TrainStopStationviewController {
	@FXML
	private TableView<TrainStopStationInformation> TrainStopStationInformationTable;
	@FXML 
    private TableColumn<TrainStopStationInformation, String> TrainNumberColumn;
    @FXML
    private TableColumn<TrainStopStationInformation, String> StationNameColumn;
    @FXML
    private TableColumn<TrainStopStationInformation, String> ArriveTimeColumn;
    @FXML 
    private TableColumn<TrainStopStationInformation, String> LeaveTimeColumn;
    @FXML
    private TableColumn<TrainStopStationInformation, String> TimeConsumeColumn;
    @FXML
    private TableColumn<TrainStopStationInformation, String> DistanceColumn;
    @FXML
    private TableColumn<TrainStopStationInformation, String> LastStationColumn;
    @FXML
    private TableColumn<TrainStopStationInformation, String> NextStationColumn;
    @FXML
    private TextField StopStationField;
    
    private Stage dialogStage;
    private DatabaseOperation d;
    
    public TrainStopStationviewController() {}     
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        TrainNumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().TrainNumberProperty());
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
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    @FXML
    private void handleTrainNumberQuery() throws SQLException{
    	String StopStationName=StopStationField.getText();
    	
    	//A arraylist of Station for StationQuery
        ObservableList<TrainStopStationInformation> TrainData=FXCollections.observableArrayList();
    	
        String information=d.searchTrainInformationInOneStation(StopStationName);
        if(information.equals(""))
        {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("");
            alert.setHeaderText(StopStationName+"站不存在或者无列车经过该站");
            alert.setContentText("");
            alert.showAndWait();
        }else {
        	String[] traininf=information.split("\t");
        	int station_num=traininf.length/8;
        	
        	for(int i=0;i<station_num;i++)
        	{
        		TrainStopStationInformation station=new TrainStopStationInformation(traininf[i*8],traininf[i*8+1],traininf[i*8+2],traininf[i*8+3],traininf[i*8+4],traininf[i*8+5],traininf[i*8+6],traininf[i*8+7]);
        		TrainData.add(station);
        	}
        	TrainStopStationInformationTable.setItems(TrainData);
        }
    }
}
