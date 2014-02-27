import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/** CommThread receives from one socket and sends to another **/
public class CommThread implements Runnable {
	Socket s;			// socket to server
	Scanner in;			// input source
	PrintWriter out;		// output destination
	int option;
	String un, pw, myID, desiredID;	// username and password
	
	/** Creates a CommThread
	  *
	  * @param un the username
	  * @param pw the password
	  * @param myID the current device ID
	  * @param desiredID the ID of the target device
	  * @param s the socket to the server
	  * @param in the source of input
	  */
	CommThread(String un, String pw, String myID, String desiredID, Socket s, Scanner in) {
		this.s = s;
		this.in = in;
		this.un = un;
		this.pw = pw;
		this.myID = myID;
		this.desiredID = desiredID;
		option = 0;
	}

	/** Creates a CommThread
	  *
	  * @param s the socket to the server
	  * @param out the destination for output
	  */
	CommThread(Socket s, PrintWriter out) {
		this.s = s;
		this.out = out;
		option = 1;
	}

	/** Relays information to and from server **/
	@Override
	public void run() {
		try {
			if (option == 0) {
				out = new PrintWriter(s.getOutputStream());
				out.println(un);
				out.println(pw);
				out.println(myID);
				out.println(desiredID);
				out.flush();
				
				while (true) {
					// send info if present
					if (in.hasNext()) {
						out.println(in.nextLine());
						out.flush();
					}
				}
			} else if (option == 1) {
				in = new Scanner(s.getInputStream());

				while (true) {
					// print info if present
					if (in.hasNext()) {
						out.println(in.nextLine());
						out.flush();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
