package com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.IsProductActive;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.IsProductActiveResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductForEdit;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductList;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderStatusCountResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductForEditResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopNameAndImageResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.TotalProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

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
        this.repository = Repository.getRepository();
    }

    //get parent list from server as live data0
    public void parentCategories() {
        repository.postTogetParentCategories(parentCategoryListResponseMutableLiveData);
    }

    public LiveData<ParentCategoryListResponse> getParentCategoryListResponseLiveData() {
        return this.parentCategoryListResponseMutableLiveData;
    }

    //getChildCategoryListResponse by parent ID
    public void childCategoryByParentId(SubCategoryBYParentCatID parentID) {
        repository.postTogetChildCategories(subCategoryBYParentCatIDResponseMutableLiveData, parentID);
    }

    public LiveData<SubCategoryBYParentCatIDResponse> getChildCategories() {
        return this.subCategoryBYParentCatIDResponseMutableLiveData;
    }

    //add new product
    public void addNewProduct(AddNewProduct addNewProduct) {
        repository.addNewProducts(addNewProductResponseMutableLiveData, addNewProduct);
    }

    public LiveData<AddNewProductResponse> getNewProduct() {
        return this.addNewProductResponseMutableLiveData;
    }

    //product for editing
    public void productForEdit(ProductForEdit productForEdit) {
        repository.productForEdit(productForEditMutableLiveData, productForEdit);
    }

    public LiveData<ProductForEditResponse> getProductForEdit() {
        return this.productForEditMutableLiveData;
    }

    //get product list to show on product landing  page ...
    public void productList(ProductList productList, String lastProductId) {
        repository.productList(productListResponseMutableLiveData, productList, lastProductId);
    }

    public LiveData<ProductListResponse> getProductList() {
        return this.productListResponseMutableLiveData;
    }

    //update product
    public void updateproduct(UpdateProduct updateProduct) {
        repository.updateProduct(updateProductResponseMutableLiveData, updateProduct);
    }

    public LiveData<UpdateProductResponse> getProductUpdateResponse() {
        return this.updateProductResponseMutableLiveData;
    }

    //toggle product activeness
    public void isProductActive(IsProductActive isProductActive) {
        repository.updateProductStatus(isProductActiveResponseMutableLiveData, isProductActive);
    }

    public LiveData<IsProductActiveResponse> getProductActiveResponse() {
        return this.isProductActiveResponseMutableLiveData;
    }

    //get All Status Count
    public void allStatusCount() {
        repository.getAllStatusCount(getOrderStatusCountResponseMutableLiveData);
    }

    public MutableLiveData<GetOrderStatusCountResponse> getAllStatusCount() {
        return this.getOrderStatusCountResponseMutableLiveData;
    }

    //delete Product Api
    public void deleteProduct(DeleteProduct deleteProduct) {
        repository.deleteProduct(deleteProductResponseMutableLiveData, deleteProduct);
    }

    public MutableLiveData<DeleteProductResponse> deleteProductResponse() {
        return this.deleteProductResponseMutableLiveData;
    }

    //get shop name and image
    public void shopImageAndName() {
        repository.getShopNameAndImage(shopNameAndImageResponseMutableLiveData);
    }

    public LiveData<ShopNameAndImageResponse> getShopNameAndImage() {
        return shopNameAndImageResponseMutableLiveData;
    }

    //get total products
    public void shopTotalProducts() {
        repository.getProductCount(totalProductResponseMutableLiveData);
    }

    public LiveData<TotalProductResponse> getShopTotalProducts() {
        return totalProductResponseMutableLiveData;
    }


}
