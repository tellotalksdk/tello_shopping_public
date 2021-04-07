package com.tilismtech.tellotalk_shopping_sdk.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetOrderByStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetTimings;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.IsProductActive;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.IsProductActiveResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.OrderByStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductForEdit;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductList;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateOrderStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateRiderInfo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetTimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductForEditResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateOrderStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateRiderInfoResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ViewFullOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

import static com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient.getRetrofitClient;

public class Repository {

    public static Repository repository;
    public Context myCtx;

    public String boundary;


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

    public void setShopBasicSetting(MutableLiveData<ShopBasicSettingResponse> shopBasicSettingResponseMutableLiveData, ShopBasicSetting shopBasicSetting, Context myContext) {

        boundary = UUID.randomUUID().toString();

        File file = new File(shopBasicSetting.getShopProfile());

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part ShopProfile = MultipartBody.Part.createFormData("ShopProfile", file.getName(), requestBody); //for send an image as multipart

        RequestBody ShippingFee = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShippingFee());
        RequestBody tax = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getTax());
        RequestBody Province = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getProvince());
        RequestBody Area = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getArea());
        RequestBody City = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getCity());
        RequestBody Country = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getCountry());
        RequestBody Shop_Theme = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShop_Theme());
        RequestBody ProfileId = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getProfileId());

        getRetrofitClient().setShopBasicSetting("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(),
                ShopProfile, ShippingFee, tax, Province, Area, City, Country, Shop_Theme, ProfileId
        ).enqueue(new Callback<ShopBasicSettingResponse>() {
            @Override
            public void onResponse(Call<ShopBasicSettingResponse> call, Response<ShopBasicSettingResponse> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        ShopBasicSettingResponse shopBasicSettingResponse = response.body();
                        shopBasicSettingResponseMutableLiveData.setValue(shopBasicSettingResponse);
                    }
                } else {
                    Log.i("TAG", "onResponse: " + response.code()); //500 code occur but run
                    shopBasicSettingResponseMutableLiveData.setValue(null);
                }

                Log.i("TAG", "Error: " + response.errorBody());
                Log.i("TAG", "Error: " + response.raw());

            }

            @Override
            public void onFailure(Call<ShopBasicSettingResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
        System.out.println();

    }

    public void getTimings(MutableLiveData<GetTimingsResponse> getTimingsResponseMutableLiveData, GetTimings getTimings) {
        getRetrofitClient().getShopTiming("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), getTimings.getProfileId()).enqueue(new Callback<GetTimingsResponse>() {
            @Override
            public void onResponse(Call<GetTimingsResponse> call, Response<GetTimingsResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        GetTimingsResponse getTimingsResponse = response.body();
                        getTimingsResponseMutableLiveData.setValue(getTimingsResponse);
                    }
                } else {
                    getTimingsResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetTimingsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void postTogetParentCategories(MutableLiveData<ParentCategoryListResponse> parentCategoryListResponseMutableLiveData) {
        getRetrofitClient().getParentCategories("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken()).enqueue(new Callback<ParentCategoryListResponse>() {
            @Override
            public void onResponse(Call<ParentCategoryListResponse> call, Response<ParentCategoryListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ParentCategoryListResponse parentCategoryListResponse = response.body();
                        parentCategoryListResponseMutableLiveData.setValue(parentCategoryListResponse);
                    } else {
                        parentCategoryListResponseMutableLiveData.setValue(null);
                    }
                } else {
                    Log.i("TAG", "onResponse: " + "Not Successfull" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ParentCategoryListResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void postTogetChildCategories(MutableLiveData<SubCategoryBYParentCatIDResponse> subCategoryBYParentCatIDResponseMutableLiveData, SubCategoryBYParentCatID parentID) {
        getRetrofitClient().getSubcategoryByParentID("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), parentID.getParentCategoryId()).enqueue(new Callback<SubCategoryBYParentCatIDResponse>() {
            @Override
            public void onResponse(Call<SubCategoryBYParentCatIDResponse> call, Response<SubCategoryBYParentCatIDResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        SubCategoryBYParentCatIDResponse subCategoryBYParentCatIDResponse = response.body();
                        subCategoryBYParentCatIDResponseMutableLiveData.setValue(subCategoryBYParentCatIDResponse);
                    } else {
                        subCategoryBYParentCatIDResponseMutableLiveData.setValue(null);
                    }
                } else {

                    response.code();

                }
            }

            @Override
            public void onFailure(Call<SubCategoryBYParentCatIDResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void addNewProducts(MutableLiveData<AddNewProductResponse> addNewProductResponseMutableLiveData, AddNewProduct addNewProduct) {

//        File file = new File(shopBasicSetting.getShopProfile());
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part ShopProfile = MultipartBody.Part.createFormData("ShopProfile", file.getName(), requestBody); //for send an image as multipart

        RequestBody Product_Category_id = RequestBody.create(okhttp3.MultipartBody.FORM, addNewProduct.getProduct_Category_id());
        RequestBody Title = RequestBody.create(okhttp3.MultipartBody.FORM, addNewProduct.getTitle());
        RequestBody Sub_Product_Category_id = RequestBody.create(okhttp3.MultipartBody.FORM, addNewProduct.getSub_Product_Category_id());
        RequestBody Discount_Price = RequestBody.create(okhttp3.MultipartBody.FORM, addNewProduct.getDiscount_Price());
        RequestBody Sku = RequestBody.create(okhttp3.MultipartBody.FORM, addNewProduct.getSku());
        RequestBody Summary = RequestBody.create(okhttp3.MultipartBody.FORM, addNewProduct.getSummary());
        RequestBody ProfileId = RequestBody.create(okhttp3.MultipartBody.FORM, addNewProduct.getProfileId());
        RequestBody ProductStatus = RequestBody.create(okhttp3.MultipartBody.FORM, addNewProduct.getProductStatus());
        RequestBody Price = RequestBody.create(okhttp3.MultipartBody.FORM, addNewProduct.getPrice());


        getRetrofitClient().addNewProducts("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(),
                null,
                Product_Category_id,
                Title,
                Sub_Product_Category_id,
                Discount_Price,
                Sku,
                Summary,
                ProfileId,
                ProductStatus,
                Price).enqueue(new Callback<AddNewProductResponse>() {
            @Override
            public void onResponse(Call<AddNewProductResponse> call, Response<AddNewProductResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        AddNewProductResponse addNewProductResponse = new AddNewProductResponse();
                        addNewProductResponseMutableLiveData.setValue(addNewProductResponse);
                        response.code();
                    }
                } else {
                    addNewProductResponseMutableLiveData.setValue(null);
                    response.code();
                }
            }

            @Override
            public void onFailure(Call<AddNewProductResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void productForEdit(MutableLiveData<ProductForEditResponse> productForEditMutableLiveData, ProductForEdit productForEdit) {
        getRetrofitClient().getProductForEdit("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), productForEdit.getProfileId(), productForEdit.getProductId()).enqueue(new Callback<ProductForEditResponse>() {
            @Override
            public void onResponse(Call<ProductForEditResponse> call, Response<ProductForEditResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        ProductForEditResponse productForEditResponse = response.body();
                        productForEditMutableLiveData.setValue(productForEditResponse);
                    }
                } else { //incase response is null
                    productForEditMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ProductForEditResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updateProduct(MutableLiveData<UpdateProductResponse> updateProductResponseMutableLiveData, UpdateProduct updateProduct) {

//        File file = new File(shopBasicSetting.getShopProfile());
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part Product_Pic = MultipartBody.Part.createFormData("ShopProfile", file.getName(), requestBody); //for send an image as multipart

        //  RequestBody Product_Category_id = RequestBody.create(okhttp3.MultipartBody.FORM, updateProduct.getProduct_Category_id());
        RequestBody Title = RequestBody.create(okhttp3.MultipartBody.FORM, updateProduct.getTitle());
        //   RequestBody Sub_Product_Category_id = RequestBody.create(okhttp3.MultipartBody.FORM, updateProduct.getSub_Product_Category_id());
        RequestBody Discount_Price = RequestBody.create(okhttp3.MultipartBody.FORM, updateProduct.getDiscount_Price());
        RequestBody Sku = RequestBody.create(okhttp3.MultipartBody.FORM, updateProduct.getSku());
        RequestBody Summary = RequestBody.create(okhttp3.MultipartBody.FORM, updateProduct.getSummary());
        RequestBody ProfileId = RequestBody.create(okhttp3.MultipartBody.FORM, Constant.PROFILE_ID);
        RequestBody ProductStatus = RequestBody.create(okhttp3.MultipartBody.FORM, updateProduct.getProductStatus());
        RequestBody Price = RequestBody.create(okhttp3.MultipartBody.FORM, updateProduct.getPrice());
        RequestBody ProductId = RequestBody.create(okhttp3.MultipartBody.FORM, updateProduct.getProductId());


        getRetrofitClient().updateProduct("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(),
                null,
                Title,
                Discount_Price,
                Sku,
                Summary,
                ProfileId,
                ProductStatus,
                Price,
                ProductId).enqueue(new Callback<UpdateProductResponse>() {
            @Override
            public void onResponse(Call<UpdateProductResponse> call, Response<UpdateProductResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        updateProductResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateProductResponse> call, Throwable t) {

            }
        });


    }

    public void productList(MutableLiveData<ProductListResponse> productListResponseMutableLiveData, ProductList productList) {
        getRetrofitClient().getProductList("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), productList.getProfileId()).enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        ProductListResponse productListResponse = response.body();
                        productListResponseMutableLiveData.setValue(productListResponse);
                    }
                } else {
                    productListResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    public void viewFullOrder(MutableLiveData<ViewFullOrderResponse> viewFullOrderMutableLiveData, ViewFullOrder viewFullOrder) {
        getRetrofitClient().viewfullorder("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), viewFullOrder).enqueue(new Callback<ViewFullOrderResponse>() {
            @Override
            public void onResponse(Call<ViewFullOrderResponse> call, Response<ViewFullOrderResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        ViewFullOrderResponse viewFullOrderResponse = response.body();
                        viewFullOrderMutableLiveData.setValue(viewFullOrderResponse);
                    }
                } else {
                    viewFullOrderMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ViewFullOrderResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updateRiderInfo(MutableLiveData<UpdateRiderInfoResponse> updateRiderInfoMutableLiveData, UpdateRiderInfo updateRiderInfo) {
        getRetrofitClient().updateRiderInformation("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), updateRiderInfo).enqueue(new Callback<UpdateRiderInfoResponse>() {
            @Override
            public void onResponse(Call<UpdateRiderInfoResponse> call, Response<UpdateRiderInfoResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        UpdateRiderInfoResponse updateRiderInfoResponse = response.body();
                        updateRiderInfoMutableLiveData.setValue(updateRiderInfoResponse);
                    }
                } else {
                    updateRiderInfoMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UpdateRiderInfoResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getShopDetails(MutableLiveData<GetShopDetailResponse> getShopDetailResponseMutableLiveData, GetShopDetail shopDetail) {
        getRetrofitClient().getShopDetail("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), shopDetail).enqueue(new Callback<GetShopDetailResponse>() {
            @Override
            public void onResponse(Call<GetShopDetailResponse> call, Response<GetShopDetailResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        GetShopDetailResponse getShopDetailResponse = response.body();
                        getShopDetailResponseMutableLiveData.setValue(getShopDetailResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetShopDetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void updateOrderStatus(MutableLiveData<UpdateOrderStatusResponse> updateOrderStatusResponseMutableLiveData, UpdateOrderStatus updateOrderStatus) {
        getRetrofitClient().updateOrderStatus("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), updateOrderStatus).enqueue(new Callback<UpdateOrderStatusResponse>() {
            @Override
            public void onResponse(Call<UpdateOrderStatusResponse> call, Response<UpdateOrderStatusResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        UpdateOrderStatusResponse updateOrderStatusResponse = response.body();
                        updateOrderStatusResponseMutableLiveData.setValue(updateOrderStatusResponse);
                    }
                } else {
                    updateOrderStatusResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UpdateOrderStatusResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getOrderbyStatus(MutableLiveData<GetOrderByStatusResponse> getOrderByStatusResponseMutableLiveData, OrderByStatus orderByStatue) {
        getRetrofitClient().getOrderbyStatus("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), orderByStatue).enqueue(new Callback<GetOrderByStatusResponse>() {
            @Override
            public void onResponse(Call<GetOrderByStatusResponse> call, Response<GetOrderByStatusResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        GetOrderByStatusResponse getOrderByStatusResponse = response.body();
                        getOrderByStatusResponseMutableLiveData.setValue(getOrderByStatusResponse);
                    }
                } else {
                    getOrderByStatusResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetOrderByStatusResponse> call, Throwable t) {

            }
        });
    }

    public void updateProductStatus(MutableLiveData<IsProductActiveResponse> isProductActiveResponseMutableLiveData, IsProductActive isProductActive) {
        getRetrofitClient().updateProductStatus("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), isProductActive).enqueue(new Callback<IsProductActiveResponse>() {
            @Override
            public void onResponse(Call<IsProductActiveResponse> call, Response<IsProductActiveResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        isProductActiveResponseMutableLiveData.setValue(response.body());
                    } else {
                        isProductActiveResponseMutableLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<IsProductActiveResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
