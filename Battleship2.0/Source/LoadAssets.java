/********************** 
Name: Load Assets
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
import java.net.*;

public class LoadAssets
{	
	private ImageIcon m_Board, m_BoardBlank, m_MenuBackground,m_Instructions, m_GameBackground;
	private ImageIcon m_BackToMainMenu_B, m_Exit_B, m_LoadAGame_B, m_MenuBox;
	private ImageIcon m_PlayGame_B, m_Settings_B, m_StartANewGame_B, m_MenuHeader;
	private ImageIcon m_StartGame_B, m_Battleship_X, m_Battleship_Y, m_AircraftCarrier_X, m_AircraftCarrier_Y;
	private ImageIcon m_Cruiser_X, m_Destroyer_X, m_Destroyer_Y, m_Submarine_X,m_Submarine_Y;
	private ImageIcon m_Cruiser_Y, m_SinglePlayer_B, m_MultiPlayer_B, m_LobbyBackground;
	private ImageIcon m_HitMarker, m_Target, m_Target2, m_GameOverBG, m_LobbyBox;
	private Image m_Cursor;
	private final int GridCol;
	private final int GridRows;
	LoadAssets()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		int ScreenWidth = gd.getDisplayMode().getWidth();
		int ScreenHeight = gd.getDisplayMode().getHeight();
		boolean X = true;
		boolean Y = false;
		GridCol = 16;
		GridRows = 19;
		
		m_Board = loadGameImage("Grid8.png", 577, 685);
		m_BoardBlank = loadGameImage("GameBoardBlank.png",577, 685);
		m_Instructions = loadGameImage("Instructions.png", ScreenWidth, 100);
        m_GameOverBG = loadGameImage("GameOverBG.png",550,420);
		m_MenuBackground = loadGameImage("MenuBG.jpg", ScreenWidth, ScreenHeight);
		m_GameBackground = loadGameImage("GameBG1.jpg", ScreenWidth, ScreenHeight);
        m_MenuBox = loadGameImage("MenuBox.png",600,700);
        m_MenuHeader = loadGameImage("GameBoardBlank.png",ScreenWidth,ScreenHeight/2-getImage("MenuBox").getIconHeight()/2);
        m_LobbyBackground = loadGameImage("LobbyBG.jpg", ScreenWidth, ScreenHeight);
        m_LobbyBox = loadGameImage("MenuBox.png",ScreenWidth - 300,700);
        
		
		m_HitMarker = loadGif("Fire.gif",m_Board.getIconWidth()/GridCol,  m_Board.getIconHeight()/GridRows);
		m_Target = loadGameImage("Target.png",m_Board.getIconWidth()/GridCol,  m_Board.getIconHeight()/GridRows);
		m_Target2 = loadGameImage("Target2.png",m_Board.getIconWidth()/GridCol,  m_Board.getIconHeight()/GridRows);
		m_Cursor = getShipImages("Battleship.png", Ship.BATTLESHIP_LENGTH, false);
		
		m_BackToMainMenu_B = loadButtonImage("BackToMainMenuButton.png");
		m_Exit_B = loadButtonImage("ExitButton.png");
		m_LoadAGame_B = loadButtonImage("LoadAGameButton.png");
		m_PlayGame_B = loadButtonImage("PlayGameButton.png");
		m_Settings_B = loadButtonImage("SettingsButton.png");
		m_StartANewGame_B = loadButtonImage("StartANewGameButton.png");
		m_StartGame_B = loadButtonImage("StartGameButton.png");
        m_SinglePlayer_B = loadButtonImage("SinglePlayerButton.png");
        m_MultiPlayer_B = loadButtonImage("MultiPlayerButton.png");
		
		m_AircraftCarrier_X = loadShipImage("AircraftCarrier.png", Ship.CARRIER_LENGTH, X);
		m_AircraftCarrier_Y = loadShipImage("AircraftCarrier_Y.png", Ship.CARRIER_LENGTH, Y);
		
		m_Battleship_X = loadShipImage("Battleship.png", Ship.BATTLESHIP_LENGTH, X);
		m_Battleship_Y = loadShipImage("Battleship_Y.png", Ship.BATTLESHIP_LENGTH, Y);
		
		m_Destroyer_X = loadShipImage("Destroyer.png", Ship.DESTROYER_LENGTH, X);
		m_Destroyer_Y = loadShipImage("Destroyer_Y.png", Ship.DESTROYER_LENGTH, Y);
		
		m_Submarine_X = loadShipImage("Submarine.png", Ship.SUBMARINE_LENGTH, X);
		m_Submarine_Y = loadShipImage("Submarine_Y.png", Ship.SUBMARINE_LENGTH, Y);
		
		m_Cruiser_X = loadShipImage("Cruiser.png" , Ship.CRUISER_LENGTH, X);
		m_Cruiser_Y = loadShipImage("Cruiser_Y.png", Ship.CRUISER_LENGTH, Y);
	}
	
	public ImageIcon getImage(String name)
	{
		switch(name)
		{
			case "GameBoard": return m_Board;
            
            case "MenuHeader": return m_MenuHeader;
            
            case "LobbyBG": return m_LobbyBackground;
            
            case "LobbyBox": return m_LobbyBox;
			
			case "GameBoardBlank": return m_BoardBlank;
			
			case "GameBG": return m_GameBackground;
			
			case "Instructions": return m_Instructions;
			
			case "MenuBG": return m_MenuBackground;
			
			case "HitMarker": return m_HitMarker;
            
            case "GameOverBG": return m_GameOverBG;
			
			case "Target": return m_Target;
			
			case "Target2": return m_Target2;
            
            case "MenuBox": return m_MenuBox;
			
            case "SinglePlayerButton": return m_SinglePlayer_B;
            
            case "MultiPlayerButton": return m_MultiPlayer_B;
            
			case "BackToMainMenuButton": return m_BackToMainMenu_B;
			
			case "ExitButton": return m_Exit_B;
			
			case "LoadAGameButton": return m_LoadAGame_B;
			
			case "PlayGameButton": return m_PlayGame_B;
			
			case "SettingsButton": return m_Settings_B;
			
			case "StartANewGameButton": return m_StartANewGame_B;
			
			case "StartGameButton": return m_StartGame_B;
			
			case "AircraftCarrier_X": return m_AircraftCarrier_X;
			case "AircraftCarrier_Y": return m_AircraftCarrier_Y;
			
			case "Battleship_X": return m_Battleship_X;
			case "Battleship_Y": return m_Battleship_Y;
			
			case "Submarine_X": return m_Submarine_X;
			case "Submarine_Y": return m_Submarine_Y;
			
			case "Cruiser_X": return m_Cruiser_X;
			case "Cruiser_Y": return m_Cruiser_Y;
			
			case "Destroyer_X": return m_Destroyer_X;
			case "Destroyer_Y": return m_Destroyer_Y;
			
			default: System.out.println("Image Not Found: " + name); return null;
		}
	}
	public Image getImage(String name, int domb)
	{
		return m_Cursor;
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
			img = img.getScaledInstance(w, h,  java.awt.Image.SCALE_AREA_AVERAGING);
			return new ImageIcon(img);
		} catch (IOException ex) 
		{
			System.out.println("FIle Not Found\nFile Path: " + path);System.exit(0);
		}
		return null;
	}
	private ImageIcon loadGif(String name, int w, int h)
	{
		String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/GUI/GameImages/" + name);
		System.out.println(path);
		ImageIcon gif;
		
		gif = new ImageIcon(path);
		return gif;
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
	private ImageIcon loadMarkerImage(String name)
	{
		String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/Markers/" + name);
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
	private ImageIcon loadShipImage(String name, int scale, boolean orientation)
	{
		String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/Ships/" + name);
		Image img;
		int height = m_Board.getIconHeight()/GridRows;
		int width = m_Board.getIconWidth()/GridCol;
		
		if(orientation)
		{
			try 
			{
				img = ImageIO.read(new File(path));
				img = img.getScaledInstance(width*scale, height,  java.awt.Image.SCALE_SMOOTH);
				return new ImageIcon(img);
			} catch (IOException ex) 
			{
				System.out.println("FIle Not Found\nFile Path: " + path);System.exit(1);
			}
		}else
		{
			try 
			{
				img = ImageIO.read(new File(path));
				img = img.getScaledInstance(width, height*scale,  java.awt.Image.SCALE_SMOOTH);
				return new ImageIcon(img);
			} catch (IOException ex) 
			{
				System.out.println("FIle Not Found\nFile Path: " + path);System.exit(1);
			}
		}
		
		return null;
	}
	private Image getShipImages(String name, int scale, boolean orientation)
	{
		String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/Ships/" + name);
		Image img;
		int height = m_Board.getIconHeight()/GridRows;
		int width = m_Board.getIconWidth()/GridCol;
		
		if(orientation)
		{
			try 
			{
				img = ImageIO.read(new File(path));
				img = img.getScaledInstance(width*scale, height,  java.awt.Image.SCALE_SMOOTH);
				return img;
			} catch (IOException ex) 
			{
				System.out.println("FIle Not Found\nFile Path: " + path);System.exit(1);
			}
		}else
		{
			try 
			{
				img = ImageIO.read(new File(path));
				img = img.getScaledInstance(width, height*scale,  java.awt.Image.SCALE_SMOOTH);
				return img;
			} catch (IOException ex) 
			{
				System.out.println("FIle Not Found\nFile Path: " + path);System.exit(1);
			}
		}

		return null;
	}
}