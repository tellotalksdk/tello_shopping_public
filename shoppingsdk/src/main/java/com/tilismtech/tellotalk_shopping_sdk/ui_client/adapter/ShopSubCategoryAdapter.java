package com.tilismtech.tellotalk_shopping_sdk.ui_client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ColorChooserAdapter;
import com.tilismtech.tellotalk_shopping_sdk.ui_client.pojo.ShopItems;

import java.util.List;

public class ShopSubCategoryAdapter extends RecyclerView.Adapter<ShopSubCategoryAdapter.ShopSubCategoryVH>{

    List<ShopItems> shopItemsList;
    Context myCtx;


    public ShopSubCategoryAdapter(List<ShopItems> shopItemsList , Context myCtx) {
        this.shopItemsList = shopItemsList;
        this.myCtx = myCtx;
    }

    @NonNull
    @Override
    public ShopSubCategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_view, parent, false);
        return new ShopSubCategoryVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopSubCategoryVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ShopSubCategoryVH extends RecyclerView.ViewHolder{
        TextView productTitle , productDescription , productCategoryName , productOriginalPrice , productDiscountedPrice , inc , dec , productCount;

        public ShopSubCategoryVH(@NonNull View itemView) {
            super(itemView);
            productTitle = itemView.findViewById(R.id.productTitle);
            productDescription = itemView.findViewById(R.id.productDescription);
        }

    }
}
