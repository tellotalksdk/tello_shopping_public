package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopTiming {

    public class DaysSetting {

        @SerializedName("SettingId")
        @Expose
        private Integer settingId;
        @SerializedName("Shop_Day_Name")
        @Expose
        private String shopDayName;
        @SerializedName("Shop_Start_Time")
        @Expose
        private String shopStartTime;
        @SerializedName("Shop_End_Time")
        @Expose
        private String shopEndTime;
        @SerializedName("Shop_Status_Daywise")
        @Expose
        private String shopStatusDaywise;

        public Integer getSettingId() {
            return settingId;
        }

        public void setSettingId(Integer settingId) {
            this.settingId = settingId;
        }

        public String getShopDayName() {
            return shopDayName;
        }

        public void setShopDayName(String shopDayName) {
            this.shopDayName = shopDayName;
        }

        public String getShopStartTime() {
            return shopStartTime;
        }

        public void setShopStartTime(String shopStartTime) {
            this.shopStartTime = shopStartTime;
        }

        public String getShopEndTime() {
            return shopEndTime;
        }

        public void setShopEndTime(String shopEndTime) {
            this.shopEndTime = shopEndTime;
        }

        public String getShopStatusDaywise() {
            return shopStatusDaywise;
        }

        public void setShopStatusDaywise(String shopStatusDaywise) {
            this.shopStatusDaywise = shopStatusDaywise;
        }

    }


        @SerializedName("ProfileId")
        @Expose
        private String profileId;
        @SerializedName("DaysSetting")
        @Expose
        private List<DaysSetting> daysSetting = null;

        public String getProfileId() {
            return profileId;
        }

        public void setProfileId(String profileId) {
            this.profileId = profileId;
        }

        public List<DaysSetting> getDaysSetting() {
            return daysSetting;
        }

        public void setDaysSetting(List<DaysSetting> daysSetting) {
            this.daysSetting = daysSetting;
        }

}
