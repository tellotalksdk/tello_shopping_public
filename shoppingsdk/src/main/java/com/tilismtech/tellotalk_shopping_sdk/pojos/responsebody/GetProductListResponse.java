package com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductListResponse {

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

        @SerializedName("ProductId")
        @Expose
        private Integer productId;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Sku")
        @Expose
        private String sku;
        @SerializedName("Price")
        @Expose
        private Integer price;
        @SerializedName("Discount_Price")
        @Expose
        private Integer discountPrice;
        @SerializedName("ProductStatus")
        @Expose
        private String productStatus;
        @SerializedName("prodpic")
        @Expose
        private String prodpic;
        @SerializedName("Product_Category_Name")
        @Expose
        private String  Product_Category_Name;

        public String getProduct_Category_Name() {
            return Product_Category_Name;
        }

        public void setProduct_Category_Name(String product_Category_Name) {
            Product_Category_Name = product_Category_Name;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(Integer discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        public String getProdpic() {
            return prodpic;
        }

        public void setProdpic(String prodpic) {
            this.prodpic = prodpic;
        }

    }
}
