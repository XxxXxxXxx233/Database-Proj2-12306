package db_12306.gui.view.OperateTrain;

import java.sql.SQLException;

import db_12306.db_operation_update.DatabaseOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DeleteTrainviewController {
	@FXML
	private TextField TrainNumberField;
	
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
    private void handleDeleteTrain() throws SQLException {
    	if(trainnumbernullcheck()) {
    		String trainNumber=TrainNumberField.getText();
    		d.deleteTrain(trainNumber);
    		Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(dialogStage);
            alert.setTitle("注销列车");
            alert.setHeaderText("列车注销成功");
            alert.setContentText("");
            alert.showAndWait();
    	}
    	
    }
    	
	private boolean trainnumbernullcheck() {
        String errorMessage = "";
        if (TrainNumberField.getText() == null || TrainNumberField.getText().length() == 0) {
            errorMessage += "车次不得为空!\n"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("无效输入信息！");
            alert.setHeaderText("输入信息有误，请重新填写");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
}
