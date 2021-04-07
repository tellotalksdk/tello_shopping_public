package com.tilismtech.tellotalk_shopping_sdk.ui.orderlist;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.OrderByStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateRiderInfo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateOrderStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateRiderInfoResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ViewFullOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk.repository.Repository;

public class OrderListViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<ViewFullOrderResponse> viewFullOrderResponseMutableLiveData;
    private MutableLiveData<UpdateRiderInfoResponse> updateRiderInfoResponseMutableLiveData;
    private MutableLiveData<GetOrderByStatusResponse> getOrderByStatusResponseMutableLiveData;
    private MutableLiveData<UpdateOrderStatusResponse> updateOrderStatusResponseMutableLiveData;


    public OrderListViewModel() {
        this.repository = Repository.getRepository();
        this.viewFullOrderResponseMutableLiveData = new MutableLiveData<>();
        this.updateRiderInfoResponseMutableLiveData = new MutableLiveData<>();
        this.getOrderByStatusResponseMutableLiveData = new MutableLiveData<>();
        this.updateOrderStatusResponseMutableLiveData = new MutableLiveData<>();
    }

    //viewFullOrder apis
    public void viewFullOrder(ViewFullOrder viewFullOrder) {
        repository.viewFullOrder(viewFullOrderResponseMutableLiveData, viewFullOrder);
    }

    public MutableLiveData<ViewFullOrderResponse> getViewFullOrderResponse() {
        return this.viewFullOrderResponseMutableLiveData;
    }

    //updateRider Info apis
    public void updateRiderInfo(UpdateRiderInfo updateRiderInfo) {
        repository.updateRiderInfo(updateRiderInfoResponseMutableLiveData, updateRiderInfo);
    }

    public MutableLiveData<UpdateRiderInfoResponse> getupdateRiderInfoResponse() {
        return this.updateRiderInfoResponseMutableLiveData;
    }

    //order By Status
    public void orderByStatus(OrderByStatus order) {
        repository.getOrderbyStatus(getOrderByStatusResponseMutableLiveData, order);
    }

    public MutableLiveData<GetOrderByStatusResponse> getOrderByStatusResponse() {
        return this.getOrderByStatusResponseMutableLiveData;
    }

    //update Order Status -- move to apis
    public void updateOrderStatus(OrderByStatus order) {
        repository.getOrderbyStatus(getOrderByStatusResponseMutableLiveData, order);
    }

    public MutableLiveData<GetOrderByStatusResponse> updateOrderStatusResponse() {
        return this.getOrderByStatusResponseMutableLiveData;
    }


}