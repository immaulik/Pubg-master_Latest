package com.example.pubgtournament.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubgtournament.Activities.PopUpBalanceActivity;
import com.example.pubgtournament.Activities.SoloMatchInfo;
import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.Tournament;
import com.example.pubgtournament.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Darshan Soni on 13-Jun-19.
 */
public class soloAdapter extends RecyclerView.Adapter<soloAdapter.soloViewHolder> {

    Context context;
    List<Tournament> tournamentList;
    String user_id;
    String user_balance1;
    String pubg_name;
    String bonus;
    private static final String shared_pref = "UserDetails";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int num;
    String match_number;

    public soloAdapter(Context context, List<Tournament> tournamentList, String user_id, String user_balance1, String pubg_name, String bonus, String match_number) {

        this.context = context;
        this.tournamentList = tournamentList;
        this.user_id = user_id;
        this.pubg_name = pubg_name;
        this.user_balance1 = user_balance1;
        this.bonus = bonus;
        this.match_number = match_number;
    }

    @NonNull
    @Override
    public soloViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.soloitemfile, viewGroup, false);
        sharedPreferences = context.getSharedPreferences(shared_pref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new soloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull soloViewHolder holder, int i) {

        holder.simpleSeekBar.setClickable(false);
        holder.simpleSeekBar.setEnabled(false);
        final Tournament tournament = tournamentList.get(i);
        holder.simpleSeekBar.setProgress(num);

        holder.first_prize.setText(String.valueOf(tournament.getFirst_prize()));
        holder.entry_fee.setText(String.valueOf(tournament.getMatch_fee()));
        holder.match_number.setText(String.valueOf(tournament.getMatch_number()));

        String Total=String.valueOf(tournament.getTotal());
        Log.d("Totalaa",Total);

        if(Integer.parseInt(Total)>=8)
        {
            holder.simpleSeekBar.setProgress(80);
            holder.btnSolo.setEnabled(false);
        }
        else
        {
            holder.simpleSeekBar.setProgress(Integer.parseInt(Total)*10);
            holder.btnSolo.setEnabled(true);
        }

        holder.btnSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float fee = Float.parseFloat(user_balance1);
                String entry = holder.entry_fee.getText().toString();
                int e = Integer.parseInt(entry);

                String first_prize = tournament.getFirst_prize();
                String entry_fee = tournament.getMatch_fee();
                String match_number = tournament.getMatch_number();

//                int a1=Integer.parseInt(user_balance1);
//                int user_balance=a1-fee;
//                Log.d("entry_fee",""+fee);
//                Log.d("user_balance",""+user_balance1);
//                Log.d("a1solosololosososloe",""+a1);

                int mNuber, fPrize, pKill, eFee;
                mNuber = Integer.parseInt(match_number);
                fPrize = Integer.parseInt(first_prize);

                Intent intent = new Intent(context, PopUpBalanceActivity.class);
                intent.putExtra("userBalanceInt", fee);
                intent.putExtra("entryFee", e);
                intent.putExtra("userbonus", Float.parseFloat(bonus));
                intent.putExtra("user_id", Integer.parseInt(user_id));
                intent.putExtra("first_prize", first_prize);
                intent.putExtra("pubg_name", pubg_name);
                intent.putExtra("user_balance", user_balance1);
                intent.putExtra("match_number",mNuber);
                intent.putExtra("first_prize",fPrize);
                intent.putExtra("match_number",match_number);
                context.startActivity(intent);




//                Log.d("userBalanceAdapter",""+user_balance1);
//
//                int fee=Integer.parseInt(user_balance1);
//                Log.d("userBalanceInt",""+ fee);
//
//                String entry=holder.entry_fee.getText().toString();
//
//                int e=Integer.parseInt(entry);
//                Log.d("entryFeeAdapter",""+e);
//
//
//                double res = (e / 100.0f) * 35;
//                Log.d("aa11", String.valueOf(res));
//                int updatebonus=0;
//
//                if(Integer.parseInt(bonus)>=(int)res)
//                {
//                    int a= (int) (Integer.parseInt(bonus)-res);
//                    Log.d("aa", String.valueOf(a));
//                    int b= (int) (e-res);
//                    Log.d("aa", String.valueOf(b));
//                    int c=fee-b;
//                    Log.d("aa", String.valueOf(c));
//                }
//                else if(Integer.parseInt(bonus)<=res)
//                {
//                    int a= (int) (res-Integer.parseInt(bonus));
//                    Log.d("aa", String.valueOf(a));
//                    int b= (int) (fee-a);
//                    Log.d("aa", String.valueOf(b));
//                }
//                else if(Integer.parseInt(bonus)==0)
//                {
//                    int a=fee-e;
//                    Log.d("aa", String.valueOf(a));
//                }
//                Log.d("aabonus",String.valueOf(res));
//
//                int updated=fee-e;
//                Log.d("updatedBalance",""+updated);
//
//
////
//
//                if (fee<=0 || fee<e){
//                    Toast.makeText(context, "You don't have sufficient balance", Toast.LENGTH_SHORT).show();
//                }else {
//
//                    holder.btnSolo.setEnabled(false);
//
//                    Toast.makeText(context, "You Have Successfully Joined Solo Contest", Toast.LENGTH_SHORT).show();
//
//
//
//                    Log.d("firstPrize",""+first_prize);
//
//                    String chickenStatus="";
//                    int usersKill=0;
//                    int killPayment=0;
//                    int userPrize=0;
//                    int id=Integer.parseInt(user_id);
//
//                    int mNuber,fPrize,pKill,eFee;
//                    mNuber=Integer.parseInt(match_number);
//                    fPrize=Integer.parseInt(first_prize);
//                    eFee=Integer.parseInt(entry_fee);
//
//                    Calendar calendar=Calendar.getInstance();
//                    SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
//                    String time=format.format(calendar.getTime());
//
//                    String date= DateFormat.getDateInstance().format(calendar.getTime());
//
//
//
//                    editor.putString("user_balance", String.valueOf(updated));
//                    editor.apply();
//
//                    String balance = sharedPreferences.getString("user_balance", null);
//                    Log.d("user_updated", balance);
//
//                    String history="You have successfully joined "+e+"rs Contest";
//                    String result="Upcoming";
//
//                    Call<ResponseBody> call =  RetrofitClient
//                            .getInstance()
//                            .getApi()
//                            .userTransaction(pubg_name,history,date,time,fPrize, eFee, mNuber,result,usersKill,chickenStatus,killPayment,userPrize);
//
//                    call.enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                            try {
//                                String s=response.body().string();
//
//                                Log.d("responseResponse",""+s);
//
//                                if (s.equals("Data Submit Successfully")){
//
//                                    Call<ResponseBody> callUpdated=RetrofitClient
//                                            .getInstance()
//                                            .getApi()
//                                            .updateBalance(updated,id);
//
//                                    callUpdated.enqueue(new Callback<ResponseBody>() {
//                                        @Override
//                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                            String sUpdated= null;
//                                            try {
//                                                sUpdated = response.body().string();
//                                                Log.d("responseUpdated",""+sUpdated);
//                                            } catch (IOException e1) {
//                                                e1.printStackTrace();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                                        }
//                                    });
//
//                                }
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                        }
//                    });
//                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SoloMatchInfo.class);
                intent.putExtra("first_prize", tournament.getFirst_prize());
                intent.putExtra("entry_fee", tournament.getMatch_fee());
                intent.putExtra("match_number", tournament.getMatch_number());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public class soloViewHolder extends RecyclerView.ViewHolder {

        TextView first_prize, entry_fee, match_version, match_number;
        Button btnSolo;
        SeekBar simpleSeekBar;

        public soloViewHolder(@NonNull View itemView) {
            super(itemView);

            first_prize = itemView.findViewById(R.id.solo_first_prize);
            entry_fee = itemView.findViewById(R.id.solo_entry_fee);
            match_version = itemView.findViewById(R.id.solo_match_version);
            match_number = itemView.findViewById(R.id.solo_match_number);

            btnSolo = itemView.findViewById(R.id.btn_solo);
            simpleSeekBar = (SeekBar) itemView.findViewById(R.id.simpleSeekBar);

        }
    }

}
