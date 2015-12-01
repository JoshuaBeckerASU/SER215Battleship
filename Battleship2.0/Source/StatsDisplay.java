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
   private RecordTracking records = null;
   private JPanel panel = null;
   
   
   //used for testing
   public static void main(String[] args)
   {
      boolean isWinner = true;
      GraphicsEnvironment ge = 
            GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice gd = ge.getDefaultScreenDevice();
      StatsDisplay stats = new StatsDisplay("userName", isWinner, 2, 5);
   }
   
   
   public StatsDisplay(String userName, boolean isWinner, int sLost, int destroyed)
   {
      super("TranslucentWindow");
      
      records = new RecordTracking();
      records.connect(userName);
      records.modifyRecords(isWinner, sLost, destroyed);
      
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
		m_ScreenWidth = 225;
		m_ScreenHeight = 225;
      
      m_StatsDisplay = new JFrame("Statistics");
      m_StatsDisplay.setLayout(new BorderLayout());
      panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setAlignmentX(Component.CENTER_ALIGNMENT);
      panel.setMinimumSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
      
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

     m_Overall_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_GamesPlayed_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_GamesLost_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_GamesWon_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_ShipsLost_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_ShipsDestroyed_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_LossPercent_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_WonPercent_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_Name_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_CurrentGame_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_Lost_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_Destroyed_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_Victory_L.setAlignmentX(Component.CENTER_ALIGNMENT);
     m_Exit_B.setAlignmentX(Component.CENTER_ALIGNMENT);
      
      m_Exit_B.setMargin(new Insets(0,0,0,0));
      m_Exit_B.setAlignmentX(Component.CENTER_ALIGNMENT);
      m_Exit_B.setActionCommand("Exit");
   }
   
   public void addElements()
   {
      panel.add(m_CurrentGame_L);
      panel.add(m_Victory_L);
      panel.add(m_Destroyed_L);
      panel.add(m_Lost_L);
      panel.add(m_Overall_L);
      panel.add(m_GamesPlayed_L);
      panel.add(m_GamesWon_L);
      panel.add(m_GamesLost_L);
      panel.add(m_ShipsDestroyed_L);
      panel.add(m_ShipsLost_L);
      panel.add(m_WonPercent_L);
      panel.add(m_LossPercent_L);
      panel.add(m_Exit_B);
      
      m_StatsDisplay.add(panel, BorderLayout.CENTER);
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
   
   public void getRecords()
   {
      gamesPlayed = records.getGamesPlayed();
      gamesLost = records.getGamesLost();
      gamesWon = records.getGamesWon();
      shipsLost = records.getShipsLost();
      shipsDestroyed = records.getShipsDestroyed();
      gamesWonP = records.getPWon();
      gamesLostP = records.getPLost();
      m_Name = records.getUserName();
   }
}