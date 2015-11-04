/********************** 
Name: PlayerRecords 
Author: David Ward
Create On: 10/28/15
Updated On: 
Contributors:
battleship_db
team4
3H2qib2$
50.62.209.118:3306
***********************/

import java.sql.*;

public class DBConnection
{
   //creating the connection string to the DB
   private final String URL = "jdbc:mysql://50.62.209.118:3306/battleship_db";
   private final String USER = "team4", PASS = "3H2qib2$";
   private Connection conn = null;
   private Statement stmt = null, stmtInsert = null, stmtUpdate = null;
   private int intUpdatePlayed, intUpdateGWon, intUpdateGLost, intUpdateDestroyed, intUpdateSLost, intPlayed, intGWon, intGLost, intDestroyed, intSLost;
   private double updatePWon, updatePLost, pWon, pLost;
   private String sqlUpdatePlayed, sqlUpdateGWon, sqlUpdateGLost, sqlUpdateDestroyed, sqlUpdateSLost, sqlUpdatePWon, sqlUpdatePLost;
   private String sqlPlayed, sqlGWon, sqlGLost, sqlDestroyed, sqlSLost, sqlPWon, sqlPLost;
   private String sql, createRecords;
   private String playerName;
   
   public DBConnection()
   {
      
   }
   
   public void connect(String name)
   {
      playerName = name;
      
      try
      {
         try
         {
            Class.forName("com.mysql.jdbc.Driver");
         }
         
         catch(Exception e)
         {
            System.out.println("Fail");
         }
         
         conn = DriverManager.getConnection(URL,USER,PASS);
         stmt = conn.createStatement();
         
         sql = "SELECT count(*) cnt from bs_player_stats WHERE name = '" + name + "';";
         ResultSet rs = stmt.executeQuery(sql);
         
         //retrieve stats for existing user
         if(!rs.isBeforeFirst())
         {
             sqlPlayed = "SELECT games_played from bs_player_stats WHERE name = '" + name + "';";
             sqlGWon = "SELECT games_won from bs_player_stats WHERE name = '" + name + "';";
             sqlGLost = "SELECT games_lost from bs_player_stats WHERE name = '" + name + "';";
             sqlDestroyed = "SELECT ships_destroyed from bs_player_stats WHERE name = '" + name + "';";
             sqlSLost = "SELECT ships_lost from bs_player_stats WHERE name = '" + name + "';";
             sqlPWon = "SELECT win_percentage from bs_player_stats WHERE name = '" + name + "';";
             sqlPLost = "SELECT loss_percentage from bs_player_stats WHERE name = '" + name + "';";
             
             ResultSet rsPlayed = stmt.executeQuery(sqlPlayed);
             intPlayed = ((Number) rsPlayed.getObject(1)).intValue();
             
             ResultSet rsGWon = stmt.executeQuery(sqlGWon);             
             intGWon = ((Number) rsGWon.getObject(1)).intValue();
             
             ResultSet rsGLost = stmt.executeQuery(sqlGLost);
             intGLost = ((Number) rsGLost.getObject(1)).intValue();
             
             ResultSet rsDestroyed = stmt.executeQuery(sqlDestroyed);
             intDestroyed = ((Number) rsDestroyed.getObject(1)).intValue();
             
             ResultSet rsSLost = stmt.executeQuery(sqlSLost);
             intSLost = ((Number) rsSLost.getObject(1)).intValue();
             
             //ResultSet rsPWon = stmt.executeQuery(sqlPWon);
             
             //ResultSet rsPLost = stmt.executeQuery(sqlPLost);

             
             pWon = ((double) intGWon / intPlayed) * 100;
             pLost = ((double) intGLost / intPlayed) * 100;
             
             rsPlayed.close();
             rsGWon.close();
             rsGLost.close();
             rsDestroyed.close();
             rsSLost.close();
             //rsPWon.close();
             //rsPLost.close();
         }
         
         else
         {
            /*create records for new user
            stmtInsert = conn.createStatement();
            createRecords = "INSERT into bs_player_stats(name, games_played, games_won, games_lost, ships_destroyed, ships_lost, win_percentage, loss_percentage) VALUES('"
               + name + "', 0, 0, 0, 0, 0, 0.00, 0.00);";
            stmtInsert.executeUpdate(createRecords);
            */
            System.out.println("ERROR: User records not found");
         }

         rs.close();
         stmt.close();
         conn.close();
         
         System.out.println("Connection closed");
      }
      
      catch(SQLException se)
      {
         se.printStackTrace();
      }
   }
   
   //return records for display
   public String getUserName()
   {
      return playerName;
   }
   
   public int getGamesWon()
   {
      return intGWon;
   }
   
   public int getGamesLost()
   {
      return intGLost;
   }
   
   public int getShipsDestroyed()
   {
      return intDestroyed;
   }
   
   public int getShipsLost()
   {
      return intSLost;
   }
   
   public double getPWon()
   {
      return pWon;
   }
   
   public double getPLost()
   {
      return pLost;
   }
   
   public void modifyRecords(boolean isVictorious, int sDestroyed, int sLost)
   {
      String sqlUpdate;
      if(isVictorious)
      {
         intGWon++;
      }
      
      else
      {
         intGLost++;
      }
      
      intPlayed++;
      pWon = ((double) intGWon / intPlayed) * 100;
      pLost = ((double) intGLost / intPlayed) * 100;
      
      intDestroyed += sDestroyed;
      intSLost += sLost;
      
      sqlUpdate = "UPDATE bs_player_stats SET games_played = " + intPlayed + ", games_won = " + intGWon + ", games_lost = " + intGLost + ", ships_destroyed = " + intDestroyed
         + ", ships_lost = " + intSLost + ", win_percentage = " + pWon + ", loss_percentage = " + pLost + ";";
      
      try
      {
         try
         {
            Class.forName("com.mysql.jdbc.Driver");
         }
         
         catch(Exception e)
         {
            System.out.println("Fail");
         }
         
         stmtUpdate = conn.createStatement();
         
         stmtUpdate.executeUpdate(sqlUpdate);

         stmtUpdate.close();
         conn.close();
         
         System.out.println("Records updated. Connection closed");
      }
      
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      
   }
}