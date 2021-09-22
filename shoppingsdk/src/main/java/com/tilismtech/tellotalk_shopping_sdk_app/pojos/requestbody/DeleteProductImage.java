package com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody;

public class DeleteProductImage {

    private String profileId;
    private String productId;
    private String imageId;

    public DeleteProductImage(String profileId, String productId, String imageId) {
        this.profileId = profileId;
        this.productId = productId;
        this.imageId = imageId;
    }

    public DeleteProductImage() {
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}

