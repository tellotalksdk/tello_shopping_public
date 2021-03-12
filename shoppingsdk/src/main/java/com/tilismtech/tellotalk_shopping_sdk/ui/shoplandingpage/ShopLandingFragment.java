package com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tilismtech.tellotalk_shopping_sdk.R;

public class ShopLandingFragment extends Fragment {

    ImageView setting , open_edit_details;
    Dialog dialog_edit_details;
    RelativeLayout outerRL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_landing_page, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        open_edit_details = view.findViewById(R.id.open_edit_details);
        open_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_edit_details = new Dialog(getActivity());
                dialog_edit_details.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_edit_details.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog_edit_details.setContentView(R.layout.dialog_edit_product);

                outerRL = dialog_edit_details.findViewById(R.id.outerRL);

                Window window = dialog_edit_details.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                window.setAttributes(wlp);
                dialog_edit_details.setCanceledOnTouchOutside(true);
                dialog_edit_details.show();

                outerRL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_edit_details.hide();
                    }
                });
            }
        });
    }


}

