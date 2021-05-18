package com.tilismtech.tellotalk_shopping_sdk.ui_seller.settingprofileediting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tilismtech.tellotalk_shopping_sdk.R;

public class EditingProfileFragment extends Fragment {
    private ImageView ic_delete , ic_edit ;
    private EditText et_street , et_city , et_province , et_country;
    private RelativeLayout addressRL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_setting_profile_info,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);


        ic_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressRL.setVisibility(View.GONE);
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
    }


}
