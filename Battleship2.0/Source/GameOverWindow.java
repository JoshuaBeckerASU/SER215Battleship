/*************************************
* @file: GameOverWindow.java
* @author: Joshua Becker
* @date:11/7/15
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

public class GameOverWindow implements Serializable
{
	private JFrame m_GameOver_F, m_OldWindow;
	private int m_ScreenWidth, m_ScreenHeight;
	private JButton m_BackToMenu_B, m_StartGame_B, m_ReplayGame_B;
	private JLabel m_Background_L;
	private LoadAssets m_Assets;
	
    public GameOverWindow(JFrame window)// Constructor
    {
		m_Assets = Main.s_Assets;
		
		m_OldWindow = window;
        m_OldWindow.setEnabled(false);
		
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
		
		m_Background_L = new JLabel(m_Assets.getImage("GameOverBG"));
		
		m_GameOver_F = new JFrame("GAME OVER");
		
		m_BackToMenu_B = new JButton(m_Assets.getImage("BackToMainMenuButton"));
		m_StartGame_B = new JButton(m_Assets.getImage("StartGameButton"));
        m_ReplayGame_B = new JButton(m_Assets.getImage("ReplayGame"));
	}
	/**buildComponents
	* set up components and there attributes.
	* 
	**/
	public void buildComponents()
	{
		m_GameOver_F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_GameOver_F.add(m_Background_L);
		
		m_Background_L.setLayout(new BoxLayout(m_Background_L, BoxLayout.Y_AXIS));
		
		m_GameOver_F.setUndecorated(true);
        m_GameOver_F.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        m_GameOver_F.setSize(new Dimension(m_Assets.getImage("GameOverBG").getIconWidth(), m_Assets.getImage("GameOverBG").getIconHeight()));
        m_GameOver_F.setLocation( m_ScreenWidth/2-m_Assets.getImage("GameOverBG").getIconWidth()/2, m_ScreenHeight/2-m_Assets.getImage("GameOverBG").getIconHeight()/2);//centering
        m_GameOver_F.setAlwaysOnTop(true);
		
		m_BackToMenu_B.setMargin(new Insets(0,0,0,0));
		m_StartGame_B.setMargin(new Insets(0,0,0,0));
        m_ReplayGame_B.setMargin(new Insets(0,0,0,0));
		
		m_BackToMenu_B.setActionCommand("BackToMenu");
		m_StartGame_B.setActionCommand("StartGame");
        m_ReplayGame_B.setActionCommand("ReplayGame");
		
        m_ReplayGame_B.setAlignmentX(Component.CENTER_ALIGNMENT);
		m_BackToMenu_B.setAlignmentX(Component.CENTER_ALIGNMENT);
		m_StartGame_B.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	/**addElements
	* add components to panels and
	* adds panels to Frame
	* 
	**/
	public void addElements()
	{
        m_Background_L.add(new JLabel("\n"));
        m_Background_L.add(new JLabel("\n"));
        m_Background_L.add(new JLabel("\n"));
		m_Background_L.add(new JLabel("\n"));
        
		m_Background_L.add(m_ReplayGame_B);
		m_Background_L.add(new JLabel("\n"));
		m_Background_L.add(m_StartGame_B);
		m_Background_L.add(new JLabel("\n"));
		m_Background_L.add(m_BackToMenu_B);
		
		m_GameOver_F.add(m_Background_L);
		
		m_GameOver_F.pack();
		
		m_GameOver_F.setVisible(true);
	}
	
	/**addActionListeners
	* adds ActionListener, which wait till
	* an action is Performed then sends 
	* a event to the type of listener.
	* 
	**/
	private void addActionListeners()
	{
        m_ReplayGame_B.addActionListener(new ButtonListener());
		m_BackToMenu_B.addActionListener(new ButtonListener());
		m_StartGame_B.addActionListener(new ButtonListener());
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
				case "BackToMenu": MenuWindow menu = new MenuWindow(new JFrame("domnb"));//loading screen...
                                   m_OldWindow.dispose(); m_GameOver_F.dispose();
					break;
				case "StartGame":
                    break;
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
