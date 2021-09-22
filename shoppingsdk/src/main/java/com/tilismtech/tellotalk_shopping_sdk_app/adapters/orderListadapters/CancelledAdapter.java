package com.tilismtech.tellotalk_shopping_sdk_app.adapters.orderListadapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk_app.R;
import com.tilismtech.tellotalk_shopping_sdk_app.adapters.CustomSpinnerAdapter;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetOrderByStatusResponse;

import java.util.ArrayList;
import java.util.List;

public class CancelledAdapter extends RecyclerView.Adapter<CancelledAdapter.CancelledItemViewHolder> implements Filterable {

    List<GetOrderByStatusResponse.Request> cancelledItems;
    Context myCtx;
    Button done;
    OnOrderClickListener onOrderClickListener;
    List<GetOrderByStatusResponse.Request> cancelledItemsFull;
    private int totalPrice = 0;

    public CancelledAdapter(List<GetOrderByStatusResponse.Request> receivedItemPojos, Context myCtx, OnOrderClickListener onOrderClickListener) {
        this.cancelledItems = receivedItemPojos;
        this.myCtx = myCtx;
        this.onOrderClickListener = onOrderClickListener;
        if (cancelledItems != null) {
            this.cancelledItemsFull = new ArrayList<>(cancelledItems);
        }
    }

    @NonNull
    @Override
    public CancelledItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_orderlist_cancelled_items, parent, false);
        return new CancelledItemViewHolder(v, this.onOrderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CancelledItemViewHolder holder, int position) {
        GetOrderByStatusResponse.Request receivedItemPojo = cancelledItems.get(position);

   /*     String str = receivedItemPojo.getOrderNo();
        String[] arrOfStr = str.split("-");

    */

        holder.orderNumber.setText("Order # " + receivedItemPojo.getOrderNo());
        holder.customerName.setText(receivedItemPojo.getFirstName() + " " + receivedItemPojo.getMiddleName() + "\n" + receivedItemPojo.getMobile());
        holder.address.setText(receivedItemPojo.getCompleteAddress());
        holder.date.setText(receivedItemPojo.getOrderDate());
        holder.rupees.setText("Rs : " + receivedItemPojo.getGrandTotal());

        if (!TextUtils.isEmpty(receivedItemPojo.getRiderName()) && !TextUtils.isEmpty(receivedItemPojo.getRiderContact())) {
            holder.addRiderInfo1.setVisibility(View.VISIBLE);
            holder.addRiderInfo.setVisibility(View.GONE);
            holder.edit_rider_info.setVisibility(View.VISIBLE);
            holder.addRiderInfo1.setText(receivedItemPojo.getRiderName() + " / " + receivedItemPojo.getRiderContact());
        } else {
            holder.addRiderInfo1.setVisibility(View.GONE);
            holder.addRiderInfo.setVisibility(View.VISIBLE);
            holder.edit_rider_info.setVisibility(View.GONE);
        }

        holder.quantity.setText("Qty ." + receivedItemPojo.getQty());

        holder.iv_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Toast.makeText(myCtx, "" + receivedItemPojo.getReason(), Toast.LENGTH_SHORT).show();
            }
        });

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
        if (cancelledItems != null) {
            return cancelledItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return cancelledItemFilter;
    }

    public Filter cancelledItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GetOrderByStatusResponse.Request> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(cancelledItemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GetOrderByStatusResponse.Request item : cancelledItemsFull) {
                    if (String.valueOf(item.getOrderNo()).toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    } else if (String.valueOf(item.getMobile()).toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    } else if ((String.valueOf(item.getFirstName()).toLowerCase().contains(filterPattern))) {
                        filterList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (cancelledItems != null) {
                cancelledItems.clear();
                cancelledItems.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };

    public class CancelledItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderNumber, customerName, address, quantity, date, rupees, addRiderInfo, viewFull, orderStatus, addRiderInfo1;
        private Spinner spinner_moveto;
        OnOrderClickListener onOrderClickListener;
        ImageView edit_rider_info;
        ImageView iv_locate;
        Button cancelReasonbtn;
        private LinearLayout totalItems;


        public CancelledItemViewHolder(@NonNull View itemView, OnOrderClickListener onOrderClickListener) {
            super(itemView);

            iv_locate = itemView.findViewById(R.id.iv_locate);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            customerName = itemView.findViewById(R.id.customerName);
            address = itemView.findViewById(R.id.address);
            quantity = itemView.findViewById(R.id.quantity);
            date = itemView.findViewById(R.id.date);
            rupees = itemView.findViewById(R.id.rupees);
            addRiderInfo = itemView.findViewById(R.id.addRiderInfo);
            viewFull = itemView.findViewById(R.id.viewFull);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            spinner_moveto = itemView.findViewById(R.id.spinner_moveto);
            addRiderInfo1 = itemView.findViewById(R.id.addRiderInfo1);
            edit_rider_info = itemView.findViewById(R.id.edit_rider_info);
            cancelReasonbtn = itemView.findViewById(R.id.cancelReasonbtn);
            totalItems = itemView.findViewById(R.id.totalItems);

            this.onOrderClickListener = onOrderClickListener;
            viewFull.setOnClickListener(this);
            addRiderInfo.setOnClickListener(this);
            orderStatus.setOnClickListener(this);
            addRiderInfo1.setOnClickListener(this);
            edit_rider_info.setOnClickListener(this);
            itemView.setOnClickListener(this);

            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.cancel));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_moveto.setAdapter(adapter);

            /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(myCtx, R.layout.spinner_text, myCtx.getResources().getStringArray(R.array.cancel));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
            spinner_moveto.setAdapter(spinnerArrayAdapter);*/

            spinner_moveto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (parent.getId() == spinner_moveto.getId()) {
                        if (parent.getItemAtPosition(position).equals("Accept")) {
                            onOrderClickListener.OnStatusChange(2, Integer.parseInt(cancelledItems.get(getAdapterPosition()).getOrderId()));
                        } else if (parent.getItemAtPosition(position).equals("Dispatch")) {
                            onOrderClickListener.OnStatusChange(3, Integer.parseInt(cancelledItems.get(getAdapterPosition()).getOrderId()));
                        } else if (parent.getItemAtPosition(position).equals("Deliver")) {
                            onOrderClickListener.OnStatusChange(4, Integer.parseInt(cancelledItems.get(getAdapterPosition()).getOrderId()));
                        } else if (parent.getItemAtPosition(position).equals("Paid")) {
                            onOrderClickListener.OnStatusChange(5, Integer.parseInt(cancelledItems.get(getAdapterPosition()).getOrderId()));
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
                onOrderClickListener.OnViewFullOrderListener(Integer.parseInt(cancelledItems.get(getAdapterPosition()).getOrderId()));
            } else if (v.getId() == R.id.addRiderInfo) {
                onOrderClickListener.OnRiderInfoUpdateListener(Integer.parseInt(cancelledItems.get(getAdapterPosition()).getOrderId()));
            } else if (v.getId() == R.id.orderStatus) {
                // onOrderClickListener.OnStatusChange(5, cancelledItems.get(getAdapterPosition()).getOrderid());
            } else if (v.getId() == R.id.edit_rider_info) {
                onOrderClickListener.OnRiderInfoUpdateListener(Integer.parseInt(cancelledItems.get(getAdapterPosition()).getOrderId()), cancelledItems.get(getAdapterPosition()));
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
