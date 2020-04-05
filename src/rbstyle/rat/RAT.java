package rbstyle.rat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RAT extends Thread{
	
	void send(Socket receiver, String data) throws IOException {
		OutputStream output	= receiver.getOutputStream();
		PrintWriter writer = new PrintWriter(output,true);
		writer.println(data);
	}
	
	String receive(Socket sender) throws IOException {
		InputStream input = sender.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		/*String token = null;
		String all = "";
		while(true) {
			token = reader.readLine();
			if(token.length()<=0) break;
			all += token + "\n";
		}
		return all;*/
		return reader.readLine();
	}

}
