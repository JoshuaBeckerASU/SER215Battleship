/**
Name: Load Game Window
Author: Joshua Becker
Create On: 9/9/15
Updated On: 9/19/15
Contributors:
 **/
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.ImageIcon.*;
import javax.swing.JFrame;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class LoadGameWindow implements Serializable
{
	private JFrame m_LoadGameFrame, m_MenuFrame;
	private int m_ScreenWidth, m_ScreenHeight;
	private JButton m_StartNewGame_B, m_BackToMenu_B, m_LoadAGame_B;
	private JPanel m_MenuButton_P;
	private JLabel m_Background_L;
	private LoadAssets m_Assets;
	
    public LoadGameWindow(JFrame window, LoadAssets assets)// constructer
    {
		m_Assets = assets;
		
		m_MenuFrame = window;
		
		createComponents();
		
		buildComponents();
		
		addActionListeners();
		
		addElements();
	}
	/**createComponents
	* creates components and gives them
	* default values.
	**/
	public void createComponents()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		m_ScreenWidth = gd.getDisplayMode().getWidth();
		m_ScreenHeight = gd.getDisplayMode().getHeight();
		
		m_Background_L = new JLabel(m_Assets.getImage("MenuBG"));
		
		m_LoadGameFrame = new JFrame("Game Set Up");
		
		m_MenuButton_P = new JPanel();
		
		m_StartNewGame_B = new JButton(m_Assets.getImage("StartANewGameButton"));
		m_LoadAGame_B = new JButton(m_Assets.getImage("LoadAGameButton"));
		m_BackToMenu_B = new JButton(m_Assets.getImage("BackToMainMenuButton"));
	}
	/**buildComponents
	* set up components and there attributes.
	**/
	public void buildComponents()
	{
		m_LoadGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_LoadGameFrame.add(m_Background_L);
		m_Background_L.setLayout(new FlowLayout());
		
		m_LoadGameFrame.setUndecorated(true);
        m_LoadGameFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		m_LoadGameFrame.setLayout(new BorderLayout());
		m_LoadGameFrame.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
		
		m_MenuButton_P.setLayout(new FlowLayout());
		
		m_BackToMenu_B.setMargin(new Insets(0,0,0,0));
		m_StartNewGame_B.setMargin(new Insets(0,0,0,0));
		m_LoadAGame_B.setMargin(new Insets(0,0,0,0));
		
		m_BackToMenu_B.setActionCommand("BackToMenu");
		m_StartNewGame_B.setActionCommand("StartANewGame");
		m_LoadAGame_B.setActionCommand("LoadAGame");
		
	}
	/**addElements
	* add components to panels and
	* adds panels to Frame
	**/
	public void addElements()
	{
		m_Background_L.add(m_StartNewGame_B);
		m_Background_L.add(m_LoadAGame_B);
		m_Background_L.add(m_BackToMenu_B);
		
		m_LoadGameFrame.add(m_Background_L);
		
		m_LoadGameFrame.pack();
		
		m_LoadGameFrame.setVisible(true);
	}
	
	/**addActionListeners
	* adds ActionListener, which wait till
	* an action is Performed then sends 
	* a event to the type of listener.
	**/
	private void addActionListeners()
	{
		m_StartNewGame_B.addActionListener(new ButtonListener());
		m_BackToMenu_B.addActionListener(new ButtonListener());
		m_LoadAGame_B.addActionListener(new ButtonListener());
	}

	/**Listeners
	* Once an event occurs the program goes here
	* and decides what to do with each event.
	**/
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String command = event.getActionCommand();
			switch(command)
			{
				case "LoadAGame":LoadGameWindow gameMenu1 = new LoadGameWindow(m_LoadGameFrame, m_Assets);m_MenuFrame.setVisible(false);
					break;
				case "BackToMenu": m_MenuFrame.setVisible(true); m_LoadGameFrame.dispose();
					break;
				case "StartANewGame":StartNewGameWindow gameMenu2 = new StartNewGameWindow(m_LoadGameFrame, m_Assets);m_MenuFrame.setVisible(false);
					break;
			}
		}  
	}
}
