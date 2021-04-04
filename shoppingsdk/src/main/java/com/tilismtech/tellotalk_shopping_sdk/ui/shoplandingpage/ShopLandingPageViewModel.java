package com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

public class ShopLandingPageViewModel extends ViewModel {

    private MutableLiveData<ParentCategoryListResponse> parentCategoryListResponseMutableLiveData;
    private MutableLiveData<SubCategoryBYParentCatIDResponse> subCategoryBYParentCatIDResponseMutableLiveData;
    private MutableLiveData<AddNewProductResponse> addNewProductResponseMutableLiveData;

    private Repository repository;

    public ShopLandingPageViewModel() {
        this.parentCategoryListResponseMutableLiveData = new MutableLiveData<>();
        this.subCategoryBYParentCatIDResponseMutableLiveData = new MutableLiveData<>();
        this.addNewProductResponseMutableLiveData = new MutableLiveData<>();
        this.repository = Repository.getRepository();
    }

    //get parent list from server as live data
    public void parentCategories() {
        repository.postTogetParentCategories(parentCategoryListResponseMutableLiveData);
    }

    public LiveData<ParentCategoryListResponse> getParentCategoryListResponseLiveData() {
        return this.parentCategoryListResponseMutableLiveData;
    }

    //getChildCategoryListResponse
    public void childCategoryByParentId(SubCategoryBYParentCatID parentID) {
        repository.postTogetChildCategories(subCategoryBYParentCatIDResponseMutableLiveData, parentID);
    }

    public LiveData<SubCategoryBYParentCatIDResponse> getChildCategories() {
        return this.subCategoryBYParentCatIDResponseMutableLiveData;
    }

    //add new product
    public void addNewProduct(AddNewProduct addNewProduct) {
        repository.addNewProducts(addNewProductResponseMutableLiveData,addNewProduct);
    }

    public LiveData<AddNewProductResponse> getNewProduct() {
        return this.addNewProductResponseMutableLiveData;
    }
}
