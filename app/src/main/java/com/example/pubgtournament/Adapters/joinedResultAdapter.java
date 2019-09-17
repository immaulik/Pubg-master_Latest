package com.example.pubgtournament.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pubgtournament.Activities.ResultContests;
import com.example.pubgtournament.Activities.SoloMatchInfo;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.List;

/**
 * Created by Darshan Soni on 30-Jun-19.
 */
public class joinedResultAdapter extends RecyclerView.Adapter<joinedResultAdapter.transactionViewHolder> {

    ResultContests resultContests;
    List<Transaction> transactionList;

    public joinedResultAdapter(ResultContests resultContests, List<Transaction> transactionList) {

        this.resultContests=resultContests;
        this.transactionList=transactionList;

    }

    @NonNull
    @Override
    public transactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(resultContests);
        View view= layoutInflater.inflate(R.layout.joinedresultitemfile,viewGroup,false);
        return new transactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull transactionViewHolder holder, int i) {

        Transaction transaction=transactionList.get(i);

        holder.joinedUserTextView.setText(transaction.getPubgName());
        holder.joinedUserKillsTextView.setText(transaction.getUsersKill());
        holder.joinedUserKillsPrizeTextView.setText(transaction.getKillPayment());

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class transactionViewHolder extends RecyclerView.ViewHolder{

        TextView joinedUserTextView,joinedUserKillsTextView,joinedUserKillsPrizeTextView;

        public transactionViewHolder(@NonNull View itemView) {
            super(itemView);

            joinedUserTextView=itemView.findViewById(R.id.joined_user_name);
            joinedUserKillsTextView=itemView.findViewById(R.id.joined_user_kill);
            joinedUserKillsPrizeTextView=itemView.findViewById(R.id.joined_user_kill_prize);

        }
    }

}
