/**
*@author Alec Shinn
*@version 1.0
*@date 10/30/15
*/

import java.net.*;
import java.io.*;
import java.util.*;



public class GameClient implements Runnable{
	
	//iostreams
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	

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

	public GameClient(){

		System.out.println("Conecting to Server...");
		connectToServer();
		
	}

	public void run(){
		try{
			while(true){
				if(player==PLAYER1){
					System.out.print("\n\nPlayer1>");
					String message=input.nextLine();

					sendMessage(message);

					message=receiveMessage();
					System.out.println("\n\nPLAYER2>"+message);
				
				}
				else{

					message=receiveMessage();
					System.out.println("\n\nPLAYER1>"+message);

					System.out.print("\n\nPlayer2>");

					String message=input.nextLine();

					sendMessage(message);
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

	public String receiveMessage() throws IOException,ClassNotFoundException{
		return (String)fromServer.readObject();
	}

	public void sendMessage(String message)throws IOException{
		toServer.writeObject(message);
	}

	//again this main is just for testing purposes. will be removed eventually
	public static void main(String[] args){
		new GameClient();
	
		
	}



}