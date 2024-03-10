import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static String DB_URL = "jdbc:mysql://"+ Config.IP_SERVER +":" + Config.PORT_DB + "/" + Config.NAME_DB;
    private static String USER_NAME = Config.USER_DB;
    private static String PASSWORD = Config.PASSWORD_DB;

    public Connection getConnect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
            System.out.println(">> Connect successfully!");
        } catch (SQLException e) {
            System.out.println(">> Connect failure!");
            e.printStackTrace();
        }
        return connection;
    }
}
