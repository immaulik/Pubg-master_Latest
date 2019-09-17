package com.example.pubgtournament.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.pubgtournament.Adapters.joinedResultAdapter;
import com.example.pubgtournament.Adapters.joinedResultAdapter2;
import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultContests extends AppCompatActivity {

    RecyclerView recyclerView,winnerRecyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_contests);

        toolbar = findViewById(R.id.user_result_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Result Area");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.user_result_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        winnerRecyclerView=findViewById(R.id.user_result_recycler_winner);
        winnerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String number = getIntent().getStringExtra("match_number");
        Log.d("numberResult", number);

        String name = "Result";

        Call<List<Transaction>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getResultContest(number, name);

        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {

                List<Transaction> transactionList=response.body();

                joinedResultAdapter adapter=new joinedResultAdapter(ResultContests.this,transactionList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });

        String chickenStatus="Yes";

        Call<List<Transaction>> callStatus=RetrofitClient
                .getInstance()
                .getApi()
                .getKills(number,name,chickenStatus);

        callStatus.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                List<Transaction> list=response.body();

                joinedResultAdapter2 joinedResultAdapter2=new joinedResultAdapter2(ResultContests.this,list);
                winnerRecyclerView.setAdapter(joinedResultAdapter2);
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });

    }
}
