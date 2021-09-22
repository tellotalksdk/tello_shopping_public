package com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.orderlist;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.OrderByStatus;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.UpdateOrderStatus;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.UpdateRiderInfo;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetAllOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetOrderStatusCountResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.UpdateOrderStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.UpdateRiderInfoResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ViewFullOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.repository.Repository;

public class OrderListViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<ViewFullOrderResponse> viewFullOrderResponseMutableLiveData;
    private MutableLiveData<UpdateRiderInfoResponse> updateRiderInfoResponseMutableLiveData;
    private MutableLiveData<GetOrderByStatusResponse> getOrderByStatusResponseMutableLiveData;
    private MutableLiveData<UpdateOrderStatusResponse> updateOrderStatusResponseMutableLiveData;
    private MutableLiveData<GetAllOrderResponse> getAllOrderResponseMutableLiveData;
    private MutableLiveData<GetOrderStatusCountResponse> getOrderStatusCountResponseMutableLiveData;


    public OrderListViewModel() {
        this.repository = Repository.getRepository();
        this.viewFullOrderResponseMutableLiveData = new MutableLiveData<>();
        this.updateRiderInfoResponseMutableLiveData = new MutableLiveData<>();
        this.getOrderByStatusResponseMutableLiveData = new MutableLiveData<>();
        this.updateOrderStatusResponseMutableLiveData = new MutableLiveData<>();
        this.getAllOrderResponseMutableLiveData = new MutableLiveData<>();
        this.getOrderStatusCountResponseMutableLiveData = new MutableLiveData<>();
    }

    //viewFullOrder apis
    public void viewFullOrder(ViewFullOrder viewFullOrder, Context context) {
        repository.viewFullOrder(viewFullOrderResponseMutableLiveData, viewFullOrder, context);
    }

    public MutableLiveData<ViewFullOrderResponse> getViewFullOrderResponse() {
        return this.viewFullOrderResponseMutableLiveData;
    }

    //updateRider Info apis
    public void updateRiderInfo(UpdateRiderInfo updateRiderInfo, Context context) {
        repository.updateRiderInfo(updateRiderInfoResponseMutableLiveData, updateRiderInfo, context);
    }

    public MutableLiveData<UpdateRiderInfoResponse> getupdateRiderInfoResponse() {
        return this.updateRiderInfoResponseMutableLiveData;
    }

    //order By Status
    public void orderByStatus(OrderByStatus order, Context context) {
        repository.getOrderbyStatus(getOrderByStatusResponseMutableLiveData, order, context);
    }

    public MutableLiveData<GetOrderByStatusResponse> getOrderByStatusResponse() {
        return this.getOrderByStatusResponseMutableLiveData;
    }

    //update Order Status -- move to apis
    public void updateOrderStatus(UpdateOrderStatus order, Context context) {
        repository.updateOrderStatus(updateOrderStatusResponseMutableLiveData, order, context);
    }

    public MutableLiveData<UpdateOrderStatusResponse> updateOrderStatusResponse() {
        return this.updateOrderStatusResponseMutableLiveData;
    }

    //getAllOrder to inside order list (All) tabs
    public void AllOrders(String profileId, Context context) {
        repository.getAllOrders(getAllOrderResponseMutableLiveData, profileId, context);
    }

    public MutableLiveData<GetAllOrderResponse> getOrders() {
        return this.getAllOrderResponseMutableLiveData;
    }


}
