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

	public String getActivityID(String activityName, String activityTime, String activityDate){
		
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeQuery("SELECT activityID FROM activity WHERE activityName = " + activityName + " AND activityTime = " + activityTime + " AND activityDate = " + activityDate);

		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}		
	}

	public void addParticipants(String participantName, String activityID){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("INSERT INTO participants(participantName, activityID) VALUES(" + participantName + ", " + activityID + ")");

		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}
	}

	public ArrayList<String> getActivity(String activityID){

		Statement s = null;
		ResultSet rs = null; 
		ArrayList<String> arr = new ArrayList<String>();

		try{
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT * FROM activity WHERE activityID =" + activityID + ";");
			while(rs.next()){
				
				arr.add("Activity ID: "+rs.getString("activityID"));
				arr.add("Activity name: "+rs.getString("activityName"));
				arr.add("Time: "+rs.getString("activityTime"));
				arr.add("Date: "+rs.getString("activityDate"));
				arr.add("Notes: " + rs.getString("activityNote"));
            	
			}

		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}

		return arr;
	}

	public ArrayList<String> getAllActivities(){

		Statement s = null;
		ResultSet rs = null; 
		ArrayList<String> arr = new ArrayList<String>();

		try{
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT * FROM activity");
			while(rs.next()){
				
				arr.add("Activity ID: "+rs.getString("activityID"));
				arr.add("Activity name: "+rs.getString("activityName"));
				arr.add("Time: "+rs.getString("activityTime"));
				arr.add("Date: "+rs.getString("activityDate"));
				arr.add("Notes: " + rs.getString("activityNote"));
            	
			}
		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}

		return arr;
	}

	public ArrayList<String> getParticipants(String activityID){

		Statement s = null;
		ResultSet rs = null; 
		ArrayList<String> arr = new ArrayList<String>();

		try{
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT paticipantName FROM participants WHERE activityID =" + activityID);
			while(rs.next()){
				arr.add(rs.getString("participantName"));
            	
			}

		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}

		return arr;
	}

}