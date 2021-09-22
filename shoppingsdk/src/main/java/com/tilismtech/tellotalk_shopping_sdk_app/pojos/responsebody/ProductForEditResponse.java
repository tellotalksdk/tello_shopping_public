package com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductForEditResponse {

    public class Data {

        @SerializedName("requestList")
        @Expose
        private RequestList requestList;

        public RequestList getRequestList() {
            return requestList;
        }

        public void setRequestList(RequestList requestList) {
            this.requestList = requestList;
        }

    }


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusDetail")
    @Expose
    private String statusDetail;
    @SerializedName("profileId")
    @Expose
    private Object profileId;
    @SerializedName("oTP")
    @Expose
    private Object oTP;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public Object getProfileId() {
        return profileId;
    }

    public void setProfileId(Object profileId) {
        this.profileId = profileId;
    }

    public Object getoTP() {
        return oTP;
    }

    public void setoTP(Object oTP) {
        this.oTP = oTP;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class ProductImageDTO {

        @SerializedName("imageId")
        @Expose
        private String imageId;
        @SerializedName("imageURL")
        @Expose
        private String imageURL;

        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

    }

    public class RequestList {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("summary")
        @Expose
        private String summary;
        @SerializedName("productCategoryid")
        @Expose
        private String productCategoryid;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("parentProductCategoryId")
        @Expose
        private String parentProductCategoryId;
        @SerializedName("parentCategoryName")
        @Expose
        private String parentCategoryName;
        @SerializedName("productStatus")
        @Expose
        private String productStatus;
        @SerializedName("productCategoryName")
        @Expose
        private String productCategoryName;
        @SerializedName("images")
        @Expose
        private Object images;
        @SerializedName("videoLink")
        @Expose
        private String videoLink;
        @SerializedName("productImageDTO")
        @Expose
        private List<ProductImageDTO> productImageDTO = null;

        public String getVideoLink() {
            return videoLink;
        }

        public void setVideoLink(String videoLink) {
            this.videoLink = videoLink;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getProductCategoryid() {
            return productCategoryid;
        }

        public void setProductCategoryid(String productCategoryid) {
            this.productCategoryid = productCategoryid;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getParentProductCategoryId() {
            return parentProductCategoryId;
        }

        public void setParentProductCategoryId(String parentProductCategoryId) {
            this.parentProductCategoryId = parentProductCategoryId;
        }

        public String getParentCategoryName() {
            return parentCategoryName;
        }

        public void setParentCategoryName(String parentCategoryName) {
            this.parentCategoryName = parentCategoryName;
        }

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        public String getProductCategoryName() {
            return productCategoryName;
        }

        public void setProductCategoryName(String productCategoryName) {
            this.productCategoryName = productCategoryName;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public List<ProductImageDTO> getProductImageDTO() {
            return productImageDTO;
        }

        public void setProductImageDTO(List<ProductImageDTO> productImageDTO) {
            this.productImageDTO = productImageDTO;
        }

    }
}
