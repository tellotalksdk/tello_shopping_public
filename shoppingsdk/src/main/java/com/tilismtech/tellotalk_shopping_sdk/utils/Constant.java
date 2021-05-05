package com.tilismtech.tellotalk_shopping_sdk.utils;

import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;

public class Constant {

   public static String BASE_URL = "https://www.tilismtechservices.com/ShoppingSDK_staging/";
    //public static String BASE_URL = "http://localhost:51322/";
    // public static String PROFILE_ID = "3F64D77CB1BA4A3CA6CF9B9D786D4A43";
    public static String PROFILE_ID = TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getProfileId();

    public static String getProfileId() {
        return PROFILE_ID;
    }

    public static void setProfileId(String profileId) {
        PROFILE_ID = profileId;
    }
}
