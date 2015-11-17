import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GameServer_ extends JFrame
{

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
        boolean turn = true;
    
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
            jta.append("\nCreate InputStream Player One: \n");
            ObjectInputStream fromPlayerOne = new ObjectInputStream(socket1.getInputStream());
            
            jta.append("\nCreate OutputStream Player One: \n");
            ObjectOutputStream toPlayerOne = new ObjectOutputStream(socket1.getOutputStream());
            
            jta.append("\nCreate InputStream Player Two: \n");
            ObjectInputStream fromPlayerTwo = new ObjectInputStream(socket2.getInputStream());
            
            jta.append("\nCreate OutputStream Player Two: \n");
            ObjectOutputStream toPlayerTwo = new ObjectOutputStream(socket2.getOutputStream());
            
            
            while (true) 
            {
                getBoardFromPlayer gtBrdOne = new getBoardFromPlayer(fromPlayerOne);
                getBoardFromPlayer gtBrdTwo = new getBoardFromPlayer(fromPlayerTwo);
                
                Thread one = new Thread(gtBrdOne);
                Thread two = new Thread(gtBrdTwo);
                // Receive radius from the client
                jta.append("\nWaiting For Boards...: \n");
                one.start();
                two.start();
                while(one.isAlive() || two.isAlive())
                {
                    
                }
                jta.append("\nGot Boards...: \n");
                
                for(int i = 0; i < 5; i++)
                {
                    toPlayerOne.writeObject(gtBrdTwo.getBoard()[i]);
                    toPlayerTwo.writeObject(gtBrdOne.getBoard()[i]);
                }

                
                while(true)
                {
                    toPlayerOne.writeObject(turn);// your player one
                    toPlayerTwo.writeObject(!turn);// your player two
                    if(turn)
                    {
                        for(int i = 0; i < 5; i++)
                        {
                            Location hitLoc = (Location) fromPlayerOne.readObject();
                            jta.append("\nLocation Receive from Player One: " + hitLoc + '\n');
                            toPlayerTwo.writeObject(hitLoc);
                        }
                        turn = false;
                    }else
                    {
                        for(int i = 0; i < 5; i++)
                        {
                            Location hitLoc = (Location) fromPlayerTwo.readObject();
                            jta.append("\nLocation Receive from Player Two: " + hitLoc + '\n');
                            toPlayerOne.writeObject(hitLoc);
                        }
                        turn = true;
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
    
    private class getBoardFromPlayer implements Runnable
    {
        private Location m_Board[];
        private ObjectInputStream m_FromPlayer;
        getBoardFromPlayer(ObjectInputStream from)
        {
            m_FromPlayer = from;
            m_Board = new Location[5];
        }
        public void run()
        {
            try
            {
                for(int i = 0; i < 5; i++)
                {
                    m_Board[i] = (Location) m_FromPlayer.readObject();
                    jta.append("\n got Location: " + m_Board[i]);
                }
                    
            }catch(IOException e)
            {
                System.out.println("IOException in run of GetBoard");
                System.err.println(e);
            }catch(ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException in run of GetBoard");
                System.err.println(e);
            }
                
        }
        public Location[] getBoard()
        {
            return m_Board;
        }
    }
    /*
    private class ChatService implements Runnable{
        private ObjectInputStream fromClient1;
        private ObjectOutputStream toClient1;
        private ObjectInputStream fromClient2;
        private ObjectOutputStream toClient2;

        public ChatService(ObjectInputStream ois1,ObjectOutputStream oos1,ObjectInputStream ois2,ObjectOutputStream oos2){
            fromClient1=ois1;
            toClient1=oos1;

            fromClient2=ois2;
            toClient2=oos2;
        }

        public void run(){
            MessageListener clientListener1=new MessageListener(fromClient1);
            MessageListener clientListener2=new MessageListener(fromClient2);

            Thread listener1=new Thread(clientListener1);
            Thread listener2=new Thread(clientListener2);

            while(listener1.isAlive() || listener2.isAlive()){
                if(!(listener1.isAlive())){
                    String message=clientListener1.getMessage();
                    toPlayerTwo.writeObject(new String(message));
                    listener1.start();
                }
                else if(!(listener2.isAlive())){
                    String message=clientListener2.getMessage();
                    toPlayerOne.writeObject(new String(message));
                    listener2.start();
                }
        }

        }

        private class MessageListener implements Runnable{

            private ObjectInputStream fromClient;
            private String message;

            public MessageListener(ObjectInputStream ois){
                fromClient=ois;
                message="";
            }

            public String getMessage(){
                return message;
            }

            public void run(){
                message=(String)fromClient.readObject();
            }
        }
    }*/
}
