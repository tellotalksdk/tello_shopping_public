package com.tilismtech.tellotalk_shopping_sdk.utils;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Utility {

    public static void hideKeyboard(Context myCtx,View view){
        InputMethodManager inputMethodManager = (InputMethodManager) myCtx.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
    }
}
