package com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewFullOrderResponse {

    public class BuyerDetail {

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
        @SerializedName("buyerProfileId")
        @Expose
        private String buyerProfileId;
        @SerializedName("completeAddress")
        @Expose
        private String completeAddress;
        @SerializedName("accountFrom")
        @Expose
        private String accountFrom;
        @SerializedName("accountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("accountTitle")
        @Expose
        private String accountTitle;
        @SerializedName("cardNumber")
        @Expose
        private String cardNumber;
        @SerializedName("cardType")
        @Expose
        private String cardType;

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

        public String getBuyerProfileId() {
            return buyerProfileId;
        }

        public void setBuyerProfileId(String buyerProfileId) {
            this.buyerProfileId = buyerProfileId;
        }

        public String getCompleteAddress() {
            return completeAddress;
        }

        public void setCompleteAddress(String completeAddress) {
            this.completeAddress = completeAddress;
        }

        public String getAccountFrom() {
            return accountFrom;
        }

        public void setAccountFrom(String accountFrom) {
            this.accountFrom = accountFrom;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountTitle() {
            return accountTitle;
        }

        public void setAccountTitle(String accountTitle) {
            this.accountTitle = accountTitle;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
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

    public class RequestList {

        @SerializedName("orderId")
        @Expose
        private String orderId;
        @SerializedName("orderNo")
        @Expose
        private String orderNo;
        @SerializedName("orderStatus")
        @Expose
        private String orderStatus;
        @SerializedName("grandTotal")
        @Expose
        private String grandTotal;
        @SerializedName("orderDate")
        @Expose
        private String orderDate;
        @SerializedName("tax")
        @Expose
        private String tax;
        @SerializedName("shipping")
        @Expose
        private Object shipping;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("promo")
        @Expose
        private String promo;
        @SerializedName("discount")
        @Expose
        private Object discount;
        @SerializedName("payType")
        @Expose
        private String payType;
        @SerializedName("modeOfPayment")
        @Expose
        private Object modeOfPayment;
        @SerializedName("riderInfo")
        @Expose
        private List<RiderInfo> riderInfo = null;
        @SerializedName("sellerDetails")
        @Expose
        private List<SellerDetail> sellerDetails = null;
        @SerializedName("buyerDetails")
        @Expose
        private List<BuyerDetail> buyerDetails = null;
        @SerializedName("productsDetails")
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

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public Object getShipping() {
            return shipping;
        }

        public void setShipping(Object shipping) {
            this.shipping = shipping;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPromo() {
            return promo;
        }

        public void setPromo(String promo) {
            this.promo = promo;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public Object getModeOfPayment() {
            return modeOfPayment;
        }

        public void setModeOfPayment(Object modeOfPayment) {
            this.modeOfPayment = modeOfPayment;
        }

        public List<RiderInfo> getRiderInfo() {
            return riderInfo;
        }

        public void setRiderInfo(List<RiderInfo> riderInfo) {
            this.riderInfo = riderInfo;
        }

        public List<SellerDetail> getSellerDetails() {
            return sellerDetails;
        }

        public void setSellerDetails(List<SellerDetail> sellerDetails) {
            this.sellerDetails = sellerDetails;
        }

        public List<BuyerDetail> getBuyerDetails() {
            return buyerDetails;
        }

        public void setBuyerDetails(List<BuyerDetail> buyerDetails) {
            this.buyerDetails = buyerDetails;
        }

        public List<ProductsDetail> getProductsDetails() {
            return productsDetails;
        }

        public void setProductsDetails(List<ProductsDetail> productsDetails) {
            this.productsDetails = productsDetails;
        }

    }

    public class RiderInfo {

        @SerializedName("riderName")
        @Expose
        private String riderName;
        @SerializedName("riderContact")
        @Expose
        private String riderContact;
        @SerializedName("Ordertackigid")
        @Expose
        private String ordertackigid;

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

        public String getOrdertackigid() {
            return ordertackigid;
        }

        public void setOrdertackigid(String ordertackigid) {
            this.ordertackigid = ordertackigid;
        }

    }

    public class SellerDetail {

        @SerializedName("ProfileId")
        @Expose
        private String profileId;
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
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("iBAN")
        @Expose
        private String iBAN;
        @SerializedName("accountFrom")
        @Expose
        private String accountFrom;
        @SerializedName("accountType")
        @Expose
        private String accountType;
        @SerializedName("accountTitle")
        @Expose
        private String accountTitle;

        public String getProfileId() {
            return profileId;
        }

        public void setProfileId(String profileId) {
            this.profileId = profileId;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getiBAN() {
            return iBAN;
        }

        public void setiBAN(String iBAN) {
            this.iBAN = iBAN;
        }

        public String getAccountFrom() {
            return accountFrom;
        }

        public void setAccountFrom(String accountFrom) {
            this.accountFrom = accountFrom;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getAccountTitle() {
            return accountTitle;
        }

        public void setAccountTitle(String accountTitle) {
            this.accountTitle = accountTitle;
        }

    }



/*

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


    public class ProductsDetail {

        @SerializedName("productId")
        @Expose
        private String productId;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("Discount")
        @Expose
        private String discount;
        @SerializedName("Quantity")
        @Expose
        private String quantity;
        @SerializedName("Sub_Total")
        @Expose
        private String subTotal;
        @SerializedName("parent_product_category_id")
        @Expose
        private String parentProductCategoryId;
        @SerializedName("Parent_Category_Name")
        @Expose
        private String parentCategoryName;
        @SerializedName("Product_Category_id")
        @Expose
        private String productCategoryId;
        @SerializedName("Product_Category_Name")
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

    public class RequestList {

        @SerializedName("OrderId")
        @Expose
        private String orderId;
        @SerializedName("orderno")
        @Expose
        private String orderno;
        @SerializedName("Order_status")
        @Expose
        private String orderStatus;
        @SerializedName("grandtotal")
        @Expose
        private String grandtotal;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("middlename")
        @Expose
        private String middlename;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("orderdate")
        @Expose
        private String orderdate;
        @SerializedName("BuyerProfileId")
        @Expose
        private String buyerProfileId;
        @SerializedName("riderName")
        @Expose
        private String riderName;
        @SerializedName("riderContact")
        @Expose
        private String riderContact;
        @SerializedName("Ordertackigid")
        @Expose
        private String ordertackigid;
        @SerializedName("CompleteAddress")
        @Expose
        private String completeAddress;
        @SerializedName("tax")
        @Expose
        private String tax;
        @SerializedName("shipping")
        @Expose
        private Object shipping;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("promo")
        @Expose
        private String promo;
        @SerializedName("discount")
        @Expose
        private Object discount;
        @SerializedName("PayType")
        @Expose
        private String payType;
        @SerializedName("Modeofpayment")
        @Expose
        private String modeofpayment;
        @SerializedName("ProductsDetails")
        @Expose
        private List<ProductsDetail> productsDetails = null;
        @SerializedName("Seller_firstname")
        @Expose
        private String Seller_firstname;
        @SerializedName("Seller_middlename")
        @Expose
        private String Seller_middlename;
        @SerializedName("Seller_lastname")
        @Expose
        private String Seller_lastname;
        @SerializedName("Seller_mobile")
        @Expose
        private String Seller_mobile;
        @SerializedName("Seller_Address")
        @Expose
        private String Seller_Address;

        public String getSeller_firstname() {
            return Seller_firstname;
        }

        public void setSeller_firstname(String seller_firstname) {
            Seller_firstname = seller_firstname;
        }

        public String getSeller_middlename() {
            return Seller_middlename;
        }

        public void setSeller_middlename(String seller_middlename) {
            Seller_middlename = seller_middlename;
        }

        public String getSeller_lastname() {
            return Seller_lastname;
        }

        public void setSeller_lastname(String seller_lastname) {
            Seller_lastname = seller_lastname;
        }

        public String getSeller_mobile() {
            return Seller_mobile;
        }

        public void setSeller_mobile(String seller_mobile) {
            Seller_mobile = seller_mobile;
        }

        public String getSeller_Address() {
            return Seller_Address;
        }

        public void setSeller_Address(String seller_Address) {
            Seller_Address = seller_Address;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getGrandtotal() {
            return grandtotal;
        }

        public void setGrandtotal(String grandtotal) {
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

        public String getOrdertackigid() {
            return ordertackigid;
        }

        public void setOrdertackigid(String ordertackigid) {
            this.ordertackigid = ordertackigid;
        }

        public String getCompleteAddress() {
            return completeAddress;
        }

        public void setCompleteAddress(String completeAddress) {
            this.completeAddress = completeAddress;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public Object getShipping() {
            return shipping;
        }

        public void setShipping(Object shipping) {
            this.shipping = shipping;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPromo() {
            return promo;
        }

        public void setPromo(String promo) {
            this.promo = promo;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getModeofpayment() {
            return modeofpayment;
        }

        public void setModeofpayment(String modeofpayment) {
            this.modeofpayment = modeofpayment;
        }

        public List<ProductsDetail> getProductsDetails() {
            return productsDetails;
        }

        public void setProductsDetails(List<ProductsDetail> productsDetails) {
            this.productsDetails = productsDetails;
    }    }
*/


}
