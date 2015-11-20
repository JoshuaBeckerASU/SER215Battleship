/********************** 
Name: Stats display 
Author: David Ward
Create On: 11/20/15
Updated On: 
Contributors:
***********************/
import java.io.*;

public class UserProfile implements java.io.Serializable
{
   private String username;
   private int gamesPlayed, gamesLost, gamesWon, shipsLost, shipsDestroyed;
   private double gamesWonP, gamesLostP;
   
   
   public UserProfile(String name, int played, int won, int lost, int sLost, int destroyed, double lostP, double wonP)
   {
      username = name;
      gamesPlayed = played;
      gamesWon = won;
      gamesLost = lost;
      shipsLost = sLost;
      shipsDestroyed = destroyed;
      gamesWonP = wonP;
      gamesLostP = lostP;
   }
   
   public void modifyRecords(boolean isVictorious, int sDestroyed, int sLost)
   {
      if(isVictorious)
      {
         gamesWon++;
      }
      
      else
      {
         gamesLost++;
      }
      
      gamesPlayed++;
      gamesWonP = ((double) gamesWon / gamesPlayed) * 100;
      gamesLostP = ((double) gamesLost / gamesPlayed) * 100;
      
      shipsDestroyed += sDestroyed;
      shipsLost += sLost;
   }
   
   public String getUser()
   {
      return username;
   }

   public int getPlayed()
   {
      return gamesPlayed;
   }
   
   public int getWon()
   {
      return gamesWon;
   }
   
   public int getLost()
   {
      return gamesLost;
   }
   
   public int getDestroyed()
   {
      return shipsDestroyed;
   }
   
   public int getSLost()
   {
      return shipsLost;
   }
   
   public double getLostP()
   {
      return gamesLostP;
   }
   
   public double getWonP()
   {
      return gamesWonP;
   }
}