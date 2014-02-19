import java.io.IOException;
import java.net.Socket;

public class Main {
        // The IP and port of the server process
        private final static String HOST = "54.213.92.214";
        private final static int PORT = 443;

        public static void main(String[] args) throws IOException {
                try {
                        // Establishes a socket and spawns a child thread
                        Socket s = new Socket(HOST, PORT);
                        System.out.println("You connected to " + HOST);
                        Client client = new Client(s);
                        Thread t = new Thread(client);
                        t.start();
                } catch (Exception e) {
                        System.out.println("Could not connect to server");
                }
        }
}
