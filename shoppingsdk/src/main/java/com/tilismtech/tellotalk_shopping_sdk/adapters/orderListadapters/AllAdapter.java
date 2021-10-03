package com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetAllOrderResponse;

import java.util.ArrayList;
import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllStatusItemViewHolder> implements Filterable {

    List<GetAllOrderResponse.Request> allItems;
    Context myCtx;
    Button done;
    OnOrderClickListener onOrderClickListener;

    //  List<GetOrderByStatusResponse.Request> acceptedItems;
    List<GetAllOrderResponse.Request> acceptedItemsFULL;
    List<GetAllOrderResponse.Request> filterlist;

    int totalPrice = 0;


    public AllAdapter(List<GetAllOrderResponse.Request> receivedItemPojos, Context myCtx, OnOrderClickListener onOrderClickListener) {
        this.allItems = receivedItemPojos;
        if (allItems != null) {
            this.acceptedItemsFULL = new ArrayList<>(this.allItems);
        }
        this.myCtx = myCtx;
        this.onOrderClickListener = onOrderClickListener;
    }


    @Override
    public Filter getFilter() {
        return acceptedItemFilter;
    }

    public Filter acceptedItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterlist = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterlist.addAll(acceptedItemsFULL);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GetAllOrderResponse.Request item : acceptedItemsFULL) {
                    if (String.valueOf(item.getOrderNo()).toLowerCase().contains(filterPattern)) {
                        filterlist.add(item);
                    } else if (String.valueOf(item.getMobile()).toLowerCase().contains(filterPattern)) {
                        filterlist.add(item);
                    } else if ((String.valueOf(item.getFirstName()).toLowerCase().contains(filterPattern))) {
                        filterlist.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (allItems != null) {
                allItems.clear();
                allItems.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };

    @NonNull
    @Override
    public AllStatusItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_orderlist_all_items, parent, false);
        return new AllStatusItemViewHolder(v, this.onOrderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStatusItemViewHolder holder, int position) {
        GetAllOrderResponse.Request receivedItemPojo = allItems.get(position);

 /*       String str = receivedItemPojo.getOrderNo();
        String[] arrOfStr = str.split("-");*/

        holder.orderNumber.setText("Order # " + receivedItemPojo.getOrderNo());
        // holder.orderNumber.setText("Order # " + receivedItemPojo.getOrderno());
        holder.customerName.setText(receivedItemPojo.getFirstName() + " " + receivedItemPojo.getMiddleName() + "\n" + receivedItemPojo.getMobile());
        holder.address.setText(receivedItemPojo.getCompleteAddress());
        holder.date.setText(receivedItemPojo.getOrderDate());
        holder.rupees.setText("Rs : " + receivedItemPojo.getGrandTotal());

        if (!TextUtils.isEmpty(receivedItemPojo.getRiderName()) && !TextUtils.isEmpty(receivedItemPojo.getRiderContact())) {
            holder.addRiderInfo1.setVisibility(View.VISIBLE);
            holder.addRiderInfo.setVisibility(View.GONE);
            //  holder.edit_rider_info.setVisibility(View.VISIBLE);
            holder.addRiderInfo1.setText(receivedItemPojo.getRiderName() + " / " + receivedItemPojo.getRiderContact());
        } else {
            holder.addRiderInfo1.setVisibility(View.GONE);
            holder.addRiderInfo.setVisibility(View.GONE);
            // holder.edit_rider_info.setVisibility(View.GONE);
        }

        holder.quantity.setText("Qty ." + receivedItemPojo.getQty());


        if (Integer.parseInt(receivedItemPojo.getOrderStatus()) == 1) {
            holder.orderStat.setText("Received");
            holder.orderStat.setTextColor(Color.BLUE);
        } else if (Integer.parseInt(receivedItemPojo.getOrderStatus()) == 2) {
            holder.orderStat.setText("Accepted");
            holder.orderStat.setTextColor(Color.BLUE);
        } else if (Integer.parseInt(receivedItemPojo.getOrderStatus()) == 3) {
            holder.orderStat.setText("Dispatched");
            holder.orderStat.setTextColor(Color.BLUE);
        } else if (Integer.parseInt(receivedItemPojo.getOrderStatus()) == 4) {
            holder.orderStat.setText("Delivered");
            holder.orderStat.setTextColor(Color.BLUE);
        } else if (Integer.parseInt(receivedItemPojo.getOrderStatus()) == 5) {
            holder.orderStat.setText("Paid");
            holder.orderStat.setTextColor(Color.BLUE);
        } else if (Integer.parseInt(receivedItemPojo.getOrderStatus()) == 6) {
            holder.orderStat.setText("Cancelled");
            holder.orderStat.setTextColor(Color.RED);
            holder.cancelReasonbtn.setVisibility(View.VISIBLE);
        }

        holder.cancelReasonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogCancelReason = new Dialog(myCtx);
                dialogCancelReason.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogCancelReason.setContentView(R.layout.dialog_cancel_order_reason);
                dialogCancelReason.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
                dialogCancelReason.show();

                TextView textView = dialogCancelReason.findViewById(R.id.cancelReason);
                Button okay = dialogCancelReason.findViewById(R.id.confirmRiderbtn);

                if (!TextUtils.isEmpty(receivedItemPojo.getReason())) {
                    textView.setText(receivedItemPojo.getReason());
                } else {
                    textView.setText("No Reason Specified");
                }

                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogCancelReason.dismiss();
                    }
                });
            }
        });


        if (receivedItemPojo.getProductsDetails() != null) {
            if (receivedItemPojo.getProductsDetails().size() > 0) {
                for (int i = 0; i < receivedItemPojo.getProductsDetails().size(); i++) {
                    View child = View.inflate(myCtx, R.layout.individual_item_layout, null);
                    TextView productName, productQuantity, productTotalPrice;
                    productName = child.findViewById(R.id.productName);
                    productQuantity = child.findViewById(R.id.productQuantity);
                    productTotalPrice = child.findViewById(R.id.productTotalCost);

                    totalPrice = totalPrice + Integer.parseInt(receivedItemPojo.getProductsDetails().get(i).getSubTotal());

                    productName.setText(receivedItemPojo.getProductsDetails().get(i).getTitle());
                    productQuantity.setText(receivedItemPojo.getProductsDetails().get(i).getQuantity());
                    productTotalPrice.setText(receivedItemPojo.getProductsDetails().get(i).getSubTotal());

                    holder.totalItems.addView(child);
                }
                totalPrice = 0;
            }
        }

    }

    @Override
    public int getItemCount() {
        if (allItems != null) {
            return allItems.size();
        } else {
            return 0;
        }
    }


    public class AllStatusItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderNumber, customerName, address, quantity, date, rupees, addRiderInfo, viewFull, orderStat, addRiderInfo1;
        private Spinner spinner_moveto;
        OnOrderClickListener onOrderClickListener;
        ImageView edit_rider_info;
        LinearLayout totalItems;
        Button cancelReasonbtn;


        public AllStatusItemViewHolder(@NonNull View itemView, OnOrderClickListener onOrderClickListener) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.orderNumber);
            customerName = itemView.findViewById(R.id.customerName);
            address = itemView.findViewById(R.id.address);
            quantity = itemView.findViewById(R.id.quantity);
            date = itemView.findViewById(R.id.date);
            rupees = itemView.findViewById(R.id.rupees);
            addRiderInfo = itemView.findViewById(R.id.addRiderInfo);
            viewFull = itemView.findViewById(R.id.viewFull);
            orderStat = itemView.findViewById(R.id.orderStat);
            addRiderInfo1 = itemView.findViewById(R.id.addRiderInfo1);
            spinner_moveto = itemView.findViewById(R.id.spinner_moveto);
            edit_rider_info = itemView.findViewById(R.id.edit_rider_info);
            totalItems = itemView.findViewById(R.id.totalItems);
            cancelReasonbtn = itemView.findViewById(R.id.cancelReasonbtn);

            this.onOrderClickListener = onOrderClickListener;
            viewFull.setOnClickListener(this);
            addRiderInfo.setOnClickListener(this);
            edit_rider_info.setOnClickListener(this);
            addRiderInfo1.setOnClickListener(this);
            itemView.setOnClickListener(this);


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.all));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
            spinner_moveto.setAdapter(spinnerArrayAdapter);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.viewFull) {
                onOrderClickListener.OnViewFullOrderListener(Integer.parseInt(allItems.get(getAdapterPosition()).getOrderId()), Integer.parseInt(allItems.get(getAdapterPosition()).getOrderStatus()));
            } else if (v.getId() == R.id.addRiderInfo) {
                onOrderClickListener.OnRiderInfoUpdateListener(getAdapterPosition());
            } else if (v.getId() == R.id.edit_rider_info) {
                onOrderClickListener.OnRiderInfoUpdateListener(Integer.parseInt(allItems.get(getAdapterPosition()).getOrderId()));
            }
        }

    }


    public interface OnOrderClickListener {
        void OnViewFullOrderListener(int position, int orderStatus);

        void OnRiderInfoUpdateListener(int position);
    }

}
