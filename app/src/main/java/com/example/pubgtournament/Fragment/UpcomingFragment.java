package com.example.pubgtournament.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pubgtournament.Adapters.upcomingAdapter;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    upcomingAdapter adapter;
    String result = "Upcoming";
    String dateRetofit, pubg_name, t_id, timing, first_prize, per_kill, history, entry_fee, match_version, map_type, match_type, match_number;
    RecyclerView recyclerView;
    List<Transaction> listTransactions;
    SwipeRefreshLayout swipeRefreshLayout;
    String chickenStatus="";
    String usersKill="";
    String killPayment="";
    String usersPrize="";

    private static final String shared_pref="UserDetails";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentTime = "18:26:23";

        recyclerView = view.findViewById(R.id.myContestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listTransactions = new ArrayList<>();

        swipeRefreshLayout=view.findViewById(R.id.upcomingSwipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        String date = sdf.format(calendar.getTime());

        Log.d("time", currentTime);
        Log.d("date", date);

        SharedPreferences sharedPreferences=getContext().getSharedPreferences(shared_pref, Context.MODE_PRIVATE);
        String name=sharedPreferences.getString("pubg_name",null);

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "", "Loading...");
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://darshansoni0902.000webhostapp.com/PubgTournament/userTransaction.php?pubg_name=" + name+"&result="+result;
        Log.d("url1234", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                pubg_name = jsonObject.getString("pubg_name");
                                t_id = jsonObject.getString("t_id");
                                dateRetofit = jsonObject.getString("date");
                                timing = jsonObject.getString("timing");
                                history = jsonObject.getString("history");
                                entry_fee = jsonObject.getString("entry_fee");
                                first_prize = jsonObject.getString("first_prize");
                                match_number = jsonObject.getString("match_number");

                                Transaction tournament = new Transaction(t_id, pubg_name, history, dateRetofit, timing, first_prize,
                                        entry_fee, match_number, result,usersKill,killPayment,chickenStatus,usersPrize);

                                listTransactions.add(tournament);

                            }
                            adapter = new upcomingAdapter(getContext(), listTransactions);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        queue.add(stringRequest);

//        Call<List<Transaction>> call= RetrofitClient
//                .getInstance()
//                .getApi()
//                .getUserById(result);
//
//        call.enqueue(new Callback<List<Transaction>>() {
//
//            @Override
//            public void onResponse(Call<List<Transaction>> call, retrofit2.Response<List<Transaction>> response) {
//
//                List<Transaction> transactionList=response.body();
//
//                Log.d("transactionData", String.valueOf(transactionList));
//
//                for (Transaction transaction : transactionList){
//                    pubg_name=transaction.getPubgName();
//                    t_id=transaction.getTId();
//                    dateRetofit=transaction.getDate();
//                    timing=transaction.getTiming();
//                    history=transaction.getHistory();
//                    entry_fee=transaction.getEntryFee();
//                    first_prize=transaction.getFirstPrize();
//                    per_kill=transaction.getPerKill();
//                    map_type=transaction.getMapType();
//                    match_number=transaction.getMatchNumber();
//                    match_type=transaction.getMatchType();
//                    match_version=transaction.getMatchVersion();
//
//                    Log.d("UpcomingData",pubg_name+t_id);
//
//                }
//                Transaction transaction=new Transaction(t_id,pubg_name,history,dateRetofit,timing
//                ,first_prize,per_kill,entry_fee,match_version,map_type,match_type,match_number,result);
//
//                listTransactions.add(transaction);
//
//                upcomingAdapter upcomingAdapter=new upcomingAdapter(getContext(),listTransactions);
//                recyclerView.setAdapter(upcomingAdapter);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Transaction>> call, Throwable t) {
//
//            }
//        });

        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.detach(UpcomingFragment.this).attach(UpcomingFragment.this).commit();
            }
        },1000);
    }
}
