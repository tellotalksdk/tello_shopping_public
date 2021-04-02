package com.tilismtech.tellotalk_shopping_sdk;

import android.app.Application;
import android.content.Context;

public class TelloApplication extends Application {

    public static TelloApplication instance;
    private static Application sApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sApplication = this;
    }

    public static TelloApplication getInstance() {
        return instance;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }


}
