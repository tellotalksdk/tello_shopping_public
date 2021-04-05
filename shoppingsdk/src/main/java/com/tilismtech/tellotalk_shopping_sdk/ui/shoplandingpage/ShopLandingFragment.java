package com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ProductListAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ProductListpojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductForEdit;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ProductList;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductForEditResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductListResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


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
    private HorizontalScrollView orderListtabbar;
    private LinearLayout productList;
    private RecyclerView recycler_add_product;
    private ProductListAdapter productListAdapter;
    private ArrayList<ProductListpojo> productListpojos;
    private LinearLayout LLimages, LLimages_edit;
    private EditText productName, productCategory, originalPrice, discountedPrice, skucodeoptional, product_description;
    private ShopLandingPageViewModel shopLandingPageViewModel;


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
        // orderListtabbar = view.findViewById(R.id.orderListtabbar);
        productList = view.findViewById(R.id.productList);
        recycler_add_product = view.findViewById(R.id.recycler_add_product);
        shopLandingPageViewModel = new ViewModelProvider(this).get(ShopLandingPageViewModel.class);
        initRV();

        //this button show only first time when there is np product list
        addProduct_btn = view.findViewById(R.id.addProduct_btn);
        addProduct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddProduct = new Dialog(getActivity());
                dialogAddProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogAddProduct.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogAddProduct.setContentView(R.layout.dialog_add_product);

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
                        dialogAddProduct.dismiss();
                        //startActivity(new Intent(ShopLandingActivity.this,ShopLandingActivity.class));
                        navController.navigate(R.id.shopLandingFragment);
                    }
                });

                Window window = dialogAddProduct.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                window.setAttributes(wlp);
                dialogAddProduct.show();
            }
        });

        open_edit_details = view.findViewById(R.id.open_edit_details);
        open_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_edit_product);
                outerRL = dialog.findViewById(R.id.outerRL);
                ImageView choose = dialog.findViewById(R.id.chooseMultipleProducts);
                LLimages = dialog.findViewById(R.id.LLimages);

                LLimages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "clickedd", Toast.LENGTH_SHORT).show();
                        /*Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ALLOW_MULTIPLE_IMAGES);*/
                    }
                });


                outerRL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
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
        });
    }

    private void initRV() {
        initDummyData();
        productListAdapter = new ProductListAdapter(productListpojos, getActivity(), getReference());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        recycler_add_product.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        recycler_add_product.setAdapter(productListAdapter);

        //we populate recycler view here
       /* ProductList productList = new ProductList();
        productList.setProfileId(Constant.PROFILE_ID);
        shopLandingPageViewModel.productList(productList);
        shopLandingPageViewModel.getProductList().observe(getActivity(), new Observer<ProductListResponse>() {
            @Override
            public void onChanged(ProductListResponse productListResponse) {
                if(productListResponse != null){
                    productListResponse.getData().getRequestList();
                    productListAdapter = new ProductListAdapter(  productListResponse.getData().getRequestList(), getActivity(), getReference());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                    recycler_add_product.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                    recycler_add_product.setAdapter(productListAdapter);

                }
            }
        });*/
    }

    private void initDummyData() {
        productListpojos = new ArrayList<>();
        productListpojos.add(new ProductListpojo("Samsung Y10", "RS 10000", "RS 8500", "Mobile", true, R.drawable.ic_bbq));
        productListpojos.add(new ProductListpojo("Samsung Y10", "RS 10000", "RS 8500", "Mobile", true, R.drawable.ic_bbq));
        productListpojos.add(new ProductListpojo("Samsung Y10", "RS 10000", "RS 8500", "Mobile", true, R.drawable.ic_bbq));
        productListpojos.add(new ProductListpojo("Samsung Y10", "RS 10000", "RS 8500", "Mobile", true, R.drawable.ic_bbq));
        productListpojos.add(new ProductListpojo("Samsung Y10", "RS 10000", "RS 8500", "Mobile", true, R.drawable.ic_bbq));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //FOR ADD PRODUCT DIALOG
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

        //FOR EDIT PRODUCT DIALOG

        if (requestCode == ALLOW_MULTIPLE_IMAGES_EDIT && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                Uri imageUri;
                ImageView iv;
                int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                for (int i = 0; i < count; i++) {
                    View inflater = getLayoutInflater().inflate(R.layout.image_item_for_multiple_images, null);
                    iv = inflater.findViewById(R.id.iv);
                    imageUri = data.getClipData().getItemAt(i).getUri();
                    iv.setImageURI(imageUri);
                    LLimages_edit.addView(inflater);
                }

                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        } else if (data.getData() != null) {
            String imagePath = data.getData().getPath();
            //do something with the image (save it to some directory or whatever you need to do with it here)
        }


    }

    //rececler product view edit icons action
    @Override
    public void onOpenProductEditor(int position) {

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

        post_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(productName.getText().toString()) ||
                        TextUtils.isEmpty(productCategory.getText().toString()) ||
                        TextUtils.isEmpty(originalPrice.getText().toString()) ||
                        TextUtils.isEmpty(discountedPrice.getText().toString()) ||
                        TextUtils.isEmpty(skucodeoptional.getText().toString()) ||
                        TextUtils.isEmpty(product_description.getText().toString())
                ) {
                    Toast.makeText(getActivity(), "Some fields are missing...", Toast.LENGTH_SHORT).show();
                } else {
                    //every thing fine post edit api
                    ProductForEdit productForEdit = new ProductForEdit();
                    productForEdit.setProfileId(Constant.PROFILE_ID);
                    productForEdit.setProductId("1"); //product Id comes from product list api

                    //same error Get must not have a body....
                    // shopLandingPageViewModel.productForEdit(productForEdit);
                    shopLandingPageViewModel.getProductForEdit().observe(getActivity(), new Observer<ProductForEditResponse>() {
                        @Override
                        public void onChanged(ProductForEditResponse productForEditResponse) {
                            if (productForEditResponse != null) {
                                Toast.makeText(getActivity(), "" + productForEditResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();

                                productName.setText(productForEditResponse.getData().getRequestList().getTitle());
                                productCategory.setText(productForEditResponse.getData().getRequestList().getProductCategoryName());
                                originalPrice.setText(productForEditResponse.getData().getRequestList().getPrice());
                                discountedPrice.setText(productForEditResponse.getData().getRequestList().getDiscount());
                                skucodeoptional.setText(productForEditResponse.getData().getRequestList().getSku());
                                //product description key missing from getproductforedit api...
                                //  product_description.setText(productForEditResponse.getData().getRequestList().getprod());

                            }
                        }
                    });


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

    public ProductListAdapter.OnProductEditorClickDialog getReference() {
        return this;
    }


}

