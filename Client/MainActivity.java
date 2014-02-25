package com.example.tcptest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

   private Socket socket;
   private static TextView tv;

   private static final int SERVERPORT = 443;
   private static final String SERVER_IP = "54.213.92.214";

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      tv = (TextView) findViewById(R.id.textView);

      new Thread(new ClientThread()).start();
   }
   
   public static Handler mHandler = new Handler() {
      public void handleMessage(Message msg) {
          tv.setText((msg.obj).toString());
      }
  };

   public void onClick(View view) {
      try {
         EditText et = (EditText) findViewById(R.id.EditText01);
         String str = et.getText().toString();
         PrintWriter out = new PrintWriter(new BufferedWriter(
               new OutputStreamWriter(socket.getOutputStream())),
               true);
         out.println(str);         
         
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   class ClientThread implements Runnable {

      @Override
      public void run() {

         try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            socket = new Socket(serverAddr, SERVERPORT);
            
            Scanner in = new Scanner(socket.getInputStream());

            while (true) {
               if (in.hasNext()) {
                  mHandler.obtainMessage(1, in.nextLine()).sendToTarget();
               }
            }

         } catch (UnknownHostException e1) {
            e1.printStackTrace();
         } catch (IOException e1) {
            e1.printStackTrace();
         }

      }

   }
}
