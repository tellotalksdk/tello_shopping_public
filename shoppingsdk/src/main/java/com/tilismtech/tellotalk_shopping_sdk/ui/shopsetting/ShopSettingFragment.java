package com.tilismtech.tellotalk_shopping_sdk.ui.shopsetting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ColorChooserAdapter;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ColorChooserPojo;
import com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage.ShopLandingActivity;

import java.util.ArrayList;
import java.util.List;

public class ShopSettingFragment extends Fragment implements ColorChooserAdapter.OnColorChooserListener {

    Button saveAccountbtn;
    NavController navController;
    ImageView iv_timings, iv_websitetheme, iv_back;
    Spinner province, city, area;
    RelativeLayout outerRL;
    RecyclerView recycler_colors;
    ColorChooserAdapter colorChooserAdapter;
    ColorChooserAdapter.OnColorChooserListener onColorChooserListener;
    List<ColorChooserPojo> colorList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        saveAccountbtn = view.findViewById(R.id.saveAccountbtn);
        iv_timings = view.findViewById(R.id.iv_timings);
        iv_websitetheme = view.findViewById(R.id.iv_websitetheme);
        province = view.findViewById(R.id.province);
        city = view.findViewById(R.id.city);
        area = view.findViewById(R.id.area);
        iv_back = view.findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        saveAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navController.navigate;
            }
        });


        iv_timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_shop_settings);
                outerRL = dialog.findViewById(R.id.outerRL);

                outerRL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                wlp.gravity = Gravity.BOTTOM;
                // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);

                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

        iv_websitetheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dialog dialog = new Dialog(getActivity(), R.style.DialogSlideAnim);
                Dialog dialog = new Dialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_setting_color);

                colorList = new ArrayList<>();
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));
                colorList.add(new ColorChooserPojo(R.drawable.circle, false));


                recycler_colors = dialog.findViewById(R.id.recycler_colors);
                colorChooserAdapter = new ColorChooserAdapter(colorList, getActivity(), getReference());

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6, LinearLayoutManager.VERTICAL, false);
                recycler_colors.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                recycler_colors.setAdapter(colorChooserAdapter);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                wlp.gravity = Gravity.BOTTOM;
                // wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);

                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

        saveAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShopLandingActivity.class));
                //navController.navigate(R.id.action_shopSettingFragment_to_shopLandingFragment);
            }
        });

    }

    @Override
    public void onColorClick(int position, ImageView circles) {
        //Toast.makeText(getActivity(), " position : " + position, Toast.LENGTH_SHORT).show();

        colorList.get(position).setSelected(true);

        for (int i = 0; i < colorList.size(); i++) {
            if (i == position) {
                colorList.get(i).setSelected(true);
                continue;
            }
            colorList.get(i).setSelected(false);
        }

        colorChooserAdapter.notifyDataSetChanged();


    }

    public ColorChooserAdapter.OnColorChooserListener getReference() {
        return this;
    }
}
