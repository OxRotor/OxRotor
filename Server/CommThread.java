import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/** CommThread receives from one socket and sends to another **/
public class CommThread implements Runnable {
	Socket s1, s2;			// sockets from two Android devices
	Scanner in;			// input source
	PrintWriter out;		// output destination

	CommThread(Socket s1, Socket s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	/** Tests the user credentials
	  * 
	  * @return whether or not the user is authorized */
	public boolean valid() {
		String un = "", pw = "";
		try {
			in = new Scanner(s1.getInputStream());
			if (in.hasNext()) {
				un = in.nextLine();
			}
			if (in.hasNext()) {
				pw = in.nextLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Authenticate.verify(un, pw);
	}

	/** Terminates a connection based on invalid credentials in the pair */
	public void reject() {
		try {
			out = new PrintWriter(s2.getOutputStream());
			out.println("Your device pair has not been properly verified.");
			out.println("Please retry to connect to the other device.");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Relays information between clients **/
	@Override
	public void run() {
		try {
			// initialize variables for socket i/o
			in = new Scanner(s1.getInputStream());
			out = new PrintWriter(s2.getOutputStream());

			// run for an indefinite period of time
			while (true) {
				// relay info if present
				if (in.hasNext()) {
					out.println(in.nextLine());
					out.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
