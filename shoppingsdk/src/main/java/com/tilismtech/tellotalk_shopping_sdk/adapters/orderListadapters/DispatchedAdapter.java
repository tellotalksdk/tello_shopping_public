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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.CustomSpinnerAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;

import java.util.ArrayList;
import java.util.List;

public class DispatchedAdapter extends RecyclerView.Adapter<DispatchedAdapter.DispatchedItemViewHolder> implements Filterable {


    Context myCtx;
    Button done;
    OnOrderClickListener onOrderClickListener;

    List<GetOrderByStatusResponse.Request> dispatchedItems;
    List<GetOrderByStatusResponse.Request> dispatchedItemsFULL;
    List<GetOrderByStatusResponse.Request> filterlist;


    public DispatchedAdapter(List<GetOrderByStatusResponse.Request> receivedItemPojos, Context myCtx, OnOrderClickListener onOrderClickListener) {
        this.dispatchedItems = receivedItemPojos;
        if (dispatchedItems != null) {
            this.dispatchedItemsFULL = new ArrayList<>(this.dispatchedItems);
        }
        this.myCtx = myCtx;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public DispatchedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_orderlist_dispatched_items, parent, false);
        return new DispatchedItemViewHolder(v, this.onOrderClickListener);
    }

    @Override
    public Filter getFilter() {
        return dispatchedItemFilter;
    }

    public Filter dispatchedItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterlist = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterlist.addAll(dispatchedItemsFULL);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GetOrderByStatusResponse.Request item : dispatchedItemsFULL) {
                    if (String.valueOf(item.getOrderno()).toLowerCase().contains(filterPattern)) {
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
            dispatchedItems.clear();
            dispatchedItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    @Override
    public void onBindViewHolder(@NonNull DispatchedItemViewHolder holder, int position) {
        GetOrderByStatusResponse.Request receivedItemPojo = dispatchedItems.get(position);

        String str = receivedItemPojo.getOrderno();
        String[] arrOfStr = str.split("-");

        holder.orderNumber.setText("Order # " + arrOfStr[3]);
        holder.customerName.setText(receivedItemPojo.getFirstname() + " " + receivedItemPojo.getMiddlename() + "\n" + receivedItemPojo.getMobile());
        holder.address.setText(receivedItemPojo.getCompleteAddress());
        holder.date.setText(receivedItemPojo.getOrderdate());
        holder.rupees.setText("Rs : " + receivedItemPojo.getGrandtotal());

        if (receivedItemPojo.getRiderName() != null && receivedItemPojo.getRiderContact() != null) {
            holder.addRiderInfo1.setVisibility(View.VISIBLE);
            holder.addRiderInfo.setVisibility(View.GONE);
            holder.edit_rider_info.setVisibility(View.VISIBLE);
            holder.addRiderInfo1.setText(receivedItemPojo.getRiderName() + " / " + receivedItemPojo.getRiderContact());
        } else {
            holder.addRiderInfo1.setVisibility(View.GONE);
            holder.addRiderInfo.setVisibility(View.VISIBLE);
            holder.edit_rider_info.setVisibility(View.GONE);
        }

        holder.quantity.setText("Qty ." + receivedItemPojo.getQuantity());


      /*  holder.addRiderInfo.setOnClickListener(new View.OnClickListener() {
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
        });*/


    }

    @Override
    public int getItemCount() {
        if (dispatchedItems != null) {
            return dispatchedItems.size();
        } else {
            return 0;
        }
    }


    public class DispatchedItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderNumber, customerName, address, quantity, date, rupees, addRiderInfo, viewFull, orderStatus, orderCancel, addRiderInfo1;
        private Spinner spinner_moveto;
        private ImageView edit_rider_info;
        OnOrderClickListener onOrderClickListener;


        public DispatchedItemViewHolder(@NonNull View itemView, OnOrderClickListener onOrderClickListener) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.orderNumber);
            customerName = itemView.findViewById(R.id.customerName);
            address = itemView.findViewById(R.id.address);
            quantity = itemView.findViewById(R.id.quantity);
            date = itemView.findViewById(R.id.date);
            rupees = itemView.findViewById(R.id.rupees);
            addRiderInfo = itemView.findViewById(R.id.addRiderInfo);
            addRiderInfo1 = itemView.findViewById(R.id.addRiderInfo1);
            viewFull = itemView.findViewById(R.id.viewFull);
            edit_rider_info = itemView.findViewById(R.id.edit_rider_info);

            spinner_moveto = itemView.findViewById(R.id.spinner_moveto);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderCancel = itemView.findViewById(R.id.orderCancel);

            this.onOrderClickListener = onOrderClickListener;
            viewFull.setOnClickListener(this);
            addRiderInfo.setOnClickListener(this);
            orderStatus.setOnClickListener(this);
            orderCancel.setOnClickListener(this);
            edit_rider_info.setOnClickListener(this);
            itemView.setOnClickListener(this);

            /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.dispatched));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
            spinner_moveto.setAdapter(spinnerArrayAdapter);*/

            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.dispatched));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_moveto.setAdapter(adapter);


            spinner_moveto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (parent.getId() == spinner_moveto.getId()) {
                        if (parent.getItemAtPosition(position).equals("Accept")) {
                            onOrderClickListener.OnStatusChange(2, dispatchedItems.get(getAdapterPosition()).getOrderid());
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.viewFull) {
                onOrderClickListener.OnViewFullOrderListener(dispatchedItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.addRiderInfo) {
                onOrderClickListener.OnRiderInfoUpdateListener(dispatchedItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.orderStatus) {
                onOrderClickListener.OnStatusChange(4, dispatchedItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.orderCancel) {
                onOrderClickListener.OnStatusChange(6, dispatchedItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.edit_rider_info) {
                onOrderClickListener.OnRiderInfoUpdateListener(dispatchedItems.get(getAdapterPosition()).getOrderid());
            }
        }
    }


    public interface OnOrderClickListener {
        void OnViewFullOrderListener(int position);

        void OnRiderInfoUpdateListener(int position);

        void OnStatusChange(int status, int OrderID);

        void OnSpinnerItem(int position, Spinner spinner);
    }


}
