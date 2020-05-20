package db_12306.gui.view.BuyTicket;


import java.sql.SQLException;

import db_12306.gui.model.Passenger;
import db_12306.gui.model.passengerInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PurchaseviewController {
	@FXML
    private TextField NameField;
	@FXML
    private TextField IdnumberField;
	@FXML
	private TableView<Passenger> PassengerInfoTable;
	@FXML
	private TableColumn<Passenger, String> NameColumn;
    @FXML
    private TableColumn<Passenger, String> IdnumberColumn;
    
    
    private Stage dialogStage;
    private BuyTicketviewController parent;
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        NameColumn.setCellValueFactory(
                cellData -> cellData.getValue().NameProperty());
        IdnumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().IdnumberProperty());

        // Listen for selection changes and show the person details when changed.
        PassengerInfoTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> select(newValue));
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setparent(BuyTicketviewController parent) {
    	this.parent=parent;
    	PassengerInfoTable.setItems(parent.getPassengerData());
    }
    
    private void select(Object newValue) {}
    
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = PassengerInfoTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	PassengerInfoTable.getItems().remove(selectedIndex);
        	parent.Passengers.remove(selectedIndex);
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
    	String name=NameField.getText();
    	String idnumber=IdnumberField.getText();
    	if(name.equals("")||idnumber.equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("");
            alert.setHeaderText("无效输入");
            alert.setContentText("姓名和身份证号码不得为空.");
            alert.showAndWait();
    	}else {
    		passengerInfo newpassenger=new passengerInfo(name, idnumber);
    		parent.Passengers.add(newpassenger);
    		Passenger newpass=new Passenger(name,idnumber);
    		parent.getPassengerData().add(newpass);
    	}
    }
    
    @FXML 
    private void justbuyit() throws SQLException {
    	parent.justbuyit();
    }
}
