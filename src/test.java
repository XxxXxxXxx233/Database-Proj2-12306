import java.sql.*;

public class test {
    public static void main(String[] args) throws SQLException {
        DatabaseOperation d = new DatabaseOperation();
//        System.out.println(d.searchStationInProvince("北京"));
//        System.out.println(d.searchStationInCity("深圳"));
//        System.out.println(d.searchTrainBasicInformation("G6160"));
//        System.out.println(d.searchTrainDetailInformation("G6160"));
        d.buySomeTickets(3, "深圳北", "广州南");
        System.out.println();
        d.searchOrderAndTicket(3);
        System.out.println();
        d.returnSomeTickets(3);
        System.out.println();

        d.buySomeTickets(3, "深圳北", "广州南");
        System.out.println();
        d.searchOrderAndTicket(3);
        System.out.println();
        d.returnSomeTickets(3);
        System.out.println();
        d.searchOrderAndTicket(3);
        System.out.println();

        d.buySomeTickets(3, "深圳北", "广州南");
        System.out.println();
        d.searchOrderAndTicket(3);
        System.out.println();
    }
}
