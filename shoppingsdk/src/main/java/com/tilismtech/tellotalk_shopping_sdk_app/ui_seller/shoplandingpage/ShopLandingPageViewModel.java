package com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.shoplandingpage;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.DeleteProduct;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.DeleteProductImage;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.IsProductActive;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.IsProductActiveResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.ProductForEdit;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.ProductList;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.UpdateProduct;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.DeleteProductImageResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.DeleteProductResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetOrderStatusCountResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ProductForEditResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ProductListResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ShopNameAndImageResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.TotalProductResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.UpdateProductResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.repository.Repository;

public class ShopLandingPageViewModel extends ViewModel {

    private MutableLiveData<ParentCategoryListResponse> parentCategoryListResponseMutableLiveData;
    private MutableLiveData<SubCategoryBYParentCatIDResponse> subCategoryBYParentCatIDResponseMutableLiveData;
    private MutableLiveData<AddNewProductResponse> addNewProductResponseMutableLiveData;
    private MutableLiveData<ProductForEditResponse> productForEditMutableLiveData;
    private MutableLiveData<ProductListResponse> productListResponseMutableLiveData;
    private MutableLiveData<UpdateProductResponse> updateProductResponseMutableLiveData;
    private MutableLiveData<IsProductActiveResponse> isProductActiveResponseMutableLiveData;
    private MutableLiveData<GetOrderStatusCountResponse> getOrderStatusCountResponseMutableLiveData;
    private MutableLiveData<DeleteProductResponse> deleteProductResponseMutableLiveData;
    private MutableLiveData<ShopNameAndImageResponse> shopNameAndImageResponseMutableLiveData;
    private MutableLiveData<TotalProductResponse> totalProductResponseMutableLiveData;
    private MutableLiveData<DeleteProductImageResponse> deleteProductImageMutableLiveData;

    private Repository repository;

    public ShopLandingPageViewModel() {
        this.parentCategoryListResponseMutableLiveData = new MutableLiveData<>();
        this.subCategoryBYParentCatIDResponseMutableLiveData = new MutableLiveData<>();
        this.addNewProductResponseMutableLiveData = new MutableLiveData<>();
        this.productForEditMutableLiveData = new MutableLiveData<>();
        this.productListResponseMutableLiveData = new MutableLiveData<>();
        this.updateProductResponseMutableLiveData = new MutableLiveData<>();
        isProductActiveResponseMutableLiveData = new MutableLiveData<>();
        this.getOrderStatusCountResponseMutableLiveData = new MutableLiveData<>();
        this.deleteProductResponseMutableLiveData = new MutableLiveData<>();
        this.shopNameAndImageResponseMutableLiveData = new MutableLiveData<>();
        this.totalProductResponseMutableLiveData = new MutableLiveData<>();
        this.deleteProductImageMutableLiveData = new MutableLiveData<>();
        this.repository = Repository.getRepository();
    }

    //get parent list from server as live data0
    public void parentCategories(Context context) {
        repository.postTogetParentCategories(parentCategoryListResponseMutableLiveData,context);
    }

    public LiveData<ParentCategoryListResponse> getParentCategoryListResponseLiveData() {
        return this.parentCategoryListResponseMutableLiveData;
    }

    //getChildCategoryListResponse by parent ID
    public void childCategoryByParentId(SubCategoryBYParentCatID parentID,Context context) {
        repository.postTogetChildCategories(subCategoryBYParentCatIDResponseMutableLiveData, parentID,context);
    }

    public LiveData<SubCategoryBYParentCatIDResponse> getChildCategories() {
        return this.subCategoryBYParentCatIDResponseMutableLiveData;
    }

    //add new product
    public void addNewProduct(AddNewProduct addNewProduct,Context context) {
        repository.addNewProducts(addNewProductResponseMutableLiveData, addNewProduct,context);
    }

    public LiveData<AddNewProductResponse> getNewProduct() {
        return this.addNewProductResponseMutableLiveData;
    }

    //product for editing
    public void productForEdit(ProductForEdit productForEdit,Context context) {
        repository.productForEdit(productForEditMutableLiveData, productForEdit,context);
    }

    public LiveData<ProductForEditResponse> getProductForEdit() {
        return this.productForEditMutableLiveData;
    }

    //get product list to show on product landing  page ...
    public void productList(ProductList productList, String lastProductId,Context context) {
        repository.productList(productListResponseMutableLiveData, productList, lastProductId,context);
    }

    public LiveData<ProductListResponse> getProductList() {
        return this.productListResponseMutableLiveData;
    }

    //update product
    public void updateproduct(UpdateProduct updateProduct,Context context) {
        repository.updateProduct(updateProductResponseMutableLiveData, updateProduct,context);
    }

    public LiveData<UpdateProductResponse> getProductUpdateResponse() {
        return this.updateProductResponseMutableLiveData;
    }

    //toggle product activeness
    public void isProductActive(IsProductActive isProductActive,Context context) {
        repository.updateProductStatus(isProductActiveResponseMutableLiveData, isProductActive,context);
    }

    public LiveData<IsProductActiveResponse> getProductActiveResponse() {
        return this.isProductActiveResponseMutableLiveData;
    }

    //get All Status Count
    public void allStatusCount(Context context) {
        repository.getAllStatusCount(getOrderStatusCountResponseMutableLiveData,context);
    }

    public MutableLiveData<GetOrderStatusCountResponse> getAllStatusCount() {
        return this.getOrderStatusCountResponseMutableLiveData;
    }

    //delete Product Api
    public void deleteProduct(DeleteProduct deleteProduct,Context context) {
        repository.deleteProduct(deleteProductResponseMutableLiveData, deleteProduct,context);
    }

    public MutableLiveData<DeleteProductResponse> deleteProductResponse() {
        return this.deleteProductResponseMutableLiveData;
    }

    //get shop name and image
    public void shopImageAndName(Context context) {
        repository.getShopNameAndImage(shopNameAndImageResponseMutableLiveData,context);
    }

    public LiveData<ShopNameAndImageResponse> getShopNameAndImage() {
        return shopNameAndImageResponseMutableLiveData;
    }

    //get total products
    public void shopTotalProducts(Context context) {
        repository.getProductCount(totalProductResponseMutableLiveData,context);
    }

    public LiveData<TotalProductResponse> getShopTotalProducts() {
        return totalProductResponseMutableLiveData;
    }

    //deleteproductImage
    public void deleteProduct(DeleteProductImage deleteProductImage,Context context) {
        repository.deleteProductImage(deleteProductImageMutableLiveData,deleteProductImage,context);
    }

    public LiveData<DeleteProductImageResponse> dPResponse() {
        return this.deleteProductImageMutableLiveData;
    }


}
