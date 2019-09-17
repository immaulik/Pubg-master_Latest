package com.example.pubgtournament.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pubgtournament.Activities.SquadMatchInfo;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.List;

/**
 * Created by Darshan Soni on 30-Jun-19.
 */
public class joinedSquadUserAdapter extends RecyclerView.Adapter<joinedSquadUserAdapter.transactionViewHolder> {

    SquadMatchInfo squadMatchInfo;
    List<Transaction> transactionList;

    public joinedSquadUserAdapter(SquadMatchInfo squadMatchInfo, List<Transaction> transactionList) {

        this.squadMatchInfo=squadMatchInfo;
        this.transactionList=transactionList;

    }

    @NonNull
    @Override
    public transactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(squadMatchInfo);
        View view= layoutInflater.inflate(R.layout.joinedsquaduseritemfile,viewGroup,false);
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

            joinedUserTextView=itemView.findViewById(R.id.joined_squad_user_name);

        }
    }

}
