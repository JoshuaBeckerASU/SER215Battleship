/*************************************
* @file: WaitingScreenWindow.java
* @author: Joshua Becker
* @date: 11/7/15
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
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.ImageIcon.*;
import javax.swing.JFrame;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class WaitingScreenWindow implements Serializable
{
	private JFrame m_WaitingScreenFrame, m_OldWindow;
	private int m_ScreenWidth, m_ScreenHeight;
	private JLabel m_Background_L, m_Waiting_L;
	private LoadAssets m_Assets;
	
    public WaitingScreenWindow()// Constructor
    {
		m_Assets = Main.s_Assets;
		
		createComponents();
		
		buildComponents();
		
		addActionListeners();
		
		addElements();
	}
	/**createComponents
	* creates components and gives them
	* default values.
	* 
	**/
	public void createComponents()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		m_ScreenWidth = gd.getDisplayMode().getWidth();
		m_ScreenHeight = gd.getDisplayMode().getHeight();
		
		m_Background_L = new JLabel(m_Assets.getImage("WaitingScreenBG"));
        
        m_Waiting_L = new JLabel(m_Assets.getImage("WaitingScreen"));
		
		m_WaitingScreenFrame = new JFrame("WaitingScreen");
	}
	/**buildComponents
	* set up components and there attributes.
	* 
	**/
	public void buildComponents()
	{
		m_WaitingScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_WaitingScreenFrame.add(m_Background_L);
		
		m_Background_L.setLayout(new BoxLayout(m_Background_L, BoxLayout.Y_AXIS));
		
		m_WaitingScreenFrame.setUndecorated(true);
        m_WaitingScreenFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        m_WaitingScreenFrame.setSize(new Dimension(m_Assets.getImage("WaitingScreenBG").getIconWidth(), m_Assets.getImage("WaitingScreenBG").getIconHeight()));
        m_WaitingScreenFrame.setLocation( m_ScreenWidth/2-m_Assets.getImage("WaitingScreenBG").getIconWidth()/2, m_ScreenHeight/2-m_Assets.getImage("WaitingScreenBG").getIconHeight()/2);//centering
        m_WaitingScreenFrame.setAlwaysOnTop(true);
        
        m_Waiting_L.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	/**addElements
	* add components to panels and
	* adds panels to Frame
	* 
	**/
	public void addElements()
	{
        for(int i = 0; i < 20; i++)
            m_Background_L.add(new JLabel("\n"));//spaceing
        
		m_Background_L.add(m_Waiting_L);
		
		m_WaitingScreenFrame.add(m_Background_L);
		
		m_WaitingScreenFrame.pack();
		
		m_WaitingScreenFrame.setVisible(true);
	}
	
    public void dispose()
    {
        m_WaitingScreenFrame.dispose();
    }
	/**addActionListeners
	* adds ActionListener, which wait till
	* an action is Performed then sends 
	* a event to the type of listener.
	* 
	**/
	private void addActionListeners()
	{
	}

	/**Listeners
	* Once an event occurs the program goes here
	* and decides what to do with each event.
	*
	*@peram MenuPanel.
	*@peram nothing.
	* 
	**/
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String command = event.getActionCommand();
			switch(command)
			{
				case "BackToMenu": MenuWindow menu = new MenuWindow(m_WaitingScreenFrame);//loading screen...
                                   m_OldWindow.dispose(); m_WaitingScreenFrame.dispose();
					break;
				case "StartGame":
						/*Loading Screen...*/
                        //GameSetUpWindow newGame = new GameSetUpWindow(m_WaitingScreenFrame);
                    break;
				// create default error message
                case "ReplayGame":
                    break;
			}
		}  
	}
	
	private class ComboListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String command = event.getActionCommand();
			switch(command)
			{
				case "AIDiff":
					break;
				case "numOfPly":
					break;
			// create default error message
			}
		}
	}
}
