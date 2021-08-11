package com.tilismtech.tellotalk_shopping_sdk.utils;

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


}
