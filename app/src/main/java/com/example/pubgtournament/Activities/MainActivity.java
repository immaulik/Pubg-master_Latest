package com.example.pubgtournament.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.pubgtournament.Adapters.soloAdapter;
import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.Tournament;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

//    TabLayout tabLayout;
//    ViewPager viewPager;

    private static final String shared_pref = "UserDetails";

    RecyclerView recyclerView;
    List<Tournament> tournamentList;
    soloAdapter adapter;
    String match_fee,first_prize,match_number,match_id;
    public String user_id, user_balance1,pubg_name,bonus,total;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        tabLayout = findViewById(R.id.tab_layout);
//        viewPager = findViewById(R.id.viewpager);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences(shared_pref,Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("user_id",null);
        user_balance1=sharedPreferences.getString("user_balance",null);
        pubg_name=sharedPreferences.getString("pubg_name",null);
        bonus=sharedPreferences.getString("bonus",null);
        String referCode = sharedPreferences.getString("refer_code",null);

        Log.d("user_id",user_id);
        Log.d("pubg_name",pubg_name);
        Log.d("user_balance",user_balance1);
        Log.d("bonus",bonus);
        Log.d("referCode",referCode);

        recyclerView=findViewById(R.id.mainMatchesRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        tournamentList=new ArrayList<>();

        swipeRefreshLayout=findViewById(R.id.soloSwipe);
        swipeRefreshLayout.setOnRefreshListener(MainActivity.this);

        Call<List<Tournament>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getMatches();

        call.enqueue(new Callback<List<Tournament>>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {

                List<Tournament> tournaments = response.body();

                for (Tournament tournament : tournaments){

                    match_id=tournament.getMatch_id();
                    match_fee=tournament.getMatch_fee();
                    first_prize=tournament.getFirst_prize();
                    match_number=tournament.getMatch_number();
                    total=tournament.getTotal();

                    Tournament tournament1 = new Tournament(match_id,match_fee,first_prize,match_number,total);
                    tournamentList.add(tournament1);
                }

                adapter = new soloAdapter(MainActivity.this,tournamentList,user_id,user_balance1,pubg_name,bonus,match_number);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.wallet:
                Intent intent = new Intent(MainActivity.this, WalletActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_profile:
                Intent intentProfile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;

            case R.id.log_out:
                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("pubg_name","hello");
                editor.commit();

                Intent intent = new Intent(MainActivity.this, LoginUser.class);
                startActivity(intent);
                finish();
                break;

            case R.id.my_contests:
                Intent contestIntent=new Intent(MainActivity.this,Joined_Contests.class);
                startActivity(contestIntent);
                break;

            case R.id.referAndEarn:
                Intent intent1 = new Intent(MainActivity.this,ReferAndEarn.class);
                startActivity(intent1);
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected void onStart() {
        Call<List<Transaction>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserByName(pubg_name);

        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                List<Transaction> transactions = response.body();

                for (Transaction transaction : transactions){
                    String status = transaction.getJoined_Status();
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });

        super.onStart();


    }
}
