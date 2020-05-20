package db_12306.gui;

import java.io.IOException;
import java.sql.SQLException;

import db_12306.db_operation_update.DatabaseOperation;
import db_12306.gui.view.AdministratorPageController;
import db_12306.gui.view.LoginviewController;
import db_12306.gui.view.NormalUserPageController;
import db_12306.gui.view.SignUpController;
import db_12306.gui.view.BuyTicket.BuyTicketviewController;
import db_12306.gui.view.BuyTicket.TravelRecommendviewController;
import db_12306.gui.view.ModifyStation.ModifyStationviewController;
import db_12306.gui.view.OperateTrain.DeleteTrainviewController;
import db_12306.gui.view.OperateTrain.InsertTrainviewController;
import db_12306.gui.view.OperateTrain.OperateTrainviewController;
import db_12306.gui.view.OrderQueryandRefund.OrderQueryviewController;
import db_12306.gui.view.Query.QueryviewController;
import db_12306.gui.view.Query.StationQueryviewController;
import db_12306.gui.view.Query.TrainNumberviewController;
import db_12306.gui.view.Query.TrainStopStationviewController;
import db_12306.gui.view.UserInfoModify.UserInfoModifyviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;
    public String currentaccount;
    DatabaseOperation d;
    
    public static void main(String[] args) {
        launch(args);
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		d = new DatabaseOperation();
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("12306");
        this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
        initRootLayout();
        showLoginview();    
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showLoginview() {
		try {
            //load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Loginview.fxml"));
            AnchorPane Loginview = (AnchorPane) loader.load();
            
            // Set stage
            rootLayout.setCenter(Loginview);
            
            //set controller
            LoginviewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDatabasePOperation(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showSignUpview() throws IOException
	{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/SignUpview.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage signupStage = new Stage();
        signupStage.setTitle("12306-注册");
        signupStage.initModality(Modality.WINDOW_MODAL);
        signupStage.initOwner(primaryStage);
        signupStage.getIcons().add(new Image("file:resources/images/icon.png"));
        Scene scene = new Scene(page);
        signupStage.setScene(scene);

        SignUpController controller = loader.getController();
        controller.setDialogStage(signupStage);
        controller.setDatabasePOperation(d);

        signupStage.showAndWait();   
	}
	
	public void showNormalUserPage() 
	{
		try {		
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NormalUserPage.fxml"));
            AnchorPane NormalUserPage = (AnchorPane) loader.load();
                       
            rootLayout.setCenter(NormalUserPage);
            primaryStage.setTitle("12306");
            
            NormalUserPageController controller = loader.getController();
            controller.setMainApp(this);
            controller.changeAccountLabel();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showQueryview() 
	{
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Query/Queryview.fxml"));
            AnchorPane Queryview = (AnchorPane) loader.load();

	        Stage QueryviewStage = new Stage();
	        QueryviewStage.setTitle("12306-查询");
	        QueryviewStage.initModality(Modality.WINDOW_MODAL);
	        QueryviewStage.initOwner(primaryStage);
	        QueryviewStage.getIcons().add(new Image("file:resources/images/icon.png"));
	        Scene scene = new Scene(Queryview);
	        QueryviewStage.setScene(scene);

	        QueryviewController controller = loader.getController();
	        controller.setDialogStage(QueryviewStage);
	        controller.setMainApp(this);

	        QueryviewStage.showAndWait();             
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void showStationQueryview(Stage iniOwner)
	{
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Query/StationQueryview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage StationStage = new Stage();
            StationStage.setTitle("12306-车站查询");
            StationStage.initModality(Modality.WINDOW_MODAL);
            StationStage.initOwner(iniOwner);
            StationStage.getIcons().add(new Image("file:resources/images/icon.png"));
            Scene scene = new Scene(page);
            StationStage.setScene(scene);

            StationQueryviewController controller = loader.getController();
            controller.setDialogStage(StationStage);
            controller.setDatabasePOperation(d);

            StationStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showTrainNumberQueryview(Stage iniOwner)
	{
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Query/TrainNumberQueryview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage TrainStage = new Stage();
            TrainStage.setTitle("12306-列车查询");
            TrainStage.initModality(Modality.WINDOW_MODAL);
            TrainStage.initOwner(iniOwner);
            TrainStage.getIcons().add(new Image("file:resources/images/icon.png"));
            Scene scene = new Scene(page);
            TrainStage.setScene(scene);

            TrainNumberviewController controller = loader.getController();
            controller.setDialogStage(TrainStage);
            controller.setDatabasePOperation(d);

            TrainStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showTrainStopStationQueryview(Stage iniOwner)
	{
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Query/TrainStopStationQueryview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage TrainStage = new Stage();
            TrainStage.setTitle("12306-列车查询");
            TrainStage.initModality(Modality.WINDOW_MODAL);
            TrainStage.initOwner(iniOwner);
            TrainStage.getIcons().add(new Image("file:resources/images/icon.png"));
            Scene scene = new Scene(page);
            TrainStage.setScene(scene);

            TrainStopStationviewController controller = loader.getController();
            controller.setDialogStage(TrainStage);
            controller.setDatabasePOperation(d);

            TrainStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showBuyTicketview() throws NumberFormatException, SQLException 
	{
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BuyTicket/BuyTicketview.fxml"));
            AnchorPane BuyTicketview = (AnchorPane) loader.load();

	        Stage BuyTicketviewStage = new Stage();
	        BuyTicketviewStage.setTitle("12306-订票");
	        BuyTicketviewStage.initModality(Modality.WINDOW_MODAL);
	        BuyTicketviewStage.initOwner(primaryStage);
	        BuyTicketviewStage.getIcons().add(new Image("file:resources/images/icon.png"));
	        Scene scene = new Scene(BuyTicketview);
	        BuyTicketviewStage.setScene(scene);

	        BuyTicketviewController controller = loader.getController();
	        controller.setDialogStage(BuyTicketviewStage);
	        controller.setDatabasePOperation(d);
	        controller.setuserID(currentaccount);
	        controller.setMainApp(this);

	        BuyTicketviewStage.showAndWait();             
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showTravelRecommendview(BuyTicketviewController b,String routine) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/BuyTicket/TravelRecommendview.fxml"));
        AnchorPane TravelRecommendview = (AnchorPane) loader.load();

        Stage TravelRecommendviewStage = new Stage();
        TravelRecommendviewStage.setTitle("12306-订票-行程推荐");
        TravelRecommendviewStage.initModality(Modality.WINDOW_MODAL);
        TravelRecommendviewStage.initOwner(primaryStage);
        TravelRecommendviewStage.getIcons().add(new Image("file:resources/images/icon.png"));
        Scene scene = new Scene(TravelRecommendview);
        TravelRecommendviewStage.setScene(scene);

        TravelRecommendviewController controller = loader.getController();
        controller.setDialogStage(TravelRecommendviewStage);
        controller.setDatabasePOperation(d);
        controller.setBuyTicket(b);
        controller.setroutine(routine);
        controller.setLabel();

        TravelRecommendviewStage.showAndWait(); 
	}
	
	public void showOrderQueryview() throws NumberFormatException, SQLException 
	{
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderQueryandRefund/OrderQueryview.fxml"));
            AnchorPane OrderQueryview = (AnchorPane) loader.load();

	        Stage OrderQueryviewStage = new Stage();
	        OrderQueryviewStage.setTitle("12306-查询订单及退票");
	        OrderQueryviewStage.initModality(Modality.WINDOW_MODAL);
	        OrderQueryviewStage.initOwner(primaryStage);
	        OrderQueryviewStage.getIcons().add(new Image("file:resources/images/icon.png"));
	        Scene scene = new Scene(OrderQueryview);
	        OrderQueryviewStage.setScene(scene);

	        OrderQueryviewController controller = loader.getController();
	        controller.setDialogStage(OrderQueryviewStage);
	        controller.setMainApp(this);
	        controller.setDatabasePOperation(d);
	        controller.setuserID(this);
	        controller.showOrders();

	        OrderQueryviewStage.showAndWait();             
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showAdministratorPage() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AdministratorPage.fxml"));
            AnchorPane AdministratorPage = (AnchorPane) loader.load();
            
            rootLayout.setCenter(AdministratorPage);
            primaryStage.setTitle("12306（管理员）");
            
            AdministratorPageController controller = loader.getController();
            controller.setMainApp(this);
            controller.changeAccountLabel();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showModifyStationview() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ModifyStation/ModifyStationview.fxml"));
        AnchorPane ModifyStation = (AnchorPane) loader.load();

        Stage ModifyStationStage = new Stage();
        ModifyStationStage.setTitle("12306-修改车站信息（管理员）");
        ModifyStationStage.initModality(Modality.WINDOW_MODAL);
        ModifyStationStage.initOwner(primaryStage);
        ModifyStationStage.getIcons().add(new Image("file:resources/images/icon.png"));
        Scene scene = new Scene(ModifyStation);
        ModifyStationStage.setScene(scene);

        ModifyStationviewController controller = loader.getController();
        controller.setDialogStage(ModifyStationStage);
        controller.setDatabasePOperation(d);

        ModifyStationStage.showAndWait();
	}
	
	public void showOperateTrainview() 
	{
		try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OperateTrain/OperateTrainview.fxml"));
            AnchorPane OperateTrainview = (AnchorPane) loader.load();

	        Stage OperateTrainviewStage = new Stage();
	        OperateTrainviewStage.setTitle("12306-修改列车信息（管理员）");
	        OperateTrainviewStage.initModality(Modality.WINDOW_MODAL);
	        OperateTrainviewStage.initOwner(primaryStage);
	        OperateTrainviewStage.getIcons().add(new Image("file:resources/images/icon.png"));
	        Scene scene = new Scene(OperateTrainview);
	        OperateTrainviewStage.setScene(scene);

	        OperateTrainviewController controller = loader.getController();
	        controller.setDialogStage(OperateTrainviewStage);
	        controller.setMainApp(this);

	        OperateTrainviewStage.showAndWait();             
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showInsertTrainview(Stage parentStage) 
	{
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OperateTrain/InsertTrainview.fxml"));
            AnchorPane InsertTrainview = (AnchorPane) loader.load();

	        Stage InsertTrainviewStage = new Stage();
	        InsertTrainviewStage.setTitle("12306-修改列车信息-新增列车（管理员）");
	        InsertTrainviewStage.initModality(Modality.WINDOW_MODAL);
	        InsertTrainviewStage.initOwner(parentStage);
	        InsertTrainviewStage.getIcons().add(new Image("file:resources/images/icon.png"));
	        Scene scene = new Scene(InsertTrainview);
	        InsertTrainviewStage.setScene(scene);

	        InsertTrainviewController controller = loader.getController();
	        controller.setDialogStage(InsertTrainviewStage);
	        controller.setDatabasePOperation(d);

	        InsertTrainviewStage.showAndWait();             
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showDeleteTrainview(Stage parentStage) 
	{
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OperateTrain/DeleteTrainview.fxml"));
            AnchorPane DeleteTrainview = (AnchorPane) loader.load();

	        Stage DeleteTrainviewStage = new Stage();
	        DeleteTrainviewStage.setTitle("12306-修改列车信息-注销列车（管理员）");
	        DeleteTrainviewStage.initModality(Modality.WINDOW_MODAL);
	        DeleteTrainviewStage.initOwner(parentStage);
	        DeleteTrainviewStage.getIcons().add(new Image("file:resources/images/icon.png"));
	        Scene scene = new Scene(DeleteTrainview);
	        DeleteTrainviewStage.setScene(scene);

	        DeleteTrainviewController controller = loader.getController();
	        controller.setDialogStage(DeleteTrainviewStage);
	        controller.setDatabasePOperation(d);

	        DeleteTrainviewStage.showAndWait();             
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void showUserInfoModifyview() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/UserInfoModify/UserInfoModifyview.fxml"));
        AnchorPane UserInfoModifyview = (AnchorPane) loader.load();

        rootLayout.setCenter(UserInfoModifyview);
        primaryStage.setTitle("12306-修改用户信息");

        UserInfoModifyviewController controller = loader.getController();
        controller.setDatabasePOperation(d);
        controller.setMainApp(this);		
	}	
}
