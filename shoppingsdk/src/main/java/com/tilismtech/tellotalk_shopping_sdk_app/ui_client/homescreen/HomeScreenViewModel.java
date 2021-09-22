package com.tilismtech.tellotalk_shopping_sdk_app.ui_client.homescreen;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk_app.repository.Repository;
import com.tilismtech.tellotalk_shopping_sdk_app.ui_client.pojo_client.response.AllCatList_UnderShop;

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
