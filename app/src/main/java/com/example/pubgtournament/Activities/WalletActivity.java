package com.example.pubgtournament.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.pubgtournament.Adapters.walletpagerAdapter;
import com.example.pubgtournament.R;

public class WalletActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Toolbar toolbar = findViewById(R.id.toolbar_wallet);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wallet History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout=findViewById(R.id.tab_layout_wallet);
        viewPager=findViewById(R.id.viewpager_wallet);

        walletpagerAdapter pagerAdapter = new walletpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
