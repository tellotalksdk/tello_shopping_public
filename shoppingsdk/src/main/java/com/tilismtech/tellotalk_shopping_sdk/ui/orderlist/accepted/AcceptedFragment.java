package com.tilismtech.tellotalk_shopping_sdk.ui.orderlist.accepted;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters.AcceptedAdapter;
import com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters.ReceivedAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.OrderByStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateOrderStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderStatusCountResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateOrderStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ViewFullOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui.orderlist.OrderListViewModel;
import com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage.ShopLandingActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage.ShopLandingPageViewModel;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AcceptedFragment extends Fragment implements AcceptedAdapter.OnOrderClickListener {
    RecyclerView recycler_accepted_orders;
    AcceptedAdapter acceptedAdapter;
    List<ReceivedItemPojo> receivedItemPojos;
    OrderListViewModel orderListViewModel;
    AcceptedAdapter.OnOrderClickListener onOrderClickListener;
    ImageView screenShot;
    ScrollView scroller;
    ShopLandingPageViewModel shopLandingPageViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accepted_order_list, container, false);
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


        orderListViewModel = new ViewModelProvider(this).get(OrderListViewModel.class);
        recycler_accepted_orders = view.findViewById(R.id.recycler_accepted_orders);
        initReceivedItems();


    }


    private void initReceivedItems() {

        OrderByStatus orderByStatus = new OrderByStatus();
        orderByStatus.setProfileId(Constant.PROFILE_ID);
        orderByStatus.setStatus("2"); //for received order list

        orderListViewModel.orderByStatus(orderByStatus);
        orderListViewModel.getOrderByStatusResponse().observe(getActivity(), new Observer<GetOrderByStatusResponse>() {
            @Override
            public void onChanged(GetOrderByStatusResponse getOrderByStatusResponse) {
                if (getOrderByStatusResponse != null) {
                    // Toast.makeText(getActivity(), "" + getOrderByStatusResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                    acceptedAdapter = new AcceptedAdapter(getOrderByStatusResponse.getData().getRequestList(), getActivity(), getReference());
                    recycler_accepted_orders.setAdapter(acceptedAdapter);

                    if (getArguments() != null) {
                        if (acceptedAdapter != null) {
                            acceptedAdapter.getFilter().filter(getArguments().getString("query"));
                            Toast.makeText(getActivity(), " accepted fragment : " + getArguments().getString("query"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "accepted fragment is null ...", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
    }

    @Override
    public void OnViewFullOrderListener(int orderId) {
        EditText et_order, et_orderStatus, et_orderDate, et_ProductName, et_ProductPrice, et_ProductDiscountedPrice, et_qty, et_payableAmount, et_SellerName, et_SellerMobileNumber, et_SellerAddress, et_SellerIBAN, et_BuyerName, et_BuyerMobile, et_BuyerAddress, et_BuyerIBAN;

        //Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_product_detail_order_list);
        LinearLayout flash, productDetailLL;
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
        productDetailLL = dialog.findViewById(R.id.productDetailLL);

        flash = dialog.findViewById(R.id.linear);
        flash.setVisibility(View.GONE);

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
                captureScreenShot(bitmap, flash);
            }
        });


        ViewFullOrder viewFullOrder = new ViewFullOrder();
        viewFullOrder.setOrderId(String.valueOf(orderId));
        viewFullOrder.setProfileId(Constant.PROFILE_ID);
        viewFullOrder.setOrderStatus("2");

        orderListViewModel.viewFullOrder(viewFullOrder);
        orderListViewModel.getViewFullOrderResponse().observe(getActivity(), new Observer<ViewFullOrderResponse>() {
            @Override
            public void onChanged(ViewFullOrderResponse viewFullOrderResponse) {
                // Toast.makeText(getActivity(), "order : " + viewFullOrderResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();

                if (viewFullOrderResponse.getData().getRequestList() != null) {
                    et_order.setText(viewFullOrderResponse.getData().getRequestList().getOrderno());
                    et_orderStatus.setText("Accepted");
                    et_orderDate.setText(viewFullOrderResponse.getData().getRequestList().getOrderdate());

                    productDetailLL.removeAllViews();
                    if (viewFullOrderResponse.getData().getRequestList().getProductsDetails() != null) {
                        for (int i = 0; i < viewFullOrderResponse.getData().getRequestList().getProductsDetails().size(); i++) {
                            //productDetailLL.addView();
                            View inflater = getLayoutInflater().inflate(R.layout.product_detail, null);

                            EditText et_ProductName = inflater.findViewById(R.id.et_ProductName);
                            EditText et_ProductPrice = inflater.findViewById(R.id.et_ProductPrice);
                            EditText et_ProductDiscountedPrice = inflater.findViewById(R.id.et_ProductDiscountedPrice);
                            EditText et_qty = inflater.findViewById(R.id.et_qty);
                            EditText et_payableAmount = inflater.findViewById(R.id.et_payableAmount);


                            et_ProductName.setText(viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getTitle());
                            et_ProductPrice.setText(viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getPrice());
                            et_ProductDiscountedPrice.setText(viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getDiscount());
                            et_qty.setText(viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getQuantity());

                            //payabale amount = discount * qty of product
                            int payableAmount = Integer.parseInt(viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getDiscount()) * Integer.parseInt(viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getQuantity());
                            et_payableAmount.setText(String.valueOf(payableAmount));
                            productDetailLL.addView(inflater);
                        }
                    }


                    et_SellerName.setText(viewFullOrderResponse.getData().getRequestList().getSeller_firstname() + " " + viewFullOrderResponse.getData().getRequestList().getSeller_lastname());
                    et_SellerMobileNumber.setText(viewFullOrderResponse.getData().getRequestList().getSeller_mobile());
                    et_SellerAddress.setText(viewFullOrderResponse.getData().getRequestList().getSeller_Address());
                  //  et_SellerIBAN.setText(viewFullOrderResponse.getData().getRequestList());

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

    private void captureScreenShot(Bitmap bitmap, LinearLayout flash) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/TelloShopping");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        Log.i("TAG", "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getActivity(), "Screen Shot Captured...", Toast.LENGTH_SHORT).show();

            flash.setVisibility(View.VISIBLE);
            AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
            animation1.setDuration(500);
            //  animation1.setStartOffset(5000);
            animation1.setFillAfter(true);
            flash.startAnimation(animation1);

            //this code refresh gallery
            MediaScannerConnection.scanFile(getActivity(), new String[]{file.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("TAG", "Scanned " + path);
                        }
                    });

            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getAbsolutePath()))));


        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void OnRiderInfoUpdateListener(int position) {
        Toast.makeText(getActivity(), "position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnStatusChange(int status, int OrderID) {
        //  Toast.makeText(getActivity(), "" + status + OrderID, Toast.LENGTH_SHORT).show();

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

    public AcceptedAdapter.OnOrderClickListener getReference() {
        return this;
    }
}
