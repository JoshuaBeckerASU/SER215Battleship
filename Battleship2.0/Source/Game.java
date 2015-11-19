/*************************************
* @file: Game.java
* @author: Joshua Becker
* @date: 10/26/15
* @description:
*  
* @contributors:Jacob Leonard
*  
* @index
* [
*     m_: for member variables
*     g_: for global variables
*     s_: for static variables
* ]
* 
***************************************/
import javax.swing.*;
import java.util.*;
import java.io.*;


public class Game implements Serializable
{
    public static int MULTIPLAYERGAME = -1;
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
    private SetUpBoardWindow m_SetUpBoard_W;
    private WaitingScreenWindow m_WaitingScreen_W;
    private static Game m_CurrentGame;
    private boolean m_IsMultiplayer;
    public GameClient_ m_Client;
    private Thread m_Client_T;
    private int m_ShotCount = 0;
    private boolean m_Turn;
    
    private class getInputFromServer implements Runnable
    {
        private ObjectInputStream m_FromServer_OIS;
        private Object m_Object;
        getInputFromServer(ObjectInputStream fromServer)
        {
            m_FromServer_OIS = fromServer;
        }
        public void run()
        {
            try
            {
                m_Object = m_FromServer_OIS.readObject();
            }catch(IOException e)
            {
                System.out.println("IOException in get Input From Server class");
                System.err.println(e);
            }catch(ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException in get Input From Server class");
                System.err.println(e);
                System.exit(1);
            }
            if(m_Object instanceof Location)
            {
                System.out.println("Getting Location From Server...\n");
                multiPlayerSelTarget(((Location)m_Object).x(), ((Location)m_Object).y(),m_Players[1],m_Players[0] , false, m_ShotCount);
                
                m_ShotCount++;
                m_Players[1].incNumOfSelTargets();
                System.out.println("ShotCount = "+m_ShotCount+"Got Location From Server\n" + (Location) m_Object);
                try
                {
                    getShotsFromServer();
                }catch(IOException e)
                {
                    System.out.println("IOException in getInputFromServer line 75");
                    System.err.println(e);
                }catch(ClassNotFoundException e)
                {
                    System.out.println("ClassNotFoundException in get Input From Server class line 79");
                    System.err.println(e);
                    System.exit(1);
                }
                
            }
        }
        public Object getObject()
        {
            return m_Object;
        }
    }
	
