package com.tilismtech.tellotalk_shopping_sdk.api;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetTimings;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.OrderByStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductForEdit;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductList;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateOrderStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateRiderInfo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetTimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductForEditResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateOrderStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateRiderInfoResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ViewFullOrderResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Iapi {

    @FormUrlEncoded
    @POST("generatetoken")
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


    // returning null right now ...
    @POST("api/shop/RegisterShop")
    Call<ShopRegisterResponse> shopRegister(@Header("Authorization") String token,
                                            @Body ShopRegister shopRegister
    );


    //shopsetting
    @Multipart
    @POST("api/shop/ShopSettingwithImage")
    Call<ShopBasicSettingResponse> setShopBasicSetting(@Header("Authorization") String token,
                                                       @Part("ShippingFee") RequestBody ShippingFee,
                                                       @Part("tax") RequestBody tax,
                                                       @Part("Province") RequestBody Province,
                                                       @Part("Area") RequestBody Area,
                                                       @Part("City") RequestBody City,
                                                       @Part("Country") RequestBody Country,
                                                       @Part("Shop_Theme") RequestBody Shop_Theme,
                                                       @Part("ProfileId") RequestBody ProfileId,
                                                       @Part("ShopProfile") MultipartBody.Part ShopProfile);


    //get all product categories list
    @GET("api/Product/ProductCategoryList")
    Call<ProductCategoryListResponse> getProductCategories(@Header("Authorization") String token);

    //get all parent categories list
    @GET("api/Product/ParentProductCategoryList")
    Call<ParentCategoryListResponse> getParentCategories(@Header("Authorization") String token);

    //getproductforedit
    @GET("api/Product/GetProductforedit")
    Call<ProductForEditResponse> getProductForEdit(@Header("Authorization") String token, @Body ProductForEdit productForEdit);

    //update rider information api
    @POST("/api/Order/UpdateRiderInfo")
    Call<UpdateRiderInfoResponse> updateRiderInformation(@Header("Authorization") String token, @Body UpdateRiderInfo updateRiderInfo);

    //getordersbystatus
    @GET("api/Product/getOrderbyStatus")
    Call<GetOrderByStatusResponse> getOrderbyStatus(@Header("Authorization") String token, @Body OrderByStatus orderByStatus);


    //updateorderstatus
    @GET("api/Order/UpdateOrderStatus")
    Call<UpdateOrderStatusResponse> updateOrderStatus(@Header("Authorization") String token, @Body UpdateOrderStatus updateOrderStatus);

    //viewfullorder
    @GET("api/Product/ViewFullOrder")
    Call<ViewFullOrderResponse> viewfullorder(@Header("Authorization") String token, @Body ViewFullOrder viewFullOrder);

    //getproductList
    @GET("api/Product/GetProductList")
    Call<ProductCategoryListResponse> getProductList(@Header("Authorization") String token, @Body ProductList productList);

    //subcategory by parent id...
    @GET("api/Product/ProdCatLstbyparentid")
    Call<SubCategoryBYParentCatIDResponse> getSubcategoryByParentID(@Header("Authorization") String token, @Body SubCategoryBYParentCatID subCategoryBYParentCatID);

    //shop detain get...
    @GET("api/shop/getshopdetails")
    Call<GetShopDetailResponse> getShopDetail(@Header("Authorization") String token, @Body GetShopDetail shopDetail);

    //gettimings
    @GET("api/shop/getshopTimming")
    Call<GetTimingsResponse> getShopTiming(@Header("Authorization") String token, @Body GetTimings timings);

    //posttiming remaining....


}
