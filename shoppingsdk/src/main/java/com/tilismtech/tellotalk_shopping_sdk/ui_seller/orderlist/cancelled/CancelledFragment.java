package com.tilismtech.tellotalk_shopping_sdk.ui_seller.orderlist.cancelled;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters.CancelledAdapter;
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
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.orderlist.OrderListViewModel;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage.ShopLandingActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage.ShopLandingPageViewModel;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CancelledFragment extends Fragment implements CancelledAdapter.OnOrderClickListener {

    RecyclerView recycler_cancelled_orders;
    CancelledAdapter cancelledAdapter;
    List<ReceivedItemPojo> receivedItemPojos;
    OrderListViewModel orderListViewModel;
    private ShopLandingPageViewModel shopLandingPageViewModel;
    ImageView screenShot;
    ScrollView scroller;
    EditText etRiderName, etRiderNumber, etRiderTracking;
    public com.tilismtech.tellotalk_shopping_sdk.customviews.HorizontalDottedProgress horizontalProgressBar;
    Dialog dialogCongratulation;
    private int totalSumofAllOrderAmount = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cancelled_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopLandingPageViewModel = new ViewModelProvider(this).get(ShopLandingPageViewModel.class);
        //this will update the order list all tabs status counts
        horizontalProgressBar = view.findViewById(R.id.horizontalProgressBar);

        shopLandingPageViewModel.allStatusCount(getActivity());
        shopLandingPageViewModel.getAllStatusCount().observe(getActivity(), new Observer<GetOrderStatusCountResponse>() {
            @Override
            public void onChanged(GetOrderStatusCountResponse getOrderStatusCountResponse) {
                if (getOrderStatusCountResponse != null) {
                    //Toast.makeText(ShopLandingActivity.this, ":" + getOrderStatusCountResponse.getData().getRequestList().get(0).getRecieved(), Toast.LENGTH_SHORT).show();
                    ((ShopLandingActivity) getActivity()).setOrderStatus(getOrderStatusCountResponse.getData().getRequestList());
                }
            }
        });


       /* if (getArguments() != null) {
            Toast.makeText(getActivity(), "" + getArguments().getString("query"), Toast.LENGTH_SHORT).show();
        }
*/
        orderListViewModel = new ViewModelProvider(this).get(OrderListViewModel.class);
        recycler_cancelled_orders = view.findViewById(R.id.recycler_cancelled_orders);
        initReceivedItems();

    }


    private void initReceivedItems() {


        OrderByStatus orderByStatus = new OrderByStatus();
        orderByStatus.setProfileId(Constant.PROFILE_ID);
        orderByStatus.setStatus("6"); //for received order list

        orderListViewModel.orderByStatus(orderByStatus,getActivity());
        orderListViewModel.getOrderByStatusResponse().observe(getActivity(), new Observer<GetOrderByStatusResponse>() {
            @Override
            public void onChanged(GetOrderByStatusResponse getOrderByStatusResponse) {
                if (getOrderByStatusResponse != null) {
                    //  Toast.makeText(getActivity(), "" + getOrderByStatusResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                    cancelledAdapter = new CancelledAdapter(getOrderByStatusResponse.getData().getRequestList(), getActivity(), getReference());
                    recycler_cancelled_orders.setAdapter(cancelledAdapter);

                    if (getArguments() != null) {
                        if (cancelledAdapter != null) {
                            cancelledAdapter.getFilter().filter(getArguments().getString("query"));
                            //  Toast.makeText(getActivity(), " cancelled fragment  : " + getArguments().getString("query"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "cancelled fragment is null ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    horizontalProgressBar.clearAnimation();
                    horizontalProgressBar.setVisibility(View.GONE);
                }
                horizontalProgressBar.clearAnimation();
                horizontalProgressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void OnViewFullOrderListener(int orderId) {
        EditText et_order, et_orderStatus, et_orderDate, et_ProductName, et_ProductPrice, et_ProductDiscountedPrice, et_qty, et_payableAmount, et_SellerName, et_SellerMobileNumber, et_SellerAddress, et_SellerIBAN, et_BuyerName, et_BuyerMobile, et_BuyerAddress, et_BuyerIBAN;
        LinearLayout flash, productDetailLL;
        //Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
        et_SellerAddress = dialog.findViewById(R.id.et_SellerAddress);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    CaptureScreenShot(bitmap, flash);
                } else {
                    captureScreenShot(bitmap, flash);
                }

            }
        });


        ViewFullOrder viewFullOrder = new ViewFullOrder();
        viewFullOrder.setOrderId(String.valueOf(orderId));
        viewFullOrder.setProfileId(Constant.PROFILE_ID);
        viewFullOrder.setOrderStatus("6");

        orderListViewModel.viewFullOrder(viewFullOrder,getActivity());
        orderListViewModel.getViewFullOrderResponse().observe(getActivity(), new Observer<ViewFullOrderResponse>() {
            @Override
            public void onChanged(ViewFullOrderResponse viewFullOrderResponse) {
                // Toast.makeText(getActivity(), "order : " + viewFullOrderResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                if (viewFullOrderResponse.getData().getRequestList() != null) {
                    et_order.setText(viewFullOrderResponse.getData().getRequestList().getOrderNo());
                    et_orderStatus.setText("Cancelled");
                    et_orderDate.setText(" " + viewFullOrderResponse.getData().getRequestList().getOrderDate());

                    productDetailLL.removeAllViews();
                    if (viewFullOrderResponse.getData().getRequestList().getProductsDetails() != null) {
                        for (int i = 0; i < viewFullOrderResponse.getData().getRequestList().getProductsDetails().size(); i++) {
                            //productDetailLL.addView();
                            View inflater = getLayoutInflater().inflate(R.layout.product_detail, null);
                            totalSumofAllOrderAmount += Integer.parseInt(viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getDiscount());

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
                        View inflater1 = getLayoutInflater().inflate(R.layout.product_total, null);
                        TextView totalAmount = inflater1.findViewById(R.id.totalAmount);
                        totalAmount.setText(String.valueOf(totalSumofAllOrderAmount));
                        productDetailLL.addView(inflater1);
                        totalSumofAllOrderAmount = 0;
                    }


                    et_SellerName.setText(viewFullOrderResponse.getData().getRequestList().getSellerDetails().get(0).getAccountTitle());
                    et_SellerMobileNumber.setText(viewFullOrderResponse.getData().getRequestList().getSellerDetails().get(0).getMobile());
                    et_SellerAddress.setText(viewFullOrderResponse.getData().getRequestList().getSellerDetails().get(0).getAddress());
                    et_SellerIBAN.setText(viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getAccountNumber());

                    et_BuyerName.setText(viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getFirstName() + " " + viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getMiddleName() + " " + viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getLastName());
                    et_BuyerMobile.setText(viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getMobile());
                    et_BuyerAddress.setText(viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getCompleteAddress());
                    et_BuyerIBAN.setText(viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getAccountNumber());

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

    private void CaptureScreenShot(Bitmap bitmap, LinearLayout flash) {
        OutputStream fos;

        try {
            ContentResolver contentResolver = getActivity().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "IMAGE_" + ".jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "TelloShopping");
            Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            fos = (OutputStream) contentResolver.openOutputStream(Objects.requireNonNull(imageUri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Objects.requireNonNull(fos);

            Toast.makeText(getActivity(), "Screen Shot Captured...", Toast.LENGTH_SHORT).show();

            flash.setVisibility(View.VISIBLE);
            AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
            animation1.setDuration(500);
            //  animation1.setStartOffset(5000);
            animation1.setFillAfter(true);
            flash.startAnimation(animation1);

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Some thing went wrong try again...", Toast.LENGTH_SHORT).show();
        }
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

            //this code
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


    @Override
    public void OnRiderInfoUpdateListener(int orderId) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
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

                if (TextUtils.isEmpty(etRiderName.getText().toString()) &&
                        TextUtils.isEmpty(etRiderNumber.getText().toString()) &&
                        TextUtils.isEmpty(etRiderTracking.getText().toString())
                ) {
                    Toast.makeText(getActivity(), "Some Fields are missing...", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateRiderInfo updateRiderInfo = new UpdateRiderInfo();
                    updateRiderInfo.setRiderName(etRiderName.getText().toString());
                    updateRiderInfo.setRiderContact(etRiderNumber.getText().toString());
                    updateRiderInfo.setOrderTrackingId(etRiderTracking.getText().toString());
                    updateRiderInfo.setOrderId(String.valueOf(orderId));
                    updateRiderInfo.setProfileId(Constant.PROFILE_ID);

                    orderListViewModel.updateRiderInfo(updateRiderInfo,getActivity());
                    orderListViewModel.getupdateRiderInfoResponse().observe(getActivity(), new Observer<UpdateRiderInfoResponse>() {
                        @Override
                        public void onChanged(UpdateRiderInfoResponse updateRiderInfoResponse) {
                            if (updateRiderInfoResponse != null) {
                                dialog.dismiss();
                                initReceivedItems();
                                shopLandingPageViewModel.allStatusCount(getActivity());
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
    public void OnRiderInfoUpdateListener(int position, GetOrderByStatusResponse.Request request) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
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

        etRiderName.setText(request.getRiderName().toString());
        etRiderNumber.setText(request.getRiderContact().toString());

        Button done = dialog.findViewById(R.id.confirmRiderbtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkValidation()
                ) {
                    UpdateRiderInfo updateRiderInfo = new UpdateRiderInfo();
                    updateRiderInfo.setRiderName(etRiderName.getText().toString());
                    updateRiderInfo.setRiderContact(etRiderNumber.getText().toString());
                    updateRiderInfo.setOrderTrackingId(etRiderTracking.getText().toString());
                    updateRiderInfo.setOrderId(String.valueOf(position));
                    updateRiderInfo.setProfileId(Constant.PROFILE_ID);

                    orderListViewModel.updateRiderInfo(updateRiderInfo,getActivity());
                    orderListViewModel.getupdateRiderInfoResponse().observe(getActivity(), new Observer<UpdateRiderInfoResponse>() {
                        @Override
                        public void onChanged(UpdateRiderInfoResponse updateRiderInfoResponse) {
                            if (updateRiderInfoResponse != null) {
                                // Toast.makeText(getActivity(), "" + updateRiderInfoResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                initReceivedItems();
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
        dialogCongratulation = new Dialog(getActivity());
        dialogCongratulation.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogCongratulation.setContentView(R.layout.dialog_order_status_confirmation);
        dialogCongratulation.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        dialogCongratulation.show();

        Button continue_status = dialogCongratulation.findViewById(R.id.continue_status);
        Button cancel_status = dialogCongratulation.findViewById(R.id.cancel_status);
        TextView dialogMessage = dialogCongratulation.findViewById(R.id.dialogMsg);
        EditText editText = dialogCongratulation.findViewById(R.id.reasonForCancellation);
        editText.setVisibility(View.VISIBLE);

        if (status == 2) {
            dialogMessage.setText("After clicking on continue button the order status will be changed to Accept...");
            editText.setVisibility(View.GONE);
        } else if (status == 3) {
            dialogMessage.setText("After clicking on continue button the order status will be changed to Dispatch...");
            editText.setVisibility(View.GONE);
        } else if (status == 4) {
            dialogMessage.setText("After clicking on continue button the order status will be changed to Deliver...");
            editText.setVisibility(View.GONE);
        } else if (status == 5) {
            dialogMessage.setText("After clicking on continue button the order status will be changed to Paid...");
            editText.setVisibility(View.GONE);
        }

        continue_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateOrderStatus updateOrderStatus = new UpdateOrderStatus();
                updateOrderStatus.setOrderId(String.valueOf(OrderID));
                updateOrderStatus.setProfileId(Constant.PROFILE_ID);
                updateOrderStatus.setStatus(String.valueOf(status));

                orderListViewModel.updateOrderStatus(updateOrderStatus,getActivity());
                orderListViewModel.updateOrderStatusResponse().observe(getActivity(), new Observer<UpdateOrderStatusResponse>() {
                    @Override
                    public void onChanged(UpdateOrderStatusResponse updateOrderStatusResponse) {
                        if (updateOrderStatusResponse != null) {
                            Toast.makeText(getActivity(), "Order Has been moved...", Toast.LENGTH_SHORT).show();
                            initReceivedItems();
                            shopLandingPageViewModel.allStatusCount(getActivity());
                            shopLandingPageViewModel.getAllStatusCount().observe(getActivity(), new Observer<GetOrderStatusCountResponse>() {
                                @Override
                                public void onChanged(GetOrderStatusCountResponse getOrderStatusCountResponse) {
                                    if (getOrderStatusCountResponse != null) {
                                        //Toast.makeText(ShopLandingActivity.this, ":" + getOrderStatusCountResponse.getData().getRequestList().get(0).getRecieved(), Toast.LENGTH_SHORT).show();
                                        ((ShopLandingActivity) getActivity()).setOrderStatus(getOrderStatusCountResponse.getData().getRequestList());
                                        dialogCongratulation.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        cancel_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCongratulation.dismiss();
            }
        });


    }

    public CancelledAdapter.OnOrderClickListener getReference() {
        return this;
    }


    //validation for rider info dialog box fields.
    public boolean checkValidation() {
        if (etRiderName.getText().toString() == null || TextUtils.isEmpty(etRiderName.getText().toString())) {
            Toast.makeText(getActivity(), "Rider Name is Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

       /* if (etRiderNumber.getText().toString() == null || TextUtils.isEmpty(etRiderNumber.getText().toString())) {
            Toast.makeText(getActivity(), "Rider Number is Required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etRiderTracking.getText().toString() == null || TextUtils.isEmpty(etRiderTracking.getText().toString())) {
            Toast.makeText(getActivity(), "Tracking ID is Required...", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;
    }
}
