import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.swing.*;
import java.text.*;

public class DatabaseHandler{

	static final String DB_URL = "jdbc:sqlite:balder.db";
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	public static Connection conn = null;
        private Statement s = null;
        private ResultSet rs = null;
	public static GregorianCalendar cal = new GregorianCalendar();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private ArrayList<String> arr;
        static ArrayList<String> participantList;
                
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
            try{
		s = DatabaseHandler.conn.createStatement();
		s.executeUpdate("INSERT INTO participants (participantName, activityID) VALUES( '" + participantName + "', '" + activityID + "');");

            }

            catch(SQLException se){
		System.out.println(se.getMessage());
            }
	}

	public ArrayList<String> getActivity(String activityID){
		arr = new ArrayList<String>();

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
                
            arr = new ArrayList<String>();

		java.util.Date date = cal.getTime();
		String dateCheck = dateFormat.format(date);
		String id = null;
		ResultSet rs2 = null;

		try{
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT * FROM activity WHERE activityDate >= '" + dateCheck + "'");
			
			while(rs.next()){
				
				arr.add("Aktivitets-ID: "+rs.getString("activityID"));
				arr.add("Aktivitetsnamn: "+rs.getString("activityName"));
				arr.add("Tid: "+rs.getString("activityTime"));
				arr.add("Datum: "+rs.getString("activityDate"));
				arr.add("Anteckning: " + rs.getString("activityNote"));
				arr.add("Deltagare:");
				id = rs.getString("activityID");
				rs2 = s.executeQuery("SELECT participantName FROM participants WHERE activityID = '" + id + "'");
				while(rs2.next()){
					arr.add(rs2.getString);
				}
                arr.add("______________________________");

			}
		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}

		return arr;
	}

	public ArrayList<String> getParticipants(String activityID){
                
                arr = new ArrayList<String>();

		try{
			s = DatabaseHandler.conn.createStatement();
			rs = s.executeQuery("SELECT participantName FROM participants WHERE activityID = '" + activityID + "'");
			while(rs.next()){
				arr.add(rs.getString("participantName"));
            	
			}

		}

		catch(SQLException se){
			System.out.println(se.getMessage());
		}

		return arr;
	}

   /* public boolean loginCheck(String userName, String passWord){
        
    }*/
}