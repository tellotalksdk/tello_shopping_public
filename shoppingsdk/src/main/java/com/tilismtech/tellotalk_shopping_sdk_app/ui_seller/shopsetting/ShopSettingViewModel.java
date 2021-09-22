package com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.shopsetting;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.GetTimings;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.ShopTiming;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.CityListResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ColorThemeResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetTimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ProvinceListResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ShopTimingResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.repository.Repository;

public class ShopSettingViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ShopBasicSettingResponse> shopBasicSettingResponseMutableLiveData;
    private MutableLiveData<GetTimingsResponse> getTimingsResponseMutableLiveData;
    private MutableLiveData<ShopTimingResponse> shopTimingResponseMutableLiveData;
    private MutableLiveData<ColorThemeResponse> colorThemeResponseMutableLiveData;
    private MutableLiveData<ProvinceListResponse> provinceListResponseMutableLiveData;
    private MutableLiveData<CityListResponse> cityListResponseMutableLiveData;


    public ShopSettingViewModel() {
        repository = Repository.getRepository();
        this.shopBasicSettingResponseMutableLiveData = new MutableLiveData<>();
        this.getTimingsResponseMutableLiveData = new MutableLiveData<>();
        this.shopTimingResponseMutableLiveData = new MutableLiveData<>();
        this.colorThemeResponseMutableLiveData = new MutableLiveData<>();
        this.provinceListResponseMutableLiveData = new MutableLiveData<>();
        this.cityListResponseMutableLiveData = new MutableLiveData<>();
    }

    //shop setting with Image
    public void postShopSettingDetails(ShopBasicSetting shopBasicSetting, Context myContext) {
        repository.setShopBasicSetting(shopBasicSettingResponseMutableLiveData, shopBasicSetting, myContext);
    }

    public MutableLiveData<ShopBasicSettingResponse> getShopSettingResponse() {
        return shopBasicSettingResponseMutableLiveData;
    }

    //shop setting without Image
    public void postShopSettingDetailsWithOutImage(ShopBasicSetting shopBasicSetting, Context myContext) {
        repository.setShopBasicSettingWithOutImage(shopBasicSettingResponseMutableLiveData, shopBasicSetting, myContext);
    }

    public MutableLiveData<ShopBasicSettingResponse> getShopSettingResponseWithOutImage() {
        return shopBasicSettingResponseMutableLiveData;
    }


    //get timing

    public void posttogetTimings(GetTimings getTimings, Context context) {
        repository.getTimings(getTimingsResponseMutableLiveData, getTimings, context);
    }

    public MutableLiveData<GetTimingsResponse> gettimings() {
        return getTimingsResponseMutableLiveData;
    }

    //post timing
    public void postTiming(ShopTiming shopTiming, Context context) {
        repository.postTiming(shopTimingResponseMutableLiveData, shopTiming, context);
    }

    public MutableLiveData<ShopTimingResponse> getUpdateTiming() {
        return this.shopTimingResponseMutableLiveData;
    }

    //color themes
    public void ColorThemes() {
        repository.getColorTheme(colorThemeResponseMutableLiveData);
    }

    public MutableLiveData<ColorThemeResponse> getColorTheme() {
        return this.colorThemeResponseMutableLiveData;
    }

    //getAllProvince
    public void AllProvince() {
        repository.getAllProvincesByID(provinceListResponseMutableLiveData);
    }

    public MutableLiveData<ProvinceListResponse> getAllProvince() {
        return this.provinceListResponseMutableLiveData;
    }

    //getAllCities

    public void AllCitiesByProvince(String stateID) {
        repository.getAllCitiesBYProvinceID(cityListResponseMutableLiveData, stateID);
    }

    public MutableLiveData<CityListResponse> getAllCitiesByProvinceID() {
        return this.cityListResponseMutableLiveData;
    }


}
