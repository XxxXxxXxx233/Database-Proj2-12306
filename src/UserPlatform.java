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
        currentUser = Integer.parseInt(d.getUserID(account));
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
        currentUser = Integer.parseInt(d.getUserID(account));
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

    }

    @Override
    public void trainUpdate() throws SQLException {

    }

    @Override
    public void stationInsert() throws SQLException {

    }

    @Override
    public void stationUpdate() throws SQLException {

    }

}
