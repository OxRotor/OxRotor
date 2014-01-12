package com.example.oxclient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private EditText username;
	private EditText password;
	protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      username = (EditText)findViewById(R.id.editText1);
      password = (EditText)findViewById(R.id.editText2);
	}

	public void onClick(View v) 
	{
	   if(username.getText().toString().equals("pedro") && password.getText().toString().equals("posse")){
		    Intent intent = new Intent(this, Control.class);
	   		startActivity(intent);
	   }
	   else{
		   Toast.makeText(getApplicationContext(),"Wrong Credentials",Toast.LENGTH_SHORT).show();
	   }
	}
}