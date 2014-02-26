package edu.usna.oxcontroller;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
   
   EditText userText, passText;
   Button login;
   Toast toast;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      userText = (EditText) findViewById(R.id.userText);
      passText = (EditText) findViewById(R.id.passText);
      login = (Button)findViewById(R.id.loginButton);
      login.setOnClickListener(this);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }
   
   @Override
   public void onClick(View v) {
       //if(v == login){
           //if(userText.getText().toString().equals("Pedro") && passText.getText().toString().equals("posse")){
              Intent intent = new Intent(getBaseContext(), MainMenu.class);
              intent.putExtra("username", userText.getText().toString());
              startActivity(intent);
           /*}
           else {
              toast = Toast.makeText(getBaseContext(), "Invalid Credentials", Toast.LENGTH_LONG);
              toast.show();
           }   
           */        
       //} 
   }

}
