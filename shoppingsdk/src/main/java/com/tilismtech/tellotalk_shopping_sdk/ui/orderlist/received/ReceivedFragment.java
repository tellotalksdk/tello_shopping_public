package com.tilismtech.tellotalk_shopping_sdk.ui.orderlist.received;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.orderListadapters.ReceivedAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ReceivedItemPojo;
import com.tilismtech.tellotalk_shopping_sdk.ui.orderlist.OrderListViewModel;
import com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage.ShopLandingPageViewModel;

import java.security.cert.CertPathBuilderSpi;
import java.util.ArrayList;
import java.util.List;

public class ReceivedFragment extends Fragment implements ReceivedAdapter.OnOrderClickListener {

    RecyclerView recycler_received_orders;
    ReceivedAdapter receivedAdapter;
    List<ReceivedItemPojo> receivedItemPojos;
    OrderListViewModel orderListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_received_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Toast.makeText(getActivity(), "" + getArguments().getString("query"), Toast.LENGTH_SHORT).show();
        }
        orderListViewModel = new ViewModelProvider(this).get(OrderListViewModel.class);
        recycler_received_orders = view.findViewById(R.id.recycler_received_orders);
        initReceivedItems();
        receivedAdapter = new ReceivedAdapter(receivedItemPojos,getActivity(),getReference());
        recycler_received_orders.setAdapter(receivedAdapter);

    }

    private void initReceivedItems() {
        receivedItemPojos = new ArrayList<>();
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345","Ahmed \n1234567","Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345","Ahmed \n1234567","Unit # 102 , Parsa Tower , Karachi"));
        receivedItemPojos.add(new ReceivedItemPojo("Order # 102345","Ahmed \n1234567","Unit # 102 , Parsa Tower , Karachi"));

    }


    @Override
    public void OnViewFullOrderListener(int position) {
        Toast.makeText(getActivity(), "position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnRiderInfoUpdateListener(int position) {
        Toast.makeText(getActivity(), "position " + position, Toast.LENGTH_SHORT).show();
    }

    public ReceivedAdapter.OnOrderClickListener getReference(){
        return this;
    }
}
