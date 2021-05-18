package com.tilismtech.tellotalk_shopping_sdk.ui_seller.settingprofileediting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tilismtech.tellotalk_shopping_sdk.R;

public class SettingProfileEditingActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout personalInfoRL, storeSettingRL, bankRL;
    NavHostFragment navHostFragment;
    NavController navController;
    View horizontalLine1, horizontalLine2, horizontalLine3;
    TextView tv_storesettings, tv_bank, tv_personal;
    com.google.android.material.tabs.TabLayout tab1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile_editing);
        initViews();
    }

    public void initViews() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        personalInfoRL = findViewById(R.id.personalInfoRL);
        storeSettingRL = findViewById(R.id.storeSettingRL);
        bankRL = findViewById(R.id.bankRL);


        horizontalLine1 = findViewById(R.id.horizontalLine1);
        horizontalLine2 = findViewById(R.id.horizontalLine2);
        horizontalLine3 = findViewById(R.id.horizontalLine3);

        tv_storesettings = findViewById(R.id.tv_storesettings);
        tv_bank = findViewById(R.id.tv_bank);
        tv_personal = findViewById(R.id.tv_personal);

        personalInfoRL.setOnClickListener(this);
        storeSettingRL.setOnClickListener(this);
        bankRL.setOnClickListener(this);

        horizontalLine1.setOnClickListener(this);
        horizontalLine2.setOnClickListener(this);
        horizontalLine3.setOnClickListener(this);

        tv_storesettings.setOnClickListener(this);
        storeSettingRL.setOnClickListener(this);
        bankRL.setOnClickListener(this);

        tab1 = findViewById(R.id.tab1);
        tab1.getTabAt(0).select();
        tab1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        navController.navigate(R.id.storeSettingFragment);
                        break;
                    case 1:
                        navController.navigate(R.id.bankSettingFragment);
                        break;
                }
              /*  if (tab.getPosition() == 0) {
                    navController.navigate(R.id.storeSettingFragment);
                } else if (tab.getPosition() == 1) {
                    navController.navigate(R.id.bankSettingFragment);
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.personalInfoRL) {
           // navController.navigate(R.id.editingProfileFragment);

            tv_personal.setTextColor(Color.parseColor("#50D4BF"));
            tv_storesettings.setTextColor(Color.parseColor("#000000"));
            tv_bank.setTextColor(Color.parseColor("#000000"));

            horizontalLine2.setVisibility(View.GONE);
            horizontalLine1.setVisibility(View.VISIBLE);
            horizontalLine3.setVisibility(View.GONE);

        } else if (v.getId() == R.id.storeSettingRL) {
            navController.navigate(R.id.storeSettingFragment);
            Toast.makeText(this, "clicked...", Toast.LENGTH_SHORT).show();

            tv_personal.setTextColor(Color.parseColor("#000000"));
            tv_storesettings.setTextColor(Color.parseColor("#50D4BF"));
            tv_bank.setTextColor(Color.parseColor("#000000"));

            horizontalLine2.setVisibility(View.VISIBLE);
            horizontalLine1.setVisibility(View.GONE);
            horizontalLine3.setVisibility(View.GONE);

        } else if (v.getId() == R.id.bankRL) {
            navController.navigate(R.id.bankSettingFragment);

            tv_personal.setTextColor(Color.parseColor("#000000"));
            tv_storesettings.setTextColor(Color.parseColor("#000000"));
            tv_bank.setTextColor(Color.parseColor("#50D4BF"));

            horizontalLine2.setVisibility(View.GONE);
            horizontalLine1.setVisibility(View.GONE);
            horizontalLine3.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}