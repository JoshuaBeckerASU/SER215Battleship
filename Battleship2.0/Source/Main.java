/********************** 
Name: main 
Author: Joshua Becker
Create On: 10/28/15
Updated On: 10/28/15
Contributors:
***********************/
import javax.swing.*;
public class Main
{
    public static Thread m_LoadAssetsThread;
    public static Thread m_GameThread;
    public static LoadAssets m_Assets;
    public static void main(String[] args)
    {
		//need loading screen for this
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
        
		MenuWindow menu = new MenuWindow(m_Assets);
        m_GameThread = new Thread(menu);
        
        m_GameThread.start();
    }
}