package com.tilismtech.tellotalk_shopping_sdk.ui.storesetting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AddBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.AddBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui.shopregistration.ShopRegistrationViewModel;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

public class StoreSettingFragment extends Fragment {

    private ImageView ic_delete, ic_edit, addnewaddress, addbranchaddress;
    private EditText et_street, et_city, et_province, et_country, et_shop_url;
    private LinearLayout addressRL;
    private RelativeLayout addedAddress;
    StoreSettingViewModel storeSettingViewModel;

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

        storeSettingViewModel = new ViewModelProvider(this).get(StoreSettingViewModel.class);
        GetShopDetail getShopDetail = new GetShopDetail();
        getShopDetail.setProfileId(Constant.PROFILE_ID);
        storeSettingViewModel.postShopDetail(getShopDetail);
        storeSettingViewModel.getShopDetail().observe(getActivity(), new Observer<GetShopDetailResponse>() {
            @Override
            public void onChanged(GetShopDetailResponse getShopDetailResponse) {
                if (getShopDetailResponse != null) {
                    Toast.makeText(getActivity(), "" + getShopDetailResponse.getStatusDetail() + getShopDetailResponse.getData().getRequestList().getBranchAddress().size(), Toast.LENGTH_SHORT).show();
                    et_shop_url.setText(getShopDetailResponse.getData().getRequestList().getShopURl());
                  //  getShopDetailResponse.getData().getRequestList().getBranchAddress().size();
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

                ic_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addressRL.removeView(inflater);
                    }
                });

                addbranchaddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(), "CLICKED", Toast.LENGTH_SHORT).show();

                        AddBranchAddress addBranchAddress = new AddBranchAddress();
                        addBranchAddress.setProfileId(Constant.PROFILE_ID);
                        addBranchAddress.setCity(et_city.getText().toString());
                        addBranchAddress.setProvince(et_province.getText().toString());
                        addBranchAddress.setCountry(et_country.getText().toString());
                        addBranchAddress.setLine1(et_street.getText().toString());

                        storeSettingViewModel.addBranchAddress(addBranchAddress);
                        storeSettingViewModel.getAddBranchAddress().observe(getActivity(), new Observer<AddBranchAddressResponse>() {
                            @Override
                            public void onChanged(AddBranchAddressResponse addBranchAddressResponse) {
                                if (addBranchAddressResponse != null) {
                                    Toast.makeText(getActivity(), "" + addBranchAddressResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                });

            }
        });
    }

    private void initViews(View view) {

        //et_country = view.findViewById(R.id.et_country);
        addressRL = view.findViewById(R.id.addressRL);
        addnewaddress = view.findViewById(R.id.addnewaddress);
        addedAddress = view.findViewById(R.id.addedAddress);
        et_shop_url = view.findViewById(R.id.et_shop_url);
    }
}
