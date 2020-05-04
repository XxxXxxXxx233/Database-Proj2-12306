import java.sql.SQLException;

public interface DataModification {
    void userRegister() throws SQLException;
    void userLogIn() throws SQLException;
    void userLogOut() throws SQLException;
    void userUpdate() throws SQLException;

    void trainInsert() throws SQLException;
    void trainUpdate() throws SQLException;

    void stationInsert() throws SQLException;
    void stationUpdate() throws SQLException;

}
