package com.tilismtech.tellotalk_shopping_sdk.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetTimings;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateRiderInfo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetTimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

        ShopBasicSetting shopBasicSetting1 = shopBasicSetting;

        File file = new File(shopBasicSetting.getShopProfile());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part ShopProfile = MultipartBody.Part.createFormData("ShopProfile", file.getName(), requestBody); //for send an image as multipart

        RequestBody ShippingFee = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShippingFee());
        RequestBody tax = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getTax());
        RequestBody Province = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getProvince());
        RequestBody Area = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getArea());
        RequestBody City = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShippingFee());
        RequestBody Country = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShippingFee());
        RequestBody Shop_Theme = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShippingFee());
        RequestBody ProfileId = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShippingFee());

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
       /* getRetrofitClient().getShopTiming("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), getTimings).enqueue(new Callback<GetTimingsResponse>() {
            @Override
            public void onResponse(Call<GetTimingsResponse> call, Response<GetTimingsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        System.out.println(response.body().getStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetTimingsResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: ");
            }
        });*/
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
        getRetrofitClient().getSubcategoryByParentID("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), parentID).enqueue(new Callback<SubCategoryBYParentCatIDResponse>() {
            @Override
            public void onResponse(Call<SubCategoryBYParentCatIDResponse> call, Response<SubCategoryBYParentCatIDResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        SubCategoryBYParentCatIDResponse subCategoryBYParentCatIDResponse = response.body();
                        subCategoryBYParentCatIDResponseMutableLiveData.setValue(subCategoryBYParentCatIDResponse);
                    }
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

        RequestBody Product_Category_id = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getProduct_Category_id());
        RequestBody Title = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getTitle());
        RequestBody Sub_Product_Category_id = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getSub_Product_Category_id());
        RequestBody Discount_Price = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getDiscount_Price());
        RequestBody Sku = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getSku());
        RequestBody Summary = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getSummary());
        RequestBody ProfileId = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getProfileId());
        RequestBody ProductStatus = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getProductStatus());
        RequestBody Price = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getPrice());


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
                }
            }

            @Override
            public void onFailure(Call<AddNewProductResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

}
