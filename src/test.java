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
        try {
            if(Integer.parseInt(in.nextLine()) == 1) {
                up.userRegister();
            } else {
                up.userLogIn();
            }
        } catch (Exception e) {
            System.out.println("Error!");
            return;
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
        System.out.println("11 -> 删除列车");
        System.out.println("12 -> 插入新站点");
        System.out.println("13 -> 修改现有站点信息");
        System.out.println("14 -> 删除站点");
        System.out.println("15 -> 修改用户资料");
        System.out.println("16 -> 注销登录");
        int num = 0;
        while (num != -1) {
            try {
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
                        if(d.isStationAlive(stationName)){
                            System.out.println(d.searchTrainInformationInOneStation(stationName));
                        } else {
                            System.out.println("该站点不存在");
                        }
                        break;
                    case 4:
                        System.out.print("请输入出发站: ");
                        String from = in.nextLine();
                        System.out.print("请输入到达站: ");
                        String to = in.nextLine();
                        if(d.isStationAlive(from) && d.isStationAlive(to)) {
                            System.out.println(d.searchTrainFromOneStationToAnother(from, to));
                        } else {
                            System.out.println("非法站点");
                        }
                        break;
                    case 5:
                        System.out.print("请输入车次: ");
                        String train_code = in.nextLine();
                        if(d.isTrainAlive(train_code)) {
                            System.out.println(d.searchTrainBasicInformation(train_code));
                            System.out.println(d.searchTrainDetailInformation(train_code));
                        } else {
                            System.out.println("该车次不存在");
                        }
                        break;
                    case 6:
                        System.out.print("按站买票(1) / 按城市买票(2)? ");
                        if(Integer.parseInt(in.nextLine()) == 1) {
                            System.out.print("请输入出发站: ");
                            String fromStation = in.nextLine();
                            System.out.print("请输入到达站: ");
                            String toStation = in.nextLine();
                            if(d.isStationAlive(fromStation) && d.isStationAlive(toStation)) {
                                d.buySomeTicketsByStation(user, fromStation, toStation);
                            } else {
                                System.out.println("非法站点");
                            }
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
                        up.trainDelete();
                        break;
                    case 12:
                        up.stationInsert();
                        break;
                    case 13:
                        up.stationUpdate();
                        break;
                    case 14:
                        up.stationDelete();
                        break;
                    case 15:
                        up.userUpdate();
                        break;
                    case 16:
                        up.userLogOut();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error!");
            }
        }
    }
}
