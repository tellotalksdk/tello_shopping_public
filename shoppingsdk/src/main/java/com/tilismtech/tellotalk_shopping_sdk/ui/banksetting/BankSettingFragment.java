package com.tilismtech.tellotalk_shopping_sdk.ui.banksetting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tilismtech.tellotalk_shopping_sdk.R;

public class BankSettingFragment extends Fragment {
    Button btn_bank;
    RelativeLayout RL1, RL2, RL3;
    Button continue_btn_1, continue_btn_2, continue_btn_3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_setting_bank_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RL1 = view.findViewById(R.id.RL1);
        RL2 = view.findViewById(R.id.RL2);
        RL3 = view.findViewById(R.id.RL3);


        RL1.setVisibility(View.VISIBLE);
        RL2.setVisibility(View.GONE);
        RL3.setVisibility(View.GONE);


        btn_bank = view.findViewById(R.id.btn_bank);
        btn_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RL1.setVisibility(View.GONE);
                RL2.setVisibility(View.VISIBLE);
                RL3.setVisibility(View.GONE);

            }
        });

        continue_btn_1 = view.findViewById(R.id.continue_btn_RL1);
        continue_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL1.setVisibility(View.GONE);
                RL2.setVisibility(View.VISIBLE);
                RL3.setVisibility(View.GONE);
            }
        });

        continue_btn_2 = view.findViewById(R.id.continue_btn_RL2);
        continue_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL1.setVisibility(View.GONE);
                RL2.setVisibility(View.GONE);
                RL3.setVisibility(View.VISIBLE);
            }
        });

        continue_btn_3 = view.findViewById(R.id.continue_btn_RL3);
        continue_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL1.setVisibility(View.VISIBLE);
                RL2.setVisibility(View.GONE);
                RL3.setVisibility(View.GONE);
            }
        });

    }


}
