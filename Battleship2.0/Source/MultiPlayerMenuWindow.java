/******************************
* @file: MultiPlayerSetUpWindow.java
* @author: Joshua Becker
* @date:
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

public class MultiPlayerMenuWindow
{
	private JFrame m_MultiPlayerMenu_F, m_OldWindow_F;
	private int m_ScreenWidth, m_ScreenHeight;
	private JButton m_JoinGame_B, m_HostGame_B, m_BackToMainMenu_B;
	private JLabel m_Background_L;
    private JLabel m_MenuButton_L;
    private Game m_Game;
	private LoadAssets m_Assets;
    private LobbySlot [] m_LSlots;
    private Thread m_Client_T;
    private Thread m_Server_T;
    private GameServer_ m_Server;
    private GameClient_ m_Client;
    //private GameClient m_Client;
	
    public MultiPlayerMenuWindow(JFrame oldWindow, LoadAssets assets)// constructer
    {
        m_OldWindow_F = oldWindow;
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
		
		m_MultiPlayerMenu_F = new JFrame("Menu");
		
		m_JoinGame_B = new JButton(m_Assets.getImage("JoinGameButton"));
		m_BackToMainMenu_B = new JButton(m_Assets.getImage("MainMenuButton"));
		m_HostGame_B = new JButton(m_Assets.getImage("HostGameButton"));
	}
	/**buildComponents
	* set up components and there attributes.
	* J.B.
	**/
	public void buildComponents()
	{
		m_MultiPlayerMenu_F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_MultiPlayerMenu_F.add(m_Background_L);
		m_Background_L.setLayout(new BoxLayout(m_Background_L, BoxLayout.Y_AXIS));
        
        m_MenuButton_L.setLayout(new BoxLayout(m_MenuButton_L, BoxLayout.Y_AXIS));
        m_MenuButton_L.setSize(new Dimension(m_Assets.getImage("MenuBox").getIconWidth(),m_Assets.getImage("MenuBox").getIconHeight()));
		
		m_MultiPlayerMenu_F.setUndecorated(true);
        m_MultiPlayerMenu_F.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		m_MultiPlayerMenu_F.setLayout(new BorderLayout());
		m_MultiPlayerMenu_F.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
		
		m_HostGame_B.setMargin(new Insets(0,0,0,0));
		m_JoinGame_B.setMargin(new Insets(0,0,0,0));
		m_BackToMainMenu_B.setMargin(new Insets(0,0,0,0));
		
		m_HostGame_B.setActionCommand("HostGame");
		m_JoinGame_B.setActionCommand("JoinGame");
		m_BackToMainMenu_B.setActionCommand("BackToMainMenu");
        
        m_HostGame_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_JoinGame_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_BackToMainMenu_B.setAlignmentX(Component.CENTER_ALIGNMENT);
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
            m_MenuButton_L.add(new JLabel("\n"));//spaceing
        
		m_MenuButton_L.add(m_JoinGame_B);
        m_MenuButton_L.add(new JLabel("\n"));
        m_MenuButton_L.add(new JLabel("\n"));
        m_MenuButton_L.add(m_HostGame_B);
        m_MenuButton_L.add(new JLabel("\n"));
        m_MenuButton_L.add(new JLabel("\n"));
        m_MenuButton_L.add(m_BackToMainMenu_B);

        
        JLabel tmp = new JLabel(m_Assets.getImage("MenuHeader"));
        tmp.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        m_Background_L.add(tmp);
        m_Background_L.add(m_MenuButton_L);
		
		m_MultiPlayerMenu_F.add(m_Background_L);
		
		m_MultiPlayerMenu_F.pack();
		
		m_MultiPlayerMenu_F.setVisible(true);
	}
	
	/**addActionListeners
	* adds ActionListener, which wait till
	* an action is Performed then sends 
	* a event to the type of listener.
	* J.B.
	**/
	private void addActionListeners()
	{
		m_JoinGame_B.addActionListener(new ButtonListener());
		m_HostGame_B.addActionListener(new ButtonListener());
		m_BackToMainMenu_B.addActionListener(new ButtonListener());
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
				case "HostGame": m_OldWindow_F.dispose();
                    m_Server = new GameServer_("Josh");
                    m_Server_T = new Thread(m_Server);
                    m_Server_T.start();
                    
                    m_LSlots = new LobbySlot[2];
                    m_LSlots[0] = new LobbySlot(1,m_Assets);
                    m_LSlots[1] = new LobbySlot(2,m_Assets);
                    m_Game = new Game(-1, m_LSlots, m_Assets);
                    
					break;
                case "JoinGame": m_OldWindow_F.dispose();
                        m_LSlots = new LobbySlot[2];
                        m_LSlots[0] = new LobbySlot(1,m_Assets);
                        m_LSlots[1] = new LobbySlot(2,m_Assets);
                        m_Game = new Game(-1, m_LSlots, m_Assets);
                        
                    break;
				case "BackToMainMenu": m_OldWindow_F.setVisible(true); m_MultiPlayerMenu_F.dispose();
					break;
				case "Settings":
					break;
			}
		}  
	}
}
