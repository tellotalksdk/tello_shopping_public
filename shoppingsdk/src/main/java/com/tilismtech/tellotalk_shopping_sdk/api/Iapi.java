package com.tilismtech.tellotalk_shopping_sdk.api;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetTimings;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.IsProductActive;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.IsProductActiveResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.OrderByStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductForEdit;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductList;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopTiming;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateOrderStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateRiderInfo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetAllOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderStatusCountResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetTimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductForEditResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopTimingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.TimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateOrderStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateRiderInfoResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ViewFullOrderResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Iapi {

    @FormUrlEncoded
    @POST("generatetoken")
    Call<GenerateTokenResponse> generateToken(@Field("username") String username,
                                              @Field("password") String password,
                                              @Field("grant_type") String grant_type,
                                              @Field("profileId") String profileId,
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
    @Headers({
            "Accept: application/json"
    })
    @Multipart
    @POST("api/shop/ShopSettingwithImage")
    Call<ShopBasicSettingResponse> setShopBasicSetting(@Header("Authorization") String token,
                                                       @Part MultipartBody.Part ShopProfile,
                                                       @Part("ShippingFee") RequestBody ShippingFee,
                                                       @Part("tax") RequestBody tax,
                                                       @Part("Province") RequestBody Province,
                                                       @Part("Area") RequestBody Area,
                                                       @Part("City") RequestBody City,
                                                       @Part("Country") RequestBody Country,
                                                       @Part("Shop_Theme") RequestBody Shop_Theme,
                                                       @Part("ProfileId") RequestBody ProfileId
    );


    //get all product categories list
    @GET("api/Product/ProductCategoryList")
    Call<ProductCategoryListResponse> getProductCategories(@Header("Authorization") String token);

    //get all parent categories list
    @Headers({"Accept: application/json",
            "Content-Type: application/json"}
    )
    @GET("api/Product/ParentProductCategoryList")
    Call<ParentCategoryListResponse> getParentCategories(
            @Header("Authorization") String token);

    //addNewProductApi
    @Headers({
            "Accept: application/json"
    })
    @Multipart
    @POST("api/Product/AddNewProductwithImage")
    Call<AddNewProductResponse> addNewProducts(@Header("Authorization") String token,
                                               @Part List<MultipartBody.Part> Product_Pic,
                                               @Part("Product_Category_id") RequestBody Product_Category_id,
                                               @Part("Title") RequestBody Title,
                                               @Part("Sub_Product_Category_id") RequestBody Sub_Product_Category_id,
                                               @Part("Discount_Price") RequestBody Discount_Price,
                                               @Part("Sku") RequestBody Sku,
                                               @Part("Summary") RequestBody Summary,
                                               @Part("ProfileId") RequestBody ProfileId,
                                               @Part("ProductStatus") RequestBody ProductStatus,
                                               @Part("Price") RequestBody Price);

    //updateProductApi
    @Headers({
            "Accept: application/json"
    })
    @Multipart
    @POST("api/Product/UpdateProductwithImage")
    Call<UpdateProductResponse> updateProduct(@Header("Authorization") String token,
                                              @Part List<MultipartBody.Part> Product_Pic,
                                              @Part("Product_Category_id") RequestBody Product_Category_id,
                                              @Part("Title") RequestBody Title,
                                              @Part("Sub_Product_Category_id") RequestBody Sub_Product_Category_id,
                                              @Part("Discount_Price") RequestBody Discount_Price,
                                              @Part("Sku") RequestBody Sku,
                                              @Part("Summary") RequestBody Summary,
                                              @Part("ProfileId") RequestBody ProfileId,
                                              @Part("ProductStatus") RequestBody ProductStatus,
                                              @Part("Price") RequestBody Price,
                                              @Part("ProductId") RequestBody ProductId);

    //getproductforedit
    @Headers({"Accept: */*",
            "Content-Type: application/json"}
    )
    @GET("api/Product/GetProductforedit")
    Call<ProductForEditResponse> getProductForEdit(@Header("Authorization") String token, @Query("ProfileId") String ProfileId,
                                                   @Query("ProductId") String ProductId);

    //update rider information api
    @Headers({"Accept: */*",
            "Content-Type: application/json"}
    )
    @POST("api/Order/UpdateRiderInfo")
    Call<UpdateRiderInfoResponse> updateRiderInformation(@Header("Authorization") String token, @Body UpdateRiderInfo updateRiderInfo);

    //getordersbystatus
    @GET("api/Order/getOrderbyStatus")
    Call<GetOrderByStatusResponse> getOrderbyStatus(@Header("Authorization") String token,
                                                    @Query("ProfileId") String ProfileId,
                                                    @Query("StatusId") String StatusId);


    //updateorderstatus
    @Headers({"Accept: */*",
            "Content-Type: application/json"}
    )
    @POST("api/Order/UpdateOrderStatus")
    Call<UpdateOrderStatusResponse> updateOrderStatus(@Header("Authorization") String token, @Body UpdateOrderStatus updateOrderStatus);

    //viewfullorder
    @Headers({"Accept: */*",
            "Content-Type: application/json"}
    )
    @GET("api/Order/ViewFullOrder")
    Call<ViewFullOrderResponse> viewfullorder(@Header("Authorization") String token,
                                              @Query("ProfileId") String ProfileId,
                                              @Query("OrderId") String OrderId,
                                              @Query("OrderStatus") String OrderStatus);

    //getproductList
    @GET("api/Product/GetProductList")
    Call<ProductListResponse> getProductList(@Header("Authorization") String token, @Query("ProfileId") String ProfileId);

    //subcategory by parent id.
    @Headers({"Accept: */*",
            "Content-Type: application/json"}
    )
    @GET("api/Product/ProdCatLstbyparentid")
    Call<SubCategoryBYParentCatIDResponse> getSubcategoryByParentID(@Header("Authorization") String token,
                                                                    @Query("Parent_Category_Id") String Parent_Category_Id);

    //shop detail get...
    @Headers({"Accept: */*",
            "Content-Type: application/json"}
    )
    @GET("api/shop/getshopdetails")
    Call<GetShopDetailResponse> getShopDetail(@Header("Authorization") String token, @Query("ProfileId") String ProfileId);

    //gettimings
    @GET("api/shop/getshopTimming")
    Call<GetTimingsResponse> getShopTiming(@Header("Authorization") String token, @Query("ProfileId") String ProfileId);

    //posttiming remaining....
    @POST("api/shop/ShopTiming")
    Call<ShopTimingResponse> postTiming(@Header("Authorization") String token, @Body ShopTiming shopTiming);

    //product status is in order list screen where we have 6 status
    @POST("api/Product/UpdateProductStatus")
    Call<IsProductActiveResponse> updateProductStatus(@Header("Authorization") String token, @Body IsProductActive isProductActive);

    //get all order to show inside order list All tab ...
    @GET("api/Order/getAllOrders")
    Call<GetAllOrderResponse> getAllOrderList(@Header("Authorization") String token, @Query("ProfileId") String ProfileId);

    //call to get all status count in order tabs
    @GET("api/Order/getOrderStatusCount")
    Call<GetOrderStatusCountResponse> getOrderAllStatusCount(@Header("Authorization") String token,
                                                             @Query("ProfileId") String profileId);

    //Add | Update | Delete Shop Branch Address...

    @POST("api/shop/AddShopBranchAddress")
    Call<AddBranchAddressResponse> addBranchAddress(@Header("Authorization") String token, @Body AddBranchAddress addBranchAddress);

    @POST("api/shop/UpdateShopBranchAddress")
    Call<UpdateBranchAddressResponse> updateBranchAddress(@Header("Authorization") String token, @Body UpdateBranchAddress updateBranchAddress);

    @POST("api/shop/DeleteShopBranchAddress")
    Call<DeleteBranchAddressResponse> deleteBranchAddress(@Header("Authorization") String token, @Body DeleteBranchAddress deleteBranchAddress);

    //Delete Product Api
    @POST("api/Product/DeleteProduct")
    Call<DeleteProductResponse> deleteProduct(@Header("Authorization") String token, @Body DeleteProduct deleteProduct);

}
