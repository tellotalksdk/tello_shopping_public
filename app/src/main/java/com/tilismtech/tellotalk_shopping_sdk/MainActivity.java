package com.tilismtech.tellotalk_shopping_sdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.tilismtech.tellotalk_shopping_sdk.listeners.OnSuccessListener;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloApiClient;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GenerateToken;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GTResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui_client.homescreen.ClientHomeActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage.ShopLandingActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration.ShopRegistrationActivity;
import com.tilismtech.tellotalk_shopping_sdk.utils.ApplicationUtils;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.Utility;

public class MainActivity extends AppCompatActivity {

    Button button, client;
    EditText fN, mN, lN, cN, pI;
    com.google.android.material.switchmaterial.SwitchMaterial toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fN = findViewById(R.id.fName);
        mN = findViewById(R.id.mName);
        lN = findViewById(R.id.lName);
        cN = findViewById(R.id.contact);
        pI = findViewById(R.id.profileid);
        toggle = findViewById(R.id.toggle);


        button = findViewById(R.id.button);
        client = findViewById(R.id.client);
        //03152612485
        //azan id  : 3F64D77CB1BA4A3CA6CF9B9D786D4A234567


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "" + ApplicationUtils.changeNumberFormat(cN.getText().toString(), true), Toast.LENGTH_SHORT).show();

                String mobNumber = ApplicationUtils.changeNumberFormat(cN.getText().toString(),true);
                TelloApiClient.initializeShoppingSDK(MainActivity.this, mobNumber, fN.getText().toString(), mN.getText().toString(), lN.getText().toString(), mobNumber, "Faiz@gmail.com");
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ClientHomeActivity.class));
            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate
                                        .MODE_NIGHT_YES);
            }
        });

    }
}