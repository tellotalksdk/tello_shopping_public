package com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParentCategoryListResponse {

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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("slug")
        @Expose
        private String slug;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

    }
}
