/* 
Name: Set Up Board Window
Author: Joshua Becker
Create On: 10/26/15
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

public class SetUpBoardWindow
{
	private JFrame m_SetUpBoard_F, m_Game_F, m_OldWindow_F;
	private int m_ScreenWidth, m_ScreenHeight;
	private JButton m_BackToMenu_B, m_StartGame_B;
	private JComboBox<String> m_NumOfPly_CB, m_AIDiff_CB;
	private JLabel m_Background_L, m_Instructions_L, m_Header_L;
	private Game m_CurrentGame;
	private Player m_CurrentPlayer;
	private Ship m_CurrentShip;
	private LoadAssets m_Assets;
	
    public SetUpBoardWindow(Game game, LoadAssets assets, JFrame window)// constructer
    {
		m_CurrentGame = game;
		m_CurrentPlayer = game.getPlayer("Player 1");
		m_CurrentShip = m_CurrentPlayer.getShip(0);
		
		m_OldWindow_F = window;
		
		m_Assets = assets;
		
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
		
		m_Background_L = new JLabel(m_Assets.getImage("GameBG"));
		m_Instructions_L = new JLabel(m_Assets.getImage("Instructions"));
		m_Header_L = new JLabel(m_Assets.getImage("Instructions"));
		
		m_BackToMenu_B = new JButton("BackToMenu");
		
		m_SetUpBoard_F = new JFrame("GamePlay");
	}
	/**buildComponents
	* set up components and there attributes.
	**/
	public void buildComponents()
	{
		m_SetUpBoard_F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		m_Background_L.setLayout(new BoxLayout(m_Background_L, BoxLayout.Y_AXIS));
		m_Instructions_L.setLayout(new BoxLayout(m_Instructions_L, BoxLayout.Y_AXIS));
		
		m_Header_L.setPreferredSize(new Dimension(m_ScreenWidth, 20));
		
		m_SetUpBoard_F.setUndecorated(true);
        m_SetUpBoard_F.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		m_SetUpBoard_F.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
	}
	/**addElements
	* add components to panels and
	* adds panels to Frame
	**/
	public void addElements()
	{
		setKeyBind();
		m_Background_L.setForeground(Color.WHITE);
		
		JLabel instructions[] = {new JLabel("Use the Arrow Keys to move the ship"), new JLabel("Press the Space Bar to change the oriantation"), 
								 new JLabel("Press Enter to Place the Ship"), new JLabel("Press Esc to quit")};
		instructions[0].setAlignmentX(Component.CENTER_ALIGNMENT);
		instructions[1].setAlignmentX(Component.CENTER_ALIGNMENT);
		instructions[2].setAlignmentX(Component.CENTER_ALIGNMENT);
		instructions[3].setAlignmentX(Component.CENTER_ALIGNMENT);
		
		instructions[0].setForeground(Color.WHITE);
		instructions[1].setForeground(Color.WHITE);
		instructions[2].setForeground(Color.WHITE);
		instructions[3].setForeground(Color.WHITE);	
		
		m_Instructions_L.add(new JLabel("\n"));
		m_Instructions_L.add(instructions[0]);
		m_Instructions_L.add(instructions[1]);
		m_Instructions_L.add(instructions[2]);
		m_Instructions_L.add(instructions[3]);
		
		m_CurrentPlayer.setNextShip();
		JPanel board = m_CurrentPlayer.getBoard();
		
		board.setAlignmentX(Component.CENTER_ALIGNMENT);
		m_Instructions_L.setAlignmentX(Component.CENTER_ALIGNMENT);
		m_Header_L.setAlignmentX(Component.CENTER_ALIGNMENT);
		System.out.println("w= " + m_Header_L.getWidth() + " h = " + m_Header_L.getHeight());
		
		m_Background_L.add(m_Header_L);
		m_Background_L.add(new JLabel("\n"));
		m_Background_L.add(new JLabel("\n"));
		m_Background_L.add(board);
		m_Background_L.add(new JLabel("\n"));
		m_Background_L.add(new JLabel("\n"));
		m_Background_L.add(m_Instructions_L);
		
		m_SetUpBoard_F.add(m_Background_L);
		
		m_SetUpBoard_F.pack();
		
		m_SetUpBoard_F.setVisible(true);
	}
	/**addActionListeners
	* adds ActionListener, which wait till
	* an action is Performed then sends 
	* a event to the type of listener.
	**/
	private void addActionListeners()
	{
		m_BackToMenu_B.addActionListener(new ButtonListener());
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
				case "BackToMenu": m_SetUpBoard_F.dispose();System.exit(1);
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
	/**setKeyBind
	* maps the key strokes with there actions
	* and decides what to do with each event.
	**/
	private class KeyAction extends AbstractAction
    {
		private String m_Command;
		KeyAction()
		{
			
		}
		
		KeyAction(String command)
		{
			m_Command = command;
		}
        public void actionPerformed(ActionEvent event)
        {
			if(m_Command.compareTo("EXIT") == 0)
			{
				System.exit(1);// DOUBLE CHECK WHERE TO SEND...
			}else
			{
				switch(m_Command)
				{
					case "UP": 
							m_CurrentPlayer.updateBoard(m_CurrentShip, m_CurrentShip.getLocation().x(),  m_CurrentShip.getLocation().y()-1);
							m_Background_L.setSize(new Dimension(m_ScreenWidth-1, m_ScreenHeight-1));
							m_Background_L.setSize(new Dimension(m_ScreenWidth, m_ScreenHeight));
							break;
					case "DOWN": 
							m_CurrentPlayer.updateBoard(m_CurrentShip, m_CurrentShip.getLocation().x(),  m_CurrentShip.getLocation().y()+1);
							m_Background_L.setSize(new Dimension(m_ScreenWidth-1, m_ScreenHeight-1));
							m_Background_L.setSize(new Dimension(m_ScreenWidth, m_ScreenHeight));
							break;
					case "LEFT": 
							m_CurrentPlayer.updateBoard(m_CurrentShip, m_CurrentShip.getLocation().x()-1,  m_CurrentShip.getLocation().y());
							m_Background_L.setSize(new Dimension(m_ScreenWidth-1, m_ScreenHeight-1));
							m_Background_L.setSize(new Dimension(m_ScreenWidth, m_ScreenHeight));
							break;
					case "RIGHT": 
							m_CurrentPlayer.updateBoard(m_CurrentShip, m_CurrentShip.getLocation().x()+1,  m_CurrentShip.getLocation().y());
							m_Background_L.setSize(new Dimension(m_ScreenWidth-1, m_ScreenHeight-1));
							m_Background_L.setSize(new Dimension(m_ScreenWidth, m_ScreenHeight));
							break;
					case "SPACE":
							m_CurrentPlayer.flipAxis(m_CurrentShip);
							break;
					case "ENTER":
							m_CurrentPlayer.addToTaken(m_CurrentShip.x(),m_CurrentShip.y(),m_CurrentShip);
							m_CurrentShip = m_CurrentPlayer.getNextShip();
							m_CurrentPlayer.setNextShip();
							System.out.println(m_CurrentPlayer.get)
							if(m_CurrentPlayer.allShipsSet())
							{
								// DOUBLE CHECK IF THEY ARE READY...
								GameWindow newGame = new GameWindow(m_CurrentGame,m_Assets,m_SetUpBoard_F);
								m_OldWindow_F.dispose();
								// DELETE MEMU BUTTONS AND THINGS...
							}
							break;
				}
			}
		}
    }
	/**setKeyBind
	* maps the key strokes with there actions
	* and decides what to do with each event.
	**/
	private void setKeyBind()
	{
		m_Background_L.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"),"EXIT");
		m_Background_L.getActionMap().put( "EXIT", new KeyAction("EXIT"));
		
		m_Background_L.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"ENTER");
		m_Background_L.getActionMap().put( "ENTER", new KeyAction("ENTER"));
		
		m_Background_L.getInputMap().put(KeyStroke.getKeyStroke("UP"),"UP");
		m_Background_L.getActionMap().put( "UP", new KeyAction("UP"));
		
		m_Background_L.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"DOWN");
		m_Background_L.getActionMap().put( "DOWN", new KeyAction("DOWN"));
		
		m_Background_L.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"LEFT");
		m_Background_L.getActionMap().put( "LEFT", new KeyAction("LEFT"));
		
		m_Background_L.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"RIGHT");
		m_Background_L.getActionMap().put( "RIGHT", new KeyAction("RIGHT"));
		
		m_Background_L.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"SPACE");
		m_Background_L.getActionMap().put( "SPACE", new KeyAction("SPACE"));
	}
}
