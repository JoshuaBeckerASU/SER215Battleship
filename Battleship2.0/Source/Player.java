/********************** 
Name: Player Object
Author: Joshua Becker
Create On: 10/29/15
Contributors: Jacob Leonard
***********************/
import javax.swing.*;
import java.io.*;
import java.util.*;


public class Player implements Serializable
{
	private String m_Name;
	private boolean m_Type;
	private int m_Wins;
	private int m_Losses;
	
	// AI Implementation: condense once implemented
	private int m_enemyShipsLeft;
	private boolean m_enemyWasHit;
	private int m_originHitX;
	private int m_originHitY;
	private int m_lastHitX;
	private int m_lastHitY; 
	private int[] m_directionsShot;
	private String m_currentTarget;
	private	Stack<Integer> xHits = new Stack<Integer>();
	private	Stack<Integer> yHits = new Stack<Integer>();
	private	Stack<String> shipStack = new Stack<String>(); // Associated with xHits and yHits for remember what other ships were hit.
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
		
		m_enemyShipsLeft = 5;
		m_enemyWasHit = false;
		m_currentTarget = "NULL";
		m_originHitX = -1;
		m_originHitY = -1;
		m_lastHitX = -1;
		m_lastHitY = -1;
		m_directionsShot = new int[] {0,0,0,0}; // Cardinal Directions from OriginHit [0]SOUTH, [1]NORTH, [2]EAST, [3]WEST | 0 indicates not Tried. 1 Indicates Tried.

		
		
		m_Game = game;
		m_AirCarr = new Ship("AircraftCarrier", Ship.CARRIER_LENGTH, m_Assets);
		m_Battleship = new Ship("Battleship", Ship.BATTLESHIP_LENGTH, m_Assets);
		m_Sub = new Ship("Submarine", Ship.SUBMARINE_LENGTH, m_Assets);
		m_Cruiser = new Ship("Cruiser", Ship.CRUISER_LENGTH, m_Assets);
		m_Destoyer = new Ship("Destroyer", Ship.DESTROYER_LENGTH, m_Assets);
		m_Board = new Board(m_Assets, this, m_Game);
	}
	public int getFleetHealth()
    {
        int health = 0;
        health = health + m_AirCarr.getLives()*6;
        health = health + m_Battleship.getLives()*6;
        health = health + m_Sub.getLives()*6;
        health = health + m_Cruiser.getLives()*6;
        health = health + m_Destoyer.getLives()*6;
        return health;
    }
	public JPanel getBoard()
	{
		return m_Board.getBoard();
	}
	
	public Board getBoardObject(){
	
		return m_Board;
	
	}
    public void setBoardObject(Board board)
    {
        m_Board = board;
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
	//////////////////////////
	// FOR AI IMPLEMENTATION//
	//////////////////////////	
	/* Many of these methods are more or less to keep track of important information between round cycles 
	**(i.e., if a ship that was hit was not sunk in the previous 5 shots)
	*/
	public int getEnemyShipsLeft(){ // get ENEMY ships left from previous round to compare against current number of enemy ships
		return m_enemyShipsLeft;
	}
	public void setEnemyShipsLeft(int num){
		m_enemyShipsLeft = num;
	}
	public void decEnemyShipsLeft(){
		m_enemyShipsLeft--;
	}
	
	public int getLastHitX(){
		return m_lastHitX;
	}
	public int getLastHitY(){
		return m_lastHitY;
	}
	public void setLastHitX(int lastHitX){
		m_lastHitX = lastHitX;
	}
	public void setLastHitY(int lastHitY){
		m_lastHitY = lastHitY;
	}
	
	public int getOriginHitX(){
		return m_originHitX;
	}
	public int getOriginHitY(){
		return m_originHitY;
	}
	public void setOriginHitX(int originHitX){
		m_originHitX = originHitX;
	}
	public void setOriginHitY(int originHitY){
		m_originHitY = originHitY;
	}

	public int[] getDirectionsShot(){
		return m_directionsShot;
	}
	public void setDirectionsShot(int index, int value){
		m_directionsShot[index] = value;
	}
	
	public void resetDirectionsShot(){ // Reset the cardinal direction.
		int newArray[] = {0,0,0,0};
		m_directionsShot = newArray;
	}

	public boolean wasEnemyHit(){
	
		return m_enemyWasHit;
	}
	public void setEnemyWasHit(boolean bool){
		m_enemyWasHit = bool;
	}
	
	public void setCurrentTarget(String shipName){
		m_currentTarget = shipName;
	}
	
	public String getCurrentTarget(){
		return m_currentTarget;
	}
	
	public int popHitX(){
		return (int)xHits.pop();
	}
	public int popHitY(){
		return (int)yHits.pop();
	}
	
	public void pushHitX(int x){
		xHits.push(x);
	}
	public void pushHitY(int y){
		yHits.push(y);
	}
	
	/*public boolean isHitXEmpty(){
		return xHits.empty();
	}*/
	
	public void pushShip(String shipName){
		shipStack.push(shipName);
	}
	public String popShip(){
		return shipStack.pop();
	}
	public boolean isShipStackEmpty(){
		System.out.println("Checking if ship stack is empty");
		boolean isEmpty = true;
		isEmpty = shipStack.isEmpty();
		return isEmpty;
	}
	//////////////////////////////////
	// END AI IMPLEMENTATION METHODS//
	//////////////////////////////////		
	
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
    public void decNumOfSelTargets()
    {
        m_NumOfSelectedTargets--;
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
    
    public void setShip(int index, Ship ship)
    {
		switch(index)
		{
			case 0: 	m_AirCarr = ship;
			case 1:     m_Battleship = ship;break; 
			case 2:     m_Sub = ship;break;
			case 3:     m_Cruiser = ship;break;
			case 4:     m_Destoyer = ship;break;
			default:    System.out.println("SHIP NOT FOUND in SETSHIP");break;
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
    /*
	public String checkHit(int x, int y)
	{
		String loc = m_Board.getTakenLoc()[x][y];
        System.out.println("SHIP: " + loc);
		String message = "MISS";
		if(loc.compareTo("NOSHIP") != 0)
		{
            if(loc.compareTo("USED") == 0)
            {
                message = "USED";
            }else if(getShip(loc).getLives() <= 0)
			{
                System.out.println("ship sunk");
                getShip(loc).decLives();
				decShipsLeft();
				message = getShip(loc).getName();
			}else
			{
                getShip(loc).decLives();
				message = "HIT";
			}
		}
        m_Board.getTakenLoc()[x][y] = "USED";
		return message;
	}*/
    public String[][] getStringBoard()
    {
        return m_Board.getTakenLoc();
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