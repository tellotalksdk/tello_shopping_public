package com.tilismtech.tellotalk_shopping_sdk.utils;

import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;

public class Constant {

   //  public static String BASE_URL = "https://www.tilismtechservices.com/ShoppingSDK_staging/";
    public static String BASE_URL = "http://172.16.10.53/abc/";


    //public static String BASE_URL = "http://localhost:51322/";
    // public static String PROFILE_ID = "3F64D77CB1BA4A3CA6CF9B9D786D4A43";
    public static String PROFILE_ID = TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getProfileId();
    public static String FIRST_NAME = TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getOwnerName();
    //  public static String MIDDLE_NAME = TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getProfileId();
    //  public static String LAST_NAME = TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getProfileId();
    public static String CONTACT_NUMBER = TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getRegisteredNumber();

    public  String getProfileId() {
        return PROFILE_ID;
    }

    public  void setProfileId(String profileId) {
        PROFILE_ID = profileId;
    }
}
