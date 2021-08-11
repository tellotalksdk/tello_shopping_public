package com.tilismtech.tellotalk_shopping_sdk.ui_client.homescreen;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ViewFullOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;
import com.tilismtech.tellotalk_shopping_sdk.ui_client.pojo_client.response.AllCatList_UnderShop;

public class HomeScreenViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<AllCatList_UnderShop> allCatList_underShopMutableLiveData;

    public HomeScreenViewModel() {
        this.repository = Repository.getRepository();
        allCatList_underShopMutableLiveData = new MutableLiveData<>();
    }

    //getAllcategory_list under shop apis
    public void AllCategoryListUnderShop(String shopId, Context context) {
        repository.getAllCategoryList_UnderShop(shopId, allCatList_underShopMutableLiveData, context);
    }

    public MutableLiveData<AllCatList_UnderShop> getAllCategoryListUnderShop() {
        return this.allCatList_underShopMutableLiveData;
    }
}
