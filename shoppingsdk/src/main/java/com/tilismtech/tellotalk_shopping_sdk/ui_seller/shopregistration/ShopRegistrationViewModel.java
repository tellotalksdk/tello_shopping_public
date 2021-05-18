package com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

public class ShopRegistrationViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<GenerateTokenResponse> generateTokenResponseLiveData;
    private MutableLiveData<ShopRegisterResponse> shopRegisterResponseMutableLiveData;


    public ShopRegistrationViewModel() {
        repository = Repository.getRepository();
        generateTokenResponseLiveData = new MutableLiveData<>();
        shopRegisterResponseMutableLiveData = new MutableLiveData<>();
    }

    //generateTokenCall
    public void postGenerateToken(AccessTokenPojo accessTokenPojo) {
        repository.generateTokenresponse(generateTokenResponseLiveData, accessTokenPojo.getUsername(), accessTokenPojo.getPassword(), accessTokenPojo.getGrant_type(), accessTokenPojo.getprofileId(), accessTokenPojo.getFirstname(), accessTokenPojo.getMiddlename(), accessTokenPojo.getLastname(), accessTokenPojo.getPhone(), accessTokenPojo.getEmail());
    }

    public LiveData<GenerateTokenResponse> getGenerateToken() {
        return generateTokenResponseLiveData;
    }

    //shopRegistrationCall
    public void postShopRegister(ShopRegister shopRegister) {
        repository.registerShop(shopRegisterResponseMutableLiveData, shopRegister);
    }

    public LiveData<ShopRegisterResponse> getShopResponse() {
        return shopRegisterResponseMutableLiveData;
    }



}
