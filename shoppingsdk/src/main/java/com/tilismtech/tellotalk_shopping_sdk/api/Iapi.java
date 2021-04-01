package com.tilismtech.tellotalk_shopping_sdk.api;

import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Iapi {

    @FormUrlEncoded
    @POST("/generatetoken")
    Call<GenerateTokenResponse> generateToken(@Field("username") String username,
                                              @Field("password") String password,
                                              @Field("grant_type") String grant_type,
                                              @Field("profile") String profile,
                                              @Field("firstname") String firstname,
                                              @Field("middlename") String middlename,
                                              @Field("lastname") String lastname,
                                              @Field("phone") String phone,
                                              @Field("email") String email
                              );
}
