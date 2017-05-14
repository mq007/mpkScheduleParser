package mpkParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final static String DBURL2 = "jdbc:mysql://127.0.0.1:3306/mpk?serverTimezone=UTC";
    private final static String DBUSER2 = "root";
    private final static String DBPASS = "ZaQ1XsW2";
    private static Connection connection = null;

    public static Connection getConenction(){
        try {
            connection = DriverManager.getConnection(DBURL2, DBUSER2, DBPASS);
            if(connection != null){
                //System.out.println("Connected to the datebase");
                return connection;
            }
        } catch ( SQLException e) {
            System.out.println("Problem with database");
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
