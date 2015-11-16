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

  public GameClient_() 
  {
    try {

        // Create a socket to connect to the server
        System.out.println("Connecting...");
        m_Socket = new Socket("localhost", 8000);
        System.out.println("Connected...");
        // Create an input stream to receive Object from the server
        fromServer = new ObjectInputStream( m_Socket.getInputStream() );
    
        // Create an output stream to send Object to the server
        toServer =  new ObjectOutputStream( m_Socket.getOutputStream() );
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
