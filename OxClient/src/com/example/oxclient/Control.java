package com.example.oxclient;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Control extends Activity {
   
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_control);
   }
   
   public void north(View v) 
   {
      MessageBox("You pressed North!");
   }
   
   public void south(View v) 
   {
      MessageBox("You pressed South!");
   }
   
   public void east(View v) 
   {
      MessageBox("You pressed East!");
   }
   
   public void west(View v) 
   {
      MessageBox("You pressed West!");
   }
   
   public void MessageBox(String message)
   {
      Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
   } 
}
