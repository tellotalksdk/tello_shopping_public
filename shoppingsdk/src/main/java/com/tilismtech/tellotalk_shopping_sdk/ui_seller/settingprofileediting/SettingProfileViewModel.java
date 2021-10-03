package com.tilismtech.tellotalk_shopping_sdk.ui_seller.settingprofileediting;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

public class SettingProfileViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ShopBasicSettingResponse> shopBasicSettingResponseMutableLiveData;

    public SettingProfileViewModel() {
        repository = Repository.getRepository();
        this.shopBasicSettingResponseMutableLiveData = new MutableLiveData<>();
    }

    //shop setting
    public void postShopSettingDetails(ShopBasicSetting shopBasicSetting, Context myContext) {
        repository.setShopBasicSetting(shopBasicSettingResponseMutableLiveData, shopBasicSetting, myContext);
    }

    public MutableLiveData<ShopBasicSettingResponse> getShopSettingResponse() {
        return shopBasicSettingResponseMutableLiveData;
    }
}
