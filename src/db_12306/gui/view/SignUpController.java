package db_12306.gui.view;

import java.sql.SQLException;

import db_12306.db_operation_update.DatabaseOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {
	@FXML
    private TextField accountField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField idcardNumberField;
    @FXML
    private TextField phoneNumberField;
    
    private Stage dialogStage;
    DatabaseOperation d;
    
    @FXML
    private void initialize() {}
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    
    @FXML
    private void handleOk() throws SQLException {
    	boolean registerok=false;
    	try {
	        if (isInputValid()) {
	        	String account=accountField.getText();
	        	String password=passwordField.getText();
	        	String name=nameField.getText();
	        	String idcardNumber=idcardNumberField.getText();
	        	String phoneNumber=phoneNumberField.getText();
	          
	        	//The function to regiter;
	        	d.userRegister(name, idcardNumber, phoneNumber, account, password);   
	        	registerok=true;	        
	        }
        }catch(SQLException e) {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("");
            alert.setHeaderText("���֤������ע��򲻺Ϸ�");
            alert.setContentText("");
            alert.showAndWait();
            registerok=false;
        }finally {
        	if(registerok) {
        		Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initOwner(dialogStage);
	            alert.setTitle("");
	            alert.setHeaderText("ע��ɹ���");
	            alert.setContentText("");
	            alert.showAndWait();
	            dialogStage.close();
        	}
        	
        }
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() throws SQLException {
    	if(nullcheck()){
    		if(!d.checkExistingAccount(accountField.getText())) {
    			Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("��Ч������Ϣ��");
                alert.setHeaderText("���˺��ѱ�ע�ᣬ���������룡");
                alert.setContentText("");
                alert.showAndWait();
                return false;
            }else {
            	return true;
            }
    	}else {
    		return false;
    	}
    }
    
    
    //check where the information for registration is valid
    private boolean nullcheck() {
        String errorMessage = "";
        if (accountField.getText() == null || accountField.getText().length() == 0) {
            errorMessage += "�˺Ų���Ϊ��!\n"; 
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "���벻��Ϊ��!\n"; 
        }
        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "��������Ϊ��!\n"; 
        }
        if (idcardNumberField.getText() == null || idcardNumberField.getText().length() == 0) {
            errorMessage += "���֤���벻��Ϊ��!\n"; 
        } 
        if (phoneNumberField.getText() == null || phoneNumberField.getText().length() == 0) {
            errorMessage += "�ֻ�������벻��Ϊ��!\n"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("��Ч������Ϣ��");
            alert.setHeaderText("���޸���Ч��������Ϣ��");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
