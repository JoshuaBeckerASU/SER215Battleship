/********************** 
Name: PlayerRecords 
Author: David Ward
Create On: 11/02/15
Updated On: 
Contributors:
***********************/
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.ImageIcon.*;
import javax.swing.JFrame;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class UserLogin
{
   private JFrame m_UserLoginFrame;
   private int m_ScreenWidth, m_ScreenHeight;
   private JButton m_Login, m_Register, m_NewUser, m_Cancel, m_Exit; //m_Login button to login as existing user, m_Register button to register new user, m_NewUser button to bring up new user registration
   private JPanel m_LabelText_Existing_P, m_Buttons_Existing_P, m_LabelText_New_P, m_Buttons_New_P;
   private JLabel m_UserName_L, m_Password_L, m_Results_L;
   private String m_UserName_S, m_Password_S, m_LoginResult_S, m_RegResult_S;

   public static void main(String[] args)
   {
      
   }
   
   //check if username and password are correct
   public boolean loginSuccess()
   {
      boolean isLoggedIn;
      int resultCode;
      
      UserRegistration logUser = new UserRegistration(m_UserName_S, m_Password_S);
      
      resultCode = logUser.userLogin();
      
      switch(resultCode)
      {
         case 0: m_LoginResult_S = "You have successfully logged in.";
            isLoggedIn = true;
            break;
            
         case 1: m_LoginResult_S = "Username and password combination does not exist";
            isLoggedIn = false;
            break;
            
         case 2: m_LoginResult_S = "Database connection failed.";
            isLoggedIn = false;
            break;
      }
      
      return isLoggedIn;
   }
   
   //check if user registered correctly
   public boolean registrationSuccess()
   {
      boolean isLoggedIn;
      int resultCode;
      
      UserRegistration userReg = new UserRegistration(m_UserName_S, m_Password_S);
      
      resultCode = userReg.userReg();
      
      switch(resultCode)
      {
         case 0: m_RegResult_S = "You have successfully registered.";
            isLoggedIn = true;
            break;
            
         case 1: m_RegResult_S = "Username already exists. Username must be unique. Registration failed.";
            isLoggedIn = false;
            break;
            
         case 2: m_RegResult_S = "Database connection failed. Registration failed.";
            isLoggedIn = false;
            break;
      }
      
      return isLoggedIn;
   }
}