package rbstyle.server.rat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * 
 * This type of object implements Runnable so that it listen always
 * @author rondeo
 *
 */
public class Server extends RAT{
	private ServerSocket listener = null;
	private List<Socket> clients = new ArrayList<>();
	private boolean listen = true;
	
	public Server() throws IOException {
		this(1111);
	}
	
	public Server(int port) throws IOException {
		listener = new  ServerSocket(port);
		System.out.println("listening to port "+port);
		start();
	}
	
	@Override
	public void run() {
		try {
			startListening();
		} catch (IOException e) {
			e.printStackTrace();
			stopListening();
		}
	}
	
	public void startListening() throws IOException {
		while(listen) {
			Socket tmpSender = listener.accept();
			boolean push = true;
			
			for(Socket s : clients)
				if(s.equals(tmpSender))
					push = false;
			
			if(push)
				clients.add(tmpSender);
		}
	}
	
	public void stopListening() {
		try {
			listen = false;
			for(Socket s : clients)
				if(!s.isClosed())
					s.close();
			clients.clear();
			listener.close();
			stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Socket> getConnections() {
		return clients;
	}
	
	public void send(int index, JSONObject data) throws IOException {
		send(clients.get(index),data);
	}
	
	public JSONObject receive(int index) throws IOException, ParseException {
		return receive(clients.get(index));
	}
	
}
