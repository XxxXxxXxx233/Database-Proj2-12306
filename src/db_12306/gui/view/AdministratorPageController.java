package db_12306.gui.view;

import java.io.IOException;
import java.sql.SQLException;

import db_12306.gui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdministratorPageController {
	@FXML
	private Label accountLabel;

	private MainApp mainApp;
	
	public AdministratorPageController() {}
	
	@FXML
	private void initialize() {}
	
	public void changeAccountLabel(){
		accountLabel.setText(mainApp.currentaccount);
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	
    @FXML
    private void handleQuery() {
    	mainApp.getPrimaryStage().close();
    	mainApp.showQueryview();
    	mainApp.initRootLayout();
    	mainApp.showAdministratorPage();
    }
    
    @FXML
    private void handleBuyTicket() throws NumberFormatException, SQLException {
    	mainApp.getPrimaryStage().close();
    	mainApp.showBuyTicketview();
    	mainApp.initRootLayout();
    	mainApp.showAdministratorPage();	
    }
    
    @FXML
    private void handleOrderQurey() throws NumberFormatException, SQLException {
    	mainApp.getPrimaryStage().close();
    	mainApp.showOrderQueryview();
    	mainApp.initRootLayout();
    	mainApp.showAdministratorPage();
    }
    
    @FXML
    private void handleModifyInformation() throws IOException {
    	mainApp.showUserInfoModifyview();
    } 
    
    @FXML
    private void handleLogout() {
    	mainApp.showLoginview();
    }
    
    @FXML
    private void handleModifyStation() throws IOException {
    	mainApp.getPrimaryStage().close();
    	mainApp.showModifyStationview();
    	mainApp.initRootLayout();
    	mainApp.showAdministratorPage();
    }
    
    @FXML
    private void handleModifyTrain() {
    	mainApp.getPrimaryStage().close();
    	mainApp.showOperateTrainview();
    	mainApp.initRootLayout();
    	mainApp.showAdministratorPage();
    }
}
