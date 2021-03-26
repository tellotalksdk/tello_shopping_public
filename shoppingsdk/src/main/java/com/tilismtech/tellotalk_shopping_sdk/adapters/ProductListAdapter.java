package com.tilismtech.tellotalk_shopping_sdk.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ColorChooserPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ProductListpojo;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductItemVH> {


    List<ProductListpojo> productListpojos;
    Context myCtx;
    ColorChooserAdapter.OnColorChooserListener onColorChooserListener;
    LinearLayout outerRL;
    Button closeEditbtn;

    public ProductListAdapter(List<ProductListpojo> productListpojos, Context myCtx) {
        this.productListpojos = productListpojos;
        this.myCtx = myCtx;
        this.onColorChooserListener = onColorChooserListener;
    }

    @NonNull
    @Override
    public ProductItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_items, parent, false);
        return new ProductItemVH(v);
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

        holder.open_edit_details.setOnClickListener(new View.OnClickListener() {
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
        });
    }

    @Override
    public int getItemCount() {
        return productListpojos.size();
    }

    public class ProductItemVH extends RecyclerView.ViewHolder {
        TextView productTitle, originalprice, discountedprice, productcategory;
        Switch isActive;
        ImageView productImage, open_edit_details;

        public ProductItemVH(@NonNull View itemView) {
            super(itemView);

            productTitle = itemView.findViewById(R.id.productTitle);
            originalprice = itemView.findViewById(R.id.originalprice);
            discountedprice = itemView.findViewById(R.id.discountedprice);
            productcategory = itemView.findViewById(R.id.productcategory);
            isActive = itemView.findViewById(R.id.isActive);
            productImage = itemView.findViewById(R.id.productImage);
            open_edit_details = itemView.findViewById(R.id.open_edit_details);
        }
    }
}
