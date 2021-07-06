package com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.receiver.NetworkReceiver;
import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;
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

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        NoInternetDetection loadingDialog = new NoInternetDetection(this);
        networkReceiver = new NetworkReceiver(loadingDialog);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);


    }


}