package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.java2d.pipe.RenderingEngine;

public class MDB {
	private static Connection connection = null;
        private static InputStream inputStream;
        private static 

        public static void feedDbProterties()  {
        try {
            Properties prop = new Properties();
            String propFileName = "configs.properties";
            inputStream = ClassLoader.class.getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            // get the property value and print it out
            String DB_NAME = prop.getProperty("DB_NAME");
            String DB_IP = prop.getProperty("DB_IP");
            String DB_USERNAME = prop.getProperty("DB_USERNAME");
            String DB_PASSWORD = prop.getProperty("DB_PASSWORD");
            String result = "DB PROP = " + DB_NAME + ", " + DB_IP + ", " + DB_USERNAME + ", " + DB_PASSWORD;
            System.out.println(result );
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } 
        }
	
	public static void connect() throws SQLException {
                feedDbProterties();
               System.out.println(new File(".").getAbsolutePath());

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String mysqlURL="jdbc:mysql://127.0.0.1:3306/isidrone?serverTimezone=UTC";
			connection = DriverManager.getConnection(mysqlURL, "root", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet execQuery(String query) {
		PreparedStatement ps = getPS(query);
		ResultSet rs = null;
		try {
			if(ps != null) {
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
			if(connection == null || connection.isClosed())
				connect();
			ps = connection.prepareStatement(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}
	
	public static PreparedStatement getPS(String query, int id) {
		PreparedStatement ps = null;
		try { 
			if(connection == null || connection.isClosed())
				connect();
			if (id == 1)
				ps = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS);
		}
		catch (SQLException e) {
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
			if(connection == null || connection.isClosed())
				connect();
				ps = connection.prepareStatement(query,columns);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}
	
	public static void disconnect() {
		try {
			if(connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			connection = null;
		}
	}
}
