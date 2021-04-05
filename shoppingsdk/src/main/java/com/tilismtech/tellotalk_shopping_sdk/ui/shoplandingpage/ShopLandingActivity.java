package com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui.settingprofileediting.SettingProfileEditingActivity;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import java.security.cert.CertPathBuilderSpi;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.ArrayList;
import java.util.List;

public class ShopLandingActivity extends AppCompatActivity {

    private static final int ALLOW_MULTIPLE_IMAGES = 1;
    private NavHostFragment navHostFragment;
    private ImageView setting, addProduct, arrowback, chooseMultipleProductsIV;
    private ImageView iv_close, iv_back_addproduct;
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
    private String parentCategory, childCategory, productStatus = "N";
    private LinearLayout LLimages;
    private Switch isActiveproduct;

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
                //  uploadChildCategory();

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

                        if (TextUtils.isEmpty(et_OriginalPrice.getText().toString()) ||
                                TextUtils.isEmpty(et_DiscountedPrice.getText().toString()) ||
                                TextUtils.isEmpty(et_SKU.getText().toString()) ||
                                TextUtils.isEmpty(et_Description.getText().toString()) ||
                                TextUtils.isEmpty(et_ProductTitle.getText().toString())
                        ) {
                            Toast.makeText(ShopLandingActivity.this, "Some Fields are missing...", Toast.LENGTH_SHORT).show();
                        } else {
                            AddNewProduct addNewProduct = new AddNewProduct();
                            addNewProduct.setDiscount_Price(et_DiscountedPrice.getText().toString());
                            addNewProduct.setPrice(et_OriginalPrice.getText().toString());
                            addNewProduct.setProduct_Category_id("1"); //parentCategory
                            addNewProduct.setSub_Product_Category_id("1"); //childCategory
                            addNewProduct.setSku("12sku");
                            addNewProduct.setSummary(et_Description.getText().toString());
                            addNewProduct.setProfileId(Constant.PROFILE_ID);
                            addNewProduct.setProductStatus(productStatus); //work with toggle on and off
                            addNewProduct.setProduct_Pic(""); //here we send a picture path from device...
                            addNewProduct.setTitle(et_ProductTitle.getText().toString());

                            shopLandingPageViewModel.addNewProduct(addNewProduct);
                            shopLandingPageViewModel.getNewProduct().observe(ShopLandingActivity.this, new Observer<AddNewProductResponse>() {
                                @Override
                                public void onChanged(AddNewProductResponse addNewProductResponse) {
                                    if (addNewProductResponse != null) {
                                        Toast.makeText(ShopLandingActivity.this, " : " + addNewProductResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ShopLandingActivity.this, "Null...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                            dialogAddProduct.dismiss();
                            navController.navigate(R.id.shopLandingFragment);
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

        productList = findViewById(R.id.productList);
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
                simpleSearchView.setVisibility(View.INVISIBLE);

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

                addProduct.setVisibility(View.INVISIBLE);
                simpleSearchView.setVisibility(View.VISIBLE);

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

                addProduct.setVisibility(View.INVISIBLE);
                simpleSearchView.setVisibility(View.VISIBLE);

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
                tv_addproducts.setVisibility(View.VISIBLE);
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

    private void uploadParentCategory(Spinner parentSpinner, Spinner childSpinner) {


        shopLandingPageViewModel.parentCategories();
        shopLandingPageViewModel.getParentCategoryListResponseLiveData().observe(ShopLandingActivity.this, new Observer<ParentCategoryListResponse>() {
            @Override
            public void onChanged(ParentCategoryListResponse parentCategoryListResponse) {
                if (parentCategoryListResponse != null) {
                    //populate spinner here...
                    parentCategories = new ArrayList<>();

                    for (int i = 0; i < parentCategoryListResponse.getData().getRequestList().size(); i++) {
                        parentCategories.add(parentCategoryListResponse.getData().getRequestList().get(i).getColumn1());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ShopLandingActivity.this, R.layout.spinner_text, parentCategories);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                    parentSpinner.setAdapter(spinnerArrayAdapter);

                    //      uploadChildCategory("1",childSpinner);

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

                    for (int i = 0; i < subCategoryBYParentCatIDResponse.getData().getRequestList().size(); i++) {
                        parentCategories.add(subCategoryBYParentCatIDResponse.getData().getRequestList().get(i).getColumn1());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ShopLandingActivity.this, R.layout.spinner_text, childCategories);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                    childSpinner.setAdapter(spinnerArrayAdapter);

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
                parentCategory = parentSpinner.getItemAtPosition(position).toString();
            }

            if (parent.getId() == childSpinner.getId()) {
                childCategory = childSpinner.getItemAtPosition(position).toString();
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
                Uri imageUri;
                ImageView iv;
                int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                for (int i = 0; i < count; i++) {
                    View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                    iv = inflater.findViewById(R.id.iv);
                    imageUri = data.getClipData().getItemAt(i).getUri();
                    iv.setImageURI(imageUri);
                    LLimages.addView(inflater);
                }

                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        } else if (data.getData() != null) {
            String imagePath = data.getData().getPath();
            //do something with the image (save it to some directory or whatever you need to do with it here)
        }
    }
}
