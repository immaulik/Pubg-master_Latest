package com.example.pubgtournament.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubgtournament.R;
import com.example.pubgtournament.Api.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUser extends AppCompatActivity {

    TextInputLayout pubgName,firstName,lastName,emailId,password,referCode,mobileNo;
    Button btnRegister;
    TextView loginTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        pubgName = findViewById(R.id.pubg_user_name);
        firstName = findViewById(R.id.user_first_name);
        lastName = findViewById(R.id.user_last_name);
        emailId = findViewById(R.id.user_email_id);
        password = findViewById(R.id.user_password);
        referCode = findViewById(R.id.user_refer_code);
        mobileNo = findViewById(R.id.user_mobile_number);

        btnRegister = findViewById(R.id.btnRegister);
        loginTextview=findViewById(R.id.already_account);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pubg_name, first_name, last_name, email_id, password, refer_code;
                String mobile_number;
//                int user_balance=50000;
                int bonus=10;
//                int withdraw_balance=0;

                pubg_name = pubgName.getEditText().getText().toString();
                first_name = firstName.getEditText().getText().toString();
                last_name = lastName.getEditText().getText().toString();
                email_id = emailId.getEditText().getText().toString();
                password = RegisterUser.this.password.getEditText().getText().toString();
                refer_code = referCode.getEditText().getText().toString();
                mobile_number = String.valueOf(mobileNo.getEditText().getText());

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .createUser(pubg_name, first_name, last_name, email_id, mobile_number, password, refer_code,00,10,00);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            String s=response.body().string();
                            if (s.equals("inserted")){
                                Intent intent=new Intent(RegisterUser.this,LoginUser.class);
                                intent.putExtra("pubg_name",pubg_name);
                                intent.putExtra("password",password);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegisterUser.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(RegisterUser.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loginTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterUser.this,LoginUser.class);
                startActivity(intent);
            }
        });

    }
}
