package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import android.net.Uri;

import java.util.List;

public class UpdateProduct {
    private List<Uri> Product_Pic;
    private String Product_Category_id;
    private String Title;
    private String Sub_Product_Category_id;
    private String Discount_Price;
    private String Sku;
    private String Summary;
    private String ProfileId;
    private String ProductStatus;
    private String Price;
    private String ProductId;


    public List<Uri> getProduct_Pic() {
        return Product_Pic;
    }

    public void setProduct_Pic(List<Uri> product_Pic) {
        Product_Pic = product_Pic;
    }

    public String getProduct_Category_id() {
        return Product_Category_id;
    }

    public void setProduct_Category_id(String product_Category_id) {
        Product_Category_id = product_Category_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSub_Product_Category_id() {
        return Sub_Product_Category_id;
    }

    public void setSub_Product_Category_id(String sub_Product_Category_id) {
        Sub_Product_Category_id = sub_Product_Category_id;
    }

    public String getDiscount_Price() {
        return Discount_Price;
    }

    public void setDiscount_Price(String discount_Price) {
        Discount_Price = discount_Price;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getProfileId() {
        return ProfileId;
    }

    public void setProfileId(String profileId) {
        ProfileId = profileId;
    }

    public String getProductStatus() {
        return ProductStatus;
    }

    public void setProductStatus(String productStatus) {
        ProductStatus = productStatus;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }
}
