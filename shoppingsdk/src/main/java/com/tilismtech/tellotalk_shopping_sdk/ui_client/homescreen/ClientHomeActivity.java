package com.tilismtech.tellotalk_shopping_sdk.ui_client.homescreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.orderlist.OrderListViewModel;

public class ClientHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavHostFragment navHostFragment;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    androidx.appcompat.widget.Toolbar toolbar;
    ImageView drawerOpenClose;
    NavController navController;
    NavigationView navigationView;
    HomeScreenViewModel homeScreenViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        homeScreenViewModel = new ViewModelProvider(this).get(HomeScreenViewModel.class);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        drawerOpenClose = findViewById(R.id.drawerOpenClose);
        navigationView = findViewById(R.id.nav_view);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        drawerOpenClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //homeScreenViewModel.AllCategoryListUnderShop("9");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.computer) {
            navController.navigate(R.id.clientHomeFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (item.getItemId() == R.id.computer) {

        } else if (item.getItemId() == R.id.tv_smart_box) {

        } else if (item.getItemId() == R.id.camera_photography) {

        } else if (item.getItemId() == R.id.headphones) {

        } else if (item.getItemId() == R.id.musical_instrument) {

        } else if (item.getItemId() == R.id.smart_phone_and_tablets) {

        } else if (item.getItemId() == R.id.accessories) {

        } else if (item.getItemId() == R.id.home_audio_theatre) {

        } else if (item.getItemId() == R.id.faxmachine) {

        } else if (item.getItemId() == R.id.household_goods) {

        } else if (item.getItemId() == R.id.watch) {

        } else if (item.getItemId() == R.id.others) {

        }
        return true;
    }
}