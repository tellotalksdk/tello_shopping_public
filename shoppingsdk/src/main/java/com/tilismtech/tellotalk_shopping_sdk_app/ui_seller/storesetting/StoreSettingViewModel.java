package com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.storesetting;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.AddBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.DeleteBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.UpdateBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.AddBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.DeleteBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.UpdateBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.repository.Repository;

public class StoreSettingViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<GetShopDetailResponse> getShopDetailResponseMutableLiveData;
    private MutableLiveData<AddBranchAddressResponse> addBranchAddressResponseMutableLiveData;
    private MutableLiveData<UpdateBranchAddressResponse> updateBranchAddressResponseMutableLiveData;
    private MutableLiveData<DeleteBranchAddressResponse> deleteBranchAddressResponseMutableLiveData;

    public StoreSettingViewModel() {
        repository = Repository.getRepository();
        this.getShopDetailResponseMutableLiveData = new MutableLiveData<>();
        this.addBranchAddressResponseMutableLiveData = new MutableLiveData<>();
        this.updateBranchAddressResponseMutableLiveData = new MutableLiveData<>();
        this.deleteBranchAddressResponseMutableLiveData = new MutableLiveData<>();
    }

    //post and get shop detail
    public void postShopDetail(GetShopDetail shopDetail, Context context) {
        repository.getShopDetails(getShopDetailResponseMutableLiveData, shopDetail,context);
    }

    public MutableLiveData<GetShopDetailResponse> getShopDetail() {
        return this.getShopDetailResponseMutableLiveData;
    }

    //add branch address api
    public void addBranchAddress(AddBranchAddress addBranchAddress,Context context) {
        repository.addBranchAddress(addBranchAddressResponseMutableLiveData, addBranchAddress,context);
    }

    public MutableLiveData<AddBranchAddressResponse> getAddBranchAddress() {
        return addBranchAddressResponseMutableLiveData;
    }

    //update branch address api
    public void updateBranchAddress(UpdateBranchAddress updateBranchAddress,Context context) {
        repository.updateBranchAddress(updateBranchAddressResponseMutableLiveData, updateBranchAddress,context);
    }

    public MutableLiveData<UpdateBranchAddressResponse> getUpdateBranchAddress() {
        return updateBranchAddressResponseMutableLiveData;
    }

    //delete branch address api
    public void deleteBranchAddress(DeleteBranchAddress deleteBranchAddress,Context context) {
        repository.deleteBranchAddress(deleteBranchAddressResponseMutableLiveData, deleteBranchAddress,context);
    }

    public MutableLiveData<DeleteBranchAddressResponse> getDeleteBranchAddress() {
        return deleteBranchAddressResponseMutableLiveData;
    }

}
