package com.tilismtech.tellotalk_shopping_sdk.gallery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.gallery.Fragments.OneFragment;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {
    public static int selectionTitle, maxSelection, mode;
    public static String title;
    public Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);
       /* toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_sdk);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());*/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> returnResult());
        title = getIntent().getStringExtra("title");
        maxSelection = getIntent().getIntExtra("maxSelection", 5);
        if (maxSelection == 0) maxSelection = Integer.MAX_VALUE;
        mode = getIntent().getIntExtra("mode", 1);
        getSupportActionBar().setTitle(title);
        selectionTitle = 0;
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        OpenGallery.selected.clear();
        OpenGallery.imagesSelected.clear();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (selectionTitle > 0) {
            //toolbar.setTitle(String.valueOf(selectionTitle) + " Selected");
        }
    }

    //This method set up the tab view for images and videos
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (mode == 1 || mode == 2) {
            adapter.addFragment(new OneFragment(), "Images");
        }
      /*  if (mode == 1 || mode == 3)
            adapter.addFragment(new TwoFragment(), "Videos");*/
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void returnResult() {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("result", OpenGallery.imagesSelected);
        returnIntent.putExtras(bundle);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}