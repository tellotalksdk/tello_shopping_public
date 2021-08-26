package com.tilismtech.tellotalk_shopping_sdk.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.listeners.OnSuccessListener;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddBank;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddWallet;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteCardorWallet;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteProductImage;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GenerateToken;
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
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopTiming;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateOrderStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateRiderInfo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateUserAndImage;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddBankResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddWalletResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.BankListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ClientWalletDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ColorThemeResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteBankResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteProductImageResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GTResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetAllOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderStatusCountResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetTimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetUserBankDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductForEditResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopExistResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopNameAndImageResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopTimingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.TimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.TotalProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateOrderStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateRiderInfoResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateUserAndImageResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.VerifyOtpResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ViewFullOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.WalletListResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui_client.pojo_client.response.AllCatList_UnderShop;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

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

    //region firstTokenApi
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
    //endregion

    //region secondTokenApi
    public void generateTokenResponse(MutableLiveData<GTResponse> gtResponseMutableLiveData, GenerateToken generateToken) {
        getRetrofitClient().generateToken(generateToken).enqueue(new Callback<GTResponse>() {
            @Override
            public void onResponse(Call<GTResponse> call, Response<GTResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        GTResponse gtResponse = response.body();
                        gtResponseMutableLiveData.setValue(gtResponse);
                    } else {
                        Log.i("TAG", "onResponse: " + response.code()); //500 code occur but run
                        gtResponseMutableLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<GTResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region updateUserNameImage
    public void updateUserName_Image(MutableLiveData<UpdateUserAndImageResponse> updateUserAndImageResponseMutableLiveData, UpdateUserAndImage updateUserAndImage, Context myCtx) {

        File file = new File(updateUserAndImage.getProfilePic());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part profilePic = MultipartBody.Part.createFormData("profilePic", file.getName(), requestBody); //for send an image as multipart

        RequestBody firstName = RequestBody.create(MediaType.parse("text/plain"), updateUserAndImage.getFirstName());
        RequestBody middleName = RequestBody.create(MediaType.parse("text/plain"), updateUserAndImage.getMiddleName());
        RequestBody lastName = RequestBody.create(MediaType.parse("text/plain"), updateUserAndImage.getLastName());
        RequestBody profileId = RequestBody.create(MediaType.parse("text/plain"), updateUserAndImage.getProfileId());

        getRetrofitClient().updateUserImageAndName("Bearer " + TelloPreferenceManager.getInstance(myCtx).getAccessToken(),
                firstName, middleName, lastName, profileId, profilePic).enqueue(new Callback<UpdateUserAndImageResponse>() {
            @Override
            public void onResponse(Call<UpdateUserAndImageResponse> call, Response<UpdateUserAndImageResponse> response) {
                if (response.isSuccessful()) {
                    if (response != null) {
                        UpdateUserAndImageResponse updateUserAndImage1 = response.body();
                        updateUserAndImageResponseMutableLiveData.setValue(updateUserAndImage1);
                    } else {
                        updateUserAndImageResponseMutableLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateUserAndImageResponse> call, Throwable t) {
                t.printStackTrace();
                updateUserAndImageResponseMutableLiveData.setValue(null);
            }
        });

    }
    //endregion


    //region getuserNameresponse
    public void updateUserName(MutableLiveData<UpdateUserAndImageResponse> updateUserAndImageResponseMutableLiveData, UpdateUserAndImage updateUserAndImage, Context context) {

        File file = new File(updateUserAndImage.getProfilePic());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part profilePic = MultipartBody.Part.createFormData("profilePic", file.getName(), requestBody); //for send an image as multipart

        RequestBody firstName = RequestBody.create(MediaType.parse("text/plain"), updateUserAndImage.getFirstName());
        RequestBody middleName = RequestBody.create(MediaType.parse("text/plain"), updateUserAndImage.getMiddleName());
        RequestBody lastName = RequestBody.create(MediaType.parse("text/plain"), updateUserAndImage.getLastName());
        RequestBody profileId = RequestBody.create(MediaType.parse("text/plain"), updateUserAndImage.getProfileId());

        getRetrofitClient().updateUserName("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(),
                firstName, middleName, lastName, profileId).enqueue(new Callback<UpdateUserAndImageResponse>() {
            @Override
            public void onResponse(Call<UpdateUserAndImageResponse> call, Response<UpdateUserAndImageResponse> response) {
                if (response.isSuccessful()) {
                    if (response != null) {
                        UpdateUserAndImageResponse updateUserAndImage1 = response.body();
                        updateUserAndImageResponseMutableLiveData.setValue(updateUserAndImage1);
                    } else {
                        updateUserAndImageResponseMutableLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateUserAndImageResponse> call, Throwable t) {
                t.printStackTrace();
                updateUserAndImageResponseMutableLiveData.setValue(null);
            }
        });

    }

    //endregion

    //region getShopNameandImage
    public void getShopNameAndImage(MutableLiveData<ShopNameAndImageResponse> shopNameAndImageResponseMutableLiveData, Context context) {
        getRetrofitClient().getShopNameAndImage("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), Constant.PROFILE_ID).enqueue(new Callback<ShopNameAndImageResponse>() {
            @Override
            public void onResponse(Call<ShopNameAndImageResponse> call, Response<ShopNameAndImageResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        shopNameAndImageResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ShopNameAndImageResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region productCount
    public void getProductCount(MutableLiveData<TotalProductResponse> totalProductResponseMutableLiveData, Context context) {
        getRetrofitClient().getTotalProductCount("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), Constant.PROFILE_ID).enqueue(new Callback<TotalProductResponse>() {
            @Override
            public void onResponse(Call<TotalProductResponse> call, Response<TotalProductResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        totalProductResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<TotalProductResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region shopExistence
    public void isShopExist(String profileId) {
        getRetrofitClient().isShopExist("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), profileId).enqueue(new Callback<ShopExistResponse>() {
            @Override
            public void onResponse(Call<ShopExistResponse> call, Response<ShopExistResponse> response) {

            }

            @Override
            public void onFailure(Call<ShopExistResponse> call, Throwable t) {

            }
        });
    }
    //endregion

    //region registerShop
    public void registerShop(MutableLiveData<ShopRegisterResponse> shopRegisterResponseMutableLiveData, ShopRegister shopRegister) {
        getRetrofitClient().shopRegister("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), shopRegister).enqueue(new Callback<ShopRegisterResponse>() {
            @Override
            public void onResponse(Call<ShopRegisterResponse> call, Response<ShopRegisterResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        ShopRegisterResponse shopRegisterResponse = response.body();
                        shopRegisterResponseMutableLiveData.setValue(shopRegisterResponse);
                    } else {
                        ShopRegisterResponse message = new Gson().fromJson(response.errorBody().charStream(), ShopRegisterResponse.class);
                        Log.i("TAG", "onResponse: " + message.toString());
                        shopRegisterResponseMutableLiveData.setValue(message);
                    }
                }

                Log.i("TAG", "onResponse: ");
            }

            @Override
            public void onFailure(Call<ShopRegisterResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region verifyOtp
    public void verifyOTP(MutableLiveData<VerifyOtpResponse> verifyOtpResponseMutableLiveData, String otp, Context context , String contact) {
        getRetrofitClient().verifyOTP("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), contact, otp).enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        verifyOtpResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region reSendotp
    public void resendOTP(MutableLiveData<VerifyOtpResponse> resendOtp, Context context,String contact) {
        Constant constant = new Constant();
        getRetrofitClient().resendOTP("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), contact).enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        resendOtp.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region shopBasicSetting
    public void setShopBasicSetting(MutableLiveData<ShopBasicSettingResponse> shopBasicSettingResponseMutableLiveData, ShopBasicSetting shopBasicSetting, Context myContext) {

        //  boundary = UUID.randomUUID().toString();

        File file = new File(shopBasicSetting.getShopProfile());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part ShopProfile = MultipartBody.Part.createFormData("shopProfile", file.getName(), requestBody); //for send an image as multipart

        RequestBody ShippingFee = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShippingFee());
        RequestBody tax = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getTax());
        RequestBody Province = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getProvince());
        RequestBody Area = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getArea());
        RequestBody City = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getCity());
        RequestBody Country = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getCountry());
        RequestBody Shop_Theme = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShop_Theme());
        RequestBody ProfileId = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getProfileId());
        RequestBody Lat = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getLat());
        RequestBody Long = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getLong());

        getRetrofitClient().setShopBasicSetting("Bearer " + TelloPreferenceManager.getInstance(myContext).getAccessToken(),
                ShopProfile, ShippingFee, tax, Province, Area, City, Country, Shop_Theme, ProfileId, Lat, Long
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
                shopBasicSettingResponseMutableLiveData.setValue(null);
            }
        });
        System.out.println();

    }
    //endregion

    //region shopBasicSetting
    public void setShopBasicSettingWithOutImage(MutableLiveData<ShopBasicSettingResponse> shopBasicSettingResponseMutableLiveData, ShopBasicSetting shopBasicSetting, Context myContext) {

        //  boundary = UUID.randomUUID().toString();

        File file = new File(shopBasicSetting.getShopProfile());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part ShopProfile = MultipartBody.Part.createFormData("shopProfile", file.getName(), requestBody); //for send an image as multipart

        RequestBody ShippingFee = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShippingFee());
        RequestBody tax = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getTax());
        RequestBody Province = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getProvince());
        RequestBody Area = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getArea());
        RequestBody City = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getCity());
        RequestBody Country = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getCountry());
        RequestBody Shop_Theme = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getShop_Theme());
        RequestBody ProfileId = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getProfileId());
        RequestBody Lat = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getLat());
        RequestBody Long = RequestBody.create(MediaType.parse("text/plain"), shopBasicSetting.getLong());

        getRetrofitClient().setShopBasicSetting_WithOut_Image("Bearer " + TelloPreferenceManager.getInstance(myContext).getAccessToken(),
                 ShippingFee, tax, Province, Area, City, Country, Shop_Theme, ProfileId, Lat, Long
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
                shopBasicSettingResponseMutableLiveData.setValue(null);
            }
        });
        System.out.println();

    }
    //endregion

    //region getTimings
    public void getTimings(MutableLiveData<GetTimingsResponse> getTimingsResponseMutableLiveData, GetTimings getTimings, Context context) {
        getRetrofitClient().getShopTiming("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), getTimings.getProfileId()).enqueue(new Callback<GetTimingsResponse>() {
            @Override
            public void onResponse(Call<GetTimingsResponse> call, Response<GetTimingsResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        GetTimingsResponse getTimingsResponse = response.body();
                        getTimingsResponseMutableLiveData.setValue(getTimingsResponse);
                    } else {
                        getTimingsResponseMutableLiveData.setValue(null);
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
    //endregion

    //region getparentCategories
    public void postTogetParentCategories(MutableLiveData<ParentCategoryListResponse> parentCategoryListResponseMutableLiveData, Context context) {
        getRetrofitClient().getParentCategories("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken()).enqueue(new Callback<ParentCategoryListResponse>() {
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
    //endregion

    //region getChildCategories
    public void postTogetChildCategories(MutableLiveData<SubCategoryBYParentCatIDResponse> subCategoryBYParentCatIDResponseMutableLiveData, SubCategoryBYParentCatID parentID, Context context) {
        getRetrofitClient().getSubcategoryByParentID("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), parentID.getParentCategoryId()).enqueue(new Callback<SubCategoryBYParentCatIDResponse>() {
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
    //endregion

    //region addNewProduct
    public void addNewProducts(MutableLiveData<AddNewProductResponse> addNewProductResponseMutableLiveData, AddNewProduct addNewProduct, Context context) {

       /* File file = new File(addNewProduct.getProduct_Pic().get(0));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part Product_Pic = MultipartBody.Part.createFormData("Product_Pic", file.getName(), requestBody); //for send an image as multipart
*/

        List<MultipartBody.Part> Product_Pic = getAllImages(addNewProduct.getProduct_Pic());

        RequestBody Product_Category_id = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getProduct_Category_id());
        RequestBody Sub_Product_Category_id = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getSub_Product_Category_id());
        RequestBody Title = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getTitle());
        RequestBody Discount_Price = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getDiscount_Price());
        RequestBody Sku = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getSku());
        RequestBody Summary = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getSummary());
        RequestBody ProfileId = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getProfileId());
        RequestBody ProductStatus = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getProductStatus());
        RequestBody Price = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getPrice());
        RequestBody videoName = RequestBody.create(MediaType.parse("text/plain"), addNewProduct.getVideoName());


        getRetrofitClient().addNewProducts("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(),
                Product_Pic,
                Product_Category_id,
                Title,
                Sub_Product_Category_id,
                Discount_Price,
                Sku,
                Summary,
                ProfileId,
                ProductStatus,
                Price,
                videoName).enqueue(new Callback<AddNewProductResponse>() {
            @Override
            public void onResponse(Call<AddNewProductResponse> call, Response<AddNewProductResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        AddNewProductResponse addNewProductResponse = new AddNewProductResponse();
                        addNewProductResponseMutableLiveData.setValue(addNewProductResponse);
                        response.code();
                    } else {
                        addNewProductResponseMutableLiveData.setValue(null);
                    }
                } else {
                    addNewProductResponseMutableLiveData.setValue(null);
                    response.code();
                }
            }

            @Override
            public void onFailure(Call<AddNewProductResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
                addNewProductResponseMutableLiveData.setValue(null);
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region productForEdit
    public void productForEdit(MutableLiveData<ProductForEditResponse> productForEditMutableLiveData, ProductForEdit productForEdit, Context context) {
        getRetrofitClient().getProductForEdit("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), productForEdit.getProfileId(), productForEdit.getProductId()).enqueue(new Callback<ProductForEditResponse>() {
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
    //endregion

    //region updateProduct
    public void updateProduct(MutableLiveData<UpdateProductResponse> updateProductResponseMutableLiveData, UpdateProduct updateProduct, Context context) {

        List<MultipartBody.Part> Product_Pic = getAllImages(updateProduct.getProduct_Pic());

        /*    File file = new File(updateProduct.getProduct_Pic().get(0));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part Product_Pic = MultipartBody.Part.createFormData("Product_Pic", file.getName(), requestBody); //for send an image as multipart
*/
        RequestBody Product_Category_id = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getParentProductCategoryId());
        RequestBody Sub_Product_Category_id = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getProductCategoryId());
        RequestBody Title = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getTitle());
        RequestBody Discount_Price = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getDiscountPrice());
        RequestBody Sku = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getSku());
        RequestBody Summary = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getSummary());
        RequestBody ProfileId = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getProfileId());
        RequestBody ProductStatus = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getProductStatus());
        RequestBody Price = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getPrice());
        RequestBody ProductId = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getProductId());
        RequestBody videoLink = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getVideoLink());

        getRetrofitClient().updateProduct("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(),
                Product_Pic,
                Product_Category_id,
                Title,
                Sub_Product_Category_id,
                Discount_Price,
                Sku,
                Summary,
                ProfileId,
                ProductStatus,
                Price,
                ProductId,
                videoLink).enqueue(new Callback<UpdateProductResponse>() {
            @Override
            public void onResponse(Call<UpdateProductResponse> call, Response<UpdateProductResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        UpdateProductResponse updateProductResponse = response.body();
                        updateProductResponseMutableLiveData.setValue(updateProductResponse);
                    }
                } else {
                    updateProductResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UpdateProductResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
    //endregion


    //this method will return list of images to send toward server either one image or more both will work...
    //region getAllImages
    public List<MultipartBody.Part> getAllImages(List<String> product_pic) {
        List<MultipartBody.Part> parts = new ArrayList<>();

        for (int i = 0; i < product_pic.size(); i++) {
            File file = new File(product_pic.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part Product_Pic = MultipartBody.Part.createFormData("Product_Pic", file.getName(), requestBody); //for send an image as multipart
            parts.add(Product_Pic);
        }


        return parts;
    }
    //endregion

    //region productList
    public void productList(MutableLiveData<ProductListResponse> productListResponseMutableLiveData, ProductList productList, String lastProductId, Context context) {
        getRetrofitClient().getProductList("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), productList.getProfileId(), lastProductId).enqueue(new Callback<ProductListResponse>() {
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
    //endregion

    //region viewFullorder
    public void viewFullOrder(MutableLiveData<ViewFullOrderResponse> viewFullOrderMutableLiveData, ViewFullOrder viewFullOrder, Context context) {

        getRetrofitClient().viewfullorder("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), viewFullOrder.getProfileId(), viewFullOrder.getOrderId(), viewFullOrder.getOrderStatus()).enqueue(new Callback<ViewFullOrderResponse>() {
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
    //endregion

    //region updateRiderInfo
    public void updateRiderInfo(MutableLiveData<UpdateRiderInfoResponse> updateRiderInfoMutableLiveData, UpdateRiderInfo updateRiderInfo, Context context) {
        getRetrofitClient().updateRiderInformation("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), updateRiderInfo).enqueue(new Callback<UpdateRiderInfoResponse>() {
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
    //endregion

    //region getShopDetail
    public void getShopDetails(MutableLiveData<GetShopDetailResponse> getShopDetailResponseMutableLiveData, GetShopDetail shopDetail, Context context) {

        getRetrofitClient().getShopDetail("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), shopDetail.getProfileId()).enqueue(new Callback<GetShopDetailResponse>() {
            @Override
            public void onResponse(Call<GetShopDetailResponse> call, Response<GetShopDetailResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        GetShopDetailResponse getShopDetailResponse = response.body();
                        getShopDetailResponseMutableLiveData.setValue(getShopDetailResponse);
                    }
                } else {
                    getShopDetailResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetShopDetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region updateOrderStatus
    public void updateOrderStatus(MutableLiveData<UpdateOrderStatusResponse> updateOrderStatusResponseMutableLiveData, UpdateOrderStatus updateOrderStatus, Context context) {
        updateOrderStatus.getOrderId();
        getRetrofitClient().updateOrderStatus("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), updateOrderStatus).enqueue(new Callback<UpdateOrderStatusResponse>() {
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
    //endregion

    //region getOrderByStatus
    public void getOrderbyStatus(MutableLiveData<GetOrderByStatusResponse> getOrderByStatusResponseMutableLiveData, OrderByStatus orderByStatus, Context context) {

        getRetrofitClient().getOrderbyStatus("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), orderByStatus.getProfileId(), orderByStatus.getStatus()).enqueue(new Callback<GetOrderByStatusResponse>() {
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
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region updateProductStatus
    public void updateProductStatus(MutableLiveData<IsProductActiveResponse> isProductActiveResponseMutableLiveData, IsProductActive isProductActive, Context context) {
        getRetrofitClient().updateProductStatus("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), isProductActive).enqueue(new Callback<IsProductActiveResponse>() {
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
    //endregion

    //region getAllorders
    public void getAllOrders(MutableLiveData<GetAllOrderResponse> getAllOrderResponseMutableLiveData, String ProfileId, Context context) {
        getRetrofitClient().getAllOrderList("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), ProfileId).enqueue(new Callback<GetAllOrderResponse>() {
            @Override
            public void onResponse(Call<GetAllOrderResponse> call, Response<GetAllOrderResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        getAllOrderResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllOrderResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region getAllStatusCount
    public void getAllStatusCount(MutableLiveData<GetOrderStatusCountResponse> getOrderStatusCountResponseMutableLiveData, Context context) {
        getRetrofitClient().getOrderAllStatusCount("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), Constant.PROFILE_ID).enqueue(new Callback<GetOrderStatusCountResponse>() {
            @Override
            public void onResponse(Call<GetOrderStatusCountResponse> call, Response<GetOrderStatusCountResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        GetOrderStatusCountResponse GetOrderStatusCountResponse = response.body();
                        getOrderStatusCountResponseMutableLiveData.setValue(GetOrderStatusCountResponse);
                    }
                } else {
                    getOrderStatusCountResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetOrderStatusCountResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region postTiming
    public void postTiming(MutableLiveData<ShopTimingResponse> shopTimingResponseMutableLiveData, ShopTiming shopTiming, Context context) {
        getRetrofitClient().postTiming("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), shopTiming).enqueue(new Callback<ShopTimingResponse>() {
            @Override
            public void onResponse(Call<ShopTimingResponse> call, Response<ShopTimingResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        ShopTimingResponse shopTimingResponse = response.body();
                        shopTimingResponseMutableLiveData.setValue(shopTimingResponse);
                    } else {
                        ShopTimingResponse shopTimingResponse = new ShopTimingResponse();
                        shopTimingResponse.setMessage("Some thing went wrong" + " : " + response.raw().networkResponse().code());
                        shopTimingResponseMutableLiveData.setValue(shopTimingResponse);
                    }
                } else {
                    shopTimingResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ShopTimingResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region postBranchAddress
    public void addBranchAddress(MutableLiveData<AddBranchAddressResponse> addBranchAddressResponseMutableLiveData, AddBranchAddress addBranchAddress, Context context) {
        getRetrofitClient().addBranchAddress("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), addBranchAddress).enqueue(new Callback<AddBranchAddressResponse>() {
            @Override
            public void onResponse(Call<AddBranchAddressResponse> call, Response<AddBranchAddressResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        addBranchAddressResponseMutableLiveData.setValue(response.body());
                    }
                } else {
                    addBranchAddressResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AddBranchAddressResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region updateBranchAddress
    public void updateBranchAddress(MutableLiveData<UpdateBranchAddressResponse> updateBranchAddressResponseMutableLiveData, UpdateBranchAddress updateBranchAddress, Context context) {
        getRetrofitClient().updateBranchAddress("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), updateBranchAddress).enqueue(new Callback<UpdateBranchAddressResponse>() {
            @Override
            public void onResponse(Call<UpdateBranchAddressResponse> call, Response<UpdateBranchAddressResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        updateBranchAddressResponseMutableLiveData.setValue(response.body());
                    }
                } else {
                    updateBranchAddressResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UpdateBranchAddressResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region deleteBranchAddress
    public void deleteBranchAddress(MutableLiveData<DeleteBranchAddressResponse> deleteBranchAddressMutableLiveData, DeleteBranchAddress deleteBranchAddress, Context context) {
        getRetrofitClient().deleteBranchAddress("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), deleteBranchAddress).enqueue(new Callback<DeleteBranchAddressResponse>() {
            @Override
            public void onResponse(Call<DeleteBranchAddressResponse> call, Response<DeleteBranchAddressResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        deleteBranchAddressMutableLiveData.setValue(response.body());
                    }
                } else {
                    deleteBranchAddressMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<DeleteBranchAddressResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region deleteProduct
    public void deleteProduct(MutableLiveData<DeleteProductResponse> deleteProductResponseMutableLiveData, DeleteProduct deleteProduct, Context context) {
        getRetrofitClient().deleteProduct("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), deleteProduct).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        DeleteProductResponse deleteProductResponse = response.body();
                        deleteProductResponseMutableLiveData.setValue(deleteProductResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region deleteProductImage
    public void deleteProductImage(MutableLiveData<DeleteProductImageResponse> deleteProduct, DeleteProductImage deleteProductImage, Context context) {
        getRetrofitClient().deleteProductImage("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), deleteProductImage).enqueue(new Callback<DeleteProductImageResponse>() {
            @Override
            public void onResponse(Call<DeleteProductImageResponse> call, Response<DeleteProductImageResponse> response) {
                deleteProduct.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DeleteProductImageResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region colorTheme
    public void getColorTheme(MutableLiveData<ColorThemeResponse> colorThemeResponseMutableLiveData) {
        getRetrofitClient().getColorThemes().enqueue(new Callback<ColorThemeResponse>() {
            @Override
            public void onResponse(Call<ColorThemeResponse> call, Response<ColorThemeResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        ColorThemeResponse colorThemeResponse = response.body();
                        colorThemeResponseMutableLiveData.setValue(colorThemeResponse);
                    } else {
                        Log.i("TAG", "onResponse: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ColorThemeResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region addWallet
    public void addWallet(MutableLiveData<AddWalletResponse> addWalletResponseMutableLiveData, AddWallet addWallet, Context context) {
        getRetrofitClient().addWallet("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), addWallet).enqueue(new Callback<AddWalletResponse>() {
            @Override
            public void onResponse(Call<AddWalletResponse> call, Response<AddWalletResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        addWalletResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<AddWalletResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region addBank
    public void addBank(MutableLiveData<AddBankResponse> addBankResponseMutableLiveData, AddBank addBank, Context context) {
        getRetrofitClient().addBank("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), addBank).enqueue(new Callback<AddBankResponse>() {
            @Override
            public void onResponse(Call<AddBankResponse> call, Response<AddBankResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        addBankResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<AddBankResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }
    //endregion

    //region deleteCardorWallet
    public void deleteCardorWallet(MutableLiveData<DeleteBankResponse> deleteBankResponseMutableLiveData, DeleteCardorWallet deleteCardorWallet, Context context) {
        getRetrofitClient().deleteWalletorCard("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), deleteCardorWallet).enqueue(new Callback<DeleteBankResponse>() {
            @Override
            public void onResponse(Call<DeleteBankResponse> call, Response<DeleteBankResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        deleteBankResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteBankResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region getClientWalletDetail
    public void getClientWalletDetails(MutableLiveData<ClientWalletDetailResponse> clientWalletDetailResponseMutableLiveData, Context context) {
        getRetrofitClient().getClientWalletDetails("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), Constant.PROFILE_ID).enqueue(new Callback<ClientWalletDetailResponse>() {
            @Override
            public void onResponse(Call<ClientWalletDetailResponse> call, Response<ClientWalletDetailResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        clientWalletDetailResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ClientWalletDetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region getClientBankDetail
    public void getClientBankDetails(MutableLiveData<GetUserBankDetailResponse> getUserBankDetailResponseMutableLiveData, Context context) {
        getRetrofitClient().getUserbankDetails("Bearer " + TelloPreferenceManager.getInstance(context).getAccessToken(), Constant.PROFILE_ID).enqueue(new Callback<GetUserBankDetailResponse>() {
            @Override
            public void onResponse(Call<GetUserBankDetailResponse> call, Response<GetUserBankDetailResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        getUserBankDetailResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserBankDetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region getBankList
    public void bankList(MutableLiveData<BankListResponse> bankListResponseMutableLiveData) {
        getRetrofitClient().getBankDetailList().enqueue(new Callback<BankListResponse>() {
            @Override
            public void onResponse(Call<BankListResponse> call, Response<BankListResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        bankListResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BankListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion

    //region getWalletList
    public void walletList(MutableLiveData<WalletListResponse> walletListResponseMutableLiveData) {
        getRetrofitClient().getWalletDetailList().enqueue(new Callback<WalletListResponse>() {
            @Override
            public void onResponse(Call<WalletListResponse> call, Response<WalletListResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        walletListResponseMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //endregion


    //Client Side API started....

    public void getAllCategoryList_UnderShop(String ShopId, MutableLiveData<AllCatList_UnderShop> allCatList_underShopMutableLiveData, Context context) {
        getRetrofitClient().getAllCategoryList_UnderShop(ShopId).enqueue(new Callback<AllCatList_UnderShop>() {
            @Override
            public void onResponse(Call<AllCatList_UnderShop> call, Response<AllCatList_UnderShop> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        allCatList_underShopMutableLiveData.setValue(response.body());
                    }
                } else {
                    allCatList_underShopMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AllCatList_UnderShop> call, Throwable t) {
                t.printStackTrace();
                allCatList_underShopMutableLiveData.setValue(null);
            }
        });
    }

}
