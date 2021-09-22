package com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteBranchAddress {


        @SerializedName("profileId")
        @Expose
        private String profileId;
        @SerializedName("id")
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
