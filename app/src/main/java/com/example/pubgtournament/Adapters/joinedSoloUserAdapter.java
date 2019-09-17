package com.example.pubgtournament.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pubgtournament.Activities.SoloMatchInfo;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.List;

/**
 * Created by Darshan Soni on 30-Jun-19.
 */
public class joinedSoloUserAdapter extends RecyclerView.Adapter<joinedSoloUserAdapter.transactionViewHolder> {

    SoloMatchInfo soloMatchInfo;
    List<Transaction> pubg_name;

    public joinedSoloUserAdapter(SoloMatchInfo soloMatchInfo, List<Transaction> pubg_name) {

        this.soloMatchInfo=soloMatchInfo;
        this.pubg_name=pubg_name;

    }

    @NonNull
    @Override
    public transactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(soloMatchInfo);
        View view= layoutInflater.inflate(R.layout.joineduseritemfile,viewGroup,false);
        return new transactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull transactionViewHolder holder, int i) {

        Transaction transaction=pubg_name.get(i);

        holder.joinedUserTextView.setText(transaction.getPubgName());

    }

    @Override
    public int getItemCount() {
        return pubg_name.size();
    }

    public class transactionViewHolder extends RecyclerView.ViewHolder{

        TextView joinedUserTextView;

        public transactionViewHolder(@NonNull View itemView) {
            super(itemView);

            joinedUserTextView=itemView.findViewById(R.id.joined_user_name);

        }
    }

}
