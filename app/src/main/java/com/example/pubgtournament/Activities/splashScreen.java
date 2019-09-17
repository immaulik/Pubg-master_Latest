package com.example.pubgtournament.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.pubgtournament.R;

public class splashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences,sharedPreferences1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        toolbar=findViewById(R.id.splash_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pubg Tournament");

        sharedPreferences=getSharedPreferences("UserDetails",MODE_PRIVATE);
        String email=sharedPreferences.getString("pubg_name","hello");
        if(!email.equals("hello"))
        {
            Thread myThread = new Thread()
            {
                @Override
                public void run() {
                    try {
                        sleep(1200);
                        Intent intent=new Intent(splashScreen.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences=getSharedPreferences("UserDetails",MODE_PRIVATE);
        String email=sharedPreferences.getString("pubg_name","hello");
        if(email.equals("hello"))
        {
            Intent intent=new Intent(splashScreen.this,LoginUser.class);
            startActivity(intent);
        }
    }
}
