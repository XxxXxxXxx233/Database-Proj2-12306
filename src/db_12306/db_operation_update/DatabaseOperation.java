package db_12306.db_operation_update;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import db_12306.gui.model.passengerInfo;
import db_12306.db_operation.ConnectionPool;

public class DatabaseOperation implements BasicOperation {

    ConnectionPool cp;
    Scanner in = new Scanner(System.in);

    public DatabaseOperation() {
        cp = new ConnectionPool();
    }

    @Override
    public String searchStationInProvince(String provinceName) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select s.station_name as station_name, c.city_name as city_name\n" +
                "from station s\n" +
                "         join city c on s.city_id = c.city_id\n" +
                "where c.province_name = ''||?||'' and s.alive = 'Y';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, provinceName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("station_name")).append("\t");
                sb.append(resultSet.getString("city_name")).append("\t");
                sb.append(provinceName).append("\t");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String searchStationInCity(String cityName) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select s.station_name  as station_name,\n" +
                "       c.province_name as province_name\n" +
                "from station s\n" +
                "         join city c on s.city_id = c.city_id\n" +
                "where c.city_name = ''||?||'' and s.alive = 'Y';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, cityName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("station_name")).append("\t");
                sb.append(cityName).append("\t");
                sb.append(resultSet.getString("province_name")).append("\t");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String searchTrainBasicInformation(String trainCode) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sqlBasicInfo = "select t.train_code                                                                   as train_code,\n" +
                "       t.train_type                                                                   as train_type,\n" +
                "       (select s1.station_name from station s1 where s1.station_id = t.start_station) as start_station,\n" +
                "       (select s1.station_name from station s1 where s1.station_id = t.end_station)   as end_station,\n" +
                "       t.total_time                                                                   as total_time,\n" +
                "       t.total_mile                                                                   as total_mile\n" +
                "from train t\n" +
                "where t.train_code = ''||?||'';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sqlBasicInfo);
            preparedStatement.setString(1, trainCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("train_code")).append("\t");
                sb.append(resultSet.getString("train_type")).append("\t");
                sb.append(resultSet.getString("start_station")).append("\t");
                sb.append(resultSet.getString("end_station")).append("\t");
                sb.append(resultSet.getString("total_time")).append("\t");
                sb.append(resultSet.getString("total_mile"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String searchTrainDetailInformation(String trainCode) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sqlDetailInfo = "select tas.passing_order                                                                 as passing_order,\n" +
                "       (select s1.station_name from station s1 where s1.station_id = tas.station_id)     as station_name,\n" +
                "       tas.arrival_time                                                                  as arrival_time,\n" +
                "       tas.departure_time                                                                as departure_time,\n" +
                "       tas.total_time                                                                    as total_time,\n" +
                "       tas.miles                                                                         as total_mile,\n" +
                "       (select s1.station_name from station s1 where s1.station_id = tas.former_station) as former_station,\n" +
                "       (select s1.station_name from station s1 where s1.station_id = tas.latter_station) as latter_station\n" +
                "from train_and_station tas\n" +
                "         join train t on tas.train_id = t.train_id\n" +
                "where t.train_code = ''||?||''\n" +
                "order by tas.passing_order;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sqlDetailInfo);
            preparedStatement.setString(1, trainCode);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("passing_order")).append("\t");
                sb.append(resultSet.getString("station_name")).append("\t");
                sb.append(resultSet.getString("arrival_time")).append("\t");
                sb.append(resultSet.getString("departure_time")).append("\t");
                sb.append(resultSet.getString("total_time")).append("\t");
                sb.append(resultSet.getString("total_mile")).append("\t");
                if(resultSet.getString("former_station") == null) {
                    sb.append("无").append("\t");
                } else {
                    sb.append(resultSet.getString("former_station")).append("\t");
                }
                if(resultSet.getString("latter_station") == null) {
                    sb.append("无").append("\t");
                } else {
                    sb.append(resultSet.getString("latter_station")).append("\t");
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String searchTrainInformationInOneStation(String stationName) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sqlDetailInfo = "select (select train_code from train t where sub1.train_id = t.train_id)                  as train_code,\n" +
                "       (select station_name from station s where sub1.station_id = s.station_id)          as station_name,\n" +
                "       sub1.arrival_time,\n" +
                "       sub1.departure_time,\n" +
                "       sub1.total_time,\n" +
                "       sub1.miles,\n" +
                "       (select s1.station_name from station s1 where s1.station_id = sub1.former_station) as former_station,\n" +
                "       (select s1.station_name from station s1 where s1.station_id = sub1.latter_station) as latter_station\n" +
                "from (\n" +
                "         select *\n" +
                "         from train_and_station\n" +
                "         where station_id = (select station_id from station s where station_name = ''||?||'')\n" +
                "           and (select alive from train where train.train_id = train_and_station.train_id) = 'Y'\n" +
                "     ) sub1;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sqlDetailInfo);
            preparedStatement.setString(1, stationName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("train_code")).append("\t");
                sb.append(resultSet.getString("station_name")).append("\t");
                sb.append(resultSet.getString("arrival_time")).append("\t");
                sb.append(resultSet.getString("departure_time")).append("\t");
                sb.append(resultSet.getString("total_time")).append("\t");
                sb.append(resultSet.getString("miles")).append("\t");
                if(resultSet.getString("former_station") == null) {
                    sb.append("无").append("\t");
                } else {
                    sb.append(resultSet.getString("former_station")).append("\t");
                }
                if(resultSet.getString("latter_station") == null) {
                    sb.append("无").append("\t");
                } else {
                    sb.append(resultSet.getString("latter_station")).append("\t");
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String searchTrainFromOneStationToAnother(String fromStation, String toStation) throws SQLException {      
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sqlBasicInfo = "select distinct t.train_code                                                                       as train_code,\n" +
                "                t.train_type                                                                       as train_type,\n" +
                "                (select s1.station_name from station s1 where s1.station_id = sub1.depart_station) as depart_station,\n" +
                "                (select tas.departure_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.depart_station)                                       as depart_time,\n" +
                "                (select s2.station_name from station s2 where s2.station_id = sub1.arrive_station) as arrival_station,\n" +
                "                (select tas.arrival_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.arrive_station)                                       as arrival_time\n" +
                "from (\n" +
                "         select distinct tt.train_id       as train_id,\n" +
                "                         tt.depart_station as depart_station,\n" +
                "                         tt.arrive_station as arrive_station\n" +
                "         from ticket_type tt\n" +
                "         where tt.depart_station = (select s1.station_id as from_id\n" +
                "                                    from station s1\n" +
                "                                    where s1.station_name = ''||?||'')\n" +
                "           and tt.arrive_station = (select s2.station_id as to_id\n" +
                "                                    from station s2\n" +
                "                                    where s2.station_name = ''||?||'')) sub1\n" +
                "         join train t on t.train_id = sub1.train_id and t.alive = 'Y'\n" +
                "order by depart_time;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sqlBasicInfo);
            preparedStatement.setString(1, fromStation);
            preparedStatement.setString(2, toStation);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("train_code")).append("\t");
                sb.append(resultSet.getString("train_type")).append("\t");
                sb.append(resultSet.getString("depart_station")).append("\t");
                sb.append(resultSet.getString("depart_time")).append("\t");
                sb.append(resultSet.getString("arrival_station")).append("\t");
                sb.append(resultSet.getString("arrival_time")).append("\t");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String searchTicketFromOneCityToAnother(String fromCity, String toCity) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        Map<String, String> seatTypeMap = new HashMap<>();
        seatTypeMap.put("second_class", "二等座");
        seatTypeMap.put("first_class", "一等座");
        seatTypeMap.put("business_class", "商务座");
        StringBuilder sb = new StringBuilder();
        String sqlBasicInfo = "select rank() over (order by sub2.price, arrival_time, train_code), sub2.*\n" +
                "from (\n" +
                "         select t.train_code                                                                       as train_code,\n" +
                "                t.train_type                                                                       as train_type,\n" +
                "                (select s1.station_name from station s1 where s1.station_id = sub1.depart_station) as depart_station,\n" +
                "                (select tas.departure_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.depart_station)                                       as depart_time,\n" +
                "                (select s2.station_name from station s2 where s2.station_id = sub1.arrive_station) as arrival_station,\n" +
                "                (select tas.arrival_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.arrive_station)                                       as arrival_time,\n" +
                "                sub1.seat_type                                                                     as seat_type,\n" +
                "                sub1.rest_num                                                                      as rest_num,\n" +
                "                sub1.price                                                                         as price\n" +
                "         from (\n" +
                "                  select tt.ticket_type_id as ticket_type_id,\n" +
                "                         tt.train_id       as train_id,\n" +
                "                         tt.depart_station as depart_station,\n" +
                "                         tt.arrive_station as arrive_station,\n" +
                "                         tt.seat_type      as seat_type,\n" +
                "                         tt.rest_num       as rest_num,\n" +
                "                         tt.price          as price\n" +
                "                  from ticket_type tt\n" +
                "                  where tt.depart_station in (select s1.station_id as from_id\n" +
                "                                             from station s1\n" +
                "                                             where s1.station_name in (select station_name\n" +
                "                                                                       from station\n" +
                "                                                                       where city_id = (select city_id from city where city_name = ''||?||'')))\n" +
                "                    and tt.arrive_station in (select s2.station_id as to_id\n" +
                "                                             from station s2\n" +
                "                                             where s2.station_name in (select station_name\n" +
                "                                                                      from station\n" +
                "                                                                      where city_id = (select city_id from city where city_name = ''||?||'')))\n" +
                "                    and tt.price != 0) sub1\n" +
                "                  join train t on t.train_id = sub1.train_id and t.alive = 'Y' )sub2;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sqlBasicInfo);
            preparedStatement.setString(1, fromCity);
            preparedStatement.setString(2, toCity);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("rank")).append("\t");
                sb.append(resultSet.getString("train_code")).append("\t");
                sb.append(resultSet.getString("train_type")).append("\t");
                sb.append(resultSet.getString("depart_station")).append("\t");
                sb.append(resultSet.getString("depart_time")).append("\t");
                sb.append(resultSet.getString("arrival_station")).append("\t");
                sb.append(resultSet.getString("arrival_time")).append("\t");
                sb.append(seatTypeMap.get(resultSet.getString("seat_type"))).append("\t");
                sb.append(resultSet.getString("rest_num")).append("\t");
                sb.append(resultSet.getString("price")).append("\t");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String searchTicketFromOneStationToAnother(String fromStation, String toStation) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        Map<String, String> seatTypeMap = new HashMap<>();
        seatTypeMap.put("second_class", "二等座");
        seatTypeMap.put("first_class", "一等座");
        seatTypeMap.put("business_class", "商务座");
        StringBuilder sb = new StringBuilder();
        String sqlBasicInfo = "select rank() over (order by sub2.price, arrival_time, train_code), sub2.*\n" +
                "from (\n" +
                "         select t.train_code                                                                       as train_code,\n" +
                "                t.train_type                                                                       as train_type,\n" +
                "                (select s1.station_name from station s1 where s1.station_id = sub1.depart_station) as depart_station,\n" +
                "                (select tas.departure_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.depart_station)                                       as depart_time,\n" +
                "                (select s2.station_name from station s2 where s2.station_id = sub1.arrive_station) as arrival_station,\n" +
                "                (select tas.arrival_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.arrive_station)                                       as arrival_time,\n" +
                "                sub1.seat_type                                                                     as seat_type,\n" +
                "                sub1.rest_num                                                                      as rest_num,\n" +
                "                sub1.price                                                                         as price\n" +
                "         from (\n" +
                "                  select tt.ticket_type_id as ticket_type_id,\n" +
                "                         tt.train_id       as train_id,\n" +
                "                         tt.depart_station as depart_station,\n" +
                "                         tt.arrive_station as arrive_station,\n" +
                "                         tt.seat_type      as seat_type,\n" +
                "                         tt.rest_num       as rest_num,\n" +
                "                         tt.price          as price\n" +
                "                  from ticket_type tt\n" +
                "                  where tt.depart_station = (select s1.station_id as from_id\n" +
                "                                             from station s1\n" +
                "                                             where s1.station_name = ''||?||'')\n" +
                "                    and tt.arrive_station = (select s2.station_id as to_id\n" +
                "                                             from station s2\n" +
                "                                             where s2.station_name = ''||?||'')\n" +
                "                    and tt.price != 0) sub1\n" +
                "                  join train t on t.train_id = sub1.train_id and t.alive = 'Y') sub2;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sqlBasicInfo);
            preparedStatement.setString(1, fromStation);
            preparedStatement.setString(2, toStation);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("rank")).append("\t");
                sb.append(resultSet.getString("train_code")).append("\t");
                sb.append(resultSet.getString("train_type")).append("\t");
                sb.append(resultSet.getString("depart_station")).append("\t");
                sb.append(resultSet.getString("depart_time")).append("\t");
                sb.append(resultSet.getString("arrival_station")).append("\t");
                sb.append(resultSet.getString("arrival_time")).append("\t");
                sb.append(seatTypeMap.get(resultSet.getString("seat_type"))).append("\t");
                sb.append(resultSet.getString("rest_num")).append("\t");
                sb.append(resultSet.getString("price")).append("\t");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public void searchOrderAndTicket(int userID) throws SQLException {
        System.out.println("---------- 当前用户的订单如下: ----------");
        System.out.print(searchOrderInformation(userID));
        System.out.print("是否要查询订单详情（是/否）: ");
        String text = in.next();
        in.nextLine();
        if(text.equals("是")) {
            System.out.print("请输入要查询的订单id: ");
            ArrayList<Integer> orderIDArr = getOrderInformation(userID);
            int orderID = orderIDArr.get(in.nextInt() - 1);
            in.nextLine();
            System.out.println("---------- 该订单下所有车票如下: ----------");
            System.out.print(searchTicketInformationInAnOrder(orderID));
        }
    }

    public String searchOrderInformation(int userID) throws SQLException {
   //     System.out.println("订单id\t创建时间\t状态\t出发城市\t到达城市\t车票数");
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select row_number() over (order by sub1.create_datetime)                   as id,\n" +
                "       sub1.order_id                                                       as order_id,\n" +
                "       sub1.create_datetime                                                as create_time,\n" +
                "       sub1.order_status                                                   as order_status,\n" +
                "       (select c.city_name from city c where sub1.depart_city = c.city_id) as depart_city,\n" +
                "       (select c.city_name from city c where sub1.arrive_city = c.city_id) as arrive_city,\n" +
                "       sub1.cnt                                                            as ticket_amount\n" +
                "from (\n" +
                "         select o.order_id, o.create_datetime, o.order_status, o.depart_city, o.arrive_city, count(*) as cnt\n" +
                "         from orders o\n" +
                "                  join order_user_ticket ot on o.order_id = ot.order_id\n" +
                "         where o.user_id = ?\n" +
                "         group by o.order_id\n" +
                "     ) sub1;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("id")).append("\t");
                sb.append(resultSet.getString("create_time")).append("\t");
                sb.append(resultSet.getString("order_status")).append("\t");
                sb.append(resultSet.getString("depart_city")).append("\t");
                sb.append(resultSet.getString("arrive_city")).append("\t");
                sb.append(resultSet.getString("ticket_amount")).append("\t");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    public String searchTicketInformationInAnOrder(int orderID) throws SQLException {
      //  System.out.println("序号\t车次\t类型\t出发站\t出发时间\t到达站\t到达时间\t座位类型\t票价\t乘车人\t车厢号\t座位号\t状态");
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select (select u.user_name from users u where u.user_id = sub1.user_id) as user_name,\n" +
                "       t.*\n" +
                "from ticket t\n" +
                "         join (\n" +
                "    select *\n" +
                "    from order_user_ticket\n" +
                "    where order_id = ?) sub1\n" +
                "              on sub1.ticket_id = t.ticket_id order by sub1.ticket_id;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, orderID);
            resultSet = preparedStatement.executeQuery();
            int count = 1;
            while (resultSet.next()) {
                sb.append(count).append("\t");
                String ticketBasicInfo = searchTicketTypeInformation(Integer.parseInt(resultSet.getString("ticket_type")));
                sb.append(ticketBasicInfo).append("\t");
                sb.append(resultSet.getString("user_name")).append("\t");
                sb.append(resultSet.getString("carriage_position")).append("\t");
                sb.append(resultSet.getString("seat_position")).append("\t");
                sb.append(getSeatStatus(orderID, Integer.parseInt(resultSet.getString("ticket_id")))+"\t");
         //       sb.append(System.lineSeparator());
                count++;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    private String searchTicketTypeInformation(int ticketTypeID) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        Map<String, String> seatTypeMap = new HashMap<>();
        seatTypeMap.put("second_class", "二等座");
        seatTypeMap.put("first_class", "一等座");
        seatTypeMap.put("business_class", "商务座");
        String sql = "select t.train_code                                                                       as train_code,\n" +
                "       t.train_type                                                                       as train_type,\n" +
                "       (select s1.station_name from station s1 where s1.station_id = sub1.depart_station) as depart_station,\n" +
                "       (select tas.departure_time\n" +
                "        from train_and_station tas\n" +
                "        where tas.train_id = sub1.train_id\n" +
                "          and tas.station_id = sub1.depart_station)                                       as depart_time,\n" +
                "       (select s2.station_name from station s2 where s2.station_id = sub1.arrive_station) as arrival_station,\n" +
                "       (select tas.arrival_time\n" +
                "        from train_and_station tas\n" +
                "        where tas.train_id = sub1.train_id\n" +
                "          and tas.station_id = sub1.arrive_station)                                       as arrival_time,\n" +
                "       sub1.seat_type                                                                     as seat_type,\n" +
                "       sub1.price                                                                         as price\n" +
                "from (\n" +
                "         select tt.ticket_type_id as ticket_type_id,\n" +
                "                tt.train_id       as train_id,\n" +
                "                tt.depart_station as depart_station,\n" +
                "                tt.arrive_station as arrive_station,\n" +
                "                tt.seat_type      as seat_type,\n" +
                "                tt.rest_num       as rest_num,\n" +
                "                tt.price          as price\n" +
                "         from ticket_type tt\n" +
                "         where tt.ticket_type_id = ?) sub1\n" +
                "         join train t on t.train_id = sub1.train_id;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, ticketTypeID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("train_code")).append("\t");
                sb.append(resultSet.getString("train_type")).append("\t");
                sb.append(resultSet.getString("depart_station")).append("\t");
                sb.append(resultSet.getString("depart_time")).append("\t");
                sb.append(resultSet.getString("arrival_station")).append("\t");
                sb.append(resultSet.getString("arrival_time")).append("\t");
                sb.append(seatTypeMap.get(resultSet.getString("seat_type"))).append("\t");
                sb.append(resultSet.getString("price"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public void buySomeTicketsByStation(int userID, String fromStation, String toStation) throws SQLException {
        int fromCity = getCityIDByStation(fromStation);        
        int toCIty = getCityIDByStation(toStation);       
        System.out.println(searchTicketFromOneStationToAnother(fromStation, toStation));  
        ArrayList<Integer> ticketTypeArr = getTicketFromOneStationToAnother(fromStation, toStation);       
        if(ticketTypeArr.size() == 0) {
            System.out.println("未查询到有效车票");
            return;
        }
        System.out.print("请输入您想购买的票的序号 (-1退出): ");
        int num = in.nextInt();
        in.nextLine();
        if(num == -1)
            return;
        int ticketType = ticketTypeArr.get(num - 1);
        int rest_num = getRestNumByTicketType(ticketType);
        System.out.print("请输入您想购买的张数: ");
        int number = in.nextInt();
        in.nextLine();
        if(number > rest_num) {
            System.out.println("购买数量超过剩余数量");
            return;
        }
        
        int orderID = createOneOrder(userID, fromCity, toCIty);
        System.out.println("请输入乘客信息（姓名/身份证号码: ");
        ArrayList<passengerInfo> passengerInfoArr = new ArrayList<>();
        for(int i=0; i<number; i++) {
            passengerInfoArr.add(new passengerInfo(in.next(), in.next()));
        }
        in.nextLine();
        ArrayList<Integer> ticketIDArr = generateSeatPosition(ticketType, number);
        System.out.println(getSeatPosition(passengerInfoArr, ticketIDArr));
        insertOrderInformation(orderID, ticketIDArr, passengerInfoArr);
        if(updateTicketRestNum(ticketType, number) != -1) {
            System.out.println("买票成功");
        } else {
            System.out.println("买票失败");
        }
    }

    @Override
    public void buySomeTicketsByCity(int userID, String fromCity, String toCity) throws SQLException {
        int fromCityID = getCityIDByName(fromCity);
        int toCItyID = getCityIDByStation(toCity);

        System.out.println(searchTicketFromOneCityToAnother(fromCity, toCity));
        ArrayList<Integer> ticketTypeArr = getTicketFromOneCityToAnother(fromCity, toCity);
        if(ticketTypeArr.size() == 0) {
            System.out.println("未查询到直达车票");
            String fromCapital = getCapitalOfProvinceByCityName(fromCity);
            String toCapital = getCapitalOfProvinceByCityName(toCity);
            ticketTypeArr = getTicketFromOneCityToAnother(fromCapital, toCity);
            if(ticketTypeArr.size() == 0) {
                ticketTypeArr = getTicketFromOneCityToAnother(fromCity, toCapital);
                if(ticketTypeArr.size() == 0) {
                    ticketTypeArr = getTicketFromOneCityToAnother(fromCapital, toCapital);
                    if(ticketTypeArr.size() == 0) {
                        System.out.println("未查询到有效车票");
                        return;
                    } else {
                        ticketTypeArr = getTicketFromOneCityToAnother(toCapital, toCity);
                        if(ticketTypeArr.size() == 0) {
                            System.out.println("未查询到有效车票");
                            return;
                        } else {
                            System.out.println("查询到有效路线: " + fromCity + " -> " + fromCapital + " -> " + toCapital + " -> " + toCity);
                            System.out.print("是否要购买该条线路的车票（是/否）: ");
                            String check = in.nextLine();
                            if(check.equals("是")) {
                                buySomeTicketsByCity(userID, fromCity, fromCapital);
                                buySomeTicketsByCity(userID, fromCapital, toCapital);
                                buySomeTicketsByCity(userID, toCapital, toCity);
                            }
                            return;
                        }
                    }
                } else {
                    ticketTypeArr = getTicketFromOneCityToAnother(toCapital, toCity);
                    if(ticketTypeArr.size() == 0) {
                        System.out.println("未查询到有效车票");
                        return;
                    } else {
                        System.out.println("查询到有效路线: " + fromCity + " -> " + toCapital + " -> " + toCity);
                        System.out.print("是否要购买该条线路的车票（是/否）: ");
                        String check = in.nextLine();
                        if(check.equals("是")) {
                            buySomeTicketsByCity(userID, fromCity, toCapital);
                            buySomeTicketsByCity(userID, toCapital, toCity);
                        }
                        return;
                    }
                }
            } else {
                ticketTypeArr = getTicketFromOneCityToAnother(fromCity, fromCapital);
                if(ticketTypeArr.size() == 0) {
                    System.out.println("未查询到有效车票");
                    return;
                } else {
                    System.out.println("查询到有效路线: " + fromCity + " -> " + fromCapital + " -> " + toCity);
                    System.out.print("是否要购买该条线路的车票（是/否）: ");
                    String check = in.nextLine();
                    if(check.equals("是")) {
                        buySomeTicketsByCity(userID, fromCity, fromCapital);
                        buySomeTicketsByCity(userID, fromCapital, toCity);
                    }
                    return;
                }
            }
        }
        System.out.print("请输入您想购买的票的序号 (-1退出): ");
        int num = in.nextInt();
        in.nextLine();
        if(num == -1)
            return;
        int ticketType = ticketTypeArr.get(num - 1);
        int rest_num = getRestNumByTicketType(ticketType);
        System.out.print("请输入您想购买的张数: ");
        int number = in.nextInt();
        in.nextLine();
        if(number > rest_num) {
            System.out.println("购买数量超过剩余数量");
            return;
        }
        
        int orderID = createOneOrder(userID, fromCityID, toCItyID);
        System.out.println("请输入乘客信息（姓名/身份证号码: ");
        ArrayList<passengerInfo> passengerInfoArr = new ArrayList<>();
        for(int i=0; i<number; i++) {
            passengerInfoArr.add(new passengerInfo(in.next(), in.next()));
        }
        in.nextLine();
        ArrayList<Integer> ticketIDArr = generateSeatPosition(ticketType, number);
        System.out.println(getSeatPosition(passengerInfoArr, ticketIDArr));
        insertOrderInformation(orderID, ticketIDArr, passengerInfoArr);
        System.out.println("订单关系表创建成功");
        if(updateTicketRestNum(ticketType, number) != -1) {
            System.out.println("买票成功");
        } else {
            System.out.println("买票失败");
        }
    }

    @Override
    public void returnSomeTickets(int userID) throws SQLException {
        System.out.println("---------- 当前用户的订单如下: ----------");
        System.out.print(searchOrderInformation(userID));
        ArrayList<Integer> orderIDArr = getOrderInformation(userID);
        System.out.print("请选择要退票的订单id: ");
        int orderID = orderIDArr.get(in.nextInt() - 1);
        in.nextLine();
        System.out.println("---------- 该订单下所有车票如下: ----------");
        System.out.print(searchTicketInformationInAnOrder(orderID));
        System.out.print("取消订单（1）还是退部分票（2）: ");
        int situation = in.nextInt();
        in.nextLine();
        if(situation == 1) {
            cancelWholeOrder(orderID);
            System.out.println("已取消该订单");
        } else if(situation == 2) {
            System.out.print("请输入你想取消的票的序号: ");
            String str = in.nextLine();
            String[] index = str.split(" ");
            ArrayList<Integer> indexArr = new ArrayList<>();
            for(int i=0; i<index.length; i++){
                indexArr.add(Integer.parseInt(index[i]) - 1);
            }
            cancelSomeTickets(orderID, indexArr);
            System.out.println("退票成功");
            if(!hasValidTicket(orderID)) {
                System.out.println("该订单下所有车票均已取消");
                updateOrderStatus(orderID, "已取消");
            }
        }
    }

    @Override
    public void userRegister(String username, String id, String phone_number, String account, String password) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "insert into users (user_name, id_card_num, phone_number, account, password, administrator) values (''||?||'', ''||?||'', ''||?||'', ''||?||'', ''||?||'', ''||?||'');";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, phone_number);
            preparedStatement.setString(4, account);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, "N");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    @Override
    public boolean checkExistingAccount(String account) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        String sql = "select count(*) from users where account = ''||?||'';";
        int number = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, account);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number = Integer.parseInt(resultSet.getString("count"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return number == 0;
    }

    @Override
    public String getPasswordByAccount(String account) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select password from users where account = ''||?||'';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, account);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String getPasswordByUserID(int userID) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select password from users where user_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String getUserID(String account) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select user_id from users where account = ''||?||'';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, account);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("user_id"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public String getUserInformation(int user_id) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select * from users where user_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {               
                sb.append(resultSet.getString("account")).append("\t");
                sb.append(resultSet.getString("password")).append("\t");
                sb.append(resultSet.getString("user_name")).append("\t");
                sb.append(resultSet.getString("phone_number")).append("\t");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public void updateUserInfo(int user_id, String column, String info) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "update users set " + column + " = ''||?||'' where user_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, info);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    @Override
    public String insertTrainBasicInfo(String[] basicInfo) throws SQLException {
        Connection con = cp.getConnection();
        String train_code = basicInfo[0];
        String train_type = basicInfo[1];
        String start_station = basicInfo[2];
        String end_station = basicInfo[3];
        String total_time = basicInfo[4];
        String total_mile = basicInfo[5];
        String sql = "insert into train (train_id, train_code, train_type, start_station, end_station, total_time, total_mile)\n" +
                "values ((select count(*) from train) + 1, ''||?||'', ''||?||'', ?, ?, ''||?||'', ''||?||'');";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, train_code);
            preparedStatement.setString(2, train_type);
            preparedStatement.setInt(3, getStationIDByName(start_station));
            preparedStatement.setInt(4, getStationIDByName(end_station));
            preparedStatement.setString(5, total_time);
            preparedStatement.setString(6, total_mile);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return searchTrainBasicInformation(train_code);
    }

    @Override
    public String insertTrainDetailInfo(String train_code, String[][] detailInfo) throws SQLException {
        Connection con = cp.getConnection();
        int train_id = getTrainIDByCode(train_code);
        String sql = "insert into train_and_station (train_id, station_id, passing_order, arrival_time, departure_time, total_time, miles,\n" +
                "                               former_station, latter_station)\n" +
                "values (?, ?, ?, ''||?||'', ''||?||'', ''||?||'', ''||?||'', ?, ?);";
        try {
            for(int i=0; i<detailInfo.length; i++) {
                int passing_order = Integer.parseInt(detailInfo[i][0]);
                String station_name = detailInfo[i][1];
                String arrival_time = detailInfo[i][2];
                String departure_time = detailInfo[i][3];
                String total_time = detailInfo[i][4];
                String total_mile = detailInfo[i][5];
                String former = detailInfo[i][6].equals("-") ? null : detailInfo[i][6];
                int former_id = getStationIDByName(former);
                String latter = detailInfo[i][7].equals("-") ? null : detailInfo[i][7];
                int latter_id = getStationIDByName(latter);
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, train_id);
                preparedStatement.setInt(2, getStationIDByName(station_name));
                preparedStatement.setInt(3, passing_order);
                preparedStatement.setString(4, arrival_time);
                preparedStatement.setString(5, departure_time);
                preparedStatement.setString(6, total_time);
                preparedStatement.setString(7, total_mile);
                if(former_id != 0)
                    preparedStatement.setInt(8, getStationIDByName(former));
                else
                    preparedStatement.setNull(8, Types.INTEGER);
                if(latter_id != 0)
                    preparedStatement.setInt(9, getStationIDByName(latter));
                else
                    preparedStatement.setNull(9, Types.INTEGER);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        insertTicketType(train_id, detailInfo);
        return searchTrainDetailInformation(train_code);
    }

    @Override
    public void deleteTrain(String train_code) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "update train set alive = 'N' where train_code = ''||?||''";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, train_code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    @Override
    public boolean checkExistingStation(String stationName, String cityName) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        String sql = "select count(*) from station where station_name = ''||?||'' and city_id = (select city_id from city where city_name = ''||?||'');";
        int number = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, stationName);
            preparedStatement.setString(2, cityName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number = Integer.parseInt(resultSet.getString("count"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return number == 0;
    }

    private void insertTicketType(int train_id, String[][] detailInfo) throws SQLException {
        ArrayList<ticketType> arr = new ArrayList<>();
        for(int i=0; i<detailInfo.length; i++) {
            String depart_station = detailInfo[i][1];
            int beginDistance = Integer.parseInt(detailInfo[i][5]);
            for(int j=i+1; j<detailInfo.length; j++) {
                String arrive_station = detailInfo[j][1];
                int endDistance = Integer.parseInt(detailInfo[j][5]);
                ticketType temp = new ticketType();
                temp.train_id = train_id;
                temp.depart_station = getStationIDByName(depart_station);
                temp.arrive_station = getStationIDByName(arrive_station);
                temp.price = 0.46 * (endDistance - beginDistance);
                arr.add(temp);
            }
        }
        Connection con = cp.getConnection();
        String sql = "insert into ticket_type (ticket_type_id, train_id, dates, seat_type, depart_station, arrive_station, rest_num, price)\n" +
                "values (?, ?, ?, ''||?||'', ?, ?, ?, ?);";
        ArrayList<ticket> tarr = new ArrayList<>();
        try {
            String[] seatType = {"business_class", "first_class", "second_class"};
            int[] restNum = {50, 100, 500};
            double[] multi = {3, 1.5, 1};
            for(int i=0; i<arr.size(); i++) {
                ticketType temp = arr.get(i);
                for(int j=0; j<3; j++) {
                    ticket t = new ticket();
                    int ticketTypeCount = getCountOfTicketType();
                    t.ticketType = ticketTypeCount;
                    t.seat_type = seatType[j];
                    tarr.add(t);
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setInt(1, ticketTypeCount);
                    preparedStatement.setInt(2, temp.train_id);
                    preparedStatement.setDate(3, Date.valueOf("2020-01-01"));
                    preparedStatement.setString(4, seatType[j]);
                    preparedStatement.setInt(5, temp.depart_station);
                    preparedStatement.setInt(6, temp.arrive_station);
                    preparedStatement.setInt(7, restNum[j]);
                    preparedStatement.setDouble(8, temp.price * multi[j]);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        insertTicket(tarr);
    }

    private void insertTicket(ArrayList<ticket> tarr) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "insert into ticket (ticket_type, carriage_position, seat_position, available)\n" +
                "values (?, ?, ''||?||'', ''||?||'');";
        String[] business = {"A", "C", "F"};
        String[] first = {"A", "C", "D", "F"};
        String[] second = {"A", "B", "C", "D", "F"};
        try {
            for(int j=0; j<tarr.size(); j++) {
                ticket t = tarr.get(j);
                if(t.seat_type.equals("business_class")) {
                    int row = 0;
                    for(int i=0; i<50; i++) {
                        if(i%3 == 0) {
                            row++;
                        }
                        PreparedStatement preparedStatement = con.prepareStatement(sql);
                        preparedStatement.setInt(1, t.ticketType);
                        preparedStatement.setInt(2, 1);
                        preparedStatement.setString(3, row + business[i%3]);
                        preparedStatement.setString(4, "Y");
                        preparedStatement.executeUpdate();
                    }
                } else if(t.seat_type.equals("first_class")) {
                    int row = 0;
                    for(int i=0; i<100; i++) {
                        if(i%4 == 0) {
                            row++;
                        }
                        PreparedStatement preparedStatement = con.prepareStatement(sql);
                        preparedStatement.setInt(1, t.ticketType);
                        preparedStatement.setInt(2, 2);
                        preparedStatement.setString(3, row + first[i%4]);
                        preparedStatement.setString(4, "Y");
                        preparedStatement.executeUpdate();
                    }
                } else {
                    for(int k=0; k<4; k++){
                        int row = 0;
                        for(int i=0; i<50; i++) {
                            if(i%5 == 0) {
                                row++;
                            }
                            PreparedStatement preparedStatement = con.prepareStatement(sql);
                            preparedStatement.setInt(1, t.ticketType);
                            preparedStatement.setInt(2, 3+k);
                            preparedStatement.setString(3, row + second[i%5]);
                            preparedStatement.setString(4, "Y");
                            preparedStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    private int getCountOfTicketType() throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        int count = 0;
        String sql = "select count(*) + 1 as count from ticket_type;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = Integer.parseInt(resultSet.getString("count"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return count;
    }

    @Override
    public int getStationIDByName(String stationName) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        int id = 0;
        String sql = "select station_id from station where station_name = ''||?||'';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, stationName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("station_id"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return id;
    }

    @Override
    public String getStationInformation(int stationID) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select sub1.station_name,\n" +
                "       (select c.city_name from city c where c.city_id = sub1.city_id) as city_name\n" +
                "from (\n" +
                "         select *\n" +
                "         from station\n" +
                "         where station_id = ?) sub1;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, stationID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append("1.站名: ").append(resultSet.getString("station_name")).append("\n");
                sb.append("2.所在城市: ").append(resultSet.getString("city_name")).append("\n");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    @Override
    public void updateStationInfo(int stationID, String column, String info) throws SQLException {
        Connection con = cp.getConnection();
        try {
            if(column.equals("station_name")) {
                String sql = "update station set " + column + " = ''||?||'' where station_id = ?;";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, info);
                preparedStatement.setInt(2, stationID);
                preparedStatement.executeUpdate();
            } else {
                String sql = "update station set " + column + " = ? where station_id = ?;";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, getCityIDByName(info));
                preparedStatement.setInt(2, stationID);
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    @Override
    public String createNewStation(String stationName, String cityName) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "insert into station (station_id, station_name, city_id)\n" +
                "values ((select count(*) from station) + 1, ''||?||'', (select city_id from city where city_name = ''||?||''));";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, stationName);
            preparedStatement.setString(2, cityName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return getStationInformation(getStationIDByName(stationName));
    }

    @Override
    public void deleteStation(String stationName) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "update station set alive = 'N' where station_name = ''||?||''";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, stationName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    @Override
    public boolean checkExistingTrain(String train_code) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        String sql = "select count(*) from train where train_code = ''||?||'';";
        int number = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, train_code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number = Integer.parseInt(resultSet.getString("count"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return number == 0;
    }

    @Override
    public void updateTrainBasicInfo(String train_code, String column, String info) throws SQLException {
        Connection con = cp.getConnection();
        int train_id = getTrainIDByCode(train_code);
        String sql = "update train set " + column +" = ''||?||'' where train_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, info);
            preparedStatement.setInt(2, train_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    @Override
    public void updateTrainDetailInfo(String train_code, String stationName, String column, String info) throws SQLException {
        Connection con = cp.getConnection();
        int train_id = getTrainIDByCode(train_code);
        int station_id = getStationIDByName(stationName);
        String sql = "update train_and_station set " + column +" = ''||?||'' where train_id = ? and station_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, info);
            preparedStatement.setInt(2, train_id);
            preparedStatement.setInt(3, station_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    @Override
    public boolean isAdministrator(int user_id) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        String sql = "select administrator from users where user_id = ?;";
        String role = "";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                role = resultSet.getString("administrator");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return role.equals("Y");
    }

    @Override
    public boolean isTrainAlive(String train_code) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        String sql = "select alive from train where train_code = ''||?||'';";
        String role = "";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, train_code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                role = resultSet.getString("alive");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return role.equals("Y");
    }

    @Override
    public boolean isStationAlive(String stationName) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        String sql = "select alive from station where station_name = ''||?||'';";
        String role = "";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, stationName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                role = resultSet.getString("alive");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return role.equals("Y");
    }

    private int getCityIDByName(String cityName) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        int id = 0;
        String sql = "select city_id from city where city_name = ''||?||'';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, cityName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("city_id"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return id;
    }

    public ArrayList<Integer> getOrderInformation(int userID) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        ArrayList<Integer> orderIDArr = new ArrayList<>();
        String sql = "select row_number() over (order by sub1.create_datetime)                   as id,\n" +
                "       sub1.order_id                                                       as order_id,\n" +
                "       sub1.create_datetime                                                as create_time,\n" +
                "       sub1.order_status                                                   as order_status,\n" +
                "       (select c.city_name from city c where sub1.depart_city = c.city_id) as depart_city,\n" +
                "       (select c.city_name from city c where sub1.arrive_city = c.city_id) as arrive_city,\n" +
                "       sub1.cnt                                                            as ticket_amount\n" +
                "from (\n" +
                "         select o.order_id, o.create_datetime, o.order_status, o.depart_city, o.arrive_city, count(*) as cnt\n" +
                "         from orders o\n" +
                "                  join order_user_ticket ot on o.order_id = ot.order_id\n" +
                "         where o.user_id = ?\n" +
                "         group by o.order_id\n" +
                "     ) sub1;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderIDArr.add(Integer.parseInt(resultSet.getString("order_id")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return orderIDArr;
    }

    public void cancelWholeOrder(int orderID) throws SQLException {
        ArrayList<Integer> ticketIDArr = getTicketIDInAnOrder(orderID);
        ArrayList<Integer> ticketTypeIDArr = getTicketTypeIDByTicketID(ticketIDArr);
        updateOrderStatus(orderID, "已取消");
        updateOrderUserTicketStatus(orderID, "已取消");
        for(int i=0; i<ticketTypeIDArr.size(); i++) {
            updateSeatStatus(ticketIDArr.get(i), "Y");
            updateTicketRestNum(ticketTypeIDArr.get(i), -1);
        }
    }

    private void cancelSomeTickets(int orderID, ArrayList<Integer> indexArr) throws SQLException {
        ArrayList<Integer> ticketIDArr = getTicketIDInAnOrder(orderID);
        ArrayList<Integer> ticketTypeIDArr = getTicketTypeIDByTicketID(ticketIDArr);
        for(int i=0; i<indexArr.size(); i++) {
            updateSeatStatus(ticketIDArr.get(indexArr.get(i)), "Y");
            updateOrderUserTicketStatus(orderID, ticketIDArr.get(indexArr.get(i)), "已取消");
            updateTicketRestNum(ticketTypeIDArr.get(indexArr.get(i)), -1);
        }
    }
    
    public void canceloneTickets(int orderID, int index) throws SQLException {
        ArrayList<Integer> ticketIDArr = getTicketIDInAnOrder(orderID);
        ArrayList<Integer> ticketTypeIDArr = getTicketTypeIDByTicketID(ticketIDArr);       
        updateSeatStatus(ticketIDArr.get(index), "Y");
        updateOrderUserTicketStatus(orderID, ticketIDArr.get(index), "已取消");
        updateTicketRestNum(ticketTypeIDArr.get(index), -1);
    }

    private ArrayList<Integer> getTicketIDInAnOrder(int orderID) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        ArrayList<Integer> ticketIDArr = new ArrayList<>();
        String sql = "select *\n" +
                "from ticket\n" +
                "where ticket_id in (select ticket_id\n" +
                "                    from order_user_ticket\n" +
                "                    where order_id = ?)\n" +
                "order by ticket_id;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, orderID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ticketIDArr.add(Integer.parseInt(resultSet.getString("ticket_id")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return ticketIDArr;
    }

    private ArrayList<Integer> getTicketTypeIDByTicketID(ArrayList<Integer> ticketIDArr) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        ArrayList<Integer> ticketTypeID = new ArrayList<>();
        String sql = "select ticket_type from ticket where ticket_id = ?;";
        try {
            for(int i=0; i<ticketIDArr.size(); i++) {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, ticketIDArr.get(i));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    ticketTypeID.add(Integer.parseInt(resultSet.getString("ticket_type")));
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return ticketTypeID;
    }

    private boolean hasValidTicket(int orderID) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        int number = 0;
        String sql = "select count(*) from order_user_ticket where order_id = ? and status != '已取�?';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, orderID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number = Integer.parseInt(resultSet.getString("count"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return number != 0;
    }

    public int createOneOrder(int userID, int fromCity, int toCity) throws SQLException {
        Connection con = cp.getConnection();
        Timestamp currentTime = getCurrentTimestamp();
        String orderStatus = "进行中";
        String sql = "insert into orders (user_id, create_datetime, order_status, depart_city, arrive_city) values (?, ?, ''||?||'', ?, ?);";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.setTimestamp(2, currentTime);
            preparedStatement.setString(3, orderStatus);
            preparedStatement.setInt(4, fromCity);
            preparedStatement.setInt(5, toCity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return getOrderID(userID, currentTime);
    }

    public void insertOrderInformation(int orderID, ArrayList<Integer> ticketID, ArrayList<passengerInfo> passengerInfoArr) throws SQLException {
        Connection con = cp.getConnection();
        String orderStatus = "进行中";
        String sql = "insert into order_user_ticket (order_id, ticket_id, user_id, status) values (?, ?, ?, '进行中');";
        try {
            for(int i=0; i<ticketID.size(); i++) {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, orderID);
                preparedStatement.setInt(2, ticketID.get(i));
                preparedStatement.setInt(3, getPassengerID(passengerInfoArr.get(i)));
       //         preparedStatement.setString(4, orderStatus);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    private int getPassengerID(passengerInfo info) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        String sql = "select user_id from users where user_name = ''||?||'' and id_card_num = ''||?||'';";
        int id = -1;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, info.name);
            preparedStatement.setString(2, info.ID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("user_id"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return id;
    }

    private int getOrderID(int userID, Timestamp t) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        int id = -1;
        String sql = "select order_id\n" +
                "from orders\n" +
                "where user_id = ? and create_datetime = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.setTimestamp(2, t);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("order_id"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return id;
    }

    public int getCityIDByStation(String stationName) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        int id = -1;
        String sql = "select c.city_id\n" +
                "from city c\n" +
                "    join station s on c.city_id = s.city_id\n" +
                "where s.station_name = ''||?||'';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, stationName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("city_id"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return id;
    }

    private Timestamp getCurrentTimestamp() throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        Timestamp ts = null;
        String sql = "select current_timestamp as t;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ts = resultSet.getTimestamp("t");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return ts;
    }

    public ArrayList<Integer> getTicketFromOneStationToAnother(String fromStation, String toStation) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        ArrayList<Integer> arr = new ArrayList<>();
        String sqlBasicInfo = "select rank() over (order by sub2.price, arrival_time, train_code), sub2.*\n" +
                "from (\n" +
                "         select sub1.ticket_type_id                                                                as ticket_type_id,\n" +
                "                t.train_code                                                                       as train_code,\n" +
                "                t.train_type                                                                       as train_type,\n" +
                "                (select s1.station_name from station s1 where s1.station_id = sub1.depart_station) as depart_station,\n" +
                "                (select tas.departure_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.depart_station)                                       as depart_time,\n" +
                "                (select s2.station_name from station s2 where s2.station_id = sub1.arrive_station) as arrival_station,\n" +
                "                (select tas.arrival_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.arrive_station)                                       as arrival_time,\n" +
                "                sub1.seat_type                                                                     as seat_type,\n" +
                "                sub1.rest_num                                                                      as rest_num,\n" +
                "                sub1.price                                                                         as price\n" +
                "         from (\n" +
                "                  select tt.ticket_type_id as ticket_type_id,\n" +
                "                         tt.train_id       as train_id,\n" +
                "                         tt.depart_station as depart_station,\n" +
                "                         tt.arrive_station as arrive_station,\n" +
                "                         tt.seat_type      as seat_type,\n" +
                "                         tt.rest_num       as rest_num,\n" +
                "                         tt.price          as price\n" +
                "                  from ticket_type tt\n" +
                "                  where tt.depart_station = (select s1.station_id as from_id\n" +
                "                                             from station s1\n" +
                "                                             where s1.station_name = ''||?||'')\n" +
                "                    and tt.arrive_station = (select s2.station_id as to_id\n" +
                "                                             from station s2\n" +
                "                                             where s2.station_name = ''||?||'')\n" +
                "                    and tt.price != 0) sub1\n" +
                "                  join train t on t.train_id = sub1.train_id) sub2;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sqlBasicInfo);
            preparedStatement.setString(1, fromStation);
            preparedStatement.setString(2, toStation);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                arr.add(Integer.parseInt(resultSet.getString("ticket_type_id")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return arr;
    }

    public ArrayList<Integer> getTicketFromOneCityToAnother(String fromCity, String toCity) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        ArrayList<Integer> arr = new ArrayList<>();
        String sqlBasicInfo = "select rank() over (order by sub2.price, arrival_time, train_code), sub2.*\n" +
                "from (\n" +
                "         select sub1.ticket_type_id                                                                as ticket_type_id,\n" +
                "                t.train_code                                                                       as train_code,\n" +
                "                t.train_type                                                                       as train_type,\n" +
                "                (select s1.station_name from station s1 where s1.station_id = sub1.depart_station) as depart_station,\n" +
                "                (select tas.departure_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.depart_station)                                       as depart_time,\n" +
                "                (select s2.station_name from station s2 where s2.station_id = sub1.arrive_station) as arrival_station,\n" +
                "                (select tas.arrival_time\n" +
                "                 from train_and_station tas\n" +
                "                 where tas.train_id = sub1.train_id\n" +
                "                   and tas.station_id = sub1.arrive_station)                                       as arrival_time,\n" +
                "                sub1.seat_type                                                                     as seat_type,\n" +
                "                sub1.rest_num                                                                      as rest_num,\n" +
                "                sub1.price                                                                         as price\n" +
                "         from (\n" +
                "                  select tt.ticket_type_id as ticket_type_id,\n" +
                "                         tt.train_id       as train_id,\n" +
                "                         tt.depart_station as depart_station,\n" +
                "                         tt.arrive_station as arrive_station,\n" +
                "                         tt.seat_type      as seat_type,\n" +
                "                         tt.rest_num       as rest_num,\n" +
                "                         tt.price          as price\n" +
                "                  from ticket_type tt\n" +
                "                  where tt.depart_station in (select s1.station_id as from_id\n" +
                "                                              from station s1\n" +
                "                                              where s1.station_name in (select station_name\n" +
                "                                                                        from station\n" +
                "                                                                        where city_id = (select city_id from city where city_name = ''||?||'')))\n" +
                "                    and tt.arrive_station in (select s2.station_id as to_id\n" +
                "                                              from station s2\n" +
                "                                              where s2.station_name in (select station_name\n" +
                "                                                                        from station\n" +
                "                                                                        where city_id = (select city_id from city where city_name = ''||?||'')))\n" +
                "                    and tt.price != 0) sub1\n" +
                "                  join train t on t.train_id = sub1.train_id) sub2;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sqlBasicInfo);
            preparedStatement.setString(1, fromCity);
            preparedStatement.setString(2, toCity);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                arr.add(Integer.parseInt(resultSet.getString("ticket_type_id")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return arr;
    }

    private String getSeatStatus(int orderID, int ticketID) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select status from order_user_ticket where order_id = ? and ticket_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, ticketID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("status"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }

    public String getSeatPosition(ArrayList<passengerInfo> passengerInfoArr, ArrayList<Integer> ticketID) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("已为您分配了如下座位:");
        sb.append("姓名\t车厢号\t座位号");
        Connection con = cp.getConnection();
        ResultSet resultSet;
        
        String sql = "select carriage_position, seat_position from ticket where ticket_id = ?;";
        
        try {
            for(int i=0; i<ticketID.size(); i++) {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, ticketID.get(i));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    sb.append(passengerInfoArr.get(i).name).append("\t");
                    sb.append(resultSet.getString("carriage_position")).append("\t");
                    sb.append(resultSet.getString("seat_position")).append("\t");
                    sb.append(System.lineSeparator());
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        for(int i=0; i<ticketID.size(); i++) {
            updateSeatStatus(ticketID.get(i), "N");
        }
        return sb.toString();
    }

    private void updateSeatStatus(int ticketID, String status) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "update ticket set available = ''||?||'' where ticket_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, ticketID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    private void updateOrderUserTicketStatus(int orderID, String status) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "update order_user_ticket set status = ''||?||'' where order_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    private void updateOrderUserTicketStatus(int orderID, int ticketID, String status) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "update order_user_ticket set status = ''||?||'' where order_id = ? and ticket_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderID);
            preparedStatement.setInt(3, ticketID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    private void updateOrderStatus(int orderID, String status) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "update orders set order_status = ''||?||'' where order_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
    }

    public int updateTicketRestNum(int ticketType, int number) throws SQLException {
        Connection con = cp.getConnection();
        String sql = "update ticket_type set rest_num = rest_num - ? where ticket_type_id = ?;";
        int affectRow = -1;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, number);
            preparedStatement.setInt(2, ticketType);
            affectRow = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return affectRow;
    }

    public ArrayList<Integer> generateSeatPosition(int ticketType, int number) throws SQLException {
    	System.out.println("tickettype is "+ticketType+",ticketnum is "+number);
        Connection con = cp.getConnection();
        ResultSet resultSet;
        ArrayList<Integer> ticketID = new ArrayList<>();
        String sql = "select *\n" +
                "from ticket\n" +
                "where ticket_type = ? and available = 'Y'\n" +
                "order by ticket_id\n" +
                "limit ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, ticketType);
            preparedStatement.setInt(2, number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ticketID.add(Integer.parseInt(resultSet.getString("ticket_id")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return ticketID;
    }

    private int getTrainIDByCode(String train_code) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        int id = 0;
        String sql = "select train_id from train where train_code = ''||?||''";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, train_code);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("train_id"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return id;
    }

    public String getCapitalOfProvinceByCityName(String cityName) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        StringBuilder sb = new StringBuilder();
        String sql = "select capital\n" +
                "from province_capital\n" +
                "         join city c on province_capital.province_name = c.province_name\n" +
                "where c.city_name = ''||?||'';";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, cityName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("capital"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return sb.toString();
    }
    
    private int getRestNumByTicketType(int TicketType) throws SQLException {
        Connection con = cp.getConnection();
        ResultSet resultSet;
        int num = 0;
        String sql = "select rest_num from ticket_type where ticket_type_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, TicketType);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                num = Integer.parseInt(resultSet.getString("rest_num"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            cp.releaseConnection(con);
        }
        return num;
    }

}

class ticketType {
    int train_id;
    String seat_type;
    int depart_station;
    int arrive_station;
    int rest_num;
    double price;
}

class ticket {
    int ticketType;
    String seat_type;
}

