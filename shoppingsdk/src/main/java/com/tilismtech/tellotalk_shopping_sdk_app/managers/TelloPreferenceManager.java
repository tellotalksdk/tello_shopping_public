package com.tilismtech.tellotalk_shopping_sdk_app.managers;

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
    public static final String FIRST_NAME = "first_name";
    public static final String MIDDLE_NAME = "middle_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String CONGRATZ = "congrat_dialog";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

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

    //firstname
    public void saveFirstName(String firstName) {
        editor.putString(FIRST_NAME, firstName);
        editor.apply();
    }

    public String getFirstName() {
        return sharedPreferences.getString(FIRST_NAME, "");
    }

    //middle name
    public void saveMiddleName(String middleName) {
        editor.putString(MIDDLE_NAME, middleName);
        editor.apply();
    }

    public String getMiddleName() {
        return sharedPreferences.getString(MIDDLE_NAME, "");
    }

    //last name
    public void saveLastName(String lastName) {
        editor.putString(LAST_NAME, lastName);
        editor.apply();
    }

    public String getLastName() {
        return sharedPreferences.getString(LAST_NAME, "");
    }

    //email
    public void saveEmail(String email) {
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "");
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

    //tello user profile id
    public void saveProfileId(String profileID) {
        editor.putString(PROFILE_ID, profileID);
        editor.apply();
    }

    public String getProfileId() {
        return sharedPreferences.getString(PROFILE_ID, "");
    }

    //setdialog to show just once
    public void savecongratsStatus(boolean isFirstTime) {
        editor.putBoolean(CONGRATZ, isFirstTime);
        editor.apply();
    }

    public boolean getcongratsStatus() {
        return sharedPreferences.getBoolean(CONGRATZ, false);
    }


    public void savelatitude(String latitude) {
        editor.putString(LATITUDE, latitude);
        editor.apply();
    }

    public String getlatitude() {
        return sharedPreferences.getString(LATITUDE, "");
    }


    public void savelongitude(String longitude) {
        editor.putString(LONGITUDE, longitude);
        editor.apply();
    }

    public String getLongitude() {
        return sharedPreferences.getString(LONGITUDE, "");
    }


    void clearAll() {
        editor.clear().apply();
    }

}
