/**
*@author Alec Shinn
*@version 1.0
*@date 10/30/15
*/

import java.net.*;
import java.io.*;
import java.util.*;



public class GameClient{
	
	//iostreams
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private BufferedReader reader;

	private Socket socket;

	//this is the InetAddress address that represents my computer
	private String localHost="10.143.235.91";

	//The port that the server is on
	private int port=8000;

	private Location currentMove;

	private Scanner input=new Scanner(System.in);

	public GameClient(){


		try{
			
			//looks for the server with InetAddress and port.
			socket=new Socket(localHost,port);
			System.out.println("Here");
			//connect iostreams
			fromServer=new ObjectInputStream(socket.getInputStream());
			System.out.println("Heres");
			toServer=new ObjectOutputStream(socket.getOutputStream());

			System.out.println("Connection established\n");

			while(true){
				System.out.print("Enter an x coordinate: ");
				int x=input.nextInt();

				System.out.print("\nEnter a y coordinate: ");
				int y=input.nextInt();

				currentMove=new Location(x,y);

				toServer.writeObject(currentMove);
				
				currentMove=(Location)fromServer.readObject();
				
				System.out.println(currentMove.getMessage());
			}
		}catch(IOException io){
			System.err.println(io);
		}catch(ClassNotFoundException c){
			System.err.println(c);
		}


	}

	//again this main is just for testing purposes. will be removed eventually
	public static void main(String[] args){
		new GameClient();
	}



}