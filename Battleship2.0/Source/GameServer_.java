/******************************
* @file: GameServer_.java
* @author: Joshua Becker, NOTE: I used some of the code from the
*                               Server example posted on blackboard
* @date:
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
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GameServer_ extends JFrame
{
    private ObjectOutputStream toChatPlayerOne;
    private ObjectOutputStream toChatPlayerTwo;
    
    private Socket m_ChatSocketOne;
    private Socket m_ChatSocketTwo;
    // Text area for displaying contents
    private JTextArea ServerConsole = new JTextArea();
    
    public static void main(String[] args) {
        new GameServer_();
    }
    
    public GameServer_() 
    {
    
        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(ServerConsole), BorderLayout.CENTER);
        setTitle("GameServer_");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!
        boolean turn = true;
    
        try 
        {
    
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            ServerConsole.append("GameServer_ started at " + new Date() + '\n');
        
            // Listen for a connection request
            ServerConsole.append("\nSearching for Players... \n");
            Socket socket1 = serverSocket.accept();
            
            ServerConsole.append("\nConnecting to chat service\n");
            m_ChatSocketOne = serverSocket.accept();
            
            ServerConsole.append("\nConnected Player One: \n");
            
            Socket socket2 = serverSocket.accept();
            
            ServerConsole.append("\nConnecting to chat service\n");
            m_ChatSocketTwo = serverSocket.accept();
            
            ServerConsole.append("\nConnected Player Two: \n");
        
            // Create Object input and output streams
            ServerConsole.append("\nCreate InputStream Player One: \n");
            ObjectInputStream fromPlayerOne = new ObjectInputStream(socket1.getInputStream());
            ObjectInputStream fromChatPlayerOne = new ObjectInputStream(m_ChatSocketOne.getInputStream());
            
            ServerConsole.append("\nCreate OutputStream Player One: \n");
            ObjectOutputStream toPlayerOne = new ObjectOutputStream(socket1.getOutputStream());
            toChatPlayerOne = new ObjectOutputStream(m_ChatSocketOne.getOutputStream());
            
            ServerConsole.append("\nCreate InputStream Player Two: \n");
            ObjectInputStream fromPlayerTwo = new ObjectInputStream(socket2.getInputStream());
            ObjectInputStream fromChatPlayerTwo = new ObjectInputStream(m_ChatSocketTwo.getInputStream());
            
            ServerConsole.append("\nCreate OutputStream Player Two: \n");
            ObjectOutputStream toPlayerTwo = new ObjectOutputStream(socket2.getOutputStream());
            toChatPlayerTwo = new ObjectOutputStream(m_ChatSocketTwo.getOutputStream());

            ChatService chatService = new ChatService(fromChatPlayerOne, fromChatPlayerTwo);
            Thread chat = new Thread(chatService);
            chat.start();
            
            while (true) 
            {
                getBoardFromPlayer gtBrdOne = new getBoardFromPlayer(fromPlayerOne);
                getBoardFromPlayer gtBrdTwo = new getBoardFromPlayer(fromPlayerTwo);
                
                Thread one = new Thread(gtBrdOne);
                Thread two = new Thread(gtBrdTwo);
                // Receive radius from the client
                ServerConsole.append("\nWaiting For Boards...: \n");
                one.start();
                two.start();
                while(one.isAlive() || two.isAlive())
                {
                    
                }
                ServerConsole.append("\nGot Boards...: \n");
                
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
                            ServerConsole.append("\nLocation Receive from Player One: " + hitLoc + '\n');
                            toPlayerTwo.writeObject(hitLoc);
                            ServerConsole.append("\nLocation sent too Player One: " + hitLoc + '\n');
                        }
                        turn = false;
                    }else
                    {
                        for(int i = 0; i < 5; i++)
                        {
                            Location hitLoc = (Location) fromPlayerTwo.readObject();
                            ServerConsole.append("\nLocation Receive from Player Two: " + hitLoc + '\n');
                            toPlayerOne.writeObject(hitLoc);
                            ServerConsole.append("\nLocation sent too Player One: " + hitLoc + '\n');
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
                    ServerConsole.append("\n got Location: " + m_Board[i]);
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

    private class ChatService implements Runnable
    {
        private String m_Message;
        private MessageListener playerOne_ML;
        private MessageListener playerTwo_ML;
        private ObjectInputStream m_FromPlayerOneChat;
        private ObjectInputStream m_FromPlayerTwoChat;
        public ChatService(ObjectInputStream fromChat, ObjectInputStream fromChat2)
        {
            m_FromPlayerOneChat = fromChat;
            m_FromPlayerTwoChat = fromChat2;
        }

        public void run()
        {
            playerOne_ML=new MessageListener(m_FromPlayerOneChat);
            playerTwo_ML=new MessageListener(m_FromPlayerTwoChat);

            Thread listener1=new Thread(playerOne_ML);
            Thread listener2=new Thread(playerTwo_ML);
            
            listener1.start();
            listener2.start();
            try
            {
                while(true)
                {
                    if(!(listener1.isAlive()))
                    {
                        m_Message =playerOne_ML.getMessage();
                        ServerConsole.append("\nPlayer One Has a Message = " + m_Message + '\n');
                        toChatPlayerTwo.writeObject(new String(m_Message));
                        toChatPlayerOne.writeObject(new String(m_Message));
                        listener1=new Thread(playerOne_ML);
                        listener1.start();
                    }
                    if(!(listener2.isAlive()))
                    {
                        String m_Message=playerTwo_ML.getMessage();
                        ServerConsole.append("\nPlayer Two Has a Message = " + m_Message + '\n');
                        toChatPlayerTwo.writeObject(new String(m_Message));
                        toChatPlayerOne.writeObject(new String(m_Message));
                        listener2=new Thread(playerTwo_ML);
                        listener2.start();
                    }
                }
            }catch(IOException e)
            {
                System.out.println("IOException in run of ChatService");
                System.err.println(e);
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

            public void run()
            {
                try
                {
                    System.out.println("WAITING FOR MESSAGE");
                    message=(String)fromClient.readObject();
                    System.out.println("GOT A MESSAGE");
                }catch(IOException e)
                {
                    System.out.println("IOException in run of MessageListener");
                    System.err.println(e);
                }catch(ClassNotFoundException e)
                {
                    System.out.println("ClassNotFoundException in run of MessageListener");
                    System.err.println(e);
                }
                    
                }
        }
    }
}
