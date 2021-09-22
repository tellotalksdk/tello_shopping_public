package com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopNameAndImageResponse {

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

        @SerializedName("shopProfile")
        @Expose
        private String shopProfile;
        @SerializedName("shopName")
        @Expose
        private String shopName;

        public String getShopProfile() {
            return shopProfile;
        }

        public void setShopProfile(String shopProfile) {
            this.shopProfile = shopProfile;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

    }
}
