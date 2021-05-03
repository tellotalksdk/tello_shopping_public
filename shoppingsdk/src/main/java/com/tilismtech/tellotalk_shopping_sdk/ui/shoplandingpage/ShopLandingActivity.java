package com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ProductListAdapter;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductList;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderStatusCountResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui.settingprofileediting.SettingProfileEditingActivity;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;

import java.io.IOException;
import java.security.cert.CertPathBuilderSpi;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.ArrayList;
import java.util.List;


public class ShopLandingActivity extends AppCompatActivity {

    private static final int ALLOW_MULTIPLE_IMAGES = 1;
    private NavHostFragment navHostFragment;
    private ImageView setting, addProduct, arrowback, chooseMultipleProductsIV;
    private ImageView iv_close, iv_back_addproduct, menuDots;
    private Button getStarted_btn, uploadProduct;
    private Dialog dialogCongratulation, dialogAddProduct;
    private TextView productList, orderList, chat;
    private LinearLayout Lineartabbar;
    private HorizontalScrollView orderListtabbar;
    private RelativeLayout received, accepted, dispatched, delivered, paid, cancel, all;
    private TextView deliveryStatus, number, deliveryStatus1, number1, deliveryStatus2, number2, deliveryStatus3, number3;
    private TextView deliveryStatus4, number4, deliveryStatus5, number5, deliveryStatus6, number6;
    private SearchView simpleSearchView;
    private CurrentTab currentTab; //bydefault
    private ShopLandingPageViewModel shopLandingPageViewModel;
    private Spinner parentSpinner, childSpinner;
    private List<String> parentCategories, childCategories;
    private EditText et_OriginalPrice, et_DiscountedPrice, et_SKU, et_Description, et_ProductTitle;
    private String parentCategory = "1", childCategory = "1", productStatus = "N"; //by default
    private LinearLayout LLimages, LL1;
    private Switch isActiveproduct;
    private String filepath;
    private Uri imageUri;
    private List<String> filePaths;

