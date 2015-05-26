import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.swing.*;
import java.text.*;

public class DatabaseHandler{

	static final String DB_URL = "jdbc:sqlite:balder.db";
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	public static Connection conn = null;

	public static GregorianCalendar cal = new GregorianCalendar();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
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
			conn = DriverManager.getConnection("jdbc:sqlite:balder.db"); 
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
		String str = "INSERT INTO activity ( activityName, activityTime, activityDate, activityNote) VALUES ('" + activityName + "', '" + activityTime + "', '" + activityDate + "', '" + activityNote + "');";
		
		System.out.println(str);
		
		try{
			
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate(str);
			
		}

		catch(SQLException se){
			System.out.println("välkommen till catch");
			System.out.println(se.getMessage());
		}

	}

	public String getActivityID(String activityName, String activityTime, String activityDate){
		Statement s = null;
		ResultSet rs = null;
		String id = null;
		try{	
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT activityID FROM activity WHERE activityName = '" + activityName + "' AND activityTime = '" + activityTime + "' AND activityDate = '" + activityDate + "'");
			id = rs.getString("activityID");
		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}		
	
		return id;
	}

	public void addParticipants(String participantName, String activityID){
		Statement s = null;
		try{
			s = DatabaseHandler.conn.createStatement();
			s.executeUpdate("INSERT INTO participants (participantName, activityID) VALUES( '" + participantName + "', '" + activityID + "');");

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
			rs = s.executeQuery("SELECT * FROM activity WHERE activityID = '" + activityID + "';");
			while(rs.next()){
				
				arr.add("Activity ID: "+rs.getString("activityID"));
				arr.add("Activity name: "+rs.getString("activityName"));
				arr.add("Time: "+rs.getString("activityTime"));
				arr.add("Date: "+rs.getString("activityDate"));
				arr.add("Notes: " + rs.getString("activityNote"));
            	arr.add("______________________________");
            	
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

		java.util.Date date = cal.getTime();
		String dateCheck = dateFormat.format(date);

		try{
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT * FROM activity WHERE activityDate >= " + dateCheck + ")");
			while(rs.next()){
				
				arr.add("Activity ID: "+rs.getString("activityID"));
				arr.add("Activity name: "+rs.getString("activityName"));
				arr.add("Time: "+rs.getString("activityTime"));
				arr.add("Date: "+rs.getString("activityDate"));
				arr.add("Notes: " + rs.getString("activityNote"));
            	arr.add("______________________________");

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
			rs = s.executeQuery("SELECT paticipantName FROM participants WHERE activityID = '" + activityID + "'");
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