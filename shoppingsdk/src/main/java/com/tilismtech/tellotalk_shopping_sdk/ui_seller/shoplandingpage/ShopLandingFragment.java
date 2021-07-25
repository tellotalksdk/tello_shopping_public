package com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoplandingpage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.WebViewActivity;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ProductListAdapter;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ViewPagerAdapter;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ChildCategory;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddNewProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteProductImage;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.IsProductActive;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.IsProductActiveResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductForEdit;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductList;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.SubCategoryBYParentCatID;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateProduct;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddNewProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteProductImageResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductForEditResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopNameAndImageResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient.getRetrofitClient;


public class ShopLandingFragment extends Fragment implements ProductListAdapter.OnProductEditorClickDialog {

    private static final int ALLOW_MULTIPLE_IMAGES = 1;
    private static final int ALLOW_MULTIPLE_IMAGES_EDIT = 2;
    private ProductListAdapter.OnProductEditorClickDialog onProductEditorClickDialog;
    private NavController navController;
    private ImageView setting, open_edit_details, iv_back_addproduct, chooseMultipleProductsIV;
    private Dialog dialog_edit_details, dialogCongratulation;
    private LinearLayout outerRL;
    public Button addProduct_btn, uploadProduct, post_product_btn;
    private Dialog dialogAddProduct;
    private LinearLayout productList;
    private RecyclerView recycler_add_product;
    private ProductListAdapter productListAdapter;
    private LinearLayout LLimages, LLimages_edit;
    private EditText productName, productCategory, originalPrice, discountedPrice, skucodeoptional, product_description;
    private ShopLandingPageViewModel shopLandingPageViewModel, shopLandingPageViewModel1;
    private Switch edit_switch;
    private List<Uri> uriList;
    private Switch isActiveproduct;
    private String parentCategoryId = "1", childCategoryId = "1";
    private Uri imageUri;
    private String filepath;
    private List<String> filePaths;
    private TextView tv_deleteProduct, et_Category, et_SubCategory, et_YoutubeLink, et_productDescription;
    private LoadingDialog loadingDialog;
    private EditText et_OriginalPrice, et_DiscountedPrice, et_SKU, et_Description, et_ProductTitle;
    private Spinner parentSpinner, childSpinner, parentSpinneredit, childSpinneredit;
    private String parentCategory = "1", childCategory = "1", productStatus = "N"; //by default
    private List<String> parentCategories, childCategories;
    private List<ChildCategory> childCategoryList;
    private Dialog dialog;
    private String lastProductId = "0", parentCat_maintain, childCat_maintain; //maintain word is used for maintaining parent and child last selection whenever edit dialog is open...
    private EditText et_VideoUrl;
    List<ProductListResponse.Request> productListAppend, dummy;
    List<String> imageIds;
    String video, document;
    ArrayAdapter<String> spinnerArrayAdapter;
    ArrayAdapter<String> spinnerArrayAdapter2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_landing_page, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        productListAppend = new ArrayList<>();
        dummy = new ArrayList<>();
        imageIds = new ArrayList<>();
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.dismissDialog();
        productList = view.findViewById(R.id.productList);
        recycler_add_product = view.findViewById(R.id.recycler_add_product);
        shopLandingPageViewModel = new ViewModelProvider(this).get(ShopLandingPageViewModel.class);
        addProduct_btn = view.findViewById(R.id.addProduct_btn);
        setShopNameAndImage();
        initRV(); // this recycler view set product list on screen
        uriList = new ArrayList<>();
        filePaths = new ArrayList<>();
        dialogCongratulation = new Dialog(getActivity());

        //this button show only first time when there is np product list
        addProduct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddProduct = new Dialog(getActivity());
                dialogAddProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogAddProduct.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogAddProduct.setContentView(R.layout.dialog_add_product);

                chooseMultipleProductsIV = dialogAddProduct.findViewById(R.id.chooseMultipleProductsIV);
                LLimages = dialogAddProduct.findViewById(R.id.LLimages);
                isActiveproduct = dialogAddProduct.findViewById(R.id.isActiveproduct);
                isActiveproduct.setOnCheckedChangeListener(onCheckedChangeListener);

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

