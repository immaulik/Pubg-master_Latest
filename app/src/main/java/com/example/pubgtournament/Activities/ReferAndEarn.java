package com.example.pubgtournament.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubgtournament.R;

import java.util.Collections;

public class ReferAndEarn extends AppCompatActivity {

    TextView copyReferCode,shareReferCode;
    Toolbar toolbar;
    Button referBtn;

    private static final String shared_pref="UserDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);

        copyReferCode = findViewById(R.id.copyReferCode);
        shareReferCode = findViewById(R.id.shareReferCode);

        referBtn = findViewById(R.id.shareReferCodeBtn);

        toolbar = findViewById(R.id.referAndEarnToolbaar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Refer And Earn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(shared_pref, Context.MODE_PRIVATE);
        String referCode = sharedPreferences.getString("refer_code",null);

        Log.d("referCode",referCode);

        copyReferCode.setText(referCode);
        shareReferCode.setText(referCode);

        copyReferCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Refer Code", referCode);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(ReferAndEarn.this, "Refer Code Copied To Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        referBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName();
                String text="Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName +"\n Use my refer code to get 10 Rs. Bonus :" +referCode;

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }
}
