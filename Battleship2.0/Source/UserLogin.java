/********************** 
Name: PlayerRecords 
Author: David Ward
Create On: 11/02/15
Updated On: 
Contributors: Joshua Becker
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
   private int m_FrameWidth, m_FrameHeight;
   private int m_ScreenHeight,m_ScreenWidth;
   private JButton m_Login, m_Register, m_NewUser, m_Cancel, m_Exit; //m_Login button to login as existing user, m_Register button to register new user, m_NewUser button to bring up new user registration
   private JPanel m_LabelText_ExistingUser_P, m_LabelText_ExistingPass_P, m_Buttons_Existing_P, m_LabelText_NewUser_P, m_LabelText_NewPass_P, m_LabelText_ConfirmPass_P, m_Buttons_New_P;
   private JLabel m_UserName_L, m_Password_L, m_ConfirmPass_L, m_Results_L;
   private JTextField m_UserName_T;
   private JPasswordField m_Password_T, m_ConfirmPass_T;
   private String m_UserName_S, m_Password_S, m_ConfirmPass_S, m_Result_S;
   private FlowLayout m_FrameLayout = new FlowLayout(), m_PanelLayout1 = new FlowLayout(), m_PanelLayout2 = new FlowLayout();
   private GridLayout m_PanelLayoutNew1 = new GridLayout(), m_PanelLayoutNew2 = new GridLayout();
   private RecordTracking records = new RecordTracking();
   private JPanel m_Content_P;
   
   
   //for testing
   /*
   public static void main(String[] args)
   {
      new UserLogin();
   }
   */
   
   public UserLogin()
   {
      createComponents();
      buildComponentsLog();
      addElementsLog();
      addActionListeners();
      m_UserLoginFrame.setVisible(true);
   }
   
   //called for new user registration
   public void UserReg()
   {
      buildComponentsNew();
      addElementsNew();
      m_UserRegFrame.setVisible(true);
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
         case 0: m_Result_S = "You have successfully logged in.";
            isLoggedIn = true;
            break;
            
         case 1: m_Result_S = "Username and password combination does not exist";
            isLoggedIn = false;
            break;
            
         case 2: m_Result_S = "Database connection failed.";
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
      
      if(m_Password_S.equals(m_ConfirmPass_S))
      {
         UserRegistration userReg = new UserRegistration(m_UserName_S, m_Password_S);
      
         resultCode = userReg.userReg();
         
         switch(resultCode)
         {
            case 0: m_Result_S = "You have successfully registered.";
               isLoggedIn = true;
               break;
            
            case 1: m_Result_S = "Username already exists. Username must be unique. Registration failed.";
               isLoggedIn = false;
               break;
            
            case 2: m_Result_S = "Database connection failed. Registration failed.";
               isLoggedIn = false;
               break;
         }
         new UserLogin();
         m_UserRegFrame.dispose();
      }
      
      return isLoggedIn;
   }
   
   //creating components for UI
   public void createComponents()
   {
	  m_FrameWidth = 250;
	  m_FrameHeight = 400;
      
	  GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// Getting size of screen
	  m_ScreenWidth = gd.getDisplayMode().getWidth();
	  m_ScreenHeight = gd.getDisplayMode().getHeight();
      
      m_UserLoginFrame = new JFrame("User Login");
      m_UserRegFrame = new JFrame("Create User");
      
      m_Login = new JButton("Sign in");
      m_NewUser = new JButton("New User");
      m_Exit = new JButton("Exit");
      m_Cancel = new JButton("Cancel");
      m_Register = new JButton("Register");
      
      
      m_UserName_L = new JLabel("Username:");
      m_Password_L = new JLabel("Password:");
      m_ConfirmPass_L = new JLabel("Confirm Password:");
      m_Results_L = new JLabel("");
      
      m_UserName_T = new JTextField(20);
      m_Password_T = new JPasswordField(20);
      m_ConfirmPass_T = new JPasswordField(20);
      
      m_LabelText_ExistingUser_P = new JPanel(new FlowLayout());
      m_LabelText_ExistingPass_P = new JPanel(new FlowLayout());
      m_Buttons_Existing_P = new JPanel();
      m_LabelText_NewUser_P = new JPanel();
      m_LabelText_NewPass_P = new JPanel();
      m_LabelText_ConfirmPass_P = new JPanel();
      m_Content_P = new JPanel();
      
      m_Buttons_New_P = new JPanel();
   }
   
   //the following 2 methods are used for building and adding components specific to existing user login
   public void buildComponentsLog()
   {
      m_Content_P.setLayout(new BoxLayout(m_Content_P,BoxLayout.Y_AXIS));
       
      m_UserLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      m_UserLoginFrame.setSize(new Dimension(m_FrameWidth,m_FrameHeight));
      m_UserLoginFrame.setLocation((m_ScreenWidth/2) - (m_FrameWidth/2),(m_ScreenHeight/2) - (m_FrameHeight/2));
      m_UserLoginFrame.setResizable(false);
      m_UserName_T.setBackground(new Color(150,150,150));
      m_Password_T.setBackground(new Color(150,150,150));
      m_UserName_T.setForeground(Color.BLACK);
      m_Password_T.setForeground(Color.BLACK);
      
      m_Login.setMargin(new Insets(5,5,5,5));
      m_NewUser.setMargin(new Insets(5,5,5,5));
      m_Exit.setMargin(new Insets(5,5,5,5));
      
      m_LabelText_ExistingPass_P.setMaximumSize(new Dimension(m_FrameWidth,50));
      m_LabelText_ExistingPass_P.setMinimumSize(new Dimension(m_FrameWidth,40));
      m_LabelText_ExistingPass_P.setPreferredSize(new Dimension(m_FrameWidth,45));
      m_LabelText_ExistingUser_P.setMinimumSize(new Dimension(m_FrameWidth,40));
      m_LabelText_ExistingUser_P.setMaximumSize(new Dimension(m_FrameWidth,45));
      m_LabelText_ExistingUser_P.setPreferredSize(new Dimension(m_FrameWidth,45));
      
      m_Login.setPreferredSize(new Dimension(70,20));
      m_Exit.setPreferredSize(new Dimension(70,20));
      m_NewUser.setPreferredSize(new Dimension(75,20));
      
      m_Login.setActionCommand("Login");
      m_NewUser.setActionCommand("New User");
      m_Exit.setActionCommand("Exit");
   }
   
   public void addElementsLog()
   {
      m_LabelText_ExistingUser_P.add(m_UserName_L);
      m_LabelText_ExistingUser_P.add(m_UserName_T);
      m_LabelText_ExistingPass_P.add(m_Password_L);
      m_LabelText_ExistingPass_P.add(m_Password_T);
      
      m_Buttons_Existing_P.add(m_Login);
      m_Buttons_Existing_P.add(m_NewUser);
      m_Buttons_Existing_P.add(m_Exit);
      
      m_Content_P.add(m_LabelText_ExistingUser_P);
      m_Content_P.add(m_LabelText_ExistingPass_P);
      m_Content_P.add(m_Buttons_Existing_P);
      
      m_UserLoginFrame.add(m_Content_P);
   }
   
   //the following 2 methods are used for building and adding components specific to new user registration
   public void buildComponentsNew()
   {
      m_UserRegFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      m_UserRegFrame.setSize(new Dimension(m_FrameWidth,m_FrameHeight));
      m_UserRegFrame.setLocation((m_ScreenWidth/2) - (m_FrameWidth/2),(m_ScreenHeight/2) - (m_FrameHeight/2));
      m_UserRegFrame.setResizable(false);
      
      m_Login.setMargin(new Insets(5,5,5,5));
      m_NewUser.setMargin(new Insets(5,5,5,5));
      m_Exit.setMargin(new Insets(5,5,5,5));
      
      m_ConfirmPass_T.setForeground(Color.WHITE);
      m_ConfirmPass_T.setBackground(new Color(150,150,150));
      
      m_Register.setPreferredSize(new Dimension(90,20));
      m_Cancel.setPreferredSize(new Dimension(90,20));
      
      m_Register.setActionCommand("Register");
      m_Cancel.setActionCommand("Cancel");
   }
   
   public void addElementsNew()
   {
      m_LabelText_NewUser_P.add(m_UserName_L);
      m_LabelText_NewUser_P.add(m_UserName_T);
      m_LabelText_NewUser_P.add(m_Password_L);
      m_LabelText_NewUser_P.add(m_Password_T);
      m_LabelText_NewUser_P.add(m_ConfirmPass_L);
      m_LabelText_NewUser_P.add(m_ConfirmPass_T);
      
      m_Buttons_New_P.add(m_Register);
      m_Buttons_New_P.add(m_Cancel);
      
      m_Content_P.removeAll();
      m_Content_P.add(m_LabelText_NewUser_P);
      m_Content_P.add(m_Buttons_New_P);
      
      m_UserRegFrame.add(m_Content_P);
   }
   
   private void addActionListeners()
   {
      m_Login.addActionListener(new ButtonListener());
      m_Register.addActionListener(new ButtonListener());
      m_NewUser.addActionListener(new ButtonListener());
      m_Cancel.addActionListener(new ButtonListener());
      m_Exit.addActionListener(new ButtonListener());
   }
   
   //button actions defined
   private class ButtonListener implements ActionListener
   {
      private boolean isRegged = false, isLogged = false;
      
      public void actionPerformed(ActionEvent event)
      {
         String command = event.getActionCommand();
			
         switch(command)
         {
            case "Login":
               m_UserName_S = m_UserName_T.getText();
               m_Password_S = m_Password_T.getText();
               isLogged = loginSuccess();
               records.connect(m_UserName_S);
               m_UserLoginFrame.dispose();
               Main.startGame();
               break;
            case "New User":
               UserReg();
               m_UserLoginFrame.dispose();
               break;
            case "Exit":
               m_UserLoginFrame.dispose();
               System.exit(1);
               break;
            case "Register":
               m_UserName_S = m_UserName_T.getText();
               m_Password_S = m_Password_T.getText();
               m_ConfirmPass_S = m_ConfirmPass_T.getText();
               isRegged = registrationSuccess();
               m_UserRegFrame.dispose();
               break;
            case "Cancel":
               m_UserRegFrame.dispose();
               new UserLogin();
               break;
         }
         
         if(isLogged == true || isRegged == true)
         {
            //once logged in or registered closes login window and loads game
            System.out.println("Game login/registration is successful.");
         }
         
         else
         {
            System.out.println(m_Result_S);
         }
      }
   }
}