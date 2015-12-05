/*************************************
* @file: LoadingWindow.java
* @author: Joshua Becker
* @date:11/7/15
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
import java.awt.Color.*;

public class LoadingWindow
{
	private JFrame m_Loading_F;
	private int m_ScreenWidth, m_ScreenHeight;
	private JLabel m_Background_L, m_Loading_L;
    private ImageIcon m_Background_IC, m_Loading_L_IC;
    private static JLabel m_Message;
	
    public LoadingWindow(ImageIcon background, ImageIcon loading) // Constructor
    {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();// getting size of screen
		m_ScreenWidth = gd.getDisplayMode().getWidth();
		m_ScreenHeight = gd.getDisplayMode().getHeight();
        m_Background_IC = background;
        m_Loading_L_IC = loading;
        
		m_Background_L = new JLabel(m_Background_IC);
        m_Loading_L = new JLabel(m_Loading_L_IC);
        m_Loading_F = new JFrame("Loading Window");
        m_Message = new JLabel("Loading");
        
		m_Loading_F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_Loading_F.add(m_Background_L);
        m_Loading_F.setAlwaysOnTop(true);
        
        m_Background_L.setLayout(new BoxLayout(m_Background_L, BoxLayout.Y_AXIS));
        
		m_Loading_F.setUndecorated(true);
        m_Loading_F.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		m_Loading_F.setLayout(new BorderLayout());
		m_Loading_F.setSize(new Dimension(m_ScreenWidth,m_ScreenHeight));
        m_Loading_L.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Message.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Message.setForeground(Color.BLACK);
        
        for(int i = 0; i < m_ScreenHeight/23; i++)
            m_Background_L.add(new JLabel("\n")); // Spacing
        
        m_Background_L.add(m_Loading_L);
        m_Background_L.add(m_Message);
        
        m_Loading_F.add(m_Background_L);
		
		m_Loading_F.pack();
        
        m_Loading_F.setVisible(true);
	}
    public void dispose()
    {
        m_Loading_F.dispose();
    }
    public JFrame getMainFrame()
    {
        return m_Loading_F;
    }
    public static void updateMessage(String text)
    {
       m_Message.setText(text);
    }
    public void showScreen()
    {
		m_Loading_F.setVisible(true);
    }
}
