package com.tilismtech.tellotalk_shopping_sdk.ui_seller.banksetting;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddBank;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddWallet;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteCardorWallet;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddBankResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddWalletResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.BankListResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ClientWalletDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteBankResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetUserBankDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.WalletListResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.ApplicationUtils;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class BankSettingFragment extends Fragment {
    private Button btn_bank;
    private RelativeLayout RL1, RL2, RL4;
    private LinearLayout RL3, RL5;
    private Button continue_btn_1, continue_btn_2, continue_btn_3, continue_btn_4, btn_wallet;
    private ImageView iv_back, iv_back1, iv_back_two;
    private boolean isbankClicked, iswalletClicked;
    private EditText account_title, account_number, account_title_wallet, account_mobile_number, account_cnic;
    private BankSettingViewModel bankSettingViewModel;
    private List<String> banks, wallets;
    private Spinner spinner_bank_names, spinner_wallet_names;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner searchableBanks, searchableWallets;
    LoadingDialog loadingDialog;

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
        RL5 = view.findViewById(R.id.RL5);
        loadingDialog = new LoadingDialog(getActivity());
        btn_wallet = view.findViewById(R.id.btn_wallet);
        account_title = view.findViewById(R.id.account_title);
        account_number = view.findViewById(R.id.account_number);
        account_title_wallet = view.findViewById(R.id.account_title_wallet);
        account_cnic = view.findViewById(R.id.account_cnic);
        spinner_bank_names = view.findViewById(R.id.spinner_bank_names);
        account_mobile_number = view.findViewById(R.id.account_mobile_number);
        spinner_wallet_names = view.findViewById(R.id.spinner_wallet_names);
        iv_back_two = view.findViewById(R.id.iv_back_two);
        searchableBanks = view.findViewById(R.id.searchableBanks);
        searchableWallets = view.findViewById(R.id.searchableWallets);
        bankSettingViewModel = new ViewModelProvider(this).get(BankSettingViewModel.class);
        banks = new ArrayList<>();
        wallets = new ArrayList<>();
        populateBankList();
        populateWalletList();
        populateBankWalletDetails();

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

        iv_back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL1.setVisibility(View.VISIBLE);
                RL2.setVisibility(View.GONE);
                RL3.setVisibility(View.GONE);
                RL5.setVisibility(View.GONE);
                RL4.setVisibility(View.GONE);
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


                if (!ApplicationUtils.isNetworkConnected(getActivity())) {
                    Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isbankClicked) {
                    RL1.setVisibility(View.GONE);
                    RL2.setVisibility(View.VISIBLE);
                    RL3.setVisibility(View.GONE);
                    RL5.setVisibility(View.GONE);
                }

                if (iswalletClicked) {
                    RL1.setVisibility(View.GONE);
                    RL2.setVisibility(View.GONE);
                    RL3.setVisibility(View.GONE);
                    RL5.setVisibility(View.GONE);
                    RL4.setVisibility(View.VISIBLE);
                }

                if (!iswalletClicked && !isbankClicked) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.select_either_wallet_bank), Toast.LENGTH_SHORT).show();
                }
            }
        });

        continue_btn_2 = view.findViewById(R.id.continue_btn_RL2);
        continue_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //here we set api for adding bank details

                if (checkBankValidation()) {


                    if (!ApplicationUtils.isNetworkConnected(getActivity())) {
                        Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AddBank addBank = new AddBank();
                    addBank.setAccountFrom(searchableBanks.getSelectedItem().toString());
                    addBank.setAccountNumber(account_number.getText().toString());
                    addBank.setIsdefault("1");
                    addBank.setNameOfOwner(account_title.getText().toString());
                    addBank.setProfileId(Constant.PROFILE_ID);
                    bankSettingViewModel.addBankDetails(addBank, getActivity());
                    bankSettingViewModel.getBankDetailResponse().observe(getActivity(), new Observer<AddBankResponse>() {
                        @Override
                        public void onChanged(AddBankResponse addBankResponse) {
                            if (addBankResponse != null) {
                                Toast.makeText(getActivity(), "" + addBankResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                populateBankWalletDetails();
                            }
                        }
                    });
                    RL1.setVisibility(View.GONE);
                    RL2.setVisibility(View.GONE);
                    RL3.setVisibility(View.VISIBLE);
                    RL5.setVisibility(View.VISIBLE);
                    RL4.setVisibility(View.GONE);
                }

            }
        });

        continue_btn_3 = view.findViewById(R.id.continue_btn_RL3);
        continue_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!ApplicationUtils.isNetworkConnected(getActivity())) {
                    Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    return;
                }

                RL1.setVisibility(View.VISIBLE);
                RL2.setVisibility(View.GONE);
                RL3.setVisibility(View.GONE);
                RL5.setVisibility(View.GONE);
            }
        });

        continue_btn_4 = view.findViewById(R.id.continue_btn_RL4);
        continue_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkWalletValidation()) {

                    if (!ApplicationUtils.isNetworkConnected(getActivity())) {
                        Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AddWallet addWallet = new AddWallet();
                    addWallet.setAccountFrom(searchableWallets.getSelectedItem().toString());
                    addWallet.setAccountNumber(account_mobile_number.getText().toString());
                    addWallet.setCnic(account_cnic.getText().toString());
                    addWallet.setProfileId(Constant.PROFILE_ID);
                    addWallet.setNameOfOwner(account_title_wallet.getText().toString());
                    bankSettingViewModel.addWalletDetails(addWallet, getActivity());
                    bankSettingViewModel.getWalletDetailResponse().observe(getActivity(), new Observer<AddWalletResponse>() {
                        @Override
                        public void onChanged(AddWalletResponse addBankResponse) {
                            if (addBankResponse != null) {
                                Toast.makeText(getActivity(), "" + addBankResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                populateBankWalletDetails();
                            }
                        }
                    });
                    RL1.setVisibility(View.GONE);
                    RL2.setVisibility(View.GONE);
                    RL3.setVisibility(View.VISIBLE);
                    RL5.setVisibility(View.VISIBLE);
                    RL4.setVisibility(View.GONE);
                }
            }
        });

    }

    private void populateBankWalletDetails() {

        loadingDialog.showDialog();

        bankSettingViewModel.getUserBankList().removeObservers(getActivity());
        bankSettingViewModel.UserBankList(getActivity());
        bankSettingViewModel.getUserBankList().observe(getActivity(), new Observer<GetUserBankDetailResponse>() {
            @Override
            public void onChanged(GetUserBankDetailResponse getUserBankDetailResponse) {
                if (getUserBankDetailResponse != null) {
                    loadingDialog.dismissDialog();
                    if (getUserBankDetailResponse.getData().getRequestList() != null) {
                        // loadingDialog.dismissDialog();
                        if (getUserBankDetailResponse.getData().getRequestList().size() > 0) {
                            RL1.setVisibility(View.GONE);
                            RL2.setVisibility(View.GONE);
                            RL3.setVisibility(View.VISIBLE);
                            RL5.setVisibility(View.VISIBLE);
                            RL4.setVisibility(View.GONE);
                            iv_back_two.setVisibility(View.VISIBLE);

                            if (RL3.getChildCount() > 0) {
                                RL3.removeAllViews();
                            }

                            for (int i = 0; i < getUserBankDetailResponse.getData().getRequestList().size(); i++) {
                                View inflater = getLayoutInflater().inflate(R.layout.bank_wallet_detail_layout, null);

                                TextView name = inflater.findViewById(R.id.name);
                                TextView title = inflater.findViewById(R.id.title);
                                TextView accountnumber = inflater.findViewById(R.id.accountnumber);
                                ImageView ic_delete = inflater.findViewById(R.id.ic_delete);

                                name.setText(getUserBankDetailResponse.getData().getRequestList().get(i).getAccountFrom());
                                title.setText(getUserBankDetailResponse.getData().getRequestList().get(i).getAccountTitle());
                                accountnumber.setText(getUserBankDetailResponse.getData().getRequestList().get(i).getAccountNumber());

                                int finalI = i;
                                ic_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //here we call delete api

                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
                                        dialog.setContentView(R.layout.dialog_confirm_delete_image);
                                        dialog.show();

                                        Button Yes = dialog.findViewById(R.id.Yes);
                                        Button No = dialog.findViewById(R.id.No);
                                        TextView cancelReason = dialog.findViewById(R.id.cancelReason);
                                        cancelReason.setText("Do you want to remove this account?");


                                        Yes.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                DeleteCardorWallet deleteCardorWallet = new DeleteCardorWallet();
                                                deleteCardorWallet.setId(String.valueOf(getUserBankDetailResponse.getData().getRequestList().get(finalI).getId()));
                                                deleteCardorWallet.setProfileId(Constant.PROFILE_ID);

                                                bankSettingViewModel.deleteBankorWallet(deleteCardorWallet, getActivity());
                                                bankSettingViewModel.getdeleteBankorWallet().observe(getActivity(), new Observer<DeleteBankResponse>() {
                                                    @Override
                                                    public void onChanged(DeleteBankResponse deleteBankResponse) {
                                                        if (deleteBankResponse != null) {
                                                            //Toast.makeText(getActivity(), "successfully deleted...", Toast.LENGTH_SHORT).show();
                                                            RL3.removeView(inflater);
                                                            dialog.dismiss();
                                                        }
                                                    }
                                                });


                                            }
                                        });

                                        No.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                    }
                                });

                                RL3.addView(inflater);
                            }
                        }
                    }
                } else {
                    // loadingDialog.dismissDialog();
                }
            }
        });

        bankSettingViewModel.getUserWalletList().removeObservers(getActivity());
        bankSettingViewModel.UserWalletList(getActivity());
        bankSettingViewModel.getUserWalletList().observe(getActivity(), new Observer<ClientWalletDetailResponse>() {
            @Override
            public void onChanged(ClientWalletDetailResponse clientWalletDetailResponse) {
                if (clientWalletDetailResponse != null) {
                    loadingDialog.dismissDialog();
                    if (clientWalletDetailResponse.getData().getRequestList() != null) {
                        loadingDialog.dismissDialog();
                        if (clientWalletDetailResponse.getData().getRequestList().size() > 0) {
                            RL1.setVisibility(View.GONE);
                            RL2.setVisibility(View.GONE);
                            RL3.setVisibility(View.VISIBLE);
                            RL5.setVisibility(View.VISIBLE);
                            RL4.setVisibility(View.GONE);
                            iv_back_two.setVisibility(View.VISIBLE);

                            for (int i = 0; i < clientWalletDetailResponse.getData().getRequestList().size(); i++) {
                                View inflater = getLayoutInflater().inflate(R.layout.bank_wallet_detail_layout, null);

                                TextView name = inflater.findViewById(R.id.name);
                                TextView title = inflater.findViewById(R.id.title);
                                TextView accountnumber = inflater.findViewById(R.id.accountnumber);
                                ImageView ic_delete = inflater.findViewById(R.id.ic_delete);

                                name.setText(clientWalletDetailResponse.getData().getRequestList().get(i).getAccountFrom());
                                title.setText(clientWalletDetailResponse.getData().getRequestList().get(i).getNameOfOwner());
                                accountnumber.setText(clientWalletDetailResponse.getData().getRequestList().get(i).getAccountNumber());

                                int finalI = i;
                                ic_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //here we call delete api

                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
                                        dialog.setContentView(R.layout.dialog_confirm_delete_image);
                                        dialog.show();

                                        Button Yes = dialog.findViewById(R.id.Yes);
                                        Button No = dialog.findViewById(R.id.No);
                                        TextView cancelReason = dialog.findViewById(R.id.cancelReason);
                                        cancelReason.setText("Do you want to remove this account?");


                                        Yes.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                DeleteCardorWallet deleteCardorWallet = new DeleteCardorWallet();
                                                deleteCardorWallet.setId(String.valueOf(clientWalletDetailResponse.getData().getRequestList().get(finalI).getId()));
                                                deleteCardorWallet.setProfileId(Constant.PROFILE_ID);

                                                bankSettingViewModel.deleteBankorWallet(deleteCardorWallet, getActivity());
                                                bankSettingViewModel.getdeleteBankorWallet().observe(getActivity(), new Observer<DeleteBankResponse>() {
                                                    @Override
                                                    public void onChanged(DeleteBankResponse deleteBankResponse) {
                                                        if (deleteBankResponse != null) {
                                                            //Toast.makeText(getActivity(), "successfully deleted...", Toast.LENGTH_SHORT).show();
                                                            RL3.removeView(inflater);
                                                            dialog.dismiss();
                                                        }
                                                    }
                                                });
                                            }
                                        });

                                        No.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });


                                    }
                                });

                                RL3.addView(inflater);
                            }
                        }
                    }
                } else {
                    loadingDialog.dismissDialog();
                }
            }
        });
    }

    private boolean checkWalletValidation() {

        if (TextUtils.isEmpty(account_title_wallet.getText().toString())) {
            Toast.makeText(getActivity(), "Account Title is required...", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (TextUtils.isEmpty(account_mobile_number.getText().toString())) {
            Toast.makeText(getActivity(), "Mobile Number is required...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(account_cnic.getText().toString())) {
            Toast.makeText(getActivity(), "CNIC is required...", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private boolean checkBankValidation() {


        if (TextUtils.isEmpty(account_title.getText().toString())) {
            Toast.makeText(getActivity(), "Account Title is required...", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (TextUtils.isEmpty(account_number.getText().toString())) {
            Toast.makeText(getActivity(), "Account Number is required...", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
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
                // spinner_bank_names.setAdapter(spinnerArrayAdapter);
                searchableBanks.setAdapter(spinnerArrayAdapter);
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
                //spinner_wallet_names.setAdapter(spinnerArrayAdapter);
                searchableWallets.setAdapter(spinnerArrayAdapter);
            }
        });
    }


}