	Game()
	{
		
	}
	Game(int difficulty, LobbySlot slots[])//SInglePlayer
	{
		m_Assets = Main.s_Assets;
        m_Slots = slots;
        
        m_difficulty = difficulty;
        m_IsMultiplayer = false;
        
		m_CurrentPlayerIndex = 0;
		m_NumOfGames = 0;
		m_TargetLoc = new Location[5];
        m_CurrentGame = this;
	}
	Game(GameClient_ client, LobbySlot slots[])//MultiPlayer
	{
		m_Assets = Main.s_Assets;
        m_Slots = slots;

        m_difficulty = 1;
        m_IsMultiplayer = true;
        m_Client = client;
        
		m_CurrentPlayerIndex = 0;
		m_NumOfGames = 0;
		m_TargetLoc = new Location[5];
        m_CurrentGame = this;
	}
    public void sendMessage(String message)
    {
        m_Client.sendMessage(new String(message));
    }
    public void setBoardObject(Board board)
    {
        m_Players[1].setBoardObject(board);
    }
    public void setUpGame(Game game)
    {
        m_CurrentGame = game;
        
        fillPlayers();
        setUpBoards();
    }
	public void showWaitingScreen()
    {
       m_SetUpBoard_W.setEnabled(false);
       m_WaitingScreen_W = new WaitingScreenWindow();
    }
    public void hideWaitingScreen()
    {
        m_SetUpBoard_W.setEnabled(true);
        m_WaitingScreen_W.dispose();
    }
	public void startGame()
	{
        m_CurrentPlayer = m_Players[0];
        getOpponentPlayer().enableBoard();
		m_GameWindow = new GameWindow(m_CurrentGame);
        m_OldWindow = m_GameWindow.getFrame();
	}
	public void startMultiplayerGame()
	{
        System.out.println("StartMultiPlayerGame");
        m_GameWindow = new GameWindow(m_CurrentGame);
        m_OldWindow = m_GameWindow.getFrame();
        
        m_Client.setGameWindow(m_GameWindow);
        nextTurn();
	}
    public boolean isMultiplayer()
    {
        return m_IsMultiplayer;
    }
    public void setUpBoards()
    {
        if(m_Players[0].isHuman() && !m_Players[0].allShipsSet())
        {
            System.out.println("here");
            m_SetUpBoard_W = new SetUpBoardWindow(m_Players[0]);
        }else if(m_Players.length > 1 && m_Players[1].isHuman() && !m_Players[1].allShipsSet() && !m_IsMultiplayer)
        {
            System.out.println("there");
            m_SetUpBoard_W = new SetUpBoardWindow(m_Players[1]);
        }else if(m_Players.length > 2 && m_Players[2].isHuman() && !m_Players[2].allShipsSet() && !m_IsMultiplayer)
        {
            System.out.println("there");
            m_SetUpBoard_W = new SetUpBoardWindow(m_Players[2]);
        }else
        {
            if(!m_IsMultiplayer)
                startGame();
            else
                startMultiplayerGame();
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
    public Player getPlayer(int index)
    {
        return m_Players[index];
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
        for(int i = 0; i < 2; i++)
        {
            if(m_Slots[i].getisActive().isSelected())count++;
        }
        m_Players = new Player[count];
        for(int i = 0; i < m_Players.length; i++)
            if(((String) m_Slots[i].getType().getSelectedItem()).compareTo("Computer Player") == 0)
            {
                m_Players[i] = new Player(m_Slots[i].getNameTA().getText(), false,m_CurrentGame);
                fillAI(m_Players[i]);
            } 
            else
                m_Players[i] = new Player(m_Slots[i].getNameTA().getText(), true, m_CurrentGame);
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
        //getOpponentPlayer().setExitedIcon(m_TargetLoc[4].x(),m_TargetLoc[4].y());
        if(!m_IsMultiplayer)
        {
            m_CurrentPlayer.enableBoard();
            getOpponentPlayer().disableBoard();
            if(m_CurrentPlayerIndex == m_Players.length -1)
            {
                m_CurrentPlayer = m_Players[0];
                m_CurrentPlayerIndex = 0;
            }else
            {
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
        }else
        {
            try
            {
                m_GameWindow.resetActionConsole();
                m_Players[0].disableBoard();
                m_Players[1].disableBoard();
                //System.out.println("Waiting for server boolean values");
                m_Turn = (boolean) m_Client.getInputStream().readObject();
                
                if(m_Turn)
                {
                    m_Players[1].enableBoard();
                    //System.out.println("Your Players turn");
                    m_CurrentPlayer = m_Players[0];
                    m_CurrentPlayerIndex = 0;
                }else
                {
                    //System.out.println("Other Players turn");
                    m_CurrentPlayerIndex = 1;
                    m_CurrentPlayer = m_Players[1];
                    getShotsFromServer();
                    //nextTurn();
                }
            }catch(IOException e)
            {
                System.out.println("IOException in taketurn");
                System.exit(1);
            }catch(ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException in taketurn");
                System.exit(1);
            }
        }
	}
    public void getShotsFromServer() throws IOException, ClassNotFoundException
    {
        if(m_ShotCount != 5)
        {
            m_Players[0].disableBoard();
            m_Players[1].disableBoard();
            BoardMouseAction.setIcon(BoardMouseAction.getIcon());
            getInputFromServer input = new getInputFromServer(m_Client.getInputStream());
            
            Thread thread = new Thread(input);
            thread.start();

        }else
        {
            nextTurn();
            m_ShotCount = 0;
        }
            
    }
    public void multiPlayerSelTarget(int x, int y, Player playerOffence, Player playerDefence, boolean player,int shotNum)
    {
		m_TargetLoc[shotNum] = new Location(x,y);
        //System.out.println(playerDefence.getStringBoard()[x][y]);
        JLabel tmp = ((JLabel) playerDefence.getTargetBoard()[x].getComponent(y));
        String result = playerDefence.getStringBoard()[x][y];
		switch(result)
        {
            case "MARKED": playerOffence.decNumOfSelTargets();
                        break;
            
            case "NOSHIP": 
                        if(m_Turn)
                            BoardMouseAction.setIcon(m_Assets.getImage("Target"));
                        else
                            tmp.setIcon(m_Assets.getImage("Target"));
                        m_GameWindow.updateActionConsole("Miss On Location x = " + x + " y = " + y+ "\n\n"+ (4 - shotNum) + " Shots Left\n");
                        break;
            default:
                        if(playerDefence.getShip(result) != null)
                        {
                            playerDefence.getShip(result).decLives();
                            
                            if(m_Turn)
                                BoardMouseAction.setIcon(m_Assets.getImage("HitMarker"));
                            else
                                tmp.setIcon(m_Assets.getImage("HitMarker"));
                            
                            m_GameWindow.decFleetHealth(player);
                            m_GameWindow.updateActionConsole("HIT On Location x = " + x + " y = " + y+ "\n\n"+ (4 - shotNum) + " Shots Left\n");
                            
                            if(playerDefence.getShip(result).getLives() == 0)
                            {
                                playerDefence.showShip(result);
                            }
                        }
                        break;
        }
        playerDefence.getStringBoard()[x][y] = "MARKED";
        if(isYourTurn())
        {
            try
            {
                //System.out.println("SENDING NEW Location" +  x + "  " + y);
                m_Client.getOutputStream().writeObject(new Location(x,y));
                m_Client.getOutputStream().flush();
            }catch(IOException e)
            {
                System.out.println("IOException in playerSelectedTarget");
                System.exit(1);
            }
        }
        if(m_Players[0].getFleetHealth() == 0 || m_Players[1].getFleetHealth() == 0)
        {
            gameOver();
        }
    }
    public boolean isYourTurn()
    {
        return m_Turn;
    }
    /*public void takeAITurn()
    {
        m_GameWindow.resetActionConsole();
        Random rand = new Random();
		while(m_CurrentPlayer.getNumOfSelectedTargets() < 5)
		{
            int x = rand.nextInt(15);
            int y = rand.nextInt(18); 
			m_CurrentPlayer.incNumOfSelTargets();
		    playerSelectedTarget(x,y);
		}
        nextTurn();		
    }*/
	private void takeAITurn() // Three difficulty levels: 0: Random, 1: Basic Logic, 2: 30% chance of direct hit. (3rd not implemented yet).
	{
		Random rand = new Random();
		m_GameWindow.resetActionConsole();
		System.out.println("HERE! with Difficulty as:" + m_difficulty);

		switch (m_difficulty){			
			
			// EASY DIFFICULTY
			case 0: 
				while(m_CurrentPlayer.getNumOfSelectedTargets() < 5)
				{
					int x = rand.nextInt(16);
					int y = rand.nextInt(18);
					m_CurrentPlayer.incNumOfSelTargets();
					playerSelectedTarget(x,y);
				}
				nextTurn();
				break;
				
			// MEDIUM DIFFICULTY	
			case 1: 
				int shots = 0;
				int i = 0;
				System.out.println("HERE");
				/*if(currentTarget == "NULL" && m_CurrentPlayer.isShipStackEmpty()){
					System.out.println("Ship is empty.");
					currentTarget = m_CurrentPlayer.getCurrentTarget();
			
				}*/
				if(m_CurrentPlayer.getCurrentTarget() == "NULL" && !(m_CurrentPlayer.isShipStackEmpty())){
					m_CurrentPlayer.setCurrentTarget(m_CurrentPlayer.popShip());
					System.out.println("\nPOPPED SHIP: " + m_CurrentPlayer.getCurrentTarget());
					int x = m_CurrentPlayer.popHitX();
					int y = m_CurrentPlayer.popHitY();
					m_CurrentPlayer.setOriginHitX(x);
					m_CurrentPlayer.setOriginHitY(y);
					m_CurrentPlayer.setLastHitX(x);
					m_CurrentPlayer.setLastHitY(y);
					
					System.out.println("\n\nCURRENT SHIP TO HUNT: " + m_CurrentPlayer.getCurrentTarget());
					System.out.println("ORIGIN TARGET: " + x + ", " + y);
				
				}			 
				// Get a past target and coordinates.
				System.out.println("\n Case 1: Medium Difficulty\n");

				while(shots < 5){ // For five shots...
					System.out.println("After For Loop: SHOT #: " + shots);
					if(m_CurrentPlayer.getCurrentTarget() != "NULL"){ // Then last turn, there was a hit, but ship count didn't change: thus not sunk // getOpponentPlayer().getShipsLeft() == m_CurrentPlayer.getEnemyShipsLeft() && m_CurrentPlayer.wasEnemyHit() == true
						System.out.println("After Conditional" + m_CurrentPlayer.getCurrentTarget());
						System.out.println("CARDINAL DIRECTION VALUE: " + m_CurrentPlayer.getDirectionsShot()[i]);
						System.out.println("Cardinal Direction INDEX Value: " + i);
						if(m_CurrentPlayer.getDirectionsShot()[i] == 0){ // If AI hasn't previously tried this direction...
							switch (i){ // Discerning direction based on i
								case 0: // South
									System.out.println("TRYING SOUTH");
									
									if(!(m_CurrentPlayer.getLastHitY()+1 > 18)){
										m_CurrentPlayer.incNumOfSelTargets();
										shots++;
										playerSelectedTarget(m_CurrentPlayer.getLastHitX(),m_CurrentPlayer.getLastHitY()+1);
									}
									
									if(!(m_CurrentPlayer.getLastHitY()+1 > 18) && getOpponentPlayer().getStringBoard()[m_CurrentPlayer.getLastHitX()][m_CurrentPlayer.getLastHitY()+1] == "MARKED"){
										m_CurrentPlayer.setDirectionsShot(i, 1);
										m_CurrentPlayer.setLastHitX(m_CurrentPlayer.getOriginHitX());
										m_CurrentPlayer.setLastHitY(m_CurrentPlayer.getOriginHitY());
										System.out.println("MISS!");
										i++;
									}
									if(m_CurrentPlayer.getLastHitY() == 18){
										m_CurrentPlayer.setDirectionsShot(i, 1);
										System.out.println("\n\n AT BORDER");
									}
									break;
								case 1: // North
									System.out.println("TRYING NORTH");
									
									if(!(m_CurrentPlayer.getLastHitY()-1 < 0)){
										m_CurrentPlayer.incNumOfSelTargets();
										shots++;
										playerSelectedTarget(m_CurrentPlayer.getLastHitX(),m_CurrentPlayer.getLastHitY()-1);
									}
				
									if(!(m_CurrentPlayer.getLastHitY()-1 < 0) && getOpponentPlayer().getStringBoard()[m_CurrentPlayer.getLastHitX()][m_CurrentPlayer.getLastHitY()-1] == "MARKED"){
										m_CurrentPlayer.setDirectionsShot(i, 1);
										m_CurrentPlayer.setLastHitX(m_CurrentPlayer.getOriginHitX());
										m_CurrentPlayer.setLastHitY(m_CurrentPlayer.getOriginHitY());
										System.out.println("MISS!");
										i++;											
									}
									if(m_CurrentPlayer.getLastHitY() == 0){
										m_CurrentPlayer.setDirectionsShot(i, 1);
										System.out.println("\n\n AT BORDER");
									}								
									break;
									
								case 2: // East
									System.out.println("TRYING EAST");
									
									if(!(m_CurrentPlayer.getLastHitX()+1 > 15)){
										m_CurrentPlayer.incNumOfSelTargets();
										shots++;
										playerSelectedTarget(m_CurrentPlayer.getLastHitX()+1,m_CurrentPlayer.getLastHitY());
									}
									
									if(!(m_CurrentPlayer.getLastHitX()+1 > 15) && getOpponentPlayer().getStringBoard()[m_CurrentPlayer.getLastHitX()+1][m_CurrentPlayer.getLastHitY()] == "MARKED"){
										m_CurrentPlayer.setDirectionsShot(i, 1);
										m_CurrentPlayer.setLastHitX(m_CurrentPlayer.getOriginHitX());
										m_CurrentPlayer.setLastHitY(m_CurrentPlayer.getOriginHitY());
										System.out.println("MISS!");
										i++;											
									}
									if(m_CurrentPlayer.getLastHitX() == 15){
										m_CurrentPlayer.setDirectionsShot(i, 1);
										System.out.println("\n\n AT BORDER");
									}
									break;	
									
								case 3: // West
									System.out.println("TRYING WEST");		

									if(!(m_CurrentPlayer.getLastHitX()-1 < 0)){
										m_CurrentPlayer.incNumOfSelTargets();
										shots++;											
										playerSelectedTarget(m_CurrentPlayer.getLastHitX()-1,m_CurrentPlayer.getLastHitY());
									}
								
									if(!(m_CurrentPlayer.getLastHitX()-1 < 0) && getOpponentPlayer().getStringBoard()[m_CurrentPlayer.getLastHitX()-1][m_CurrentPlayer.getLastHitY()] == "MARKED"){
										m_CurrentPlayer.setDirectionsShot(i, 1);
										m_CurrentPlayer.setLastHitX(m_CurrentPlayer.getOriginHitX());
										m_CurrentPlayer.setLastHitY(m_CurrentPlayer.getOriginHitY());	
										System.out.println("MISS at!");										
									}
									if(m_CurrentPlayer.getLastHitX() == 0){
										m_CurrentPlayer.setDirectionsShot(i, 1);
										System.out.println("\n\n AT BORDER");
									}
									break;
									
								default: // Should there be an error, circle back directionIndex (i) to 0.
									System.out.println("i is " + i);
									i = 0;
							}
						}else if(m_CurrentPlayer.getDirectionsShot()[i] == 1 ){
							if(i<3){
								System.out.println("Incrementing i...");
								i++;
							}
							else{
								i = 0;
								m_CurrentPlayer.resetDirectionsShot();
							}
						}	
					}
					else{
						System.out.println("IN ELSE");
						m_CurrentPlayer.incNumOfSelTargets();
						
						int randX = rand.nextInt(16);
						int randY = rand.nextInt(19);
						
						while(getOpponentPlayer().getStringBoard()[randX][randY] == "MARKED"){
						
							randX = rand.nextInt(16);
							randY = rand.nextInt(19);

						}
						playerSelectedTarget(randX,randY);
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
	public void playerSelectedTarget(int x,int y)
	{
        System.out.println("PlayerSelected Target");
		m_TargetLoc[m_CurrentPlayer.getNumOfSelectedTargets()-1] = new Location(x,y);
        System.out.println(getOpponentPlayer().getStringBoard()[x][y]);
        JLabel tmp = ((JLabel) getOpponentPlayer().getTargetBoard()[x].getComponent(y));
        String result = getOpponentPlayer().getStringBoard()[x][y];
		switch(result)
        {
            case "MARKED": getCurrentPlayer().decNumOfSelTargets();
                        break;
            
            case "NOSHIP": 
                        System.out.println("here");
						if(m_CurrentPlayer.isHuman() && !isMultiplayer())
						{
							BoardMouseAction.setIcon(m_Assets.getImage("Target"));
						}else
						{
							tmp.setIcon(m_Assets.getImage("Target"));
							System.out.println("Miss at " + x + ", " + y);
						}
						m_GameWindow.updateActionConsole("MISS On Location x = " + x + " y = " + y +"\n\n"+ (5 - m_CurrentPlayer.getNumOfSelectedTargets()) + " Shots Left\n");
            default:
                        if(m_CurrentPlayer.getShip(result) != null)
                        {
                            getOpponentPlayer().getShip(result).decLives();
							   if(m_CurrentPlayer.isHuman() && !isMultiplayer())
                            {
                            	BoardMouseAction.setIcon(m_Assets.getImage("HitMarker"));
                            }else
                            {
                            	tmp.setIcon(m_Assets.getImage("HitMarker"));
                            	//m_CurrentPlayer.setEnemyWasHit(true); // Enemy was Hit on last turn. 
                            	if(m_CurrentPlayer.getOriginHitX() == -1 && m_CurrentPlayer.getOriginHitY() == -1){ // Unnecessary to check both; but for the sake of testing...
                            		m_CurrentPlayer.setCurrentTarget(result);
									m_CurrentPlayer.setOriginHitX(x);
                            		m_CurrentPlayer.setOriginHitY(y);
                            		m_CurrentPlayer.setEnemyWasHit(true);
                            		System.out.println("HERE IN AWT");
                            	}
                            	m_CurrentPlayer.setLastHitX(x);
                            	m_CurrentPlayer.setLastHitY(y);
                            	System.out.println("Hit at " + x +", "+ y);
								
								// The following implements the function of allowing the AI to remember ships it hit.
								if(m_CurrentPlayer.getShip(result).getName() != m_CurrentPlayer.getCurrentTarget()){
									System.out.println("\n\nADDING " + m_CurrentPlayer.getShip(result).getName() + " to stack.");
									m_CurrentPlayer.pushHitX(x);
									m_CurrentPlayer.pushHitY(y);
									m_CurrentPlayer.pushShip(m_CurrentPlayer.getShip(result).getName());
								}
                            
                            }
                            m_GameWindow.decFleetHealth(m_CurrentPlayerIndex == 0);
                            m_GameWindow.updateActionConsole("HIT On Location x = " + x + " y = " + y+ "\n\n"+ (5 - m_CurrentPlayer.getNumOfSelectedTargets()) + " Shots Left\n");
							
							if(getOpponentPlayer().getShip(result).getLives() == 0) // If ship isDead. 
							{
								getOpponentPlayer().showShip(result);
								m_GameWindow.decFleetHealth(m_CurrentPlayerIndex == 0);
								System.out.println("\nRESETTING\n");
								m_CurrentPlayer.setCurrentTarget("NULL");
								m_CurrentPlayer.setOriginHitX(-1);
								m_CurrentPlayer.setOriginHitY(-1);
								m_CurrentPlayer.setEnemyWasHit(false);
								m_CurrentPlayer.setEnemyShipsLeft(getOpponentPlayer().getShipsLeft());
								m_CurrentPlayer.resetDirectionsShot();
							}

                        }
					break;
        }
        getOpponentPlayer().getStringBoard()[x][y] = "MARKED";
        if(m_Players[0].getFleetHealth() == 0 || m_Players[1].getFleetHealth() == 0)
        {
            gameOver();
        }
	}
	
    public void gameOver()
    {
		m_GameWindow.updateActionConsole("\nGAME OVER\n" + getOpponentPlayer().getName() + " Has Lost...");
        // update player states here
		GameOverWindow gameOver = new GameOverWindow(m_GameWindow.getFrame());
    }
    public static Game getCurrentGame()
    {
        return m_CurrentGame;
    }
}