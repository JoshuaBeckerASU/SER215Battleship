/******************************
* @file: MultiPlayerWindow.java
* @author: Joshua Becker
* @date:11/12/15
* @description:
* 
* @index
* [
*     m_: for member variables
*     g_: for global variables
* ]
*******************************/

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.ImageIcon.*;
import javax.swing.JFrame;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class StartNewGameWindow
{
	private JFrame m_StartNewGameFrame, m_GameSetUpFrame;
	private int m_ScreenWidth, m_ScreenHeight;
	private JButton m_BackToMenu_B, m_StartGame_B;
	private JComboBox<String> m_NumOfPly_CB, m_AIDiff_CB;
	private JLabel m_Background_L;
	private LoadAssets m_Assets;
	
    public StartNewGameWindow(JFrame window, LoadAssets assets)// Constructor
    {
		m_Assets = assets;
		
		m_GameSetUpFrame = window;
		
		createComponents();
		
		buildComponents();
		
		addActionListeners();
		
		addElements();
	}
	/**createComponents
	* creates components and gives them
	* default values.
	* J.B.
	**/
	public void createComponents()
	{
		String numOfPly[] = {"Select Number Of Players", "2", "3", "4", "5"};
		String difficulty [] = {"Select Difficulty", "ur Dumb", "Your Still Not Smart" , "Ok You Can Play", "You're Slightly Intelligent", "Ok You are Very Lucky if you win"} ;
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		m_ScreenWidth = gd.getDisplayMode().getWidth();
		m_ScreenHeight = gd.getDisplayMode().getHeight();
		
		m_Background_L = new JLabel(m_Assets.getImage("MenuBG"));
		
		m_StartNewGameFrame = new JFrame("Game Set Up: new Game");
		
		m_AIDiff_CB = new JComboBox<String>(difficulty);
		m_NumOfPly_CB = new JComboBox<String>(numOfPly);
		
		m_BackToMenu_B = new JButton(m_Assets.getImage("BackToMainMenuButton"));
		m_StartGame_B = new JButton(m_Assets.getImage("StartGameButton"));
	}
	/**buildComponents
	* set up components and there attributes.
	* J.B.
	**/
	public void buildComponents()
	{
		m_StartNewGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_StartNewGameFrame.add(m_Background_L);
		
		m_Background_L.setLayout(new BoxLayout(m_Background_L, BoxLayout.Y_AXIS));
		
		m_StartNewGameFrame.setUndecorated(true);
        m_StartNewGameFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		m_StartNewGameFrame.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
		
		m_BackToMenu_B.setMargin(new Insets(0,0,0,0));
		m_StartGame_B.setMargin(new Insets(0,0,0,0));
		
		m_BackToMenu_B.setActionCommand("BackToMenu");
		m_StartGame_B.setActionCommand("StartGame");
		
		m_NumOfPly_CB.setActionCommand("NumOfPly");
		m_AIDiff_CB.setActionCommand("AIDiff");
		
		m_AIDiff_CB.setMaximumSize(new Dimension(200,30));
		m_NumOfPly_CB.setMaximumSize(new Dimension(200,30));
		
		m_NumOfPly_CB.setAlignmentX(Component.CENTER_ALIGNMENT);
		m_AIDiff_CB.setAlignmentX(Component.CENTER_ALIGNMENT);
		m_BackToMenu_B.setAlignmentX(Component.CENTER_ALIGNMENT);
		m_StartGame_B.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	/**addElements
	* add components to panels and
	* adds panels to Frame
	* J.B.
	**/
	public void addElements()
	{	
		m_Background_L.add(new JLabel("\n\n"));
		m_Background_L.add(m_AIDiff_CB);
		m_Background_L.add(new JLabel("\n\n"));
		m_Background_L.add(m_NumOfPly_CB);
		m_Background_L.add(new JLabel("\n\n"));
		m_Background_L.add(m_StartGame_B);
		m_Background_L.add(new JLabel("\n\n"));
		m_Background_L.add(m_BackToMenu_B);
		
		m_StartNewGameFrame.add(m_Background_L);
		
		m_StartNewGameFrame.pack();
		
		m_StartNewGameFrame.setVisible(true);
	}
	
	/**addActionListeners
	* adds ActionListener, which wait till
	* an action is Performed then sends 
	* a event to the type of listener.
	* J.B.
	**/
	private void addActionListeners()
	{
		m_AIDiff_CB.addActionListener(new ComboListener());
		m_NumOfPly_CB.addActionListener(new ComboListener());
		m_BackToMenu_B.addActionListener(new ButtonListener());
		m_StartGame_B.addActionListener(new ButtonListener());
	}

	/**Listeners
	* Once an event occurs the program goes here
	* and decides what to do with each event.
	*
	*@peram MenuPanel.
	*@peram nothing.
	* J.B.
	**/
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String command = event.getActionCommand();
			switch(command)
			{
				case "BackToMenu": m_GameSetUpFrame.setVisible(true); m_StartNewGameFrame.dispose();
					break;
				case "StartGame":
						/*Loading Screen...*/
						Game game = new Game(m_AIDiff_CB.getSelectedIndex(), m_NumOfPly_CB.getSelectedIndex(), m_Assets);
						SetUpBoardWindow newGame = new SetUpBoardWindow(game, m_Assets, m_StartNewGameFrame);
						m_GameSetUpFrame.dispose();
				// create default error message
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
