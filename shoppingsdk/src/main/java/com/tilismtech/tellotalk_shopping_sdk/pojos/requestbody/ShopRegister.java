package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopRegister {

    @SerializedName("ProfileId")
    @Expose
    private String profileId;
    @SerializedName("ShopURl")
    @Expose
    private String shopURl;
    @SerializedName("RegisterPhone")
    @Expose
    private String registerPhone;
    @SerializedName("ShopCategoryId")
    @Expose
    private String shopCategoryId;
    @SerializedName("Shop_Description")
    @Expose
    private String shopDescription;
    @SerializedName("Email")
    @Expose
    private String email;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getShopURl() {
        return shopURl;
    }

    public void setShopURl(String shopURl) {
        this.shopURl = shopURl;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    public String getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(String shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
