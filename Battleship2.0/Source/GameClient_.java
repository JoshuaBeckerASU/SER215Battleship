import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameClient_
{
  // IO streams
  private Socket m_Socket;
  private ObjectOutputStream toServer;
  private ObjectInputStream fromServer;
  private Location m_Location;
  
  /*public static void main(String args[])
  {
      new GameClient_();
  }*/

  public GameClient_() 
  {
    try {

        // Create a socket to connect to the server
        System.out.println("Connecting...");
        m_Socket = new Socket("localhost", 8000);
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
  public ObjectInputStream getInputStream()
  {
      return fromServer;
  }
  public ObjectOutputStream getOutputStream()
  {
      return toServer;
  }

  private class ChatService implements Runnable{
        private ObjectInputStream fromServer;
        private ObjectOutputStream toServer;
        
        public ChatService(ObjectInputStream ois,ObjectOutputStream oos){
            fromServer=ois;
            toServer=oos;

        }

        public void run(){
            MessageListener chatListener=new MessageListener(fromServer);
            

            Thread listener=new Thread(chatListener);
            
            while(listener.isAlive()){
                if(!(listener.isAlive())){
                    String message=chatListener.getMessage();
                    toServer.writeObject(new String(message));
                    listener.start();
                }
                
        }
        public ObjectInputStream getChatInputStream(){
          return fromServer;
        }
        
        public ObjectOutputStream getChatOutputStream(){
          return toServer;
        }

        private class MessageListener implements Runnable{

            private ObjectInputStream fromServer;
            private String message;

            public MessageListener(ObjectInputStream ois){
                fromServer=ois;
                message="";
            }

            public getMessage(){
                return message;
            }

            public void run(){
                message=(String)fromServer.readObject();
            }
        }
    }
}
