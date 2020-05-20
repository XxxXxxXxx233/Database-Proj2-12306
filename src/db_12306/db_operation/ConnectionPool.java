package db_12306.db_operation;

//import java.io.InputStream;
import java.util.*;
import java.sql.*;

public class ConnectionPool implements BasicConnectionPool{

    private String driver;
    private String url;
    private String username;
    private String password;
    private int poolInitSize;
    private int maxActive;
    private LinkedList<Connection> freeConnections = new LinkedList<>();
    private LinkedList<Connection> usedConnections = new LinkedList<>();

    public ConnectionPool(){
        initConnectionPool();
    }

    private void initConnectionPool() {
        try {
            driver = "org.postgresql.Driver";
            url = "jdbc:postgresql://localhost:5432/db_project3";
            username = "postgres";
            password = "liqilong1229";
            poolInitSize =10;
            maxActive = 100;
            Class.forName(driver);
            for(int i=0; i<poolInitSize; i++)
            {
                Connection con = createConnection(url, username, password);
                freeConnections.add(con);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(freeConnections.isEmpty()) {
            if(usedConnections.size() < maxActive) {
                freeConnections.add(createConnection(url, username, password));
            } else {
                throw new RuntimeException("Max pool size, no available connections now!");
            }
        }
        Connection con = freeConnections.removeFirst();
        usedConnections.add(con);
        return con;
    }

    @Override
    public boolean releaseConnection(Connection con) {
        freeConnections.add(con);
        return usedConnections.remove(con);
    }

    public void shutDown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for(Connection c : freeConnections) {
            c.close();
        }
        freeConnections.clear();
    }

    public int getSize() {
        return freeConnections.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
