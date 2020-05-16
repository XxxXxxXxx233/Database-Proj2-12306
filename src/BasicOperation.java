import java.sql.SQLException;

public interface BasicOperation {

    String searchStationInProvince(String provinceName) throws SQLException;
    String searchStationInCity(String cityName) throws SQLException;
    String searchTrainBasicInformation(String trainCode) throws SQLException;
    String searchTrainDetailInformation(String trainCode) throws SQLException;
    String searchTrainInformationInOneStation(String stationName) throws SQLException;
    String searchTrainFromOneStationToAnother(String fromStation, String toStation) throws SQLException;
    String searchTicketFromOneCityToAnother(String fromCity, String toCity) throws SQLException;
    String searchTicketFromOneStationToAnother(String fromStation, String toStation) throws SQLException;
    void searchOrderAndTicket(int userID) throws SQLException;

    void buySomeTicketsByStation(int userID, String fromStation, String toStation) throws SQLException;
    void buySomeTicketsByCity(int userID, String fromCity, String toCity) throws SQLException;
    void returnSomeTickets(int userID) throws SQLException;

    void userRegister(String username, String id, String phone_number, String account, String password) throws SQLException;
    boolean checkExistingAccount(String account) throws SQLException;
    String getPasswordByAccount(String account) throws SQLException;
    String getPasswordByUserID(int userID) throws SQLException;
    String getUserID(String account) throws SQLException;

    String getUserInformation(int user_id) throws SQLException;
    void updateUserInfo(int user_id, String column, String info) throws SQLException;

    String insertTrainBasicInfo(String[] basicInfo) throws SQLException;
    String insertTrainDetailInfo(String train_code, String[][] detailInfo) throws SQLException;
    void deleteTrain(String train_code) throws SQLException;

    boolean checkExistingStation(String stationName, String cityName)throws SQLException;
    int getStationIDByName(String stationName) throws SQLException;
    String getStationInformation(int stationID) throws SQLException;
    void updateStationInfo(int stationID, String column, String info) throws SQLException;
    String createNewStation(String stationName, String cityName) throws SQLException;
    void deleteStation(String stationName) throws SQLException;

    boolean checkExistingTrain(String train_code) throws SQLException;
    void updateTrainBasicInfo(String train_code, String column, String info) throws SQLException;
    void updateTrainDetailInfo(String train_code, String stationName, String column, String info) throws SQLException;

    boolean isAdministrator(int user_id) throws SQLException;
    boolean isTrainAlive(String train_code) throws SQLException;
    boolean isStationAlive(String stationName) throws SQLException;
}
