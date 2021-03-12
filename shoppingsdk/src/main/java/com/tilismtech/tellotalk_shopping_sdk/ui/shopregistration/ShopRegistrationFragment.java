package com.tilismtech.tellotalk_shopping_sdk.ui.shopregistration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tilismtech.tellotalk_shopping_sdk.R;

public class ShopRegistrationFragment extends Fragment {

    Button btnCreateAccount;
    Button requestAgain , done_btn;
    NavController navController;
    RelativeLayout RL;
    ImageView iv_back;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_registration,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        btnCreateAccount = view.findViewById(R.id.createAccountbtn);
        requestAgain = view.findViewById(R.id.requestAgain);
        done_btn = view.findViewById(R.id.done_btn);
        RL = view.findViewById(R.id.RL);
        iv_back = view.findViewById(R.id.iv_back);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               RL.setVisibility(View.VISIBLE);
               btnCreateAccount.setVisibility(View.GONE);
           }
       });

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_shopRegistrationFragment_to_shopSettingFragment);
            }
        });

        requestAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }
}
