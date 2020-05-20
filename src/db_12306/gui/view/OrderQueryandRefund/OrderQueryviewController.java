package db_12306.gui.view.OrderQueryandRefund;

import java.sql.SQLException;
import java.util.ArrayList;

import db_12306.db_operation_update.DatabaseOperation;
import db_12306.gui.MainApp;
import db_12306.gui.model.Order;
import db_12306.gui.model.OrderDetailInfo;
import db_12306.gui.model.Passenger;
import db_12306.gui.model.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class OrderQueryviewController {
	@FXML
	private TableView<Order> OrderListTable;
	@FXML 
    private TableColumn<Order, String> OrderorderColumn;
    @FXML
    private TableColumn<Order, String> CreateTimeColumn;
    @FXML
    private TableColumn<Order, String> OrderStatusColumn;
    @FXML
    private TableColumn<Order, String> fromCityColumn;
    @FXML
    private TableColumn<Order, String> toCityColumn;
    @FXML
    private TableColumn<Order, String> TicketNumColumn;
    
    @FXML
	private TableView<OrderDetailInfo> OrderDetailInfoTable;
    @FXML 
    private TableColumn<OrderDetailInfo, String> OrderColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> TrainNumberColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> TrainTypeColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> fromStationColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> departTimeColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> toStationColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> arriveTimeColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> SeatTypeColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> PriceColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> PassengerNameColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> CarriageNumColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> SeatNumColumn;
    @FXML 
    private TableColumn<OrderDetailInfo, String> StatusColumn;
    
    
    private Stage dialogStage;
    private MainApp mainApp;
    private DatabaseOperation d;
    private ObservableList<Order> orderData = FXCollections.observableArrayList();
    private ObservableList<OrderDetailInfo> orderdetailData = FXCollections.observableArrayList();
	private int userID;
	private int currentOrderid;
	private int ticketid;
    
    
    @FXML
    private void initialize() throws SQLException {
        // Initialize the person table with the two columns.
        OrderorderColumn.setCellValueFactory(
                cellData -> cellData.getValue().OrderorderProperty());
        CreateTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().CreateTimeProperty());
        OrderStatusColumn.setCellValueFactory(
                cellData -> cellData.getValue().StatusProperty());
        fromCityColumn.setCellValueFactory(
                cellData -> cellData.getValue().fromCityProperty());
        toCityColumn.setCellValueFactory(
                cellData -> cellData.getValue().toCityProperty());
        TicketNumColumn.setCellValueFactory(
                cellData -> cellData.getValue().TicketNumProperty());
        
        OrderColumn.setCellValueFactory(
                cellData -> cellData.getValue().OrderProperty());
        TrainNumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().TrainNumberProperty());
        TrainTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().TrainTypeProperty());
        fromStationColumn.setCellValueFactory(
                cellData -> cellData.getValue().fromStationProperty());
        toStationColumn.setCellValueFactory(
                cellData -> cellData.getValue().toStationProperty());
        departTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().departTimeProperty());
        arriveTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().arriveTimeProperty());
        SeatTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().SeatTypeProperty());
        PriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().PriceProperty());
        PassengerNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().PassengerNameProperty());
        CarriageNumColumn.setCellValueFactory(
                cellData -> cellData.getValue().CarriageNumProperty());
        SeatNumColumn.setCellValueFactory(
                cellData -> cellData.getValue().SeatNumProperty());
        StatusColumn.setCellValueFactory(
                cellData -> cellData.getValue().StatusProperty());
        
        //spy
        OrderListTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
					try {
						showOrderDetailInfo(newValue);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
        
        OrderDetailInfoTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectOrderDetailInfo(newValue));
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void setDatabasePOperation(DatabaseOperation d){
    	this.d=d;
    }
    
    public void setuserID(MainApp mainApp) throws NumberFormatException, SQLException{
    	this.userID=Integer.parseInt(d.getUserID(mainApp.currentaccount));
    }
    
	public void showOrders() throws NumberFormatException, SQLException {		
		String information=d.searchOrderInformation(this.userID);
		String[] orderinfo=information.split("\t");
		int order_num=orderinfo.length/6;
    	for(int i=0;i<order_num;i++){
    		Order order=new Order(orderinfo[i*6],orderinfo[i*6+1],orderinfo[i*6+2],orderinfo[i*6+3],orderinfo[i*6+4],orderinfo[i*6+5]);
    		orderData.add(order);
    	}
    	OrderListTable.setItems(orderData);
	}
	
	private void selectOrderDetailInfo(OrderDetailInfo a)  {		
		if(a!=null) {
			ticketid=Integer.parseInt(a.getOrder());
			System.out.println(ticketid);
		}
	}
	
	private void showOrderDetailInfo(Order order) throws SQLException {
		orderdetailData.clear();
		if(order!=null) {
			currentOrderid=Integer.parseInt(order.getOrderorder());
			ArrayList<Integer> orderIDArr = d.getOrderInformation(userID);
			int orderID=orderIDArr.get(currentOrderid-1);
			String information=d.searchTicketInformationInAnOrder(orderID);
			String[] orderdetailinfo=information.split("\t");
			int number=orderdetailinfo.length/13;
	    	for(int i=0;i<number;i++){
	    		OrderDetailInfo a=new OrderDetailInfo(orderdetailinfo[i*13],orderdetailinfo[i*13+1],orderdetailinfo[i*13+2],orderdetailinfo[i*13+3],orderdetailinfo[i*13+4],orderdetailinfo[i*13+5],orderdetailinfo[i*13+6],orderdetailinfo[i*13+7],orderdetailinfo[i*13+8],orderdetailinfo[i*13+9],orderdetailinfo[i*13+10],orderdetailinfo[i*13+11],orderdetailinfo[i*13+12]);
	    		orderdetailData.add(a);
	    	}
	    	OrderDetailInfoTable.setItems(orderdetailData);
		}		
	}
	
	@FXML
	private void handleDeleteOrder() throws SQLException {
		ArrayList<Integer> orderIDArr = d.getOrderInformation(userID);
		d.cancelWholeOrder(orderIDArr.get(currentOrderid-1));
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(dialogStage);
        alert.setTitle("");
        alert.setHeaderText("已取消该订单");
        alert.setContentText("");
        alert.showAndWait();
        orderData.clear();
        showOrders();
	}
	
	@FXML
	private void handleDeleteTicket() throws SQLException {
		ArrayList<Integer> orderIDArr = d.getOrderInformation(userID);
		d.canceloneTickets(orderIDArr.get(currentOrderid-1),ticketid-1);
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(dialogStage);
        alert.setTitle("");
        alert.setHeaderText("已取消该票");
        alert.setContentText("");
        alert.showAndWait();
        orderData.clear();
        showOrders();
	}
}
