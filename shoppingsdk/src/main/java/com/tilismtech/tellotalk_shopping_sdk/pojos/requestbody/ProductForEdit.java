package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductForEdit {
    @SerializedName("ProfileId")
    @Expose
    private String profileId;
    @SerializedName("ProductId")
    @Expose
    private String productId;

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

}
