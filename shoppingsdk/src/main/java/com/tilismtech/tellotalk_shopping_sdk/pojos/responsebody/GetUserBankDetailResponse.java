package com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserBankDetailResponse {


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
        @SerializedName("profileId")
        @Expose
        private Object profileId;
        @SerializedName("oTP")
        @Expose
        private Object oTP;
        @SerializedName("data")
        @Expose
        private Data data;

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

        public Object getProfileId() {
            return profileId;
        }

        public void setProfileId(Object profileId) {
            this.profileId = profileId;
        }

        public Object getoTP() {
            return oTP;
        }

        public void setoTP(Object oTP) {
            this.oTP = oTP;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
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
        @SerializedName("AccountType")
        @Expose
        private String accountType;
        @SerializedName("AccountFrom")
        @Expose
        private String accountFrom;
        @SerializedName("AppToken")
        @Expose
        private String appToken;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("AccountTitle")
        @Expose
        private String accountTitle;

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

        public String getAppToken() {
            return appToken;
        }

        public void setAppToken(String appToken) {
            this.appToken = appToken;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAccountTitle() {
            return accountTitle;
        }

        public void setAccountTitle(String accountTitle) {
            this.accountTitle = accountTitle;
        }

    }
}
