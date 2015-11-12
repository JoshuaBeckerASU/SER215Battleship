/******************************
* @file: SinglePlayerWindow.java
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

public class SinglePlayerWindow
{
	private JFrame m_SinglePlayer_F, m_OldWindow;
	private int m_ScreenWidth, m_ScreenHeight;
	private JButton m_ResetLobby_B, m_BackToMainMenu_B, m_Settings_B, m_SinglePlayer_B;
	private JLabel m_Background_L;
    private JLabel m_Slots_L;
    private JLabel m_Content_L;
    private Slot m_SlotOne, m_SlotTwo, m_SlotThree;
	private LoadAssets m_Assets;
	
    public SinglePlayerWindow(JFrame oldWindow ,LoadAssets assets)// constructer
    {
		m_Assets = assets;
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
		
		m_Background_L = new JLabel(m_Assets.getImage("LobbyBG"));
        m_Content_L = new JLabel(m_Assets.getImage("LobbyBox"));
        
        m_Slots_L = new JLabel(m_Assets.getImage("GameBoardBlank"));
        
       /* m_SlotOne_L = new JLabel(m_Assets.getImage("GameBoardBlank"));
        m_SlotTwo_L = new JLabel(m_Assets.getImage("GameBoardBlank"));
        m_SlotThree_L = new JLabel(m_Assets.getImage("GameBoardBlank"));*/
		
		m_SinglePlayer_F = new JFrame("SinglePlayerWindow");
		
		m_ResetLobby_B = new JButton(m_Assets.getImage("ResetLobbyButton"));
		m_Settings_B = new JButton(m_Assets.getImage("SettingsButton"));
		m_BackToMainMenu_B = new JButton(m_Assets.getImage("BackToMainMenuButton"));
        m_SinglePlayer_B = new JButton(m_Assets.getImage("SinglePlayerButton"));
        
        m_SlotOne = new Slot(1);
        m_SlotTwo = new Slot(2);
        m_SlotThree = new Slot(3);
	}
    
    private class Slot extends JLabel
    {
        private JTextArea m_Name_TA;
        private JSpinner m_Difficulty_S;
        private JSpinner m_TypeOfPlayer_S;
        private int m_index;
        Slot(int index)
        {
            m_index = index;
            
            setIcon(m_Assets.getImage("SettingsButton"));
            setLayout(new FlowLayout(FlowLayout.CENTER, 100, 3));
            
            buildComponents();
            addComponents();
        }
        private void buildComponents()
        {
            m_Name_TA = new JTextArea("Player " + m_index);
            m_Difficulty_S = new JSpinner();
            m_TypeOfPlayer_S = new JSpinner();
        }
        private void addComponents()
        {
            add(m_Name_TA);
            add(m_Difficulty_S);
            add(m_TypeOfPlayer_S);
        }
        public JTextArea getNameTA()
        {
            return m_Name_TA;
        }
        public JSpinner getDiff()
        {
            return m_Difficulty_S;
        }
        public JSpinner getType()
        {
            return m_TypeOfPlayer_S;
        }
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
        
        m_Content_L.setLayout(new BorderLayout());
        
        m_Slots_L.setLayout(new BoxLayout(m_Slots_L, BoxLayout.Y_AXIS));
        m_Slots_L.setSize(new Dimension(m_Assets.getImage("MenuBox").getIconWidth(),m_Assets.getImage("MenuBox").getIconHeight()));
		
		m_SinglePlayer_F.setUndecorated(true);
        m_SinglePlayer_F.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		m_SinglePlayer_F.setLayout(new BorderLayout());
		m_SinglePlayer_F.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
		
		m_BackToMainMenu_B.setMargin(new Insets(0,0,0,0));
		m_ResetLobby_B.setMargin(new Insets(0,0,0,0));
		m_Settings_B.setMargin(new Insets(0,0,0,0));
        m_SinglePlayer_B.setMargin(new Insets(0,0,0,0));
		
		m_BackToMainMenu_B.setActionCommand("BackToMainMenu");
		m_ResetLobby_B.setActionCommand("ResetLobby");
		m_Settings_B.setActionCommand("Settings");
        
        m_BackToMainMenu_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_ResetLobby_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Settings_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_SinglePlayer_B.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Slots_L.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_SlotOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_SlotTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_SlotThree.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        m_Content_L.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	/**addElements
	* add components to panels and
	* adds panels to Frame
	* 
	**/
	public void addElements()
	{
        m_Slots_L.add(m_SlotOne);
        m_Slots_L.add(m_SlotTwo);
        m_Slots_L.add(m_SlotThree);
      
		m_Content_L.add(m_Slots_L, BorderLayout.CENTER);
        /*m_Content_L.add(new JLabel("\n\n"));
        m_Content_L.add(m_SlotTwo);
        m_Content_L.add(new JLabel("\n\n"));
		m_Content_L.add(m_SlotThree);*/
		m_Content_L.add(m_BackToMainMenu_B, BorderLayout.NORTH);
        
        JLabel tmp = new JLabel(m_Assets.getImage("MenuHeader"));
        tmp.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        m_Background_L.add(tmp);
        m_Background_L.add(m_Content_L);
		
		m_SinglePlayer_F.add(m_Background_L);
		
		m_SinglePlayer_F.pack();
		
		m_SinglePlayer_F.setVisible(true);
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
		m_Settings_B.addActionListener(new ButtonListener());
        m_SinglePlayer_B.addActionListener(new ButtonListener());
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
				case "ResetLobby": new GameSetUpWindow(m_SinglePlayer_F, m_Assets);//new ResetLobbyWindow(m_SinglePlayer_F, m_Assets);
					break;
                case "SinglePlayer": new GameSetUpWindow(m_SinglePlayer_F, m_Assets);//new SinglePlayerWindow(m_SinglePlayer_F, M_Assets)
                    break;
				case "BackToMainMenu": System.exit(1);
					break;
				case "Settings":
					break;
			}
		}  
	}
}
