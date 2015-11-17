import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.ImageIcon.*;
import javax.swing.JFrame;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class WaitForConnection extends JFrame{
	private JFrame m_OldWindow_jf,m_ConnectionWindow_jf;
	private JLabel m_Background_jl;
	private JButton m_Cancel_jb;
	private LoadAssets m_Assets;
	private int m_ScreenWidth,m_ScreenHeight;

	public WaitForConnection(JFrame oldWindow)
    {
		m_OldWindow_jf=oldWindow;
		m_Assets= Main.m_Assets;

        createComponents();
		buildCompenents();
		addElements();

	}

	public void createComponents(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		m_ScreenWidth = gd.getDisplayMode().getWidth();
		m_ScreenHeight = gd.getDisplayMode().getHeight();

		m_ConnectionWindow_jf=new JFrame("Connecting");

		m_Background_jl=new JLabel(m_Assets.getImage("ConnectingToServer"));

		m_Cancel_jb=new JButton("Cancel");
	}

	public void buildCompenents()
    {
		m_ConnectionWindow_jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_ConnectionWindow_jf.add(m_Background_jl);
        
		m_Background_jl.setLayout(new BoxLayout(m_Background_jl,BoxLayout.X_AXIS));
        m_Background_jl.setPreferredSize(new Dimension(400, 400));

		m_ConnectionWindow_jf.setUndecorated(true);
		m_ConnectionWindow_jf.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		m_ConnectionWindow_jf.setSize(new Dimension(m_ScreenWidth/4,m_ScreenHeight/4));
		m_ConnectionWindow_jf.setLocation(m_ScreenWidth/4,m_ScreenHeight/4);
		m_ConnectionWindow_jf.setAlwaysOnTop(true);
	
		m_Cancel_jb.setMargin(new Insets(0,0,0,0));
		m_Cancel_jb.setActionCommand("Cancel");
		m_Cancel_jb.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	public void addElements(){
		m_Background_jl.add(m_Cancel_jb);

		m_ConnectionWindow_jf.add(m_Background_jl);
        
		m_ConnectionWindow_jf.pack();
        
		m_ConnectionWindow_jf.setVisible(true);
	}

	public void attachListeners(){}

}