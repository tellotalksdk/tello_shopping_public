package com.tilismtech.tellotalk_shopping_sdk.api;

import android.util.Log;

import com.google.gson.Gson;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GenerateToken;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GTResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;

import static com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient.getRetrofitClient;

public class TokenRefreshAuthenthicator extends TelloApplication  implements Authenticator {

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {

        String updatedToken = getUpdatedToken();
        Log.i("TAG", "authenticate: ");

        return response.request().newBuilder()
                .header("Authorization", "Bearer " + updatedToken)
                .build();
    }

    private String getUpdatedToken() {

        GenerateToken generateToken = new GenerateToken();

        generateToken.setGrantUsername("Basit@tilismtech.com");
        generateToken.setGrantPassword("basit@1234");
        generateToken.setGrantType("password");
        generateToken.setProfileId(Constant.PROFILE_ID);
        generateToken.setFirstname(TelloPreferenceManager.getInstance(getApplicationContext()).getFirstName());
        generateToken.setMiddlename(TelloPreferenceManager.getInstance(getApplicationContext()).getMiddleName());
        generateToken.setLastname(TelloPreferenceManager.getInstance(getApplicationContext()).getLastName());
        generateToken.setPhone(TelloPreferenceManager.getInstance(getApplicationContext()).getRegisteredNumber());
        generateToken.setEmail(TelloPreferenceManager.getInstance(getApplicationContext()).getEmail());

        getRetrofitClient().generateToken(generateToken).enqueue(new Callback<GTResponse>() {
            @Override
            public void onResponse(Call<GTResponse> call, retrofit2.Response<GTResponse> response) {
                if (response != null) {

                    if (response.isSuccessful()) {
                        GTResponse gtResponse = response.body();
                        if (gtResponse != null) {
                            if (gtResponse.getData() != null) {
                                if (gtResponse.getData().getRequestList().getAccessToken() != null)
                                    TelloPreferenceManager.getInstance(getApplicationContext()).saveAccessToken(gtResponse.getData().getRequestList().getAccessToken());
                            }
                        }
                    } else {
                        GTResponse message = new Gson().fromJson(response.errorBody().charStream(), GTResponse.class);
                        Log.i("TAG", "onResponse: " + message.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<GTResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

/*
        getRetrofitClient().generateToken("Basit@tilismtech.com", "basit@1234", "password", Constant.PROFILE_ID, "Hassan", "Muddassir", "Rizvi", "03330347473", "Hasan2399@gmail.com").enqueue(new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, retrofit2.Response<GenerateTokenResponse> response) {
                if (response.isSuccessful()) {
                    GenerateTokenResponse generateTokenResponse = response.body();
                    // TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveProfileId(Constant.PROFILE_ID);
                    TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveAccessToken(generateTokenResponse.getAccessToken());
                    Log.i("TAG", "onResponse: " + "trigger after 401");
                }
            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                Log.i("TAG", "onFailure: " + "trigger after 401");
            }
        });*/

        return TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken();
    }
}
