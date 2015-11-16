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
}
