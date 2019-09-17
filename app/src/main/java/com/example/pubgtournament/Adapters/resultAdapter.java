package com.example.pubgtournament.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pubgtournament.Activities.ResultContests;
import com.example.pubgtournament.Activities.UpcomingContests;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.R;

import java.util.List;

/**
 * Created by Darshan Soni on 08-Jul-19.
 */
public class resultAdapter extends RecyclerView.Adapter<resultAdapter.upcomingViewHolder> {

    Context context;
    List<Transaction> listTransactions;

    public resultAdapter(Context context, List<Transaction> listTransactions) {
        this.context = context;
        this.listTransactions = listTransactions;
    }

    @NonNull
    @Override
    public upcomingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.resultitemfile,viewGroup,false);

        return new resultAdapter.upcomingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull upcomingViewHolder holder, int i) {
        final Transaction tournament=listTransactions.get(i);

        holder.first_prize_textView.setText(String.valueOf(tournament.getFirstPrize()));
        holder.entry_fee_textView.setText(String.valueOf(tournament.getEntryFee()));
        holder.match_number_textView.setText(String.valueOf(tournament.getMatchNumber()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ResultContests.class);
                intent.putExtra("match_number",tournament.getMatchNumber());
                intent.putExtra("pubg_name",tournament.getPubgName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTransactions.size();
    }

    public class upcomingViewHolder extends RecyclerView.ViewHolder{

        TextView first_prize_textView,entry_fee_textView,match_version_textView,map_type_textView,match_number_textView;

        public upcomingViewHolder(@NonNull View itemView) {
            super(itemView);

            first_prize_textView=itemView.findViewById(R.id.result_first_prize);
            entry_fee_textView=itemView.findViewById(R.id.result_entry_fee);
            match_version_textView=itemView.findViewById(R.id.result_match_version);
            match_number_textView=itemView.findViewById(R.id.result_match_number);

        }
    }

}
