import java.io.*;
import java.util.*;

public class Authenticate {
        /** Verifies a user against a database
          * 
          * @param username the login username
          * @param password the login password
          * @return whether or not the user is authorized
          */
        public static boolean verify (String username, String password) throws IOException {
                Scanner fin = new Scanner(new File("users.txt"));
                while (fin.hasNext()) {
                        if (username.equals(fin.next()))
                                if (password.equals(fin.next()))
                                        return true;
                }   
                return false;
        }   
}
