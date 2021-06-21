package com.tilismtech.tellotalk_shopping_sdk.managers;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.listeners.OnSuccessListener;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GenerateToken;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GTResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopExistResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage.ShopLandingActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration.ShopRegistrationActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopsetting.ShopSettingFragment;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient.getRetrofitClient;

public class TelloApiClient {

    public static TelloApiClient instance;
    private static boolean isshopExist;
    private String access_token;


    public TelloApiClient(String access_token) {
        this.access_token = access_token;
    }

    public TelloApiClient() {
    }

    public static TelloApiClient getInstance() {
        if (instance == null) {
            instance = new TelloApiClient();
        }
        return instance;
    }

    public static void initApp(Context context) {
        context.startActivity(new Intent(context, ShopRegistrationActivity.class));
    }

    public static boolean initializeShoppingSDK(Context context, String profileId, String firstName, String middleName, String lastName, String phone, String email) {
        boolean isUserAvailable = false;

        GenerateToken generateToken = new GenerateToken();

        generateToken.setGrantUsername("Basit@tilismtech.com");
        generateToken.setGrantPassword("basit@1234");
        generateToken.setGrantType("password");
        generateToken.setProfileId(profileId);
        generateToken.setFirstname(firstName);
        generateToken.setMiddlename(middleName);
        generateToken.setLastname(lastName);
        generateToken.setPhone(phone);
        generateToken.setEmail(email);

        try {
            generateTokenResponse(generateToken, context, new OnSuccessListener() {
                @Override
                public void onSuccess(Object object) {
                    GTResponse gtResponseError = (GTResponse) object;
                    if ("-6".equals(gtResponseError.getStatus().toString())) {
                        Toast.makeText(context, "" + gtResponseError.getStatusDetail(), Toast.LENGTH_SHORT).show();
                    } else {
                        isShopExist(Constant.PROFILE_ID, context);
                    }
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return isUserAvailable;
    }

    /*//old api fopr token
    public static boolean generateAccessToken(AccessTokenPojo accessTokenPojo, Context myCtx) {
        getRetrofitClient().generateToken(accessTokenPojo.getUsername(), accessTokenPojo.getPassword(), accessTokenPojo.getGrant_type(), accessTokenPojo.getprofileId(), accessTokenPojo.getFirstname(), accessTokenPojo.getMiddlename(), accessTokenPojo.getLastname(), accessTokenPojo.getPhone(), accessTokenPojo.getEmail()).enqueue(new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, Response<GenerateTokenResponse> response) {
                if (response.isSuccessful()) {
                    GenerateTokenResponse generateTokenResponse = response.body();
                    TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveAccessToken(response.body().getAccessToken());
                    TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveRegisteredNumber(accessTokenPojo.getPhone());
                    TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveOwnerName(accessTokenPojo.getFirstname() + " " + accessTokenPojo.getMiddlename());
                    TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveProfileId(accessTokenPojo.getprofileId());
                    //   TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveShopURI(accessTokenPojo.g);
                    Log.i("TAG", "onResponse: " + TelloPreferenceManager.getInstance(myCtx).getAccessToken());
                    Log.i("TAG", "onResponse: " + TelloPreferenceManager.getInstance(myCtx).getProfileId());
                    Log.i("TAG", "onResponse: " + Constant.PROFILE_ID);

                }
            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
        return true;
    }*/

    //new and running api for token
    public static void generateTokenResponse(GenerateToken generateToken, Context myCtx, OnSuccessListener onSuccessListener) {
        getRetrofitClient().generateToken(generateToken).enqueue(new Callback<GTResponse>() {
            @Override
            public void onResponse(Call<GTResponse> call, Response<GTResponse> response) {
                if (response != null) {

                    if (response.isSuccessful()) {
                        GTResponse gtResponse = response.body();
                        if (gtResponse != null) {
                            if (gtResponse.getData() != null) {
                                if (gtResponse.getData().getRequestList().getAccessToken() != null)
                                    TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveAccessToken(gtResponse.getData().getRequestList().getAccessToken());

                                TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveRegisteredNumber(generateToken.getPhone());
                                TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveFirstName(generateToken.getFirstname());
                                TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveMiddleName(generateToken.getMiddlename());
                                TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveLastName(generateToken.getLastname());
                                TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveEmail(generateToken.getEmail());
                                TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveProfileId(generateToken.getProfileId());
                                TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveOwnerName(generateToken.getFirstname() + " " + generateToken.getMiddlename());
                                onSuccessListener.onSuccess(gtResponse);

                                Constant c = new Constant();
                                c.setProfileId(TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getProfileId());

                                Log.i("TAG", "TOKEN SAVE : " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getProfileId());
                            }
                        }
                    } else {
                        GTResponse message = new Gson().fromJson(response.errorBody().charStream(), GTResponse.class);
                        Log.i("TAG", "onResponse: " + message.toString());
                        onSuccessListener.onSuccess(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<GTResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static boolean isShopExist(String profileId, Context context) {
        getRetrofitClient().isShopExist("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), profileId).enqueue(new Callback<ShopExistResponse>() {
            @Override
            public void onResponse(Call<ShopExistResponse> call, Response<ShopExistResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        try {
                            if (response.body().getData().get(0).getIsShopExist()) {
                                isshopExist = true;
                                context.startActivity(new Intent(context, ShopLandingActivity.class));
                            } else {
                                isshopExist = false;
                                context.startActivity(new Intent(context, ShopRegistrationActivity.class));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ShopExistResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return isshopExist;
    }

    public static final class Builder {
        private String accessKey;


        public Builder() {
        }

        public Builder accessKey(String accessKey) {
            this.accessKey = accessKey;
            return this;
        }


        public TelloApiClient build() {
            return TelloApiClient.setUpClient(this);
        }
    }

    private static TelloApiClient setUpClient(Builder builder) {
        instance = new TelloApiClient();
        return instance;
    }
}
