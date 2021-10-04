package com.tilismtech.tellotalk_shopping_sdk.ui_seller.orderlist.accepted;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.tilismtech.tellotalk_shopping_sdk.customviews.HorizontalDottedProgress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ItemDetail;
import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters.AcceptedAdapter;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.OrderByStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateOrderStatus;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ViewFullOrder;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderStatusCountResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateOrderStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ViewFullOrderResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.orderlist.OrderListViewModel;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage.ShopLandingActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage.ShopLandingPageViewModel;
import com.tilismtech.tellotalk_shopping_sdk.utils.ApplicationUtils;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AcceptedFragment extends Fragment implements AcceptedAdapter.OnOrderClickListener {

    public static final int PERMISSION_BLUETOOTH = 1;
    RecyclerView recycler_accepted_orders;
    AcceptedAdapter acceptedAdapter;
    List<ReceivedItemPojo> receivedItemPojos;
    OrderListViewModel orderListViewModel;
    AcceptedAdapter.OnOrderClickListener onOrderClickListener;
    ImageView screenShot, printer;
    ScrollView scroller;
    Dialog dialogCongratulation;
    ShopLandingPageViewModel shopLandingPageViewModel;
    private ViewFullOrderResponse viewFullOrderResponseForPrint;
    EditText et_order, et_orderStatus, et_orderDate, et_ProductName, et_ProductPrice, et_ProductDiscountedPrice, et_qty, et_payableAmount, et_SellerName, et_SellerMobileNumber, et_SellerAddress, et_SellerIBAN, et_BuyerName, et_BuyerMobile, et_BuyerAddress, et_BuyerIBAN;
    public HorizontalDottedProgress horizontalProgressBar;
    private int totalSumofAllOrderAmount = 0;

    LoadingDialog loadingDialog;
    int count = 0;
    private TextView nrf;


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
        loadingDialog = new LoadingDialog(getActivity());

        shopLandingPageViewModel = new ViewModelProvider(this).get(ShopLandingPageViewModel.class);
        //this will update the order list all tabs status counts
        horizontalProgressBar = view.findViewById(R.id.horizontalProgressBar);
        nrf = view.findViewById(R.id.nrf);
        if (!ApplicationUtils.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            horizontalProgressBar.setVisibility(View.GONE);
            return;
        }

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


        orderListViewModel = new ViewModelProvider(this).get(OrderListViewModel.class);
        recycler_accepted_orders = view.findViewById(R.id.recycler_accepted_orders);
        initReceivedItems();


    }


    private void initReceivedItems() {

        OrderByStatus orderByStatus = new OrderByStatus();
        orderByStatus.setProfileId(Constant.PROFILE_ID);
        orderByStatus.setStatus("2"); //for received order list

        orderListViewModel.orderByStatus(orderByStatus, getActivity());
        orderListViewModel.getOrderByStatusResponse().observe(getActivity(), new Observer<GetOrderByStatusResponse>() {
            @Override
            public void onChanged(GetOrderByStatusResponse getOrderByStatusResponse) {
                if (getOrderByStatusResponse != null) {
                    acceptedAdapter = new AcceptedAdapter(getOrderByStatusResponse.getData().getRequestList(), getActivity(), getReference());
                    recycler_accepted_orders.setAdapter(acceptedAdapter);
                    count = acceptedAdapter.getItemCount();
                    if (getArguments() != null) {
                        if (acceptedAdapter != null) {
                            acceptedAdapter.getFilter().filter(getArguments().getString("query"));

                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    count = acceptedAdapter.getItemCount();
                                    //Toast.makeText(getActivity(), "" + count, Toast.LENGTH_SHORT).show();


                                    if (getArguments().getString("isComingFromSearch") != null) {
                                        if (getArguments().getString("isComingFromSearch").equals("Y")) {
                                            //its mean a click tap on received button and search is not involves
                                            if (count == 0) {
                                                nrf.setVisibility(View.VISIBLE);
                                            } else {
                                                nrf.setVisibility(View.GONE);
                                            }

                                        }
                                    }

                          /*          if (getArguments().getString("isComingFromTapping") != null) {
                                        if (getArguments().getString("isComingFromTapping").equals("Y")) {
                                            //its mean a click tap on received button and search is not involves
                                            if (count == 0) {
                                                nrf.setVisibility(View.VISIBLE);
                                            } else {
                                                nrf.setVisibility(View.GONE);
                                            }
                                        }
                                    }*/
                                }
                            }, 10);
                        } else {
                            Toast.makeText(getActivity(), "Accepted Adapter is null ...", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                horizontalProgressBar.clearAnimation();
                horizontalProgressBar.setVisibility(View.GONE);

                if (count == 0) {
                    nrf.setVisibility(View.VISIBLE);
                } else {
                    nrf.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void OnViewFullOrderListener(int orderId) {
        // EditText et_order, et_orderStatus, et_orderDate, et_ProductName, et_ProductPrice, et_ProductDiscountedPrice, et_qty, et_payableAmount, et_SellerName, et_SellerMobileNumber, et_SellerAddress, et_SellerIBAN, et_BuyerName, et_BuyerMobile, et_BuyerAddress, et_BuyerIBAN;
        //  Toast.makeText(getActivity(), "" + orderId, Toast.LENGTH_SHORT).show();
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
        et_SellerAddress = dialog.findViewById(R.id.et_SellerAddress);
        et_SellerIBAN = dialog.findViewById(R.id.et_SellerIBAN);
        et_BuyerName = dialog.findViewById(R.id.et_BuyerName);
        et_BuyerMobile = dialog.findViewById(R.id.et_BuyerMobile);
        et_BuyerAddress = dialog.findViewById(R.id.et_BuyerAddress);
        et_BuyerIBAN = dialog.findViewById(R.id.et_BuyerIBAN);
        printer = dialog.findViewById(R.id.printer);
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
        viewFullOrder.setOrderStatus("2");

        orderListViewModel.viewFullOrder(viewFullOrder, getActivity());
        orderListViewModel.getViewFullOrderResponse().observe(getActivity(), new Observer<ViewFullOrderResponse>() {
            @Override
            public void onChanged(ViewFullOrderResponse viewFullOrderResponse) {
                // Toast.makeText(getActivity(), "order : " + viewFullOrderResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();

                if (viewFullOrderResponse.getData().getRequestList() != null) {
                    viewFullOrderResponseForPrint = viewFullOrderResponse;

                    et_order.setText(viewFullOrderResponse.getData().getRequestList().getOrderNo());
                    et_orderStatus.setText("Accepted");
                    et_orderDate.setText(" " + viewFullOrderResponse.getData().getRequestList().getOrderDate());

                    productDetailLL.removeAllViews();
                    if (viewFullOrderResponse.getData().getRequestList().getProductsDetails() != null) {
                        for (int i = 0; i < viewFullOrderResponse.getData().getRequestList().getProductsDetails().size(); i++) {
                            //productDetailLL.addView();
                            View inflater = getLayoutInflater().inflate(R.layout.product_detail, null);
                            totalSumofAllOrderAmount += Integer.parseInt(viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getSubTotal());

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


                    et_SellerName.setText(viewFullOrderResponse.getData().getRequestList().getSellerDetails().get(0).getFirstName());
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

        printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(getActivity(), "Device Not Supported Bluetooth", Toast.LENGTH_SHORT).show();
                } else if (!mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(getActivity(), "Bluetooth not enabled...", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getActivity(), "Bluetooth enabled...", Toast.LENGTH_SHORT).show();

                    loadingDialog.showDialog();

                    new CountDownTimer(3000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            printReceipt(viewFullOrderResponseForPrint);
                        }
                    }.start();

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

    private void printReceipt(ViewFullOrderResponse viewFullOrderResponse) {


        try {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH}, PERMISSION_BLUETOOTH);
            } else {
                BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                    HashMap<String, ItemDetail> stringItemDetailHashMap = new HashMap<String, ItemDetail>();

                    for (int i = 0; i < viewFullOrderResponse.getData().getRequestList().getProductsDetails().size(); i++) {
                        ItemDetail itemDetail = new ItemDetail(viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getQuantity(), viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getSubTotal(), viewFullOrderResponse.getData().getRequestList().getProductsDetails().get(i).getTitle());
                        stringItemDetailHashMap.put(String.valueOf(i), itemDetail);
                    }

                    String str = viewFullOrderResponse.getData().getRequestList().getOrderNo();
                    String[] arrOfStr = str.split("-");

                    String date = parseDateToddMMyyyy(viewFullOrderResponse.getData().getRequestList().getOrderDate());

                    stringItemDetailHashMap.size();
                    int total = 0;

                    String text1 = "[C]<img width=700 height=700>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,
                            getActivity().getApplicationContext().getResources().getDrawableForDensity(R.drawable.favicon,
                                    DisplayMetrics.DENSITY_280)) + "</img>\n" +
                            "[C]Powered By Tello Talk\n\n" +
                            "[C]<b>" + TelloPreferenceManager.getInstance(getActivity()).getShopUri() + ".tellocast.com</b>\n" +
                            "[C]Tello : " + viewFullOrderResponse.getData().getRequestList().getSellerDetails().get(0).getMobile() + "\n" +
                            "[C]" + date + "\n" +
                            "[C]<b>Order# " + arrOfStr[0] + "</b>\n" +
                            //   "[C]" + viewFullOrderResponse.getData().getRequestList().getOrderDate() + "\n\n" +

                            //    "[L]<b> Seller Name </b>\n   " + viewFullOrderResponse.getData().getRequestList().getSellerDetails().get(0).getFirstName() + " " + viewFullOrderResponse.getData().getRequestList().getSellerDetails().get(0).getMiddleName() + "\n" +
                            //    "[L]<b> Seller Contact </b>\n   " + viewFullOrderResponse.getData().getRequestList().getSellerDetails().get(0).getMobile() + "\n" +

                            "[C]--------------------------------\n" +
                            "[L]Name: " + viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getFirstName() + " " + viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getMiddleName() + "\n" +
                            "[L]Tello: " + viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getMobile() + "\n" +
                            "[L]Address: " + viewFullOrderResponse.getData().getRequestList().getBuyerDetails().get(0).getCompleteAddress() + "\n" +
                            "[C]--------------------------------\n";

                    //Integer.parseInt(stringItemDetailHashMap.get(String.valueOf(i)).getNoOfUnits())
                    //stringItemDetailHashMap.get(String.valueOf(i)).getTotalAmount()
                    for (int i = 0; i < stringItemDetailHashMap.size(); i++) {
                        text1 += "[L]<b>" + stringItemDetailHashMap.get(String.valueOf(i)).getProductName() + "</b>\n" +
                                "[L]" + Integer.parseInt(stringItemDetailHashMap.get(String.valueOf(i)).getNoOfUnits()) + " Pcs[R] Rs. " + Integer.parseInt(stringItemDetailHashMap.get(String.valueOf(i)).getTotalAmount()) + "\n";

                        total += Integer.valueOf(stringItemDetailHashMap.get(String.valueOf(i)).getTotalAmount());
                    }

                    text1 += "[C]==============================\n" +
                            "[L]<b>TOTAL</b>[R]<b> Rs." + total + "</b>\n" +
                            "[C]==============================\n" +
                            "[L]\n" +
                            "[L]\n" +
                            "[L]\n";


                    if (connection.isConnected()) {
                        printer.printFormattedText(text1);
                        loadingDialog.dismissDialog();
                    } else {
                        Toast.makeText(getActivity(), "Device paired... but not in range...", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismissDialog();
                    }
                } else {
                    Toast.makeText(getActivity(), "No printer was connected! Try Again or Paired Printer", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }
            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
            loadingDialog.dismissDialog();
        }
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

        dialogCongratulation = new Dialog(getActivity());
        dialogCongratulation.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogCongratulation.setContentView(R.layout.dialog_order_status_confirmation);
        dialogCongratulation.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        dialogCongratulation.show();

        Button continue_status = dialogCongratulation.findViewById(R.id.continue_status);
        Button cancel_status = dialogCongratulation.findViewById(R.id.cancel_status);
        TextView dialogMessage = dialogCongratulation.findViewById(R.id.dialogMsg);
        EditText editText = dialogCongratulation.findViewById(R.id.reasonForCancellation);

        if (status == 3) {
            dialogMessage.setText("After clicking on continue button the order status will be changed to Dispatch...");
            editText.setVisibility(View.GONE);

        } else {
            dialogMessage.setText("After clicking on continue button the order status will be changed to Cancel...");
            editText.setVisibility(View.VISIBLE);

        }

        continue_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateOrderStatus updateOrderStatus = new UpdateOrderStatus();
                updateOrderStatus.setOrderId(String.valueOf(OrderID));
                updateOrderStatus.setProfileId(Constant.PROFILE_ID);
                updateOrderStatus.setStatus(String.valueOf(status));
                updateOrderStatus.setContent(TextUtils.isEmpty(editText.getText().toString()) ? "" : editText.getText().toString());

                LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.showDialog();

                orderListViewModel.updateOrderStatus(updateOrderStatus, getActivity());
                orderListViewModel.updateOrderStatusResponse().observe(getActivity(), new Observer<UpdateOrderStatusResponse>() {
                    @Override
                    public void onChanged(UpdateOrderStatusResponse updateOrderStatusResponse) {
                        if (updateOrderStatusResponse != null) {
                            // Toast.makeText(getActivity(), "Order Has been moved...", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismissDialog();
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

    public AcceptedAdapter.OnOrderClickListener getReference() {
        return this;
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "MM/dd/yyyy HH:mm:ss";
        String outputPattern = "MMM/dd/yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
