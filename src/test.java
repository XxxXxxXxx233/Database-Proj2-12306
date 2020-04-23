import java.sql.*;

public class test {
    public static void main(String[] args) throws SQLException {
        ConnectionPool cp = new ConnectionPool();
        System.out.println(findTrain(cp.getConnection(), "深圳北", "上海虹桥"));
        System.out.println(findTrain(cp.getConnection(), "深圳北", "广州南"));
    }
    public static String findTrain(Connection con, String from, String to) throws SQLException {
        String sql = "select tt.*\n" +
                "from ticket_type tt\n" +
                "    join station s on tt.arrive_station = s.station_id or tt.depart_station = s.station_id\n" +
                "where tt.depart_station = (select station_id from station s1 where station_name = ''||?||'')\n" +
                "and tt.arrive_station = (select station_id from station s2 where station_name = ''||?||'')\n" +
                "and tt.seat_type = 'first_class';";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, from);
        preparedStatement.setString(2, to);
        ResultSet resultSet = preparedStatement.executeQuery();
        StringBuilder sb = new StringBuilder();
        while(resultSet.next())
        {
            sb.append(resultSet.getString("ticket_type_id") + "\t");
            sb.append(resultSet.getString("train_id") + "\t");
            sb.append(resultSet.getString("dates") + "\t");
            sb.append(resultSet.getString("seat_type") + "\t");
            sb.append(resultSet.getString("depart_station") + "\t");
            sb.append(resultSet.getString("arrive_station") + "\t");
            sb.append(resultSet.getString("rest_num") + "\t");
            sb.append(resultSet.getString("price") + "\t");
            sb.append(System.lineSeparator());
        }
        System.out.println("Searched by: " + con);
        return sb.toString();
    }
}
