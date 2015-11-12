/* 
Name: Menu Panel 
Author: Joshua Becker
Create On: 9/9/15
Updated On: 9/19/15
Contributors:
 */

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.ImageIcon.*;
import javax.swing.JFrame;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class MenuWindow
{
	private JFrame m_MenuFrame;
	private int m_ScreenWidth, m_ScreenHeight;
	private JButton m_MultiPlayer_B, m_Exit_B, m_Settings_B, m_SinglePlayer_B;
	private JLabel m_Background_L;
    private JLabel m_MenuButton_L;
	private LoadAssets m_Assets;
	
    public MenuWindow(LoadAssets assets)// constructer
    {
		m_Assets = assets;
		
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
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// geting size of screen
		m_ScreenWidth = gd.getDisplayMode().getWidth();
		m_ScreenHeight = gd.getDisplayMode().getHeight();
        
		
		m_Background_L = new JLabel(m_Assets.getImage("MenuBG"));
        m_MenuButton_L = new JLabel(m_Assets.getImage("MenuBox"));
		
		m_MenuFrame = new JFrame("Menu");
		
		m_MultiPlayer_B = new JButton(m_Assets.getImage("MultiPlayerButton"));
		m_Settings_B = new JButton(m_Assets.getImage("SettingsButton"));
		m_Exit_B = new JButton(m_Assets.getImage("ExitButton"));
        m_SinglePlayer_B = new JButton(m_Assets.getImage("SinglePlayerButton"));
	}
	/**buildComponents
	* set up components and there attributes.
	* J.B.
	**/
	public void buildComponents()
	{
		m_MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_MenuFrame.add(m_Background_L);
		m_Background_L.setLayout(new BoxLayout(m_Background_L, BoxLayout.Y_AXIS));
        
        m_MenuButton_L.setLayout(new BoxLayout(m_MenuButton_L, BoxLayout.Y_AXIS));
        m_MenuButton_L.setSize(new Dimension(m_Assets.getImage("MenuBox").getIconWidth(),m_Assets.getImage("MenuBox").getIconHeight()));
		
		m_MenuFrame.setUndecorated(true);
        m_MenuFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		m_MenuFrame.setLayout(new BorderLayout());
		m_MenuFrame.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
		
		m_Exit_B.setMargin(new Insets(0,0,0,0));
		m_MultiPlayer_B.setMargin(new Insets(0,0,0,0));
		m_Settings_B.setMargin(new Insets(0,0,0,0));
        m_SinglePlayer_B.setMargin(new Insets(0,0,0,0));
		
		m_Exit_B.setActionCommand("Exit");
		m_MultiPlayer_B.setActionCommand("MultiPlayer");
		m_Settings_B.setActionCommand("Settings");
        m_SinglePlayer_B.setActionCommand("SinglePlayer");
        
        m_Exit_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_MultiPlayer_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Settings_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_SinglePlayer_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_MenuButton_L.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	/**addElements
	* add components to panels and
	* adds panels to Frame
	* J.B.
	**/
	public void addElements()
	{
        for(int i = 0; i < 13; i++)
            m_MenuButton_L.add(new JLabel("\n\n"));//spaceing
        
		m_MenuButton_L.add(m_MultiPlayer_B);
        m_MenuButton_L.add(new JLabel("\n\n"));
        m_MenuButton_L.add(m_SinglePlayer_B);
        m_MenuButton_L.add(new JLabel("\n\n"));
		m_MenuButton_L.add(m_Settings_B);
        m_MenuButton_L.add(new JLabel("\n\n"));
		m_MenuButton_L.add(m_Exit_B);
        
        JLabel tmp = new JLabel(m_Assets.getImage("MenuHeader"));
        tmp.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        m_Background_L.add(tmp);
        m_Background_L.add(m_MenuButton_L);
		
		m_MenuFrame.add(m_Background_L);
		
		m_MenuFrame.pack();
		
		m_MenuFrame.setVisible(true);
	}
	
	/**addActionListeners
	* adds ActionListener, which wait till
	* an action is Performed then sends 
	* a event to the type of listener.
	* J.B.
	**/
	private void addActionListeners()
	{
		m_MultiPlayer_B.addActionListener(new ButtonListener());
		m_Exit_B.addActionListener(new ButtonListener());
		m_Settings_B.addActionListener(new ButtonListener());
        m_SinglePlayer_B.addActionListener(new ButtonListener());
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
				case "MultiPlayer": new GameSetUpWindow(m_MenuFrame, m_Assets);//new MultiPlayerWindow(m_MenuFrame, m_Assets);
					break;
                case "SinglePlayer": new SinglePlayerWindow(m_MenuFrame, m_Assets);
                    break;
				case "Exit": m_MenuFrame.dispose(); System.exit(1);
					break;
				case "Settings":
					break;
			}
		}  
	}
}
