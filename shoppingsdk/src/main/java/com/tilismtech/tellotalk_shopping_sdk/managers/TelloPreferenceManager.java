package com.tilismtech.tellotalk_shopping_sdk.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class TelloPreferenceManager {
    private static TelloPreferenceManager instance;
    private static final String PREFRENSE_NAME = "Tello_Pref";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REGISTER_NUMBER = "register_number";
    public static final String PROFILE_ID = "profile_id";
    public static final String SHOP_URI = "shop_uri";
    public static final String OWNER_NAME = "owner_name";
    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public static TelloPreferenceManager getInstance(Context myCtx) {
        if (instance == null) {
            instance = new TelloPreferenceManager(myCtx);
        }
        return instance;
    }

    private TelloPreferenceManager(Context myCtx) {
        sharedPreferences = myCtx.getSharedPreferences(PREFRENSE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //access token
    public void saveAccessToken(String access_token) {
        editor.putString(ACCESS_TOKEN, access_token);
        editor.apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, "");
    }

    //owner_name
    public void saveOwnerName(String ownerName) {
        editor.putString(OWNER_NAME, ownerName);
        editor.apply();
    }

    public String getOwnerName() {
        return sharedPreferences.getString(OWNER_NAME, "");
    }



    //registernumber
    public void saveRegisteredNumber(String registerNumber) {
        editor.putString(REGISTER_NUMBER, registerNumber);
        editor.apply();
    }

    public String getRegisteredNumber() {
        return sharedPreferences.getString(REGISTER_NUMBER, "");
    }

    //shop url
    public void saveShopURI(String shop_url) {
        editor.putString(SHOP_URI, shop_url);
        editor.apply();
    }

    public String getShopUri() {
        return sharedPreferences.getString(SHOP_URI, "");
    }

    void clearAll() {
        editor.clear().apply();
    }

}
