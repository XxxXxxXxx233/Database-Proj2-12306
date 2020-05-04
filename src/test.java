import java.sql.*;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws SQLException {
        DatabaseOperation d = new DatabaseOperation();
//        System.out.println(d.searchStationInProvince("北京"));
//        System.out.println(d.searchStationInCity("深圳"));
//        System.out.println(d.searchTrainInformationInOneStation("尼木"));
//        System.out.println(d.searchTrainBasicInformation("G6160"));
//        System.out.println(d.searchTrainDetailInformation("Z8804"));
/*        d.buySomeTickets(3, "深圳北", "广州南");
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
        System.out.println();*/

        Scanner in = new Scanner(System.in);
        UserPlatform up = new UserPlatform();
        up.userLogIn();
        System.out.println("-1 -> 退出");
        System.out.println("1 -> 查询省份内的所有车站");
        System.out.println("2 -> 查询城市内的所有车站");
        System.out.println("3 -> 查询到达某站的所有列车");
        System.out.println("4 -> 查询从某站到某站的所有列车");
        System.out.println("5 -> 查询某车次的信息");
        System.out.println("6 -> 订票");
        System.out.println("7 -> 查询订单与票");
        System.out.println("8 -> 退票");
        System.out.println("9 -> 修改资料");
        System.out.println("10 -> 注销登录");
        int num = 0;
        while (num != -1) {
            int user = up.getCurrentUser();
            if(user == -1) {
                up.userLogIn();
            }
            System.out.print("请输入想进行的操作: ");
            num = in.nextInt();
            switch (num) {
                case 1:
                    System.out.print("请输入省份: ");
                    String province = in.next();
                    System.out.println(d.searchStationInProvince(province));
                    break;
                case 2:
                    System.out.print("请输入城市: ");
                    String city = in.next();
                    System.out.println(d.searchStationInCity(city));
                    break;
                case 3:
                    System.out.print("请输入站名: ");
                    String stationName = in.next();
                    System.out.println(d.searchTrainInformationInOneStation(stationName));
                    break;
                case 4:
                    System.out.print("请输入出发站: ");
                    String from = in.next();
                    System.out.print("请输入到达站: ");
                    String to = in.next();
                    System.out.println(d.searchTrainFromOneStationToAnother(from, to));
                    break;
                case 5:
                    System.out.print("请输入车次: ");
                    String train_code = in.next();
                    System.out.println(d.searchTrainBasicInformation(train_code));
                    System.out.println(d.searchTrainDetailInformation(train_code));
                    break;
                case 6:
                    System.out.print("请输入出发站: ");
                    String fromStation = in.next();
                    System.out.print("请输入到达站: ");
                    String toStation = in.next();
                    d.buySomeTickets(user, fromStation, toStation);
                    break;
                case 7:
                    d.searchOrderAndTicket(user);
                    break;
                case 8:
                    d.returnSomeTickets(user);
                    break;
                case 9:
                    up.userUpdate();
                    break;
                case 10:
                    up.userLogOut();
                    break;
            }
        }
    }
}
