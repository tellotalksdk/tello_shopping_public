package com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;

import java.util.ArrayList;
import java.util.List;

public class AcceptedAdapter extends RecyclerView.Adapter<AcceptedAdapter.AcceptedItemViewHolder> implements Filterable {

    List<ReceivedItemPojo> receivedItemPojos;
    Context myCtx;
    Button done;
    OnOrderClickListener onOrderClickListener;
    //
    List<GetOrderByStatusResponse.Request> acceptedItems;
    List<GetOrderByStatusResponse.Request> acceptedItemsFULL;
    List<GetOrderByStatusResponse.Request> filterlist;


    public AcceptedAdapter(List<GetOrderByStatusResponse.Request> receivedItemPojos, Context myCtx, OnOrderClickListener onOrderClickListener) {
        this.acceptedItems = receivedItemPojos;
        if (acceptedItems != null) {
            this.acceptedItemsFULL = new ArrayList<>(this.acceptedItems);
        }
        this.myCtx = myCtx;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public AcceptedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_orderlist_accepted_order, parent, false);
        return new AcceptedItemViewHolder(v, onOrderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedItemViewHolder holder, int position) {
        GetOrderByStatusResponse.Request receivedItemPojo = acceptedItems.get(position);

        holder.orderNumber.setText("Order # " + receivedItemPojo.getOrderid());
        holder.customerName.setText(receivedItemPojo.getFirstname() + receivedItemPojo.getMiddlename() + "\n" + receivedItemPojo.getMobile());
        holder.address.setText(receivedItemPojo.getCompleteAddress());
        holder.date.setText(receivedItemPojo.getOrderdate());
        holder.rupees.setText("Rs : " + receivedItemPojo.getGrandtotal());


    }

    @Override
    public int getItemCount() {
        if (acceptedItems != null) {
            return acceptedItems.size();
        } else {
            return 0;
        }
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
                for (GetOrderByStatusResponse.Request item : acceptedItemsFULL) {
                    if (String.valueOf(item.getOrderid()).toLowerCase().contains(filterPattern)) {
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
            acceptedItems.clear();
            acceptedItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public int getFilterSize(){
        return filterlist.size();
    }

    public class AcceptedItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderNumber, customerName, address, quantity, date, rupees, addRiderInfo, viewFull, orderStatus, orderCancel;
        private Spinner spinner_moveto;
        OnOrderClickListener onOrderClickListener;


        public AcceptedItemViewHolder(@NonNull View itemView, OnOrderClickListener onOrderClickListener) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.orderNumber);
            customerName = itemView.findViewById(R.id.customerName);
            address = itemView.findViewById(R.id.address);
            quantity = itemView.findViewById(R.id.quantity);
            date = itemView.findViewById(R.id.date);
            rupees = itemView.findViewById(R.id.rupees);
            addRiderInfo = itemView.findViewById(R.id.addRiderInfo);
            viewFull = itemView.findViewById(R.id.viewFull);
            spinner_moveto = itemView.findViewById(R.id.spinner_moveto);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderCancel = itemView.findViewById(R.id.orderCancel);

            this.onOrderClickListener = onOrderClickListener;
            viewFull.setOnClickListener(this);
            addRiderInfo.setOnClickListener(this);
            orderStatus.setOnClickListener(this);
            orderCancel.setOnClickListener(this);
            itemView.setOnClickListener(this);


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.accepted));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
            spinner_moveto.setAdapter(spinnerArrayAdapter);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.viewFull) {
                onOrderClickListener.OnViewFullOrderListener(acceptedItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.addRiderInfo) {
                onOrderClickListener.OnRiderInfoUpdateListener(getAdapterPosition());
            } else if (v.getId() == R.id.orderStatus) {
                onOrderClickListener.OnStatusChange(3, acceptedItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.orderCancel) {
                onOrderClickListener.OnStatusChange(6, acceptedItems.get(getAdapterPosition()).getOrderid());
            }
        }
    }


    public interface OnOrderClickListener {
        void OnViewFullOrderListener(int position);

        void OnRiderInfoUpdateListener(int position);

        void OnStatusChange(int status, int OrderID);

    }


}
