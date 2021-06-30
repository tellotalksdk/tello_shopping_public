package com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShopDetailResponse {

    public class BranchAddress {

        @SerializedName("addressId")
        @Expose
        private String addressId;
        @SerializedName("line1")
        @Expose
        private String line1;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("province")
        @Expose
        private String province;

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

    }

    public class Data {

        @SerializedName("requestList")
        @Expose
        private RequestList requestList;

        public RequestList getRequestList() {
            return requestList;
        }

        public void setRequestList(RequestList requestList) {
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

    public Object getProfileId() {
        return profileId;
    }

    public void setProfileId(Object profileId) {
        this.profileId = profileId;
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


    public class RequestList {

        @SerializedName("shopId")
        @Expose
        private String shopId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("shopCategoryId")
        @Expose
        private String shopCategoryId;
        @SerializedName("shopURl")
        @Expose
        private String shopURl;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("area")
        @Expose
        private String area;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("province")
        @Expose
        private String province;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("shopDescription")
        @Expose
        private String shopDescription;
        @SerializedName("shopTheme")
        @Expose
        private String shopTheme;
        @SerializedName("tax")
        @Expose
        private String tax;
        @SerializedName("shippingFee")
        @Expose
        private String shippingFee;
        @SerializedName("shopProfile")
        @Expose
        private String shopProfile;
        @SerializedName("shopOwnerName")
        @Expose
        private String shopOwnerName;
        @SerializedName("shopOwnerImage")
        @Expose
        private String shopOwnerImage;
        @SerializedName("shopRating")
        @Expose
        private String shopRating;
        @SerializedName("BranchAddress")
        @Expose
        private List<BranchAddress> branchAddress = null;

        public String getShopRating() {
            return shopRating;
        }

        public void setShopRating(String shopRating) {
            this.shopRating = shopRating;
        }

        public String getShopOwnerName() {
            return shopOwnerName;
        }

        public void setShopOwnerName(String shopOwnerName) {
            this.shopOwnerName = shopOwnerName;
        }

        public String getShopOwnerImage() {
            return shopOwnerImage;
        }

        public void setShopOwnerImage(String shopOwnerImage) {
            this.shopOwnerImage = shopOwnerImage;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShopCategoryId() {
            return shopCategoryId;
        }

        public void setShopCategoryId(String shopCategoryId) {
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

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(String shippingFee) {
            this.shippingFee = shippingFee;
        }

        public String getShopProfile() {
            return shopProfile;
        }

        public void setShopProfile(String shopProfile) {
            this.shopProfile = shopProfile;
        }

        public List<BranchAddress> getBranchAddress() {
            return branchAddress;
        }

        public void setBranchAddress(List<BranchAddress> branchAddress) {
            this.branchAddress = branchAddress;
        }

    }
}
