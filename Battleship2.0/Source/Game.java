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
    private boolean m_IsMultiPlayer;
	
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
        m_IsMultiPlayer = false;
	}
	Game(LoadAssets assets)
	{
		m_Assets = assets;
        m_Slots = slots;
		m_difficulty = difficulty;
		m_CurrentPlayerIndex = 0;
		m_NumOfGames = 0;
		m_TargetLoc = new Location[5];
        m_CurrentGame = this;
        m_IsMultiPlayer = true;
        setUpMultiPlayerGame();
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

	private void takeAITurn() // Three difficulty levels: 0: Random, 1: Basic Logic, 2: 30% chance of direct hit. (3rd not implemented yet).
	{
		Random rand = new Random();
		m_GameWindow.resetActionConsole();
		System.out.println("HERE! with Diffculty as:" + m_difficulty);

			switch (m_difficulty){			
				
				// EASY DIFFICULTY
				case 0: 
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
					}break;
					
				// MEDIUM DIFFICULTY	
				case 1: 
					int shots = 0;
					int i = 0;
					System.out.println("First Case");
					int[] cardinalArray = m_CurrentPlayer.getDirectionsShot();
					System.out.println("After cardinal assignment");
					while(shots < 5){ // For five shots...
						System.out.println("After For Loop: SHOT #: " + shots);
						if(getOpponentPlayer().getShipsLeft() == m_CurrentPlayer.getEnemyShipsLeft() && m_CurrentPlayer.wasEnemyHit() == true){ // Then last turn, there was a hit, but ship count didn't change: thus not sunk
							System.out.println("After Conditional");
							System.out.println("CARDINAL DIRECTION VALUE: " + m_CurrentPlayer.getDirectionsShot()[i]);
							System.out.println("Cardinal Direction INDEX Value: " + i);
							if(m_CurrentPlayer.getDirectionsShot()[i] == 0){ // If AI hasn't previously tried this direction...
								switch (i){ // Discerning direction based on i in the array
									case 0: // South
										m_CurrentPlayer.incNumOfSelTargets();
										shots++;
										PlayerSelectedTarget(m_CurrentPlayer.getLastHitX(),m_CurrentPlayer.getLastHitY()+1);
										if(getOpponentPlayer().checkHit(m_CurrentPlayer.getLastHitX(),m_CurrentPlayer.getLastHitY()+1) == "MISS"){
											m_CurrentPlayer.setDirectionsShot(i, 1);
											m_CurrentPlayer.setLastHitX(m_CurrentPlayer.getOriginHitX());
											m_CurrentPlayer.setLastHitY(m_CurrentPlayer.getOriginHitY());
											System.out.println("MISS!");
											i++;
										}
										break;
									case 1: // North
										m_CurrentPlayer.incNumOfSelTargets();
										shots++;
										PlayerSelectedTarget(m_CurrentPlayer.getLastHitX(),m_CurrentPlayer.getLastHitY()-1);
										if(getOpponentPlayer().checkHit(m_CurrentPlayer.getLastHitX(),m_CurrentPlayer.getLastHitY()-1) == "MISS"){
											m_CurrentPlayer.setDirectionsShot(i, 1);
											m_CurrentPlayer.setLastHitX(m_CurrentPlayer.getOriginHitX());
											m_CurrentPlayer.setLastHitY(m_CurrentPlayer.getOriginHitY());
											System.out.println("MISS!");
											i++;											
										}
										break;
									case 2: // East
										m_CurrentPlayer.incNumOfSelTargets();
										shots++;
										PlayerSelectedTarget(m_CurrentPlayer.getLastHitX()+1,m_CurrentPlayer.getLastHitY());
										if(getOpponentPlayer().checkHit(m_CurrentPlayer.getLastHitX()+1,m_CurrentPlayer.getLastHitY()) == "MISS"){
											m_CurrentPlayer.setDirectionsShot(i, 1);
											m_CurrentPlayer.setLastHitX(m_CurrentPlayer.getOriginHitX());
											m_CurrentPlayer.setLastHitY(m_CurrentPlayer.getOriginHitY());
											System.out.println("MISS!");
											i++;											
										}
										break;											
									case 3: // West
										m_CurrentPlayer.incNumOfSelTargets();
										shots++;											
										PlayerSelectedTarget(m_CurrentPlayer.getLastHitX()-1,m_CurrentPlayer.getLastHitY());
										if(getOpponentPlayer().checkHit(m_CurrentPlayer.getLastHitX()-1,m_CurrentPlayer.getLastHitY()) == "MISS"){
											m_CurrentPlayer.setDirectionsShot(i, 1);
											m_CurrentPlayer.setLastHitX(m_CurrentPlayer.getOriginHitX());
											m_CurrentPlayer.setLastHitY(m_CurrentPlayer.getOriginHitY());	
											System.out.println("MISS at!");	
											i++;											
										}
										break;
									default: // Should be an error
										System.out.println("i is " + i);
										i = 0;
								}
							}else if(m_CurrentPlayer.getDirectionsShot()[i] == 1)
								i++;
							
						}
						else if(m_CurrentPlayer.wasEnemyHit() == true && (getOpponentPlayer().getShipsLeft() != m_CurrentPlayer.getEnemyShipsLeft())){ // If a ship was not previously hit 
							// ... Then a ship was presumably sunk but was not updated.
							System.out.println("IN IF ELSE BLOCK");
							System.out.println("Opponent Player Ships: " + getOpponentPlayer().getShipsLeft() + "Current Player Tracking: " + m_CurrentPlayer.getEnemyShipsLeft());
							m_CurrentPlayer.setOriginHitX(-1);
							m_CurrentPlayer.setOriginHitY(-1);
							m_CurrentPlayer.setEnemyWasHit(false); // We're not continuing to attack a ship that has yet to be sunk.
							m_CurrentPlayer.setEnemyShipsLeft(getOpponentPlayer().getShipsLeft());
							System.out.println("AFTER UPDATE: Opponent Player Ships: " + getOpponentPlayer().getShipsLeft() + "Current Player Tracking: " + m_CurrentPlayer.getEnemyShipsLeft());					
							// Decrement ship left compared to opponent.
							// Need to reset ShipDirection Array, too
							}
						else{
							System.out.println("IN ELSE");
							m_CurrentPlayer.incNumOfSelTargets();
							PlayerSelectedTarget(rand.nextInt(15) + 1,rand.nextInt(17)+1);
							shots++;
							} 
					}
					System.out.println("### AI TURN ENDED ###");
					nextTurn();		
					
			}// End Difficulty Switch block		
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
                            tmp.setText("HIT");
							//m_CurrentPlayer.setEnemyWasHit(true); // Enemy was Hit on last turn. 
							if(m_CurrentPlayer.getOriginHitX() == -1 && m_CurrentPlayer.getOriginHitY() == -1){ // Unnecessary to check both; but for the sake of testing...
								m_CurrentPlayer.setOriginHitX(x);
								m_CurrentPlayer.setOriginHitY(y);
								m_CurrentPlayer.setEnemyWasHit(true);
								System.out.println("HERE IN AWT");
							}
							m_CurrentPlayer.setLastHitX(x);
							m_CurrentPlayer.setLastHitY(y);
							System.out.println("Last Hits Were: " + x + y);
			
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
                            tmp.setText("MISS");
							System.out.println("Miss at " + x + ", " + y);
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