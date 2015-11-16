import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GameServer_ extends JFrame {

  // Text area for displaying contents
  private JTextArea jta = new JTextArea();

  public static void main(String[] args) {
    new GameServer_();
  }

  public GameServer_() {

    // Place text area on the frame
    setLayout(new BorderLayout());
    add(new JScrollPane(jta), BorderLayout.CENTER);
    setTitle("GameServer_");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!
    int turn = 1;

    try 
    {

        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        jta.append("GameServer_ started at " + new Date() + '\n');
    
        // Listen for a connection request
        jta.append("\nSearching for Players... \n");
        Socket socket1 = serverSocket.accept();
        jta.append("\nConnected Player One: \n");
        Socket socket2 = serverSocket.accept();
        jta.append("\nConnected Player Two: \n");
    
        // Create Object input and output streams
        ObjectInputStream fromPlayerOne = new ObjectInputStream(socket1.getInputStream());
        ObjectOutputStream toPlayerOne = new ObjectOutputStream(socket1.getOutputStream());
        
        ObjectInputStream fromPlayerTwo = new ObjectInputStream(socket2.getInputStream());
        ObjectOutputStream toPlayerTwo = new ObjectOutputStream(socket2.getOutputStream());
    
        while (true) 
        {
    
            // Receive radius from the client
            Board playerOneBoard = (Board) fromPlayerOne.readObject();
            jta.append("\nBoard Receive from Player One: \n");
            Board playerTwoBoard = (Board) fromPlayerTwo.readObject();
            jta.append("\nBoard Receive from Player Two: \n");
            
            toPlayerOne.writeObject(playerTwoBoard);
            toPlayerTwo.writeObject(playerOneBoard);
            
            while(true)
            {
                toPlayerOne.writeObject(turn);// your player one
                toPlayerTwo.writeObject(turn);// your player two
                if(turn == 1)
                {
                    for(int i = 0; i < 5; i++)
                    {
                        Location hitLoc = (Location) fromPlayerOne.readObject();
                        jta.append("\nLocation Receive from Player One: " + hitLoc + '\n');
                        toPlayerTwo.writeObject(hitLoc);
                    }
                    turn = 2;
                }else
                {
                    for(int i = 0; i < 5; i++)
                    {
                        Location hitLoc = (Location) fromPlayerTwo.readObject();
                        jta.append("\nLocation Receive from Player Two: " + hitLoc + '\n');
                        toPlayerOne.writeObject(hitLoc);
                    }
                    turn = 1;
                }
            }
        }
        // Compute area

        // Send area back to the client
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
    catch(ClassNotFoundException e)
    {
        System.err.println(e);
    }
  }
}