    //these fields hide when onsearch is pressed
    ImageView profileImage;
    TextView shopName, totalProducts, tv_addproducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_landing);
        shopLandingPageViewModel = new ViewModelProvider(this).get(ShopLandingPageViewModel.class);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        productList = findViewById(R.id.productList);
        filePaths = new ArrayList<>();

        currentTab = CurrentTab.RECEIVED;


        simpleSearchView = findViewById(R.id.simpleSearchView);
        Lineartabbar = findViewById(R.id.tabbar);
        orderListtabbar = findViewById(R.id.orderListtabbar);
        orderListtabbar.setVisibility(View.GONE);

        deliveryStatus = findViewById(R.id.deliveryStatus);
        deliveryStatus1 = findViewById(R.id.deliveryStatus1);
        deliveryStatus2 = findViewById(R.id.deliveryStatus2);
        deliveryStatus3 = findViewById(R.id.deliveryStatus3);
        deliveryStatus4 = findViewById(R.id.deliveryStatus4);
        deliveryStatus5 = findViewById(R.id.deliveryStatus5);
        deliveryStatus6 = findViewById(R.id.deliveryStatus6);


        number = findViewById(R.id.number);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        number4 = findViewById(R.id.number4);
        number5 = findViewById(R.id.number5);
        number6 = findViewById(R.id.number6);
        LL1 = findViewById(R.id.linearTopsearch);
        menuDots = findViewById(R.id.menuDots);

        shopLandingPageViewModel.allStatusCount();
        shopLandingPageViewModel.getAllStatusCount().observe(this, new Observer<GetOrderStatusCountResponse>() {
            @Override
            public void onChanged(GetOrderStatusCountResponse getOrderStatusCountResponse) {
                if (getOrderStatusCountResponse != null) {
                    //Toast.makeText(ShopLandingActivity.this, ":" + getOrderStatusCountResponse.getData().getRequestList().get(0).getRecieved(), Toast.LENGTH_SHORT).show();
                    number.setText(String.valueOf(getOrderStatusCountResponse.getData().getRequestList().get(0).getRecieved()));
                    number1.setText(String.valueOf(getOrderStatusCountResponse.getData().getRequestList().get(0).getAccept()));
                    number2.setText(String.valueOf(getOrderStatusCountResponse.getData().getRequestList().get(0).getDispatch()));
                    number3.setText(String.valueOf(getOrderStatusCountResponse.getData().getRequestList().get(0).getDelieverd()));
                    number4.setText(String.valueOf(getOrderStatusCountResponse.getData().getRequestList().get(0).getPaid()));
                    number5.setText(String.valueOf(getOrderStatusCountResponse.getData().getRequestList().get(0).getCancel()));
                    number6.setText(String.valueOf(getOrderStatusCountResponse.getData().getRequestList().get(0).getAll()));
                }
            }
        });


        profileImage = findViewById(R.id.profileImage);
        shopName = findViewById(R.id.shopName);
        totalProducts = findViewById(R.id.totalProducts);
        tv_addproducts = findViewById(R.id.tv_addproducts);

        dialogCongratulation = new Dialog(ShopLandingActivity.this);
        dialogCongratulation.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogCongratulation.setContentView(R.layout.dialog_congratulation);
        dialogCongratulation.show();

        iv_close = dialogCongratulation.findViewById(R.id.iv_close);
        getStarted_btn = dialogCongratulation.findViewById(R.id.getStarted_btn);

        ProductList productList1 = new ProductList();
        productList1.setProfileId(Constant.PROFILE_ID);
        hidecongratsdialog();

        setTotalProductOnActionBar();

        /* shopLandingPageViewModel.productList(productList1);
        shopLandingPageViewModel.getProductList().observe(ShopLandingActivity.this, new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                if (productListResponse != null) {
                    totalProducts.setText(productListResponse.getData().getRequestList().size() + " Product");
                }
            }
        });*/

        menuDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ShopLandingActivity.this, menuDots);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(ShopLandingActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCongratulation.dismiss();
            }
        });

        getStarted_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCongratulation.dismiss();
            }
        });

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopLandingActivity.this, SettingProfileEditingActivity.class));
            }
        });

        addProduct = findViewById(R.id.addProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddProduct = new Dialog(ShopLandingActivity.this);
                dialogAddProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogAddProduct.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogAddProduct.setContentView(R.layout.dialog_add_product);

                chooseMultipleProductsIV = dialogAddProduct.findViewById(R.id.chooseMultipleProductsIV);
                LLimages = dialogAddProduct.findViewById(R.id.LLimages);
                isActiveproduct = dialogAddProduct.findViewById(R.id.isActiveproduct);


                chooseMultipleProductsIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ALLOW_MULTIPLE_IMAGES);
                    }
                });

                et_OriginalPrice = dialogAddProduct.findViewById(R.id.et_OriginalPrice);
                et_DiscountedPrice = dialogAddProduct.findViewById(R.id.et_DiscountedPrice);
                et_SKU = dialogAddProduct.findViewById(R.id.et_SKU);
                et_Description = dialogAddProduct.findViewById(R.id.et_Description);
                et_ProductTitle = dialogAddProduct.findViewById(R.id.et_ProductTitle);

                parentSpinner = dialogAddProduct.findViewById(R.id.parentSpinner);
                childSpinner = dialogAddProduct.findViewById(R.id.childSpinner);
                parentSpinner.setOnItemSelectedListener(onItemSelectedListener);
                childSpinner.setOnItemSelectedListener(onItemSelectedListener);

                uploadParentCategory(parentSpinner, childSpinner);

                iv_back_addproduct = dialogAddProduct.findViewById(R.id.iv_back);
                iv_back_addproduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAddProduct.dismiss();
                    }
                });

                uploadProduct = dialogAddProduct.findViewById(R.id.uploadProduct);
                uploadProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(ShopLandingActivity.this, "clickedd...", Toast.LENGTH_SHORT).show();

                        if (TextUtils.isEmpty(et_OriginalPrice.getText().toString()) ||
                                TextUtils.isEmpty(et_DiscountedPrice.getText().toString()) ||
                                TextUtils.isEmpty(et_Description.getText().toString()) ||
                                TextUtils.isEmpty(et_ProductTitle.getText().toString()) ||
                                TextUtils.isEmpty(et_SKU.getText().toString())
                        ) {
                            Toast.makeText(ShopLandingActivity.this, "Some Fields are missing...", Toast.LENGTH_SHORT).show();
                        } else {
                            AddNewProduct addNewProduct = new AddNewProduct();
                            addNewProduct.setDiscount_Price(et_DiscountedPrice.getText().toString());
                            addNewProduct.setPrice(et_OriginalPrice.getText().toString());
                            addNewProduct.setProduct_Category_id(parentCategory); //parentCategory
                            addNewProduct.setSub_Product_Category_id(childCategory); //childCategory
                            addNewProduct.setSku(et_SKU.getText().toString());
                            addNewProduct.setSummary(et_Description.getText().toString());
                            addNewProduct.setProfileId(Constant.PROFILE_ID);
                            addNewProduct.setProductStatus(productStatus); //work with toggle on and off
                            addNewProduct.setProduct_Pic(filePaths); //here we send a picture path from device...
                            addNewProduct.setTitle(et_ProductTitle.getText().toString());

                            LoadingDialog loadingDialog = new LoadingDialog(ShopLandingActivity.this);
                            loadingDialog.showDialog();
                            shopLandingPageViewModel.addNewProduct(addNewProduct);
                            shopLandingPageViewModel.getNewProduct().removeObservers(ShopLandingActivity.this);
                            shopLandingPageViewModel.getNewProduct().observe(ShopLandingActivity.this, new Observer<AddNewProductResponse>() {
                                @Override
                                public void onChanged(AddNewProductResponse addNewProductResponse) {
                                    shopLandingPageViewModel.getNewProduct().removeObservers(ShopLandingActivity.this);
                                    if (addNewProductResponse != null) {
                                        //Toast.makeText(ShopLandingActivity.this, " : " + addNewProductResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                        filePaths.clear();


                                        new CountDownTimer(5000, 1000) {
                                            public void onTick(long millisUntilFinished) {
                                            }

                                            public void onFinish() {
                                                try {
                                                    loadingDialog.dismissDialog();
                                                    dialogAddProduct.dismiss();
                                                    setTotalProductOnActionBar();
                                                    navController.navigate(R.id.shopLandingFragment);
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                }
                                            }
                                        }.start();

                                    } else {
                                        loadingDialog.dismissDialog();
                                        Toast.makeText(ShopLandingActivity.this, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.dismissDialog();
                                }
                            });

                            // navController.navigate(R.id.shopLandingFragment);
                        }
                    }
                });

                Window window = dialogAddProduct.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                window.setAttributes(wlp);
                dialogAddProduct.show();
            }
        });


        productList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListtabbar.setVisibility(View.GONE);

                productList.setTextColor(Color.WHITE);
                productList.setBackground(getResources().getDrawable(R.drawable.bg_text_left_rounded));

                orderList.setTextColor(Color.BLACK);
                orderList.setBackgroundColor(Color.TRANSPARENT);

                chat.setTextColor(Color.BLACK);
                chat.setBackgroundColor(Color.TRANSPARENT);

                Lineartabbar.setBackground(getResources().getDrawable(R.drawable.bg_tab));

               /* addProduct.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_circle_outline));
                setting.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_settings));
*/

                addProduct.setVisibility(View.VISIBLE);
                setting.setVisibility(View.VISIBLE);
                LL1.setVisibility(View.GONE);
                tv_addproducts.setVisibility(View.VISIBLE);

                navController.navigate(R.id.shopLandingFragment);
            }
        });

        orderList = findViewById(R.id.orderList);
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListtabbar.setVisibility(View.VISIBLE);

                productList.setTextColor(Color.BLACK);
                productList.setBackgroundColor(Color.TRANSPARENT);

                orderList.setTextColor(Color.WHITE);
                orderList.setBackground(getResources().getDrawable(R.drawable.bg_text_center_grey));

                chat.setTextColor(Color.BLACK);
                chat.setBackgroundColor(Color.TRANSPARENT);

                Lineartabbar.setBackground(getResources().getDrawable(R.drawable.bg_tab));

                //addProduct.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
                //setting.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu));

                addProduct.setVisibility(View.GONE);
                setting.setVisibility(View.GONE);
                LL1.setVisibility(View.VISIBLE);
                tv_addproducts.setVisibility(View.GONE);

                navController.navigate(R.id.receivedFragment);
            }
        });

        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListtabbar.setVisibility(View.GONE);

                productList.setTextColor(Color.BLACK);
                productList.setBackgroundColor(Color.TRANSPARENT);

                orderList.setTextColor(Color.BLACK);
                orderList.setBackgroundColor(Color.TRANSPARENT);

                chat.setTextColor(Color.WHITE);
                chat.setBackground(getResources().getDrawable(R.drawable.bg_text_right_rounded));

                Lineartabbar.setBackground(getResources().getDrawable(R.drawable.bg_tab));

             /*   addProduct.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
                setting.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu));
*/

                addProduct.setVisibility(View.GONE);
                setting.setVisibility(View.GONE);
                LL1.setVisibility(View.VISIBLE);
                tv_addproducts.setVisibility(View.GONE);

                navController.navigate(R.id.chat);
            }
        });

        arrowback = findViewById(R.id.arrowback);
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //received , accepted, dispatched, delivered, paid, cancel, all;

        received = findViewById(R.id.received);
        received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(ShopLandingActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                navController.navigate(R.id.receivedFragment);

                received.setBackground(getResources().getDrawable(R.drawable.order_list_tabs));
                accepted.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                dispatched.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                delivered.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                paid.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                cancel.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                all.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));

                deliveryStatus.setTextColor(Color.WHITE);
                deliveryStatus1.setTextColor(Color.BLACK);
                deliveryStatus2.setTextColor(Color.BLACK);
                deliveryStatus3.setTextColor(Color.BLACK);
                deliveryStatus4.setTextColor(Color.BLACK);
                deliveryStatus5.setTextColor(Color.BLACK);
                deliveryStatus6.setTextColor(Color.BLACK);

                number.setTextColor(Color.WHITE);
                number1.setTextColor(Color.BLACK);
                number2.setTextColor(Color.BLACK);
                number3.setTextColor(Color.BLACK);
                number4.setTextColor(Color.BLACK);
                number5.setTextColor(Color.BLACK);
                number6.setTextColor(Color.BLACK);

                currentTab = CurrentTab.RECEIVED;
            }
        });

        accepted = findViewById(R.id.accepted);
        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.acceptedFragment, null, new NavOptions.Builder()
                        .setPopUpTo(R.id.received, true)
                        .build());

                received.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                accepted.setBackground(getResources().getDrawable(R.drawable.order_list_tabs));
                dispatched.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                delivered.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                paid.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                cancel.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                all.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));

                deliveryStatus.setTextColor(Color.BLACK);
                deliveryStatus1.setTextColor(Color.WHITE);
                deliveryStatus2.setTextColor(Color.BLACK);
                deliveryStatus3.setTextColor(Color.BLACK);
                deliveryStatus4.setTextColor(Color.BLACK);
                deliveryStatus5.setTextColor(Color.BLACK);
                deliveryStatus6.setTextColor(Color.BLACK);

                number.setTextColor(Color.BLACK);
                number1.setTextColor(Color.WHITE);
                number2.setTextColor(Color.BLACK);
                number3.setTextColor(Color.BLACK);
                number4.setTextColor(Color.BLACK);
                number5.setTextColor(Color.BLACK);
                number6.setTextColor(Color.BLACK);

                currentTab = CurrentTab.ACCEPTED;
                //  navController.navigate(R.id.acceptedFragment);
            }
        });

        dispatched = findViewById(R.id.dispatched);
        dispatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.dispatchedFragment);

                received.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                accepted.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                dispatched.setBackground(getResources().getDrawable(R.drawable.order_list_tabs));
                delivered.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                paid.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                cancel.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                all.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));

                deliveryStatus.setTextColor(Color.BLACK);
                deliveryStatus1.setTextColor(Color.BLACK);
                deliveryStatus2.setTextColor(Color.WHITE);
                deliveryStatus3.setTextColor(Color.BLACK);
                deliveryStatus4.setTextColor(Color.BLACK);
                deliveryStatus5.setTextColor(Color.BLACK);
                deliveryStatus6.setTextColor(Color.BLACK);

                number.setTextColor(Color.BLACK);
                number1.setTextColor(Color.BLACK);
                number2.setTextColor(Color.WHITE);
                number3.setTextColor(Color.BLACK);
                number4.setTextColor(Color.BLACK);
                number5.setTextColor(Color.BLACK);
                number6.setTextColor(Color.BLACK);

                currentTab = CurrentTab.DISPATCHED;

            }
        });

        delivered = findViewById(R.id.delivered);
        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.deliveredFragment);

                received.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                accepted.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                dispatched.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                delivered.setBackground(getResources().getDrawable(R.drawable.order_list_tabs));
                paid.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                cancel.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                all.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));

                deliveryStatus.setTextColor(Color.BLACK);
                deliveryStatus1.setTextColor(Color.BLACK);
                deliveryStatus2.setTextColor(Color.BLACK);
                deliveryStatus3.setTextColor(Color.WHITE);
                deliveryStatus4.setTextColor(Color.BLACK);
                deliveryStatus5.setTextColor(Color.BLACK);
                deliveryStatus6.setTextColor(Color.BLACK);

                number.setTextColor(Color.BLACK);
                number1.setTextColor(Color.BLACK);
                number2.setTextColor(Color.BLACK);
                number3.setTextColor(Color.WHITE);
                number4.setTextColor(Color.BLACK);
                number5.setTextColor(Color.BLACK);
                number6.setTextColor(Color.BLACK);

                currentTab = CurrentTab.DELIVERED;

            }
        });

        paid = findViewById(R.id.paid);
        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.paidFragment);

                received.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                accepted.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                dispatched.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                delivered.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                paid.setBackground(getResources().getDrawable(R.drawable.order_list_tabs));
                cancel.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                all.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));

                deliveryStatus.setTextColor(Color.BLACK);
                deliveryStatus1.setTextColor(Color.BLACK);
                deliveryStatus2.setTextColor(Color.BLACK);
                deliveryStatus3.setTextColor(Color.BLACK);
                deliveryStatus4.setTextColor(Color.WHITE);
                deliveryStatus5.setTextColor(Color.BLACK);
                deliveryStatus6.setTextColor(Color.BLACK);

                number.setTextColor(Color.BLACK);
                number1.setTextColor(Color.BLACK);
                number2.setTextColor(Color.BLACK);
                number3.setTextColor(Color.BLACK);
                number4.setTextColor(Color.WHITE);
                number5.setTextColor(Color.BLACK);
                number6.setTextColor(Color.BLACK);

                currentTab = CurrentTab.PAID;
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.cancelledFragment);

                received.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                accepted.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                dispatched.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                delivered.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                paid.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                cancel.setBackground(getResources().getDrawable(R.drawable.order_list_tabs));
                all.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));

                deliveryStatus.setTextColor(Color.BLACK);
                deliveryStatus1.setTextColor(Color.BLACK);
                deliveryStatus2.setTextColor(Color.BLACK);
                deliveryStatus3.setTextColor(Color.BLACK);
                deliveryStatus4.setTextColor(Color.BLACK);
                deliveryStatus5.setTextColor(Color.WHITE);
                deliveryStatus6.setTextColor(Color.BLACK);

                number.setTextColor(Color.BLACK);
                number1.setTextColor(Color.BLACK);
                number2.setTextColor(Color.BLACK);
                number3.setTextColor(Color.BLACK);
                number4.setTextColor(Color.BLACK);
                number5.setTextColor(Color.WHITE);
                number6.setTextColor(Color.BLACK);

                currentTab = CurrentTab.CANCEL;

            }
        });

        all = findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.allFragment);

                received.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                accepted.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                dispatched.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                delivered.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                paid.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                cancel.setBackground(getResources().getDrawable(R.drawable.order_list_tabs_unselected));
                all.setBackground(getResources().getDrawable(R.drawable.order_list_tabs));

                deliveryStatus.setTextColor(Color.BLACK);
                deliveryStatus1.setTextColor(Color.BLACK);
                deliveryStatus2.setTextColor(Color.BLACK);
                deliveryStatus3.setTextColor(Color.BLACK);
                deliveryStatus4.setTextColor(Color.BLACK);
                deliveryStatus5.setTextColor(Color.BLACK);
                deliveryStatus6.setTextColor(Color.WHITE);

                number.setTextColor(Color.BLACK);
                number1.setTextColor(Color.BLACK);
                number2.setTextColor(Color.BLACK);
                number3.setTextColor(Color.BLACK);
                number4.setTextColor(Color.BLACK);
                number5.setTextColor(Color.BLACK);
                number6.setTextColor(Color.WHITE);

                currentTab = CurrentTab.ALL;
            }
        });

        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                profileImage.setVisibility(View.GONE);
                shopName.setVisibility(View.GONE);
                totalProducts.setVisibility(View.GONE);
                tv_addproducts.setVisibility(View.GONE);

                switch (currentTab) {
                    case RECEIVED:
                        Bundle bundle = new Bundle();
                        bundle.putString("query", query);
                        navController.navigate(R.id.receivedFragment, bundle);
                        break;
                    case ACCEPTED:
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("query", query);
                        navController.navigate(R.id.acceptedFragment, bundle1);
                        break;
                    case DISPATCHED:
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("query", query);
                        navController.navigate(R.id.dispatchedFragment, bundle2);
                        break;
                    case DELIVERED:
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("query", query);
                        navController.navigate(R.id.deliveredFragment, bundle3);
                        break;
                    case PAID:
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("query", query);
                        navController.navigate(R.id.paidFragment, bundle4);
                        break;
                    case CANCEL:
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("query", query);
                        navController.navigate(R.id.cancelledFragment, bundle5);
                        break;
                    case ALL:
                        Bundle bundle6 = new Bundle();
                        bundle6.putString("query", query);
                        navController.navigate(R.id.allFragment, bundle6);
                        break;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        simpleSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                profileImage.setVisibility(View.VISIBLE);
                shopName.setVisibility(View.VISIBLE);
                totalProducts.setVisibility(View.VISIBLE);
                //tv_addproducts.setVisibility(View.VISIBLE);
                return false;
            }
        });

        simpleSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileImage.setVisibility(View.GONE);
                shopName.setVisibility(View.GONE);
                totalProducts.setVisibility(View.GONE);
                tv_addproducts.setVisibility(View.GONE);
            }
        });

    }

    public void hidecongratsdialog() {
        dialogCongratulation.dismiss();
    }

    public void setTotalProductOnActionBar() {
        ProductList productList1 = new ProductList();
        productList1.setProfileId(TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getProfileId());
        shopLandingPageViewModel.productList(productList1);
        shopLandingPageViewModel.getProductList().observe(ShopLandingActivity.this, new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                if (productListResponse != null) {
                    if (productListResponse.getData().getRequestList() != null) {
                        totalProducts.setText(productListResponse.getData().getRequestList().size() + " Products");
                    } else {
                        totalProducts.setText("0" + " Product");
                    }
                }
            }
        });

    }

    private void uploadParentCategory(Spinner parentSpinner, Spinner childSpinner) {
        shopLandingPageViewModel.parentCategories();
        shopLandingPageViewModel.getParentCategoryListResponseLiveData().observe(ShopLandingActivity.this, new Observer<ParentCategoryListResponse>() {
            @Override
            public void onChanged(ParentCategoryListResponse parentCategoryListResponse) {
                if (parentCategoryListResponse != null) {
                    //populate spinner here...
                    parentCategories = new ArrayList<>();

                    for (int i = 0; i < parentCategoryListResponse.getData().getRequestList().size(); i++) {
                        parentCategories.add(parentCategoryListResponse.getData().getRequestList().get(i).getTitle());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ShopLandingActivity.this, R.layout.spinner_text, parentCategories);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                    parentSpinner.setAdapter(spinnerArrayAdapter);

                    // Toast.makeText(ShopLandingActivity.this, "product is : " +  parentCategoryListResponse.getData().getRequestList().get(0).getColumn1(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShopLandingActivity.this, "Null", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadChildCategory(String id, Spinner childSpinner) {
        SubCategoryBYParentCatID subCategoryBYParentCatID = new SubCategoryBYParentCatID();
        subCategoryBYParentCatID.setParentCategoryId(id);

        shopLandingPageViewModel.childCategoryByParentId(subCategoryBYParentCatID);
        shopLandingPageViewModel.getChildCategories().observe(this, new Observer<SubCategoryBYParentCatIDResponse>() {
            @Override
            public void onChanged(SubCategoryBYParentCatIDResponse subCategoryBYParentCatIDResponse) {
                if (subCategoryBYParentCatIDResponse != null) {
                    childCategories = new ArrayList<>();

                    //  Toast.makeText(ShopLandingActivity.this, "status : " + subCategoryBYParentCatIDResponse.getData().getRequestList().size(), Toast.LENGTH_SHORT).show();

                    if (subCategoryBYParentCatIDResponse.getData().getRequestList() != null && subCategoryBYParentCatIDResponse.getData().getRequestList().size() > 0) {

                        for (int i = 0; i < subCategoryBYParentCatIDResponse.getData().getRequestList().size(); i++) {
                            childCategories.add(subCategoryBYParentCatIDResponse.getData().getRequestList().get(i).getTitle());
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ShopLandingActivity.this, R.layout.spinner_text, childCategories);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                        childSpinner.setAdapter(spinnerArrayAdapter);

                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    enum CurrentTab {
        RECEIVED,
        ACCEPTED,
        DISPATCHED,
        DELIVERED,
        PAID,
        CANCEL,
        ALL
    }

    //listener for selecting parent and child category items...
    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == parentSpinner.getId()) {
                parentCategory = String.valueOf(parentSpinner.getSelectedItemPosition() + 1);
                uploadChildCategory(parentCategory, childSpinner);
            }

            if (parent.getId() == childSpinner.getId()) {
                childCategory = String.valueOf(childSpinner.getSelectedItemPosition() + 1);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    //for switch button on timings dialog...
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == isActiveproduct.getId()) {
                isActiveproduct.setChecked(isChecked);
                productStatus = isActiveproduct.isChecked() ? "Y" : "N";
                Toast.makeText(ShopLandingActivity.this, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

        }
    };

    //here we set multiple images from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ALLOW_MULTIPLE_IMAGES && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                ImageView iv;
              /*  int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                for (int i = 0; i < count; i++) {
                    View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                    iv = inflater.findViewById(R.id.iv);
                    imageUri = data.getClipData().getItemAt(i).getUri();
                    Log.i("TAG", "onActivityResult: " + imageUri.getPath());
                    filepath = getImagePath(imageUri);
                    Log.i("TAG", "onActivityResult: " + filepath);
                    filePaths.add(filepath); //getting multiple image file path and save all selected image path in string array
                    iv.setImageURI(imageUri);
                    LLimages.addView(inflater);
                }*/

                // filepath = getPath(ShopLandingActivity.this, imageUri);
                // filepath = getFileNameByUri(ShopLandingActivity.this, imageUri);
                // filepath = getRealPathFromURI(ShopLandingActivity.this, imageUri);
                //do something with the image (save it to some directory or whatever you need to do with it here)

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.image_item_for_multiple_images, null);
                        //  View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                        iv = view.findViewById(R.id.iv);
                        Uri selectedImage = data.getClipData().getItemAt(i).getUri();
                        Log.i("TAG", "onActivityResult: " + selectedImage.getPath());
                        filepath = getImagePath(selectedImage);
                        Log.i("TAG", "onActivityResult: " + filepath);
                        filePaths.add(filepath); //getting multiple image file path and save all selected image path in string array

                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                        iv.setImageBitmap(resized);
                        LLimages.addView(view);
                    }
                } else {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                        iv = inflater.findViewById(R.id.iv);
                        imageUri = data.getClipData().getItemAt(i).getUri();
                        Log.i("TAG", "onActivityResult: " + imageUri.getPath());
                        filepath = getImagePath(imageUri);
                        Log.i("TAG", "onActivityResult: " + filepath);
                        filePaths.add(filepath); //getting multiple image file path and save all selected image path in string array
                        iv.setImageURI(imageUri);
                        LLimages.addView(inflater);
                    }
                }


            } else if (data.getData() != null) {
                //  String imagePath = data.getData().getPath();
                // imageUri = data.getData();
                // View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                // ImageView iv = inflater.findViewById(R.id.iv);
                // filepath = getImagePath(imageUri);
                // filePaths.add(filepath);
                // iv.setImageURI(imageUri);
                // LLimages.addView(inflater);

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    Uri selectedImage = data.getData();
                    LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.image_item_for_multiple_images, null);

                    // View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images,null);
                    // ImageView iv = inflater.findViewById(R.id.iv);
                    ImageView iv = view.findViewById(R.id.iv);
                    filepath = getImagePath(selectedImage);
                    filePaths.add(filepath);

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                    iv.setImageBitmap(resized);
                    LLimages.addView(view);
                } else {
                    String imagePath = data.getData().getPath();
                    imageUri = data.getData();
                    View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                    ImageView iv = inflater.findViewById(R.id.iv);
                    filepath = getImagePath(imageUri);
                    filePaths.add(filepath);
                    iv.setImageURI(imageUri);
                    LLimages.addView(inflater);
                }

            }
        }
    }

    public String getImagePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    //This method return file path when we choose image from gallery
    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    public static String getFileNameByUri(Context context, Uri uri) {
        String fileName = "unknown";//default fileName
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                filePathUri = Uri.parse(cursor.getString(column_index));
                fileName = filePathUri.getLastPathSegment().toString();
            }
        } else if (uri.getScheme().compareTo("file") == 0) {
            fileName = filePathUri.getLastPathSegment().toString();
        } else {
            fileName = fileName + "_" + filePathUri.getLastPathSegment();
        }
        return fileName;
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void setOrderStatus(List<GetOrderStatusCountResponse.Request> requestList) {
        // Toast.makeText(this, "" + requestList.get(0).getRecieved() , Toast.LENGTH_SHORT).show();
        number.setText(String.valueOf(requestList.get(0).getRecieved()));
        number1.setText(String.valueOf(requestList.get(0).getAccept()));
        number2.setText(String.valueOf(requestList.get(0).getDispatch()));
        number3.setText(String.valueOf(requestList.get(0).getDelieverd()));
        number4.setText(String.valueOf(requestList.get(0).getPaid()));
        number5.setText(String.valueOf(requestList.get(0).getCancel()));
        number6.setText(String.valueOf(requestList.get(0).getAll()));
    }

}
