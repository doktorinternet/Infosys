import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.swing.*;

public class DatabaseHandler{

	static final String DB_URL = "jdbc:sqlite:minDatabas.db";
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	public static Connection conn = null;
	
	public void initDatabase(){

		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException cnfE) {
			System.err.println("Could not find JDBC driver!");
			cnfE.printStackTrace();
			System.exit(1);
		}
		System.out.println("Driver found");

		try {
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:sqlite:minDatabas.db"); 
		} catch (SQLException sqlE) {
			System.out.println("Couldn't connect:");
			sqlE.printStackTrace();
			System.out.println("Exiting...");
			System.exit(1);
		}
		if (conn != null) {
			System.out.println("Connection to database established");
		} else {
			System.out.println("Connection failed");
		}
	}

	public static ArrayList<String> search(String st){
		Statement s = null;
		ResultSet rs = null; 
		ArrayList<String> arr = new ArrayList<String>(); 
		try {
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery(st);
			while(rs.next()) {
				 
				arr.add("Name: "+rs.getString("givenName"));

			} 
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		return arr; 
	}

	public void addActivity(String activityName, String activityTime, String activityDate, String activityNote){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("INSERT INTO activity(activityName, activityTime, activityDate, activityNote) VALUES(" + activityName + ", " + activityTime + ", " + activityDate + ", " + activityNote + ")");

		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}

	}

	public void addParticipants(String residentID, String participantName, String activityID){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("INSERT INTO participants(residentID, participantName, activityID) VALUES(" + residentID + ", " + participantName + ", " + activityID + ")");

		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}
	}
}