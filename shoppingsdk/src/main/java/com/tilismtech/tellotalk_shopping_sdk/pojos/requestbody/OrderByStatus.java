package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderByStatus {



        @SerializedName("ProfileId")
        @Expose
        private String profileId;
        @SerializedName("Status")
        @Expose
        private String status;

        public String getProfileId() {
            return profileId;
        }

        public void setProfileId(String profileId) {
            this.profileId = profileId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


}
