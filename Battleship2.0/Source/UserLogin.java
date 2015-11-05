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
   private JButton m_Login, m_Register, m_Exit;
   private JPanel m_LabelText_P, m_Buttons_P;
   private JLabel m_UserName_L, m_Password_L;
   private String m_UserName_S, m_Password_S, m_LoginResult_S;

   public static void main(String[] args)
   {
      
   }
   
   public boolean loginSuccess()
   {
      boolean isLoggedIn;
      int resultCode;
      
      UserRegistration logUser = new UserRegistration(m_UserName_S, m_Password_S);
      
      resultCode = logUser.dbConnect();
      
      switch(resultCode)
      {
         case 0: m_LoginResult_S = "You have successfully logged in.";
            isLoggedIn = true;
            break;
            
         case 1: m_LoginResult_S = "Your user been successfully created.";
            isLoggedIn = true;
            break;
            
         case 2: m_LoginResult_S = "User registration failed.";
            isLoggedIn = false;
            break;
            
         case 3: m_LoginResult_S = "Username and password combination does not exist.";
            isLoggedIn = false;
            break;
      }
      
      return isLoggedIn;
   }
}