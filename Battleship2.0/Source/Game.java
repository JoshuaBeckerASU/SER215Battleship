/********************** 
Name: Game Object
Author: Joshua Becker
Create On: 10/26/15
Contributors:
***********************/
import javax.swing.*;
import java.util.*;

public class Game
{
	private Player m_Players[];
	private Player m_CurrentPlayer;
	private int m_CurrentPlayerIndex;
	private int m_difficulty;
	private int m_NumOfGames;
	private GameWindow m_GameWindow;
	private JFrame m_OldWindow;
	private LoadAssets m_Assets;
	private Location m_TargetLoc[];
	
	Game()
	{
		
	}
	Game(int difficulty, int numOfPlys, LoadAssets assets)
	{
		m_Assets = assets;
		m_difficulty = difficulty;
		m_Players = new Player[2];// change this later...
		fillPlayers();
		m_CurrentPlayerIndex = 0;
		m_NumOfGames = 0;
		m_TargetLoc = new Location[5];
	}
	
	public void startGame(JFrame oldWindow)
	{
		m_OldWindow = oldWindow;
		m_GameWindow = new GameWindow(this, m_Assets, m_OldWindow);
	}
	/**getNumOfPlys
	* gets the number of players
	* @return int: number of players
	**/
	
	public int getNumOfPlys()
	{
		return m_Players.length;
	}
	/**getDifficulty
	* gets the difficulty
	* @return int: difficulty
	**/
	
	public int getDifficulty()
	{
		return m_difficulty;
	}
	/**getPlayer
	* gets the player
	* @param String: name of wanted player;
	* @return Player: wanted Player object;
	**/
	
	public Player getPlayer(String name)
	{
		for(int i = 0; i < m_Players.length; i++)
		{
			if(m_Players[i].getName().equals(name))
			{
				return m_Players[i];
			}				
		}
		return null;
	}
	
	/**getNumOfGames
	* gets the number of games played
	* @return int: number of games played
	**/
	public int getNumOfGames()
	{
		return m_NumOfGames;
	}
	
	/**incNumOfGames
	* increments the number of games by one
	**/
	public void incNumOfGames()
	{
		m_NumOfGames++;
	}
	
	/**resetGame
	* resets the Current Game
	**/
	public void resetGame()
	{
		for(int i = 0; i < m_Players.length; i++)
		{
			m_Players[i].resetShips();
		}
	}
	
	public Player getCurrentPlayer()
	{
		return m_CurrentPlayer;
	}
	
	public Player getNextPlayer()// will need to switch to get opponents board.
	{
		if(m_CurrentPlayerIndex == m_Players.length-1)
		{
			return m_Players[0];
		}else
		{
			return m_Players[1];
		}
	}

	/**TEMP METHOD fillPlayers
	* fillPlayers with some value
	**/
	private void fillPlayers()
	{
		m_Players[0] = new Player("Player 1", true, m_Assets, this);
		m_Players[1] = new Player("AI", false, m_Assets, this);
		m_CurrentPlayer = m_Players[0];
		fillAI();
	}
	public void fillAI()
	{
		Ship ship = m_Players[1].getNextShip();
		m_Players[1].setNextShip();
		m_Players[1].updateBoard(ship, 2,2, "DOWN");
		m_Players[1].addToTaken(ship.x(),ship.y(),ship);
		
		ship = m_Players[1].getNextShip();
		m_Players[1].setNextShip();
		m_Players[1].updateBoard(ship, 3,3, "RIGHT");
		m_Players[1].addToTaken(ship.x(),ship.y(),ship);
		
		ship = m_Players[1].getNextShip();
		m_Players[1].setNextShip();
		m_Players[1].updateBoard(ship, 4,4,"DOWN");
		m_Players[1].addToTaken(ship.x(),ship.y(),ship);
		
	    ship = m_Players[1].getNextShip();
		m_Players[1].setNextShip();
		m_Players[1].updateBoard(ship, 5,5, "DOWN");
		m_Players[1].addToTaken(ship.x(),ship.y(),ship);

		ship = m_Players[1].getNextShip();
		m_Players[1].setNextShip();
		m_Players[1].updateBoard(ship, 6,6, "LEFT");
		m_Players[1].addToTaken(ship.x(),ship.y(),ship);
		
	}
	public void nextTurn()
	{
		if(m_CurrentPlayerIndex == m_Players.length -1)
		{
			m_CurrentPlayer = m_Players[0];
			m_CurrentPlayerIndex = 0;
		}else
		{
			System.out.println("here");
			m_CurrentPlayerIndex++;
			m_CurrentPlayer = m_Players[m_CurrentPlayerIndex];
		}
		
		if(!m_CurrentPlayer.isHuman())
		{
			m_CurrentPlayer.resetShots();
			takeAITurn();
		}else
		{
			takeHumanTurn();
		}
	}

	private void takeAITurn()
	{
		Random rand = new Random(0);
		for(int i = 0; i < 5; i++)
		{
			if(m_CurrentPlayer.getNumOfSelectedTargets() == 4)
			{
				m_CurrentPlayer.incNumOfSelTargets();
				PlayerSelectedTarget(rand.nextInt(15) + 1,rand.nextInt(17)+1);
				nextTurn();
			}else
			{
				m_CurrentPlayer.incNumOfSelTargets();
				PlayerSelectedTarget(rand.nextInt(15) + 1,rand.nextInt(17)+1);
			}
		}
	}
	private void takeHumanTurn()
	{
		m_CurrentPlayer.resetShots();
	}
	public void PlayerSelectedTarget(int x,int y)
	{
		m_TargetLoc[m_CurrentPlayer.getNumOfSelectedTargets()-1] = new Location(x,y);
		m_GameWindow.updateActionConsole(m_CurrentPlayer.getName() + ":  Selected Target...\t"
										 + (5 - m_CurrentPlayer.getNumOfSelectedTargets()) + " Shots Left");
		checkIfHit();
	}
	public void checkIfHit()
	{
		int index = m_CurrentPlayer.getNumOfSelectedTargets()-1;
		int x = m_TargetLoc[index].x();
		int y = m_TargetLoc[index].y();
		for(int i = 0; i < 5; i++)
		{
			if(getNextPlayer().checkHit(x,y))
			{
				if(m_CurrentPlayer.isHuman())
				{
					BoardMouseAction.setIcon(m_Assets.getImage("HitMarker"));
				}else
				{
					((JLabel) getNextPlayer().getTargetBoard()[x].getComponent(y)).setIcon(m_Assets.getImage("HitMarker"));
				}
				
				m_GameWindow.updateActionConsole("HIT On Locaion\tx = " + x + " y = " + y);
				
				break;
			}else
			{
				if(m_CurrentPlayer.isHuman())
				{
					BoardMouseAction.setIcon(m_Assets.getImage("Target"));
				}else
				{
					((JLabel) getNextPlayer().getTargetBoard()[x].getComponent(y)).setIcon(m_Assets.getImage("Target"));
				}
				m_GameWindow.updateActionConsole("MISS On Locaion\tx = " + x + " y = " + y);
				break;
			}
		}
	}
}