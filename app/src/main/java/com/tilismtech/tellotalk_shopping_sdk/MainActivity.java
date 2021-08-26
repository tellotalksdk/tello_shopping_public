package com.tilismtech.tellotalk_shopping_sdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
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
import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;
import com.tilismtech.tellotalk_shopping_sdk.utils.Utility;

public class MainActivity extends AppCompatActivity {

    Button button, client;
    EditText fN, mN, lN, cN, pI;
    com.google.android.material.switchmaterial.SwitchMaterial toggle;
    LoadingDialog loadingDialog;

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
        loadingDialog = new LoadingDialog(MainActivity.this);


        button = findViewById(R.id.button);
        client = findViewById(R.id.client);
        //03152612485
        //azan id  : 3F64D77CB1BA4A3CA6CF9B9D786D4A234567


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "" + ApplicationUtils.changeNumberFormat(cN.getText().toString(), true), Toast.LENGTH_SHORT).show();


                    loadingDialog.showDialog();
                    TelloApiClient.initializeShoppingSDK(MainActivity.this, cN.getText().toString(), fN.getText().toString(), mN.getText().toString(), lN.getText().toString(), cN.getText().toString(), "Faiz@gmail.com", loadingDialog);

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

    private boolean checkValidation() {

        if (TextUtils.isEmpty(fN.getText().toString())) {
            Toast.makeText(this, "Name is empty...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(cN.getText().toString())) {
            Toast.makeText(this, "Phone Number is empty...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (correctMobile(cN.getText().toString()) == false) {
            Toast.makeText(this, "Mobile Number is Incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (cN.length() != 11) {
            Toast.makeText(this, "Please Dial an valid number...", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private boolean correctMobile(String mobileNumber) {

        if (mobileNumber.charAt(0) != '0') {
            return false;
        }


        if (mobileNumber.charAt(1) != '3') {
            return false;
        }

        if (mobileNumber.charAt(2) != '0') {
            return false;
        }

        if (mobileNumber.charAt(2) != '1') {
            return false;
        }

        if (mobileNumber.charAt(2) != '2') {
            return false;
        }

        if (mobileNumber.charAt(2) != '3') {
            return false;
        }

        if (mobileNumber.charAt(2) != '4') {
            return false;
        }


//        if (mobileNumber.charAt(2) != '0' ||
//                mobileNumber.charAt(2) != '1' ||
//                mobileNumber.charAt(2) != '2' ||
//                mobileNumber.charAt(2) != '3' ||
//                mobileNumber.charAt(2) != '4'
//
//        ) {
//            return false;
//        }else{
//            return true;
//        }

        return true;

    }


}