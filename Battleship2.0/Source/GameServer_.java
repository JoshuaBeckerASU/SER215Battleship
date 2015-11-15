/**
*@author Alec Shinn
*@version 1.0
*@date10/30/15
*/
/*At this point all I have is a Server that waits for a connection. The main is just for easier testing it will be removed later*/
import java.net.*;
import java.io.*;
import java.util.*;

//dont need threading since server will also act as a client


public class GameServer_ implements Runnable{
	//creates the server socket
	private ServerSocket serverSocket;

	private int port=8000,numOfSessions=1;
	
	//creates the socket that will communicate with the client 
	private Socket socket1,socket2;

	//load assets object for game constructor
	//private LoadAssets gameAssets;

	public GameServer_(String ownerName){


		
	}
    public void run()
    {
		try{
			//attach serversocket to a port
			serverSocket=new ServerSocket(port);

			//socket1=serverSocket.accept();

			//dont need while loop anymore since no dedicated server 
			while(true){

				
				//connect on socket
				socket1=serverSocket.accept();

				//tell the player what player they are 1 or 2
				new DataOutputStream(socket1.getOutputStream()).writeInt(1);

				//get clients InetAddress
				InetAddress clientAddress1=socket1.getInetAddress();


				System.out.println("Connection established with "+clientAddress1);

				//wait for connection
				socket2=serverSocket.accept();

				new DataOutputStream(socket2.getOutputStream()).writeInt(2);

				//get clients InetAddress
				InetAddress clientAddress2=socket2.getInetAddress();

				System.out.println("Connection established with "+clientAddress2);

				ChatSession chatSession=new ChatSession(socket1,socket2);

				numOfSessions++;

				//this is just for testing chat 
				new Thread(chatSession).start();

			
			}

		}catch(IOException io){
			System.err.println(io);
		}
    }
	class ChatSession implements Runnable{

		private Socket s_player1,s_player2;
		
		private ObjectInputStream is_fromPlayer1,is_fromPlayer2;
		private ObjectOutputStream os_toPlayer1,os_toPlayer2;

		private boolean continueChat=true;

		private InetAddress ip_player1,ip_player2;

		private String message;

		public ChatSession(Socket socket1,Socket socket2){

			//assign sockets to players
			s_player1=socket1;
			s_player2=socket2;

			try{
				
				//associate iostreams
				is_fromPlayer1=new ObjectInputStream(socket1.getInputStream());					
				os_toPlayer1=new ObjectOutputStream(socket1.getOutputStream());
					
				is_fromPlayer2=new ObjectInputStream(socket2.getInputStream());
				os_toPlayer2=new ObjectOutputStream(socket2.getOutputStream());
					
				System.out.println("All data streams connected");
				
				//get IP's
				ip_player1=s_player1.getInetAddress();
				ip_player2=s_player2.getInetAddress();
			
			}catch(IOException io){
				System.err.println(io);
			}

			message="";

		}

		public void run(){
			
			try{
				while(true){
					Board tmp = (Board) is_fromPlayer1.readObject();
                    Board tmp2 = (Board) is_fromPlayer2.readObject();
                    
                    os_toPlayer1.writeObject(tmp2);
                    os_toPlayer2.writeObject(tmp);
                    
                    os_toPlayer1.writeObject(1);
                    os_toPlayer2.writeObject(1);
                    
                    os_toPlayer1.writeObject(1);//you start
                    os_toPlayer2.writeObject(2);//you wait
                    
                    startTurns();
				}
			}catch(IOException io){
				System.err.println(io);
			}
			catch(ClassNotFoundException c){
				System.err.println(c);
			}
		}
        public void startTurns()
        {
            Integer turn = 1;
            Location tmp;
            try
            {
                while(true)
                {
                    if(turn == 1)
                    {
                        for(int i =0; i < 5; i++)
                        {
                            tmp = (Location) is_fromPlayer1.readObject();
                            os_toPlayer2.writeObject(tmp);
                        }
                        turn = 0;
                    }else
                    {
                        for(int i =0; i < 5; i++)
                        {
                            tmp = (Location) is_fromPlayer2.readObject();
                            os_toPlayer1.writeObject(tmp);
                        }
                        turn = 1;
                    }
                    os_toPlayer1.writeObject(1);//GameOver signal
                    os_toPlayer2.writeObject(1);
                    
                    os_toPlayer1.writeObject(turn);
                    os_toPlayer2.writeObject(turn);
                }
            }catch(IOException e)
            {
                System.out.println("IOException in GameServer startTurn");
            }catch(ClassNotFoundException c){
				System.out.println("ClassNotFoundException in GameServer startTurn");
			}
        }
	}
	
	class GameSession implements Runnable{

		private Socket s_player1,s_player2;
		
		private ObjectInputStream is_fromPlayer1,is_fromPlayer2;
		private ObjectOutputStream os_toPlayer1,os_toPlayer2;

		//chat task
		private ChatSession gameChat;

		//will be used for chat 
		private Thread chatThread;

		//current game object
		private Game currentGame;

		//load assets object for game constructor
		private LoadAssets gameAssets;

		public GameSession(Socket socket1,Socket socket2){

			gameChat=new ChatSession(socket1,socket2);

			//assign sockets to players
			s_player1=socket1;
			s_player2=socket2;

			try{
				
				//associate iostreams
				is_fromPlayer1=new ObjectInputStream(socket1.getInputStream());					
				os_toPlayer1=new ObjectOutputStream(socket1.getOutputStream());
					
				is_fromPlayer2=new ObjectInputStream(socket2.getInputStream());
				os_toPlayer2=new ObjectOutputStream(socket2.getOutputStream());
					
				System.out.println("All data streams connected");
				
			}catch(IOException io){
				System.err.println(io);
			}




		}

		public void run(){

			//get names of players
			//set up boards
			//send boards to one another
			//

		}

	}

}