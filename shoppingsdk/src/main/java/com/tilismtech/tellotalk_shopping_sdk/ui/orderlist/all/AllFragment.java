package com.tilismtech.tellotalk_shopping_sdk.ui.orderlist.all;

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
import com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters.AllAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;

import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment {

    RecyclerView recycler_all_orders;
    AllAdapter allAdapter;
    List<ReceivedItemPojo> receivedItemPojos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Toast.makeText(getActivity(), "" + getArguments().getString("query"), Toast.LENGTH_SHORT).show();
        }

        recycler_all_orders = view.findViewById(R.id.recycler_all_orders);
        initReceivedItems();
        allAdapter = new AllAdapter(receivedItemPojos, getActivity());
        recycler_all_orders.setAdapter(allAdapter);
    }

    private void initReceivedItems() {
        receivedItemPojos = new ArrayList<>();
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345", "Ahmed \n1234567", "Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345", "Ahmed \n1234567", "Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345", "Ahmed \n1234567", "Unit # 102 , Parsa Tower , Karachi"));
    }
}
