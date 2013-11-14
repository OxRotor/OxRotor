package com.example.oxclient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class Login extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
   }

   public void onClick(View v) 
   {
      Intent intent = new Intent(this, Control.class);
      startActivity(intent);
   }

}