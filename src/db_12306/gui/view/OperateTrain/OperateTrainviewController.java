package db_12306.gui.view.OperateTrain;

import db_12306.gui.MainApp;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class OperateTrainviewController {
	private MainApp mainApp;
	private Stage dialogStage;
	
	public OperateTrainviewController() {}
	
	@FXML
	private void initialize() {}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
    @FXML
    private void handleInsertTrain() {
    	mainApp.showInsertTrainview(this.dialogStage);
    }
    
    @FXML
    private void handleDeleteTrain() {
    	mainApp.showDeleteTrainview(this.dialogStage);
    }
}
