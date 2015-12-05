/******************************
* @file: WaitForConnection.java
* @author: Alec Shinn
* @date:
* @description: Joshua Becker
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

public class WaitForConnection extends JFrame
{
	private JFrame m_OldWindow_jf,m_ConnectionWindow_jf;
	private JLabel m_Background_jl;
    private JLabel m_Content_L, m_Message_L;
	private LoadAssets m_Assets;
	private int m_ScreenWidth,m_ScreenHeight;
    
	public WaitForConnection(JFrame oldWindow, String message)
    {
		m_OldWindow_jf=oldWindow;
		m_Assets= Main.s_Assets;
        m_Message_L = new JLabel(message);

        createComponents();
		buildCompenents();
		addElements();

	}
	public WaitForConnection(String message)
    {
		m_Assets= Main.s_Assets;
        m_Message_L = new JLabel(message);

        createComponents();
		buildCompenents();
		addElements();

	}

	public void createComponents(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		m_ScreenWidth = gd.getDisplayMode().getWidth();
		m_ScreenHeight = gd.getDisplayMode().getHeight();

		m_ConnectionWindow_jf=new JFrame("Connecting");

		m_Background_jl=new JLabel(m_Assets.getImage("SlotBG"));
        
        m_Content_L = new JLabel(m_Assets.getImage("Waiting"));
	}

	public void buildCompenents()
    {
		m_ConnectionWindow_jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_ConnectionWindow_jf.add(m_Background_jl);
        
        m_Background_jl.setLayout(new BorderLayout());
        m_Background_jl.setForeground(Color.WHITE);
        m_Message_L.setForeground(Color.WHITE);
        int width = 150;
        int height = 50;
        
        m_Background_jl.setMaximumSize(new Dimension(width,height));
        m_Background_jl.setPreferredSize(new Dimension(width,height-5));

		m_ConnectionWindow_jf.setUndecorated(true);
		m_ConnectionWindow_jf.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		m_ConnectionWindow_jf.setLocation(m_ScreenWidth/2 - width/2,m_ScreenHeight/2 - height/2);
        m_Message_L.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_Content_L.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	public void addElements()
    {
        m_Background_jl.add(m_Content_L, BorderLayout.NORTH);
        m_Background_jl.add(m_Message_L, BorderLayout.CENTER);
        
		m_ConnectionWindow_jf.add(m_Background_jl);
        
		m_ConnectionWindow_jf.pack();
        
        m_ConnectionWindow_jf.setVisible(true);
    }

	public void updateMessage(String message)
    {
        m_Message_L.setText(message);
    }
    
    public void dispose()
    {
        m_ConnectionWindow_jf.dispose();
    }
}