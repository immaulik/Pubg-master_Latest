package com.example.pubgtournament.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.List;

/**
 * Created by Darshan Soni on 30-Jun-19.
 */
public class transactionAdapter extends RecyclerView.Adapter<transactionAdapter.transactionViewHolder> {

    Context context;
    List<Transaction> listTransactions;

    public transactionAdapter(Context context, List<Transaction> listTransactions) {
        this.context=context;
        this.listTransactions=listTransactions;
    }

    @NonNull
    @Override
    public transactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.transactionitemfile,viewGroup,false);
        return new transactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull transactionViewHolder holder, int i) {

        Transaction transaction=listTransactions.get(i);

        holder.histroyTextView.setText(transaction.getHistory());
        holder.dateTextView.setText(transaction.getDate());
        holder.timeTextView.setText(transaction.getTiming());
    }

    @Override
    public int getItemCount() {
        return listTransactions.size();
    }

    public class transactionViewHolder extends RecyclerView.ViewHolder{

        TextView histroyTextView,dateTextView,timeTextView;

        public transactionViewHolder(@NonNull View itemView) {
            super(itemView);

            histroyTextView=itemView.findViewById(R.id.transactionHistory);
            dateTextView=itemView.findViewById(R.id.transactionDate);
            timeTextView=itemView.findViewById(R.id.transactionTime);
        }
    }

}
