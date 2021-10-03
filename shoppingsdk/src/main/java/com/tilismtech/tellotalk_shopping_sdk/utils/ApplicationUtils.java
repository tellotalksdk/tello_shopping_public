package com.tilismtech.tellotalk_shopping_sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.android.gms.common.GoogleApiAvailability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ApplicationUtils {

    public static String changeNumberFormat(String mobNumber, boolean flag) {
        if (flag) {
            mobNumber = mobNumber.replace(mobNumber.substring(0, 2), "0");
        } else {
            if (mobNumber.substring(0, 1).equals("0")) {
                mobNumber = mobNumber.replaceFirst("0", "92");
            } else {
//                StringBuilder myName = new StringBuilder(mobNumber);
//                myName.setCharAt(0, '9');
//                myName.setCharAt(1, '2');
                return mobNumber.replaceFirst("92", "0");
            }
        }
        return mobNumber;
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean deviceHasGooglePlayServices(Context context) {
        boolean flag = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == com.google.android.gms.common.ConnectionResult.SUCCESS;
        return flag;
    }

    public static boolean isValidNumber(String number) {
        String regex = "([0][3][0-5][0-5][0-9][0-9][0-9][0-9][0-9][0-9][0-9])"; //regex for valid 11 digit pakistani numbers
        String str = number;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumberSameAsCarrier(String mobileNumber, int carrierType) {


        if (carrierType == 1) { //telenor check
            String telenor = "([0][3][4][0-6][0-9][0-9][0-9][0-9][0-9][0-9][0-9])";
            Pattern p1 = Pattern.compile(telenor);
            Matcher m1 = p1.matcher(mobileNumber);
            if (m1.matches()) {
                return true;
            } else
                return false;
        } else if (carrierType == 2) { //ufone check
            String ufone = "([0][3][3][0-6][0-9][0-9][0-9][0-9][0-9][0-9][0-9])";
            Pattern p = Pattern.compile(ufone);
            Matcher m = p.matcher(mobileNumber);
            if (m.matches()) {
                return true;
            } else
                return false;


        } else if (carrierType == 3 ) { // warid check
            String warid = "([0][3][2][0-6][0-9][0-9][0-9][0-9][0-9][0-9][0-9])";
            Pattern p2 = Pattern.compile(warid);
            Matcher m2 = p2.matcher(mobileNumber);
            if (m2.matches()) {
                return true;
            } else
                return false;

        }else if (carrierType == 4 ){ //zong check
            String zong = "([0][3][1][0-6][0-9][0-9][0-9][0-9][0-9][0-9][0-9])";
            Pattern p3 = Pattern.compile(zong);
            Matcher m3 = p3.matcher(mobileNumber);
            if (m3.matches()) {
                return true;
            } else
                return false;

        }else if (carrierType == 5){ //jazz check
            String jazz = "([0][3][2][0-6][0-9][0-9][0-9][0-9][0-9][0-9][0-9])";
            Pattern p4 = Pattern.compile(jazz);
            Matcher m4 = p4.matcher(mobileNumber);
            if (m4.matches()) {
                return true;
            } else
                return false;
        }




        return true;
    }


}
