// BE CAREFUL! Must be run as root

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/** Server accepts communications from clients and pairs them together **/
public class Server {
	public static void main(String[] args) throws IOException {

		try {
			final int PORT = 443;	// Protected ports are useful to get your traffic through
			ServerSocket ss = new ServerSocket(PORT);
			System.out.println("Waiting for clients...");
			Socket android1 = null, android2 = null;
			String un, pw, mID, dID;
			ArrayList<Client> clients = new ArrayList<Client>();
			Scanner in;

			// Runs indefinitely, pairing reciprocal requests
			while (true) {
				Socket s = ss.accept();

				in = new Scanner(s.getInputStream());
				un = in.nextLine();
				pw = in.nextLine();
				mID = in.nextLine();
				dID = in.nextLine();

				if (Authenticate.verify(un, pw)) {
					System.out.println("Client connected from " + s.getLocalAddress().getHostName());
					if (idpair(clients, mID, dID)) {
						android1 = getIDPair(clients, mID, dID);
						android2 = s;
						CommThread c1 = new CommThread(android1, android2);
						CommThread c2 = new CommThread(android2, android1);
						Thread t1 = new Thread(c1);
						Thread t2 = new Thread(c2);
						t1.start();
						t2.start();
					} else {
						clients.add(new Client(s, mID, dID));
					}
				} else {
					PrintWriter out = new PrintWriter(s.getOutputStream());
					out.println("Authentication error - aborting connection");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean idpair(ArrayList<Client> clients, String mID, String dID) {
		for (Client c : clients)
			if (c.getDeviceID().equals(dID) && c.getDesiredDeviceID().equals(mID))
				return true;
		return false;
	}

	private static Socket getIDPair(ArrayList<Client> clients, String mID, String dID) {
		for (Client c : clients)
			if (c.getDeviceID().equals(dID) && c.getDesiredDeviceID().equals(mID))
				return c.getSocket();
		return null;
	}
}
