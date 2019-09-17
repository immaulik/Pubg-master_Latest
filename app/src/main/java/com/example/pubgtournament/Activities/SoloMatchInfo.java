package com.example.pubgtournament.Activities;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubgtournament.Adapters.joinedSoloUserAdapter;
import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.Tournament;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoloMatchInfo extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    TextView entryFee,firstPrize,matchVersion,matchNumber;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_match_info);

        toolbar=findViewById(R.id.solo_user_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Solo Match Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        entryFee=findViewById(R.id.solo_user_entry_fee);
        firstPrize=findViewById(R.id.solo_user_first_prize);
        matchVersion=findViewById(R.id.solo_user_match_version);
        matchNumber=findViewById(R.id.solo_info_match_number);

        swipeRefreshLayout=findViewById(R.id.soloMatchSwipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView=findViewById(R.id.solo_joined_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String prize=getIntent().getStringExtra("first_prize");
        String fee=getIntent().getStringExtra("entry_fee");
        Log.d("fee",""+fee);
        String numberIntent=getIntent().getStringExtra("match_number");
        String version;
        version=getIntent().getStringExtra("match_version");

        int number = Integer.parseInt(numberIntent);

        entryFee.setText(String.valueOf(fee));
        firstPrize.setText(String.valueOf(prize));
        matchVersion.setText(version);
        matchNumber.setText(String.valueOf(number));

        Call<List<Transaction>> call =  RetrofitClient
                .getInstance()
                .getApi()
                .getJoinedUsers2(number);


        call.enqueue(new Callback<List<Transaction>>() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SoloMatchInfo.this, (CharSequence) response.body(), Toast.LENGTH_SHORT).show();
                }else {

                    List<Transaction> transactionList= response.body();

                    joinedSoloUserAdapter adapter = new joinedSoloUserAdapter(SoloMatchInfo.this, transactionList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }

        });

    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
