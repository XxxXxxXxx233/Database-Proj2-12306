import java.sql.SQLException;

public interface BasicOperation {
    String searchStationInProvince(String provinceName) throws SQLException;
    String searchStationInCity(String cityName) throws SQLException;
    String searchTrainBasicInformation(String trainCode) throws SQLException;
    String searchTrainDetailInformation(String trainCode) throws SQLException;
    String searchTrainFromOneStationToAnother(String fromStation, String toStation) throws SQLException;
    String searchTicketFromOneStationToAnother(String fromStation, String toStation) throws SQLException;
    void searchOrderAndTicket(int userID) throws SQLException;

    void buySomeTickets(int userID, String fromStation, String toStation) throws SQLException;
    void returnSomeTickets(int userID) throws SQLException;
}
