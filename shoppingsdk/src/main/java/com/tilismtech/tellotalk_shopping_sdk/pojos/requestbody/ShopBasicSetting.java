package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;


public class ShopBasicSetting {

    private String shippingFee;
    private String tax;
    private String province;
    private String area;
    private String city;
    private String country;
    private String shopTheme;
    private String profileId;
    private String shopProfile;
    private String latitude;
    private String longitude;
    private String secondarylongitude;
    private String secondarylatitude;

    /* private String lat ;
     private String Long;
 */
    public String getLong() {
        return longitude;
    }

    public void setLong(String aLong) {
        this.longitude = aLong;
    }

    public ShopBasicSetting(String shippingFee, String tax, String province, String area, String city, String country, String shopTheme, String profileId, String shopProfile) {
        this.shippingFee = shippingFee;
        this.tax = tax;
        this.province = province;
        this.area = area;
        this.city = city;
        this.country = country;
        this.shopTheme = shopTheme;
        this.profileId = profileId;
        this.shopProfile = shopProfile;
    }

    public String getShopTheme() {
        return shopTheme;
    }

    public void setShopTheme(String shopTheme) {
        this.shopTheme = shopTheme;
    }

    public String getLat() {
        return latitude;
    }

    public void setLat(String lat) {
        this.latitude = lat;
    }

    public ShopBasicSetting() {
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getShop_Theme() {
        return shopTheme;
    }

    public void setShop_Theme(String shopTheme) {
        this.shopTheme = shopTheme;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getShopProfile() {
        return shopProfile;
    }

    public void setShopProfile(String shopProfile) {
        this.shopProfile = shopProfile;
    }

    public String getSecondarylongitude() {
        return secondarylongitude;
    }

    public void setSecondarylongitude(String secondarylongitude) {
        this.secondarylongitude = secondarylongitude;
    }

    public String getSecondarylatitude() {
        return secondarylatitude;
    }

    public void setSecondarylatitude(String secondarylatitude) {
        this.secondarylatitude = secondarylatitude;
    }
}
