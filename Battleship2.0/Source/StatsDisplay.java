/********************** 
Name: Stats display 
Author: David Ward
Create On: 11/18/15
Updated On: 
Contributors:
***********************/

/*
To Do:
switch to box layout
*/

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.ImageIcon.*;
import javax.swing.JFrame;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class StatsDisplay extends JFrame
{
   private int m_ScreenWidth, m_ScreenHeight;
   private JLabel m_GamesPlayed_L, m_GamesLost_L, m_GamesWon_L, m_ShipsLost_L, m_ShipsDestroyed_L, m_LossPercent_L, m_WonPercent_L, m_Name_L, m_CurrentGame_L, m_Lost_L, m_Destroyed_L, m_Victory_L, m_Overall_L;
   private JFrame m_StatsDisplay;
   private JButton m_Exit_B;
   private int gamesPlayed, gamesLost, gamesWon, shipsLost, shipsDestroyed, currentLost, currentDestroyed;
   private String m_Name = "", victory = "You are victorious!", lost = "You have been defeated!";
   private double gamesWonP, gamesLostP;
   private boolean isVictorious = false;
   private DecimalFormat myFormatter = new DecimalFormat("##.##");
   private UserProfile profile = null;
   
   public static void main(String[] args)
   {
      boolean isWinner = true;
      GraphicsEnvironment ge = 
            GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice gd = ge.getDefaultScreenDevice();
      StatsDisplay stats = new StatsDisplay(isWinner, 2, 5);
   }
   
   
   public StatsDisplay(boolean isWinner, int sLost, int destroyed)
   {
      super("TranslucentWindow");
      
      String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/");
      
      try
      {
         FileInputStream fileIn = new FileInputStream(path + "profile.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         profile = (UserProfile) in.readObject();
         in.close();
         fileIn.close();
      }
      
      catch(IOException i)
      {
         i.printStackTrace();
         return;
      }
      
      catch(ClassNotFoundException c)
      {
         c.printStackTrace();
         return;
      }
      
      updateProfile(isWinner, sLost, destroyed);
      
      getRecords();
      
      isVictorious = isWinner;
      currentLost = sLost;
      currentDestroyed = destroyed;
      createComponents();
      buildComponents();
      addElements();
      addActionListeners();
      m_StatsDisplay.setOpacity(0.85f);
      m_StatsDisplay.setVisible(true);
      
   }
   
   public void createComponents()
   {
		m_ScreenWidth = 350;
		m_ScreenHeight = 800;
      
      m_StatsDisplay = new JFrame("Statistics");
      m_StatsDisplay.setLayout(new GridLayout(13, 0));
      
      m_Exit_B = new JButton("Exit");
      
      m_Overall_L = new JLabel("OVERALL STATISTICS");
      m_GamesPlayed_L = new JLabel("GAMES PLAYED: " + gamesPlayed);
      m_GamesLost_L = new JLabel("GAMES LOST: " + gamesLost);
      m_GamesWon_L = new JLabel("GAMES WON: " + gamesWon);
      m_ShipsLost_L = new JLabel("SHIPS LOST: " + shipsLost);
      m_ShipsDestroyed_L = new JLabel("SHIPS DESTROYED: " + shipsDestroyed);
      m_LossPercent_L = new JLabel("LOSS PERCENTAGE: " + myFormatter.format(gamesLostP) + "%");
      m_WonPercent_L = new JLabel("WIN PERCENTAGE: " + myFormatter.format(gamesWonP) + "%");
      m_Name_L = new JLabel(m_Name);
      m_CurrentGame_L = new JLabel("CURRENT GAME RESULTS");
      m_Lost_L = new JLabel("SHIPS LOST: " + currentLost);
      m_Destroyed_L = new JLabel("SHIPS DESTROYED: " + currentDestroyed);
      
      if(isVictorious)
      {
         m_Victory_L = new JLabel(victory);
      }
      
      else
      {
         m_Victory_L = new JLabel(lost);
      }
   }
   
   public void buildComponents()
   {
      m_StatsDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      m_StatsDisplay.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
      m_StatsDisplay.setUndecorated(true);
      
      m_Exit_B.setActionCommand("Exit");
   }
   
   public void addElements()
   {
      m_StatsDisplay.add(m_CurrentGame_L);
      m_StatsDisplay.add(m_Victory_L);
      m_StatsDisplay.add(m_Destroyed_L);
      m_StatsDisplay.add(m_Lost_L);
      m_StatsDisplay.add(m_Overall_L);
      m_StatsDisplay.add(m_GamesPlayed_L);
      m_StatsDisplay.add(m_GamesWon_L);
      m_StatsDisplay.add(m_GamesLost_L);
      m_StatsDisplay.add(m_ShipsDestroyed_L);
      m_StatsDisplay.add(m_ShipsLost_L);
      m_StatsDisplay.add(m_WonPercent_L);
      m_StatsDisplay.add(m_LossPercent_L);
      m_StatsDisplay.add(m_Exit_B);
   }
   
   private void addActionListeners()
   {
      m_Exit_B.addActionListener(new ButtonListener());
   }
   
   private class ButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         String command = event.getActionCommand();
         
         switch(command)
         {
            case "Exit":
               m_StatsDisplay.dispose();
               System.exit(1);
               break;
         }
      }
   }
   
   public void updateProfile(boolean isWinner, int sLost, int destroyed)
   {
      profile.modifyRecords(isWinner, destroyed, sLost);
   }
   
   public void getRecords()
   {
      gamesPlayed = profile.getPlayed();
      gamesLost = profile.getLost();
      gamesWon = profile.getWon();
      shipsLost = profile.getSLost();
      shipsDestroyed = profile.getDestroyed();
      gamesWonP = profile.getWonP();
      gamesLostP = profile.getLostP();
      m_Name = profile.getUser();
   }
}