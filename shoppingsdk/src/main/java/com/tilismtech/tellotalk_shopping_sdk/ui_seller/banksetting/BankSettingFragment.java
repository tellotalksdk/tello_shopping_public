package com.tilismtech.tellotalk_shopping_sdk.ui_seller.banksetting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.BankListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.WalletListResponse;

import java.util.ArrayList;
import java.util.List;

public class BankSettingFragment extends Fragment {
    Button btn_bank;
    RelativeLayout RL1, RL2, RL3, RL4;
    Button continue_btn_1, continue_btn_2, continue_btn_3, continue_btn_4, btn_wallet;
    ImageView iv_back, iv_back1;
    boolean isbankClicked, iswalletClicked;
    EditText account_title, account_number, account_title_wallet, account_mobile_number;
    BankSettingViewModel bankSettingViewModel;
    private List<String> banks, wallets;
    private Spinner spinner_bank_names, spinner_wallet_names;

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

        RL1 = view.findViewById(R.id.RL1); //this is the first screen having 2 option wallet and bank...
        RL2 = view.findViewById(R.id.RL2); // this is the  flow for bank selection...
        RL3 = view.findViewById(R.id.RL3); //this is the flow for add branch address...
        RL4 = view.findViewById(R.id.RL4); //this is the flow for wallet selection...
        btn_wallet = view.findViewById(R.id.btn_wallet);
        account_title = view.findViewById(R.id.account_title);
        account_number = view.findViewById(R.id.account_number);
        account_title_wallet = view.findViewById(R.id.account_title_wallet);
        spinner_bank_names = view.findViewById(R.id.spinner_bank_names);
        spinner_wallet_names = view.findViewById(R.id.spinner_wallet_names);
        bankSettingViewModel = new ViewModelProvider(this).get(BankSettingViewModel.class);
        banks = new ArrayList<>();
        wallets = new ArrayList<>();
        populateBankList();
        populateWalletList();

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
                RL4.setVisibility(View.GONE);
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
                RL1.setVisibility(View.GONE);
                RL2.setVisibility(View.GONE);
                RL3.setVisibility(View.VISIBLE);
                RL4.setVisibility(View.GONE);
            }
        });

    }

    private void populateBankList() {
        bankSettingViewModel.bankList();
        bankSettingViewModel.getBankList().observe(getActivity(), new Observer<BankListResponse>() {
            @Override
            public void onChanged(BankListResponse bankListResponse) {
                if (bankListResponse != null) {
                    banks.clear();
                    for (int i = 0; i < bankListResponse.getData().getRequestList().size(); i++) {
                        banks.add(bankListResponse.getData().getRequestList().get(i).getBankName());
                    }
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, banks);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                spinner_bank_names.setAdapter(spinnerArrayAdapter);

            }
        });
    }

    private void populateWalletList() {
        bankSettingViewModel.walletList();
        bankSettingViewModel.getWalletListResponse().observe(getActivity(), new Observer<WalletListResponse>() {
            @Override
            public void onChanged(WalletListResponse walletListResponse) {
                if (walletListResponse != null) {
                    wallets.clear();
                    for (int i = 0; i < walletListResponse.getData().getRequestList().size(); i++) {
                        wallets.add(walletListResponse.getData().getRequestList().get(i).getBankName());
                    }
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, wallets);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                spinner_wallet_names.setAdapter(spinnerArrayAdapter);
            }
        });
    }


}
