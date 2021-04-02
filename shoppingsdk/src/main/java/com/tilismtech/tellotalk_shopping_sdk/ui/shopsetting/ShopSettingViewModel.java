package com.tilismtech.tellotalk_shopping_sdk.ui.shopsetting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

public class ShopSettingViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ShopBasicSettingResponse> shopBasicSettingResponseMutableLiveData;


    public ShopSettingViewModel() {
        repository = Repository.getRepository();
        shopBasicSettingResponseMutableLiveData = new MutableLiveData<>();
    }

    public void postShopSettingDetails(ShopBasicSetting shopBasicSetting) {
        repository.setShopBasicSetting(shopBasicSettingResponseMutableLiveData, shopBasicSetting);
    }

    public MutableLiveData<ShopBasicSettingResponse> getShopSettingResponse() {
        return shopBasicSettingResponseMutableLiveData;
    }

}
