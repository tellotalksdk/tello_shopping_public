package com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.AccessTokenPojo;

public class ShopRegistrationActivity extends AppCompatActivity {
    NavHostFragment navHostFragment;
    private ShopRegistrationViewModel shopRegistrationViewModel;
    AccessTokenPojo accessTokenPojo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_registration);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        accessTokenPojo = new AccessTokenPojo();
        accessTokenPojo.setUsername("Basit@tilismtech.com");
        accessTokenPojo.setPassword("basit@1234");
        accessTokenPojo.setGrant_type("password");
        accessTokenPojo.setprofileId("3F64D77CB1BA4A3CA6CF9B9D786D4A43");
        accessTokenPojo.setFirstname("Hasan");
        accessTokenPojo.setMiddlename("Muddassir");
        accessTokenPojo.setLastname("Naqvi");
        accessTokenPojo.setPhone("03330347473");
        accessTokenPojo.setEmail("emai@gmail.com");

        /*shopRegistrationViewModel = new ViewModelProvider(this).get(ShopRegistrationViewModel.class);

        shopRegistrationViewModel.postGenerateToken(accessTokenPojo);

        shopRegistrationViewModel.getGenerateToken().observe(this, new Observer<GenerateTokenResponse>() {
            @Override
            public void onChanged(GenerateTokenResponse generateTokenResponse) {
                if (generateTokenResponse != null) {
                    Toast.makeText(ShopRegistrationActivity.this, "" + generateTokenResponse.getAccessToken(), Toast.LENGTH_SHORT).show();
                    TelloPreferenceManager.getInstance().saveAccessToken(generateTokenResponse.getAccessToken());
                } else {
                    Toast.makeText(ShopRegistrationActivity.this, " Null ", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }
}