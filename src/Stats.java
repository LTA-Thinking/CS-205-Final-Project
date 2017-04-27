import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.URLClassLoader;
import java.net.URL;

/**
 * A Java MySQL PreparedStatement INSERT.
 * Inserts game info into database to be used as statistics
 * 
 * @Ivan Spizizen
 */
public class Stats{
	
	private static final String myDriver = "com.mysql.jdbc.Driver";
	private static final String myUrl = "jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9170217";

/*
	public void connect() throws ClassNotFoundException, SQLException
  {
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9170217";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sql9170217", "TwjduVDLc7");    
  }
 */
  public static boolean insertStats(int humanScore,int comScore,int moves,boolean winner,long gameTime)
  {
	  try
	    {
		  ClassLoader classLoader = new URLClassLoader(new URL[]{new URL("mysql-connector-java-5.1.41-bin.jar")});
		  Class.forName(myDriver,true,classLoader);
		  
	      Connection conn = DriverManager.getConnection(myUrl, "sql9170217", "TwjduVDLc7");
	      // create a sql date object so we can use it in our INSERT statement
	      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	         
	      // the mysql insert statement
	      String query = " INSERT INTO tblGameInfo(fldPlayerTreasures, fldAiTreasures, fldTotalMoves, fldWinner, fldGameTime, fldUndos,fldTimeStamp, fldPlayerId)"
	      		+ "values (?,?,?,?,?,?,?,?)";
	      
		// create the mysql insert preparedstatement
	      PreparedStatement preparedStmt = conn.prepareStatement(query);
	      
	      preparedStmt.setInt (1, humanScore);
	      preparedStmt.setInt (2,comScore);
	      preparedStmt.setInt (3, moves);
		  
		  if(winner)
			preparedStmt.setString (4, "human");
		  else
			preparedStmt.setString(4, "computer");
	      
		  preparedStmt.setLong (5, gameTime);
	      preparedStmt.setInt (6, 0);
	      preparedStmt.setTimestamp (7, timestamp);
	      preparedStmt.setString (8, "null");
	      

	      // execute the preparedstatement
	      preparedStmt.execute();
	      
	      conn.close();
	      return true;
	      
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	      return false;
	    }
	  
  }
  
  public static long[] playerStats() throws SQLException, ClassNotFoundException
  {
	  Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sql9170217", "TwjduVDLc7");
      
      //String query = "SELECT AVG(fldPlayerTreasures) FROM tblGameInfo WHERE fldPlayerId ='Ivan'";
      String query = "SELECT AVG(fldPlayerTreasures) as pt, AVG(fldTotalMoves) as tm,"
      		+ "AVG(fldGameTime) as gt, AVG(fldUndos) as u, fldPlayerId FROM tblGameInfo WHERE fldPlayerId = 'Ivan'";

      // create the java statement
      Statement st = conn.createStatement();
      
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      
      // iterate through the java resultset
      //while (rs.next())
      //{
        long fldPlayerTreasures = rs.getLong("pt");       
        long fldTotalMoves = rs.getLong("tm");
        long fldGameTime = rs.getLong("gt");
        long fldUndos = rs.getLong("u");
           
        
        // print the results
        //System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
        System.out.format("%s,%s,%s,%s,%s,%s,%s,%s\n", " Avg Player Treasures", fldPlayerTreasures, " Avg Total Game Moves",fldTotalMoves, 
        		" Avg Game Time (600=6:00 min)",fldGameTime, " Avg Player Undos",fldUndos);
		
		st.close();		
		
		long[] data = {fldPlayerTreasures,fldTotalMoves,fldGameTime};
		
		return data;
      //}
      
    }

  
  
  public static void main(String[] args) throws ClassNotFoundException, SQLException
  {
   new Stats().playerStats();
  }
  
 }  
