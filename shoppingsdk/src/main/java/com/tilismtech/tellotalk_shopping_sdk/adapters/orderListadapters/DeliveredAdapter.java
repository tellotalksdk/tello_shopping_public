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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.CustomSpinnerAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;

import java.util.ArrayList;
import java.util.List;

public class DeliveredAdapter extends RecyclerView.Adapter<DeliveredAdapter.DeliveredItemViewHolder> implements Filterable {


    Context myCtx;
    Button done;
    OnOrderClickListener onOrderClickListener;

    List<GetOrderByStatusResponse.Request> deliveredItems;
    List<GetOrderByStatusResponse.Request> deliveredItemsFULL;
    List<GetOrderByStatusResponse.Request> filterlist;

    public DeliveredAdapter(List<GetOrderByStatusResponse.Request> receivedItemPojos, Context myCtx, OnOrderClickListener onOrderClickListener) {
        this.deliveredItems = receivedItemPojos;
        if (deliveredItems != null) {
            this.deliveredItemsFULL = new ArrayList<>(this.deliveredItems);
        }
        this.myCtx = myCtx;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public DeliveredItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_orderlist_delivered_items, parent, false);
        return new DeliveredItemViewHolder(v, this.onOrderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredItemViewHolder holder, int position) {
        GetOrderByStatusResponse.Request receivedItemPojo = deliveredItems.get(position);

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

    }

    @Override
    public int getItemCount() {
        if (deliveredItems != null) {
            return deliveredItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return deliveredItemFilter;
    }

    public Filter deliveredItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterlist = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterlist.addAll(deliveredItemsFULL);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GetOrderByStatusResponse.Request item : deliveredItemsFULL) {
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
            deliveredItems.clear();
            deliveredItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    public class DeliveredItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderNumber, customerName, address, quantity, date, rupees, addRiderInfo, viewFull, orderStatus, addRiderInfo1;
        private Spinner spinner_moveto;
        OnOrderClickListener onOrderClickListener;
        ImageView edit_rider_info;


        public DeliveredItemViewHolder(@NonNull View itemView, OnOrderClickListener onOrderClickListener) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.orderNumber);
            customerName = itemView.findViewById(R.id.customerName);
            address = itemView.findViewById(R.id.address);
            quantity = itemView.findViewById(R.id.quantity);
            date = itemView.findViewById(R.id.date);
            rupees = itemView.findViewById(R.id.rupees);
            addRiderInfo = itemView.findViewById(R.id.addRiderInfo);
            viewFull = itemView.findViewById(R.id.viewFull);
            addRiderInfo1 = itemView.findViewById(R.id.addRiderInfo1);
            edit_rider_info = itemView.findViewById(R.id.edit_rider_info);

            spinner_moveto = itemView.findViewById(R.id.spinner_moveto);
            orderStatus = itemView.findViewById(R.id.orderStatus);

            this.onOrderClickListener = onOrderClickListener;
            viewFull.setOnClickListener(this);
            addRiderInfo.setOnClickListener(this);
            orderStatus.setOnClickListener(this);
            edit_rider_info.setOnClickListener(this);
            itemView.setOnClickListener(this);

            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.delivered));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_moveto.setAdapter(adapter);

            /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.delivered));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
            spinner_moveto.setAdapter(spinnerArrayAdapter);*/

            spinner_moveto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (parent.getId() == spinner_moveto.getId()) {
                        if (parent.getItemAtPosition(position).equals("Accept")) {
                            onOrderClickListener.OnStatusChange(2, deliveredItems.get(getAdapterPosition()).getOrderid());
                        } else if (parent.getItemAtPosition(position).equals("Dispatch")) {
                            onOrderClickListener.OnStatusChange(3, deliveredItems.get(getAdapterPosition()).getOrderid());
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
                onOrderClickListener.OnViewFullOrderListener(deliveredItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.addRiderInfo) {
                onOrderClickListener.OnRiderInfoUpdateListener(deliveredItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.orderStatus) {
                onOrderClickListener.OnStatusChange(5, deliveredItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.edit_rider_info) {
                onOrderClickListener.OnRiderInfoUpdateListener(deliveredItems.get(getAdapterPosition()).getOrderid(),deliveredItems.get(getAdapterPosition()));
            }
        }
    }


    public interface OnOrderClickListener {
        void OnViewFullOrderListener(int position);

        void OnRiderInfoUpdateListener(int position);

        void OnRiderInfoUpdateListener(int position, GetOrderByStatusResponse.Request request);


        void OnStatusChange(int status, int OrderID);
    }

}
