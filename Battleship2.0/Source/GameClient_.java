/**
*@author Alec Shinn
*@version 1.0
*@date 10/30/15
*/

import java.net.*;
import java.io.*;
import java.util.*;



public class GameClient_ implements Runnable{
	
	//iostreams
	private static ObjectInputStream fromServer;
	private static ObjectOutputStream toServer;
    
    private Game m_Game;
    private static int m_HitCount;
	

	private Socket socket;

	//this is the InetAddress address that represents my computer
	private String localHost="192.168.0.7";

	//The port that the server is on
	private int port=8000;

	//which player the client is
	private int player;

	//player constants
	private final int PLAYER1=1,PLAYER2=2;
	
	//is it the clients turn
	boolean myTurn;

	boolean waiting=true;

	private Location currentMove;

	private Scanner input=new Scanner(System.in);

	public GameClient_(Game game)
    {
        m_Game = game;
        m_HitCount = 0;
	}

	public void run()
    {
        connectToServer();
		try{
			while(true)
            {
				toServer.writeObject(m_Game.getCurrentPlayer().getBoardObject());
                m_Game.getOpponentPlayer().setBoardObject(((Board)fromServer.readObject()));
                
                //starting turns
                while((Integer)fromServer.readObject() != 0)//Game Over
                {
                    if((Integer)fromServer.readObject() == 1)//current player
                    {
                        while(m_HitCount < 5)
                        {
                            //useing sendTarget within Game class
                        }
                        m_HitCount = 0;
                    }else
                    {
                        Location tmp = (Location) fromServer.readObject();
                        m_Game.getCurrentPlayer().incNumOfSelTargets();
                        m_Game.playerSelectedTarget(tmp.x(),tmp.y());
                        
                        tmp = (Location) fromServer.readObject();
                        m_Game.getCurrentPlayer().incNumOfSelTargets();
                        m_Game.playerSelectedTarget(tmp.x(),tmp.y());
                        
                        tmp = (Location) fromServer.readObject();
                        m_Game.getCurrentPlayer().incNumOfSelTargets();
                        m_Game.playerSelectedTarget(tmp.x(),tmp.y());
                        
                        tmp = (Location) fromServer.readObject();
                        m_Game.getCurrentPlayer().incNumOfSelTargets();
                        m_Game.playerSelectedTarget(tmp.x(),tmp.y());
                        
                        tmp = (Location) fromServer.readObject();
                        m_Game.getCurrentPlayer().incNumOfSelTargets();
                        m_Game.playerSelectedTarget(tmp.x(),tmp.y());
                        
                        m_Game.nextTurn();
                    }
                }

			}
		}catch(IOException io){
			System.err.println(io);
		}catch(ClassNotFoundException c){
			System.err.println(c);
		}
	}

	public void connectToServer(){
		try{
			
			//looks for the server with InetAddress and port.
			socket=new Socket(localHost,port);
			
			//connect iostreams

			//get player number from server
			player=new DataInputStream(socket.getInputStream()).readInt();

			System.out.println(player);
			toServer=new ObjectOutputStream(socket.getOutputStream());
			
			fromServer=new ObjectInputStream(socket.getInputStream());
			
			System.out.println("Connection established\n");

			
		}catch(IOException io){
			System.err.println(io);
		}

		Thread thread=new Thread(this);
		
		thread.start();
	}

	public void waitForTurn() throws InterruptedException{
		while(waiting){
			Thread.sleep(100);
		}

		waiting=true;
	}
    public static void sendTarget(Location target)
    {
        try
        {
            toServer.writeObject(target);
            m_HitCount++;
        }catch(IOException e)
        {
            System.out.println("IOException in sendTargets");
        }
    }
}