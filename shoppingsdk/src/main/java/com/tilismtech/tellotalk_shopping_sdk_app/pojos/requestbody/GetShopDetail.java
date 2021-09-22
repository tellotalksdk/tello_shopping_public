package com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetShopDetail {


    @SerializedName("profileId")
    @Expose
    private String profileId;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

}
