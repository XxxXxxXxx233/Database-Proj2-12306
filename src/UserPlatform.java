import java.sql.SQLException;
import java.util.Scanner;

public class UserPlatform implements DataModification {

    DatabaseOperation d;
    Scanner in = new Scanner(System.in);
    int currentUser = -1;

    public UserPlatform() {
        d = new DatabaseOperation();
    }

    public int getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void userRegister() throws SQLException {
        System.out.print("请输入姓名: ");
        String username = in.next();
        System.out.print("请输入身份证号: ");
        String id = in.next();
        System.out.print("请输入手机号: ");
        String phone_number = in.next();
        System.out.print("请输入账号: ");
        String account = in.next();
        while(!d.checkExistingAccount(account)) {
            System.out.print("此账号已被注册，请重新输入: ");
            account = in.next();
        }
        System.out.print("请输入密码: ");
        String password = in.next();
        d.userRegister(username, id, phone_number, account, password);
        System.out.println("注册成功");
        this.currentUser = Integer.parseInt(d.getUserID(account));
    }

    @Override
    public void userLogIn() throws SQLException {
        System.out.print("请输入账号: ");
        String account = in.next();
        while(d.checkExistingAccount(account)) {
            System.out.print("此账号不存在，请重新输入: ");
            account = in.next();
        }
        String password = d.getPasswordByAccount(account);
        System.out.print("请输入密码: ");
        String userEnter = in.next();
        while(!userEnter.equals(password)) {
            System.out.print("密码错误，请重新输入: ");
            userEnter = in.next();
        }
        this.currentUser = Integer.parseInt(d.getUserID(account));
        System.out.println("登录成功");
    }

    @Override
    public void userLogOut() {
        this.currentUser = -1;
        System.out.println("已注销登录");
    }

    @Override
    public void userUpdate() throws SQLException {
        if(this.currentUser == -1)
            System.out.println("还未登录");
        else {
            System.out.print("请再次输入密码: ");
            String password = d.getPasswordByUserID(this.currentUser);
            String userEnter = in.next();
            int count = 0;
            while(!userEnter.equals(password)) {
                if(count == 5) {
                    System.out.println("错误次数过多");
                    return;
                }
                System.out.print("密码错误，请重新输入 (还有" + (5-count) + "次机会): ");
                userEnter = in.next();
                count++;
            }
            System.out.println("当前用户信息为: ");
            System.out.println(d.getUserInformation(this.currentUser));
            String[] column = {"用户名", "身份证号", "手机号", "账号", "密码"};
            String[] columnName = {"user_name", "id_card_num", "phone_number", "account", "password"};
            System.out.print("请输入要修改信息的序号（0则不修改）: ");
            int num = in.nextInt();
            in.nextLine();
            if(num >= 1 && num <= 5) {
                System.out.print("请输入新" + column[num-1] + ": ");
                String info = in.next();
                d.updateUserInfo(this.currentUser, columnName[num-1], info);
                System.out.println("新用户信息: ");
                System.out.println(d.getUserInformation(this.currentUser));
            } else if(num != 0) {
                System.out.println("非法输入！");
            }
        }
    }

    @Override
    public void trainInsert() throws SQLException {
        if(d.isAdministrator(this.currentUser)) {
            System.out.println("请输入新车次基本信息: ");
            in.nextLine();
            String[] basicInfo = in.nextLine().split(" ");
            if(!d.checkExistingTrain(basicInfo[0])) {
                System.out.println("该车次已存在");
                return;
            }
            System.out.println(d.insertTrainBasicInfo(basicInfo));
            System.out.print("请输入该列车到达的站点数量: ");
            int num = Integer.parseInt(in.nextLine());
            String[][] detailInfo = new String[num][];
            for(int i=0; i<num; i++) {
                detailInfo[i] = in.nextLine().split(" ");
            }
            System.out.println(d.insertTrainDetailInfo(basicInfo[0], detailInfo));
            System.out.println("新建成功");
        } else {
            System.out.println("没有此操作的权限");
        }
    }

