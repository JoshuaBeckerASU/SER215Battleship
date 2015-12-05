/*************************************
* @file: LobbySlot.java
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
import javax.swing.*;
import java.io.*;

public class LobbySlot extends JLabel implements Serializable
{
    private JTextArea m_Name_TA;
    private JComboBox<String> m_Difficulty_CB;
    private JComboBox<String> m_TypeOfPlayer_CB;
    private JCheckBox m_isActive_CkB;
    private LoadAssets m_Assets;
    private int m_index;
    LobbySlot(int index)
    {
        m_index = index;
        m_Assets = Main.s_Assets;
        
        setIcon(m_Assets.getImage("SlotBG"));
        setLayout(new FlowLayout(FlowLayout.CENTER, 200, 20));
        
        buildComponents();
        addComponents();
    }
    private void buildComponents()
    {
        m_Name_TA = new JTextArea("Player " + m_index);
        
        String tmp[] = {"1", "2", "3"};
        m_Difficulty_CB = new JComboBox<String>(tmp);
        
        String tmp2 [] = {"Human Player", "Computer Player"};
        m_TypeOfPlayer_CB = new JComboBox<String>(tmp2);
        m_isActive_CkB = new JCheckBox("", true);
        m_isActive_CkB.setHorizontalTextPosition(SwingConstants.LEFT);
        
        m_isActive_CkB.setMargin(new Insets(0,0,0,0));
        m_isActive_CkB.setBackground(Color.BLACK);
        
        m_Name_TA.setBackground(Color.BLACK);
        m_Difficulty_CB.setBackground(Color.BLACK);
        m_TypeOfPlayer_CB.setBackground(Color.BLACK);
        m_isActive_CkB.setBackground(Color.BLACK);
        
        m_Name_TA.setForeground(Color.WHITE);
        m_Difficulty_CB.setForeground(Color.WHITE);
        m_TypeOfPlayer_CB.setForeground(Color.WHITE);
        m_isActive_CkB.setForeground(Color.WHITE);
        
        m_Name_TA.setMinimumSize(new Dimension(50,20));
        m_Name_TA.setPreferredSize(new Dimension(100,20));
        m_Name_TA.setMaximumSize(new Dimension(100,20));
        m_Name_TA.setEditable(false);
    }
    private void addComponents()
    {
        add(m_isActive_CkB);
        add(m_Name_TA);
        add(m_Difficulty_CB);
        add(m_TypeOfPlayer_CB);
    }
    public void disableIsActive()
    {
        m_isActive_CkB.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        m_isActive_CkB.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "none");
    }
    public void disableType()
    {
        m_TypeOfPlayer_CB.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        m_TypeOfPlayer_CB.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "none");
    }
    public void disableDiff()
    {
        m_Difficulty_CB.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        m_Difficulty_CB.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "none");
    }
    public JTextArea getNameTA()
    {
        return m_Name_TA;
    }
    public JComboBox getDiff()
    {
        return m_Difficulty_CB;
    }
    public JComboBox getType()
    {
        return m_TypeOfPlayer_CB;
    }
    public JCheckBox getisActive()
    {
        return m_isActive_CkB;
    }
    public void setisActive(boolean isActive)
    {
        m_isActive_CkB.setSelected(isActive);
    }
    public void setType(int type)
    {
        m_TypeOfPlayer_CB.setSelectedIndex(type);
    }
}