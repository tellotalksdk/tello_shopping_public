package com.tilismtech.tellotalk_shopping_sdk.ui.orderlist.delivered;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters.DeliveredAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.OrderByStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateOrderStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateRiderInfo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderStatusCountResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateOrderStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateRiderInfoResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ViewFullOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui.orderlist.OrderListViewModel;
import com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage.ShopLandingActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage.ShopLandingPageViewModel;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class DeliveredFragment extends Fragment implements DeliveredAdapter.OnOrderClickListener {

    RecyclerView recycler_delivered_orders;
    DeliveredAdapter deliveredAdapter;
    List<ReceivedItemPojo> receivedItemPojos;
    OrderListViewModel orderListViewModel;
    ImageView screenShot;
    ScrollView scroller;
    ShopLandingPageViewModel shopLandingPageViewModel;
    EditText etRiderName, etRiderNumber, etRiderTracking;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delivered_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopLandingPageViewModel = new ViewModelProvider(this).get(ShopLandingPageViewModel.class);
        //this will update the order list all tabs status counts
        shopLandingPageViewModel.allStatusCount();
        shopLandingPageViewModel.getAllStatusCount().observe(getActivity(), new Observer<GetOrderStatusCountResponse>() {
            @Override
            public void onChanged(GetOrderStatusCountResponse getOrderStatusCountResponse) {
                if (getOrderStatusCountResponse != null) {
                    //Toast.makeText(ShopLandingActivity.this, ":" + getOrderStatusCountResponse.getData().getRequestList().get(0).getRecieved(), Toast.LENGTH_SHORT).show();
                    ((ShopLandingActivity) getActivity()).setOrderStatus(getOrderStatusCountResponse.getData().getRequestList());
                }
            }
        });


        if (getArguments() != null) {
            Toast.makeText(getActivity(), "" + getArguments().getString("query"), Toast.LENGTH_SHORT).show();
        }
        orderListViewModel = new ViewModelProvider(this).get(OrderListViewModel.class);
        recycler_delivered_orders = view.findViewById(R.id.recycler_delivered_orders);
        initReceivedItems();

    }

    private void initReceivedItems() {
       /* receivedItemPojos = new ArrayList<>();
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345", "Ahmed \n1234567", "Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345", "Ahmed \n1234567", "Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345", "Ahmed \n1234567", "Unit # 102 , Parsa Tower , Karachi"));
       */


        OrderByStatus orderByStatus = new OrderByStatus();
        orderByStatus.setProfileId(Constant.PROFILE_ID);
        orderByStatus.setStatus("4"); //for received order list

        orderListViewModel.orderByStatus(orderByStatus);
        orderListViewModel.getOrderByStatusResponse().observe(getActivity(), new Observer<GetOrderByStatusResponse>() {
            @Override
            public void onChanged(GetOrderByStatusResponse getOrderByStatusResponse) {
                if (getOrderByStatusResponse != null) {
                    //  Toast.makeText(getActivity(), "" + getOrderByStatusResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                    deliveredAdapter = new DeliveredAdapter(getOrderByStatusResponse.getData().getRequestList(), getActivity(), getReference());
                    recycler_delivered_orders.setAdapter(deliveredAdapter);
                }
            }
        });

    }

    @Override
    public void OnViewFullOrderListener(int orderId) {
        EditText et_order, et_orderStatus, et_orderDate, et_ProductName, et_ProductPrice, et_ProductDiscountedPrice, et_qty, et_payableAmount, et_SellerName, et_SellerMobileNumber, et_SellerAddress, et_SellerIBAN, et_BuyerName, et_BuyerMobile, et_BuyerAddress, et_BuyerIBAN;

        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_product_detail_order_list);

        ImageView iv_back = dialog.findViewById(R.id.iv_back);
        screenShot = dialog.findViewById(R.id.screenShot);
        scroller = dialog.findViewById(R.id.scroller);
        et_order = dialog.findViewById(R.id.et_order);
        et_orderDate = dialog.findViewById(R.id.et_orderDate);
        et_orderStatus = dialog.findViewById(R.id.et_orderStatus);
        et_ProductName = dialog.findViewById(R.id.et_ProductName);
        et_ProductPrice = dialog.findViewById(R.id.et_ProductPrice);
        et_ProductDiscountedPrice = dialog.findViewById(R.id.et_ProductDiscountedPrice);
        et_qty = dialog.findViewById(R.id.et_qty);
        et_payableAmount = dialog.findViewById(R.id.et_payableAmount);
        et_SellerName = dialog.findViewById(R.id.et_SellerName);
        et_SellerMobileNumber = dialog.findViewById(R.id.et_SellerMobileNumber);
        et_SellerAddress = dialog.findViewById(R.id.et_order);
        et_SellerIBAN = dialog.findViewById(R.id.et_SellerIBAN);
        et_BuyerName = dialog.findViewById(R.id.et_BuyerName);
        et_BuyerMobile = dialog.findViewById(R.id.et_BuyerMobile);
        et_BuyerAddress = dialog.findViewById(R.id.et_BuyerAddress);
        et_BuyerIBAN = dialog.findViewById(R.id.et_BuyerIBAN);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        screenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getBitmapFromView(scroller, scroller.getChildAt(0).getHeight(), scroller.getChildAt(0).getWidth());
                // screenShot.setImageBitmap(bitmap);

            }
        });


        ViewFullOrder viewFullOrder = new ViewFullOrder();
        viewFullOrder.setOrderId(String.valueOf(orderId));
        viewFullOrder.setProfileId(Constant.PROFILE_ID);
        viewFullOrder.setOrderStatus("4");

        orderListViewModel.viewFullOrder(viewFullOrder);
        orderListViewModel.getViewFullOrderResponse().observe(getActivity(), new Observer<ViewFullOrderResponse>() {
            @Override
            public void onChanged(ViewFullOrderResponse viewFullOrderResponse) {
                Toast.makeText(getActivity(), "order : " + viewFullOrderResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();

                if (viewFullOrderResponse.getData().getRequestList() != null) {
                    et_order.setText(viewFullOrderResponse.getData().getRequestList().getOrderno());
                    et_orderStatus.setText(viewFullOrderResponse.getData().getRequestList().getOrderStatus());
                    et_orderDate.setText(viewFullOrderResponse.getData().getRequestList().getOrderdate());
                    // et_ProductName.setText(viewFullOrderResponse.getData().getRequestList().getPro);

                    /*   et_ProductPrice.setText(viewFullOrderResponse.getData().getRequestList());
                         et_ProductDiscountedPrice.setText(viewFullOrderResponse.getData().getRequestList());
                         et_qty.setText(viewFullOrderResponse.getData().getRequestList());
                 et_payableAmount.setText(viewFullOrderResponse.getData().getRequestList());

                et_SellerName.setText(viewFullOrderResponse.getData().getRequestList());
                et_SellerMobileNumber.setText(viewFullOrderResponse.getData().getRequestList());
                et_SellerAddress.setText(viewFullOrderResponse.getData().getRequestList());
                et_SellerIBAN.setText(viewFullOrderResponse.getData().getRequestList());*/

                    et_BuyerName.setText(viewFullOrderResponse.getData().getRequestList().getFirstname() + viewFullOrderResponse.getData().getRequestList().getMiddlename());
                    et_BuyerMobile.setText(viewFullOrderResponse.getData().getRequestList().getMobile());
                    et_BuyerAddress.setText(viewFullOrderResponse.getData().getRequestList().getCompleteAddress());
                    // et_BuyerIBAN.setText(viewFullOrderResponse.getData().getRequestList());
                }
            }
        });

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        wlp.gravity = Gravity.BOTTOM;
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    //screen shot whole receipt...
    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void OnRiderInfoUpdateListener(int orderId) {
        //  Toast.makeText(getActivity(), "position " + position, Toast.LENGTH_SHORT).show();

        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_add_rider_info);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        wlp.gravity = Gravity.BOTTOM;
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


        etRiderName = dialog.findViewById(R.id.etRiderName);
        etRiderNumber = dialog.findViewById(R.id.etRiderContact);
        etRiderTracking = dialog.findViewById(R.id.etRiderTracking);


        Button done = dialog.findViewById(R.id.confirmRiderbtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidation()) {
                    UpdateRiderInfo updateRiderInfo = new UpdateRiderInfo();
                    updateRiderInfo.setRiderName(etRiderName.getText().toString());
                    updateRiderInfo.setRiderContact(etRiderNumber.getText().toString());
                    updateRiderInfo.setOrderTrackingId(etRiderTracking.getText().toString());
                    updateRiderInfo.setOrderId(String.valueOf(orderId));
                    updateRiderInfo.setProfileId(Constant.PROFILE_ID);

                    orderListViewModel.updateRiderInfo(updateRiderInfo);
                    orderListViewModel.getupdateRiderInfoResponse().observe(getActivity(), new Observer<UpdateRiderInfoResponse>() {
                        @Override
                        public void onChanged(UpdateRiderInfoResponse updateRiderInfoResponse) {
                            if (updateRiderInfoResponse != null) {
                                //    Toast.makeText(getActivity(), "" + updateRiderInfoResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                initReceivedItems();
                                shopLandingPageViewModel.allStatusCount();
                                shopLandingPageViewModel.getAllStatusCount().observe(getActivity(), new Observer<GetOrderStatusCountResponse>() {
                                    @Override
                                    public void onChanged(GetOrderStatusCountResponse getOrderStatusCountResponse) {
                                        if (getOrderStatusCountResponse != null) {
                                            //Toast.makeText(ShopLandingActivity.this, ":" + getOrderStatusCountResponse.getData().getRequestList().get(0).getRecieved(), Toast.LENGTH_SHORT).show();
                                            ((ShopLandingActivity) getActivity()).setOrderStatus(getOrderStatusCountResponse.getData().getRequestList());
                                        }
                                    }
                                });
                            }
                        }
                    });
                }

            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    @Override
    public void OnStatusChange(int status, int OrderID) {
        UpdateOrderStatus updateOrderStatus = new UpdateOrderStatus();
        updateOrderStatus.setOrderId(String.valueOf(OrderID));
        updateOrderStatus.setProfileId(Constant.PROFILE_ID);
        updateOrderStatus.setStatus(String.valueOf(status));

        orderListViewModel.updateOrderStatus(updateOrderStatus);
        orderListViewModel.updateOrderStatusResponse().observe(getActivity(), new Observer<UpdateOrderStatusResponse>() {
            @Override
            public void onChanged(UpdateOrderStatusResponse updateOrderStatusResponse) {
                if (updateOrderStatusResponse != null) {
                    Toast.makeText(getActivity(), "Order Has been moved...", Toast.LENGTH_SHORT).show();
                    initReceivedItems();
                    shopLandingPageViewModel.allStatusCount();
                    shopLandingPageViewModel.getAllStatusCount().observe(getActivity(), new Observer<GetOrderStatusCountResponse>() {
                        @Override
                        public void onChanged(GetOrderStatusCountResponse getOrderStatusCountResponse) {
                            if (getOrderStatusCountResponse != null) {
                                //Toast.makeText(ShopLandingActivity.this, ":" + getOrderStatusCountResponse.getData().getRequestList().get(0).getRecieved(), Toast.LENGTH_SHORT).show();
                                ((ShopLandingActivity) getActivity()).setOrderStatus(getOrderStatusCountResponse.getData().getRequestList());
                            }
                        }
                    });
                }
            }
        });
    }

    public DeliveredAdapter.OnOrderClickListener getReference() {
        return this;
    }


    //validation for rider info dialog box fields.
    public boolean checkValidation() {
        if (etRiderName.getText().toString() == null || TextUtils.isEmpty(etRiderName.getText().toString())) {
            Toast.makeText(getActivity(), "Rider Name is Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etRiderNumber.getText().toString() == null || TextUtils.isEmpty(etRiderNumber.getText().toString())) {
            Toast.makeText(getActivity(), "Rider Number is Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etRiderTracking.getText().toString() == null || TextUtils.isEmpty(etRiderTracking.getText().toString())) {
            Toast.makeText(getActivity(), "Tracking ID is Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
