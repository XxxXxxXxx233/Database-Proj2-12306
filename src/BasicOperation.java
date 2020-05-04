import java.sql.SQLException;

public interface BasicOperation {
    String searchStationInProvince(String provinceName) throws SQLException;
    String searchStationInCity(String cityName) throws SQLException;
    String searchTrainBasicInformation(String trainCode) throws SQLException;
    String searchTrainDetailInformation(String trainCode) throws SQLException;
    String searchTrainInformationInOneStation(String stationName) throws SQLException;
    String searchTrainFromOneStationToAnother(String fromStation, String toStation) throws SQLException;
    String searchTicketFromOneStationToAnother(String fromStation, String toStation) throws SQLException;
    void searchOrderAndTicket(int userID) throws SQLException;

    void buySomeTickets(int userID, String fromStation, String toStation) throws SQLException;
    void returnSomeTickets(int userID) throws SQLException;

    void userRegister(String username, String id, String phone_number, String account, String password) throws SQLException;
    boolean checkExistingAccount(String account) throws SQLException;
    String getPasswordByAccount(String account) throws SQLException;
    String getPasswordByUserID(int userID) throws SQLException;
    String getUserID(String account) throws SQLException;
    String getUserInformation(int user_id) throws SQLException;
    void updateUserInfo(int user_id, String column, String info) throws SQLException;
}
