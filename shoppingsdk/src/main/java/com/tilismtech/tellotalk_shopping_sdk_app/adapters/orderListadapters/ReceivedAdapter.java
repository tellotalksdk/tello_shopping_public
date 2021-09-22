package com.tilismtech.tellotalk_shopping_sdk_app.adapters.orderListadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk_app.R;
import com.tilismtech.tellotalk_shopping_sdk_app.adapters.CustomSpinnerAdapter;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetOrderByStatusResponse;

import java.util.ArrayList;
import java.util.List;

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

    int totalPrice = 0;


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

        /*String str = receivedItemPojo.getOrderNo();
        String[] arrOfStr = str.split("-");*/

        holder.orderNumber.setText("Order # " + receivedItemPojo.getOrderNo());

        holder.customerName.setText(receivedItemPojo.getFirstName() + " " + receivedItemPojo.getMiddleName() + "\n" + receivedItemPojo.getMobile());
        holder.address.setText(receivedItemPojo.getCompleteAddress());
        holder.quantity.setText("Qty ." + receivedItemPojo.getQty());
        holder.date.setText(receivedItemPojo.getOrderDate());
        holder.rupees.setText("Rs : " + receivedItemPojo.getGrandTotal());

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
                holder.totalCosttobeReceived.setText(String.valueOf(totalPrice));
                holder.totalCosttobeReceived.setVisibility(View.GONE);
                totalPrice = 0;
            }
        }


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
                String filterPattern1 = constraint.toString().toLowerCase().trim();
                for (GetOrderByStatusResponse.Request item : requestItemsFull) {
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
            if (requestList != null) {
                requestList.clear();
                requestList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };


    public class ReceivedItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderNumber, customerName, address, quantity, date, rupees, addRiderInfo, viewFull, orderStatus, totalCosttobeReceived;
        private Spinner spinner_moveto;
        private LinearLayout totalItems;
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
            totalItems = itemView.findViewById(R.id.totalItems);
            totalCosttobeReceived = itemView.findViewById(R.id.totalCosttobeReceived);

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
                onOrderClickListener.OnViewFullOrderListener(Integer.parseInt(requestList.get(getAdapterPosition()).getOrderId()));
            } else if (v.getId() == R.id.addRiderInfo) {
                onOrderClickListener.OnRiderInfoUpdateListener(getAdapterPosition());
            } else if (v.getId() == R.id.orderStatus) {
                onOrderClickListener.OnStatusChange(2, Integer.parseInt(requestList.get(getAdapterPosition()).getOrderId()));
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
