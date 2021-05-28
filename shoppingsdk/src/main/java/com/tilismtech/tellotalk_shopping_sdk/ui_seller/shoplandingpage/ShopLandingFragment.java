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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ProductListAdapter;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ViewPagerAdapter;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ChildCategory;
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
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ParentCategoryListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductForEditResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.SubCategoryBYParentCatIDResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateProductResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private Dialog dialog_edit_details;
    private LinearLayout outerRL;
    private Button addProduct_btn, uploadProduct, post_product_btn;
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
    private TextView tv_deleteProduct;
    private LoadingDialog loadingDialog;
    private EditText et_OriginalPrice, et_DiscountedPrice, et_SKU, et_Description, et_ProductTitle;
    private Spinner parentSpinner, childSpinner, parentSpinneredit, childSpinneredit;
    private String parentCategory = "1", childCategory = "1", productStatus = "N"; //by default
    private List<String> parentCategories, childCategories;
    private List<ChildCategory> childCategoryList;
    private Dialog dialog;
    private String lastProductId = "0";
    List<ProductListResponse.Request> productListAppend, dummy;


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
        // orderListtabbar = view.findViewById(R.id.orderListtabbar);
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.dismissDialog();
        productList = view.findViewById(R.id.productList);
        recycler_add_product = view.findViewById(R.id.recycler_add_product);
        shopLandingPageViewModel = new ViewModelProvider(this).get(ShopLandingPageViewModel.class);
        // shopLandingPageViewModel1 = new ViewModelProvider(this).get(ShopLandingPageViewModel.class);
        initRV(); // this recycler view set product list on screen
        uriList = new ArrayList<>();
        filePaths = new ArrayList<>();

        //this button show only first time when there is np product list
        addProduct_btn = view.findViewById(R.id.addProduct_btn);
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
                        //Toast.makeText(ShopLandingActivity.this, "clickedd...", Toast.LENGTH_SHORT).show();

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
                        if(productListResponse.getData().getRequestList() != null) {
                            productListAppend.addAll(productListResponse.getData().getRequestList());
                            productListAdapter.notifyDataSetChanged();
                            lastProductId = String.valueOf(productListResponse.getData().getRequestList().get(productListResponse.getData().getRequestList().size() - 1).getProductId());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {

            }
        });
    }

    private void initRV() {
        ProductList productList = new ProductList();
        productList.setProfileId(Constant.PROFILE_ID);
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.showDialog();
        shopLandingPageViewModel.productList(productList, lastProductId); //initially first set of product will be fecthed
        shopLandingPageViewModel.getProductList().observe(getActivity(), new Observer<ProductListResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                if (productListResponse != null) {
                    if (productListResponse.getData().getRequestList() != null) {
                        addProduct_btn.setVisibility(View.GONE);
                        productListAppend.addAll(productListResponse.getData().getRequestList());
                        //productListAppend.clear();
                       /* for (ProductListResponse.Request request : productListResponse.getData().getRequestList()) {
                            productListAppend.add(request);
                        }*/


                        productListAdapter = new ProductListAdapter(productListAppend, getActivity(), getReference());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                        recycler_add_product.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                        recycler_add_product.setAdapter(productListAdapter);
                        lastProductId = String.valueOf(productListResponse.getData().getRequestList().get(productListResponse.getData().getRequestList().size() - 1).getProductId());
                        loadingDialog.dismissDialog();
                    }
                    loadingDialog.dismissDialog();

                }
            }
        });


    }

    private void uploadParentCategory(Spinner parentSpinner, Spinner childSpinner) {
        shopLandingPageViewModel.parentCategories();
        shopLandingPageViewModel.getParentCategoryListResponseLiveData().observe(getActivity(), new Observer<ParentCategoryListResponse>() {
            @Override
            public void onChanged(ParentCategoryListResponse parentCategoryListResponse) {
                if (parentCategoryListResponse != null) {
                    //populate spinner here...
                    parentCategories = new ArrayList<>();

                    for (int i = 0; i < parentCategoryListResponse.getData().getRequestList().size(); i++) {
                        parentCategories.add(parentCategoryListResponse.getData().getRequestList().get(i).getTitle());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, parentCategories);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                    parentSpinner.setAdapter(spinnerArrayAdapter);
                    parentSpinneredit.setAdapter(spinnerArrayAdapter);

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

        shopLandingPageViewModel.childCategoryByParentId(subCategoryBYParentCatID);
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

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, childCategories);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                        if (isForEdit) { // load child categories when edit dialog open
                            childSpinneredit.setAdapter(spinnerArrayAdapter);
                        } else {   // load child category when add new product diualog is open
                            childSpinner.setAdapter(spinnerArrayAdapter);
                        }
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
    public void onOpenProductEditor(int productID) {

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_edit_product);

        outerRL = dialog.findViewById(R.id.outerRL);
        chooseMultipleProductsIV = dialog.findViewById(R.id.chooseMultipleProducts);
        LLimages_edit = dialog.findViewById(R.id.LLimages);
        productName = dialog.findViewById(R.id.productName);
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
        parentSpinneredit.setOnItemSelectedListener(onItemSelectedListenerEDit);
        childSpinneredit.setOnItemSelectedListener(onItemSelectedListenerEDit);

        uploadParentCategory(parentSpinneredit, childSpinneredit);

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
                dialog.dismiss();
            }
        });

        //this will trigger update api
        post_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (TextUtils.isEmpty(productName.getText().toString()) ||
                        TextUtils.isEmpty(productCategory.getText().toString()) ||
                        TextUtils.isEmpty(originalPrice.getText().toString()) ||
                        TextUtils.isEmpty(discountedPrice.getText().toString()) ||
                        TextUtils.isEmpty(skucodeoptional.getText().toString()) ||
                        TextUtils.isEmpty(product_description.getText().toString()) ||
                        filePaths.size() > 0
                ) {
                    Toast.makeText(getActivity(), "Some fields are missing...", Toast.LENGTH_SHORT).show();
                } else*/
                {
                    //every thing fine post edit api

                    if (Integer.parseInt(discountedPrice.getText().toString()) > Integer.parseInt(originalPrice.getText().toString())) {
                        Toast.makeText(getActivity(), "Discounted Price must be less than original price...", Toast.LENGTH_SHORT).show();
                        return;
                    }

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


                    // loadingDialog.showDialog();
                    shopLandingPageViewModel.updateproduct(updateProduct);
                    shopLandingPageViewModel.getProductUpdateResponse().observe(getActivity(), new Observer<UpdateProductResponse>() {
                        @Override
                        public void onChanged(UpdateProductResponse updateProductResponse) {
                            if (updateProductResponse != null) {
                                //Toast.makeText(getActivity(), "" + updateProductResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                filePaths.clear();

                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                navController.navigate(R.id.shopLandingFragment);
                            }
                        }
                    });


                }
            }
        });

        tv_deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(productID, dialog);
            }
        });


        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        wlp.gravity = Gravity.BOTTOM;
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        ProductForEdit productForEdit = new ProductForEdit();
        productForEdit.setProfileId(Constant.PROFILE_ID);
        productForEdit.setProductId(String.valueOf(productID));

        shopLandingPageViewModel.productForEdit(productForEdit);
        shopLandingPageViewModel.getProductForEdit().observe(getActivity(), new Observer<ProductForEditResponse>() {
            @Override
            public void onChanged(ProductForEditResponse productForEditResponse) {
                if (productForEditResponse != null) {

                    parentCategoryId = productForEditResponse.getData().getRequestList().getParentProductCategoryId();
                    childCategoryId = productForEditResponse.getData().getRequestList().getProductCategoryid();

                    productName.setText(productForEditResponse.getData().getRequestList().getTitle());
                    productCategory.setText(productForEditResponse.getData().getRequestList().getProductCategoryName());
                    originalPrice.setText(productForEditResponse.getData().getRequestList().getPrice());
                    discountedPrice.setText(productForEditResponse.getData().getRequestList().getDiscount());
                    skucodeoptional.setText(productForEditResponse.getData().getRequestList().getSku());
                    isActiveproduct.setChecked(productForEditResponse.getData().getRequestList().getProductStatus().equals("Y") ? true : false);
                    product_description.setText(productForEditResponse.getData().getRequestList().getSummary());

                    //when url provided this will work for sure...
                    /*if (productForEditResponse.getData().getRequestList().getProfilePic() != null) {
                        for (int i = 0; i < productForEditResponse.getData().getRequestList().getProfilePic().size(); i++) {
                            View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                            ImageView iv = inflater.findViewById(R.id.iv);
                            //imageUri = data.getClipData().getItemAt(i).getUri();
                            iv.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bbq));
                            LLimages_edit.addView(inflater);
                        }
                    }*/

                    //  product description key missing from getproductforedit api...
                    //  product_description.setText(productForEditResponse.getData().getRequestList().getprod());
                }
            }
        });


        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void deleteProduct(int productID, Dialog dialog) {
        DeleteProduct deleteProduct = new DeleteProduct();
        deleteProduct.setProductId(String.valueOf(productID));
        deleteProduct.setProfileId(Constant.PROFILE_ID);
        shopLandingPageViewModel.deleteProduct(deleteProduct);
        shopLandingPageViewModel.deleteProductResponse().observe(getActivity(), new Observer<DeleteProductResponse>() {
            @Override
            public void onChanged(DeleteProductResponse deleteProductResponse) {
                if (deleteProductResponse != null) {
                    if (deleteProductResponse.getStatus().equals("0")) {
                        Toast.makeText(getActivity(), "Product Has Been Deleted...", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        ((ShopLandingActivity) getActivity()).setTotalProductOnActionBar();
                        navController.navigate(R.id.shopLandingFragment);
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
        shopLandingPageViewModel.isProductActive(isProductActive);
        shopLandingPageViewModel.getProductActiveResponse().observe(getActivity(), new Observer<IsProductActiveResponse>() {
            @Override
            public void onChanged(IsProductActiveResponse isProductActiveResponse) {
                if (isProductActiveResponse != null) {
                    //Toast.makeText(getActivity(), " : " + isProductActiveResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onOpenProductDetailDialog(int productID) {
        TextView et_ProductName, et_ProductID, et_OriginalPrice, et_DiscountedPrice;
        androidx.viewpager.widget.ViewPager viewPager2;
        DotsIndicator dotsIndicator;
        // images array
        int[] images = {R.drawable.ic_bbq, R.drawable.ic_bbq, R.drawable.ic_bbq, R.drawable.ic_bbq,
                R.drawable.ic_bbq, R.drawable.ic_bbq, R.drawable.ic_bbq, R.drawable.ic_bbq};


        dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_product_detail_new);

        et_ProductName = dialog.findViewById(R.id.et_ProductName);
        et_ProductID = dialog.findViewById(R.id.et_ProductID);
        et_OriginalPrice = dialog.findViewById(R.id.et_OriginalPrice);
        et_DiscountedPrice = dialog.findViewById(R.id.et_DiscountedPrice);
        viewPager2 = dialog.findViewById(R.id.viewPager);


        // Creating Object of ViewPagerAdapter
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getActivity(), images);

        // Adding the Adapter to the ViewPager
        viewPager2.setAdapter(mViewPagerAdapter);
        dotsIndicator = dialog.findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(viewPager2);

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

        shopLandingPageViewModel.productForEdit(productForEdit);
        shopLandingPageViewModel.getProductForEdit().observe(getActivity(), new Observer<ProductForEditResponse>() {
            @Override
            public void onChanged(ProductForEditResponse productForEditResponse) {
                if (productForEditResponse != null) {
                    et_ProductName.setText(String.valueOf(productForEditResponse.getData().getRequestList().getTitle()));
                    et_ProductID.setText(String.valueOf(productForEditResponse.getData().getRequestList().getId()));
                    et_OriginalPrice.setText(String.valueOf(productForEditResponse.getData().getRequestList().getPrice()));
                    et_DiscountedPrice.setText(String.valueOf(productForEditResponse.getData().getRequestList().getDiscount()));
                    // Toast.makeText(getActivity(), productForEditResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
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
                uploadChildCategory(parentCategory, childSpinner, true);
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

}
