package com.example.pubgtournament.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.SendMail;
import com.example.pubgtournament.Models.UsersData;
import com.example.pubgtournament.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail;
    private Button buttonSend;
    String pass,name,email_id;
    Context context=this;

    private static final String shared_pref = "UserDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail =findViewById(R.id.editTextEmail);

        buttonSend = findViewById(R.id.buttonSend);

        //Adding click listener
        buttonSend.setOnClickListener(this);
    }

    private void sendEmail() {
        SharedPreferences sharedPreferences=getSharedPreferences(shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String email = editTextEmail.getText().toString().trim();

        Call<List<UsersData>> call= RetrofitClient
                .getInstance()
                .getApi()
                .getEmail(email);

        call.enqueue(new Callback<List<UsersData>>() {
            @Override
            public void onResponse(Call<List<UsersData>> call, Response<List<UsersData>> response) {
                List<UsersData> usersData=response.body();
                for(UsersData usersData1 : usersData)
                {
                    pass=usersData1.getPassword();
                    name=usersData1.getPubgName();
                    email_id=usersData1.getEmailId();
                }
                if(email.equals(email_id)) {

                    String subject = "User's Details";
                    String message = pass + name;
                    SendMail sm = new SendMail(context, email, subject, message);

                    sm.execute();
                }else{
                    Toast.makeText(ForgotPasswordActivity.this, "Your Eamil is not registered with us.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<UsersData>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }
}

