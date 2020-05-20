package db_12306.db_operation;

import java.sql.Connection;
import java.sql.SQLException;

public interface BasicConnectionPool {
    Connection getConnection() throws SQLException;
    boolean releaseConnection(Connection con);
    String getUrl();
    String getUser();
    String getPassword();
}
