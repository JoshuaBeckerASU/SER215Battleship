/******************************
* @file: GameClient_.java
* @author: Joshua Becker
* @date: 11/16/15
* @description:
* 
* @index
* [
*     m_: for member variables
*     g_: for global variables
* ]
*******************************/
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
    private int m_Port;
    
    public GameClient_() 
    {
        m_IP = "10.143.109.147";
        m_Port = 8000;
    }
    public GameClient_(String ip, int port) 
    {
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
        try 
        {
            System.out.println("Connecting...");
            try
            {
                m_Socket = new Socket(m_IP, m_Port);
                
                System.out.println("Connecting to ChatService...");
                m_ChatSocket = new Socket(m_IP, m_Port);
                System.out.println("\nConnected to ChatService...");
            }catch(java.net.ConnectException e)
            {
                System.out.println("Not able to cennect to server");
                System.err.println(e);
                System.exit(1);
            }
                
            System.out.println("Connected...");
            
            System.out.println("Getting OutputStream Stream From server...");
            toServer =  new ObjectOutputStream(m_Socket.getOutputStream());
            toChatServer = new ObjectOutputStream(m_ChatSocket.getOutputStream());
            toChatServer.flush();
            
            System.out.println("Getting Input Stream From server...");
            fromServer = new ObjectInputStream(m_Socket.getInputStream());
            fromChatServer = new ObjectInputStream(m_ChatSocket.getInputStream());
            
            m_ChatService = new ChatService();
            m_ChatServiceThread = new Thread(m_ChatService);
            m_ChatServiceThread.start();
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
