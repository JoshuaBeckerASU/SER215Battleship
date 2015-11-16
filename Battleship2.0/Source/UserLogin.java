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
   private JPanel m_LabelText_ExistingUser_P, m_LabelText_ExistingPass_P, m_Buttons_Existing_P, m_LabelText_NewUser_P, m_LabelText_NewPass_P, m_LabelText_ConfirmPass_P, m_Buttons_New_P;
   private JLabel m_UserName_L, m_Password_L, m_ConfirmPass_L, m_Results_L;
   private JTextField m_UserName_T, m_Password_T, m_ConfirmPass_T;
   private String m_UserName_S, m_Password_S, m_ConfirmPass_S, m_Result_S;
   private FlowLayout m_FrameLayout = new FlowLayout(), m_PanelLayout1 = new FlowLayout(), m_PanelLayout2 = new FlowLayout();
   private GridLayout m_PanelLayoutNew1 = new GridLayout(), m_PanelLayoutNew2 = new GridLayout();
   private Thread m_loadAssets;

   public static void main(String[] args)
   {
      new UserLogin();
   }
   
   public UserLogin()
   {
      createComponents();
      buildComponentsLog();
      addElementsLog();
      addActionListeners();
      m_UserLoginFrame.setVisible(true);
   }
   
   public UserLogin(Thread loadAssets)
   {
      m_loadAssets = loadAssets;
      createComponents();
      buildComponentsLog();
      addElementsLog();
      addActionListeners();
      m_UserLoginFrame.setVisible(true);
   }
   
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
      }
      
      return isLoggedIn;
   }
   
   public void createComponents()
   {
      GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		m_ScreenWidth = 500;                    //gd.getDisplayMode().getWidth();
		m_ScreenHeight = 200;                   //gd.getDisplayMode().getHeight();
      
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
      m_ConfirmPass_L = new JLabel("Confirm:");
      m_Results_L = new JLabel("");
      
      m_UserName_T = new JTextField(20);
      m_Password_T = new JTextField(20);
      m_ConfirmPass_T = new JTextField(20);
      
      m_LabelText_ExistingUser_P = new JPanel();
      m_LabelText_ExistingPass_P = new JPanel();
      m_Buttons_Existing_P = new JPanel();
      m_LabelText_NewUser_P = new JPanel();
      m_LabelText_NewPass_P = new JPanel();
      m_LabelText_ConfirmPass_P = new JPanel();
      
      m_Buttons_New_P = new JPanel();
      
      //m_LabelText_ExistingUser_P.setLayout(m_PanelLayout1);
      //m_LabelText_ExistingPass_P.setLayout(m_PanelLayout1);
      m_LabelText_ExistingUser_P.setLayout(new GridLayout(2,2));
      m_Buttons_Existing_P.setLayout(m_PanelLayout2);
      m_LabelText_NewUser_P.setLayout(new GridLayout(3,2));
      m_PanelLayoutNew1.layoutContainer(m_LabelText_ExistingUser_P);
      m_PanelLayoutNew1.layoutContainer(m_LabelText_NewUser_P);
      //m_LabelText_NewPass_P.setLayout(m_PanelLayout1);
      //m_LabelText_ConfirmPass_P.setLayout(m_PanelLayout1);
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
      
      m_Login.setActionCommand("Login");
      m_NewUser.setActionCommand("New User");
      m_Exit.setActionCommand("Exit");
   }
   
   public void addElementsLog()
   {
      m_LabelText_ExistingUser_P.add(m_UserName_L, BorderLayout.NORTH);
      m_LabelText_ExistingUser_P.add(m_UserName_T, BorderLayout.NORTH);
      m_LabelText_ExistingUser_P.add(m_Password_L, BorderLayout.SOUTH);
      m_LabelText_ExistingUser_P.add(m_Password_T, BorderLayout.SOUTH);
      m_LabelText_ExistingPass_P.add(m_Results_L);
      
      m_Buttons_Existing_P.add(m_Login);
      m_Buttons_Existing_P.add(m_NewUser);
      m_Buttons_Existing_P.add(m_Exit);
      
      m_UserLoginFrame.add(m_LabelText_ExistingUser_P, BorderLayout.NORTH);
      m_UserLoginFrame.add(m_LabelText_ExistingPass_P, BorderLayout.CENTER);
      m_UserLoginFrame.add(m_Buttons_Existing_P, BorderLayout.SOUTH);
   }
   
   public void buildComponentsNew()
   {
      m_UserRegFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      m_UserRegFrame.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
      
      m_Register.setMargin(new Insets(0,0,0,0));
      m_Cancel.setMargin(new Insets(0,0,0,0));
      
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
      m_Buttons_New_P.add( m_Cancel);
      
      m_UserRegFrame.add(m_LabelText_NewUser_P, BorderLayout.NORTH);
      m_UserRegFrame.add(m_LabelText_NewPass_P, BorderLayout.CENTER);
      m_UserRegFrame.add(m_LabelText_ConfirmPass_P, BorderLayout.CENTER);
      m_UserRegFrame.add(m_Buttons_New_P, BorderLayout.SOUTH);
   }
   
   private void addActionListeners()
   {
      m_Login.addActionListener(new ButtonListener());
      m_Register.addActionListener(new ButtonListener());
      m_NewUser.addActionListener(new ButtonListener());
      m_Cancel.addActionListener(new ButtonListener());
      m_Exit.addActionListener(new ButtonListener());
   }
   
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
               break;
            case "New User": UserReg();
               break;
            case "Exit": m_UserLoginFrame.dispose(); System.exit(1);
               break;
            case "Register":
               m_UserName_S = m_UserName_T.getText();
               m_Password_S = m_Password_T.getText();
               m_ConfirmPass_S = m_ConfirmPass_T.getText();
               isRegged = registrationSuccess();
               break;
            case "Cancel": m_UserRegFrame.dispose();
               break;
         }
         
         if(isLogged == true || isRegged == true)
         {
            //LoadAssets assets = new LoadAssets();
            //MenuWindow menu = new MenuWindow(assets);
            m_loadAssets.start();
            System.out.println("Game login/registration is successful.");
         }
         
         else
         {
            System.out.println(m_Result_S);
         }
      }
   }
}