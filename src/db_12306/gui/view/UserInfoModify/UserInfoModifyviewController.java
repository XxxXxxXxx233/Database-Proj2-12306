package db_12306.gui.view.UserInfoModify;

import java.sql.SQLException;

import db_12306.db_operation_update.DatabaseOperation;
import db_12306.gui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class UserInfoModifyviewController {
	@FXML
	private TextField passwordCheckField;
	@FXML
	private Label passwordCheckResultLabel;
	@FXML
    private Label accountLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneNumberLabel;
	@FXML
    private TextField accountField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneNumberField;
    
    private MainApp mainApp;
    
    private DatabaseOperation d;
    private boolean pass=false;
    private int wrongtime=0;
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    } 
    
    @FXML
    private void initialize() {}
    
    @FXML
    private void handlepasswordCheck() throws SQLException {
    	passwordCheckResultLabel.setText("检测中");
    	String password=passwordCheckField.getText();
    	if(password.equals(d.getPasswordByAccount(mainApp.currentaccount))) {
    		this.pass=true;
    		passwordCheckResultLabel.setText("密码正确！");
    		setLabel();
    	}else {
    		passwordCheckResultLabel.setText("密码错误！");
    		wrongtime++;
    		if(wrongtime==4)
    			passwordCheckResultLabel.setText("密码错误！再次输错密码您将强制退出程序！");
    		if(wrongtime>4) {
    			mainApp.showLoginview();
    		}
    	}
    }
    
    private void setLabel() throws NumberFormatException, SQLException {
    	String info=d.getUserInformation(Integer.parseInt(d.getUserID(mainApp.currentaccount)));
    	String[] userinfo=info.split("\t");
    	accountLabel.setText(userinfo[0]);
    	passwordLabel.setText(userinfo[1]);
    	nameLabel.setText(userinfo[2]);
    	phoneNumberLabel.setText(userinfo[3]);
    }
    
    @FXML
    private void handleModifyAccount() throws SQLException {
    	String newaccount=accountField.getText();
    	accountField.setText("");
    	if(newaccount.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("");
            alert.setHeaderText("账号不得为空！");
            alert.setContentText("");
            alert.showAndWait();
    	}else {
    		if(!d.checkExistingAccount(newaccount)) {
    			Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("");
                alert.setHeaderText("该账号已被注册！");
                alert.setContentText("");
                alert.showAndWait();
    		}else {
    			d.updateUserInfo(Integer.parseInt(d.getUserID(mainApp.currentaccount)), "account", newaccount);
    			mainApp.currentaccount=newaccount;
    			Alert alert = new Alert(AlertType.INFORMATION);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("");
                alert.setHeaderText("修改成功！");
                alert.setContentText("");
                alert.showAndWait();
                setLabel();
    		}
    	}
    }
    
    @FXML
    private void handleModifypassword() throws SQLException {
    	String newpassword=passwordField.getText();
    	passwordField.setText("");
    	if(newpassword.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("");
            alert.setHeaderText("密码不得为空！");
            alert.setContentText("");
            alert.showAndWait();
    	}else {
    		d.updateUserInfo(Integer.parseInt(d.getUserID(mainApp.currentaccount)), "password", newpassword);
    		Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("");
            alert.setHeaderText("修改成功！");
            alert.setContentText("");
            alert.showAndWait();
            setLabel();
    	}
    }
    
    @FXML
    private void handleModifyName() throws SQLException {
    	String newname=nameField.getText();
    	nameField.setText("");
    	if(newname.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("");
            alert.setHeaderText("用户名不得为空！");
            alert.setContentText("");
            alert.showAndWait();
    	}else {
    		d.updateUserInfo(Integer.parseInt(d.getUserID(mainApp.currentaccount)), "user_name", newname);
    		Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("");
            alert.setHeaderText("修改成功！");
            alert.setContentText("");
            alert.showAndWait();
            setLabel();
            
    	}
    }
    
    @FXML
    private void handleModifyPhoneNumber() throws SQLException {
    	String newphonenum=phoneNumberField.getText();
    	phoneNumberField.setText("");
    	if(newphonenum.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("");
            alert.setHeaderText("电话号码不得为空！");
            alert.setContentText("");
            alert.showAndWait();
    	}else {
    		d.updateUserInfo(Integer.parseInt(d.getUserID(mainApp.currentaccount)), "phone_number", newphonenum);
    		Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("");
            alert.setHeaderText("修改成功！");
            alert.setContentText("");
            alert.showAndWait();
            setLabel();
    	}
    }
    
    @FXML
    private void goback() throws SQLException {
    	int userid=Integer.parseInt(d.getUserID(mainApp.currentaccount));
    	boolean ifadminis=d.isAdministrator(userid);
    	if(ifadminis) {
    		mainApp.showAdministratorPage();	
    	}else {
    		mainApp.showNormalUserPage();
    	}
    }
}
