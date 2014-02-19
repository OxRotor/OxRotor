// BE CAREFUL! Must be run as root

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/** Server accepts communications from clients and pairs them together **/
public class Server {
        public static void main(String[] args) throws IOException {
                try {
                        final int PORT = 443;   // Protected ports are useful to get your traffic through
                        ServerSocket ss = new ServerSocket(PORT);
                        System.out.println("Waiting for clients...");
                        Socket android1 = null, android2 = null;

                        // runs indefinitely.  As of now, it will pair any even number of devices together
                        while (true) {
                                Socket s = ss.accept();

                                System.out.println("Client connected from " + s.getLocalAddress().getHostName());

                                if (android1 != null) {
                                        android2 = s;
                                        CommThread c1 = new CommThread(android1, android2);
                                        CommThread c2 = new CommThread(android2, android1);
                                        Thread t1 = new Thread(c1);
                                        Thread t2 = new Thread(c2);
                                        t1.start();
                                        t2.start();
                                }   
    
                                android1 = s;
                        }   
                } catch (Exception e) {
                        System.out.println("An error occurred");
                        e.printStackTrace();
                }   
        }   
}
