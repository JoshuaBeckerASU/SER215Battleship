/********************** 
Name: Player Object
Author: Joshua Becker
Create On: 10/29/15
Contributors:
***********************/
import javax.swing.*;

public class Player
{
	private String m_Name;
	private boolean m_Type;
	private int m_Wins;
	private int m_Losses;
	private LoadAssets m_Assets;
	private Board m_Board;
	private int m_NumOfSelectedTargets;//thats a long name...
	private int m_ShipsPlaced;
	private int m_ShipsLeft;
	private String m_ShipLocaions[][];
	private Game m_Game;
	public Ship m_AirCarr;
	public Ship m_Battleship;
	public Ship m_Sub;
	public Ship m_Cruiser;
	public Ship m_Destoyer;

	
	Player()
	{
		
	}
	Player(String name, boolean type, LoadAssets assets, Game game)
	{
		m_Assets = assets;
		m_Name = name;
		m_Type = type;
		m_Losses = 0;
		m_Wins = 0;
		m_ShipsPlaced = 0;
		m_NumOfSelectedTargets = 0;
		m_ShipsLeft = 5;
		m_Game = game;
		m_AirCarr = new Ship("AircraftCarrier", Ship.CARRIER_LENGTH, m_Assets);
		m_Battleship = new Ship("Battleship", Ship.BATTLESHIP_LENGTH, m_Assets);
		m_Sub = new Ship("Submarine", Ship.SUBMARINE_LENGTH, m_Assets);
		m_Cruiser = new Ship("Cruiser", Ship.CRUISER_LENGTH, m_Assets);
		m_Destoyer = new Ship("Destroyer", Ship.DESTROYER_LENGTH, m_Assets);
		m_Board = new Board(m_Assets, this, m_Game);
	}
	
	public JPanel getBoard()
	{
		return m_Board.getBoard();
	}
	
	public Board getBoardObject(){
	
		return m_Board;
	
	}
	public void enableBoard()
    {
        m_Board.enableMouseListeners();
    }
    public void disableBoard()
    {
        m_Board.disableMouseListeners();
    }
	public JPanel getBoardHide()
	{
		return m_Board.getBoardHide();
	}
	public int getShipsLeft()
	{
		return m_ShipsLeft;
	}
	public void decShipsLeft()
	{
		m_ShipsLeft--;
	}
	public int getNumOfSelectedTargets()
	{
		return m_NumOfSelectedTargets;
	}
	public String getName()
	{
		return m_Name;
	}
	public boolean isHuman()
	{
		return m_Type;
	}
	public int getWins()
	{
		return m_Wins;
	}
	public int getLosses()
	{
		return m_Losses;
	}
	public int getNumGames()
	{
		return m_Losses + m_Wins;
	}
	public void resetShips()
	{
		m_AirCarr.reset();
		m_Battleship.reset();
		m_Sub.reset();
		m_Cruiser.reset();
		m_Destoyer.reset();
	}
	public void incNumOfSelTargets()
	{
		m_NumOfSelectedTargets++;
	}
	public void resetShots()
	{
		m_NumOfSelectedTargets = 0;
	}
	public void startBoardSetup()
	{
		setNextShip();
	}
    public void setExitedIcon(int x, int y)
    {
        m_Board.setExitedIcon(x, y);
    }
	public JLabel[] getTargetBoard()
	{
		return m_Board.getTargetBoard();
	}
    /**getShip
	* gets the ship by name
	* @param int: index
	* @return: Ship object.
	**/
	public Ship getShip(String name)
	{
		switch(name)
		{
			case "AircraftCarrier": return m_AirCarr;
			case "Battleship":      return m_Battleship; 
			case "Submarine":       return m_Sub;
			case "Cruiser":         return m_Cruiser;
			case "Destroyer":       return m_Destoyer;
			default: return null;
		}
	}
	
	/**getShip
	* gets the ship at a index.
	* @param int: index
	* @return: Ship object.
	**/
	public Ship getShip(int index)
	{
		switch(index)
		{
			case 0: 	return m_AirCarr;
			case 1:     return m_Battleship; 
			case 2:     return m_Sub;
			case 3:     return m_Cruiser;
			case 4:     return m_Destoyer;
			default:    return null;
		}
	}
	
	/**updateBoard
	* updates the board with the ship object and the new x, y values.
	* @param Ship: object to be updated
	* @param int: x location on grid
	* @param int: y location on grid
	**/
	public void updateBoard(Ship ship, int x, int y, String direction)
	{
		m_Board.updateBoard(ship,x, y, direction);
	}
	
	/**getNextShip
	* gets the next ship inline to be placed
	**/
	public Ship getNextShip()
	{
		return getShip(m_Board.getShipCount());
	}
	
	/**setNextShip
	* sets's the a new ship to the board.
	* very similar to updateBoard.
	**/
	public void setNextShip()
	{
		if(m_Board.getShipCount() < 5)
		{
			m_Board.addNextShip(getShip(m_Board.getShipCount()));
		}
	}
	/**flipAxis
	* flips the axis of the ship and updates the board;
	* @param Ship: ship obj to be flipped.
	**/
	public void flipAxis(Ship ship)
	{
		m_Board.hideShip(ship,ship.x(),ship.y());
		ship.flipAxis();
		if(!m_Board.isOutOfBounds(ship.x(),ship.y(), ship) && !m_Board.hasShip(ship.x(),ship.y(),ship))
		{
			m_Board.showShip(ship,ship.x(),ship.y());
		}else
		{
			ship.flipAxis();
			m_Board.showShip(ship,ship.x(),ship.y());
		}
	}
	public void showShip(String name)
	{
		m_Board.showShip(getShip(name), getShip(name).x(),getShip(name).y());
	}
	public void addToTaken(int x, int y, Ship ship)
	{
		m_Board.addToTaken(x,y,ship);
		m_ShipsPlaced++;
	}
	public boolean allShipsSet()
	{
		return m_ShipsPlaced == 5;
	}
	public boolean hasShip(int x, int y, Ship ship)
	{
		return m_Board.hasShip(x, y, ship);
	}
	public String checkHit(int x, int y)
	{
		String loc = m_Board.getTakenLoc()[x][y];
		String message = "MISS";
		if(loc.compareTo("NOSHIP") != 0)
		{
			getShip(loc).decLives();
			if(getShip(loc).getLives() <= 0)
			{
				decShipsLeft();
				message = getShip(loc).getName();
			}else
			{
				message = "HIT";
			}
		}
		return message;
	}
	public boolean checkHit(int x, int y, boolean tmp)
	{
		JLabel label = m_Board.getTakenLabel(x,y);
        if(label.getIcon() == null)
        {
            return false;
        }else
        {
            return true;
        }
	}
	//Alec: I added this so i can use the getPlayer in game class and write the players name to the client in a print statement
	//I actually changed my implementation and dont need this but Im gonna leave it just in case someone adds to it in the future
	public String toString(){
		return m_Name;
	}
}