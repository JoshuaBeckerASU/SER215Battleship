/* 
Name: MouseAction
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
 
public class BoardMouseAction extends MouseAdapter
{
	private int m_x;
	private int m_y;
	private JLabel m_GameBoardTargets_L[];
	private Game m_Game;
	private LoadAssets m_Assets;
	private Icon m_Target1;
	private Icon m_Target2;
	private String m_CurrentPlayersName;
	private static Icon tmp;
    private static boolean m_Disabled = false;
	
	BoardMouseAction(int x , int y, Game game, JLabel[] gameBoardTargets_L, LoadAssets assets, String name)
	{
		m_x = x;
		m_y = y;
		m_Game = game;
		m_GameBoardTargets_L = gameBoardTargets_L;
		m_Assets = assets;
		m_CurrentPlayersName = name;
		m_Target1 =  m_Assets.getImage("Target");
		m_Target2 =  m_Assets.getImage("Target2");
	}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent evt) 
	{
		if(m_Game.getCurrentPlayer().allShipsSet() && (m_CurrentPlayersName.compareTo(m_Game.getCurrentPlayer().getName()) != 0) && !m_Disabled)
		{
			tmp = ((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).getIcon();
			m_GameBoardTargets_L[m_x].getComponent(m_y).setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).setIcon(m_Target2);
		}
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent evt) 
	{
        if(m_Game.getCurrentPlayer().allShipsSet() && (m_CurrentPlayersName.compareTo(m_Game.getCurrentPlayer().getName()) != 0) && !m_Disabled)
		{
            ((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).setIcon(tmp);
        }
	}
	@Override
	public void mouseClicked(java.awt.event.MouseEvent evt)
	{
		//((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).setIcon(m_Assets.getImage("HitMarker"));
	}
	@Override
	public void mousePressed(java.awt.event.MouseEvent evt)
	{
		if(m_Game.getCurrentPlayer().allShipsSet() && (m_CurrentPlayersName.compareTo(m_Game.getCurrentPlayer().getName()) != 0) && !m_Disabled)
		{
            if(((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).getText().compareTo("") == 0)
            {
                ((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).setText("USED");
                if(m_Game.getCurrentPlayer().getNumOfSelectedTargets() == 4)
                {
                    m_Game.getCurrentPlayer().incNumOfSelTargets();
                    m_Game.PlayerSelectedTarget(m_x,m_y);
                    m_Game.nextTurn();
                }else
                {
                    m_Game.getCurrentPlayer().incNumOfSelTargets();
                    m_Game.PlayerSelectedTarget(m_x,m_y);
                }
            }
		}
	}
	public static void setIcon(ImageIcon img)
	{
		tmp = img;
	}
    public static void disable()
    {
        m_Disabled = true;
    }
    public static void enable()
    {
        m_Disabled = false;
    }
}