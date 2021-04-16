package com.tilismtech.tellotalk_shopping_sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tilismtech.tellotalk_shopping_sdk.managers.TelloApiClient;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.ui.shopregistration.ShopRegistrationActivity;

public class MainActivity extends AppCompatActivity {

    Button button;
    AccessTokenPojo accessTokenPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accessTokenPojo = new AccessTokenPojo();

        //user name + password + grant type always remain same other will change...
        accessTokenPojo.setUsername("Basit@tilismtech.com");
        accessTokenPojo.setPassword("basit@1234");
        accessTokenPojo.setGrant_type("password");
        accessTokenPojo.setprofileId("3F64D77CB1BA4A3CA6CF9B9D786D4A43");
        accessTokenPojo.setFirstname("Hasan");
        accessTokenPojo.setMiddlename("Muddassir");
        accessTokenPojo.setLastname("Naqvi");
        accessTokenPojo.setPhone("03330347473");
        accessTokenPojo.setEmail("emai@gmail.com");

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelloApiClient telloApiClient = TelloApiClient.getInstance();
                telloApiClient.generateAccessToken(accessTokenPojo, MainActivity.this);
                startActivity(new Intent(MainActivity.this, ShopRegistrationActivity.class));
            }
        });

    }
}