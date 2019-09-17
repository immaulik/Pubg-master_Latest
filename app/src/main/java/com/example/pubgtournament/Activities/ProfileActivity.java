package com.example.pubgtournament.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.UsersData;
import com.example.pubgtournament.R;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextInputLayout name, fname, lname, number, email, newPassword, rePassword;
    Toolbar toolbar;
    Button button, btnPassword;
    private static final String shared_pref = "UserDetails";
    String user_id;
    int id;

    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.ptofile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.profile_btn_submit);
        btnPassword = findViewById(R.id.profile_btn_submit_password);

        name = findViewById(R.id.profile_pubg_name);
        fname = findViewById(R.id.profile_pubg_first_name);
        lname = findViewById(R.id.profile_pubg_last_name);
        number = findViewById(R.id.profile_pubg_mobile_number);
        email = findViewById(R.id.profile_pubg_email_id);
        newPassword = findViewById(R.id.profile_pubg_new_password);
        rePassword = findViewById(R.id.profile_re_enter_password);

        String name1, fname1, lname1, email1, mobile1, password;

        SharedPreferences sharedPreferences = getSharedPreferences(shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        name1 = sharedPreferences.getString("pubg_name", null);
        lname1 = sharedPreferences.getString("last_name", null);
        fname1 = sharedPreferences.getString("first_name", null);
        email1 = sharedPreferences.getString("email_id", null);
        mobile1 = sharedPreferences.getString("mobile_number", null);
        password = sharedPreferences.getString("password", null);

        Call<List<UsersData>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUsers(name1);

        call.enqueue(new Callback<List<UsersData>>() {
            @Override
            public void onResponse(Call<List<UsersData>> call, retrofit2.Response<List<UsersData>> response) {

                List<UsersData> usersData = response.body();

                for (UsersData data : usersData) {
                    user_id = data.getUserId();
                }
                Log.d("userBalanceSolo", user_id);

            }
            @Override
            public void onFailure(Call<List<UsersData>> call, Throwable t) {

            }
        });

        name.getEditText().setText(name1);
        fname.getEditText().setText(fname1);
        lname.getEditText().setText(lname1);
        number.getEditText().setText(mobile1);
        email.getEditText().setText(email1);

        name.getEditText().setEnabled(false);
        email.getEditText().setEnabled(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validateFristName() | !validatePhone()) {
                    return;
                } else {
                    String first, last, phn;
                    first = fname.getEditText().getText().toString();
                    last = lname.getEditText().getText().toString();
                    phn = number.getEditText().getText().toString();

                     id= Integer.parseInt(user_id);

                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .updateProfile(first, last, phn, id);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String s = response.body().string();

                                if (s.equals("Profile Updated")) {

                                    SharedPreferences preferences = getSharedPreferences(shared_pref,Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor1 = preferences.edit();

                                    editor1.putString("first_name",first);
                                    editor1.putString("last_name",last);
                                    editor1.putString("mobile_number",phn);

                                    editor1.apply();

                                    Toast.makeText(ProfileActivity.this, "Your Profile Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            }
        });

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validatePassword() | !validateRePassword()) {
                    return;
                } else {

                    String pass, newPass;

                    pass = newPassword.getEditText().getText().toString();
                    newPass = rePassword.getEditText().getText().toString();

                    if (pass.equals(newPass)) {

                        newPassword.getEditText().setText("");
                        rePassword.getEditText().setText("");

                        Toast.makeText(ProfileActivity.this, "Your Password Changed Successfully", Toast.LENGTH_SHORT).show();

                        Call<ResponseBody> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .updatePassword(pass, id);

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                try {
                                    String s = response.body().string();

                                    if (s.equals("Password Updated")) {

                                        editor.putString("password", pass);
                                        editor.apply();

                                        String uPass = sharedPreferences.getString("password", null);
                                        Toast.makeText(ProfileActivity.this, "Your New Password:" + uPass, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "Please Enter The Same Password As You Have Entered Above", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }

                }
            }
        });
    }

    private boolean validateFristName() {
        String validate_f_name = fname.getEditText().getText().toString().trim();
        if (validate_f_name.isEmpty()) {
            fname.setError("Field Can't Be Empty");
            return false;
        } else if (validate_f_name.length() > 10) {
            lname.setError("Username Too Long!!!");
            return false;
        } else {
            fname.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String username = lname.getEditText().getText().toString().trim();
        if (username.isEmpty()) {
            lname.setError("Field Can't Be Empty");
            return false;
        } else if (username.length() > 10) {
            lname.setError("Username Too Long!!!");
            return false;
        } else {
            lname.setError(null);
            return true;
        }
    }

//    private boolean validateAddress() {
//        String address = edtArea.getEditText().getText().toString().trim();
//        if (address.isEmpty()) {
//            edtArea.setError("Field Can't Be Empty");
//            return false;
//        } else if (address.length() > 50) {
//            edtArea.setError("Address Too Long!!!");
//            return false;
//        } else {
//            edtArea.setError(null);
//            return true;
//        }
//    }

//    private boolean validateName() {
//        String name = edtName.getEditText().getText().toString().trim();
//        if (name.isEmpty()) {
//            edtName.setError("Field Can't Be Empty");
//            return false;
//        } else if (name.length() > 10) {
//            edtName.setError("Hospital Name Too Long!!!");
//            return false;
//        } else {
//            edtUsername.setError(null);
//            return true;
//        }
//    }

    private boolean validatePassword() {
        String password = newPassword.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            newPassword.setError("Field Can't Be Empty!!!");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            newPassword.setError("Password Too Weak!!");
            return false;
        } else {
            newPassword.setError(null);
            return true;
        }
    }

    private boolean validateRePassword() {
        String password1 = rePassword.getEditText().getText().toString().trim();
        if (password1.isEmpty()) {
            rePassword.setError("Field Can't Be Empty!!!");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password1).matches()) {
            rePassword.setError("Password Too Weak!!");
            return false;
        } else {
            rePassword.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        boolean check = false;
        String phn = number.getEditText().getText().toString().trim();
        if (phn.isEmpty()) {
            number.setError("Field Can't Be Empty");
            return false;
        } else if (!Pattern.matches("[a-zA-Z]+", phn)) {
            if (phn.length() < 6 || phn.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
                number.setError("Not Valid Number");
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

}
