package com.tilismtech.tellotalk_shopping_sdk_app.ui_client.homescreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.tabs.TabLayout;
import com.tilismtech.tellotalk_shopping_sdk_app.R;

public class ClientHomeFragment extends Fragment {

    com.google.android.material.tabs.TabLayout tabLayout;
    NavController navController;
    NavHostFragment navHostFragment;

    public ClientHomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tabLayout);

        navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
    //    navController.navigate(R.id.subCategoryFragment);

      //  navController = Navigation.findNavController(view);

        tabLayout.addTab(tabLayout.newTab().setText("Deals"));
        tabLayout.addTab(tabLayout.newTab().setText("HardDisk"));
        tabLayout.addTab(tabLayout.newTab().setText("Laptop"));
        tabLayout.addTab(tabLayout.newTab().setText("Gaming PC"));
        tabLayout.addTab(tabLayout.newTab().setText("ProBook"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    Toast.makeText(getActivity(), "da", Toast.LENGTH_SHORT).show();
                  //  navController.navigate(R.id.nav_graph_client_sub_categories);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
