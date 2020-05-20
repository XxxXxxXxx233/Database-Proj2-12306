package db_12306.gui.view.BuyTicket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import db_12306.db_operation_update.DatabaseOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TravelRecommendviewController {
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;
	@FXML
	private Label label4;
	@FXML
	private Label label5;
	@FXML
	private Label label6;
	@FXML
	private Label label7;
	@FXML
	private Label label1_1;
	@FXML
	private Label label1_2;
	@FXML
	private Label label1_3;
	@FXML
	private Label label2_1;
	@FXML
	private Label label2_2;
	@FXML
	private Label label2_3;
	@FXML
	private Label label3_1;
	@FXML
	private Label label3_2;
	@FXML
	private Label label3_3;

	private Stage dialogStage;
    private DatabaseOperation d;
    private BuyTicketviewController b;
    private String routine;
    private int sign;//1 三个城市，2 四个城市
    private ArrayList<String> paths=new ArrayList<String>();
    
    @FXML
    private void initialize() {
    	label1_2.setText("-->");
    	label2_2.setText("-->");
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    public void setBuyTicket(BuyTicketviewController b) {
        this.b = b;
    }
    
    public void setroutine(String routine) {
        this.routine = routine;
    }
    
    public void setLabel() {
    	String[] path=routine.split("\t");
    	int pathnum=path.length;
    	for(int i=0;i<pathnum;i++)
    		paths.add(path[i]);
    	if(pathnum==3) {
    		sign=1;
    		label1.setText("");
    		label7.setText("");
    		label3.setText("-->");
    		label5.setText("-->");
    		label2.setText(path[0]);
    		label4.setText(path[1]);
    		label6.setText(path[2]);
    		label3_1.setText("");
    		label3_2.setText("");
    		label3_3.setText("");
    		label1_1.setText(path[0]);
    		label1_3.setText(path[1]);
    		label2_1.setText(path[1]);
    		label2_3.setText(path[2]);
    	}else {
    		sign=2;
    		label2.setText("-->");
    		label4.setText("-->");
    		label6.setText("-->");
    		label1.setText(path[0]);
    		label3.setText(path[1]);
    		label5.setText(path[2]);
    		label7.setText(path[3]);
    		label1_1.setText(path[0]);
    		label1_3.setText(path[1]);
    		label2_1.setText(path[1]);
    		label2_3.setText(path[2]);
    		label3_1.setText(path[2]);
    		label3_2.setText("-->");
    		label3_3.setText(path[3]);
    	}
    }
    
    @FXML
    private void handlePath1() throws SQLException, IOException {
    	b.fromCityField.setText(paths.get(0));
    	b.toCityField.setText(paths.get(1));
    	b.handleCityTicketQuery();
    }
    
    @FXML
    private void handlePath2() throws SQLException, IOException {
    	b.fromCityField.setText(paths.get(1));
    	b.toCityField.setText(paths.get(2));
    	b.handleCityTicketQuery();
    }
    
    @FXML
    private void handlePath3() throws SQLException, IOException {
    	if(sign==2) {
    		b.fromCityField.setText(paths.get(2));
        	b.toCityField.setText(paths.get(3));
        	b.handleCityTicketQuery();
    	}   	
    }
}
