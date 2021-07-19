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

        @SerializedName("orderId")
        @Expose
        private Integer orderid;
        @SerializedName("orderNo")
        @Expose
        private String orderno;
        @SerializedName("orderStatus")
        @Expose
        private Integer orderStatus;
        @SerializedName("grandTotal")
        @Expose
        private Double grandtotal;
        @SerializedName("firstName")
        @Expose
        private String firstname;
        @SerializedName("middleName")
        @Expose
        private String middlename;
        @SerializedName("lastName")
        @Expose
        private String lastname;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("orderDate")
        @Expose
        private String orderdate;
        @SerializedName("buyerProfileId")
        @Expose
        private String buyerProfileId;
        @SerializedName("riderName")
        @Expose
        private Object riderName;
        @SerializedName("riderContact")
        @Expose
        private Object riderContact;
        @SerializedName("completeAddress")
        @Expose
        private String completeAddress;
        @SerializedName("qty")
        @Expose
        private String quantity;
        @SerializedName("orderTrackingId")
        @Expose
        private String orderTrackingId;
        @SerializedName("reason")
        @Expose
        private String reason;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getOrderTrackingId() {
            return orderTrackingId;
        }

        public void setOrderTrackingId(String orderTrackingId) {
            this.orderTrackingId = orderTrackingId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public Integer getOrderid() {
            return orderid;
        }

        public void setOrderid(Integer orderid) {
            this.orderid = orderid;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Double getGrandtotal() {
            return grandtotal;
        }

        public void setGrandtotal(Double grandtotal) {
            this.grandtotal = grandtotal;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getMiddlename() {
            return middlename;
        }

        public void setMiddlename(String middlename) {
            this.middlename = middlename;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOrderdate() {
            return orderdate;
        }

        public void setOrderdate(String orderdate) {
            this.orderdate = orderdate;
        }

        public String getBuyerProfileId() {
            return buyerProfileId;
        }

        public void setBuyerProfileId(String buyerProfileId) {
            this.buyerProfileId = buyerProfileId;
        }

        public Object getRiderName() {
            return riderName;
        }

        public void setRiderName(Object riderName) {
            this.riderName = riderName;
        }

        public Object getRiderContact() {
            return riderContact;
        }

        public void setRiderContact(Object riderContact) {
            this.riderContact = riderContact;
        }

        public String getCompleteAddress() {
            return completeAddress;
        }

        public void setCompleteAddress(String completeAddress) {
            this.completeAddress = completeAddress;
        }

    }

}
