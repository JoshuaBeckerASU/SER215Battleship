/********************** 
Name: Board object 
Author: Joshua Becker
Create On: 10/26/15
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
import java.util.*;

public class Board
{	
	private JLabel  m_GameBoardGrid_L[][], m_GameBoard_Y_P, m_GameBoard_X_P, m_GameBoardTargets_P;
	private JPanel m_GameBoards_P;
	private	JLabel m_GameBoard_X_L[], m_GameBoard_Y_L[], m_GameBoardTargets_L[];
	private int m_ScreenHeight, m_ScreenWidth;
	private Game m_Game;
	private LoadAssets m_Assets;
	private Player m_CurrentPlayer;
	private Cursor m_CrossHair_C;
	private int m_ShipCount;
	private int m_boardWidth;
	private int m_boardHight;
	private final int m_NUM_OF_COL, m_NUM_OF_ROWS;
	private String m_HasShip[][];
	
	Board(LoadAssets assets, Player currentPlayer, Game game)
	{
		/*Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor m_CrossHair_C = toolkit.createCustomCursor(m_Assets.getImage("BattleShip_Y", 1) , new Point(20, 
           20), "img");*/
		m_NUM_OF_COL = 16;
		m_NUM_OF_ROWS = 19;
		m_Game = game;
		m_Assets = assets;
		m_CurrentPlayer = currentPlayer;
		m_ShipCount = 0;
	    m_boardHight = m_Assets.getImage("GameBoard").getIconHeight();
		m_boardWidth = m_Assets.getImage("GameBoard").getIconWidth();
		m_HasShip = new String[m_NUM_OF_COL][m_NUM_OF_ROWS];
		for(int i = 0; i < m_NUM_OF_COL; i++)
		{
			Arrays.fill(m_HasShip[i],"NOSHIP");
		}
		
		createBoards();
		
		setUpBoards();

		fillBoards();
		
		m_GameBoards_P.add(m_GameBoardTargets_P);
		m_GameBoards_P.add(m_GameBoard_Y_P);
		m_GameBoards_P.add(m_GameBoard_X_P);
		m_GameBoards_P.add(new JLabel(m_Assets.getImage("GameBoard")));
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		m_ScreenWidth = gd.getDisplayMode().getWidth();
		m_ScreenHeight = gd.getDisplayMode().getHeight();
	}
	
	public int getShipCount()
	{
		return m_ShipCount;
	}
	public JPanel getBoard()
	{
		for(int i = 0; i <5; i++)
		{
			showShip(m_CurrentPlayer.getShip(i), m_CurrentPlayer.getShip(i).x(), m_CurrentPlayer.getShip(i).y());
		}
		return m_GameBoards_P;
	}
	
	public int getNumCols(){
	
		return m_NUM_OF_COL;
	
	}

	public int getNumRows(){
	
		return m_NUM_OF_ROWS;
	
	}
	
	public JPanel getBoardHide()
	{
		for(int i = 0; i <5; i++)
		{
			hideShip(m_CurrentPlayer.getShip(i), m_CurrentPlayer.getShip(i).x(), m_CurrentPlayer.getShip(i).y());
		}
		return m_GameBoards_P;// show board with hidden ships, for others to see
	}
	public JLabel[] getTargetBoard()
	{
		return m_GameBoardTargets_L;
	}
	private void createBoards()
	{
		m_GameBoardGrid_L = new JLabel[m_NUM_OF_ROWS][m_NUM_OF_COL];
		m_GameBoard_Y_L = new JLabel[m_NUM_OF_COL];
		m_GameBoardTargets_L = new JLabel[m_NUM_OF_ROWS];
		m_GameBoard_X_L = new JLabel[m_NUM_OF_ROWS];
		
		Cursor m_CrossHair_C = new Cursor(Cursor.CROSSHAIR_CURSOR);
		
		
		m_GameBoard_Y_P = new JLabel(m_Assets.getImage("GameBoardBlank"));
		m_GameBoard_X_P = new JLabel(m_Assets.getImage("GameBoardBlank"));
		m_GameBoardTargets_P = new JLabel(m_Assets.getImage("GameBoardBlank"));
		m_GameBoards_P = new JPanel();
	}
	
	private void setUpBoards()
	{
		LayoutManager overlay = new OverlayLayout(m_GameBoards_P);
		
		m_GameBoard_Y_P.setLayout(new GridLayout(1,m_NUM_OF_COL,0,0));
		m_GameBoard_X_P.setLayout(new GridLayout(m_NUM_OF_ROWS,1,0,0));
		m_GameBoardTargets_P.setLayout(new GridLayout(1,m_NUM_OF_COL,0,0));
		
		m_GameBoards_P.setLayout(overlay);
	}

	private void fillBoards()
	{
		int count = 0;
		JLabel tmp;
		JLabel tmp2;
		for(int y = 0; y < m_NUM_OF_ROWS; y++)
		{
		    m_GameBoard_X_L[y] = new JLabel("");
			m_GameBoard_X_L[y].setLayout(new BoxLayout(m_GameBoard_X_L[y], BoxLayout.X_AXIS));
			m_GameBoard_X_L[y].setPreferredSize(new Dimension(m_boardWidth,m_boardHight/m_NUM_OF_COL));
			m_GameBoard_X_L[y].setMinimumSize(new Dimension(m_boardWidth,m_boardHight/m_NUM_OF_COL));
			m_GameBoard_X_L[y].setMaximumSize(new Dimension(m_boardWidth,m_boardHight/m_NUM_OF_COL));
			
			for(int x = 0; x < m_NUM_OF_COL; x++)
			{
				tmp = new JLabel("");
				tmp.setMaximumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
				tmp.setPreferredSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
				tmp.setMinimumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
				tmp.setForeground(Color.WHITE);
				m_GameBoard_X_L[y].add(tmp);
			}
			m_GameBoard_X_P.add(m_GameBoard_X_L[y]);
			
		}
		for(int x = 0; x < m_NUM_OF_COL; x++)
		{
		    m_GameBoard_Y_L[x] = new JLabel("");
			m_GameBoard_Y_L[x].setLayout(new BoxLayout(m_GameBoard_Y_L[x], BoxLayout.Y_AXIS));
			m_GameBoard_Y_L[x].setPreferredSize(new Dimension(m_boardWidth/m_NUM_OF_COL,m_boardHight));
			m_GameBoard_Y_L[x].setMinimumSize(new Dimension(m_boardWidth,m_boardHight/m_NUM_OF_COL));
			m_GameBoard_Y_L[x].setMaximumSize(new Dimension(m_boardWidth,m_boardHight/m_NUM_OF_COL));
			
		    m_GameBoardTargets_L[x] = new JLabel("");
			m_GameBoardTargets_L[x].setLayout(new BoxLayout(m_GameBoardTargets_L[x], BoxLayout.Y_AXIS));
			m_GameBoardTargets_L[x].setPreferredSize(new Dimension(m_boardWidth/m_NUM_OF_COL,m_boardHight));
			for(int y = 0; y < m_NUM_OF_ROWS; y++)
			{
				tmp = new JLabel("");
				tmp.setMaximumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
				tmp.setPreferredSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
				tmp.setMinimumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
				tmp.setForeground(Color.RED);
				m_GameBoard_Y_L[x].add(tmp);
				
				tmp2 = new JLabel("");
				tmp2.setMaximumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			    tmp2.setPreferredSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
				tmp2.setMinimumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
				tmp2.setForeground(Color.RED);
				tmp2.addMouseListener(new BoardMouseAction(x ,y, m_Game,m_GameBoardTargets_L, m_Assets, m_CurrentPlayer.getName()));
				m_GameBoardTargets_L[x].add(tmp2);
			}
			m_GameBoard_Y_P.add(m_GameBoard_Y_L[x]);
			m_GameBoardTargets_P.add(m_GameBoardTargets_L[x]);
		}
	}
	
	public void addNextShip(Ship ship)
	{
		int x = 1; int y = 1;
		
		while(hasShip(x,y,ship))//find safe location
		{
			y++;
		}
		
		showShip(ship, x,y);
		
		ship.setLocation(x,y);
		m_ShipCount++;
		
		
	}
	public void updateBoard(Ship ship, int x, int y, String direction)
	{
		/* 
		SKIP OVER SHIP FUNCTIONALITY (Need to add alternate directions. Atm, this only works from top to bottom)
		*/
		if(!isOutOfBounds(x, y, ship) && hasShip(x,y,ship))
		{
			// If the new location is not out of bounds but there is another ship there...
			int newx = x;
			int newy = y;
			switch(direction)
			{
				case "UP": 
						    while(!isOutOfBounds(newx, newy, ship)&& hasShip(newx,newy,ship))
								newy--;
						break;
				case "DOWN": 
							while(!isOutOfBounds(newx, newy, ship) && hasShip(newx,newy,ship))
								newy++;
						break;
				case "RIGHT": 
							while(!isOutOfBounds(newx, newy, ship) && hasShip(newx,newy,ship))			
								newx++;		
						break;		
				case "LEFT": 
							while(!isOutOfBounds(newx, newy, ship) && hasShip(newx,newy,ship))			
								newx--;
						break;
				default: System.out.println("INVALID UPDATE OF BOARD"); break;
			}
			x = newx;
			y = newy;
		}
		
		if(!isOutOfBounds(x, y, ship))
		{
			hideShip(ship, ship.x(), ship.y());
			
			showShip(ship, x,y);
		
			ship.setLocation(x,y);
		}
	}

	public void hideShip(Ship ship, int x, int y)
	{
		if(ship.getAxis() == Ship.X_AXIS)
		{
			JLabel tmp = new JLabel("");
			tmp.setMaximumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			tmp.setPreferredSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			tmp.setMinimumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			
			tmp.setForeground(Color.WHITE);
			m_GameBoard_X_L[y].remove(x);
			m_GameBoard_X_L[y].add(tmp,x);
			
			for(int i = 1; i < ship.getLength(); i++)
			{
				((JLabel) m_GameBoard_X_L[y].getComponent(x+i)).setVisible(true);
				((JLabel) m_GameBoard_X_L[y].getComponent(x+i)).setText("");
			}
		}else
		{
			JLabel tmp = new JLabel("");
			tmp.setMaximumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			tmp.setPreferredSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			tmp.setMinimumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			
			tmp.setForeground(Color.RED);
			m_GameBoard_Y_L[x].remove(y);
			m_GameBoard_Y_L[x].add(tmp,y);
			
			for(int i = 1; i < ship.getLength(); i++)
			{
				((JLabel) m_GameBoard_Y_L[x].getComponent(y-i)).setVisible(true);
				((JLabel) m_GameBoard_Y_L[x].getComponent(y-i)).setText("");
			}
		}
	}
	
	public void showShip(Ship ship, int x, int y)
	{
		if(ship.getAxis() == Ship.X_AXIS)
		{
			JLabel shipL = new JLabel(ship.getName(),ship.getImage(ship.getAxis()), JLabel.LEADING);
			shipL.setMaximumSize(new Dimension(ship.getLength()*m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			shipL.setPreferredSize(new Dimension(ship.getLength()*m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			shipL.setMinimumSize(new Dimension(ship.getLength()*m_boardWidth/m_NUM_OF_COL, m_boardHight/m_NUM_OF_ROWS));
			
			m_GameBoard_X_L[y].remove(x);
			m_GameBoard_X_L[y].add(shipL,x);
			
			for(int i = 1; i < ship.getLength(); i++)
			{
				((JLabel) m_GameBoard_X_L[y].getComponent(x+i)).setVisible(false);
				((JLabel) m_GameBoard_X_L[y].getComponent(x+i)).setText(ship.getName());
			}
		}else
		{
			JLabel shipL = new JLabel(ship.getName(),ship.getImage(ship.getAxis()), JLabel.LEADING);
			shipL.setMaximumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, (ship.getLength()*m_boardHight)/m_NUM_OF_ROWS));
			shipL.setPreferredSize(new Dimension(m_boardWidth/m_NUM_OF_COL, (ship.getLength()*m_boardHight)/m_NUM_OF_ROWS));
			shipL.setMinimumSize(new Dimension(m_boardWidth/m_NUM_OF_COL, (ship.getLength()*m_boardHight)/m_NUM_OF_ROWS));
			m_GameBoard_Y_L[x].remove(y);
			m_GameBoard_Y_L[x].add(shipL,y);
			
			for(int i = 1; i < ship.getLength(); i++)
			{
				((JLabel) m_GameBoard_Y_L[x].getComponent(y-i)).setVisible(false);
				((JLabel) m_GameBoard_Y_L[x].getComponent(y-i)).setText(ship.getName());
			}
		}
	}
	public boolean isOutOfBounds(int x, int y, Ship ship)
	{
		if(ship.getAxis() == Ship.X_AXIS)
		{
			if(y >= m_NUM_OF_ROWS || x > (m_NUM_OF_COL - ship.getLength()) || x < 0 || y < 0)
			{
				return true;
			}
		}else
		{
			if(y >= m_NUM_OF_ROWS || x >= m_NUM_OF_COL || y < (ship.getLength()-1) || x < 0 || y < 0)
			{
				return true;
			}
		}
		
	return false;
	}
	public boolean hasShip(int x, int y, Ship ship)
	{
		if(m_HasShip[x][y].equals(ship.getName()))
		{
			return true;
		}else if(ship.getAxis() == Ship.X_AXIS)
		{
			for(int j = 0; j < ship.getLength();j++)
			{
				if(m_HasShip[x+j][y].compareTo("NOSHIP") != 0)
				{
					return true;
				}
			}
		}else
		{
			for(int j = 0; j < ship.getLength();j++)
			{
				if(m_HasShip[x][y-j].compareTo("NOSHIP") != 0)
				{
					return true;
				}
			}
		}
		return false;
	}
	public String[][] getTakenLoc()
	{
		return m_HasShip;
	}
	public void addToTaken(int x, int y, Ship ship)
	{
		if(ship.getAxis() == Ship.X_AXIS)
		{
			for(int j = 0; j < ship.getLength();j++)
			{
				m_HasShip[x+j][y] =ship.getName();
			}
		}else
		{
			for(int j = 0; j < ship.getLength();j++)
			{
				m_HasShip[x][y-j] =ship.getName();
			}
		}
	}
}