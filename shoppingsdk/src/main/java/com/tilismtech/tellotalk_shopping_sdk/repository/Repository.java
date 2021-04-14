package com.tilismtech.tellotalk_shopping_sdk.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteBranchAddress;
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
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteBranchAddressResponse;
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
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

        //  boundary = UUID.randomUUID().toString();

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


        getRetrofitClient().addNewProducts("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(),
                Product_Pic,
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

        List<MultipartBody.Part> Product_Pic = getAllImages(updateProduct.getProduct_Pic());

    /*    File file = new File(updateProduct.getProduct_Pic().get(0));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part Product_Pic = MultipartBody.Part.createFormData("Product_Pic", file.getName(), requestBody); //for send an image as multipart
*/
        RequestBody Product_Category_id = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getProduct_Category_id());
        RequestBody Sub_Product_Category_id = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getSub_Product_Category_id());
        RequestBody Title = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getTitle());
        RequestBody Discount_Price = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getDiscount_Price());
        RequestBody Sku = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getDiscount_Price());
        RequestBody Summary = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getSummary());
        RequestBody ProfileId = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getProfileId());
        RequestBody ProductStatus = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getProductStatus());
        RequestBody Price = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getPrice());
        RequestBody ProductId = RequestBody.create(MediaType.parse("text/plain"), updateProduct.getProductId());

        getRetrofitClient().updateProduct("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(),
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
                ProductId).enqueue(new Callback<UpdateProductResponse>() {
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

    //this method will return list of images to send toward server either one or two both will work...
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

        getRetrofitClient().viewfullorder("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), viewFullOrder.getProfileId(), viewFullOrder.getOrderId(), viewFullOrder.getOrderStatus()).enqueue(new Callback<ViewFullOrderResponse>() {
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

        getRetrofitClient().getShopDetail("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), shopDetail.getProfileId()).enqueue(new Callback<GetShopDetailResponse>() {
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

    public void updateOrderStatus(MutableLiveData<UpdateOrderStatusResponse> updateOrderStatusResponseMutableLiveData, UpdateOrderStatus updateOrderStatus) {
        updateOrderStatus.getOrderId();
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

    public void getOrderbyStatus(MutableLiveData<GetOrderByStatusResponse> getOrderByStatusResponseMutableLiveData, OrderByStatus orderByStatus) {

        getRetrofitClient().getOrderbyStatus("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), orderByStatus.getProfileId(), orderByStatus.getStatus()).enqueue(new Callback<GetOrderByStatusResponse>() {
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

    public void getAllOrders(MutableLiveData<GetAllOrderResponse> getAllOrderResponseMutableLiveData, String ProfileId) {
        getRetrofitClient().getAllOrderList("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), ProfileId).enqueue(new Callback<GetAllOrderResponse>() {
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

    public void getAllStatusCount(MutableLiveData<GetOrderStatusCountResponse> getOrderStatusCountResponseMutableLiveData) {
        getRetrofitClient().getOrderAllStatusCount("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), Constant.PROFILE_ID).enqueue(new Callback<GetOrderStatusCountResponse>() {
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

    public void postTiming(MutableLiveData<ShopTimingResponse> shopTimingResponseMutableLiveData, ShopTiming shopTiming) {
        getRetrofitClient().postTiming("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), shopTiming).enqueue(new Callback<ShopTimingResponse>() {
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

    public void addBranchAddress(MutableLiveData<AddBranchAddressResponse> addBranchAddressResponseMutableLiveData, AddBranchAddress addBranchAddress) {
        getRetrofitClient().addBranchAddress("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), addBranchAddress).enqueue(new Callback<AddBranchAddressResponse>() {
            @Override
            public void onResponse(Call<AddBranchAddressResponse> call, Response<AddBranchAddressResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        addBranchAddressResponseMutableLiveData.setValue(response.body());
                    }
                }else{
                    addBranchAddressResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AddBranchAddressResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updateBranchAddress(MutableLiveData<UpdateBranchAddressResponse> updateBranchAddressResponseMutableLiveData, UpdateBranchAddress updateBranchAddress) {
        getRetrofitClient().updateBranchAddress("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), updateBranchAddress).enqueue(new Callback<UpdateBranchAddressResponse>() {
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

    public void deleteBranchAddress(MutableLiveData<DeleteBranchAddressResponse> deleteBranchAddressMutableLiveData, DeleteBranchAddress deleteBranchAddress) {
        getRetrofitClient().deleteBranchAddress("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), deleteBranchAddress).enqueue(new Callback<DeleteBranchAddressResponse>() {
            @Override
            public void onResponse(Call<DeleteBranchAddressResponse> call, Response<DeleteBranchAddressResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        deleteBranchAddressMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteBranchAddressResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
