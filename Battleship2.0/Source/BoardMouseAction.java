/*************************************
* @file: BoardMouseAction.java
* @author: Joshua Becker
* @date:10/26/15
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
 
public class BoardMouseAction extends MouseAdapter implements Serializable
{
	private int m_x;
	private int m_y;
	private JLabel m_GameBoardTargets_L[];
	private Game m_Game;
	private LoadAssets m_Assets;
	private Icon m_Target1;
	private Icon m_Target2;
	private String m_CurrentPlayersName;
	private static ImageIcon tmp;
    private static int shotCount;
    private boolean m_Disabled = false;
    private static boolean m_DisableFlag = true;
    
	
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
        shotCount = 0;
	}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent evt) 
	{
        if(!m_Disabled)
		if(m_Game.getCurrentPlayer().allShipsSet() && (m_CurrentPlayersName.compareTo(m_Game.getCurrentPlayer().getName()) != 0))
		{
			tmp = (ImageIcon)((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).getIcon();
			m_GameBoardTargets_L[m_x].getComponent(m_y).setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).setIcon(m_Target2);
		}
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent evt) 
	{
        if(!m_Disabled)
        {
            if(m_Game.getCurrentPlayer().allShipsSet() && (m_CurrentPlayersName.compareTo(m_Game.getCurrentPlayer().getName()) != 0))
            {
                ((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).setIcon(tmp);
            }
        }else
        {
            if(m_DisableFlag)
            {
                ((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).setIcon(tmp);
                m_DisableFlag = false;
            }
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
        if(!m_Disabled)
		if(m_Game.getCurrentPlayer().allShipsSet() && (m_CurrentPlayersName.compareTo(m_Game.getCurrentPlayer().getName()) != 0))
		{
            if(((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).getText().compareTo("") == 0)
            {
                if(!m_Game.isMultiplayer())
                {
                    if(m_Game.getCurrentPlayer().getNumOfSelectedTargets() == 4)
                    {
                        m_Game.getCurrentPlayer().incNumOfSelTargets();
                        m_Game.playerSelectedTarget(m_x,m_y);
                        System.out.println("Last Shot");
                        m_Game.nextTurn();
                    }else
                    {
                        m_Game.getCurrentPlayer().incNumOfSelTargets();
                        m_Game.playerSelectedTarget(m_x,m_y);
                    }
                }else
                {
                    if(shotCount == 4)
                    {
                        m_Game.getCurrentPlayer().incNumOfSelTargets();
                        m_Game.multiPlayerSelTarget(m_x,m_y, m_Game.getPlayer(0), m_Game.getPlayer(1),true,shotCount);
                        System.out.println("Last Shot");
                        shotCount = 0;
                        m_Game.nextTurn();
                    }else
                    {
                        m_Game.getCurrentPlayer().incNumOfSelTargets();
                        m_Game.multiPlayerSelTarget(m_x,m_y, m_Game.getPlayer(0), m_Game.getPlayer(1),true,shotCount);
                        shotCount++;
                    }
                }
            }
		}
	}
	public static void setIcon(ImageIcon img)
	{
		tmp = img;
	}
    public static ImageIcon getIcon()
    {
        return tmp;
    }
    public void disable()
    {
        m_Disabled = true;
        m_DisableFlag = true;
    }
    public void enable()
    {
        m_Disabled = false;
        m_DisableFlag = false;
    }
    public void setExitedIcon()
    {
        ((JLabel) m_GameBoardTargets_L[m_x].getComponent(m_y)).setIcon(tmp);
    }
}