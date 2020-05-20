package db_12306.gui.view.OperateTrain;

import java.sql.SQLException;
import java.util.ArrayList;

import db_12306.db_operation_update.DatabaseOperation;
import db_12306.gui.model.NewTrainStationInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class InsertTrainviewController {
    @FXML
    private TextField TrainNumberField;
    @FXML
    private TextField TrainTypeField;
    @FXML
    private TextField StartStationField;
    @FXML
    private TextField DestiStationField;
    @FXML
    private TextField TotalTimeField;
    @FXML
    private TextField TotalLengthField;
    
	@FXML 
    private TextField StationOrderField;
    @FXML
    private TextField StationNameField;
    @FXML
    private TextField ArriveTimeField;
    @FXML 
    private TextField LeaveTimeField;
    @FXML
    private TextField TimeConsumeField;
    @FXML
    private TextField DistanceField;
    @FXML
    private TextField LastStationField;
    @FXML
    private TextField NextStationField;
    
    @FXML
	private TableView<NewTrainStationInfo> NewTrainStationInfoTable;
	@FXML 
    private TableColumn<NewTrainStationInfo, String> StationOrderColumn;
    @FXML
    private TableColumn<NewTrainStationInfo, String> StationNameColumn;
    @FXML
    private TableColumn<NewTrainStationInfo, String> ArriveTimeColumn;
    @FXML 
    private TableColumn<NewTrainStationInfo, String> LeaveTimeColumn;
    @FXML
    private TableColumn<NewTrainStationInfo, String> TimeConsumeColumn;
    @FXML
    private TableColumn<NewTrainStationInfo, String> DistanceColumn;
    @FXML
    private TableColumn<NewTrainStationInfo, String> LastStationColumn;
    @FXML
    private TableColumn<NewTrainStationInfo, String> NextStationColumn;
    
    private Stage dialogStage;
	private DatabaseOperation d;
	private int StationNum=0;
//	ArrayList<passengerInfo> Passengers=new ArrayList<passengerInfo>();
	private ObservableList<NewTrainStationInfo> trainstationData = FXCollections.observableArrayList();
	
	public InsertTrainviewController() {} 
	
	@FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        StationOrderColumn.setCellValueFactory(
                cellData -> cellData.getValue().StationOrderProperty());
        StationNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().StationNameProperty());
        ArriveTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().ArriveTimeProperty());
        LeaveTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().LeaveTimeProperty());
        TimeConsumeColumn.setCellValueFactory(
                cellData -> cellData.getValue().TimeConsumeProperty());
        DistanceColumn.setCellValueFactory(
                cellData -> cellData.getValue().DistanceProperty());
        LastStationColumn.setCellValueFactory(
                cellData -> cellData.getValue().LastStationProperty());
        NextStationColumn.setCellValueFactory(
                cellData -> cellData.getValue().NextStationProperty());
        
        //spy
        NewTrainStationInfoTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
					try {
						detectSelection(newValue);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});  
        
        NewTrainStationInfoTable.setItems(trainstationData);
    }
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    private void detectSelection(NewTrainStationInfo a) throws SQLException {
    }
    
    @FXML
    private void handleAddStation() {
    	if(stationnullcheck()) {
    		String StationOrder=StationOrderField.getText();
    		String StationName=StationNameField.getText();
    		String ArriveTime=ArriveTimeField.getText();
    		String LeaveTime=LeaveTimeField.getText();
    		String TimeConsume=TimeConsumeField.getText();
    		String Distance=DistanceField.getText();
    		String LastStation=LastStationField.getText();
    		String NextStation=NextStationField.getText();
    		NewTrainStationInfo newstation=new NewTrainStationInfo(StationOrder, StationName, ArriveTime,LeaveTime,TimeConsume,Distance,LastStation,NextStation);
    		StationNum++;
    		trainstationData.add(newstation);
    		StationOrderField.setText("");
    		StationNameField.setText("");
    		ArriveTimeField.setText("");
    		LeaveTimeField.setText("");
    		TimeConsumeField.setText("");
    		DistanceField.setText("");
    		LastStationField.setText("");
    		NextStationField.setText("");	
    	}
    }
    
    @FXML
    private void handleDeleteStation() {
    	int selectedIndex = NewTrainStationInfoTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	NewTrainStationInfoTable.getItems().remove(selectedIndex);
        	StationNum--;
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.dialogStage);
            alert.setTitle("");
            alert.setHeaderText("վ��ɾ��ʧ�ܣ�");
            alert.setContentText("���ڱ���ѡ��һ���ٵ��ɾ��");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void  handleCreateTrain() throws SQLException {
    	if(trainnullcheck()) {
    		String[] basicInfo= {TrainNumberField.getText(),TrainTypeField.getText(),StartStationField.getText(),DestiStationField.getText(),TotalTimeField.getText(),TotalLengthField.getText()};
    		if(d.checkExistingTrain(basicInfo[0])) {
    			StringBuilder traininfo=new StringBuilder("�½��ɹ�");
    			
    			traininfo.append(d.insertTrainBasicInfo(basicInfo));
    			String[][] detailinfo=new String[StationNum][8];
    			for(int i=0;i<StationNum;i++) {
    				NewTrainStationInfo a=NewTrainStationInfoTable.getItems().get(i);
    				detailinfo[i][0]=a.getStationOrder();
    				detailinfo[i][1]=a.getStationName();
    				detailinfo[i][2]=a.getArriveTime();
    				detailinfo[i][3]=a.getLeaveTime();
    				detailinfo[i][4]=a.getTimeConsume();
    				detailinfo[i][5]=a.getDistance();
    				detailinfo[i][6]=a.getLastStation();
    				detailinfo[i][7]=a.getNextStation();
    			}
    			d.insertTrainDetailInfo(basicInfo[0], detailinfo);
    			Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initOwner(dialogStage);
	            alert.setTitle("�½��г�");
	            alert.setHeaderText("�г��½��ɹ����г���ϸ��Ϣ����");
	            alert.setContentText(traininfo.toString());
	            alert.showAndWait();			
    		}else {
    			Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initOwner(dialogStage);
	            alert.setTitle("�½��г�");
	            alert.setHeaderText("�г��½�ʧ�ܣ��ó����Ѵ���");
	            alert.setContentText("");
	            alert.showAndWait();
    		}
    	}
    	
    }
    
    private boolean stationnullcheck() {
        String errorMessage = "";
        if (StationOrderField.getText() == null || StationOrderField.getText().length() == 0) {
            errorMessage += "վ�򲻵�Ϊ��!\n"; 
        }
        if (StationNameField.getText() == null || StationNameField.getText().length() == 0) {
            errorMessage += "վ������Ϊ��!\n"; 
        }
        if (ArriveTimeField.getText() == null || ArriveTimeField.getText().length() == 0) {
            errorMessage += "����ʱ�䲻��Ϊ��!\n"; 
        }
        if (LeaveTimeField.getText() == null || LeaveTimeField.getText().length() == 0) {
            errorMessage += "�뿪ʱ�䲻��Ϊ��!\n"; 
        } 
        if (TimeConsumeField.getText() == null || TimeConsumeField.getText().length() == 0) {
            errorMessage += "��ʱ����Ϊ��!\n"; 
        }
        
        if (DistanceField.getText() == null || DistanceField.getText().length() == 0) {
            errorMessage += "��̲���Ϊ��!\n"; 
        }
        
        if (LastStationField.getText() == null || LastStationField.getText().length() == 0) {
            errorMessage += "ǰһվ����Ϊ��!\n"; 
        }
        
        if (NextStationField.getText() == null || NextStationField.getText().length() == 0) {
            errorMessage += "��һվ����Ϊ��!\n"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("��Ч������Ϣ��");
            alert.setHeaderText("���޸���Ч��������Ϣ��");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean trainnullcheck() {
        String errorMessage = "";
        if (TrainNumberField.getText() == null || TrainNumberField.getText().length() == 0) {
            errorMessage += "���β���Ϊ��!\n"; 
        }
        if (TrainTypeField.getText() == null || TrainTypeField.getText().length() == 0) {
            errorMessage += "���Ͳ���Ϊ��!\n"; 
        }
        if (StartStationField.getText() == null || StartStationField.getText().length() == 0) {
            errorMessage += "ʼ��վ����Ϊ��!\n"; 
        }
        if (DestiStationField.getText() == null || DestiStationField.getText().length() == 0) {
            errorMessage += "�յ�վ����Ϊ��!\n"; 
        } 
        if (TotalTimeField.getText() == null || TotalTimeField.getText().length() == 0) {
            errorMessage += "�ܺ�ʱ����Ϊ��!\n"; 
        }
        
        if (TotalLengthField.getText() == null || TotalLengthField.getText().length() == 0) {
            errorMessage += "����̲���Ϊ��!\n"; 
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
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
