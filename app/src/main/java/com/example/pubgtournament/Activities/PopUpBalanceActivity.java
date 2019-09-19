package com.example.pubgtournament.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.TransactionTwo;
import com.example.pubgtournament.Models.UsersData;
import com.example.pubgtournament.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PopUpBalanceActivity extends Activity {

    String userBalance, userbonus, match_number, total;
    int entryFee, user_id;
    TextView entryBalance, bonusBalance, totalWinnings, toPay;
    Button btnJoin;
    int totalInt, match_idInt;

    private static final String shared_pref = "UserDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_balance);

        entryBalance = findViewById(R.id.entryFeeBalance);
        bonusBalance = findViewById(R.id.bonusBalance);
        totalWinnings = findViewById(R.id.totalWinnings);
        toPay = findViewById(R.id.toPay);
        btnJoin = findViewById(R.id.btnJoinContest);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;


        getWindow().setAttributes(params);

        entryFee = getIntent().getIntExtra("entryFee", 0);
        user_id = getIntent().getIntExtra("user_id", 0);
        match_number = getIntent().getStringExtra("match_number");

        Log.d("User'sMatch_Id", match_number);

        Call<List<UsersData>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getData(user_id);

        call.enqueue(new Callback<List<UsersData>>() {
            @Override
            public void onResponse(Call<List<UsersData>> call, Response<List<UsersData>> response) {
                List<UsersData> usersData = response.body();

                for (UsersData data : usersData) {
                    userbonus = data.getBonus();
                    userBalance = data.getUserBalance();

                    Log.d("userBonus", userbonus);
                    Log.d("userBalance", userBalance);

                    float userBalanceInt = Float.parseFloat(userBalance);
                    float userBonusInt = Float.parseFloat(userbonus);

                    float totalBalance = userBalanceInt + userBonusInt;
                    Log.d("TotalBalance", "" + totalBalance);

                    totalWinnings.setText(String.valueOf(totalBalance));
                    entryBalance.setText(String.valueOf(entryFee));
                    bonusBalance.setText(userbonus);


                    match_idInt = Integer.parseInt(match_number);


                    float Entry_35 = ((entryFee / 100.0f) * 35);
                    Log.d("aaa35%", String.valueOf(Entry_35));
                    if (userBonusInt >= Entry_35) {
                        float Total_Bonus = userBonusInt - Entry_35;
                        float Remaining = entryFee - Entry_35;
                        float Total_balance = userBalanceInt - Remaining;
                        Log.d("aaarfees", String.valueOf(Remaining));
                        Log.d("aaaTotalBalance", String.valueOf(Total_balance));
                        entryBalance.setText(String.valueOf(entryFee));
                        Log.d("aaabonus", String.valueOf(Total_Bonus));
                        bonusBalance.setText(String.valueOf("-" + Entry_35));
                        toPay.setText(String.valueOf(Remaining));

                        btnJoin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Call<ResponseBody> call = RetrofitClient
                                        .getInstance()
                                        .getApi()
                                        .updateTotal(Total_balance, Total_Bonus, user_id);

                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                        try {
                                            String s = response.body().string();

                                            Log.d("StringResponse", s);

                                            if (s.equals("Amount Updated")) {
                                                float userBalanceInt = Float.parseFloat(userBalance);
                                                //int match_number = getIntent().getIntExtra("match_number", 0);

                                                Log.d("matchNumber", String.valueOf(match_number));

                                                int first_prize = getIntent().getIntExtra("first_prize", 0);
                                                String pubg_name = getIntent().getStringExtra("pubg_name");

                                                if (userBalanceInt <= 0 || userBalanceInt < entryFee) {
                                                    Toast.makeText(PopUpBalanceActivity.this, "You don't have sufficient balance", Toast.LENGTH_SHORT).show();
                                                } else {

                                                    Toast.makeText(PopUpBalanceActivity.this, "You Have Successfully Joined Solo Contest", Toast.LENGTH_SHORT).show();

                                                    String chickenStatus = "";
                                                    int usersKill = 0;
                                                    int killPayment = 0;
                                                    int userPrize = 0;

                                                    Calendar calendar = Calendar.getInstance();
                                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                                    String time = format.format(calendar.getTime());

                                                    String date = DateFormat.getDateInstance().format(calendar.getTime());

                                                    String history = "You have successfully joined " + entryFee + "rs Contest";
                                                    String result = "Upcoming";
                                                    String joinedStatus = "Joined";

                                                    Call<ResponseBody> callInsert = RetrofitClient
                                                            .getInstance()
                                                            .getApi()
                                                            .userTransaction(pubg_name, history, date, time, first_prize, entryFee, match_idInt, result, usersKill, chickenStatus, killPayment, userPrize,joinedStatus);

                                                    callInsert.enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                            try {
                                                                String s = response.body().string();

                                                                Log.d("responseResponse", "" + s);

                                                                if (s.equals("success")) {

                                                                    Toast.makeText(PopUpBalanceActivity.this, "Congrats", Toast.LENGTH_SHORT).show();

                                                                    Intent intent = new Intent(PopUpBalanceActivity.this,MainActivity.class);
                                                                    startActivity(intent);

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
                                            } else {
                                                Toast.makeText(PopUpBalanceActivity.this, "Problem", Toast.LENGTH_SHORT).show();
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
                        });

                    }
                    if(userBonusInt<=Entry_35)
                    {
                        float Remenig_fees=entryFee-userBonusInt;
                        float Totol_Balance=userBalanceInt-Remenig_fees;
                        Log.d("aaabonus","00");
                        Log.d("Total_balance", String.valueOf(Totol_Balance));

                        bonusBalance.setText(String.valueOf("-" + userBonusInt));
                        toPay.setText(String.valueOf(Remenig_fees));

                        btnJoin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                float user_bonusUpdate = 00;
                                Call<ResponseBody> responseBodyCall = RetrofitClient
                                        .getInstance()
                                        .getApi()
                                        .updateTotal(Totol_Balance,user_bonusUpdate,user_id);

                                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            String s = response.body().string();

                                            if (s.equals("Amount Updated")) {
                                                float userBalanceInt = Float.parseFloat(userBalance);
                                                int match_number = getIntent().getIntExtra("match_number", 0);
                                                int first_prize = getIntent().getIntExtra("first_prize", 0);
                                                String pubg_name = getIntent().getStringExtra("pubg_name");

                                                if (userBalanceInt <= 0 || userBalanceInt < entryFee) {
                                                    Toast.makeText(PopUpBalanceActivity.this, "You don't have sufficient balance", Toast.LENGTH_SHORT).show();
                                                } else {

                                                    Toast.makeText(PopUpBalanceActivity.this, "You Have Successfully Joined Solo Contest", Toast.LENGTH_SHORT).show();

                                                    String chickenStatus = "";
                                                    int usersKill = 0;
                                                    int killPayment = 0;
                                                    int userPrize = 0;

                                                    Calendar calendar = Calendar.getInstance();
                                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                                    String time = format.format(calendar.getTime());

                                                    String date = DateFormat.getDateInstance().format(calendar.getTime());

                                                    String history = "You have successfully joined " + entryFee + "rs Contest";
                                                    String result = "Upcoming";
                                                    String joinedStatus = "Joined";

                                                    Call<ResponseBody> callInsert = RetrofitClient
                                                            .getInstance()
                                                            .getApi()
                                                            .userTransaction(pubg_name, history, date, time, first_prize, entryFee, match_idInt, result, usersKill, chickenStatus, killPayment, userPrize,joinedStatus);

                                                    callInsert.enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                            try {
                                                                String s = response.body().string();

                                                                Log.d("responseResponse", "" + s);

                                                                if (s.equals("success")) {

                                                                    Toast.makeText(PopUpBalanceActivity.this, "Congrats", Toast.LENGTH_SHORT).show();

                                                                    Intent intent = new Intent(PopUpBalanceActivity.this,MainActivity.class);
                                                                    startActivity(intent);

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
                                            } else {
                                                Toast.makeText(PopUpBalanceActivity.this, "Problem", Toast.LENGTH_SHORT).show();
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
                        });



                    }

                    if(userBonusInt==0)
                    {
                        float Total_balance=userBalanceInt-entryFee;
                        Log.d("Total_balance", String.valueOf(Total_balance));

                        btnJoin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                float user_bonusUpdate = 00;
                                Call<ResponseBody> responseBodyCall = RetrofitClient
                                        .getInstance()
                                        .getApi()
                                        .updateBalanceFloat(Total_balance,user_id);

                                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            String s = response.body().string();

                                            if (s.equals("Amount Updated")) {
                                                float userBalanceInt = Float.parseFloat(userBalance);
                                                int first_prize = getIntent().getIntExtra("first_prize", 0);
                                                String pubg_name = getIntent().getStringExtra("pubg_name");

                                                if (userBalanceInt <= 0 || userBalanceInt < entryFee) {
                                                    Toast.makeText(PopUpBalanceActivity.this, "You don't have sufficient balance", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    String chickenStatus = "";
                                                    int usersKill = 0;
                                                    int killPayment = 0;
                                                    int userPrize = 0;
                                                    String joinedStatus = "Joined";

                                                    Calendar calendar = Calendar.getInstance();
                                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                                    String time = format.format(calendar.getTime());

                                                    String date = DateFormat.getDateInstance().format(calendar.getTime());

                                                    String history = "You have successfully joined " + entryFee + "rs Contest";
                                                    String result = "Upcoming";

                                                    Call<ResponseBody> callInsert = RetrofitClient
                                                            .getInstance()
                                                            .getApi()
                                                            .userTransaction(pubg_name, history, date, time, first_prize, entryFee, match_idInt, result, usersKill, chickenStatus, killPayment, userPrize,joinedStatus);

                                                    callInsert.enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                            try {
                                                                String s = response.body().string();

                                                                Log.d("responseResponse", "" + s);

                                                                if (s.equals("success")) {

                                                                    Toast.makeText(PopUpBalanceActivity.this, "Congrats", Toast.LENGTH_SHORT).show();

                                                                    Intent intent = new Intent(PopUpBalanceActivity.this,MainActivity.class);
                                                                    startActivity(intent);
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
                                            } else {
                                                Toast.makeText(PopUpBalanceActivity.this, "Problem", Toast.LENGTH_SHORT).show();
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
                        });

                    }

                }
            }

            @Override
            public void onFailure(Call<List<UsersData>> call, Throwable t) {

            }
        });

    }
}
