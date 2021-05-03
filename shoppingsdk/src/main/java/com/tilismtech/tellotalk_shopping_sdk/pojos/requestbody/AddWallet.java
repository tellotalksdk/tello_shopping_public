package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddWallet {

    @SerializedName("ProfileId")
    @Expose
    private String profileId;
    @SerializedName("Account_Number")
    @Expose
    private String accountNumber;
    @SerializedName("Cnic")
    @Expose
    private String cnic;
    @SerializedName("AccountFrom")
    @Expose
    private String accountFrom;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

}
