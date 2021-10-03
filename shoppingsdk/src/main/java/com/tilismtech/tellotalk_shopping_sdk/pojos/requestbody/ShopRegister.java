package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopRegister {

    @SerializedName("profileId")
    @Expose
    private String profileId;
    @SerializedName("shopURl")
    @Expose
    private String shopURl;
    @SerializedName("registerPhone")
    @Expose
    private String registerPhone;
    @SerializedName("shopCategoryId")
    @Expose
    private String shopCategoryId;
    @SerializedName("shopDescription")
    @Expose
    private String shopDescription;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("shopName")
    @Expose
    private String shopName;
    @SerializedName("carrier")
    @Expose
    private String carrier;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

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

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
}
