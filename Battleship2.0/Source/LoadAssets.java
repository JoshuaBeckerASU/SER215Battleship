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
import java.util.*;

public class LoadAssets implements Runnable, Serializable
{	
    private ImageIcon [] m_Assets;
    private ArrayList<String> m_Names;
	private final int GridCol;
	private final int GridRows;
    
	LoadAssets()
	{
		GridCol = 16;
		GridRows = 19;
        m_Assets = new ImageIcon[41];
        m_Names = new ArrayList<String>();
	}
	
	public ImageIcon getImage(String name)
	{
        int index = m_Names.indexOf(name);
        if(index < 0)
        {
            System.out.println("Image not Found  " + name);
            index = 1;
        }
        return m_Assets[index];
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
		int height = 685/GridRows;
		int width = 577/GridCol;
		
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
		int height = 685/GridRows;
		int width = 577/GridCol;
		
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
    public void run()
    {
        System.out.println("START LOADING");
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		int ScreenWidth = gd.getDisplayMode().getWidth();
		int ScreenHeight = gd.getDisplayMode().getHeight();
		boolean X = true;
		boolean Y = false;
        
		m_Assets[0] = loadGameImage("Grid8.png", 577, 685);
        LoadingWindow.updateMessage("Loading GameBoard");
		m_Assets[1] = loadGameImage("GameBoardBlank.png",577, 685);
        LoadingWindow.updateMessage("Loading GameAsset");
		m_Assets[2] = loadGameImage("Instructions.png", ScreenWidth, 100);
        LoadingWindow.updateMessage("Loading MenuAsset");
        m_Assets[3] = loadGameImage("GameOverBG.png",550,420);
        LoadingWindow.updateMessage("Loading GameOverImage");
		m_Assets[4] = loadGameImage("MenuBG.jpg", ScreenWidth, ScreenHeight);
        LoadingWindow.updateMessage("Loading Menu Background");
		m_Assets[5] = loadGameImage("GameBG1.jpg", ScreenWidth, ScreenHeight);
        LoadingWindow.updateMessage("Loading Game Background");
        m_Assets[6] = loadGameImage("MenuBox.png",600,700);
        LoadingWindow.updateMessage("Loading Menu Asset.");
		m_Assets[7] = loadShipImage("Submarine_Y.png", Ship.SUBMARINE_LENGTH, Y);
        LoadingWindow.updateMessage("Loading Menu Asset..");
        m_Assets[8] = loadGameImage("LobbyBG.jpg", ScreenWidth, ScreenHeight);
        LoadingWindow.updateMessage("Loading Menu Asset...");
		m_Assets[9] = loadShipImage("Cruiser.png" , Ship.CRUISER_LENGTH, X);
        LoadingWindow.updateMessage("Loading Game Peaces");
        m_Assets[10] = loadGameImage("SlotBG.png", ScreenWidth - 400,60);
        LoadingWindow.updateMessage("Loading Menu Asset");
        m_Assets[11] = loadGif("Fire.gif",577/GridCol,  685/GridRows);
        LoadingWindow.updateMessage("Loading Game Peaces");
		m_Assets[12] = loadGameImage("Target.png",577/GridCol,  685/GridRows);
		m_Assets[13] = loadGameImage("Target2.png",577/GridCol,  685/GridRows);
		m_Assets[14] = loadShipImage("Cruiser_Y.png", Ship.CRUISER_LENGTH, Y);
        LoadingWindow.updateMessage("Loading Menu Assets");
		m_Assets[15] = loadButtonImage("BackToMainMenuButton.png");
		m_Assets[16] = loadButtonImage("ExitButton.png");
		m_Assets[17] = loadButtonImage("LoadAGameButton.png");
        LoadingWindow.updateMessage("Loading Game Peaces");
        m_Assets[18] = loadShipImage("Submarine.png", Ship.SUBMARINE_LENGTH, X);
        LoadingWindow.updateMessage("Loading Menu Buttons");
		m_Assets[19] = loadButtonImage("PlayGameButton.png");
		m_Assets[20] = loadButtonImage("SettingsButton.png");
        LoadingWindow.updateMessage("Loading Menu Buttons.");
		m_Assets[21] = loadButtonImage("StartANewGameButton.png");
		m_Assets[22] = loadButtonImage("StartGameButton.png");
        LoadingWindow.updateMessage("Loading Menu Buttons..");
		m_Assets[23] = loadButtonImage("SinglePlayerButton.png");
		m_Assets[24] = loadButtonImage("MultiPlayerButton.png");
        LoadingWindow.updateMessage("Loading Menu Buttons...");
		m_Assets[25] = loadButtonImage("ReplayGameButton.png");
        m_Assets[26] = loadButtonImage("ResetLobbyButton.png");
        LoadingWindow.updateMessage("Loading Menu Buttons.");
        m_Assets[27] = loadButtonImage("HostGameButton.png");
        m_Assets[28] = loadButtonImage("JoinGameButton.png");
        LoadingWindow.updateMessage("Loading Menu Buttons..");
        m_Assets[29] = loadButtonImage("MainMenuButton.png");
        LoadingWindow.updateMessage("Loading Game Peaces.");
        m_Assets[30] = loadShipImage("AircraftCarrier.png", Ship.CARRIER_LENGTH, X);
        m_Assets[31] = loadShipImage("AircraftCarrier_Y.png", Ship.CARRIER_LENGTH, Y);
        LoadingWindow.updateMessage("Loading Game Peaces..");
        m_Assets[32] = loadShipImage("Battleship.png", Ship.BATTLESHIP_LENGTH, X);
		m_Assets[33] = loadShipImage("Battleship_Y.png", Ship.BATTLESHIP_LENGTH, Y);
        LoadingWindow.updateMessage("Loading Game Peaces...");
		m_Assets[34] = loadShipImage("Destroyer.png", Ship.DESTROYER_LENGTH, X);
		m_Assets[35] = loadShipImage("Destroyer_Y.png", Ship.DESTROYER_LENGTH, Y);
        LoadingWindow.updateMessage("Loading Game Peaces.");
        m_Assets[36] = loadGameImage("GameBoardBlank.png", ScreenWidth,(ScreenHeight-700)/2);
        m_Assets[37] = loadGameImage("MenuBox.png",ScreenWidth - 250, ScreenHeight- 300);
        LoadingWindow.updateMessage("Loading Game Peaces..");
        LoadingWindow.updateMessage("Done Loading...");
        m_Assets[38] = loadButtonImage("WaitingForOtherPlayerButton.png");
        m_Assets[39] = loadGameImage("Blank.png", 1000, 100);
        
		m_Names.add("GameBoard");
		m_Names.add("GameBoardBlank");
		m_Names.add("Instructions");
        m_Names.add("GameOverBG");
		m_Names.add("MenuBG");
		m_Names.add("GameBG");
        m_Names.add("MenuBox");
		m_Names.add("Submarine_Y");
        m_Names.add("LobbyBG");
		m_Names.add("Cruiser_X");
        m_Names.add("SlotBG");
        m_Names.add("HitMarker");
		m_Names.add("Target");
		m_Names.add("Target2");
		m_Names.add("Cruiser_Y");
		m_Names.add("BackToMainMenuButton");
		m_Names.add("ExitButton");
		m_Names.add("LoadAGameButton");
        m_Names.add("Submarine_X");
		m_Names.add("PlayGameButton");
		m_Names.add("SettingsButton");
		m_Names.add("StartANewGameButton");
		m_Names.add("StartGameButton");
		m_Names.add("SinglePlayerButton"); 
		m_Names.add("MultiPlayerButton");
		m_Names.add("ReplayGame");
        m_Names.add("ResetLobby");
        m_Names.add("HostGameButton");
        m_Names.add("JoinGameButton");
        m_Names.add("MainMenuButton");
        m_Names.add("AircraftCarrier_X");
        m_Names.add("AircraftCarrier_Y");
        m_Names.add("Battleship_X");
		m_Names.add("Battleship_Y");
		m_Names.add("Destroyer_X");
		m_Names.add("Destroyer_Y");
        m_Names.add("MenuHeader");
        m_Names.add("LobbyBox");
        m_Names.add("WaitingScreen");
        m_Names.add("WaitingScreenBG");
        m_Names.add("Blank");

        System.out.println("DONE LOADING");
    }
}