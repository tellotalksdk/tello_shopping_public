package com.tilismtech.tellotalk_shopping_sdk.ui.orderlist.delivered;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters.DeliveredAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;

import java.util.ArrayList;
import java.util.List;

public class DeliveredFragment extends Fragment {

    RecyclerView recycler_delivered_orders;
    DeliveredAdapter deliveredAdapter;
    List<ReceivedItemPojo> receivedItemPojos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delivered_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Toast.makeText(getActivity(), "" + getArguments().getString("query"), Toast.LENGTH_SHORT).show();
        }

        recycler_delivered_orders = view.findViewById(R.id.recycler_delivered_orders);
        initReceivedItems();
        deliveredAdapter = new DeliveredAdapter(receivedItemPojos, getActivity());
        recycler_delivered_orders.setAdapter(deliveredAdapter);
    }

    private void initReceivedItems() {
        receivedItemPojos = new ArrayList<>();
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345", "Ahmed \n1234567", "Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345", "Ahmed \n1234567", "Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345", "Ahmed \n1234567", "Unit # 102 , Parsa Tower , Karachi"));
    }
}
