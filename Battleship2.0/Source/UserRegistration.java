/********************** 
Name: PlayerRecords 
Author: David Ward
Create On: 11/02/15
Updated On: 
Contributors:
***********************/

import java.sql.*;

public class UserRegistration
{
   private String userName;
   private String passWord;
   private String email, avatarImg;
   private String failedConn = "Database connection failed!";
   private final String URL = "jdbc:mysql://50.62.209.118:3306/battleship_db";
   private final String USER = "team4", PASS = "3H2qib2$";
   private String sql, createRecords, createUser;
   private Connection conn = null;
   private Statement stmt = null, stmtInsert = null;
   
   public UserRegistration(String user, String pass)
   {
      userName = user;
      passWord = pass;
   }
   
   //for existing users
   public int userLogin()
   {
      /*
      resultCode meaning:
      0 usernameand password are correct, successfully logged in
      1 username and password combination doesn't exist
      2 database connection failed
      */
      int resultCode;
      String sqlPassCheck = "SELECT count(*) from bs_player WHERE name = '" + userName + "' AND password = MD5('" + passWord + "');";
      Statement stmtUserPass = null;
      
      try
      {
         try
         {
            Class.forName("com.mysql.jdbc.Driver");
         }
         
         catch(Exception e)
         {
            resultCode = 2;
            System.out.println(failedConn);
            return resultCode;
         }
         
         conn = DriverManager.getConnection(URL,USER,PASS);
         stmtUserPass = conn.createStatement();
         stmt = conn.createStatement();
         
         sql = "SELECT count(*) cnt from bs_player WHERE name = '" + userName + "';";
         ResultSet rs = stmt.executeQuery(sql);
         
         //if user exists return corresponding code otherwise create new user and return corresponding code
         if(!rs.isBeforeFirst())
         {
            /*
            need to create algorithm for checking password
            */
            ResultSet rsPassCheck = stmtUserPass.executeQuery(sqlPassCheck);
            
            if(!rsPassCheck.isBeforeFirst())
            {
               rs.close();
               stmt.close();
               conn.close();
            
               resultCode = 0;
               return resultCode;
            }
            
            else
            {
               rs.close();
               stmt.close();
               conn.close();
            
               resultCode = 1;
               return resultCode;
            }
         }
         
         else
         {
            
            rs.close();
            stmt.close();
            conn.close();
            
            resultCode = 1;
            return resultCode;
         }
      }
      
      catch(SQLException se)
      {
         se.printStackTrace();
         System.out.println(failedConn);
         resultCode = 2;
         return resultCode;
      }
   }
   
   public int userReg()
   {
      /*
      resultCode meaning:
      0 username created successfully
      1 username exists
      2 database connection failed
      */
      int resultCode;
      Statement stmtUserPass = null;
      
      try
      {
         try
         {
            Class.forName("com.mysql.jdbc.Driver");
         }
         
         catch(Exception e)
         {
            resultCode = 2;
            System.out.println(failedConn);
            return resultCode;
         }
         
         conn = DriverManager.getConnection(URL,USER,PASS);
         stmtUserPass = conn.createStatement();
         stmt = conn.createStatement();
         
         sql = "SELECT count(*) cnt from bs_player WHERE name = '" + userName + "';";
         ResultSet rs = stmt.executeQuery(sql);
         
         //if user exists return corresponding code otherwise create new user and return corresponding code
         if(!rs.isBeforeFirst())
         {
            rs.close();
            stmt.close();
            conn.close();
            
            resultCode = 1;
            return resultCode;
         }
         
         else
         {
            stmtUserPass = conn.createStatement();
            stmtInsert = conn.createStatement();
            createUser = "INSERT into bs_player(name, password, avatar_location, email) VALUES('" + userName + "', MD5('" + passWord + "'), 'avatar.jpg', '" + email + "');";
            createRecords = "INSERT into bs_player_stats(name, games_played, games_won, games_lost, ships_destroyed, ships_lost, win_percentage, loss_percentage) VALUES('"
               + userName + "', 0, 0, 0, 0, 0, 0.00, 0.00);";
            stmtUserPass.executeUpdate(createUser);
            stmtInsert.executeUpdate(createRecords);
            
            stmtUserPass.close();
            stmtInsert.close();
            rs.close();
            stmt.close();
            conn.close();
            
            resultCode = 0;
            return resultCode;
         }
      }
      
      catch(SQLException se)
      {
         se.printStackTrace();
         System.out.println(failedConn);
         resultCode = 2;
         return resultCode;
      }
   }
}