package com.tilismtech.tellotalk_shopping_sdk;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class TelloApplication extends MultiDexApplication {

    public static TelloApplication instance;
    private static Application sApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;
        sApplication = this;
    }

    public static TelloApplication getInstance() {
        if(instance == null){
            instance = new TelloApplication();
        }
        return instance;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }


}
