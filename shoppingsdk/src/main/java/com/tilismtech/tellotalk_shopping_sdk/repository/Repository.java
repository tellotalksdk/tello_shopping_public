package com.tilismtech.tellotalk_shopping_sdk.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient.getRetrofitClient;

public class Repository {

    public static Repository repository;
    public Context myCtx;


    public static Repository getRepository() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }


    public void generateTokenresponse(MutableLiveData<GenerateTokenResponse> generateTokenResponseLiveData, String UN, String Pass, String Grant_type, String profile, String firstName, String middleName, String lastName, String phone, String email) {
        getRetrofitClient().generateToken(UN, Pass, Grant_type, profile, firstName, middleName, lastName, phone, email).enqueue(new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, Response<GenerateTokenResponse> response) {
                if (response.isSuccessful()) {
                    GenerateTokenResponse generateTokenResponse = response.body();
                    generateTokenResponseLiveData.setValue(generateTokenResponse);
                }
            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void registerShop(MutableLiveData<ShopRegisterResponse> shopRegisterResponseMutableLiveData, ShopRegister shopRegister) {
        getRetrofitClient().shopRegister("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), shopRegister).enqueue(new Callback<ShopRegisterResponse>() {
            @Override
            public void onResponse(Call<ShopRegisterResponse> call, Response<ShopRegisterResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        ShopRegisterResponse shopRegisterResponse = response.body();
                        shopRegisterResponseMutableLiveData.setValue(response.body());
                    } else {
                        shopRegisterResponseMutableLiveData.setValue(null);
                    }
                }

                Log.i("TAG", "onResponse: ");
            }

            @Override
            public void onFailure(Call<ShopRegisterResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });

    }

    public void setShopBasicSetting(MutableLiveData<ShopBasicSettingResponse> shopBasicSettingResponseMutableLiveData, ShopBasicSetting shopBasicSetting) {


        /*getRetrofitClient().setShopBasicSetting("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(),
                ).enqueue(new Callback<ShopBasicSettingResponse>() {
            @Override
            public void onResponse(Call<ShopBasicSettingResponse> call, Response<ShopBasicSettingResponse> response) {
                if (response != null) {
                    ShopBasicSettingResponse shopBasicSettingResponse = response.body();
                    shopBasicSettingResponseMutableLiveData.setValue(shopBasicSettingResponse);
                }
            }

            @Override
            public void onFailure(Call<ShopBasicSettingResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });*/
    }
}
