package com.tilismtech.tellotalk_shopping_sdk.ui.banksetting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.BankListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.WalletListResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

public class BankSettingViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<BankListResponse> bankListResponseMutableLiveData;
    private MutableLiveData<WalletListResponse> walletListResponseMutableLiveData;

    public BankSettingViewModel() {
        this.repository = Repository.getRepository();
        this.bankListResponseMutableLiveData = new MutableLiveData<>();
        this.walletListResponseMutableLiveData = new MutableLiveData<>();
    }

    //getting bank list
    public void bankList(){
        repository.bankList(bankListResponseMutableLiveData);
    }

    public MutableLiveData<BankListResponse> getBankList(){
        return this.bankListResponseMutableLiveData;
    }

    //getting wallet list
    public void walletList(){
        repository.walletList(walletListResponseMutableLiveData);
    }

    public MutableLiveData<WalletListResponse> getWalletListResponse(){
        return this.walletListResponseMutableLiveData;
    }
}
