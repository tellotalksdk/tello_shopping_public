package com.tilismtech.tellotalk_shopping_sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tilismtech.tellotalk_shopping_sdk.managers.TelloApiClient;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.ui_client.homescreen.ClientHomeActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration.ShopRegistrationActivity;

public class MainActivity extends AppCompatActivity {

    Button button, client;
    AccessTokenPojo accessTokenPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*accessTokenPojo = new AccessTokenPojo();

        //user name + password + grant type always remain same other will change...
        accessTokenPojo.setUsername("Basit@tilismtech.com");
        accessTokenPojo.setPassword("basit@1234");
        accessTokenPojo.setGrant_type("password");

        accessTokenPojo.setprofileId("3F64D77CB1BA4A3CA6CF9B9D786D4A43");
        accessTokenPojo.setFirstname("Hasan");
        accessTokenPojo.setMiddlename("Muddassir");
        accessTokenPojo.setLastname("Naqvi");
        accessTokenPojo.setPhone("03330347473");
        accessTokenPojo.setEmail("emai@gmail.com");*/

        button = findViewById(R.id.button);
        client = findViewById(R.id.client);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  //TelloApiClient.initiateSDK();
                TelloApiClient telloApiClient = TelloApiClient.getInstance();
                GenerateToken generateToken = new GenerateToken();
                generateToken.setGrantUsername("Basit@tilismtech.com");
                generateToken.setGrantPassword("basit@1234");
                generateToken.setGrantType("password");
                generateToken.setProfileId("3F64D77CB1BA4A3CA6CF9B9D786D4A43");
                generateToken.setFirstname("Ali");
                generateToken.setMiddlename("Mehdi");
                generateToken.setLastname("Rizvi");
                generateToken.setPhone("03330347473");
                generateToken.setEmail("Mehdi2399@gmail.com");


                telloApiClient.generateTokenResponse(generateToken, MainActivity.this);
               // startActivity(new Intent(MainActivity.this, ShopRegistrationActivity.class));*/

                boolean gettingAhead = TelloApiClient.initializeShoppingSDK();
                if (gettingAhead) {
                    startActivity(new Intent(MainActivity.this, ShopRegistrationActivity.class));
                }
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