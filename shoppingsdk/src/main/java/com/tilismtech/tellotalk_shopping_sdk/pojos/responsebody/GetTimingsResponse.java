package com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTimingsResponse {

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
    @SerializedName("oTP")
    @Expose
    private Object otp;
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


    public class Request {

        @SerializedName("settingId")
        @Expose
        private Integer settingId;
        @SerializedName("shopDayName")
        @Expose
        private String shopDayName;
        @SerializedName("shopStartTime")
        @Expose
        private String shopStartTime;
        @SerializedName("shopEndTime")
        @Expose
        private String shopEndTime;
        @SerializedName("shopStatusDaywise")
        @Expose
        private String shopStatusDaywise;

        public Integer getSettingId() {
            return settingId;
        }

        public void setSettingId(Integer settingId) {
            this.settingId = settingId;
        }

        public String getShopDayName() {
            return shopDayName;
        }

        public void setShopDayName(String shopDayName) {
            this.shopDayName = shopDayName;
        }

        public String getShopStartTime() {
            return shopStartTime;
        }

        public void setShopStartTime(String shopStartTime) {
            this.shopStartTime = shopStartTime;
        }

        public String getShopEndTime() {
            return shopEndTime;
        }

        public void setShopEndTime(String shopEndTime) {
            this.shopEndTime = shopEndTime;
        }

        public String getShopStatusDaywise() {
            return shopStatusDaywise;
        }

        public void setShopStatusDaywise(String shopStatusDaywise) {
            this.shopStatusDaywise = shopStatusDaywise;
        }

    }
}
