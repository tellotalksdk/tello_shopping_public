package com.tilismtech.tellotalk_shopping_sdk_app.adapters.orderListadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk_app.R;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetOrderByStatusResponse;

import java.util.ArrayList;
import java.util.List;

public class AcceptedAdapter extends RecyclerView.Adapter<AcceptedAdapter.AcceptedItemViewHolder> implements Filterable {

    List<ReceivedItemPojo> receivedItemPojos;
    Context myCtx;
    Button done;
    OnOrderClickListener onOrderClickListener;
    private int totalPrice = 0;
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

      /*  String str = receivedItemPojo.getOrderNo();
        String[] arrOfStr = str.split("-");
*/
        holder.orderNumber.setText("Order # " + receivedItemPojo.getOrderNo());
        holder.customerName.setText(receivedItemPojo.getFirstName() + " " + receivedItemPojo.getMiddleName() + "\n" + receivedItemPojo.getMobile());
        holder.address.setText(receivedItemPojo.getCompleteAddress());
        holder.date.setText(receivedItemPojo.getOrderDate());
        holder.rupees.setText("Rs : " + receivedItemPojo.getGrandTotal());
        holder.quantity.setText("Qty ." + receivedItemPojo.getQty());


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

    }/*order: #jhgjhg*/

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
            if (acceptedItems != null) {
                acceptedItems.clear();
                acceptedItems.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };

    public int getFilterSize() {
        return filterlist.size();
    }

    public class AcceptedItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderNumber, customerName, address, quantity, date, rupees, addRiderInfo, viewFull, orderStatus, orderCancel;
        private Spinner spinner_moveto;
        private LinearLayout totalItems;
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
            totalItems = itemView.findViewById(R.id.totalItems);

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
                onOrderClickListener.OnViewFullOrderListener(Integer.parseInt(acceptedItems.get(getAdapterPosition()).getOrderId()));
            } else if (v.getId() == R.id.addRiderInfo) {
                onOrderClickListener.OnRiderInfoUpdateListener(getAdapterPosition());
            } else if (v.getId() == R.id.orderStatus) {
                onOrderClickListener.OnStatusChange(3, Integer.parseInt(acceptedItems.get(getAdapterPosition()).getOrderId()));
            } else if (v.getId() == R.id.orderCancel) {
                onOrderClickListener.OnStatusChange(6, Integer.parseInt(acceptedItems.get(getAdapterPosition()).getOrderId()));
            }
        }
    }


    public interface OnOrderClickListener {
        void OnViewFullOrderListener(int position);

        void OnRiderInfoUpdateListener(int position);

        void OnStatusChange(int status, int OrderID);

    }


}
