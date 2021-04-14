package com.tilismtech.tellotalk_shopping_sdk.ui.banksetting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tilismtech.tellotalk_shopping_sdk.R;

public class BankSettingFragment extends Fragment {
    Button btn_bank;
    RelativeLayout RL1, RL2, RL3, RL4;
    Button continue_btn_1, continue_btn_2, continue_btn_3, continue_btn_4, btn_wallet;
    ImageView iv_back, iv_back1;
    boolean isbankClicked, iswalletClicked;
    EditText account_title, account_number, account_title_wallet, account_mobile_number;

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
        RL4 = view.findViewById(R.id.RL4);
        btn_wallet = view.findViewById(R.id.btn_wallet);
        account_title = view.findViewById(R.id.account_title);
        account_number = view.findViewById(R.id.account_number);
        account_title_wallet = view.findViewById(R.id.account_title_wallet);


        btn_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iswalletClicked) {
                    btn_wallet.setBackground(getResources().getDrawable(R.drawable.bg_rounded_wallet_button));
                    btn_wallet.setTextColor(Color.BLACK);
                    btn_bank.setBackground(getResources().getDrawable(R.drawable.bg_rounded_wallet_button));
                    btn_bank.setTextColor(Color.BLACK);
                    iswalletClicked = false;
                    isbankClicked = false;
                } else {
                    btn_wallet.setBackground(getResources().getDrawable(R.drawable.bg_rounded_black_bank_btn));
                    btn_wallet.setTextColor(Color.WHITE);
                    btn_bank.setBackground(getResources().getDrawable(R.drawable.bg_rounded_wallet_button));
                    btn_bank.setTextColor(Color.BLACK);
                    iswalletClicked = true;
                    isbankClicked = false;
                }

            }
        });

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL2.setVisibility(View.GONE);
                RL1.setVisibility(View.VISIBLE);
            }
        });

        iv_back1 = view.findViewById(R.id.iv_back1);
        iv_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL4.setVisibility(View.GONE);
                RL1.setVisibility(View.VISIBLE);
            }
        });


        btn_bank = view.findViewById(R.id.btn_bank);
        btn_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isbankClicked) {
                    btn_bank.setBackground(getResources().getDrawable(R.drawable.bg_rounded_wallet_button));
                    btn_bank.setTextColor(Color.BLACK);
                    btn_wallet.setBackground(getResources().getDrawable(R.drawable.bg_rounded_wallet_button));
                    btn_wallet.setTextColor(Color.BLACK);
                    isbankClicked = false;
                    iswalletClicked = false;
                } else {
                    btn_bank.setBackground(getResources().getDrawable(R.drawable.bg_rounded_black_bank_btn));
                    btn_bank.setTextColor(Color.WHITE);
                    btn_wallet.setBackground(getResources().getDrawable(R.drawable.bg_rounded_wallet_button));
                    btn_wallet.setTextColor(Color.BLACK);
                    isbankClicked = true;
                    iswalletClicked = false;
                }
               /* RL1.setVisibility(View.GONE);
                RL2.setVisibility(View.VISIBLE);
                RL3.setVisibility(View.GONE);*/
            }
        });

        continue_btn_1 = view.findViewById(R.id.continue_btn_RL1);
        continue_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isbankClicked) {
                    RL1.setVisibility(View.GONE);
                    RL2.setVisibility(View.VISIBLE);
                    RL3.setVisibility(View.GONE);
                }

                if (iswalletClicked) {
                    RL1.setVisibility(View.GONE);
                    RL2.setVisibility(View.GONE);
                    RL3.setVisibility(View.GONE);
                    RL4.setVisibility(View.VISIBLE);
                }
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

        continue_btn_4 = view.findViewById(R.id.continue_btn_RL4);
        continue_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL1.setVisibility(View.VISIBLE);
                RL2.setVisibility(View.GONE);
                RL3.setVisibility(View.GONE);
            }
        });

    }


}
