import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	// The IP and port of the server process
	private final static String HOST = "54.213.92.214";
	private final static int PORT = 443;

	public static void main(String[] args) throws IOException {
		try {
			// Establishes a socket and spawns a child thread
			Scanner in = new Scanner(System.in);
			PrintWriter out = new PrintWriter(System.out);
			Client client = new Client("capstone", "capstone", in, out);
			Thread t = new Thread(client);
			t.start();
		} catch (Exception e) {
			System.out.println("Could not connect to server");
		}
	}
}
