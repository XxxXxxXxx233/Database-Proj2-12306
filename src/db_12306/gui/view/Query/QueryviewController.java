package db_12306.gui.view.Query;

import db_12306.gui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class QueryviewController {

	private MainApp mainApp;
	private Stage dialogStage;
	
	public QueryviewController() {}
	
	@FXML
	private void initialize() {}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
    @FXML
    private void handleStationQuery() {
    	mainApp.showStationQueryview(this.dialogStage);
    }
    
    @FXML
    private void handleTrainNumberQuery() {
    	mainApp.showTrainNumberQueryview(this.dialogStage);
    }
    
    @FXML
    private void handleTrainStopStationQuery() {
    	mainApp.showTrainStopStationQueryview(this.dialogStage);
    }
    
}
