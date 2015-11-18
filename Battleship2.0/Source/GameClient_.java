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
    // IO streams
    private Socket m_Socket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private Location m_Location;
    
    public GameClient_() 
    {

    }
    public ObjectInputStream getInputStream()
    {
        return fromServer;
    }
    public ObjectOutputStream getOutputStream()
    {
        return toServer;
    }
    
    private class ChatService implements Runnable
    {
        private ObjectInputStream fromServer;
        private ObjectOutputStream toServer;
        
        public ChatService(ObjectInputStream ois,ObjectOutputStream oos){
            fromServer=ois;
            toServer=oos;

        }

        public void run()
        {
            MessageListener chatListener=new MessageListener(fromServer);
            

            Thread listener=new Thread(chatListener);
            try
            {
                while(listener.isAlive())
                {
                    if(!(listener.isAlive()))
                    {
                        String message=chatListener.getMessage();
                        toServer.writeObject(new String(message));
                        listener.start();
                    }
                    
                }
            }catch(IOException e)
            {
                System.out.println("IOException in ChatService");
                System.err.println(e);
                System.exit(1);
            }
        }
 
        public ObjectInputStream getChatInputStream()
        {
          return fromServer;
        }
        
        public ObjectOutputStream getChatOutputStream(){
          return toServer;
        }

        private class MessageListener implements Runnable
        {

            private ObjectInputStream fromServer;
            private String message;

            public MessageListener(ObjectInputStream ois){
                fromServer=ois;
                message="";
            }

            public String getMessage(){
                return message;
            }

            public void run()
            {
                try
                {
                    message=(String)fromServer.readObject();
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
            }
        }
    }
    public void run()
    {
        try 
        {
    
            // Create a socket to connect to the server
            System.out.println("Connecting...");
            try
            {
                m_Socket = new Socket("10.142.111.41", 8000);
            }catch(java.net.ConnectException e)
            {
                System.out.println("Not able to cennect to server");
                System.err.println(e);
                System.exit(1);
            }
                
            System.out.println("Connected...");
            // Create an input stream to receive Object from the server
    
        
            // Create an output stream to send Object to the server
            System.out.println("Getting OutputStream Stream From server...");
            toServer =  new ObjectOutputStream( m_Socket.getOutputStream() );
            
            System.out.println("Getting Input Stream From server...");
            fromServer = new ObjectInputStream( m_Socket.getInputStream() );
            
            
        }
        catch (IOException ex) {
            System.err.print(ex);
        }
    }
}
