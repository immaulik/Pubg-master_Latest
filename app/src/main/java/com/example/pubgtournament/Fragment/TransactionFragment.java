package com.example.pubgtournament.Fragment;


import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pubgtournament.Adapters.resultAdapter;
import com.example.pubgtournament.Adapters.transactionAdapter;
import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    RecyclerView transactionView;
    String dateRetofit, pubg_name2, t_id, timing, first_prize, per_kill, history, entry_fee, match_version, map_type, match_type, match_number,result;
    private static final String shared_pref="UserDetails";
    SwipeRefreshLayout swipeRefreshLayout;
    List<Transaction> listTransactions;
    String chickenStatus="";
    String usersKill="";
    String killPayment="";
    String usersPrize="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_transaction, container, false);

        transactionView=view.findViewById(R.id.recyclerTransaction);
        transactionView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout=view.findViewById(R.id.transactionSwipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        listTransactions = new ArrayList<>();

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(shared_pref,Context.MODE_PRIVATE);
        String pubg_name=sharedPreferences.getString("pubg_name",null);

        Log.d("TransctionPubgName",""+pubg_name);


        Call<List<Transaction>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserByName(pubg_name);

        call.enqueue(new Callback<List<Transaction>>() {
            @SuppressLint("CommitPrefEdits")

            @Override
            public void onResponse(Call<List<Transaction>> call, retrofit2.Response<List<Transaction>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), (CharSequence) response.body(), Toast.LENGTH_SHORT).show();
                }else {

                    List<Transaction> transactionList = response.body();

                Log.d("transactionData", String.valueOf(transactionList));

                for (Transaction transaction : transactionList) {
                    pubg_name2 = transaction.getPubgName();
                    t_id=transaction.gettId();
                    dateRetofit=transaction.getDate();
                    timing=transaction.getTiming();
                    history=transaction.getHistory();
                    entry_fee=transaction.getEntryFee();
                    first_prize=transaction.getFirstPrize();
                    match_number=transaction.getMatchNumber();
                    result=transaction.getResults();

                    Transaction tournament = new Transaction(t_id, pubg_name, history, dateRetofit, timing, first_prize,
                             entry_fee ,match_number,result,usersKill,killPayment,chickenStatus,usersPrize);

                    listTransactions.add(tournament);

                }

                    transactionAdapter adapter = new transactionAdapter(getContext(), listTransactions);
                    transactionView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }

        });

        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.detach(TransactionFragment.this).attach(TransactionFragment.this).commit();
            }
        },1000);
    }
}
