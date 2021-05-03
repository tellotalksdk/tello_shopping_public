package com.tilismtech.tellotalk_shopping_sdk.managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.listeners.OnSuccessListener;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
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

    public void generateAccessToken(AccessTokenPojo accessTokenPojo, Context myCtx) {
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
