/********************** 
Name: Board object 
Author: Joshua Becker
Create On: 9/9/15
Updated On: 9/17/15
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

public class LoadAssets
{	
	private ImageIcon m_Board, m_MenuBackground,m_Instructions, m_GameBackground;
	private ImageIcon m_BackToMainMenu_B, m_Exit_B, m_LoadAGame_B;
	private ImageIcon m_PlayGame_B, m_Settings_B, m_StartANewGame_B;
	private ImageIcon m_StartGame_B, m_Battleship, m_AircraftCarrier;
	private ImageIcon m_Cruiser, m_Destroyer, m_Submarine;
	private ImageIcon m_CruiserXTop, m_CruiserXBut;
	LoadAssets()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		int ScreenWidth = gd.getDisplayMode().getWidth();
		int ScreenHeight = gd.getDisplayMode().getHeight();
		
		m_Board = loadGameImage("GameBoard.jpg",600, 700);
		m_Instructions = loadGameImage("Instructions.png", ScreenWidth, 100);
		m_MenuBackground = loadGameImage("MenuBG.jpg", ScreenWidth, ScreenHeight);
		m_GameBackground = loadGameImage("GameBG.jpg", ScreenWidth, ScreenHeight);
		
		m_BackToMainMenu_B = loadButtonImage("BackToMainMenuButton.png");
		m_Exit_B = loadButtonImage("ExitButton.png");
		m_LoadAGame_B = loadButtonImage("LoadAGameButton.png");
		m_PlayGame_B = loadButtonImage("PlayGameButton.png");
		m_Settings_B = loadButtonImage("SettingsButton.png");
		m_StartANewGame_B = loadButtonImage("StartANewGameButton.png");
		m_StartGame_B = loadButtonImage("StartGameButton.png");
		
		m_AircraftCarrier = loadShipImage("AircraftCarrier.png", 5);
		m_Battleship = loadShipImage("Battleship.png", 4);
		m_Cruiser = loadShipImage("Cruiser.png" , 3);
		m_CruiserXTop = loadShipImage("Cruiser_X_TOP.png" , 1);
		m_CruiserXBut = loadShipImage("Cruiser_X_But.png" , 1);
		m_Destroyer = loadShipImage("Destroyer.png", 3);
		m_Submarine = loadShipImage("Submarine.png", 2);
	}
	
	public ImageIcon getImage(String name)
	{
		switch(name)
		{
			case "GameBoard": return m_Board;
			
			case "GameBG": return m_GameBackground;
			
			case "Instructions": return m_Instructions;
			
			case "MenuBG": return m_MenuBackground;
			
			case "BackToMainMenuButton": return m_BackToMainMenu_B;
			
			case "ExitButton": return m_Exit_B;
			
			case "LoadAGameButton": return m_LoadAGame_B;
			
			case "PlayGameButton": return m_PlayGame_B;
			
			case "SettingsButton": return m_Settings_B;
			
			case "StartANewGameButton": return m_StartANewGame_B;
			
			case "StartGameButton": return m_StartGame_B;
			
			case "AircraftCarrier": return m_AircraftCarrier;
			case "Battleship": return m_Battleship;
			case "Submarine": return m_Submarine;
			
			case "Cruiser": return m_Cruiser;
			case "CruiserXTop": return m_CruiserXTop;
			case "CruiserXBut": return m_CruiserXBut;
			
			case "Destroyer": return m_Destroyer;
			
			default: System.out.println("Image Not Found: " + name); return null;
		}
	}
	
	private ImageIcon loadGameImage(String name, int w, int h)
	{
		String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/GUI/GameImages/" + name);
		Image img;
		
		try 
		{
			img = ImageIO.read(new File(path));
			img = img.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(img);
		} catch (IOException ex) 
		{
			System.out.println("FIle Not Found\nFile Path: " + path);System.exit(0);
		}
		return null;
	}
	private ImageIcon loadButtonImage(String name)
	{
		String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/GUI/Buttons/" + name);
		Image img;
		
		try 
		{
			img = ImageIO.read(new File(path));
			return new ImageIcon(img);
		} catch (IOException ex) 
		{
			System.out.println("FIle Not Found\nFile Path: " + path);System.exit(1);
		}
		return null;
	}
	private ImageIcon loadShipImage(String name, int scale)
	{
		String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/Ships/" + name);
		Image img;
		
		try 
		{
			img = ImageIO.read(new File(path));
			img = img.getScaledInstance(29*scale,29,  java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(img);
		} catch (IOException ex) 
		{
			System.out.println("FIle Not Found\nFile Path: " + path);System.exit(1);
		}
		return null;
	}
}