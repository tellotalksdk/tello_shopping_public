package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import java.util.List;

public class AddNewProduct {

    private List<String> Product_Pic;
    private String parentProductCategoryId;
    private String title;
    private String productCategoryId;
    private String discountPrice;
    private String sku;
    private String summary;
    private String profileId;
    private String productStatus;
    private String price;
    private String videoLink;

    public String getVideoName() {
        return videoLink;
    }

    public void setVideoName(String videoName) {
        this.videoLink = videoName;
    }

    public List<String> getProduct_Pic() {
        return this.Product_Pic;
    }

    public void setProduct_Pic(List<String> product_Pic) {
        Product_Pic = product_Pic;
    }

    public String getProduct_Category_id() {
        return this.parentProductCategoryId;
    }

    public void setProduct_Category_id(String parentProductCategoryId) {
        this.parentProductCategoryId = parentProductCategoryId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_Product_Category_id() {
        return this.productCategoryId;
    }

    public void setSub_Product_Category_id(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getDiscount_Price() {
        return this.discountPrice;
    }

    public void setDiscount_Price(String discount_Price) {
        this.discountPrice = discount_Price;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getProfileId() {
        return this.profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProductStatus() {
        return this.productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
