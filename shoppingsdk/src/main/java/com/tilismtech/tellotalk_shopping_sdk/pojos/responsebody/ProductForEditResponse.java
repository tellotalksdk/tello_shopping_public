package com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductForEditResponse {

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


    public class RequestList {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Summary")
        @Expose
        private String summary;
        @SerializedName("Product_Category_id")
        @Expose
        private String productCategoryId;
        @SerializedName("Sku")
        @Expose
        private String sku;
        @SerializedName("Price")
        @Expose
        private String price;
        @SerializedName("Discount")
        @Expose
        private String discount;
        @SerializedName("ProductStatus")
        @Expose
        private String productStatus;
        @SerializedName("parent_product_category_id")
        @Expose
        private String parentProductCategoryId;
        @SerializedName("Parent_Category_Name")
        @Expose
        private String parentCategoryName;
        @SerializedName("Product_Category_Name")
        @Expose
        private String productCategoryName;
        @SerializedName("ProfilePic")
        @Expose
        private List<String> profilePic = null;

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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(String productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        public String getParentProductCategoryId() {
            return parentProductCategoryId;
        }

        public void setParentProductCategoryId(String parentProductCategoryId) {
            this.parentProductCategoryId = parentProductCategoryId;
        }

        public String getParentCategoryName() {
            return parentCategoryName;
        }

        public void setParentCategoryName(String parentCategoryName) {
            this.parentCategoryName = parentCategoryName;
        }

        public String getProductCategoryName() {
            return productCategoryName;
        }

        public void setProductCategoryName(String productCategoryName) {
            this.productCategoryName = productCategoryName;
        }

        public List<String> getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(List<String> profilePic) {
            this.profilePic = profilePic;
        }

    }
}
