package db_12306.gui.view.BuyTicket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import db_12306.db_operation_update.DatabaseOperation;
import db_12306.gui.MainApp;
import db_12306.gui.model.Passenger;
import db_12306.gui.model.Ticket;
import db_12306.gui.model.passengerInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BuyTicketviewController {
	@FXML
    public TextField fromCityField;
	@FXML
    public TextField toCityField;
	@FXML
    private TextField fromStationField;
	@FXML
    private TextField toStationField;
	@FXML
	private TableView<Ticket> TicketInformationTable;
	@FXML 
    private TableColumn<Ticket, String> TicketOrderColumn;
    @FXML
    private TableColumn<Ticket, String> TrainNumberColumn;
    @FXML
    private TableColumn<Ticket, String> TrainTypeColumn;
    @FXML
    private TableColumn<Ticket, String> fromStationColumn;
    @FXML
    private TableColumn<Ticket, String> DepartTimeColumn;
    @FXML
    private TableColumn<Ticket, String> toStationColumn;
    @FXML
    private TableColumn<Ticket, String> ArriveTimeColumn;
    @FXML
    private TableColumn<Ticket, String> SeatTypeColumn;
    @FXML
    private TableColumn<Ticket, String> TicketRestNumColumn;
    @FXML
    private TableColumn<Ticket, String> PriceColumn;
    
    private MainApp mainApp;
	private Stage dialogStage;
	private DatabaseOperation d;
	private int userID;
    private int ticketOrder;
    public ArrayList<Integer> ticketTypeArr;
    private int tickettype;
    ArrayList<passengerInfo> Passengers=new ArrayList<passengerInfo>();
    private ObservableList<Passenger> passengerData = FXCollections.observableArrayList();
	private int fromCity;
	private int toCity;
	private int sign;//city : 1; staion:2
    
    
    public BuyTicketviewController() {}     
    
    public ObservableList<Passenger> getPassengerData() {
		return passengerData;
	}
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void setPassengerData(ObservableList<Passenger> passengerData) {
		this.passengerData = passengerData;
	} 
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        TicketOrderColumn.setCellValueFactory(
                cellData -> cellData.getValue().TicketOrderProperty());
        TrainNumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().TrainNumberProperty());
        TrainTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().TrainTypeProperty());
        fromStationColumn.setCellValueFactory(
                cellData -> cellData.getValue().fromStationProperty());
        DepartTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().DepartTimeProperty());
        toStationColumn.setCellValueFactory(
                cellData -> cellData.getValue().toStationProperty());
        ArriveTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().ArriveTimeProperty());
        SeatTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().SeatTypeProperty());
        TicketRestNumColumn.setCellValueFactory(
                cellData -> cellData.getValue().TicketRestNumProperty());
        PriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().PriceProperty());
        
        //spy
        TicketInformationTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
					try {
						detectSelection(newValue);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
        
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    public void setuserID(String account) throws NumberFormatException, SQLException{
    	this.userID=Integer.parseInt(d.getUserID(account));
    }
    
    private void detectSelection(Ticket ticket) throws SQLException {
    	if(ticket!=null) {
    		ticketOrder=Integer.parseInt(ticket.getTicketOrder());	
    		fromCity=d.getCityIDByStation(ticket.getfromStation());
    		toCity=d.getCityIDByStation(ticket.gettoStation());
    	}
    }
    
    @FXML
    public void handleCityTicketQuery() throws SQLException, IOException{
    	sign=1;
    	String fromCity=fromCityField.getText();
    	String toCity=toCityField.getText();
    	
    	//A arraylist of Station for StationQuery
        ObservableList<Ticket> TicketData=FXCollections.observableArrayList();
 
        String information=d.searchTicketFromOneCityToAnother(fromCity, toCity);
        if(information.equals("")){
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("未查询到直达票,关闭窗口之后将为您搜寻转乘路线。");
            alert.showAndWait();
            TravelRecommend(fromCity, toCity);
        }else {
        	String[] traininf=information.split("\t");
        	int station_num=traininf.length/10;
        	for(int i=0;i<station_num;i++){
        		Ticket ticket=new Ticket(traininf[i*10],traininf[i*10+1],traininf[i*10+2],traininf[i*10+3],traininf[i*10+4],traininf[i*10+5],traininf[i*10+6],traininf[i*10+7],traininf[i*10+8],traininf[i*10+9]);
        		TicketData.add(ticket);
        	}
        	TicketInformationTable.setItems(TicketData);
        	ticketTypeArr = d.getTicketFromOneCityToAnother(fromCity, toCity);
            if(ticketTypeArr.size() == 0) {
            	Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.setContentText("未查询到有效车票");
                alert.showAndWait();  
            }           
        }
    }
    
    private void TravelRecommend(String fromCity,String toCity) throws SQLException, IOException {
        String fromCapital = d.getCapitalOfProvinceByCityName(fromCity);
        String toCapital = d.getCapitalOfProvinceByCityName(toCity);
        ticketTypeArr = d.getTicketFromOneCityToAnother(fromCapital, toCity);
        String routine="";
        if(ticketTypeArr.size() == 0) {
            ticketTypeArr = d.getTicketFromOneCityToAnother(fromCity, toCapital);
            if(ticketTypeArr.size() == 0) {
                ticketTypeArr = d.getTicketFromOneCityToAnother(fromCapital, toCapital);
                if(ticketTypeArr.size() == 0) {
                	Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(dialogStage);
                    alert.setTitle("");
                    alert.setHeaderText("");
                    alert.setContentText("未查询到有效转乘车票");
                    alert.showAndWait();
                } else {
                    ticketTypeArr = d.getTicketFromOneCityToAnother(toCapital, toCity);
                    if(ticketTypeArr.size() == 0) {
                    	Alert alert = new Alert(AlertType.ERROR);
                        alert.initOwner(dialogStage);
                        alert.setTitle("");
                        alert.setHeaderText("");
                        alert.setContentText("未查询到有效转乘车票");
                        alert.showAndWait();
                    } else {
                    	routine=fromCity + "\t" + fromCapital + "\t" + toCapital + "\t" + toCity;           
                    }
                }
            } else {
                ticketTypeArr = d.getTicketFromOneCityToAnother(toCapital, toCity);
                if(ticketTypeArr.size() == 0) {
                	Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(dialogStage);
                    alert.setTitle("");
                    alert.setHeaderText("");
                    alert.setContentText("未查询到有效转乘车票");
                    alert.showAndWait();
                } else {
                	routine=fromCity + "\t" + toCapital + "\t" + toCity;          
                    }
                }
            
        } else {
            ticketTypeArr = d.getTicketFromOneCityToAnother(fromCity, fromCapital);
            if(ticketTypeArr.size() == 0) {
            	Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.setContentText("未查询到有效转乘车票");
                alert.showAndWait();
            } else {
            	routine=fromCity + "\t" + fromCapital + "\t" + toCity; 
            }
        }
        if(routine!="") {
        	mainApp.showTravelRecommendview(this,routine);
        }
        
    }
    
    @FXML
    private void handleStationTicketQuery() throws SQLException{
    	sign=2;
    	String fromStation=fromStationField.getText();
    	String toStation=toStationField.getText();
    	
    	//A arraylist of Station for StationQuery
        ObservableList<Ticket> TicketData=FXCollections.observableArrayList();
 
        String information=d.searchTicketFromOneStationToAnother(fromStation, toStation);
        if(information.equals("")){
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("未查询到有效车票");
            alert.showAndWait();
        }else {
        	String[] traininf=information.split("\t");
        	int station_num=traininf.length/10;
        	for(int i=0;i<station_num;i++){
        		Ticket ticket=new Ticket(traininf[i*10],traininf[i*10+1],traininf[i*10+2],traininf[i*10+3],traininf[i*10+4],traininf[i*10+5],traininf[i*10+6],traininf[i*10+7],traininf[i*10+8],traininf[i*10+9]);
        		TicketData.add(ticket);
        	}
        	TicketInformationTable.setItems(TicketData);
        	ticketTypeArr = d.getTicketFromOneStationToAnother(fromStation, toStation);
            if(ticketTypeArr.size() == 0) {
            	Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.setContentText("未查询到有效车票");
                alert.showAndWait();  
            }           
        }       
    }
        
    @FXML
    private void handlepurchase() throws IOException, SQLException {
    	int selectedIndex = TicketInformationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	tickettype=ticketTypeArr.get(ticketOrder-1);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BuyTicket/Purchaseview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage purchaseStage = new Stage();
            purchaseStage.setTitle("12306-订票");
            purchaseStage.initModality(Modality.WINDOW_MODAL);
            purchaseStage.initOwner(this.dialogStage);
            purchaseStage.getIcons().add(new Image("file:resources/images/icon.png"));
            Scene scene = new Scene(page);
            purchaseStage.setScene(scene);

            PurchaseviewController controller = loader.getController();
            controller.setDialogStage(purchaseStage);
            controller.setparent(this);

            // Show the dialog and wait until the user closes it
            purchaseStage.showAndWait();
            Passengers.clear();
    		passengerData.clear();
    		if(sign==1)
    			handleCityTicketQuery();
    		else
    			handleStationTicketQuery();
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.dialogStage);
            alert.setTitle("");
            alert.setHeaderText("没有票被选中");
            alert.setContentText("请选择一张票之后再进行购买");
            alert.showAndWait();
        }
    }

	public void justbuyit() throws SQLException {
		ArrayList<Integer> ticketIDArr = d.generateSeatPosition(tickettype, Passengers.size());
		d.getSeatPosition(Passengers,ticketIDArr);
		int orderID = d.createOneOrder(userID, fromCity, toCity);
		System.out.println(ticketIDArr.size()+" "+Passengers.size());
		d.insertOrderInformation(orderID, ticketIDArr, Passengers);
		if(d.updateTicketRestNum(tickettype, Passengers.size()) != -1) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.dialogStage);
            alert.setTitle("");
            alert.setHeaderText("买票成功");
            alert.setContentText("");
            alert.showAndWait();
        } else {
        	Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.dialogStage);
            alert.setTitle("");
            alert.setHeaderText("买票失败");
            alert.setContentText("");
            alert.showAndWait();
        }
		
	}
}