    @Override
    public void trainUpdate() throws SQLException {
        if(d.isAdministrator(this.currentUser)) {
            System.out.print("请输入要修改的车次: ");
            String train_code = in.next();
            System.out.println(d.searchTrainBasicInformation(train_code));
            System.out.println(d.searchTrainDetailInformation(train_code));
            System.out.print("修改基本信息(1) / 修改详细信息(2)? ");
            int choose = in.nextInt();
            in.nextLine();
            if(choose == 1) {
                System.out.println("请输入想修改的列及新的值: ");
                String[] column = {"车次", "类型", "总耗时", "总里程"};
                String[] columnName = {"train_code", "train_type", "total_time", "total_mile"};
                String[] info = in.nextLine().split(" ");
                int index = -1;
                for(int i=0; i<3; i++) {
                    if(info[0].equals(column[i])) {
                        index = i;
                        break;
                    }
                }
                if(index != -1) {
                    String name = columnName[index];
                    String value = info[1];
                    d.updateTrainBasicInfo(train_code, name, value);
                    System.out.println("更新后的信息为: ");
                    System.out.println(d.searchTrainBasicInformation(train_code));
                } else {
                    System.out.println("非法输入");
                }
            } else if (choose == 2) {
                System.out.print("请输入想修改的数量: ");
                int num = in.nextInt();
                in.nextLine();
                System.out.println("请输入想修改的列及新的值: ");
                String[] column = {"到达时间", "出发时间", "耗时", "里程"};
                String[] columnName = {"arrival_time", "departure_time", "total_time", "miles"};
                for(int i=0; i<num; i++) {
                    String[] info = in.nextLine().split(" ");
                    int index = -1;
                    for(int j=0; j<column.length; j++) {
                        if(info[1].equals(column[j])) {
                            index = j;
                            break;
                        }
                    }
                    if(index != -1) {
                        String stationName = info[0];
                        String name = columnName[index];
                        String value = info[2];
                        d.updateTrainDetailInfo(train_code, stationName, name, value);
                    } else {
                        System.out.println("非法输入");
                    }
                }
                System.out.println("更新后的信息为: ");
                System.out.println(d.searchTrainDetailInformation(train_code));
            }
        } else {
            System.out.println("没有此操作的权限");
        }
    }

    @Override
    public void trainDelete() throws SQLException {
        if(d.isAdministrator(this.currentUser)) {
            System.out.print("请输入要删除的车次: ");
            String train_code = in.next();
            d.deleteTrain(train_code);
            if(!d.isTrainAlive(train_code)) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } else {
            System.out.println("没有此操作的权限");
        }
    }

    @Override
    public void stationInsert() throws SQLException {
        if(d.isAdministrator(this.currentUser)) {
            System.out.print("请输入新站名: ");
            String stationName = in.next();
            System.out.print("请输入所在城市: ");
            String cityName = in.next();
            if(d.checkExistingStation(stationName, cityName)) {
                System.out.println("新站点信息为: ");
                System.out.print(d.createNewStation(stationName, cityName));
            } else {
                System.out.println("该站点已存在");
            }
        } else {
            System.out.println("没有此操作的权限");
        }
    }

    @Override
    public void stationUpdate() throws SQLException {
        if(d.isAdministrator(this.currentUser)) {
            System.out.print("请输入要修改的站名: ");
            String stationName = in.next();
            int stationID = d.getStationIDByName(stationName);
            System.out.println("该站当前信息为: ");
            System.out.println(d.getStationInformation(stationID));
            String[] column = {"站名", "所在城市"};
            String[] columnName = {"station_name", "city_name"};
            System.out.print("请输入要修改信息的序号（0则不修改）: ");
            int num = in.nextInt();
            in.nextLine();
            if(num >= 1 && num <= 2) {
                System.out.print("请输入新" + column[num-1] + ": ");
                String info = in.next();
                d.updateStationInfo(stationID, columnName[num-1], info);
                System.out.println("更新后的信息: ");
                System.out.println(d.getStationInformation(stationID));
            } else if(num != 0) {
                System.out.println("非法输入！");
            }
        } else {
            System.out.println("没有此操作的权限");
        }
    }

    @Override
    public void stationDelete() throws SQLException {
        if(d.isAdministrator(this.currentUser)) {
            System.out.print("请输入要删除的站名: ");
            String stationName = in.next();
            d.deleteStation(stationName);
            if(!d.isStationAlive(stationName)) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } else {
            System.out.println("没有此操作的权限");
        }
    }

}
