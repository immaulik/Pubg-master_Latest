package com.example.pubgtournament.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pubgtournament.Fragment.TransactionFragment;
import com.example.pubgtournament.Fragment.WalletFragment;

/**
 * Created by Darshan Soni on 03-Jun-19.
 */
public class walletpagerAdapter extends FragmentPagerAdapter {

    public walletpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                WalletFragment requestFragment=new WalletFragment();
                return requestFragment;

            case 1:
                TransactionFragment chatsFragment=new TransactionFragment();
                return chatsFragment;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0:
                return "Wallet";
            case 1:
                return "Transaction";
            default:
                return null;
        }

    }
}
