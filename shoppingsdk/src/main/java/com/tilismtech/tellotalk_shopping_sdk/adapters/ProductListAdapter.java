package com.tilismtech.tellotalk_shopping_sdk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ProductListpojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ProductListResponse;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductItemVH> {


    List<ProductListpojo> productListpojos;
    Context myCtx;
    OnProductEditorClickDialog onProductEditorClickDialog;
    LinearLayout outerRL;
    Button closeEditbtn;
    //
    List<ProductListResponse.Request> productList;


    public ProductListAdapter(List<ProductListpojo> productListpojos, Context myCtx, OnProductEditorClickDialog onProductEditorClickDialog) {
        this.productListpojos = productListpojos;
        this.myCtx = myCtx;
        this.onProductEditorClickDialog = onProductEditorClickDialog;
    }

    public ProductListAdapter(List<ProductListResponse.Request> productList, FragmentActivity activity, OnProductEditorClickDialog reference) {
        this.productList = productList;
        this.myCtx = myCtx;
        this.onProductEditorClickDialog = onProductEditorClickDialog;
    }


    @NonNull
    @Override
    public ProductItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_items, parent, false);
        return new ProductItemVH(v, this.onProductEditorClickDialog);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemVH holder, int position) {
        ProductListpojo productListpojo = productListpojos.get(position);

        holder.discountedprice.setText(productListpojo.getDiscountedPrice());
        holder.originalprice.setText(productListpojo.getOriginalPrice());
        holder.productcategory.setText(productListpojo.getProductCategory());
        holder.productTitle.setText(productListpojo.getProductTitle());
        holder.isActive.setChecked(productListpojo.isActive());
        holder.productImage.setImageDrawable(myCtx.getDrawable(productListpojo.getImage()));

       /* holder.open_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(myCtx);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_edit_product);
                outerRL = dialog.findViewById(R.id.outerRL);
                closeEditbtn = dialog.findViewById(R.id.post_product_btn);

                outerRL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                closeEditbtn.setOnClickListener(new View.OnClickListener() {
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
        });*/

       /* holder.viewProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(myCtx);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_product_detail_new);

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

                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return productListpojos.size();
    }

    public class ProductItemVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productTitle, originalprice, discountedprice, productcategory;
        Switch isActive;
        ImageView productImage, open_edit_details;
        CardView viewProductDetail;
        OnProductEditorClickDialog onProductEditorClickDialog;

        public ProductItemVH(@NonNull View itemView, OnProductEditorClickDialog onProductEditorClickDialog) {
            super(itemView);

            productTitle = itemView.findViewById(R.id.productTitle);
            originalprice = itemView.findViewById(R.id.originalprice);
            discountedprice = itemView.findViewById(R.id.discountedprice);
            productcategory = itemView.findViewById(R.id.productcategory);
            isActive = itemView.findViewById(R.id.isActive);
            productImage = itemView.findViewById(R.id.productImage);
            open_edit_details = itemView.findViewById(R.id.open_edit_details);
            viewProductDetail = itemView.findViewById(R.id.viewProductDetail);
            this.onProductEditorClickDialog = onProductEditorClickDialog;

            open_edit_details.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.open_edit_details) {
                this.onProductEditorClickDialog.onOpenProductEditor(getAdapterPosition());
            }
        }
    }


    public interface OnProductEditorClickDialog {
        void onOpenProductEditor(int position);
    }
}
