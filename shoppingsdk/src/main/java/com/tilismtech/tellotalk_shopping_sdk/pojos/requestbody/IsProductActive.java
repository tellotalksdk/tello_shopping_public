package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsProductActive {

    @SerializedName("ProfileId")
    @Expose
    private String profileId;
    @SerializedName("ProductStatus")
    @Expose
    private String productStatus;
    @SerializedName("ProductId")
    @Expose
    private String productId;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}