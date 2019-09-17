package com.example.pubgtournament.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pubgtournament.Activities.ResultContests;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.List;

/**
 * Created by Darshan Soni on 30-Jun-19.
 */
public class joinedResultAdapter2 extends RecyclerView.Adapter<joinedResultAdapter2.transactionViewHolder> {

    ResultContests resultContests;
    List<Transaction> list;

    public joinedResultAdapter2(ResultContests resultContests, List<Transaction> list) {
        this.resultContests = resultContests;
        this.list = list;
    }

    @NonNull
    @Override
    public transactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(resultContests);
        View view= layoutInflater.inflate(R.layout.joinedresultitemfile2,viewGroup,false);
        return new transactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull transactionViewHolder holder, int i) {

        Transaction transaction=list.get(i);

        holder.joinedUserTextView.setText(transaction.getPubgName());
        holder.joinedUserKillsTextView.setText(transaction.getUserPrize());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class transactionViewHolder extends RecyclerView.ViewHolder{

        TextView joinedUserTextView,joinedUserKillsTextView;

        public transactionViewHolder(@NonNull View itemView) {
            super(itemView);

            joinedUserTextView=itemView.findViewById(R.id.joined_user_name_winner);
            joinedUserKillsTextView=itemView.findViewById(R.id.joined_user_kill_winner);

        }
    }

}
