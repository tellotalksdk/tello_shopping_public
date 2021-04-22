package com.tilismtech.tellotalk_shopping_sdk.api;

import android.util.Log;

import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
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

public class TokenRefreshAuthenthicator implements Authenticator {

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {

        String updatedToken = getUpdatedToken();

        return response.request().newBuilder()
                .header("Header", updatedToken)
                .build();
    }

    private String getUpdatedToken() {
        getRetrofitClient().generateToken("Basit@tilismtech.com", "basit@1234", "password", Constant.PROFILE_ID, "Hassan", "Muddassir", "Rizvi", "03330347473", "Hasan2399@gmail.com").enqueue(new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, retrofit2.Response<GenerateTokenResponse> response) {
                if (response.isSuccessful()) {
                    GenerateTokenResponse generateTokenResponse = response.body();
                    TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).saveProfileId(Constant.PROFILE_ID);
                }
            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

        return TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getProfileId();
    }
}
