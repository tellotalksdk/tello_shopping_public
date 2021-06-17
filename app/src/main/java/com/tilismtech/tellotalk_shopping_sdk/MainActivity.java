package com.tilismtech.tellotalk_shopping_sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tilismtech.tellotalk_shopping_sdk.listeners.OnSuccessListener;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloApiClient;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GenerateToken;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GTResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui_client.homescreen.ClientHomeActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage.ShopLandingActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration.ShopRegistrationActivity;

public class MainActivity extends AppCompatActivity {

    Button button, client;
    EditText fN, mN, lN, cN, pI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fN = findViewById(R.id.fName);
        mN = findViewById(R.id.mName);
        lN = findViewById(R.id.lName);
        cN = findViewById(R.id.contact);
        pI = findViewById(R.id.profileid);


        button = findViewById(R.id.button);
        client = findViewById(R.id.client);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelloApiClient.initializeShoppingSDK(MainActivity.this, "3F64D77CB1BA4A3CA6CF9B9D786D4A43", "Ali", "Mehdi", "Rizvi", "03330347473", "Mehdi2399@gmail.com");
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ClientHomeActivity.class));
            }
        });

    }
}