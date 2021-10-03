package com.tilismtech.tellotalk_shopping_sdk.ui_seller.banksetting;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddBank;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddWallet;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteCardorWallet;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddBankResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddWalletResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.BankListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ClientWalletDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteBankResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetUserBankDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.WalletListResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

public class BankSettingViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<BankListResponse> bankListResponseMutableLiveData;
    private MutableLiveData<WalletListResponse> walletListResponseMutableLiveData;
    private MutableLiveData<AddBankResponse> addBankResponseMutableLiveData;
    private MutableLiveData<AddWalletResponse> addWalletResponseMutableLiveData;
    private MutableLiveData<ClientWalletDetailResponse> clientWalletDetailResponseMutableLiveData;
    private MutableLiveData<GetUserBankDetailResponse> getUserBankDetailResponseMutableLiveData;
    private MutableLiveData<DeleteBankResponse> deleteBankResponseMutableLiveData;

    public BankSettingViewModel() {
        this.repository = Repository.getRepository();
        this.bankListResponseMutableLiveData = new MutableLiveData<>();
        this.walletListResponseMutableLiveData = new MutableLiveData<>();
        this.addBankResponseMutableLiveData = new MutableLiveData<>();
        this.addWalletResponseMutableLiveData = new MutableLiveData<>();
        this.clientWalletDetailResponseMutableLiveData = new MutableLiveData<>();
        this.getUserBankDetailResponseMutableLiveData = new MutableLiveData<>();
        this.deleteBankResponseMutableLiveData = new MutableLiveData<>();
    }

    //getting bank list
    public void bankList() {
        repository.bankList(bankListResponseMutableLiveData);
    }

    public MutableLiveData<BankListResponse> getBankList() {
        return this.bankListResponseMutableLiveData;
    }

    //getting wallet list
    public void walletList() {
        repository.walletList(walletListResponseMutableLiveData);
    }

    public MutableLiveData<WalletListResponse> getWalletListResponse() {
        return this.walletListResponseMutableLiveData;
    }

    //post bank details
    public void addBankDetails(AddBank addBank,Context context) {
        repository.addBank(addBankResponseMutableLiveData, addBank,context);
    }

    public MutableLiveData<AddBankResponse> getBankDetailResponse() {
        return this.addBankResponseMutableLiveData;
    }

    //post wallet details

    public void addWalletDetails(AddWallet addWallet, Context context) {
        repository.addWallet(addWalletResponseMutableLiveData, addWallet,context);
    }

    public MutableLiveData<AddWalletResponse> getWalletDetailResponse() {
        return this.addWalletResponseMutableLiveData;
    }

    //get user bank detail list
    public void UserBankList(Context context) {
        repository.getClientBankDetails(getUserBankDetailResponseMutableLiveData,context);
    }

    public MutableLiveData<GetUserBankDetailResponse> getUserBankList() {
        return this.getUserBankDetailResponseMutableLiveData;
    }

    //get user wallet details list
    public void UserWalletList(Context context) {
        repository.getClientWalletDetails(clientWalletDetailResponseMutableLiveData,context);
    }

    public MutableLiveData<ClientWalletDetailResponse> getUserWalletList() {
        return this.clientWalletDetailResponseMutableLiveData;
    }

    //delete bank and wallet
    public void deleteBankorWallet(DeleteCardorWallet deleteCardorWallet,Context context) {
        repository.deleteCardorWallet(deleteBankResponseMutableLiveData, deleteCardorWallet,context);
    }

    public MutableLiveData<DeleteBankResponse> getdeleteBankorWallet() {
        return this.deleteBankResponseMutableLiveData;
    }
}
