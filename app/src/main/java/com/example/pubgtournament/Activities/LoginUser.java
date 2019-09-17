package com.example.pubgtournament.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.UsersData;
import com.example.pubgtournament.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUser extends AppCompatActivity {

    TextInputLayout pubgUserName, pubgPassword;
    Button btnLogin;
    TextView signUp;
    Toolbar toolbar;
    String pubg_name, first_name, last_name, email_id, mobile_number, password, refer_code;
    String user_id, user_balance, bonus, withdraw_balance;
    private static final String shared_pref = "UserDetails";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        toolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");

        pubgUserName = findViewById(R.id.pubg_login_user);
        pubgPassword = findViewById(R.id.pubg_login_password);

        btnLogin = findViewById(R.id.btnLoginUser);
        signUp = findViewById(R.id.new_user_sign_up);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,pass;
                name=pubgUserName.getEditText().getText().toString();
                Log.d("pubgUserName",name);
                pass=pubgPassword.getEditText().getText().toString();

                Call<List<UsersData>> call=RetrofitClient
                        .getInstance()
                        .getApi()
                        .getUsers(name);

                call.enqueue(new Callback<List<UsersData>>() {
                    @Override
                    public void onResponse(Call<List<UsersData>> call, Response<List<UsersData>> response) {
                        List<UsersData> usersData=response.body();

                        for (UsersData data : usersData){
                            pubg_name=data.getPubgName();
                            password=data.getPassword();
                            first_name=data.getFirstName();
                            last_name=data.getLastName();
                            refer_code=data.getReferCode();
                            user_id=data.getUserId();
                            email_id=data.getEmailId();
                            mobile_number=data.getMobileNumber();
                            user_balance=data.getUserBalance();
                            bonus=data.getBonus();
                            withdraw_balance=data.getWithdrawBalance();
                        }

                        if (name.equals(pubg_name) && pass.equals(password)){
                            sharedPreferences=getSharedPreferences(shared_pref, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();

                            editor.putString("pubg_name",pubg_name);
                            editor.putString("password",password);
                            editor.putString("first_name",first_name);
                            editor.putString("refer_code",refer_code);
                            editor.putString("user_balance",user_balance);

                            Log.d("aaaa",user_balance);
                            editor.putString("user_id",user_id);
                            editor.putString("email_id",email_id);
                            editor.putString("mobile_number",mobile_number);
                            editor.putString("user_balance",user_balance);
                            editor.putString("bonus",bonus);

                            Log.d("aabbcc",bonus);
                            editor.putString("withdraw_balance",withdraw_balance);

                            editor.apply();

                            Intent intent=new Intent(LoginUser.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(LoginUser.this, "Not Matched", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UsersData>> call, Throwable t) {

                    }
                });

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUser.this, RegisterUser.class);
                startActivity(intent);
            }
        });

    }

    public void Forgot_Password(View view) {
        Intent intent=new Intent(LoginUser.this,ForgotPasswordActivity.class);
        startActivity(intent);
    }

}
