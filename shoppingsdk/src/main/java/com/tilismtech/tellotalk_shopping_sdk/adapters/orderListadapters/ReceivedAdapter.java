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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.CustomSpinnerAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReceivedAdapter extends RecyclerView.Adapter<ReceivedAdapter.ReceivedItemViewHolder> implements Filterable {

    List<ReceivedItemPojo> receivedItemPojos;
    Context myCtx;
    Button done;
    ArrayList<String> move_to_Opt = new ArrayList<>();
    OnOrderClickListener onOrderClickListener;
    //
    List<GetOrderByStatusResponse.Request> requestList;
    List<GetOrderByStatusResponse.Request> requestItemsFull;
    List<GetOrderByStatusResponse.Request> filterlist;


    public ReceivedAdapter(List<GetOrderByStatusResponse.Request> receivedItemPojos, Context myCtx, OnOrderClickListener onOrderClickListener) {
        this.requestList = receivedItemPojos;
        if (requestList != null) {
            this.requestItemsFull = new ArrayList<>(this.requestList);
        }
        this.myCtx = myCtx;
        this.onOrderClickListener = onOrderClickListener;
    }


    @NonNull
    @Override
    public ReceivedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_orderlist_received_items, parent, false);
        return new ReceivedItemViewHolder(v, this.onOrderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedItemViewHolder holder, int position) {
        GetOrderByStatusResponse.Request receivedItemPojo = requestList.get(position);

        String str = receivedItemPojo.getOrderno();
        String[] arrOfStr = str.split("-");

        holder.orderNumber.setText("Order # " + arrOfStr[3]);
        holder.customerName.setText(receivedItemPojo.getFirstname() + " " + receivedItemPojo.getMiddlename() + "\n" + receivedItemPojo.getMobile());
        holder.address.setText(receivedItemPojo.getCompleteAddress());
        holder.quantity.setText("Qty ." + receivedItemPojo.getQuantity());
        holder.date.setText(receivedItemPojo.getOrderdate());
        holder.rupees.setText("Rs : " + receivedItemPojo.getGrandtotal());

    }

    @Override
    public int getItemCount() {
        if (requestList != null) {
            return requestList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return receivedItemFilter;
    }


    public Filter receivedItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterlist = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterlist.addAll(requestItemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GetOrderByStatusResponse.Request item : requestItemsFull) {
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
            if (requestList != null) {
                requestList.clear();
                requestList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };


    public class ReceivedItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderNumber, customerName, address, quantity, date, rupees, addRiderInfo, viewFull, orderStatus;
        private Spinner spinner_moveto;
        OnOrderClickListener onOrderClickListener;

        public ReceivedItemViewHolder(@NonNull View itemView, OnOrderClickListener onOrderClickListener) {
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

            this.onOrderClickListener = onOrderClickListener;
            viewFull.setOnClickListener(this);
            addRiderInfo.setOnClickListener(this);
            orderStatus.setOnClickListener(this);
            itemView.setOnClickListener(this);

            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(myCtx, android.R.layout.simple_spinner_dropdown_item, myCtx.getResources().getStringArray(R.array.received));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_moveto.setAdapter(adapter);

          /*  ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.received));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
            spinner_moveto.setAdapter(spinnerArrayAdapter);*/
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.viewFull) {
                onOrderClickListener.OnViewFullOrderListener(requestList.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.addRiderInfo) {
                onOrderClickListener.OnRiderInfoUpdateListener(getAdapterPosition());
            } else if (v.getId() == R.id.orderStatus) {
                onOrderClickListener.OnStatusChange(2, requestList.get(getAdapterPosition()).getOrderid());
            }
        }

    }

    public interface OnOrderClickListener {
        void OnViewFullOrderListener(int position);

        void OnRiderInfoUpdateListener(int position);

        void onOrderUpdateListener(String status);

        void OnStatusChange(int status, int OrderID);


    }


}
