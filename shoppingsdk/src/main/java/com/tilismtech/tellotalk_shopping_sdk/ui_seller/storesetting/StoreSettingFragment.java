package com.tilismtech.tellotalk_shopping_sdk.ui_seller.storesetting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.DeleteBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.DeleteBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class StoreSettingFragment extends Fragment {

    private ImageView ic_delete, ic_edit, addnewaddress, addbranchaddress, iv_top_image ;
    private EditText registeredShopNumber, et_street, et_city, et_province, et_country, et_shop_url;
    private LinearLayout addressRL;
    private RelativeLayout addedAddress;
    private String shopId, addressId;
    private List<String> branchIds; //we need to store branch id to update and delete these ids...
    StoreSettingViewModel storeSettingViewModel;
    NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_setting_store_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        registeredShopNumber.setText(TelloPreferenceManager.getInstance(getActivity()).getRegisteredNumber());
        registeredShopNumber.setEnabled(false);
        navController = Navigation.findNavController(view);
        storeSettingViewModel = new ViewModelProvider(this).get(StoreSettingViewModel.class);
        branchIds = new ArrayList<>();
        GetShopDetail getShopDetail = new GetShopDetail();
        getShopDetail.setProfileId(Constant.PROFILE_ID);
        storeSettingViewModel.postShopDetail(getShopDetail, getActivity());
        storeSettingViewModel.getShopDetail().observe(getActivity(), new Observer<GetShopDetailResponse>() {
            @Override
            public void onChanged(GetShopDetailResponse getShopDetailResponse) {
                if (getShopDetailResponse != null) {
                    // Toast.makeText(getActivity(), "" + getShopDetailResponse.getStatusDetail() + getShopDetailResponse.getData().getRequestList().getBranchAddress().size(), Toast.LENGTH_SHORT).show();
                    et_shop_url.setText(getShopDetailResponse.getData().getRequestList().getShopURl());
                    shopId = getShopDetailResponse.getData().getRequestList().getShopId();

                    if (getShopDetailResponse.getData().getRequestList().getShopProfile().equals("")) {
                        iv_top_image.setImageDrawable(getResources().getDrawable(R.drawable.banner_img));
                    } else {
                        Glide.with(getActivity()).load(getShopDetailResponse.getData().getRequestList().getShopProfile()).placeholder(R.drawable.banner_img).into(iv_top_image);
                    }

                    addMainBranchAddress(getShopDetailResponse.getData().getRequestList());


                    if (getShopDetailResponse.getData().getRequestList().getBranchAddress() != null) {
                        for (int i = 0; i < getShopDetailResponse.getData().getRequestList().getBranchAddress().size(); i++) {
                            branchIds.add(getShopDetailResponse.getData().getRequestList().getBranchAddress().get(i).getAddressId());
                        }
                        addedAddress(getShopDetailResponse.getData().getRequestList().getBranchAddress().size(), getShopDetailResponse.getData().getRequestList());
                    }

                }
            }
        });

        addnewaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflater = getLayoutInflater().inflate(R.layout.add_address_layout, null);
                addressRL.addView(inflater);

                et_street = inflater.findViewById(R.id.et_street);
                et_city = inflater.findViewById(R.id.et_city);
                et_province = inflater.findViewById(R.id.et_province);
                et_country = inflater.findViewById(R.id.et_country);
                ic_delete = inflater.findViewById(R.id.ic_delete);
                ic_edit = inflater.findViewById(R.id.ic_edit);
                addbranchaddress = inflater.findViewById(R.id.addbranchaddress);

                ic_delete.setVisibility(View.INVISIBLE);
                ic_edit.setVisibility(View.INVISIBLE);

                ic_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "CLICK", Toast.LENGTH_SHORT).show();
                    /*    DeleteBranchAddress deleteBranchAddress = new DeleteBranchAddress();
                        deleteBranchAddress.setId(addressId);
                        deleteBranchAddress.setProfileId(Constant.PROFILE_ID);

                        storeSettingViewModel.deleteBranchAddress(deleteBranchAddress);
                        storeSettingViewModel.getDeleteBranchAddress().observe(getActivity(), new Observer<DeleteBranchAddressResponse>() {
                            @Override
                            public void onChanged(DeleteBranchAddressResponse deleteBranchAddressResponse) {
                                if (deleteBranchAddressResponse != null) {
                                    Toast.makeText(getActivity(), " " + deleteBranchAddressResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/

                        //addressRL.removeView(inflater);
                    }
                });

                addbranchaddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(), "CLICKED", Toast.LENGTH_SHORT).show();
                        if (TextUtils.isEmpty(et_street.getText().toString()) ||
                                TextUtils.isEmpty(et_city.getText().toString()) ||
                                TextUtils.isEmpty(et_province.getText().toString()) ||
                                TextUtils.isEmpty(et_country.getText().toString())) {
                            Toast.makeText(getActivity(), "Some Fields are missing...", Toast.LENGTH_SHORT).show();
                        } else {

                            AddBranchAddress addBranchAddress = new AddBranchAddress();
                            addBranchAddress.setProfileId(Constant.PROFILE_ID);
                            addBranchAddress.setCity(et_city.getText().toString());
                            addBranchAddress.setProvince(et_province.getText().toString());
                            addBranchAddress.setCountry(et_country.getText().toString());
                            addBranchAddress.setLine1(et_street.getText().toString());

                            storeSettingViewModel.addBranchAddress(addBranchAddress, getActivity());
                            storeSettingViewModel.getAddBranchAddress().observe(getActivity(), new Observer<AddBranchAddressResponse>() {
                                @Override
                                public void onChanged(AddBranchAddressResponse addBranchAddressResponse) {
                                    if (addBranchAddressResponse != null) {
                                        if (addBranchAddressResponse.getStatus().equals("0")) {
                                            Toast.makeText(getActivity(), "Shop Address Has Been Added Successfully...", Toast.LENGTH_SHORT).show();
                                            navController.navigate(R.id.storeSettingFragment);
                                            Utility.hideKeyboard(getActivity(), view);
                                        }
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });
    }

    private void addMainBranchAddress(GetShopDetailResponse.RequestList requestList) {
        View inflater = getLayoutInflater().inflate(R.layout.add_address_layout, null);
        addressRL.addView(inflater);

        et_street = inflater.findViewById(R.id.et_street);
        et_city = inflater.findViewById(R.id.et_city);
        et_province = inflater.findViewById(R.id.et_province);
        et_country = inflater.findViewById(R.id.et_country);
        ic_delete = inflater.findViewById(R.id.ic_delete);
        ic_edit = inflater.findViewById(R.id.ic_edit);
        addbranchaddress = inflater.findViewById(R.id.addbranchaddress);

        ic_delete.setVisibility(View.GONE);
        ic_edit.setVisibility(View.GONE);
        addbranchaddress.setVisibility(View.GONE);

        et_street.setText(requestList.getArea());
        et_city.setText(requestList.getCity());
        et_province.setText(requestList.getProvince());
        et_country.setText(requestList.getCountry());

    }

    private void addedAddress(int size, GetShopDetailResponse.RequestList requestList) {
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                View inflater = getLayoutInflater().inflate(R.layout.add_address_layout, null);
                addressRL.addView(inflater);

                et_street = inflater.findViewById(R.id.et_street);
                et_city = inflater.findViewById(R.id.et_city);
                et_province = inflater.findViewById(R.id.et_province);
                et_country = inflater.findViewById(R.id.et_country);
                ic_delete = inflater.findViewById(R.id.ic_delete);
                ic_edit = inflater.findViewById(R.id.ic_edit);
                addbranchaddress = inflater.findViewById(R.id.addbranchaddress);

                et_street.setText(requestList.getBranchAddress().get(i).getLine1());
                et_city.setText(requestList.getBranchAddress().get(i).getCity());
                et_province.setText(requestList.getBranchAddress().get(i).getProvince());
                et_country.setText(requestList.getBranchAddress().get(i).getCountry());

                addbranchaddress.setVisibility(View.INVISIBLE);

                //delete address
                ic_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Delete Address...")
                                .setMessage("Are you sure you want to delete this branch address?")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        DeleteBranchAddress deleteBranchAddress = new DeleteBranchAddress();
                                        deleteBranchAddress.setId(branchIds.get(addressRL.indexOfChild(inflater) - 2));
                                        deleteBranchAddress.setProfileId(Constant.PROFILE_ID);

                                        storeSettingViewModel.deleteBranchAddress(deleteBranchAddress, getActivity());
                                        storeSettingViewModel.getDeleteBranchAddress().observe(getActivity(), new Observer<DeleteBranchAddressResponse>() {
                                            @Override
                                            public void onChanged(DeleteBranchAddressResponse deleteBranchAddressResponse) {
                                                if (deleteBranchAddressResponse != null) {
                                                    navController.navigate(R.id.storeSettingFragment);
                                                    //Toast.makeText(getActivity(), " " + deleteBranchAddressResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton("No", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                });

                ic_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateBranchAddress updateBranchAddress = new UpdateBranchAddress();
                        updateBranchAddress.setProfileId(Constant.PROFILE_ID);
                        updateBranchAddress.setCity(et_city.getText().toString());
                        updateBranchAddress.setProvince(et_province.getText().toString());
                        updateBranchAddress.setCountry(et_country.getText().toString());
                        updateBranchAddress.setLine1(et_street.getText().toString());
                        updateBranchAddress.setId(branchIds.get(addressRL.indexOfChild(inflater) - 2));

                        if (TextUtils.isEmpty(et_street.getText().toString()) ||
                                TextUtils.isEmpty(et_city.getText().toString()) ||
                                TextUtils.isEmpty(et_province.getText().toString()) ||
                                TextUtils.isEmpty(et_country.getText().toString())) {
                            Toast.makeText(getActivity(), "Some Fields are missing...", Toast.LENGTH_SHORT).show();
                        } else {
                            storeSettingViewModel.updateBranchAddress(updateBranchAddress, getActivity());
                            storeSettingViewModel.getUpdateBranchAddress().observe(getActivity(), new Observer<UpdateBranchAddressResponse>() {
                                @Override
                                public void onChanged(UpdateBranchAddressResponse updateBranchAddressResponse) {
                                    if (updateBranchAddressResponse != null) {
                                        if (updateBranchAddressResponse.getStatus().equals("0")) {
                                            Toast.makeText(getActivity(), "Shop Address Has Been Updated Successfully...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });

            }

        }
    }

    private void initViews(View view) {

        //et_country = view.findViewById(R.id.et_country);
        addressRL = view.findViewById(R.id.addressRL);
        addnewaddress = view.findViewById(R.id.addnewaddress);
        addedAddress = view.findViewById(R.id.addedAddress);
        et_shop_url = view.findViewById(R.id.et_shop_url);
        registeredShopNumber = view.findViewById(R.id.registeredShopNumber);
        iv_top_image = view.findViewById(R.id.iv_top_image);
    }
}
