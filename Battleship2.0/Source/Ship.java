/*************************************
* @file: Ship.java
* @author: Joshua Becker
* @date: 9/15/15
* @description:
*  
* @contributors:
*  
* @index
* [
*     m_: for member variables
*     g_: for global variables
*     s_: for static variables
* ]
* 
***************************************/
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Ship implements Serializable
{
	public static final boolean Y_AXIS = true;
	public static final boolean X_AXIS = false;
	public static final int CARRIER_LENGTH = 5;
	public static final int BATTLESHIP_LENGTH = 4;
	public static final int CRUISER_LENGTH = 2;
	public static final int DESTROYER_LENGTH = 3;
	public static final int SUBMARINE_LENGTH = 3;
	
	private String m_Name;
	private int m_Length;
	private int m_Lives;
	private ImageIcon image_X,image_Y;
	private LoadAssets m_Assets;
	private boolean m_Orientation;
	private Location m_Location;
	
	Ship()
	{
		m_Name = "Dumb Ship";
		m_Length = 20;
		m_Lives = 20;
		m_Location = new Location();
		m_Orientation = Ship.X_AXIS;
	}
	Ship(String name, int length)
	{
		m_Assets = Main.s_Assets;
		m_Name = name;
		m_Length = length;
		m_Lives = length;
		m_Location = new Location(1,1);
		m_Orientation = Ship.X_AXIS;
		
		image_X = m_Assets.getImage(name + "_X");
		image_Y = m_Assets.getImage(name + "_Y");
		if(image_X == null || image_Y == null)
		{
			System.out.println(name + " was not found");
		}
	}
    Ship(Ship ship)
    {
        m_Name = ship.getName();
        m_Length = ship.getLength();
        m_Lives = ship.getLives();
        m_Location = ship.getLocation();
        m_Orientation = ship.getAxis();
        image_X = ship.getImageX();
        image_Y = ship.getImageY();
    }
	public ImageIcon getImageX()
    {
        return image_X;
    }
    public ImageIcon getImageY()
    {
        return image_Y;
    }
	public String getName()
	{
		return m_Name;
	}
	public boolean getAxis()
	{
		return m_Orientation;
	}
	public int getLength()
	{
		return m_Length;
	}
	
	public int getLives()
	{
		return m_Lives;
	}
	public void decLives()
	{
		m_Lives--;
	}
	public int x()
	{
		return m_Location.x();
	}
	public int y()
	{
		return m_Location.y();
	}
	public ImageIcon getImage(boolean axis)
	{
		if(axis == Ship.X_AXIS)
		{
			return image_X;
		}else
		{
			return image_Y;
		}
		
	}
	public Location getLocation()
	{
		return m_Location;
	}
	public void setLocation(int x, int y)
	{
		m_Location.setLocation(x,y);
	}
	public void setAxis(boolean axis)
	{
		m_Orientation = axis;
	}
	public void flipAxis()
	{
		if(m_Orientation)
		{
			m_Orientation = false;
		}else
		{
			m_Orientation = true;
		}
		
	}
	public void reset()
	{
		m_Lives = m_Length;
	}
	public static boolean isShip(String name)
	{
		switch(name)
		{
			case "AircraftCarrier": return true;
			case "Battleship":      return true; 
			case "Submarine":       return true;
			case "Cruiser":         return true;
			case "Destroyer":       return true;
			default: return false;
		}
	}
}