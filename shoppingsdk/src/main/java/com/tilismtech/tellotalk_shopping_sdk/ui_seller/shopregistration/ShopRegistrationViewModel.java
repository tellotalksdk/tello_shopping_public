package com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopRegister;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateUserAndImage;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateUserAndImageResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.VerifyOtpResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

public class ShopRegistrationViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<GenerateTokenResponse> generateTokenResponseLiveData;
    private MutableLiveData<ShopRegisterResponse> shopRegisterResponseMutableLiveData;
    private MutableLiveData<UpdateUserAndImageResponse> updateUserAndImageResponseMutableLiveData;
    private MutableLiveData<VerifyOtpResponse> verifyOtpResponseMutableLiveData;
    private MutableLiveData<VerifyOtpResponse> resendOtpResponseMutableLiveData;


    public ShopRegistrationViewModel() {
        repository = Repository.getRepository();
        generateTokenResponseLiveData = new MutableLiveData<>();
        shopRegisterResponseMutableLiveData = new MutableLiveData<>();
        updateUserAndImageResponseMutableLiveData = new MutableLiveData<>();
        verifyOtpResponseMutableLiveData = new MutableLiveData<>();
        resendOtpResponseMutableLiveData = new MutableLiveData<>();
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

    //updateuserimageandname
    public void userImageandName(UpdateUserAndImage updateUserAndImage , Context context) {
        repository.updateUserName_Image(updateUserAndImageResponseMutableLiveData, updateUserAndImage , context);
    }

    public LiveData<UpdateUserAndImageResponse> getUpdateUserImageResponse() {
        return updateUserAndImageResponseMutableLiveData;
    }

    //updateusernameonly
    public void userName(UpdateUserAndImage updateUserAndImage, Context context) {
        repository.updateUserName(updateUserAndImageResponseMutableLiveData, updateUserAndImage,context);
    }

    public LiveData<UpdateUserAndImageResponse> getUserName() {
        return updateUserAndImageResponseMutableLiveData;
    }


    //verifyOTP
    public void verifyOTP(String otp,Context context) {
        repository.verifyOTP(verifyOtpResponseMutableLiveData, otp , context);
    }

    public LiveData<VerifyOtpResponse> getVerifyOtp() {
        return verifyOtpResponseMutableLiveData;
    }

    //resendOTP
    public void resendOTP(Context context) {
        repository.resendOTP(resendOtpResponseMutableLiveData,context);
    }

    public LiveData<VerifyOtpResponse> getresendOtp() {
        return resendOtpResponseMutableLiveData;
    }

}
