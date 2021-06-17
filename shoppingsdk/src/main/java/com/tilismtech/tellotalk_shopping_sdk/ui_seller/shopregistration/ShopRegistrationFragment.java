package com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateUserAndImage;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateUserAndImageResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.VerifyOtpResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage.ShopLandingActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopsetting.ShopSettingFragment;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class ShopRegistrationFragment extends Fragment {

    private final static int UPLOAD_IMAGE = 123;
    private final static int CAPTURE_IMAGE = 456;
    int counter = 0;
    Button requestforPin;
    Button requestAgain, done_btn;
    String mobileNumber;
    NavController navController;
    RelativeLayout RL;
    ImageView iv_back, iv_editImage, iv_user_image;
    TextView tv_shop_name, store_name_link_one, store_name_link_two, insertDigitreflection, your_number, countDown;
    EditText et_shop_name, d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, otp_one, otp_two, otp_three, userShopname;
    boolean isEditable;
    Spinner spinner_operator;
    String regex = "^[a-z0-9\\s|A-Z0-9\\s|a-zA-Z\\s]+$"; //regex for shop name must be in alphanumeric format...
    StringBuilder mobileNumberReflection = new StringBuilder("92 xxx xxx xxxx");
    ArrayList<String> mobileOpt = new ArrayList<>();
    ShopRegistrationViewModel shopRegistrationViewModel;
    boolean isD1, isD2, isD3, isD4, isD5, isD6, isD7, isD8, isD9, isD10, isD11;
    Dialog dialogImage;
    Uri imageUri;
    String filePath = "", otp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_registration, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        initViews(view);
        shopRegistrationViewModel = new ViewModelProvider(this).get(ShopRegistrationViewModel.class);

        //region requestforpin
        requestforPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    RL.setVisibility(View.VISIBLE);
                    requestforPin.setVisibility(View.GONE);
                    requestAgain.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_bg_color));
                    requestAgain.setTextColor(Color.BLACK);
                    requestAgain.setClickable(false);
                    startCountDown();
                    counter++;
                    TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveShopURI(store_name_link_one.getText().toString() + store_name_link_two.getText().toString());

                    //this will collect all digits from number fields...
                    mobileNumber = d1.getText().toString() + d2.getText().toString() + d3.getText().toString() + d4.getText().toString() + d5.getText().toString() + d6.getText().toString() + d7.getText().toString() + d8.getText().toString() + d9.getText().toString() + d10.getText().toString() + d11.getText().toString();

                    ShopRegister shopRegister = new ShopRegister();
                    shopRegister.setProfileId(Constant.PROFILE_ID); //for testing shop regoistration
                    shopRegister.setShopURl(et_shop_name.getText().toString().trim() + ".tello.pk");
                    shopRegister.setRegisterPhone(mobileNumber.toString().trim());
                    // shopRegister.setRegisterPhone("03330347473");
                    shopRegister.setEmail("sharjeel@gmail.com");
                    shopRegister.setShopCategoryId("1");
                    shopRegister.setShopDescription("shopTesting");
                    shopRegister.setShopName(et_shop_name.getText().toString());

                    shopRegistrationViewModel.postShopRegister(shopRegister);
                    shopRegistrationViewModel.getShopResponse().observe(getActivity(), new Observer<ShopRegisterResponse>() {
                        @Override
                        public void onChanged(ShopRegisterResponse shopRegisterResponse) {
                            if (shopRegisterResponse != null) {
                                if ("-6".equals(shopRegisterResponse.getStatus())) {
                                    Toast.makeText(getActivity(), "" + shopRegisterResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "" + shopRegisterResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Not Found...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                Utility.hideKeyboard(getActivity(), v);
            }
        });
        //endregion

        //region done_btn
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOTP()) {
                    otp = otp_one.getText().toString().trim() + otp_two.getText().toString().trim() + otp_three.getText().toString().trim();
                    shopRegistrationViewModel.verifyOTP(otp);
                    shopRegistrationViewModel.getVerifyOtp().observe(getActivity(), new Observer<VerifyOtpResponse>() {
                        @Override
                        public void onChanged(VerifyOtpResponse verifyOtpResponse) {
                            if (verifyOtpResponse != null) {
                                if (verifyOtpResponse.getStatus().equals("0")) {
                                    Toast.makeText(getActivity(), "OTP verified...", Toast.LENGTH_SHORT).show();
                                    // getActivity().startActivity(new Intent(getActivity(), ShopLandingActivity.class));
                                    navController.navigate(R.id.shopSettingFragment);
                                } else if (verifyOtpResponse.getStatus().equals("-1")) {
                                    Toast.makeText(getActivity(), verifyOtpResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                   // getActivity().startActivity(new Intent(getActivity(), ShopLandingActivity.class));
                    navController.navigate(R.id.shopSettingFragment);
                }
            }
        });
        //endregion

        //region requestAgain
        requestAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter < 2) {
                    // here we call resend opt
                    requestAgain.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_bg_color));
                    requestAgain.setTextColor(Color.BLACK);
                    requestAgain.setClickable(false);
                    startCountDown();
                    counter++;
                    shopRegistrationViewModel.resendOTP();
                    shopRegistrationViewModel.getresendOtp().observe(getActivity(), new Observer<VerifyOtpResponse>() {
                        @Override
                        public void onChanged(VerifyOtpResponse verifyOtpResponse) {
                            if (verifyOtpResponse != null) {

                            }
                        }
                    });
                } else {
                    requestAgain.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_bg_dark_selected));
                    requestAgain.setTextColor(Color.WHITE);
                    requestAgain.setClickable(true);
                    requestAgain.setText("Request For Call");
                }
            }
        });
        //endregion

        //region backbutton
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        //endregion

        //region shopNameAnimation
        et_shop_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_shop_name.animate().alpha(1.0f);
                store_name_link_one.setText(et_shop_name.getText().toString().replace(" ", "-") + ".");

                if (TextUtils.isEmpty(et_shop_name.getText().toString())) {
                    tv_shop_name.animate().alpha(0.0f);
                    store_name_link_one.animate().alpha(1);
                    store_name_link_one.setText("your-shop-name.");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //endregion

        //region mobileNumber
        insertDigitreflection.setText(mobileNumberReflection);

        d1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(d1.getText().toString())) {
                    d1.clearFocus();
                    d2.requestFocus();
                    d2.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        d1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD2) {
                        isD2 = false;
                    } else {
                        d1.clearFocus();
                        d11.requestFocus();
                        d11.setCursorVisible(true);
                        if (d11.getText().length() == 0) {
                            d11.setSelection(0);
                        } else {
                            d11.setSelection(1);
                        }
                    }
                }
                return false;
            }
        });


        d2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d2.getText().toString())) {
                    d2.clearFocus();
                    d3.requestFocus();
                    d3.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d2.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(3, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d2.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(3, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD3) {
                        isD3 = false;
                    } else {
                        d2.clearFocus();
                        d1.requestFocus();
                        d1.setCursorVisible(true);
                        if (d1.getText().length() == 0) {
                            d1.setSelection(0);
                        } else {
                            d1.setSelection(1);
                        }
                        isD2 = true;
                    }
                }
                return false;
            }
        });

        d3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d3.getText().toString())) {
                    d3.clearFocus();
                    d4.requestFocus();
                    d4.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d3.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(4, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d3.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(4, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD4) {
                        isD4 = false;
                    } else {
                        d3.clearFocus();
                        d2.requestFocus();
                        d2.setCursorVisible(true);
                        if (d2.getText().length() == 0) {
                            d2.setSelection(0);
                        } else {
                            d2.setSelection(1);
                        }
                        isD3 = true;
                    }
                }
                return false;
            }
        });

        d4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d4.getText().toString())) {
                    d4.clearFocus();
                    d5.requestFocus();
                    d5.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d4.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(5, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d4.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(5, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD5) {
                        isD5 = false;
                    } else {
                        d4.clearFocus();
                        d3.requestFocus();
                        d3.setCursorVisible(true);
                        if (d3.getText().length() == 0) {
                            d3.setSelection(0);
                        } else {
                            d3.setSelection(1);
                        }
                        isD4 = true;
                    }
                }
                return false;
            }
        });

        d5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d5.getText().toString())) {
                    d5.clearFocus();
                    d6.requestFocus();
                    d6.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d5.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(7, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d5.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(7, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD6) {
                        isD6 = false;
                    } else {
                        d5.clearFocus();
                        d4.requestFocus();
                        d4.setCursorVisible(true);
                        if (d4.getText().length() == 0) {
                            d4.setSelection(0);
                        } else {
                            d4.setSelection(1);
                        }
                        isD5 = true;
                    }
                }
                return false;
            }
        });

        d6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d6.getText().toString())) {
                    d6.clearFocus();
                    d7.requestFocus();
                    d7.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d6.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(8, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d6.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(8, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD7) {
                        isD7 = false;
                    } else {
                        d6.clearFocus();
                        d5.requestFocus();
                        d5.setCursorVisible(true);
                        if (d5.getText().length() == 0) {
                            d5.setSelection(0);
                        } else {
                            d5.setSelection(1);
                        }
                        isD6 = true;
                    }
                }
                return false;
            }
        });

        d7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d7.getText().toString())) {
                    d7.clearFocus();
                    d8.requestFocus();
                    d8.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d7.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(9, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d7.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(9, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d7.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD8) {
                        isD8 = false;
                    } else {
                        d7.clearFocus();
                        d6.requestFocus();
                        d6.setCursorVisible(true);
                        if (d6.getText().length() == 0) {
                            d6.setSelection(0);
                        } else {
                            d6.setSelection(1);
                        }
                        isD7 = true;
                    }
                }
                return false;
            }
        });

        d8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d8.getText().toString())) {
                    d8.clearFocus();
                    d9.requestFocus();
                    d9.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d8.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(11, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d8.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(11, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d8.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD9) {
                        isD9 = false;
                    } else {
                        d8.clearFocus();
                        d7.requestFocus();
                        d7.setCursorVisible(true);
                        if (d7.getText().length() == 0) {
                            d7.setSelection(0);
                        } else {
                            d7.setSelection(1);
                        }
                        isD8 = true;
                    }
                }
                return false;
            }
        });


        d9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d9.getText().toString())) {
                    d9.clearFocus();
                    d10.requestFocus();
                    d10.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d9.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(12, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d9.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(12, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d9.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD10) {
                        isD10 = false;
                    } else {
                        d9.clearFocus();
                        d8.requestFocus();
                        d8.setCursorVisible(true);
                        if (d8.getText().length() == 0) {
                            d8.setSelection(0);
                        } else {
                            d8.setSelection(1);
                        }
                        isD9 = true;
                    }
                }
                return false;
            }
        });

        d10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d10.getText().toString())) {
                    d10.clearFocus();
                    d11.requestFocus();
                    d11.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d10.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(13, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d10.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(13, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d10.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (isD11) {
                        isD11 = false;
                    } else {
                        d10.clearFocus();
                        d9.requestFocus();
                        d9.setCursorVisible(true);
                        if (d9.getText().length() == 0) {
                            d9.setSelection(0);
                        } else {
                            d9.setSelection(1);
                        }
                        isD10 = true;
                    }
                }
                return false;
            }
        });
        d11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(d11.getText().toString())) {
                    d11.clearFocus();
                    d1.requestFocus();
                    d1.setCursorVisible(true);
                }

                if (TextUtils.isEmpty(d11.getText().toString())) { //add single space on zero index
                    mobileNumberReflection.setCharAt(14, 'x');
                    insertDigitreflection.setText(mobileNumberReflection);
                } else { // add number on zero index
                    String s1 = d11.getText().toString();
                    char c = s1.charAt(0);//returns h
                    mobileNumberReflection.setCharAt(14, c);
                    insertDigitreflection.setText(mobileNumberReflection);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        d11.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    d11.clearFocus();
                    d10.requestFocus();
                    d10.setCursorVisible(true);
                    if (d10.getText().length() == 0) {
                        d10.setSelection(0);
                    } else {
                        d10.setSelection(1);
                    }
                    isD11 = true;
                }
                return false;
            }
        });

        //endregion

        //region OTP
        otp_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(otp_one.getText().toString())) {
                    otp_one.clearFocus();
                    otp_two.requestFocus();
                    otp_two.setCursorVisible(true);
                }

                if (!TextUtils.isEmpty(otp_one.getText().toString()) &&
                        !TextUtils.isEmpty(otp_two.getText().toString()) &&
                        !TextUtils.isEmpty(otp_three.getText().toString())) {

                    done_btn.setTextColor(Color.WHITE);
                    done_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn));
                    Utility.hideKeyboard(getActivity(), view);

                } else {
                    done_btn.setTextColor(Color.BLACK);
                    done_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn_light));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(otp_two.getText().toString())) {
                    otp_two.clearFocus();
                    otp_three.requestFocus();
                    otp_three.setCursorVisible(true);
                }

                if (!TextUtils.isEmpty(otp_one.getText().toString()) &&
                        !TextUtils.isEmpty(otp_two.getText().toString()) &&
                        !TextUtils.isEmpty(otp_three.getText().toString())) {

                    Utility.hideKeyboard(getActivity(), view);
                    done_btn.setTextColor(Color.WHITE);
                    done_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn));

                } else {
                    done_btn.setTextColor(Color.BLACK);
                    done_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn_light));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(otp_three.getText().toString())) {
                    otp_three.clearFocus();
                    otp_one.requestFocus();
                    otp_one.setCursorVisible(true);
                }

                if (!TextUtils.isEmpty(otp_one.getText().toString()) &&
                        !TextUtils.isEmpty(otp_two.getText().toString()) &&
                        !TextUtils.isEmpty(otp_three.getText().toString())) {


                    done_btn.setTextColor(Color.WHITE);
                    done_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn));
                    Utility.hideKeyboard(getActivity(), view);

                } else {
                    done_btn.setTextColor(Color.BLACK);
                    done_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn_light));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //endregion

        //region updateUserName_Image
            iv_editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we call api for set user name and image ...
                UpdateUserAndImage updateUserAndImage = new UpdateUserAndImage();
                updateUserAndImage.setFirstName("sharjeel");
                updateUserAndImage.setMiddleName(" ");
                updateUserAndImage.setLastName(" ");
                updateUserAndImage.setProfileId(Constant.PROFILE_ID);
                updateUserAndImage.setProfilePic(filePath);
                shopRegistrationViewModel.getUpdateUserImageResponse().removeObservers(getActivity()); //need to remove observer here to stop multiple call of apis
                shopRegistrationViewModel.userImageandName(updateUserAndImage);
                shopRegistrationViewModel.getUpdateUserImageResponse().observe(getActivity(), new Observer<UpdateUserAndImageResponse>() {
                    @Override
                    public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                        if (updateUserAndImage != null) {
                            Toast.makeText(getActivity(), updateUserAndImageResponse.getStatusDetail(), Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });
        //endregion

        //region mobileOperatorsetOnSpinner
        mobileOpt.add("Select");
        mobileOpt.add("Ufone");
        mobileOpt.add("Telenor");
        mobileOpt.add("Jazz");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, mobileOpt);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item); // The drop down vieww
        spinner_operator.setAdapter(spinnerArrayAdapter);
        //endregion

        //region Storage_Permissions
        iv_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });
        //endregion

        Utility.hideKeyboard(getActivity(),view);
    }

    private void checkPermissions() {
        dialogImage = new Dialog(getActivity());
        dialogImage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogImage.setContentView(R.layout.dialog_upload_capture);

        Button upload = dialogImage.findViewById(R.id.uploadImage);
        Button capture = dialogImage.findViewById(R.id.captureImage);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) +
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) +
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Grant those permissions...");
                        builder.setMessage("Camera and External Storage Permission Required...");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                }, 1);

                            }
                        });

                        builder.setNegativeButton("Cancel", null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else { //ye lines for the very first time chalein gy jab app start hongy
                        requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 1);
                    }
                } else {
                    dialogImage.dismiss();
                    openGallery();
                }
            }
        });
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) +
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) +
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Grant those permissions...");
                        builder.setMessage("Camera External Storage Permission Required...");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                }, 2);

                            }
                        });

                        builder.setNegativeButton("Cancel", null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else { //ye lines for the very first time chalein gy jab app start hongy
                        requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 2);
                    }
                } else {
                    dialogImage.dismiss();
                    openCamera();
                }
            }
        });

        Window window = dialogImage.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        window.setAttributes(wlp);
        dialogImage.show();
    }

    private boolean checkOTP() {
        if (TextUtils.isEmpty(otp_one.getText().toString())) {
            Toast.makeText(getActivity(), "OTP required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(otp_two.getText().toString())) {
            Toast.makeText(getActivity(), "OTP required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(otp_three.getText().toString())) {
            Toast.makeText(getActivity(), "OTP required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void startCountDown() {

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDown.setText("in 00 : " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                try {
                    countDown.setText("done!");
                    requestAgain.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_bg_dark_selected));
                    requestAgain.setTextColor(Color.WHITE);
                    requestAgain.setClickable(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }.start();


    }

    public void initViews(View view) {
        requestforPin = view.findViewById(R.id.requestforPin);
        requestAgain = view.findViewById(R.id.requestAgain);
        done_btn = view.findViewById(R.id.done_btn);
        RL = view.findViewById(R.id.RL);
        iv_back = view.findViewById(R.id.iv_back);
        store_name_link_one = view.findViewById(R.id.store_name_link_one);
        store_name_link_two = view.findViewById(R.id.store_name_link_two);
        insertDigitreflection = view.findViewById(R.id.insertDigitreflection);
        tv_shop_name = view.findViewById(R.id.tv_shop_name);
        et_shop_name = view.findViewById(R.id.et_shop_name);
        your_number = view.findViewById(R.id.your_number);
        countDown = view.findViewById(R.id.countDown);
        otp_one = view.findViewById(R.id.otp_one);
        otp_two = view.findViewById(R.id.otp_two);
        otp_three = view.findViewById(R.id.otp_three);
        iv_editImage = view.findViewById(R.id.iv_editImage);
        userShopname = view.findViewById(R.id.userShopname);
        spinner_operator = view.findViewById(R.id.spinner_operator);
        iv_user_image = view.findViewById(R.id.iv_user_image);

        d1 = view.findViewById(R.id.d1);
        d2 = view.findViewById(R.id.d2);
        d3 = view.findViewById(R.id.d3);
        d4 = view.findViewById(R.id.d4);
        d5 = view.findViewById(R.id.d5);
        d6 = view.findViewById(R.id.d6);
        d7 = view.findViewById(R.id.d7);
        d8 = view.findViewById(R.id.d8);
        d9 = view.findViewById(R.id.d9);
        d10 = view.findViewById(R.id.d10);
        d11 = view.findViewById(R.id.d11);


    }

    public boolean checkValidation() {
        if (TextUtils.isEmpty(et_shop_name.getText().toString())) {
            Toast.makeText(getActivity(), "Shop Name is required...", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (et_shop_name.getText().length() > 25) {
            Toast.makeText(getActivity(), "Shop Name should not be exceed 25 characters...", Toast.LENGTH_SHORT).show();
            return false;
        }


        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(et_shop_name.getText().toString());

        if (!TextUtils.isEmpty(et_shop_name.getText().toString()) && matcher.matches() == false) {
            Toast.makeText(getActivity(), "Shop Name must be in alphanumeric format...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d1.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d2.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d3.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d4.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d5.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d6.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d7.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d8.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d9.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d10.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(d11.getText().toString())) {
            Toast.makeText(getActivity(), "Phone Number Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, UPLOAD_IMAGE);
    }

    private void openCamera() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, CAPTURE_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: //pick and set image from gallery
                if (grantResults.length > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    dialogImage.dismiss();
                    openGallery();
                }
                break;
            case 2: //capture image and set from camera
                if (grantResults.length > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    dialogImage.dismiss();
                    openCamera();
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UPLOAD_IMAGE) { //Upload image from gallery
            imageUri = data.getData();
            iv_user_image.setImageURI(imageUri);
            filePath = getPath(getActivity(), imageUri);
            Log.i("TAG", "onActivityResult: Gallery Upload Path" + filePath);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                iv_user_image.setImageBitmap(resized);
            }


        } else if (resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE) {

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                Uri selectedImage = data.getData();
                iv_user_image.setImageURI(selectedImage);
                filePath = getRealPathFromURI(selectedImage);
                Log.i("TAG", "onActivityResult: Capture Capture Path" + filePath);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                iv_user_image.setImageBitmap(resized);
            } else { //other than marshmallow
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                iv_user_image.setImageBitmap(photo);
                imageUri = getImageUri(getActivity(), photo);
                filePath = getRealPathFromURI(imageUri);
                Log.i("TAG", "onActivityResult: Capture Path : " + filePath);
            }
        }

    }

    //This method return file path when we choose image from gallery
    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    //this method is used to get image uri , after capturing image from camera
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //this method is used to get image path when user capture image from camera
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

}