                et_VideoUrl = dialogAddProduct.findViewById(R.id.et_VideoUrl);
                et_VideoUrl.setText("https://www.youtube.com/watch?v=xsU14eHgmBg&t=1s&ab_channel=Electrostore");


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
                            Toast.makeText(getActivity(), "Some Fields are missing...", Toast.LENGTH_SHORT).show();
                        } else {

                            if (Integer.parseInt(et_DiscountedPrice.getText().toString()) > Integer.parseInt(et_OriginalPrice.getText().toString())) {
                                Toast.makeText(getActivity(), "Discounted price must be less than original price...", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (Integer.parseInt(et_DiscountedPrice.getText().toString()) == Integer.parseInt(et_OriginalPrice.getText().toString())) {
                                Toast.makeText(getActivity(), "Discounted price can not be same as original price...", Toast.LENGTH_SHORT).show();
                                return;
                            }


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
                            addNewProduct.setVideoName(et_VideoUrl.getText().toString());
                            Linkify.addLinks(et_VideoUrl, Linkify.ALL);

                            LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                            loadingDialog.showDialog();
                            shopLandingPageViewModel.addNewProduct(addNewProduct, getActivity());
                            // shopLandingPageViewModel.getNewProduct().removeObservers(getActivity());
                            shopLandingPageViewModel.getNewProduct().observe(getActivity(), new Observer<AddNewProductResponse>() {
                                @Override
                                public void onChanged(AddNewProductResponse addNewProductResponse) {
                                    // shopLandingPageViewModel.getNewProduct().removeObservers(getActivity());
                                    if (addNewProductResponse != null) {
                                        //Toast.makeText(ShopLandingActivity.this, " : " + addNewProductResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        filePaths.clear();
                                        loadingDialog.dismissDialog();
                                        dialogAddProduct.dismiss();

                                        navController.navigate(R.id.action_shopLandingFragment_to_shopLandingFragment2);

                                    } else {
                                        loadingDialog.dismissDialog();
                                        Toast.makeText(getActivity(), "Some thing went wrong...", Toast.LENGTH_SHORT).show();
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
         /*       dialogAddProduct = new Dialog(getActivity());
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
                //    uploadChildCategory("1", childSpinner);


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
                                TextUtils.isEmpty(et_Description.getText().toString()) ||
                                TextUtils.isEmpty(et_ProductTitle.getText().toString()) ||
                                TextUtils.isEmpty(imageUri.toString())
                        ) {
                            Toast.makeText(getActivity(), "Some Fields are missing...", Toast.LENGTH_SHORT).show();
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

                            LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                            loadingDialog.showDialog();
                            shopLandingPageViewModel.addNewProduct(addNewProduct);
                            shopLandingPageViewModel.getNewProduct().observe(getActivity(), new Observer<AddNewProductResponse>() {
                                @Override
                                public void onChanged(AddNewProductResponse addNewProductResponse) {
                                    if (addNewProductResponse != null) {
                                        // Toast.makeText(getActivity(), " : " + addNewProductResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                        filePaths.clear();
                                        dialogAddProduct.dismiss();
                                        filePaths.clear();
                                        loadingDialog.dismissDialog();
                                        dialogAddProduct.dismiss();
                                        ((ShopLandingActivity) getActivity()).setTotalProductOnActionBar();
                                        navController.navigate(R.id.shopLandingFragment);
                                    } else {
                                        Toast.makeText(getActivity(), "Null...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                Window window = dialogAddProduct.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                window.setAttributes(wlp);
                dialogAddProduct.show();*/
            }
        });

        //append recycler view on scroll via pagination...
        recycler_add_product.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Toast.makeText(getActivity(), "end of recycler ...", Toast.LENGTH_SHORT).show();
                    //initRV();
                    updateRecyclerView();
                }
            }
        });

       /* Intent intent = getActivity().getIntent();
        boolean congrats = intent.getBooleanExtra("congrats_dialog_to_show", false);
      */

        if (TelloPreferenceManager.getInstance(getActivity()).getcongratsStatus() == false) {
            dialogCongratulation.dismiss();
        } else {

            dialogCongratulation.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialogCongratulation.setContentView(R.layout.dialog_congratulation);
            dialogCongratulation.show();

            ImageView iv_close = dialogCongratulation.findViewById(R.id.iv_close);
            Button getStarted_btn = dialogCongratulation.findViewById(R.id.getStarted_btn);

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

            TelloPreferenceManager.getInstance(getActivity()).savecongratsStatus(false);
            loadingDialog.dismissDialog();
        }

    }

    private void setShopNameAndImage() {
        shopLandingPageViewModel.shopImageAndName(getActivity());
        shopLandingPageViewModel.getShopNameAndImage().observe(getActivity(), new Observer<ShopNameAndImageResponse>() {
            @Override
            public void onChanged(ShopNameAndImageResponse shopNameAndImageResponse) {
                if (shopNameAndImageResponse != null) {
                    //  Toast.makeText(getActivity(), shopNameAndImageResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateRecyclerView() {
        ProductList productList = new ProductList();
        productList.setProfileId(Constant.PROFILE_ID);
        getRetrofitClient().getProductList("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), productList.getProfileId(), lastProductId).enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response != null) {
                    if (response.body() != null) {
                        ProductListResponse productListResponse = response.body();
                        addProduct_btn.setVisibility(View.GONE);
                        if (productListResponse.getData().getRequestList() != null) {
                            productListAppend.addAll(productListResponse.getData().getRequestList());
                            productListAdapter.notifyDataSetChanged();
                            lastProductId = String.valueOf(productListResponse.getData().getRequestList().get(productListResponse.getData().getRequestList().size() - 1).getProductId());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initRV() {
        ProductList productList = new ProductList();
        productList.setProfileId(Constant.PROFILE_ID);
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());

        shopLandingPageViewModel.productList(productList, lastProductId, getActivity()); //initially first set of product will be fecthed
        shopLandingPageViewModel.getProductList().observe(getViewLifecycleOwner(), new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                if (productListResponse != null) {
                    if (productListResponse.getData().getRequestList() != null) {

                        addProduct_btn.setVisibility(View.GONE);
                        productListAppend.addAll(productListResponse.getData().getRequestList());
                        productListAdapter = new ProductListAdapter(productListAppend, getActivity(), getReference());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                        recycler_add_product.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                        recycler_add_product.setAdapter(productListAdapter);
                        productListAdapter.notifyDataSetChanged();
                        lastProductId = String.valueOf(productListResponse.getData().getRequestList().get(productListResponse.getData().getRequestList().size() - 1).getProductId());
                        loadingDialog.dismissDialog();
                        shopLandingPageViewModel.getProductList().removeObservers(getActivity());

                    } else {


                           /* dialogCongratulation = new Dialog(getActivity());
                            dialogCongratulation.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialogCongratulation.setContentView(R.layout.dialog_congratulation);
                            dialogCongratulation.show();

                            ImageView iv_close = dialogCongratulation.findViewById(R.id.iv_close);
                            Button getStarted_btn = dialogCongratulation.findViewById(R.id.getStarted_btn);

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

                            loadingDialog.dismissDialog();*/
                        loadingDialog.dismissDialog();
                        addProduct_btn.setVisibility(View.VISIBLE);

                    }
                }
                loadingDialog.dismissDialog();
            }
        });


    }

    private void uploadParentCategory(Spinner parentSpinner, Spinner childSpinner) {
        shopLandingPageViewModel.parentCategories(getActivity());
        shopLandingPageViewModel.getParentCategoryListResponseLiveData().observe(getActivity(), new Observer<ParentCategoryListResponse>() {
            @Override
            public void onChanged(ParentCategoryListResponse parentCategoryListResponse) {
                if (parentCategoryListResponse != null) {
                    //populate spinner here...
                    parentCategories = new ArrayList<>();

                    for (int i = 0; i < parentCategoryListResponse.getData().getRequestList().size(); i++) {
                        parentCategories.add(parentCategoryListResponse.getData().getRequestList().get(i).getTitle());
                    }

                    spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, parentCategories);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                    parentSpinner.setAdapter(spinnerArrayAdapter);
//                    parentSpinneredit.setAdapter(spinnerArrayAdapter);

                    // Toast.makeText(ShopLandingActivity.this, "product is : " +  parentCategoryListResponse.getData().getRequestList().get(0).getColumn1(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadParentCategory(Spinner parentSpinner, Spinner childSpinner, String parentName, String childName) {
        shopLandingPageViewModel.parentCategories(getActivity());
        shopLandingPageViewModel.getParentCategoryListResponseLiveData().observe(getActivity(), new Observer<ParentCategoryListResponse>() {
            @Override
            public void onChanged(ParentCategoryListResponse parentCategoryListResponse) {
                if (parentCategoryListResponse != null) {
                    //populate spinner here...
                    parentCategories = new ArrayList<>();

                    for (int i = 0; i < parentCategoryListResponse.getData().getRequestList().size(); i++) {
                        parentCategories.add(parentCategoryListResponse.getData().getRequestList().get(i).getTitle());
                    }

                    spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, parentCategories);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                    parentSpinner.setAdapter(spinnerArrayAdapter);
//                    parentSpinneredit.setAdapter(spinnerArrayAdapter);

                    int parent_Spinner = spinnerArrayAdapter.getPosition(parentName);
                    parentSpinneredit.setSelection(parent_Spinner);

                         /*


                            int child_Spinner = spinnerArrayAdapter2.getPosition(childCat_maintain);
                            childSpinneredit.setSelection(child_Spinner);*/

                    // Toast.makeText(ShopLandingActivity.this, "product is : " +  parentCategoryListResponse.getData().getRequestList().get(0).getColumn1(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadChildCategory(String id, Spinner childSpinner, boolean isForEdit) {
        SubCategoryBYParentCatID subCategoryBYParentCatID = new SubCategoryBYParentCatID();
        subCategoryBYParentCatID.setParentCategoryId(id);

        shopLandingPageViewModel.childCategoryByParentId(subCategoryBYParentCatID, getActivity());
        shopLandingPageViewModel.getChildCategories().observe(this, new Observer<SubCategoryBYParentCatIDResponse>() {
            @Override
            public void onChanged(SubCategoryBYParentCatIDResponse subCategoryBYParentCatIDResponse) {
                if (subCategoryBYParentCatIDResponse != null) {
                    childCategories = new ArrayList<>();
                    childCategoryList = new ArrayList<>();

                    //  Toast.makeText(ShopLandingActivity.this, "status : " + subCategoryBYParentCatIDResponse.getData().getRequestList().size(), Toast.LENGTH_SHORT).show();
                    childCategoryList.clear();
                    if (subCategoryBYParentCatIDResponse.getData().getRequestList() != null && subCategoryBYParentCatIDResponse.getData().getRequestList().size() > 0) {

                        for (int i = 0; i < subCategoryBYParentCatIDResponse.getData().getRequestList().size(); i++) {
                            childCategoryList.add(new ChildCategory(subCategoryBYParentCatIDResponse.getData().getRequestList().get(i).getId(), subCategoryBYParentCatIDResponse.getData().getRequestList().get(i).getTitle()));
                            childCategories.add(subCategoryBYParentCatIDResponse.getData().getRequestList().get(i).getTitle());
                        }

                        spinnerArrayAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, childCategories);
                        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                        if (isForEdit) { // load child categories when edit dialog open
                            childSpinneredit.setAdapter(spinnerArrayAdapter2);
                        } else {   // load child category when add new product diualog is open
                            childSpinner.setAdapter(spinnerArrayAdapter2);
                        }
                    }
                }
            }
        });
    }

    private void uploadChildCategory(String id, Spinner childSpinnere, boolean isForEdit, String childName) {
        SubCategoryBYParentCatID subCategoryBYParentCatID = new SubCategoryBYParentCatID();
        subCategoryBYParentCatID.setParentCategoryId(id);

        shopLandingPageViewModel.childCategoryByParentId(subCategoryBYParentCatID, getActivity());
        shopLandingPageViewModel.getChildCategories().observe(getViewLifecycleOwner(), new Observer<SubCategoryBYParentCatIDResponse>() {
            @Override
            public void onChanged(SubCategoryBYParentCatIDResponse subCategoryBYParentCatIDResponse) {
                if (subCategoryBYParentCatIDResponse != null) {
                    childCategories = new ArrayList<>();
                    childCategoryList = new ArrayList<>();

                    //  Toast.makeText(ShopLandingActivity.this, "status : " + subCategoryBYParentCatIDResponse.getData().getRequestList().size(), Toast.LENGTH_SHORT).show();
                    childCategoryList.clear();
                    if (subCategoryBYParentCatIDResponse.getData().getRequestList() != null && subCategoryBYParentCatIDResponse.getData().getRequestList().size() > 0) {

                        for (int i = 0; i < subCategoryBYParentCatIDResponse.getData().getRequestList().size(); i++) {
                            childCategoryList.add(new ChildCategory(subCategoryBYParentCatIDResponse.getData().getRequestList().get(i).getId(), subCategoryBYParentCatIDResponse.getData().getRequestList().get(i).getTitle()));
                            childCategories.add(subCategoryBYParentCatIDResponse.getData().getRequestList().get(i).getTitle());
                        }

                        spinnerArrayAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, childCategories);
                        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                        childSpinneredit.setAdapter(spinnerArrayAdapter2);
                        int parent_Spinner = spinnerArrayAdapter.getPosition(childName);
                        childSpinneredit.setSelection(parent_Spinner);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //FOR ADD PRODUCT DIALOG this will run on a Add Product button when there is No product in shop
        if (requestCode == ALLOW_MULTIPLE_IMAGES && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                ImageView iv;
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

                // filepath = getPath(ShopLandingActivity.this, imageUri);
                // filepath = getFileNameByUri(ShopLandingActivity.this, imageUri);
                // filepath = getRealPathFromURI(ShopLandingActivity.this, imageUri);
                //do something with the image (save it to some directory or whatever you need to do with it here)
            } else if (data.getData() != null) {
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

        //FOR EDIT PRODUCT DIALOG

        if (requestCode == ALLOW_MULTIPLE_IMAGES_EDIT && resultCode == RESULT_OK) {
            //===
            if (data.getClipData() != null) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.image_item_for_multiple_images, null);
                        //  View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                        ImageView iv = view.findViewById(R.id.iv);
                        Uri selectedImage = data.getClipData().getItemAt(i).getUri();
                        Log.i("TAG", "onActivityResult: " + selectedImage.getPath());
                        filepath = getImagePath(selectedImage);
                        Log.i("TAG", "onActivityResult: " + filepath);
                        filePaths.add(filepath); //getting multiple image file path and save all selected image path in string array

                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                        iv.setImageBitmap(resized);
                        LLimages_edit.addView(view);
                    }
                } else {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                        ImageView iv = inflater.findViewById(R.id.iv);
                        imageUri = data.getClipData().getItemAt(i).getUri();
                        Log.i("TAG", "onActivityResult: " + imageUri.getPath());
                        filepath = getImagePath(imageUri);
                        Log.i("TAG", "onActivityResult: " + filepath);
                        filePaths.add(filepath); //getting multiple image file path and save all selected image path in string array
                        iv.setImageURI(imageUri);
                        LLimages_edit.addView(inflater);
                    }
                }


            } else if (data.getData() != null) {

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    Uri selectedImage = data.getData();
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.image_item_for_multiple_images, null);

                    // View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images,null);
                    // ImageView iv = inflater.findViewById(R.id.iv);
                    ImageView iv = view.findViewById(R.id.iv);
                    filepath = getImagePath(selectedImage);
                    filePaths.add(filepath);

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                    iv.setImageBitmap(resized);
                    LLimages_edit.addView(view);
                } else {
                    String imagePath = data.getData().getPath();
                    imageUri = data.getData();
                    View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                    ImageView iv = inflater.findViewById(R.id.iv);
                    filepath = getImagePath(imageUri);
                    filePaths.add(filepath);
                    iv.setImageURI(imageUri);
                    LLimages_edit.addView(inflater);
                }

            }
            //===

            //old
            /*      if (data.getClipData() != null) {

                ImageView iv;
                int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.

                for (int i = 0; i < count; i++) {
                    View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                    iv = inflater.findViewById(R.id.iv);
                    imageUri = data.getClipData().getItemAt(i).getUri();
                    Log.i("TAG", "onActivityResult: " + imageUri.getPath());
                    String filepath = getImagePath(imageUri);
                    Log.i("TAG", "onActivityResult: " + filepath);
                    filePaths.add(filepath); //getting multiple image file path and save all selected image path in string array
                    iv.setImageURI(imageUri);
                    LLimages_edit.addView(inflater);
                }

                //do something with the image (save it to some directory or whatever you need to do with it here)
            } else if (data.getData() != null) {
                String imagePath = data.getData().getPath();
                imageUri = data.getData();
                View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                ImageView iv = inflater.findViewById(R.id.iv);
                filepath = getImagePath(imageUri);
                filePaths.add(filepath);
                iv.setImageURI(imageUri);
                LLimages_edit.addView(inflater);
            }*/
        }


    }

    //rececler product view edit icons action
    @Override
    public void onOpenProductEditor(int productID, int adapterPosition) {
        //  Toast.makeText(getActivity(), "" + productID, Toast.LENGTH_SHORT).show();

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        dialog.setContentView(R.layout.dialog_edit_product);

        outerRL = dialog.findViewById(R.id.outerRL);
        chooseMultipleProductsIV = dialog.findViewById(R.id.chooseMultipleProducts);
        LLimages_edit = dialog.findViewById(R.id.LLimages);
        productName = dialog.findViewById(R.id.productName);
        et_VideoUrl = dialog.findViewById(R.id.et_VideoUrl);
        productCategory = dialog.findViewById(R.id.productCategory);
        originalPrice = dialog.findViewById(R.id.originalPrice);
        discountedPrice = dialog.findViewById(R.id.discountedPrice);
        skucodeoptional = dialog.findViewById(R.id.skucodeoptional);
        product_description = dialog.findViewById(R.id.product_description);
        post_product_btn = dialog.findViewById(R.id.post_product_btn);
        isActiveproduct = dialog.findViewById(R.id.edit_switch);
        tv_deleteProduct = dialog.findViewById(R.id.tv_deleteProduct);
        isActiveproduct.setOnCheckedChangeListener(onCheckedChangeListener);
        parentSpinneredit = dialog.findViewById(R.id.parentSpinner);
        childSpinneredit = dialog.findViewById(R.id.childSpinner);

        uploadParentCategory(parentSpinneredit, childSpinneredit);
        parentSpinneredit.setOnItemSelectedListener(onItemSelectedListenerEDit);
        childSpinneredit.setOnItemSelectedListener(onItemSelectedListenerEDit);


        chooseMultipleProductsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), ALLOW_MULTIPLE_IMAGES_EDIT);
            }
        });

        outerRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shopLandingPageViewModel.getProductForEdit().removeObservers(getActivity());
                dialog.dismiss();
            }
        });


        //this will trigger update api
        post_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditDialogFieldsValidation()) {
                    {
                        //every thing fine post edit api

                        if (!TextUtils.isEmpty(et_VideoUrl.getText().toString())) {
                            String path = et_VideoUrl.getText().toString();
                            String segments[] = path.split(".com/");

                            if (segments.length > 1) {
                                document = segments[1];
                            } else {
                                document = "";
                            }

                        }


                        if (!TextUtils.isEmpty(discountedPrice.getText().toString())) {
                            if (Integer.parseInt(discountedPrice.getText().toString()) > Integer.parseInt(originalPrice.getText().toString())) {
                                Toast.makeText(getActivity(), "Discounted Price must be less than original price...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }


                        if (!TextUtils.isEmpty(et_VideoUrl.getText().toString())) {
                            if (!URLUtil.isValidUrl(et_VideoUrl.getText().toString())) {
                                Toast.makeText(getActivity(), "URL is not valid", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }


                        if ((et_VideoUrl.getText().toString().contains("www.youtube.com") && !document.equals("")) || TextUtils.isEmpty(et_VideoUrl.getText().toString())) {
                            UpdateProduct updateProduct = new UpdateProduct();
                            updateProduct.setTitle(productName.getText().toString());
                            updateProduct.setDiscountPrice(discountedPrice.getText().toString());
                            updateProduct.setPrice(originalPrice.getText().toString());
                            updateProduct.setProductId(String.valueOf(productID));
                            updateProduct.setProfileId(Constant.PROFILE_ID);
                            updateProduct.setParentProductCategoryId(parentCategoryId);
                            updateProduct.setProductCategoryId(childCategoryId);
                            updateProduct.setProduct_Pic(filePaths);
                            updateProduct.setSku(skucodeoptional.getText().toString());
                            updateProduct.setSummary(product_description.getText().toString());
                            updateProduct.setProductStatus(productStatus);
                            updateProduct.setProfileId(Constant.PROFILE_ID);
                            updateProduct.setVideoLink(TextUtils.isEmpty(et_VideoUrl.getText().toString()) ? "" : et_VideoUrl.getText().toString());


                            // loadingDialog.showDialog();
                            shopLandingPageViewModel.updateproduct(updateProduct, getActivity());
                            shopLandingPageViewModel.getProductUpdateResponse().observe(getActivity(), new Observer<UpdateProductResponse>() {
                                @Override
                                public void onChanged(UpdateProductResponse updateProductResponse) {
                                    if (updateProductResponse != null) {
                                        //Toast.makeText(getActivity(), "" + updateProductResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                        filePaths.clear();

                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                        navController.navigate(R.id.action_shopLandingFragment_to_shopLandingFragment2);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Only Proper YouTube Link is allowed", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                }

            }

            private boolean checkEditDialogFieldsValidation() {

                if (TextUtils.isEmpty(originalPrice.getText().toString())) {
                    Toast.makeText(getActivity(), "Original Price Field is empty...", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (TextUtils.isEmpty(discountedPrice.getText().toString())) {
                    Toast.makeText(getActivity(), "Discounted Price Field is empty...", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (TextUtils.isEmpty(skucodeoptional.getText().toString())) {
                    Toast.makeText(getActivity(), "SKU Code Field is empty...", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (TextUtils.isEmpty(originalPrice.getText().toString())) {
                    Toast.makeText(getActivity(), "Original Price Field is empty...", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (TextUtils.isEmpty(product_description.getText().toString())) {
                    Toast.makeText(getActivity(), "Product Description is empty...", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });

        tv_deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(productID, dialog, adapterPosition);
            }
        });


        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        wlp.gravity = Gravity.BOTTOM;
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;f
        window.setAttributes(wlp);

        ProductForEdit productForEdit = new ProductForEdit();
        productForEdit.setProfileId(Constant.PROFILE_ID);
        productForEdit.setProductId(String.valueOf(productID));
       /* shopLandingPageViewModel.getProductForEdit().removeObservers(this);
        shopLandingPageViewModel.productForEdit(productForEdit);
        shopLandingPageViewModel.getProductForEdit().observe(this, new Observer<ProductForEditResponse>() {
            @Override
            public void onChanged(ProductForEditResponse productForEditResponse) {
                if (productForEditResponse != null) {
                    shopLandingPageViewModel.dPResponse().removeObservers(getActivity());
                    Log.i("TAG", "trigger :::");

                    parentCategoryId = productForEditResponse.getData().getRequestList().getParentProductCategoryId();
                    childCategoryId = productForEditResponse.getData().getRequestList().getProductCategoryid();

                    productName.setText(productForEditResponse.getData().getRequestList().getTitle());
                    productCategory.setText(productForEditResponse.getData().getRequestList().getProductCategoryName());
                    originalPrice.setText(productForEditResponse.getData().getRequestList().getPrice());
                    discountedPrice.setText(productForEditResponse.getData().getRequestList().getDiscount());
                    skucodeoptional.setText(productForEditResponse.getData().getRequestList().getSku());
                    isActiveproduct.setChecked(productForEditResponse.getData().getRequestList().getProductStatus().equals("Y") ? true : false);
                    product_description.setText(productForEditResponse.getData().getRequestList().getSummary());
                    imageIds.clear();
                    //when url provided this will work for sure...
                    if (productForEditResponse.getData().getRequestList().getProductImageDTO() != null) {
                        for (int i = 0; i < productForEditResponse.getData().getRequestList().getProductImageDTO().size(); i++) {
                            View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                            ImageView iv = inflater.findViewById(R.id.iv);
                            imageIds.add(productForEditResponse.getData().getRequestList().getProductImageDTO().get(i).getImageId());
                            Glide.with(getActivity()).load(productForEditResponse.getData().getRequestList().getProductImageDTO().get(i).getImageURL()).into(iv);
                            ImageView ic_delete = inflater.findViewById(R.id.ic_delete);
                            //this will delete image
                            ic_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "Deleted....." +imageIds.get(LLimages_edit.indexOfChild(inflater) - 1) , Toast.LENGTH_SHORT).show();
                                    DeleteProductImage deleteProductImage = new DeleteProductImage();
                                    deleteProductImage.setImageId(imageIds.get(LLimages_edit.indexOfChild(inflater) - 1).toString());
                                    deleteProductImage.setProductId(String.valueOf(productID));
                                    deleteProductImage.setProfileId(Constant.PROFILE_ID);

                                    shopLandingPageViewModel.deleteProduct(deleteProductImage);
                                    shopLandingPageViewModel.dPResponse().observe(getActivity(), new Observer<DeleteProductImageResponse>() {
                                        @Override
                                        public void onChanged(DeleteProductImageResponse responseBody) {
                                            if (responseBody != null) {
                                                Toast.makeText(getActivity(), "Image Removed Successfully...", Toast.LENGTH_SHORT).show();
                                                LLimages_edit.removeView(inflater);
                                            }
                                        }
                                    });
                                }
                            });

                            LLimages_edit.addView(inflater);
                        }
                    }
                }
            }
        });*/

        getRetrofitClient().getProductForEdit("Bearer " + TelloPreferenceManager.getInstance(TelloApplication.getInstance().getContext()).getAccessToken(), productForEdit.getProfileId(), productForEdit.getProductId()).enqueue(new Callback<ProductForEditResponse>() {
            @Override
            public void onResponse(Call<ProductForEditResponse> call, Response<ProductForEditResponse> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        ProductForEditResponse productForEditResponse = response.body();
                        if (productForEditResponse != null) {

                            parentCategoryId = productForEditResponse.getData().getRequestList().getParentProductCategoryId();
                            childCategoryId = productForEditResponse.getData().getRequestList().getProductCategoryid();
                            parentCat_maintain = productForEditResponse.getData().getRequestList().getParentCategoryName();
                            childCat_maintain = productForEditResponse.getData().getRequestList().getProductCategoryName();

                            Log.i("TAG", "onResponse: " + parentCat_maintain);
                            Log.i("TAG", "onResponse: " + childCat_maintain);

                            uploadParentCategory(parentSpinneredit, childSpinneredit, parentCat_maintain, childCat_maintain);
                            //uploadChildCategory(parentCategoryId, childSpinneredit, true, childCat_maintain);

                            productName.setText(productForEditResponse.getData().getRequestList().getTitle());
                            productCategory.setText(productForEditResponse.getData().getRequestList().getProductCategoryName());
                            originalPrice.setText(productForEditResponse.getData().getRequestList().getPrice());
                            discountedPrice.setText(productForEditResponse.getData().getRequestList().getDiscount());
                            skucodeoptional.setText(productForEditResponse.getData().getRequestList().getSku());
                            isActiveproduct.setChecked(productForEditResponse.getData().getRequestList().getProductStatus().equals("Y") ? true : false);
                            product_description.setText(productForEditResponse.getData().getRequestList().getSummary());
                            // et_VideoUrl.setText(productForEditResponse.getData().getRequestList().getVideoLink());
                            et_VideoUrl.setText(productForEditResponse.getData().getRequestList().getVideoLink());
                            imageIds.clear();
                            //when url provided this will work for sure...
                            if (productForEditResponse.getData().getRequestList().getProductImageDTO() != null) {
                                for (int i = 0; i < productForEditResponse.getData().getRequestList().getProductImageDTO().size(); i++) {
                                    View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                                    ImageView iv = inflater.findViewById(R.id.iv);
                                    imageIds.add(productForEditResponse.getData().getRequestList().getProductImageDTO().get(i).getImageId());
                                    Glide.with(getActivity()).load(productForEditResponse.getData().getRequestList().getProductImageDTO().get(i).getImageURL()).into(iv);
                                    ImageView ic_delete = inflater.findViewById(R.id.ic_delete);
                                    //this will delete image
                                    ic_delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //Toast.makeText(getActivity(), "Deleted....." +imageIds.get(LLimages_edit.indexOfChild(inflater) - 1) , Toast.LENGTH_SHORT).show();
                                            loadingDialog.showDialog();
                                            DeleteProductImage deleteProductImage = new DeleteProductImage();
                                            deleteProductImage.setImageId(imageIds.get(LLimages_edit.indexOfChild(inflater) - 1).toString());
                                            deleteProductImage.setProductId(String.valueOf(productID));
                                            deleteProductImage.setProfileId(Constant.PROFILE_ID);

                                            shopLandingPageViewModel.deleteProduct(deleteProductImage, getActivity());
                                            shopLandingPageViewModel.dPResponse().removeObservers(getViewLifecycleOwner());
                                            shopLandingPageViewModel.dPResponse().observe(getViewLifecycleOwner(), new Observer<DeleteProductImageResponse>() {
                                                @Override
                                                public void onChanged(DeleteProductImageResponse responseBody) {
                                                    if (responseBody != null) {
                                                        Toast.makeText(getActivity(), "Image Removed Successfully...", Toast.LENGTH_SHORT).show();
                                                        LLimages_edit.removeView(inflater);
                                                        loadingDialog.dismissDialog();
                                                    } else {
                                                        Toast.makeText(getActivity(), "Image Not Removed.Please Try Again", Toast.LENGTH_SHORT).show();
                                                        loadingDialog.dismissDialog();
                                                    }
                                                }
                                            });
                                        }
                                    });

                                    LLimages_edit.addView(inflater);
                                }
                            }
                        }
                    }
                } else { //incase response is null

                }
            }

            @Override
            public void onFailure(Call<ProductForEditResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });


        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void deleteProduct(int productID, Dialog dialog, int adapterPosition) {

        loadingDialog.showDialog();
        DeleteProduct deleteProduct = new DeleteProduct();
        deleteProduct.setProductId(String.valueOf(productID));
        deleteProduct.setProfileId(Constant.PROFILE_ID);
        shopLandingPageViewModel.deleteProduct(deleteProduct, getActivity());
        shopLandingPageViewModel.deleteProductResponse().observe(getActivity(), new Observer<DeleteProductResponse>() {
            @Override
            public void onChanged(DeleteProductResponse deleteProductResponse) {
                if (deleteProductResponse != null) {
                    if (deleteProductResponse.getStatus().equals("0")) {
                        ((ShopLandingActivity) getActivity()).setTotalProductOnActionBar();
                        dialog.dismiss();
                        loadingDialog.dismissDialog();
                        navController.navigate(R.id.action_shopLandingFragment_to_shopLandingFragment2);
                    }
                }
            }
        });
    }

    @Override
    public void isActiveproduct(int position, boolean isActive) {
        //  Toast.makeText(getActivity(), " Position : " + position + " Product Status is : " + isActive, Toast.LENGTH_SHORT).show();
        IsProductActive isProductActive = new IsProductActive();
        isProductActive.setProductId(String.valueOf(position));
        isProductActive.setProductStatus(isActive ? "Y" : "N");
        isProductActive.setProfileId(Constant.PROFILE_ID);
        shopLandingPageViewModel.isProductActive(isProductActive, getActivity());
        shopLandingPageViewModel.getProductActiveResponse().observe(getActivity(), new Observer<IsProductActiveResponse>() {
            @Override
            public void onChanged(IsProductActiveResponse isProductActiveResponse) {
                if (isProductActiveResponse != null) {
                  /*  if (isProductActiveResponse.getStatus().equals("0")) {
                        if (productListAppend.get(position).getProductStatus().equals("N")) {
                            productListAppend.get(position).setProductStatus("Y");
                        } else {
                            productListAppend.get(position).setProductStatus("N");
                        }
                    }*/
                }
            }
        });
    }

    @Override
    public void onOpenProductDetailDialog(int productID) {
        TextView et_ProductName, et_ProductID, et_OriginalPrice, et_DiscountedPrice;
        androidx.viewpager.widget.ViewPager viewPager2;
        DotsIndicator dotsIndicator;

        dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_product_detail_new);
        Button button = dialog.findViewById(R.id.open);


        et_ProductName = dialog.findViewById(R.id.et_ProductName);
        et_ProductID = dialog.findViewById(R.id.et_ProductID);
        et_OriginalPrice = dialog.findViewById(R.id.et_OriginalPrice);
        et_DiscountedPrice = dialog.findViewById(R.id.et_DiscountedPrice);
        et_Category = dialog.findViewById(R.id.et_Category);
        et_SubCategory = dialog.findViewById(R.id.et_SubCategory);
        et_YoutubeLink = dialog.findViewById(R.id.et_YoutubeLink);
        et_productDescription = dialog.findViewById(R.id.et_productDescription);
        viewPager2 = dialog.findViewById(R.id.viewPager);

        dotsIndicator = dialog.findViewById(R.id.dots_indicator);

        ImageView iv_back = dialog.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        wlp.gravity = Gravity.BOTTOM;
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        ProductForEdit productForEdit = new ProductForEdit();
        productForEdit.setProfileId(Constant.PROFILE_ID);
        productForEdit.setProductId(String.valueOf(productID));

        shopLandingPageViewModel.productForEdit(productForEdit, getActivity());
        shopLandingPageViewModel.getProductForEdit().observe(getActivity(), new Observer<ProductForEditResponse>() {
            @Override
            public void onChanged(ProductForEditResponse productForEditResponse) {
                if (productForEditResponse != null) {
                    List<String> images = new ArrayList<>();

                    if (productForEditResponse.getData().getRequestList().getProductImageDTO() != null) {
                        for (int i = 0; i < productForEditResponse.getData().getRequestList().getProductImageDTO().size(); i++) {
                            images.add(productForEditResponse.getData().getRequestList().getProductImageDTO().get(i).getImageURL());
                        }
                    }

                    // Creating Object of ViewPagerAdapter
                    ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getActivity(), images);

                    // Adding the Adapter to the ViewPager
                    viewPager2.setAdapter(mViewPagerAdapter);
                    dotsIndicator.setViewPager(viewPager2);

                    et_ProductName.setText(String.valueOf(productForEditResponse.getData().getRequestList().getTitle()));
                    et_ProductID.setText(String.valueOf(productForEditResponse.getData().getRequestList().getId()));
                    et_OriginalPrice.setText(String.valueOf(productForEditResponse.getData().getRequestList().getPrice()));
                    et_DiscountedPrice.setText(String.valueOf(productForEditResponse.getData().getRequestList().getDiscount()));
                    et_Category.setText(productForEditResponse.getData().getRequestList().getParentCategoryName());
                    et_SubCategory.setText(productForEditResponse.getData().getRequestList().getProductCategoryName());
                    et_YoutubeLink.setText(productForEditResponse.getData().getRequestList().getVideoLink());
                    et_productDescription.setText(productForEditResponse.getData().getRequestList().getSummary());
                    video = productForEditResponse.getData().getRequestList().getVideoLink();

                    if (video.contains("www.youtube.com")) {
                        if (!URLUtil.isValidUrl(video)) {
                            button.setVisibility(View.GONE);
                        } else {
                            button.setVisibility(View.VISIBLE);
                        }
                    } else {
                        button.setVisibility(View.GONE);
                    }


                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), WebViewActivity.class);
                i.putExtra("videoUrl", video);
                startActivity(i);
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onShareProductLink(String productLink) {
        //Toast.makeText(getActivity(), "" + productLink, Toast.LENGTH_SHORT).show();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, productLink);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public ProductListAdapter.OnProductEditorClickDialog getReference() {
        return this;
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == isActiveproduct.getId()) {
                isActiveproduct.setChecked(isChecked);
                productStatus = isActiveproduct.isChecked() ? "Y" : "N";
                //  Toast.makeText(getActivity(), "" + isChecked, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public String getImagePath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    //listener for selecting parent and child category items...
    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == parentSpinner.getId()) {
                parentCategory = String.valueOf(parentSpinner.getSelectedItemPosition() + 1);
                uploadChildCategory(parentCategory, childSpinner, false);
            }

            if (parent.getId() == childSpinner.getId()) {
                // childCategory = String.valueOf(childSpinner.getSelectedItemPosition() + 1);
                childCategory = String.valueOf(childCategoryList.get(childSpinner.getSelectedItemPosition()).getSubCategoryNumber());
                //Toast.makeText(getActivity(), "" + childCategory, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    AdapterView.OnItemSelectedListener onItemSelectedListenerEDit = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == parentSpinneredit.getId()) {
                parentCategoryId = String.valueOf(parentSpinneredit.getSelectedItemPosition() + 1);
                uploadChildCategory(parentCategoryId, childSpinneredit, true);
            }

            if (parent.getId() == childSpinneredit.getId()) {
                // childCategory = String.valueOf(childSpinner.getSelectedItemPosition() + 1);
                childCategoryId = String.valueOf(childCategoryList.get(childSpinneredit.getSelectedItemPosition()).getSubCategoryNumber());
                //Toast.makeText(getActivity(), "" + childCategory, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /* Returns true if url is valid */
    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        return URLUtil.isValidUrl(url);
    }

}

