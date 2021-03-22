package com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;

import java.util.List;

public class ReceivedAdapter extends RecyclerView.Adapter<ReceivedAdapter.ReceivedItemViewHolder> {

    List<ReceivedItemPojo> receivedItemPojos;
    Context myCtx;
    Button done;

    public ReceivedAdapter(List<ReceivedItemPojo> receivedItemPojos, Context myCtx) {
        this.receivedItemPojos = receivedItemPojos;
        this.myCtx = myCtx;
    }

    @NonNull
    @Override
    public ReceivedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_orderlist_received_items, parent, false);
        return new ReceivedItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedItemViewHolder holder, int position) {
        ReceivedItemPojo receivedItemPojo = receivedItemPojos.get(position);

        holder.orderNumber.setText(receivedItemPojo.getOrder_number());
        holder.orderNumber.setText(receivedItemPojo.getOrder_number());
        holder.customerName.setText(receivedItemPojo.getCustomer_name_number());
        holder.address.setText(receivedItemPojo.getCustomer_address());

        holder.addRiderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(myCtx);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_add_rider_info);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                wlp.gravity = Gravity.BOTTOM;
                // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);


                done = dialog.findViewById(R.id.confirmRiderbtn);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

        holder.viewFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(myCtx);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_product_detail_order_list);

                ImageView iv_back = dialog.findViewById(R.id.iv_back);
                iv_back.setOnClickListener(new View.OnClickListener() {
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
        return receivedItemPojos.size();
    }

    public class ReceivedItemViewHolder extends RecyclerView.ViewHolder {

        private TextView orderNumber, customerName, address, quantity, date, rupees, addRiderInfo, viewFull;

        public ReceivedItemViewHolder(@NonNull View itemView) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.orderNumber);
            customerName = itemView.findViewById(R.id.customerName);
            address = itemView.findViewById(R.id.address);
            quantity = itemView.findViewById(R.id.quantity);
            date = itemView.findViewById(R.id.date);
            rupees = itemView.findViewById(R.id.rupees);
            addRiderInfo = itemView.findViewById(R.id.addRiderInfo);
            viewFull = itemView.findViewById(R.id.viewFull);
        }
    }


}
