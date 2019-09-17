package com.example.pubgtournament.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pubgtournament.Activities.DuoMatchInfo;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.List;

/**
 * Created by Darshan Soni on 30-Jun-19.
 */
public class joinedDuoUserAdapter extends RecyclerView.Adapter<joinedDuoUserAdapter.transactionViewHolder> {

    DuoMatchInfo duoMatchInfo;
    List<Transaction> transactionList;

    public joinedDuoUserAdapter(DuoMatchInfo duoMatchInfo, List<Transaction> transactionList) {
        this.duoMatchInfo=duoMatchInfo;
        this.transactionList=transactionList;
    }

    @NonNull
    @Override
    public transactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(duoMatchInfo);
        View view= layoutInflater.inflate(R.layout.joinedduouseritemfile,viewGroup,false);
        return new transactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull transactionViewHolder holder, int i) {

        Transaction transaction=transactionList.get(i);

        holder.joinedUserTextView.setText(transaction.getPubgName());

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class transactionViewHolder extends RecyclerView.ViewHolder{

        TextView joinedUserTextView;

        public transactionViewHolder(@NonNull View itemView) {
            super(itemView);

            joinedUserTextView=itemView.findViewById(R.id.joined_duo_user_name);

        }
    }

}
