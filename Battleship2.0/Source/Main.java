/********************** 
Name: main 
Author: Joshua Becker
Create On: 10/28/15
Updated On: 10/28/15
Contributors:
***********************/
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
public class Main
{
    public static Thread m_LoadAssetsThread;
    public static Thread m_GameThread;
    public static LoadAssets m_Assets;
    public static ImageIcon m_Loading_II, m_Background_II;
    public static void main(String[] args)
    {
        setupLoading();
        
        LoadingWindow loadW = new LoadingWindow(m_Background_II, m_Loading_II);
        m_Assets = new LoadAssets();
        
        m_LoadAssetsThread = new Thread(m_Assets);
        
        loadW.showScreen();
        m_LoadAssetsThread.start();
        while(true)
        {
            if(!m_LoadAssetsThread.isAlive())
            {
                break;
            }
        }
        
		MenuWindow menu = new MenuWindow(m_Assets, loadW.getMainFrame());
        m_GameThread = new Thread(menu);
        
        m_GameThread.start();
    }
    public static void setupLoading()
    {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		int ScreenWidth = gd.getDisplayMode().getWidth();
		int ScreenHeight = gd.getDisplayMode().getHeight();
        
		String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/GUI/GameImages/Loading.gif");
		m_Loading_II = new ImageIcon(path);
		
        path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/GUI/GameImages/MenuBG.jpg");
		Image img;
        m_Background_II = null;
		
		try 
		{
			img = ImageIO.read(new File(path));
			img = img.getScaledInstance(ScreenWidth, ScreenHeight,  java.awt.Image.SCALE_AREA_AVERAGING);
            m_Background_II = new ImageIcon(img);
		} catch (IOException ex) 
		{
			System.out.println("FIle Not Found\nFile Path: " + path);System.exit(0);
		}
    }
}