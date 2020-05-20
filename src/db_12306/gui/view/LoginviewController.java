package db_12306.gui.view;

import java.io.IOException;
import java.sql.SQLException;

import db_12306.db_operation_update.DatabaseOperation;
import db_12306.gui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginviewController {
	@FXML
	private TextField accountField;
	@FXML
	private TextField passwordField;
	
	private MainApp mainApp;
	
	public LoginviewController() {
	}
	
	DatabaseOperation d;
		
    @FXML
    private void initialize() {}
	  
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }  
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    @FXML
    private void handleSignUp() throws IOException {
    	mainApp.getPrimaryStage().close();
    	mainApp.showSignUpview();
    	mainApp.initRootLayout();
    	mainApp.showLoginview();
    }
    
    @FXML
    private void handleLogin() throws SQLException {
    	String account=accountField.getText();
    	String password=passwordField.getText();
    	
    	//check the account and function
    	if(d.checkExistingAccount(account)) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("");
            alert.setHeaderText("¥À’À∫≈≤ª¥Ê‘⁄£°");
            alert.setContentText("");
            alert.showAndWait();     
        }else if(!password.equals(d.getPasswordByAccount(account))){
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("");
            alert.setHeaderText("√‹¬Î¥ÌŒÛ£°");
            alert.setContentText("");
            alert.showAndWait();
        }else {
        	mainApp.currentaccount=account;
        	int userid=Integer.parseInt(d.getUserID(account));
            boolean ifadminis=d.isAdministrator(userid);
        	if(ifadminis) {
        		mainApp.showAdministratorPage();	
        	}else {
        		mainApp.showNormalUserPage();
        	}
        }
    } 
	
}
