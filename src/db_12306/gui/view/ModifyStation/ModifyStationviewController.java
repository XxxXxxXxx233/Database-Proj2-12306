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
            alert.setTitle("新站点创建");
            alert.setHeaderText("新站点创建成功，新站点信息为：");
            alert.setContentText(info);
            alert.showAndWait();
        } else {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(this.dialogStage);
            alert.setTitle("新站点创建");
            alert.setHeaderText("新站点创建失败，该站点已存在");
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
            alert.setTitle("站点删除");
            alert.setHeaderText("站点删除成功");
            alert.setContentText("");
            alert.showAndWait();
        } else {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(this.dialogStage);
            alert.setTitle("站点删除");
            alert.setHeaderText("站点删除失败");
            alert.setContentText("");
            alert.showAndWait();
        }
	}
}
