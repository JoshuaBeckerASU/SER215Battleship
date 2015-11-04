/**
*@author Alec Shinn
*@version 1.0
*@date10/30/15
*/
/*At this point all I have is a Server that waits for a connection. The main is just for easier testing it will be removed later*/
import java.net.*;
import java.io.*;
import java.util.*;

public class GameServer{
	//creates the server socket
	private ServerSocket serverSocket;
	
	//creates the socket that will communicate with the client 
	private Socket socket,socket1;
	
	//inputstream from the client
	private ObjectInputStream inFromClient,inFromClient1;

	//output stream to the client
	private ObjectOutputStream outToClient,outToClient1;

	//current game object
	//private Game currentGame;

	//load assets object for game constructor
	//private LoadAssets gameAssets;

	//giving it an initial value for now. Same for difficulty
	private int numberOfPlayers=2,difficulty=2;

	//the location of a players move will be sent here
	private Location currentMove,currentMove1;

	//strings that will be used to differentiate between clients
	String pIp,pIp1;
	
	public static void main(String[] args){
		new GameServer();
	}

	public GameServer(){

		try{
			//attach serversocket to a port
			serverSocket=new ServerSocket(8000);

			//connect on socket
			socket=serverSocket.accept();

			//get clients InetAddress
			InetAddress clientAddress=socket.getInetAddress();

			//converts InetAddress object to string for storage
			pIp=clientAddress.toString();

			System.out.println("Connection established with "+pIp);

			//wait for connection
			socket1=serverSocket.accept();

			//get clients InetAddress
			InetAddress clientAddress1=socket1.getInetAddress();

			//converts InetAddress object to string for storage
			pIp1=clientAddress.toString();

			System.out.println("Connection established with "+pIp1);

			//instantiate datastreams
			inFromClient=new ObjectInputStream(socket.getInputStream());
			outToClient=new ObjectOutputStream(socket.getOutputStream());

			//instantiate datastreams
			inFromClient1=new ObjectInputStream(socket1.getInputStream());
			outToClient1=new ObjectOutputStream(socket1.getOutputStream());


			/*//initialize loadAssets
			gameAssets=new LoadAssets();

			//initialize game object
			currentGame=new Game(numberOfPlayers,difficulty,gameAssets);*/

			while(true){
				currentMove=(Location)inFromClient.readObject();

				outToClient1.writeObject(currentMove);

				currentMove=(Location)inFromClient1.readObject();

				outToClient.writeObject(currentMove);
			
			}

		}catch(IOException io){
			System.err.println(io);
		}
		catch(ClassNotFoundException c){
			System.err.println(c);
		}
	}
	
}