package com.tilismtech.tellotalk_shopping_sdk.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tilismtech.tellotalk_shopping_sdk.R;

public class NoInternetDetection {

    Activity activity;
    Dialog dialog;


    public NoInternetDetection(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    public void showDialog() {

        try {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_no_internet_detection);
            Button btn = dialog.findViewById(R.id.getStarted_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            ImageView imageView = dialog.findViewById(R.id.iv_close);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception io) {
            io.printStackTrace();
        }
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
