import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A Java MySQL PreparedStatement INSERT example.
 * Demonstrates the use of a SQL INSERT statement against a
 * MySQL database, called from a Java program, using a
 * Java PreparedStatement.
 * 
 * Created by Alvin Alexander, http://alvinalexander.com
 */
public class Stats
{

  public static void main(String[] args)
  {
    try
    {
      // create a mysql database connection
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9170217";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "sql9170217", "TwjduVDLc7");
    
      // create a sql date object so we can use it in our INSERT statement
      Calendar calendar = Calendar.getInstance();
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      System.out.println(timestamp);
      
      
      // the mysql insert statement
      String query = " INSERT INTO tblGameInfo(fldPlayerTreasures, fldAiTreasures, fldTotalMoves, fldWinner, fldGameTime, fldUndos,fldTimeStamp) "
      		+ "values (?,?,?,?,?,?,?)";

      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      
      preparedStmt.setInt (1, 2);
      preparedStmt.setInt (2,2);
      preparedStmt.setInt (3, 2);
      preparedStmt.setString (4, "me");
      preparedStmt.setLong (5, 500);
      preparedStmt.setInt (6, 2);
      preparedStmt.setTimestamp (7, timestamp);
      

      // execute the preparedstatement
      preparedStmt.execute();
      
      conn.close();
      
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
  }
}