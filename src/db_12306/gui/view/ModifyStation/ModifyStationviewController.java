package db_12306.gui.view.ModifyStation;

import java.sql.SQLException;

import db_12306.db_operation_update.DatabaseOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ModifyStationviewController {
	@FXML
	private TextField NewStationNameField;
	@FXML
	private TextField NewStationCityField;
	@FXML
	private TextField DeleteStationNameField;
	
	private Stage dialogStage;
	private DatabaseOperation d;
	
	@FXML
    private void initialize() {}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
	
	@FXML
	private void handleAddNewStation() throws SQLException {
		String stationname=NewStationNameField.getText();
		String cityname=NewStationCityField.getText();
		if(d.checkExistingStation(stationname, cityname)) {
            String info=d.createNewStation(stationname, cityname);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(this.dialogStage);
            alert.setTitle("��վ�㴴��");
            alert.setHeaderText("��վ�㴴���ɹ�����վ����ϢΪ��");
            alert.setContentText(info);
            alert.showAndWait();
        } else {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(this.dialogStage);
            alert.setTitle("��վ�㴴��");
            alert.setHeaderText("��վ�㴴��ʧ�ܣ���վ���Ѵ���");
            alert.setContentText("");
            alert.showAndWait();
        }
	}
	
	@FXML
	private void handleDeleteStation() throws SQLException {
		String stationname=DeleteStationNameField.getText();
		d.deleteStation(stationname);
		if(!d.isStationAlive(stationname)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(this.dialogStage);
            alert.setTitle("վ��ɾ��");
            alert.setHeaderText("վ��ɾ���ɹ�");
            alert.setContentText("");
            alert.showAndWait();
        } else {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(this.dialogStage);
            alert.setTitle("վ��ɾ��");
            alert.setHeaderText("վ��ɾ��ʧ��");
            alert.setContentText("");
            alert.showAndWait();
        }
	}
}
