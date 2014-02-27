import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

/** Routes communications through a server to another device **/
public class Client implements Runnable {
	private Socket socket;
	// server IP may vary
	private final static String HOST = "54.213.92.214";
	// use a protected port to get traffic through
	private final static int PORT = 443;
	private String username;
	private String password;
	private Scanner in;
	private PrintWriter out;
	private String myID, desiredID;

	/** The actual client used in communication
	  *
	  * @param s a socket connected to a server
	  */
	public Client(Socket s) {
		socket = s;
	}

	/** The actual client used in communication
	  *
	  * @param username the user's login name
	  * @param password the user's password
	  * @param myID the current device ID
	  * @param desiredID the ID of the target device
	  * @param in the source of data
	  * @param out the destination for output
	  */
	public Client(String username, String password, String myID, String desiredID, Scanner in, PrintWriter out) {
		this.username = username;
		this.password = password;
		this.myID = myID;
		this.desiredID = desiredID;
		this.in = in;
		this.out = out;
		try {
			socket = new Socket(HOST, PORT);
		} catch (Exception e) {
			System.out.println("ERROR: SOCKET FAILED");
		}
	}

	@Override
	public void run() {
		try {
			CommThread c1 = new CommThread(username, password, myID, desiredID, socket, in);
			CommThread c2 = new CommThread(socket, out);
			Thread t1 = new Thread(c1);
			Thread t2 = new Thread(c2);
			t1.start();
			t2.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
