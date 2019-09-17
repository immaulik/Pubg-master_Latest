package com.example.pubgtournament.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pubgtournament.Fragment.ResultFragment;
import com.example.pubgtournament.Fragment.UpcomingFragment;

/**
 * Created by Darshan Soni on 03-Jun-19.
 */
public class myContestsAdapter extends FragmentPagerAdapter {

    public myContestsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                UpcomingFragment requestFragment=new UpcomingFragment();
                return requestFragment;

            case 1:
                ResultFragment chatsFragment=new ResultFragment();
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
                return "UPCOMING";
            case 1:
                return "RESULTS";
            default:
                return null;
        }

    }
}
