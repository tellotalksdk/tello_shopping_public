package com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShopDetailResponse {

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

        @SerializedName("ShopId")
        @Expose
        private Integer shopId;
        @SerializedName("ShopCategoryId")
        @Expose
        private Integer shopCategoryId;
        @SerializedName("ShopURl")
        @Expose
        private String shopURl;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Area")
        @Expose
        private String area;
        @SerializedName("City")
        @Expose
        private String city;
        @SerializedName("Province")
        @Expose
        private String province;
        @SerializedName("Country")
        @Expose
        private String country;
        @SerializedName("Shop_Description")
        @Expose
        private String shopDescription;
        @SerializedName("Shop_Theme")
        @Expose
        private String shopTheme;
        @SerializedName("ShopProfile")
        @Expose
        private String shopProfile;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("shippingFee")
        @Expose
        private Integer shippingFee;

        public Integer getShopId() {
            return shopId;
        }

        public void setShopId(Integer shopId) {
            this.shopId = shopId;
        }

        public Integer getShopCategoryId() {
            return shopCategoryId;
        }

        public void setShopCategoryId(Integer shopCategoryId) {
            this.shopCategoryId = shopCategoryId;
        }

        public String getShopURl() {
            return shopURl;
        }

        public void setShopURl(String shopURl) {
            this.shopURl = shopURl;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getShopDescription() {
            return shopDescription;
        }

        public void setShopDescription(String shopDescription) {
            this.shopDescription = shopDescription;
        }

        public String getShopTheme() {
            return shopTheme;
        }

        public void setShopTheme(String shopTheme) {
            this.shopTheme = shopTheme;
        }

        public String getShopProfile() {
            return shopProfile;
        }

        public void setShopProfile(String shopProfile) {
            this.shopProfile = shopProfile;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public Integer getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(Integer shippingFee) {
            this.shippingFee = shippingFee;
        }

    }
}
