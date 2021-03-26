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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tilismtech.tellotalk_shopping_sdk.R;

public class StoreSettingFragment extends Fragment {

    private ImageView ic_delete, ic_edit, addnewaddress;
    private EditText et_street, et_city, et_province, et_country;
    private LinearLayout addressRL;
    private RelativeLayout addedAddress;

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
        addnewaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflater = getLayoutInflater().inflate(R.layout.add_address_layout, null);
                addressRL.addView(inflater);

                ic_delete = inflater.findViewById(R.id.ic_delete);
                ic_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addressRL.removeView(inflater);
                    }
                });
            }
        });
    }

    private void initViews(View view) {
        et_street = view.findViewById(R.id.et_street);
        et_city = view.findViewById(R.id.et_city);
        et_province = view.findViewById(R.id.et_province);
        et_country = view.findViewById(R.id.et_country);
        ic_delete = view.findViewById(R.id.ic_delete);
        ic_edit = view.findViewById(R.id.ic_edit);
        et_country = view.findViewById(R.id.et_country);
        addressRL = view.findViewById(R.id.addressRL);
        addnewaddress = view.findViewById(R.id.addnewaddress);
        addedAddress = view.findViewById(R.id.addedAddress);
    }
}
