package com.tilismtech.tellotalk_shopping_sdk.ui.orderlist.dispatched;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters.DispatchedAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;

import java.util.ArrayList;
import java.util.List;

public class DispatchedFragment extends Fragment {

    RecyclerView recycler_dispatched_orders;
    DispatchedAdapter dispatchedAdapter;
    List<ReceivedItemPojo> receivedItemPojos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dispatched_order_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler_dispatched_orders = view.findViewById(R.id.recycler_dispatched_orders);
        initReceivedItems();
        dispatchedAdapter = new DispatchedAdapter(receivedItemPojos,getActivity());
        recycler_dispatched_orders.setAdapter(dispatchedAdapter);
    }


    private void initReceivedItems() {
        receivedItemPojos = new ArrayList<>();
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345","Ahmed \n1234567","Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345","Ahmed \n1234567","Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345","Ahmed \n1234567","Unit # 102 , Parsa Tower , Karachi"));
    }
}
