package com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderStatusCountResponse {

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

        @SerializedName("Recieved")
        @Expose
        private Integer recieved;
        @SerializedName("Accept")
        @Expose
        private Integer accept;
        @SerializedName("Dispatch")
        @Expose
        private Integer dispatch;
        @SerializedName("Delieverd")
        @Expose
        private Integer delieverd;
        @SerializedName("Paid")
        @Expose
        private Integer paid;
        @SerializedName("Cancel")
        @Expose
        private Integer cancel;
        @SerializedName("ALL")
        @Expose
        private Integer all;

        public Integer getRecieved() {
            return recieved;
        }

        public void setRecieved(Integer recieved) {
            this.recieved = recieved;
        }

        public Integer getAccept() {
            return accept;
        }

        public void setAccept(Integer accept) {
            this.accept = accept;
        }

        public Integer getDispatch() {
            return dispatch;
        }

        public void setDispatch(Integer dispatch) {
            this.dispatch = dispatch;
        }

        public Integer getDelieverd() {
            return delieverd;
        }

        public void setDelieverd(Integer delieverd) {
            this.delieverd = delieverd;
        }

        public Integer getPaid() {
            return paid;
        }

        public void setPaid(Integer paid) {
            this.paid = paid;
        }

        public Integer getCancel() {
            return cancel;
        }

        public void setCancel(Integer cancel) {
            this.cancel = cancel;
        }

        public Integer getAll() {
            return all;
        }

        public void setAll(Integer all) {
            this.all = all;
        }

    }
}
