package com.tilismtech.tellotalk_shopping_sdk.ui.storesetting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GenerateTokenResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopRegisterResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

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
    public void postShopDetail(GetShopDetail shopDetail) {
        repository.getShopDetails(getShopDetailResponseMutableLiveData, shopDetail);
    }

    public MutableLiveData<GetShopDetailResponse> getShopDetail() {
        return this.getShopDetailResponseMutableLiveData;
    }

    //add branch address api
    public void addBranchAddress(AddBranchAddress addBranchAddress) {
        repository.addBranchAddress(addBranchAddressResponseMutableLiveData, addBranchAddress);
    }

    public MutableLiveData<AddBranchAddressResponse> getAddBranchAddress() {
        return addBranchAddressResponseMutableLiveData;
    }

    //update branch address api
    public void updateBranchAddress(UpdateBranchAddress updateBranchAddress) {
        repository.updateBranchAddress(updateBranchAddressResponseMutableLiveData, updateBranchAddress);
    }

    public MutableLiveData<UpdateBranchAddressResponse> getUpdateBranchAddress() {
        return updateBranchAddressResponseMutableLiveData;
    }

    //delete branch address api
    public void deleteBranchAddress(DeleteBranchAddress deleteBranchAddress) {
        repository.deleteBranchAddress(deleteBranchAddressResponseMutableLiveData, deleteBranchAddress);
    }

    public MutableLiveData<DeleteBranchAddressResponse> getDeleteBranchAddress() {
        return deleteBranchAddressResponseMutableLiveData;
    }

}
