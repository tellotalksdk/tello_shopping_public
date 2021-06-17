package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBank {

        @SerializedName("profileId")
        @Expose
        private String profileId;
        @SerializedName("accountFrom")
        @Expose
        private String accountFrom;
        @SerializedName("accountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("nameOfOwner")
        @Expose
        private String nameOfOwner;
        @SerializedName("isdefault")
        @Expose
        private String isdefault;

        public String getProfileId() {
            return profileId;
        }

        public void setProfileId(String profileId) {
            this.profileId = profileId;
        }

        public String getAccountFrom() {
            return accountFrom;
        }

        public void setAccountFrom(String accountFrom) {
            this.accountFrom = accountFrom;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getNameOfOwner() {
            return nameOfOwner;
        }

        public void setNameOfOwner(String nameOfOwner) {
            this.nameOfOwner = nameOfOwner;
        }

        public String getIsdefault() {
            return isdefault;
        }

        public void setIsdefault(String isdefault) {
            this.isdefault = isdefault;
        }

}
