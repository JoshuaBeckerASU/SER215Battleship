/*************************************
* @file: SinglePlayerWindow.java
* @author: Joshua Becker
* @date:11/12/15
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

public class SinglePlayerWindow implements Serializable
{
	private JFrame m_SinglePlayer_F, m_OldWindow;
	private int m_ScreenWidth, m_ScreenHeight;
    private Game m_Game;
	private JButton m_ResetLobby_B, m_BackToMainMenu_B, m_StartGame_B;
	private JLabel m_Background_L;
    private JLabel m_Slots_L;
    private JLabel m_Body_L, m_Header_L, m_HeaderText_L, m_HeaderButtons_L;
    
    private LobbySlot m_Slots[];
	private LoadAssets m_Assets;
	
    public SinglePlayerWindow(JFrame oldWindow)// constructer
    {
		m_Assets = Main.s_Assets;
        m_OldWindow = oldWindow;
        
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
        
        m_Slots = new LobbySlot[3];
		
		m_Background_L = new JLabel(m_Assets.getImage("LobbyBG"));
        m_Body_L = new JLabel(m_Assets.getImage("LobbyBox"));
        m_Header_L = new JLabel();
        m_HeaderButtons_L = new JLabel(m_Assets.getImage("GameBoardBlank"));
        m_HeaderText_L = new JLabel(m_Assets.getImage("Blank"));
        
        m_Slots_L = new JLabel();
		
		m_SinglePlayer_F = new JFrame("SinglePlayerWindow");
		
		m_ResetLobby_B = new JButton(m_Assets.getImage("ResetLobby"));
		m_StartGame_B = new JButton(m_Assets.getImage("StartGameButton"));
		m_BackToMainMenu_B = new JButton(m_Assets.getImage("BackToMainMenuButton"));
        
        m_Slots[0] = new LobbySlot(1);
        m_Slots[1] = new LobbySlot(2);
        m_Slots[2] = new LobbySlot(3);
	}
	/**buildComponents
	* set up components and there attributes.
	* 
	**/
	public void buildComponents()
	{
		m_SinglePlayer_F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_SinglePlayer_F.add(m_Background_L);
		m_Background_L.setLayout(new BoxLayout(m_Background_L, BoxLayout.Y_AXIS));
        
        m_Header_L.setLayout(new BoxLayout(m_Header_L,BoxLayout.Y_AXIS));
        m_HeaderText_L.setLayout(new FlowLayout(FlowLayout.CENTER,200,0));
        m_HeaderButtons_L.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        
        m_Body_L.setLayout(new BorderLayout());
        
        m_Slots_L.setLayout(new FlowLayout(FlowLayout.CENTER,3, 50));
        
        //m_Slots_L.setSize(new Dimension(m_Assets.getImage("MenuBox").getIconWidth(),m_Assets.getImage("MenuBox").getIconHeight()));
        m_HeaderButtons_L.setMaximumSize(new Dimension(1000, 50));
        m_HeaderButtons_L.setMinimumSize(new Dimension(1000, 50));
        m_HeaderButtons_L.setSize(new Dimension(950, 50));
        m_HeaderButtons_L.setPreferredSize(new Dimension(1000, 50));
        
        m_Header_L.setMaximumSize(new Dimension(1000, 50));
        m_Header_L.setMinimumSize(new Dimension(1000, 50));
        m_Header_L.setPreferredSize(new Dimension(1000, 50));
        m_Header_L.setSize(new Dimension(950, 50));
        
        m_Slots_L.setMaximumSize(new Dimension(1000, 200));
        m_Slots_L.setMinimumSize(new Dimension(1000, 200));
        m_Slots_L.setPreferredSize(new Dimension(1000, 200));
        m_Slots_L.setSize(new Dimension(1000, 200));
        //m_Header_L.setMaximumSize(new Dimension(1000, 40));
		
		m_SinglePlayer_F.setUndecorated(true);
        
        if(MenuWindow.isWindowed())
        {
            m_SinglePlayer_F.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        }else
            m_SinglePlayer_F.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        
        m_SinglePlayer_F.setLocation(0,0);
        
		m_SinglePlayer_F.setLayout(new BorderLayout());
		m_SinglePlayer_F.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
		
		m_BackToMainMenu_B.setMargin(new Insets(0,0,0,0));
		m_ResetLobby_B.setMargin(new Insets(0,0,0,0));
		m_StartGame_B.setMargin(new Insets(0,0,0,0));
		
		m_BackToMainMenu_B.setActionCommand("BackToMainMenu");
		m_ResetLobby_B.setActionCommand("ResetLobby");
		m_StartGame_B.setActionCommand("StartGame");
        
        m_BackToMainMenu_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_ResetLobby_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_StartGame_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Slots_L.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Slots[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Slots[1].setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Slots[2].setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Header_L.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_HeaderText_L.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_HeaderButtons_L.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Body_L.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        MouseListener[] ml = (MouseListener[])m_Slots[0].getisActive().getListeners(MouseListener.class);//makeing the first check box un-editable, most always be one player...

        for (int i = 0; i < ml.length; i++)
            m_Slots[0].getisActive().removeMouseListener( ml[i] );
        
        m_Slots[0].getisActive().getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        m_Slots[0].getisActive().getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "none");
        
        m_Slots[0].getType().setEditable(false);
	}
	/**addElements
	* add components to panels and
	* adds panels to Frame
	* 
	**/
	public void addElements()
	{   
        m_Slots_L.add(m_Slots[0]);
        m_Slots_L.add(m_Slots[1]);
        //m_Slots_L.add(m_Slots[2]);
        m_Slots[2].setisActive(false);
        
        m_HeaderButtons_L.add(m_StartGame_B);
        m_HeaderButtons_L.add(m_ResetLobby_B);
        m_HeaderButtons_L.add(m_BackToMainMenu_B);
        
        m_HeaderText_L.add(new JLabel("Active Player"));
        m_HeaderText_L.add(new JLabel("Player Name"));
        m_HeaderText_L.add(new JLabel("Player Difficulty"));
        m_HeaderText_L.add(new JLabel("Type Of Player"));
        
        m_Header_L.add(m_HeaderButtons_L);
       // m_Header_L.add(m_HeaderText_L);
        /*m_Body_L.add(new JLabel("\n\n"));
        m_Body_L.add(m_SlotTwo);
        m_Body_L.add(new JLabel("\n\n"));
		m_Body_L.add(m_SlotThree);*/
		m_Body_L.add(m_Header_L, BorderLayout.NORTH);
        m_Body_L.add(m_Slots_L, BorderLayout.CENTER);
        
        JLabel tmp = new JLabel(m_Assets.getImage("MenuHeader"));
        tmp.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        m_Background_L.add(tmp);
        m_Background_L.add(m_Body_L);
		
		m_SinglePlayer_F.add(m_Background_L);
		
		m_SinglePlayer_F.pack();
		
		m_SinglePlayer_F.setVisible(true);
        m_OldWindow.setVisible(false);
	}
	/**addActionListeners
	* adds ActionListener, which wait till
	* an action is Performed then sends 
	* a event to the type of listener.
	* 
	**/
	private void addActionListeners()
	{
		m_ResetLobby_B.addActionListener(new ButtonListener());
		m_BackToMainMenu_B.addActionListener(new ButtonListener());
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
				case "ResetLobby": //new GameSetUpWindow(m_SinglePlayer_F, m_Assets);//new ResetLobbyWindow(m_SinglePlayer_F, m_Assets);
                    break;
				case "BackToMainMenu": m_OldWindow.setVisible(true);m_SinglePlayer_F.dispose();
					break;
				case "StartGame":
                        m_OldWindow.dispose();
                        m_Game = new Game(m_Slots[0].getDiff().getSelectedIndex(),m_Slots);
                        m_Game.setUpGame(m_Game);
					break;
			}
		}  
	}
}
