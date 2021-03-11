package com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.ui.settingprofileediting.SettingProfileEditingActivity;

public class ShopLandingActivity extends AppCompatActivity {

    NavHostFragment navHostFragment;
    ImageView setting, addProduct, arrowback;
    ImageView iv_close, iv_back_addproduct;
    Button getStarted_btn, uploadProduct;
    Dialog dialogCongratulation, dialogAddProduct;
    TextView productList, orderList, chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_landing);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        dialogCongratulation = new Dialog(ShopLandingActivity.this);
        dialogCongratulation.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogCongratulation.setContentView(R.layout.dialog_congratulation);
        dialogCongratulation.show();

        iv_close = dialogCongratulation.findViewById(R.id.iv_close);
        getStarted_btn = dialogCongratulation.findViewById(R.id.getStarted_btn);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCongratulation.dismiss();
            }
        });

        getStarted_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCongratulation.dismiss();
            }
        });


        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopLandingActivity.this, SettingProfileEditingActivity.class));
            }
        });

        addProduct = findViewById(R.id.addProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddProduct = new Dialog(ShopLandingActivity.this);
                dialogAddProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogAddProduct.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogAddProduct.setContentView(R.layout.dialog_add_product);

                iv_back_addproduct = dialogAddProduct.findViewById(R.id.iv_back);
                iv_back_addproduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAddProduct.dismiss();
                    }
                });

                uploadProduct = dialogAddProduct.findViewById(R.id.uploadProduct);
                uploadProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAddProduct.dismiss();
                        //startActivity(new Intent(ShopLandingActivity.this,ShopLandingActivity.class));
                        navController.navigate(R.id.shopLandingFragment);
                    }
                });

                Window window = dialogAddProduct.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                window.setAttributes(wlp);
                dialogAddProduct.show();
            }
        });

        productList = findViewById(R.id.productList);
        productList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   productList.setTextColor(Color.WHITE);
                productList.setBackground(getResources().getDrawable(R.drawable.bg_text_left_rounded));

                orderList.setTextColor(Color.BLACK);
                orderList.setBackgroundColor(Color.WHITE);

                chat.setTextColor(Color.BLACK);
                chat.setBackgroundColor(Color.WHITE);*/

                navController.navigate(R.id.shopLandingFragment);
            }
        });

        orderList = findViewById(R.id.orderList);
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* productList.setTextColor(Color.WHITE);
                productList.setBackground(getResources().getDrawable(R.drawable.bg_text_left_rounded));

                orderList.setTextColor(Color.BLACK);
                orderList.setBackgroundColor(Color.WHITE);

                chat.setTextColor(Color.BLACK);
                chat.setBackgroundColor(Color.WHITE);*/


                navController.navigate(R.id.orderListFragment);
            }
        });

        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  productList.setTextColor(Color.WHITE);
                productList.setBackgroundColor(Color.WHITE);

                orderList.setTextColor(Color.BLACK);
                orderList.setBackgroundColor(Color.WHITE);

                chat.setTextColor(Color.BLACK);
                chat.setBackground(getResources().getDrawable(R.drawable.bg_text_right_rounded));
*/
                navController.navigate(R.id.chat);
            }
        });

        arrowback = findViewById(R.id.arrowback);
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}