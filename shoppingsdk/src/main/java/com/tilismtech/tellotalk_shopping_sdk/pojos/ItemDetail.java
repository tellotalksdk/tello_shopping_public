package com.tilismtech.tellotalk_shopping_sdk.pojos;

public class ItemDetail {

   private String noOfUnits;
   private String totalAmount;
   private String productName;

    public ItemDetail(String noOfUnits, String totalAmount, String productName) {
        this.noOfUnits = noOfUnits;
        this.totalAmount = totalAmount;
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(String noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
