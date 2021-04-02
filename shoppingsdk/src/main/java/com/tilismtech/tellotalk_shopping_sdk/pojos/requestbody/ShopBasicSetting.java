package com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody;

public class ShopBasicSetting {

    private String ShippingFee;
    private String tax;
    private String Province;
    private String Area;
    private String City;
    private String Country;
    private String Shop_Theme;
    private String ProfileId;
    private String ShopProfile;

    public ShopBasicSetting(String shippingFee, String tax, String province, String area, String city, String country, String shop_Theme, String profileId, String shopProfile) {
        ShippingFee = shippingFee;
        this.tax = tax;
        Province = province;
        Area = area;
        City = city;
        Country = country;
        Shop_Theme = shop_Theme;
        ProfileId = profileId;
        ShopProfile = shopProfile;
    }

    public ShopBasicSetting() {
    }

    public String getShippingFee() {
        return ShippingFee;
    }

    public void setShippingFee(String shippingFee) {
        ShippingFee = shippingFee;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getShop_Theme() {
        return Shop_Theme;
    }

    public void setShop_Theme(String shop_Theme) {
        Shop_Theme = shop_Theme;
    }

    public String getProfileId() {
        return ProfileId;
    }

    public void setProfileId(String profileId) {
        ProfileId = profileId;
    }

    public String getShopProfile() {
        return ShopProfile;
    }

    public void setShopProfile(String shopProfile) {
        ShopProfile = shopProfile;
    }
}
