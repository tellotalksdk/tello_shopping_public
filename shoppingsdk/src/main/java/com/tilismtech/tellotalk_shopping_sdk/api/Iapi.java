package com.tilismtech.tellotalk_shopping_sdk.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Iapi {

    @GET("")
    Call<ResponseBody> getRes();
}
