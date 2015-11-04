/********************** 
Name: Game Object
Author: Joshua Becker
Create On: 10/26/15
Contributors:
***********************/
import javax.swing.*;

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
		m_Players[1].updateBoard(ship, 2,2);
		
		ship = m_Players[1].getNextShip();
		m_Players[1].setNextShip();
		m_Players[1].updateBoard(ship, 3,3);
		
		ship = m_Players[1].getNextShip();
		m_Players[1].setNextShip();
		m_Players[1].updateBoard(ship, 4,4);
		
		m_Players[1].setNextShip();
		m_Players[1].updateBoard(ship, 5,5);
		ship = m_Players[1].getNextShip();
		
		m_Players[1].setNextShip();
		m_Players[1].updateBoard(ship, 6,6);
		ship = m_Players[1].getNextShip();
	}
	public void nextTurn()
	{
		if(m_CurrentPlayerIndex == m_Players.length -1)
		{
			m_CurrentPlayer = m_Players[0];
			m_CurrentPlayerIndex = 0;
		}else
		{
			m_CurrentPlayer = m_Players[++m_CurrentPlayerIndex];
		}
	}
	public void fire()
	{
		//System.out.println(m_CurrentPlayer.getNumOfSelectedTargets() + " Targets: Name: " + m_CurrentPlayer.getName());
		checkIfHit();
	}
	public void PlayerSelectedTarget(int x,int y)
	{
		m_TargetLoc[m_CurrentPlayer.getNumOfSelectedTargets()-1] = new Location(x,y);
		m_GameWindow.updateActionConsole(m_CurrentPlayer.getName() + ":  Selected Target...\n\t" +
										 m_CurrentPlayer.getName() + ": Has " + (5 - m_CurrentPlayer.getNumOfSelectedTargets()) + " Left");
		System.out.println(m_CurrentPlayer.getNumOfSelectedTargets() + " num of targets");
	}
	public void checkIfHit()
	{/*
		for(int i = 0; i < 5; i++)
		{
			if()
			{
				
			}
		}*/
	}
}