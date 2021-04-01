package com.tilismtech.tellotalk_shopping_sdk.repository;

import com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient.getRetrofitClient;

public class Repository {


    public Repository() {
    }


    public void generateTokenresponse(String UN, String Pass, String Grant_type, String profile, String firstName, String middleName, String lastName, String phone, String email) {
        getRetrofitClient().generateToken(UN,Pass,Grant_type,profile,firstName,middleName,lastName,phone,email).enqueue(new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, Response<GenerateTokenResponse> response) {

            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {

            }
        });
    }
}
