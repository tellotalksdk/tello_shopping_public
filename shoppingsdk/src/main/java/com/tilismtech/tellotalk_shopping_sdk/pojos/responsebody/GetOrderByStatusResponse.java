package com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderByStatusResponse {


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


    public class ProductsDetail {

        @SerializedName("productId")
        @Expose
        private String productId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("subTotal")
        @Expose
        private String subTotal;
        @SerializedName("parentProductCategoryId")
        @Expose
        private String parentProductCategoryId;
        @SerializedName("parentCategoryName")
        @Expose
        private String parentCategoryName;
        @SerializedName("productCategoryId")
        @Expose
        private String productCategoryId;
        @SerializedName("productCategoryName")
        @Expose
        private String productCategoryName;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
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

        public String getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(String productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public String getProductCategoryName() {
            return productCategoryName;
        }

        public void setProductCategoryName(String productCategoryName) {
            this.productCategoryName = productCategoryName;
        }

    }

    public class Request {

        @SerializedName("orderId")
        @Expose
        private String orderId;
        @SerializedName("OrderNo")
        @Expose
        private String orderNo;
        @SerializedName("orderStatus")
        @Expose
        private String orderStatus;
        @SerializedName("grandTotal")
        @Expose
        private String grandTotal;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("middleName")
        @Expose
        private String middleName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("orderDate")
        @Expose
        private String orderDate;
        @SerializedName("buyerProfileId")
        @Expose
        private String buyerProfileId;
        @SerializedName("riderName")
        @Expose
        private String riderName;
        @SerializedName("riderContact")
        @Expose
        private String riderContact;
        @SerializedName("completeAddress")
        @Expose
        private String completeAddress;
        @SerializedName("orderTrackingId")
        @Expose
        private String orderTrackingId;
        @SerializedName("qty")
        @Expose
        private String qty;
        @SerializedName("reason")
        @Expose
        private String reason;
        @SerializedName("ProductsDetails")
        @Expose
        private List<ProductsDetail> productsDetails = null;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getBuyerProfileId() {
            return buyerProfileId;
        }

        public void setBuyerProfileId(String buyerProfileId) {
            this.buyerProfileId = buyerProfileId;
        }

        public String getRiderName() {
            return riderName;
        }

        public void setRiderName(String riderName) {
            this.riderName = riderName;
        }

        public String getRiderContact() {
            return riderContact;
        }

        public void setRiderContact(String riderContact) {
            this.riderContact = riderContact;
        }

        public String getCompleteAddress() {
            return completeAddress;
        }

        public void setCompleteAddress(String completeAddress) {
            this.completeAddress = completeAddress;
        }

        public String getOrderTrackingId() {
            return orderTrackingId;
        }

        public void setOrderTrackingId(String orderTrackingId) {
            this.orderTrackingId = orderTrackingId;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public List<ProductsDetail> getProductsDetails() {
            return productsDetails;
        }

        public void setProductsDetails(List<ProductsDetail> productsDetails) {
            this.productsDetails = productsDetails;
        }

    }
}
