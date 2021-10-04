package com.tilismtech.tellotalk_shopping_sdk.utils;

import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;

public class Constant {

    //  public static String BASE_URL = "https://www.tilismtechservices.com/ShoppingSDK_staging/";
    public static String BASE_URL = "https://tellocast.com/Shopping/"; //live url han cheer char nahy karne uskey sath
    // public static String BASE_URL = "http://172.16.10.52/abc/"; //ye waley url sy images upload honey ky baad show nahy hoty

    public static String PROFILE_ID = TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getProfileId();
    public static String CONTACT_NUMBER = TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getRegisteredNumber();

    public String getProfileId() {
        return PROFILE_ID;
    }

    public void setProfileId(String profileId) {
        PROFILE_ID = profileId;
    }

    public String getContactNumber() {
        return CONTACT_NUMBER;
    }

    public void setContactNumber(String contactNumber) {
        CONTACT_NUMBER = contactNumber;
    }


}
