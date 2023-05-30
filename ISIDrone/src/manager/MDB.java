package manager;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MDB {

    private static Connection connection = null;
    private static InputStream inputStream;
    private static String dbName, dbIp, dbUserName, dbPassword;

    public static void feedDbProterties() {
        try {
            Properties prop = new Properties();
            String propFileName = "configs.properties";
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            // get the property value and print it out
            dbName = prop.getProperty("DB_NAME");
            dbIp = prop.getProperty("DB_IP");
            dbUserName = prop.getProperty("DB_USERNAME");
            dbPassword = prop.getProperty("DB_PASSWORD");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public static void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String mysqlURL2 = String.format("jdbc:mysql://%s:3306/%s?serverTimezone=UTC", dbIp, dbName);
            System.out.println(mysqlURL2);
            connection = DriverManager.getConnection(mysqlURL2, dbUserName, dbPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet execQuery(String query) {
        PreparedStatement ps = getPS(query);
        ResultSet rs = null;
        try {
            if (ps != null) {
                ps.execute();
                rs = ps.getResultSet();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static PreparedStatement getPS(String query) {
        PreparedStatement ps = null;
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            ps = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public static PreparedStatement getPS(String query, int id) {
        PreparedStatement ps = null;
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            if (id == 1) {
                ps = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public static PreparedStatement getPS(String query, String column) {
        return getPS(query, new String[]{column});
    }

    public static PreparedStatement getPS(String query, String[] columns) {
        PreparedStatement ps = null;
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            ps = connection.prepareStatement(query, columns);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = null;
        }
    }
}
