package com.tilismtech.tellotalk_shopping_sdk.ui.shopsetting;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetTimings;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopTiming;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetTimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopTimingResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

public class ShopSettingViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ShopBasicSettingResponse> shopBasicSettingResponseMutableLiveData;
    private MutableLiveData<GetTimingsResponse> getTimingsResponseMutableLiveData;
    private MutableLiveData<ShopTimingResponse> shopTimingResponseMutableLiveData;


    public ShopSettingViewModel() {
        repository = Repository.getRepository();
        this.shopBasicSettingResponseMutableLiveData = new MutableLiveData<>();
        this.getTimingsResponseMutableLiveData = new MutableLiveData<>();
        this.shopTimingResponseMutableLiveData = new MutableLiveData<>();
    }

    //shop setting
    public void postShopSettingDetails(ShopBasicSetting shopBasicSetting, Context myContext) {
        repository.setShopBasicSetting(shopBasicSettingResponseMutableLiveData, shopBasicSetting, myContext);
    }

    public MutableLiveData<ShopBasicSettingResponse> getShopSettingResponse() {
        return shopBasicSettingResponseMutableLiveData;
    }

    //get timing

    public void posttogetTimings(GetTimings getTimings) {
        repository.getTimings(getTimingsResponseMutableLiveData, getTimings);
    }

    public MutableLiveData<GetTimingsResponse> gettimings() {
        return getTimingsResponseMutableLiveData;
    }

    //post timing
    public void postTiming(ShopTiming shopTiming) {
        repository.postTiming(shopTimingResponseMutableLiveData,shopTiming);
    }

    public MutableLiveData<ShopTimingResponse> getUpdateTiming(){
        return this.shopTimingResponseMutableLiveData;
    }

}
