package com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientWalletDetailResponse {

    public class Data {

        @SerializedName("requestList")
        @Expose
        private List<Request> requestList = null;

        public List<Request> getRequestList() {
            return requestList;
        }

        public void setRequestList(List<Request> requestList) {
            this.requestList = requestList;
        }

    }


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusDetail")
    @Expose
    private String statusDetail;
    @SerializedName("OTP")
    @Expose
    private Object otp;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("ProfileId")
    @Expose
    private Object profileId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public Object getOtp() {
        return otp;
    }

    public void setOtp(Object otp) {
        this.otp = otp;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Object getProfileId() {
        return profileId;
    }

    public void setProfileId(Object profileId) {
        this.profileId = profileId;
    }


    public class Request {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("ProfileId")
        @Expose
        private String profileId;
        @SerializedName("Account_Number")
        @Expose
        private String accountNumber;
        @SerializedName("Cnic")
        @Expose
        private String cnic;
        @SerializedName("AccountType")
        @Expose
        private String accountType;
        @SerializedName("AccountFrom")
        @Expose
        private String accountFrom;
        @SerializedName("nameOfOwner")
        @Expose
        private String nameOfOwner;

        public String getNameOfOwner() {
            return nameOfOwner;
        }

        public void setNameOfOwner(String nameOfOwner) {
            this.nameOfOwner = nameOfOwner;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

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

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getAccountFrom() {
            return accountFrom;
        }

        public void setAccountFrom(String accountFrom) {
            this.accountFrom = accountFrom;
        }

    }
}
