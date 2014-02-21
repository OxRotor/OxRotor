import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/** CommThread receives from one socket and sends to another **/
public class CommThread implements Runnable {
        Socket s;                       // socket to server
        Scanner in;                     // input source
        PrintWriter out;                // output destination
        int option;
    
        /** Creates a CommThread
          *
          * @param s the socket to the server
          * @param in the source of input
          */
        CommThread(Socket s, Scanner in) {
                this.s = s;
                this.option = 0;
                this.in = in; 
        }

        /** Creates a CommThread
          *
          * @param s the socket to the server
          * @param out the destination for output
          */
        CommThread(Socket s, PrintWriter out) {
                this.s = s;
                this.option = 1;
                this.out = out;
        }

        /** Relays information to and from server **/
        @Override
        public void run() {
                try {
                        if (option == 0) {
                                out = new PrintWriter(s.getOutputStream());
    
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
