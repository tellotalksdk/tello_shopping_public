package com.tilismtech.tellotalk_shopping_sdk.ui_client.pojo;

public class ShopItems {
    private String productTitle, productDescription, categoryName, originalPrice, discountedPrice;


    public ShopItems(String productTitle, String productDescription, String categoryName, String originalPrice, String discountedPrice) {
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.categoryName = categoryName;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
}
