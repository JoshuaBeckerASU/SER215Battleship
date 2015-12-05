/*************************************
* @file: Main.java
* @author: Joshua Becker
* @date: 10/28/15
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
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
public class Main implements Serializable
{
    private static Thread s_LoadAssetsThread;
    public static Thread s_GameThread;
    private static LoadingWindow m_LoadingW;
    public static final LoadAssets s_Assets = new LoadAssets();
    public static void main(String[] args)
    {
        new UserLogin();
    }
    public static void startLoading()
    {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// Getting size of screen
		int ScreenWidth = gd.getDisplayMode().getWidth();
		int ScreenHeight = gd.getDisplayMode().getHeight();
        
		String path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/GUI/GameImages/Loading.gif");
		ImageIcon gif = new ImageIcon(path);
		
        path = "";
		path = System.getProperty("user.dir");
		path = path.replace('\\','/');
		path = path.replaceAll("Source", "Assets/GUI/GameImages/MenuBG.jpg");
		Image img;
        ImageIcon icon = null;
		
		try 
		{
			img = ImageIO.read(new File(path));
			img = img.getScaledInstance(ScreenWidth, ScreenHeight,  java.awt.Image.SCALE_AREA_AVERAGING);
            icon = new ImageIcon(img);
		} catch (IOException ex) 
		{
			System.out.println("FIle Not Found\nFile Path: " + path);System.exit(0);
		}
        
        m_LoadingW = new LoadingWindow(icon, gif);
        
        s_LoadAssetsThread = new Thread(s_Assets);
        
        s_LoadAssetsThread.start();
    }
    public static void startGame()
    {
        MenuWindow menu = new MenuWindow(m_LoadingW.getMainFrame());
        s_GameThread = new Thread(menu);
        
        s_GameThread.start();
    }
}