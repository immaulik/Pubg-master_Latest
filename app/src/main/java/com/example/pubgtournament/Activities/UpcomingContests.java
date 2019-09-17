package com.example.pubgtournament.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.pubgtournament.Adapters.joinedSquadUserAdapter;
import com.example.pubgtournament.Adapters.joinedUpcomingUserAdapter;
import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingContests extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_contests);

        toolbar=findViewById(R.id.upcomingToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("GET ID & PASSWORD");

        String number=getIntent().getStringExtra("match_number");
        Log.d("matchNumberUpcoming",""+number);

        recyclerView=findViewById(R.id.upcomingContestsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<List<Transaction>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getJoinedUsers(number);

        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UpcomingContests.this, (CharSequence) response.body(), Toast.LENGTH_SHORT).show();
                }else {

                    List<Transaction> transactionList= response.body();

                    joinedUpcomingUserAdapter adapter=new joinedUpcomingUserAdapter(UpcomingContests.this,transactionList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });
    }
}
