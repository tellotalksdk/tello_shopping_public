package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteBranchAddress {


        @SerializedName("ProfileId")
        @Expose
        private String profileId;
        @SerializedName("Id")
        @Expose
        private String id;

        public String getProfileId() {
            return profileId;
        }

        public void setProfileId(String profileId) {
            this.profileId = profileId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

}
