package com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.receiver.NetworkReceiver;
import com.tilismtech.tellotalk_shopping_sdk.utils.NoInternetDetection;

public class ShopRegistrationActivity extends AppCompatActivity {
    NavHostFragment navHostFragment;
    private ShopRegistrationViewModel shopRegistrationViewModel;
    AccessTokenPojo accessTokenPojo;
    NetworkReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_registration);
        Fresco.initialize(this);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        Intent intent = getIntent();
        boolean settingDone = intent.getBooleanExtra("settingDone",true);
        boolean shopExistence = intent.getBooleanExtra("shopExistance",true);

        if(settingDone == false && shopExistence == true){
            navController.navigate(R.id.action_shopRegistrationFragment_to_shopSettingFragment);
        }else if(settingDone == false && shopExistence==false){
            navController.navigate(R.id.shopRegistrationFragment);
        }


        NoInternetDetection loadingDialog = new NoInternetDetection(this);
        networkReceiver = new NetworkReceiver(loadingDialog);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);


    }


}