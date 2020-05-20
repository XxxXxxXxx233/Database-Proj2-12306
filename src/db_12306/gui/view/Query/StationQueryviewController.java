package db_12306.gui.view.Query;

import java.sql.SQLException;

import db_12306.db_operation_update.DatabaseOperation;
import db_12306.gui.model.Station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class StationQueryviewController {
	@FXML
	private TableView<Station> stationTable;
	@FXML 
    private TableColumn<Station, String> nameColumn;
    @FXML
    private TableColumn<Station, String> provinceColumn;
    @FXML
    private TableColumn<Station, String> cityColumn;
    @FXML
    private TextField provinceField;
    @FXML
    private TextField cityField;
    
    private Stage dialogStage;
    private DatabaseOperation d;
    
    public StationQueryviewController() {}     
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        nameColumn.setCellValueFactory(
                cellData -> cellData.getValue().NameProperty());
        provinceColumn.setCellValueFactory(
                cellData -> cellData.getValue().ProvinceProperty());
        cityColumn.setCellValueFactory(
                cellData -> cellData.getValue().CityProperty());
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    @FXML
    private void handleProvinceStationQuery() throws SQLException {
    	String province=provinceField.getText();
    	
    	//A arraylist of Station for StationQuery
        ObservableList<Station> stationData=FXCollections.observableArrayList();
        
        String information=d.searchStationInProvince(province);
        if(information.equals(""))
        {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("");
            alert.setHeaderText(province+"省不存在或未录入"+province+"省车站信息！");
            alert.setContentText("");
            alert.showAndWait();
        }else {
        	String[] stationinf=information.split("\t");
        	int station_num=stationinf.length/3;
        	
        	for(int i=0;i<station_num;i++)
        	{
        		Station station=new Station(stationinf[i*3],stationinf[i*3+2],stationinf[i*3+1]);
        		stationData.add(station);
        	}
        	stationTable.setItems(stationData);
        }
    }
    
    @FXML
    private void handleCityStationQuery() throws SQLException {
    	String city=cityField.getText();
    	
    	//A arraylist of Station for StationQuery
        ObservableList<Station> stationData=FXCollections.observableArrayList();
        
        String information=d.searchStationInCity(city);
        if(information.equals(""))
        {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("");
            alert.setHeaderText(city+"市不存在或未录入"+city+"市车站信息！");
            alert.setContentText("");
            alert.showAndWait();
        }else {
        	String[] stationinf=information.split("\t");
        	int station_num=stationinf.length/3;
        	
        	for(int i=0;i<station_num;i++)
        	{
        		Station station=new Station(stationinf[i*3],stationinf[i*3+2],stationinf[i*3+1]);
        		stationData.add(station);
        	}
        	stationTable.setItems(stationData);
        }
    }
	
}
