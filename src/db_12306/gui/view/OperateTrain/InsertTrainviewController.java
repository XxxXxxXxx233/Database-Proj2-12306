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
            alert.setHeaderText("站点删除失败！");
            alert.setContentText("请在表中选择一项再点击删除");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void  handleCreateTrain() throws SQLException {
    	if(trainnullcheck()) {
    		String[] basicInfo= {TrainNumberField.getText(),TrainTypeField.getText(),StartStationField.getText(),DestiStationField.getText(),TotalTimeField.getText(),TotalLengthField.getText()};
    		if(d.checkExistingTrain(basicInfo[0])) {
    			StringBuilder traininfo=new StringBuilder("新建成功");
    			
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
	            alert.setTitle("新建列车");
	            alert.setHeaderText("列车新建成功，列车详细信息如下");
	            alert.setContentText(traininfo.toString());
	            alert.showAndWait();			
    		}else {
    			Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initOwner(dialogStage);
	            alert.setTitle("新建列车");
	            alert.setHeaderText("列车新建失败，该车次已存在");
	            alert.setContentText("");
	            alert.showAndWait();
    		}
    	}
    	
    }
    
    private boolean stationnullcheck() {
        String errorMessage = "";
        if (StationOrderField.getText() == null || StationOrderField.getText().length() == 0) {
            errorMessage += "站序不得为空!\n"; 
        }
        if (StationNameField.getText() == null || StationNameField.getText().length() == 0) {
            errorMessage += "站名不得为空!\n"; 
        }
        if (ArriveTimeField.getText() == null || ArriveTimeField.getText().length() == 0) {
            errorMessage += "到达时间不得为空!\n"; 
        }
        if (LeaveTimeField.getText() == null || LeaveTimeField.getText().length() == 0) {
            errorMessage += "离开时间不得为空!\n"; 
        } 
        if (TimeConsumeField.getText() == null || TimeConsumeField.getText().length() == 0) {
            errorMessage += "耗时不得为空!\n"; 
        }
        
        if (DistanceField.getText() == null || DistanceField.getText().length() == 0) {
            errorMessage += "里程不得为空!\n"; 
        }
        
        if (LastStationField.getText() == null || LastStationField.getText().length() == 0) {
            errorMessage += "前一站不得为空!\n"; 
        }
        
        if (NextStationField.getText() == null || NextStationField.getText().length() == 0) {
            errorMessage += "后一站不得为空!\n"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("无效输入信息！");
            alert.setHeaderText("请修改无效的输入信息！");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean trainnullcheck() {
        String errorMessage = "";
        if (TrainNumberField.getText() == null || TrainNumberField.getText().length() == 0) {
            errorMessage += "车次不得为空!\n"; 
        }
        if (TrainTypeField.getText() == null || TrainTypeField.getText().length() == 0) {
            errorMessage += "车型不得为空!\n"; 
        }
        if (StartStationField.getText() == null || StartStationField.getText().length() == 0) {
            errorMessage += "始发站不得为空!\n"; 
        }
        if (DestiStationField.getText() == null || DestiStationField.getText().length() == 0) {
            errorMessage += "终点站不得为空!\n"; 
        } 
        if (TotalTimeField.getText() == null || TotalTimeField.getText().length() == 0) {
            errorMessage += "总耗时不得为空!\n"; 
        }
        
        if (TotalLengthField.getText() == null || TotalLengthField.getText().length() == 0) {
            errorMessage += "总里程不得为空!\n"; 
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("无效输入信息！");
            alert.setHeaderText("请修改无效的输入信息！");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
	
	
	
}
