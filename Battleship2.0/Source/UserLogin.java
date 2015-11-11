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

public class UserLogin extends JFrame
{
   private JFrame m_UserLoginFrame, m_UserRegFrame;
   private int m_ScreenWidth, m_ScreenHeight;
   private JButton m_Login, m_Register, m_NewUser, m_Cancel, m_Exit; //m_Login button to login as existing user, m_Register button to register new user, m_NewUser button to bring up new user registration
   private JPanel m_LabelText_Existing_P, m_Buttons_Existing_P, m_LabelText_New_P, m_Buttons_New_P;
   private JLabel m_UserName_L, m_Password_L, m_Results_L;
   private JTextField m_UserName_T, m_Password_T;
   private String m_UserName_S, m_Password_S, m_LoginResult_S, m_RegResult_S;
   private FlowLayout m_FrameLayout = new FlowLayout(), m_PanelLayout1 = new FlowLayout(), m_PanelLayout2 = new FlowLayout();

   public static void main(String[] args)
   {
      new UserLogin();
   }
   
   public UserLogin()
   {
      createComponents();
      buildComponentsLog();
      addElements();
      m_UserLoginFrame.setVisible(true);
   }
   
   //check if username and password are correct
   public boolean loginSuccess()
   {
      boolean isLoggedIn = false;
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
      boolean isLoggedIn = false;
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
   
   public void createComponents()
   {
      GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		m_ScreenWidth = 500;                    //gd.getDisplayMode().getWidth();
		m_ScreenHeight = 300;                   //gd.getDisplayMode().getHeight();
      
      m_UserLoginFrame = new JFrame("User Login");
      m_UserRegFrame = new JFrame("Create User");
      m_UserLoginFrame.setLayout(m_FrameLayout);
      m_UserRegFrame.setLayout(m_FrameLayout);
      m_FrameLayout.setAlignment(FlowLayout.CENTER);
      
      m_Login = new JButton("Sign in");
      m_NewUser = new JButton("New User");
      m_Exit = new JButton("Exit");
      m_Cancel = new JButton("Cancel");
      m_Register = new JButton("Register");
      
      
      m_UserName_L = new JLabel("Username:");
      m_Password_L = new JLabel("Password:");
      m_Results_L = new JLabel("");
      
      m_UserName_T = new JTextField(40);
      m_Password_T = new JTextField(40);
      
      m_UserName_L.setAlignmentY(TOP_ALIGNMENT);
      m_UserName_T.setAlignmentY(TOP_ALIGNMENT);
      
      m_Password_L.setAlignmentY(CENTER_ALIGNMENT);
      m_Password_T.setAlignmentY(CENTER_ALIGNMENT);
      
      m_LabelText_Existing_P = new JPanel();
      m_Buttons_Existing_P = new JPanel();
      m_LabelText_New_P = new JPanel();
      m_Buttons_New_P = new JPanel();
      
      m_LabelText_Existing_P.setLayout(m_PanelLayout1);
      m_Buttons_Existing_P.setLayout(m_PanelLayout2);
      m_LabelText_New_P.setLayout(m_PanelLayout1);
      m_Buttons_New_P.setLayout(m_PanelLayout2);
      m_PanelLayout1.setAlignment(FlowLayout.CENTER);
      m_PanelLayout2.setAlignment(FlowLayout.CENTER);
   }
   
   public void buildComponentsLog()
   {
      m_UserLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      m_UserLoginFrame.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
      
      m_Login.setMargin(new Insets(0,0,0,0));
      m_NewUser.setMargin(new Insets(0,0,0,0));
      m_Exit.setMargin(new Insets(0,0,0,0));
   }
   
   public void addElements()
   {
      m_LabelText_Existing_P.add(m_UserName_L, BorderLayout.NORTH);
      m_LabelText_Existing_P.add(m_UserName_T, BorderLayout.NORTH);
      m_LabelText_Existing_P.add(m_Password_L, BorderLayout.SOUTH);
      m_LabelText_Existing_P.add(m_Password_T, BorderLayout.SOUTH);
      m_LabelText_Existing_P.add(m_Results_L);
      
      m_Buttons_Existing_P.add(m_Login);
      m_Buttons_Existing_P.add(m_NewUser);
      m_Buttons_Existing_P.add(m_Exit);
      
      m_UserLoginFrame.add(m_LabelText_Existing_P, BorderLayout.NORTH);
      m_UserLoginFrame.add(m_Buttons_Existing_P, BorderLayout.SOUTH);
   }
}