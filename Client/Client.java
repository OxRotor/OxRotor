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
    
        /** The actual client used in communication
          *
          * @param s a socket connected to a server
          */
        public Client(Socket s) {
                socket = s;
        }

        public static void main(String[] args) throws IOException {
                try {
                        Socket s = new Socket(HOST, PORT);
                        System.out.println("You connected to " + HOST);
                        Client client = new Client(s);
                        Thread t = new Thread(client);
                        t.start();
                } catch (Exception e) {
                        System.out.println("Could not connect to server");
                }
        }

        /** Runs a loop that facilitates communications **/
        @Override
        public void run() {
                try {
                        CommThread c1 = new CommThread(socket, 0);
                        CommThread c2 = new CommThread(socket, 1);
                        Thread t1 = new Thread(c1);
                        Thread t2 = new Thread(c2);
                        t1.start();
                        t2.start();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
