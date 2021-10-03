package com.tilismtech.tellotalk_shopping_sdk_app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class MyApplication extends Application {

    @Override
    public void attachBaseContext(Context context){
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

}
