package com.tilismtech.tellotalk_shopping_sdk.pojos;

public class ProductListpojo {

    private String productTitle, originalPrice, discountedPrice, productCategory;
    private boolean isActive;
    private int image;

    public ProductListpojo(String productTitle, String originalPrice, String discountedPrice, String productCategory, boolean isActive, int image) {
        this.productTitle = productTitle;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.productCategory = productCategory;
        this.isActive = isActive;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
