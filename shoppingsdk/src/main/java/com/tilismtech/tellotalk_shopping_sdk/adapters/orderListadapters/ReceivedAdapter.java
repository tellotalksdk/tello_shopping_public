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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetOrderByStatusResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class ReceivedAdapter extends RecyclerView.Adapter<ReceivedAdapter.ReceivedItemViewHolder> {

    List<ReceivedItemPojo> receivedItemPojos;
    Context myCtx;
    Button done;
    ArrayList<String> move_to_Opt = new ArrayList<>();
    OnOrderClickListener onOrderClickListener;
    //
    List<GetOrderByStatusResponse.Request> requestList;


    public ReceivedAdapter(List<GetOrderByStatusResponse.Request> receivedItemPojos, Context myCtx, OnOrderClickListener onOrderClickListener) {
        this.requestList = receivedItemPojos;
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

        holder.orderNumber.setText("Order # " + receivedItemPojo.getOrderid());
        holder.customerName.setText(receivedItemPojo.getFirstname() + receivedItemPojo.getMiddlename() + "\n" + receivedItemPojo.getMobile());
        holder.address.setText(receivedItemPojo.getCompleteAddress());
        holder.date.setText(Utility.parseDateToddMMyyyy(receivedItemPojo.getOrderdate()));
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


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.received));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
            spinner_moveto.setAdapter(spinnerArrayAdapter);
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
