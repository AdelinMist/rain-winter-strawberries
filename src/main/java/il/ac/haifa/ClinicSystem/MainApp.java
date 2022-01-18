package il.ac.haifa.ClinicSystem;

import java.io.IOException;

public class MainApp {
	public static void main(String[] args) throws IOException {
		//here we parse the arguments that were given in the configuration
		String host = args[0];
		int port = Integer.parseInt(args[1]);


		//creating a new Client and giving it the necessary arguments we got from the user
		SimpleClient chatClient = new SimpleClient(host, port, "user");
		chatClient.openConnection();
    }
}
