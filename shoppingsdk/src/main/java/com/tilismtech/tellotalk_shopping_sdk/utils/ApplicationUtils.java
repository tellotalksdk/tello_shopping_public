package com.tilismtech.tellotalk_shopping_sdk.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.GoogleApiAvailability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationUtils {

    public static String changeNumberFormat(String mobNumber,boolean flag){
        if(flag) {
            mobNumber = mobNumber.replace(mobNumber.substring(0, 2), "0");
        }else{
            if(mobNumber.substring(0,1).equals("0")){
                mobNumber=    mobNumber.replaceFirst("0","92");
            }else {
//                StringBuilder myName = new StringBuilder(mobNumber);
//                myName.setCharAt(0, '9');
//                myName.setCharAt(1, '2');
                return mobNumber.replaceFirst("92","0");
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

    public static boolean isValidNumber(String number){
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



}
