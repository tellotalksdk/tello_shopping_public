package com.tilismtech.tellotalk_shopping_sdk.managers;

import android.content.Context;
import android.util.Log;

import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GenerateToken;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GTResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient.getRetrofitClient;

public class TelloApiClient {

    public static TelloApiClient instance;
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


    public static boolean initializeShoppingSDK() {
        AccessTokenPojo accessTokenPojo = new AccessTokenPojo();

        //user name + password + grant type always remain same other will change...
        accessTokenPojo.setUsername("Basit@tilismtech.com");
        accessTokenPojo.setPassword("basit@1234");
        accessTokenPojo.setGrant_type("password");

        accessTokenPojo.setprofileId("3F64D77CB1BA4A3CA6CF9B9D786D4A43");
        accessTokenPojo.setFirstname("Hasan");
        accessTokenPojo.setMiddlename("Muddassir");
        accessTokenPojo.setLastname("Naqvi");
        accessTokenPojo.setPhone("03330347473");
        accessTokenPojo.setEmail("emai@gmail.com");

        return generateAccessToken(accessTokenPojo, TelloApplication.getContext());
    }

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
    }

    public static void generateTokenResponse(GenerateToken generateToken, Context myCtx) {
        getRetrofitClient().generateToken(generateToken).enqueue(new Callback<GTResponse>() {
            @Override
            public void onResponse(Call<GTResponse> call, Response<GTResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        GTResponse gtResponse = response.body();

                    } else {
                        Log.i("TAG", "onResponse: " + response.code()); //500 code occur but run
                    }
                }
            }

            @Override
            public void onFailure(Call<GTResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
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
