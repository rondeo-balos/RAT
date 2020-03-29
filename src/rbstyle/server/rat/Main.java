package rbstyle.server.rat;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws IOException{
		Server server = null;
		Scanner scanner = new Scanner(System.in);
		boolean run = true;
		while(run) {
			System.out.print("$ ");
			String cmd = scanner.nextLine();
			String[] token = cmd.split(" ");
			switch(token[0]) {
				case "listen":
					server = new Server(Integer.parseInt(token.length>1?token[1]:"7898"));
					break;
				case "stop":
					server.stopListening();
					break;
				case "exit":
					run = false;
					break;
			}
		}
	}

}
