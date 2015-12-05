/*************************************
* @file: GameClient_.java
* @author: Joshua Becker
* @date: 11/16/15
* @description:
*  
* @contributors: Alec Shinn
*  
* @index
* [
*     m_: for member variables
*     g_: for global variables
*     s_: for static variables
* ]
* 
***************************************/
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameClient_ implements Runnable
{
    
    private Socket m_Socket;
    private Socket m_ChatSocket;
    
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private ObjectInputStream fromChatServer;
    private ObjectOutputStream toChatServer;
    private GameWindow m_GameWindow;//for chat
    private String m_IP;
    private Thread m_ChatServiceThread;
    private ChatService m_ChatService;
    private JFrame m_OldWindow;
    private MultiPlayerMenuWindow m_MultiPlayerMenu;
    private int m_Port;
    
    public GameClient_(JFrame oldWindow, MultiPlayerMenuWindow multiPlayerMenu) 
    {
        m_MultiPlayerMenu = multiPlayerMenu;
        m_OldWindow = oldWindow;
        try
        {
            m_IP = InetAddress.getLocalHost().getHostAddress();
        }catch(UnknownHostException e)
        {
            System.err.println(e);
        }
        m_Port = 8000;
    }
    public GameClient_(String ip, int port, JFrame oldWindow, MultiPlayerMenuWindow multiPlayerMenu) 
    {
        m_MultiPlayerMenu = multiPlayerMenu;
        m_OldWindow = oldWindow;
        m_IP = ip;
        m_Port = port;
    }
    public ObjectInputStream getInputStream()
    {
        return fromServer;
    }
    public ObjectOutputStream getOutputStream()
    {
        return toServer;
    }
    public void setGameWindow(GameWindow gameWindow)
    {
        m_GameWindow = gameWindow;
    }
    
    public void sendMessage(String message)
    {
        try
        {
            System.out.println("Sending a message..." + message);
            toChatServer.writeObject(new String (message));
            toChatServer.flush();
        }catch(IOException e)
        {
            System.out.println("IOException in sendMessage");
            System.err.println(e);
            System.exit(1);
        }  
    }
    
    public void run()
    {
        WaitForConnection wfc = new WaitForConnection("Waiting For Connection");
        m_OldWindow.setEnabled(false);
        try 
        {
            m_Socket = new Socket(m_IP, m_Port);
            
            //System.out.println("Connecting to ChatService...");
            m_ChatSocket = new Socket(m_IP, m_Port);
            //System.out.println("\nConnected to ChatService...");
 
            wfc.updateMessage("Waiting For Other Players");
            
            //System.out.println("Getting OutputStream Stream From server...");
            toServer =  new ObjectOutputStream(m_Socket.getOutputStream());
            toChatServer = new ObjectOutputStream(m_ChatSocket.getOutputStream());
            toChatServer.flush();
            
            //System.out.println("Getting Input Stream From server...");
            fromServer = new ObjectInputStream(m_Socket.getInputStream());
            fromChatServer = new ObjectInputStream(m_ChatSocket.getInputStream());
            
            m_ChatService = new ChatService();
            m_ChatServiceThread = new Thread(m_ChatService);
            m_ChatServiceThread.start();
           
            m_MultiPlayerMenu.startGame();
            wfc.dispose();
            m_OldWindow.dispose();
        }catch(java.net.ConnectException e)
        {
            wfc.dispose();
            MenuWindow menu = new MenuWindow(new JFrame(""));
            JOptionPane.showMessageDialog(menu.getMainFrame(),"Error Connection refused");
            m_OldWindow.setEnabled(true);
        }
        catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    private class ChatService implements Runnable
    {
        private String m_Message;
        public ChatService()
        {
        }
        
        public void run()
        {
            while(true)
            {
                try
                {
                    m_Message=(String)fromChatServer.readObject();
                    System.out.println("GOT A MESSAGE FOR YEAH");
                }catch(IOException e)
                {
                    System.out.println("IOException in MessageListener");
                    System.err.println(e);
                    System.exit(1);
                }catch(ClassNotFoundException e)
                {
                    System.out.println("IOException in MessageListener");
                    System.err.println(e);
                    System.exit(1);
                }
                m_GameWindow.updateChatConsole(m_Message);
            }
        }
    }
}
