package edu.usna.oxcontroller;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class MainMenu extends Activity implements OnClickListener {
   
   String userName = "";
   Button manual, automatic, config;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main_menu);
      
      manual = (Button)findViewById(R.id.manButton);
      manual.setOnClickListener(this);
      automatic = (Button)findViewById(R.id.autoButton);
      automatic.setOnClickListener(this);
      config = (Button)findViewById(R.id.configButton);
      config.setOnClickListener(this);
      
      final TextView userView = (TextView) findViewById(R.id.userView);
      
      Intent intent = getIntent();
      userName = intent.getStringExtra("username");
      userView.setText("Welcome, " + userName + ".");
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main_menu, menu);      
      return true;
   }
   
   public void onClick(View v) {
       if(v == manual){
          Intent intent = new Intent(getBaseContext(), ManualActivity.class);
          startActivity(intent);
       }
       else if(v == automatic){
          Intent intent = new Intent(getBaseContext(), AutoActivity.class);
          startActivity(intent);
       }
   }
}
