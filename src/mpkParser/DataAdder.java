package mpkParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by MQ on 2017-05-13.
 */
public class DataAdder {
    private DataAdder(){};

    public static void insertIntoQuery(String tableID, String values){
        Connection connection = DatabaseConnection.getConenction();
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO `" + tableID + "` VALUES " + values + ";";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection();
        }
    }


    public static void truncateTable(String tableID){
        Connection connection = DatabaseConnection.getConenction();
        try {
            Statement statement = connection.createStatement();
            String query = "TRUNCATE TABLE " + tableID;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }
    }
}
