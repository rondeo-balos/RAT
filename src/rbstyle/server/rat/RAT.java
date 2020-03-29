package rbstyle.server.rat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RAT extends Thread{
	
	protected void send(Socket receiver, JSONObject data) throws IOException {
		OutputStream output	= receiver.getOutputStream();
		PrintWriter writer = new PrintWriter(output,true);
		writer.println(data.toJSONString());
		writer.close();
		output.close();
	}
	
	protected JSONObject receive(Socket sender) throws IOException, ParseException {
		InputStream input = sender.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(reader);
	}

}
