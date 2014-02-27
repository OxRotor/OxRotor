import java.net.Socket; 
                        
/* Simple Client class - just bunches sockets and ID's for convenience */
public class Client {   
        private Socket s;
        private String mID, dID;        // actual and desired device ID's
                
        /** A client class to help the server handle connection requests
          * 
          * @param s the socket connected to the client
          * @param mID the device ID of the requesting client
          * @param dID the device ID of the desired client */
        public Client(Socket s, String mID, String dID) {
                this.s = s;
                this.mID = mID;
                this.dID = dID;
        }
        
        /** Returns the socket from the client
          * 
          * @return the socket from this client */
        public Socket getSocket() {
                return s;
        }
        
        /** Returns the device ID used by this client
          *     
          * @return the device ID of the client */
        public String getDeviceID() {
                return mID;
        }               
                
        /** Returns the device ID used by the requested client
          *     
          * @return the desired device ID */
        public String getDesiredDeviceID() {
                return dID;
        }
}       
