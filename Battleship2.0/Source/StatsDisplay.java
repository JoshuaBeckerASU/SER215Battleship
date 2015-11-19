/********************** 
Name: Stats display 
Author: David Ward
Create On: 11/18/15
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
import java.text.*;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class StatsDisplay extends JFrame
{
   private int m_ScreenWidth, m_ScreenHeight;
   private JLabel m_GamesPlayed_L, m_GamesLost_L, m_GamesWon_L, m_ShipsLost_L, m_ShipsDestroyed_L, m_LossPercent_L, m_WonPercent_L, m_Name_L, m_CurrentGame_L, m_Lost_L, m_Destroyed_L, m_Victory_L, m_Overall_L;
   private JFrame m_StatsDisplay;
   private int gamesPlayed = 1, gamesLost = 1, gamesWon = 1, shipsLost = 1, shipsDestroyed = 1, currentLost = 1, currentDestroyed = 1;
   private String m_Name = "", victory = "You are victorious!", lost = "You have been defeated!";
   private double gamesWonP = 1, gamesLostP = 1;
   private boolean isVictorious = false;
   private DecimalFormat myFormatter = new DecimalFormat("##.##");
   
   public static void main(String[] args)
   {
      boolean isWinner = true;
      new StatsDisplay(isWinner);
   }
   
   public StatsDisplay(boolean isWinner)
   {
      super("TranslucentWindow");
      RecordTracking records = new RecordTracking();
      isVictorious = isWinner;
      //records.connect(m_Name);
      createComponents();
      buildComponents();
      addElements();
      //m_StatsDisplay.setOpacity(0.55f);
      m_StatsDisplay.setVisible(true);
      
   }
   
   public void createComponents()
   {
		m_ScreenWidth = 350;
		m_ScreenHeight = 800;
      
      m_StatsDisplay = new JFrame("Statistics");
      m_StatsDisplay.setLayout(new GridLayout(14, 1));
      
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
   }
}