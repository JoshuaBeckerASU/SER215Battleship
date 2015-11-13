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
    private LobbySlot m_Slots[];
	private Player m_CurrentPlayer;
	private int m_CurrentPlayerIndex;
	private int m_difficulty;
	private int m_NumOfGames;
	private GameWindow m_GameWindow;
	private JFrame m_OldWindow;
	private LoadAssets m_Assets;
	private Location m_TargetLoc[];
    private static Game m_CurrentGame;
	
	Game()
	{
		
	}
	Game(int difficulty, LobbySlot slots[], LoadAssets assets)
	{
		m_Assets = assets;
        m_Slots = slots;
		m_difficulty = difficulty;
		m_CurrentPlayerIndex = 0;
		m_NumOfGames = 0;
		m_TargetLoc = new Location[5];
        m_CurrentGame = this;
	}
    public void setUpGame(Game game)
    {
        m_CurrentGame = game;
        
        fillPlayers();
        setUpBoards();
    }
	
	public void startGame()
	{
        m_CurrentPlayer = m_Players[0];
        getOpponentPlayer().enableBoard();
		m_GameWindow = new GameWindow(m_CurrentGame, m_Assets);
	}
    
    public void setUpBoards()
    {
        if(m_Players[0].isHuman() && !m_Players[0].allShipsSet())
        {
            System.out.println("here");
            new SetUpBoardWindow(m_Players[0], m_Assets);
        }else if(m_Players.length > 1 && m_Players[1].isHuman() && !m_Players[1].allShipsSet())
        {
            System.out.println("there");
            new SetUpBoardWindow(m_Players[1], m_Assets);
        }else if(m_Players.length > 2 && m_Players[2].isHuman() && !m_Players[2].allShipsSet())
        {
            System.out.println("there");
            new SetUpBoardWindow(m_Players[2], m_Assets);
        }else
        {
            startGame();
        }
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
	
	public Player getOpponentPlayer()// will need to switch to get opponents board.
	{
		if(m_CurrentPlayerIndex == m_Players.length-1)
		{
			return m_Players[0];
		}else
		{
			return m_Players[m_CurrentPlayerIndex+1];
		}
	}

	/**TEMP METHOD fillPlayers
	* fillPlayers with some value
	**/
	private void fillPlayers()
	{
        int count = 0;
        for(int i = 0; i < 3; i++)
        {
            if(m_Slots[i].getisActive().isSelected())count++;
        }
        m_Players = new Player[count];
        for(int i = 0; i < m_Players.length; i++)
            if(((String) m_Slots[i].getType().getSelectedItem()).compareTo("Computer Player") == 0)
            {
                m_Players[i] = new Player(m_Slots[i].getNameTA().getText(), false, m_Assets,m_CurrentGame);
                fillAI(m_Players[i]);
            } 
            else
                m_Players[i] = new Player(m_Slots[i].getNameTA().getText(), true, m_Assets, m_CurrentGame);
	}
	
	/*	Primary method utilized in populating 
	*	the designated AI player's pieces to the board upon setup.
	*/
	public void fillAI(Player player)
	{
		Board AI_Board = player.getBoardObject(); // Need to access A.I. Board owned by Player. 
		Random random = new Random();
		int randX = 0;
		int randY = 0;
		
		// Placing Ships on Board
		for(int i=0;i<5;i++){
			// RANDOM VALUE INSTANTIATION
			randX = random.nextInt(AI_Board.getNumCols())+1; // Shifting from 0-15 --> 1 - 16
			randY = random.nextInt(AI_Board.getNumRows())+1;
			int randOrientation = random.nextInt(2); // boolean interpretation for randomly flipping ships. 
			System.out.println("X: " + randX + "Y: " + randY);
			
			// SHIP PLACEMENT
			Ship ship = m_Players[1].getNextShip();
			player.setNextShip();
			player.updateBoard(ship, randX,randY, "DOWN");
			if(randOrientation == 1) player.flipAxis(ship);
                player.addToTaken(ship.x(),ship.y(),ship);
		}
	}
	public void nextTurn()
	{
        m_CurrentPlayer.enableBoard();
        getOpponentPlayer().disableBoard();
        getOpponentPlayer().setExitedIcon(m_TargetLoc[4].x(),m_TargetLoc[4].y());
        
		if(m_CurrentPlayerIndex == m_Players.length -1)
		{
			m_CurrentPlayer = m_Players[0];
			m_CurrentPlayerIndex = 0;
		}else
		{
			m_CurrentPlayerIndex++;
			m_CurrentPlayer = m_Players[m_CurrentPlayerIndex];
		}
		
       //m_CurrentPlayer.disableBoard();
       //getOpponentPlayer().enableBoard();
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
		Random rand = new Random(System.currentTimeMillis());
		m_GameWindow.resetActionConsole();
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
		m_GameWindow.resetActionConsole();
		m_GameWindow.updateActionConsole("Waiting for " + m_CurrentPlayer.getName() + " To Take Turn\n\n" +
										 "Has " + (5 - m_CurrentPlayer.getNumOfSelectedTargets()) + "left");
										 

	}
	public void PlayerSelectedTarget(int x,int y)
	{
		m_TargetLoc[m_CurrentPlayer.getNumOfSelectedTargets()-1] = new Location(x,y);
		checkIfHit();
	}
	
	public void checkIfHit()
	{
		int index = m_CurrentPlayer.getNumOfSelectedTargets()-1;
		int x = m_TargetLoc[index].x();
		int y = m_TargetLoc[index].y();
		String result = getOpponentPlayer().checkHit(x,y);
        JLabel tmp = ((JLabel) getOpponentPlayer().getTargetBoard()[x].getComponent(y));
		switch(result)
		{
			case "HIT":
						if(m_CurrentPlayer.isHuman())
						{
							BoardMouseAction.setIcon(m_Assets.getImage("HitMarker"));
						}else
						{
							tmp.setIcon(m_Assets.getImage("HitMarker"));
						}
                        tmp.setText("HIT");
						m_GameWindow.updateActionConsole("HIT On Location x = " + x + " y = " + y+ "\n\n"+ (5 - m_CurrentPlayer.getNumOfSelectedTargets()) + " Shots Left\n");
					break;
					
			case "MISS":
						if(m_CurrentPlayer.isHuman())
						{
							BoardMouseAction.setIcon(m_Assets.getImage("Target"));
						}else
						{
							tmp.setIcon(m_Assets.getImage("Target"));
						}
                        tmp.setText("MISS");
						m_GameWindow.updateActionConsole("MISS On Location x = " + x + " y = " + y +"\n\n"+ (5 - m_CurrentPlayer.getNumOfSelectedTargets()) + " Shots Left\n");
					break;
			default: //Ship is Sunk
                        BoardMouseAction.setIcon(m_Assets.getImage("HitMarker"));
                        tmp.setText("HIT");
						getOpponentPlayer().showShip(result);
						m_GameWindow.updateActionConsole(m_CurrentPlayer.getName() + " Sunk " + getOpponentPlayer().getName() + "'s " + result);
				break;
		}
		if(getOpponentPlayer().getShipsLeft() == 0)
		{
            gameOver();
		}
	}
    public void gameOver()
    {
		m_GameWindow.updateActionConsole("\nGAME OVER\n" + getOpponentPlayer().getName() + " Has Lost...");
        // update player states here
		GameOverWindow gameOver = new GameOverWindow(m_GameWindow.getFrame(), m_Assets);
        m_OldWindow.dispose();
    }
    public static Game getCurrentGame()
    {
        return m_CurrentGame;
    }
}