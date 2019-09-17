package com.example.pubgtournament.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.UsersData;
import com.example.pubgtournament.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    TextView depositedAmount, withdrawAmount, bonusAmount;
    Button addMoney,btnWithdrawMoney,userBtnWithdraw;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;

    LinearLayout addMoneyLinear,withdrawMoneyLinear;

    String withdrawMoney, balance, bonusMoney;

    EditText amountEt, noteEt, nameEt, upiIdEt,userWithdraw;
    Button send;
    final int UPI_PAYMENT = 0;

    private static final String shared_pref="UserDetails";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet, container, false);

        depositedAmount = view.findViewById(R.id.deposited_amount);
        withdrawAmount = view.findViewById(R.id.winning_amount);
        bonusAmount = view.findViewById(R.id.bonus_amount);

        addMoneyLinear=view.findViewById(R.id.userAddMoneyLinear);
        withdrawMoneyLinear=view.findViewById(R.id.userWithdrawLinear);
        userBtnWithdraw=view.findViewById(R.id.withdrawSubmit);

        userWithdraw=view.findViewById(R.id.UserAmount);

        swipeRefreshLayout = view.findViewById(R.id.walletSwipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        btnWithdrawMoney=view.findViewById(R.id.withdraw_money);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(shared_pref,Context.MODE_PRIVATE);
        String pubg_name=sharedPreferences.getString("pubg_name",null);

        Log.d("pubg_nameWithdraw",""+pubg_name);

        retrofit2.Call<List<UsersData>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUsers(pubg_name);

        call.enqueue(new Callback<List<UsersData>>() {
            @Override
            public void onResponse(Call<List<UsersData>> call, retrofit2.Response<List<UsersData>> response) {

                List<UsersData> usersData = response.body();

                for (UsersData data : usersData) {
                    balance = data.getUserBalance();
                    withdrawMoney=data.getWithdrawBalance();
                    bonusMoney=data.getBonus();
                }
                Log.d("userBalanceSolo",""+ balance);
                Log.d("userBalanceSoloWith",""+ withdrawMoney);
                Log.d("userBalanceSolo",""+ bonusMoney);

                depositedAmount.setText(balance);
                withdrawAmount.setText(withdrawMoney);
                bonusAmount.setText(bonusMoney);

                int amt=Integer.parseInt(withdrawMoney);

                Log.d("amt",""+amt);

                if (amt > 150) {
                    btnWithdrawMoney.setEnabled(true);
                }

                userBtnWithdraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userWithdrawBalance=userWithdraw.getText().toString();
                        int balanceWithdraw=Integer.parseInt(userWithdrawBalance);

                        int totalWithdraw = amt - balanceWithdraw;
                        Log.d("totalWithdraw",""+totalWithdraw);

                        userWithdraw.setText("");

                        Call<ResponseBody> callWithdraw = RetrofitClient
                                .getInstance()
                                .getApi()
                                .withdrawRequest(pubg_name,balanceWithdraw);

                        callWithdraw.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String s=response.body().string();

                                    if (s.equals("inserted...")){
                                        Toast.makeText(getContext(), "Successfully Requested", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

                        withdrawAmount.setText(String.valueOf(totalWithdraw));
                        //Toast.makeText(getContext(), "Withdraw Request Has Been Placed", Toast.LENGTH_SHORT).show();

                        btnWithdrawMoney.setEnabled(false);
                        withdrawMoneyLinear.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<UsersData>> call, Throwable t) {

            }
        });

        initializeViews();

//        String amt= withdrawAmount.getText().toString();
//        Log.d("amt",""+amt);
//        Log.d("withDrawAmt",""+withdrawMoney);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                String amount = amountEt.getText().toString();
                String note = noteEt.getText().toString();
                String name = nameEt.getText().toString();
                String upiId = upiIdEt.getText().toString();
                payUsingUpi(amount, upiId, name, note);
            }
        });

        btnWithdrawMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (addMoneyLinear.getVisibility() == View.VISIBLE){

                    addMoneyLinear.setVisibility(View.GONE);
                }
                withdrawMoneyLinear.setVisibility(View.VISIBLE);
            }
        });

        addMoney = view.findViewById(R.id.add_money);

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoneyLinear.setVisibility(View.VISIBLE);
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
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.detach(WalletFragment.this).attach(WalletFragment.this).commit();
            }
        }, 1000);
    }

    void initializeViews() {
        send = view.findViewById(R.id.send);
        amountEt = view.findViewById(R.id.amount_et);
        noteEt = view.findViewById(R.id.note);
        nameEt = view.findViewById(R.id.name);
        upiIdEt = view.findViewById(R.id.upi_id);
    }

    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if (null != chooser.resolveActivity(getActivity().getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(getActivity(), "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(getActivity())) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(getActivity(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: " + approvalRefNo);

                String amount = amountEt.getText().toString();
                int amount1 = Integer.parseInt(amount);
                int b1 = Integer.parseInt(balance);
                int b = b1 + amount1;
                Log.d("finalAmount", "" + b);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("user_balance", b);
                editor.apply();

            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(getActivity(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
