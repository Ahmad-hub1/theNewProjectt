package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

	public class DBConnection {
    private Connection connection;
    private static DBConnection dbconnection;
    private static final String dbName = "DMA-CSD-S241_10519155";
    private static final String serverAddress = "hildur.ucn.dk";
    private static final String serverPort = "1433";
    private static final String userName = "DMA-CSD-S241_10519155";
    private static final String password = "Password1!";

    private DBConnection() { //constructor
        String connectionString = String.format(
            "jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s;encrypt=false",
            serverAddress, serverPort, dbName, userName, password);

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionString);

            if (connection != null) {
                System.out.println("Forbundet til database: " + dbName);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load JDBC driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Could not connect to database");
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (dbconnection == null) {
            dbconnection = new DBConnection();
        }
        return dbconnection;
    }

    public Connection getConnection() {
        return connection;
    }
}