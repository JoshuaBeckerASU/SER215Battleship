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
public class Main implements Serializable
{
    public static Thread m_LoadAssetsThread;
    public static Thread m_GameThread;
    public static LoadAssets m_Assets;
    public static void main(String[] args)
    {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
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
        
        LoadingWindow loadW = new LoadingWindow(icon, gif);
        m_Assets = new LoadAssets();
        
        m_LoadAssetsThread = new Thread(m_Assets);
        
        m_LoadAssetsThread.start();
        while(true)
        {
            if(!m_LoadAssetsThread.isAlive())
            {
                break;
            }
        }
        new WaitForConnection(new JFrame());//testing
		MenuWindow menu = new MenuWindow(m_Assets, loadW.getMainFrame());
        m_GameThread = new Thread(menu);
        
        m_GameThread.start();
    }
}