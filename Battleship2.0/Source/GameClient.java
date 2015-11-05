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
	private String localHost="10.143.235.91";

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


		connectToServer();

		System.out.println("connected to server now run");

		run();


	}

	public void run(){
		try{
			while(true){
				if(player==PLAYER1){

					String message=input.nextLine();

					sendMessage(message);

					message=receiveMessage();
				
				}
				else{
					String message=receiveMessage();

					message=input.nextLine();

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
			System.out.println("Here");
			//connect iostreams

			//get player number from server
			player=new DataInputStream(socket.getInputStream()).readInt();

			System.out.println(player);
			fromServer=new ObjectInputStream(socket.getInputStream());
			System.out.println("p1 is connected");
			
			toServer=new ObjectOutputStream(socket.getOutputStream());
			System.out.println("p1 os connected");
			System.out.println("Connection established\n");

			
		}catch(IOException io){
			System.err.println(io);
		}
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