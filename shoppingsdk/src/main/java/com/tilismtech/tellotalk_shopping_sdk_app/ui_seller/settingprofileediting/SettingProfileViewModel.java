package com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.settingprofileediting;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.repository.Repository;

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
