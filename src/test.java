import java.sql.*;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws SQLException {
        DatabaseOperation d = new DatabaseOperation();
        Scanner in = new Scanner(System.in);
        UserPlatform up = new UserPlatform();
        System.out.println("1 -> 注册新用户");
        System.out.println("2 -> 登录");
        System.out.print("请输入: ");
        if(Integer.parseInt(in.nextLine()) == 1) {
            up.userRegister();
        } else {
            up.userLogIn();
        }

        System.out.println("-1 -> 退出");
        System.out.println("1 -> 查询省份内的所有车站");
        System.out.println("2 -> 查询城市内的所有车站");
        System.out.println("3 -> 查询到达某站的所有列车");
        System.out.println("4 -> 查询从某站到某站的所有列车");
        System.out.println("5 -> 查询某车次的信息");
        System.out.println("6 -> 订票");
        System.out.println("7 -> 查询订单与票");
        System.out.println("8 -> 退票");
        System.out.println("9 -> 插入新列车");
        System.out.println("10 -> 修改现有列车信息");
        System.out.println("11 -> 插入新站点");
        System.out.println("12 -> 修改现有站点信息");
        System.out.println("13 -> 修改用户资料");
        System.out.println("14 -> 注销登录");
        int num = 0;
        while (num != -1) {
            int user = up.getCurrentUser();
            if(user == -1) {
                System.out.println("-1 -> 退出");
                System.out.println("1 -> 注册新用户");
                System.out.println("2 -> 登录");
                System.out.print("请输入: ");
                int check = Integer.parseInt(in.nextLine());
                if(check == -1) {
                    break;
                } else if(check == 1) {
                    up.userRegister();
                } else {
                    up.userLogIn();
                }
                user = up.getCurrentUser();
            }
            System.out.print("请输入想进行的操作: ");
            num = Integer.parseInt(in.nextLine());
            switch (num) {
                case 1:
                    System.out.print("请输入省份: ");
                    String province = in.nextLine();
                    System.out.println(d.searchStationInProvince(province));
                    break;
                case 2:
                    System.out.print("请输入城市: ");
                    String city = in.nextLine();
                    System.out.println(d.searchStationInCity(city));
                    break;
                case 3:
                    System.out.print("请输入站名: ");
                    String stationName = in.nextLine();
                    System.out.println(d.searchTrainInformationInOneStation(stationName));
                    break;
                case 4:
                    System.out.print("请输入出发站: ");
                    String from = in.nextLine();
                    System.out.print("请输入到达站: ");
                    String to = in.nextLine();
                    System.out.println(d.searchTrainFromOneStationToAnother(from, to));
                    break;
                case 5:
                    System.out.print("请输入车次: ");
                    String train_code = in.nextLine();
                    System.out.println(d.searchTrainBasicInformation(train_code));
                    System.out.println(d.searchTrainDetailInformation(train_code));
                    break;
                case 6:
                    System.out.println("按站买票(1) / 按城市买票(2) ?");
                    if(Integer.parseInt(in.nextLine()) == 1) {
                        System.out.print("请输入出发站: ");
                        String fromStation = in.nextLine();
                        System.out.print("请输入到达站: ");
                        String toStation = in.nextLine();
                        d.buySomeTicketsByStation(user, fromStation, toStation);
                    } else {
                        System.out.print("请输入出发城市: ");
                        String fromCity = in.nextLine();
                        System.out.print("请输入到达城市: ");
                        String toCity = in.nextLine();
                        d.buySomeTicketsByCity(user, fromCity, toCity);
                    }
                    break;
                case 7:
                    d.searchOrderAndTicket(user);
                    break;
                case 8:
                    d.returnSomeTickets(user);
                    break;
                case 9:
                    up.trainInsert();
                    break;
                case 10:
                    up.trainUpdate();
                    break;
                case 11:
                    up.stationInsert();
                    break;
                case 12:
                    up.stationUpdate();
                    break;
                case 13:
                    up.userUpdate();
                    break;
                case 14:
                    up.userLogOut();
                    break;
            }
        }
    }
}
