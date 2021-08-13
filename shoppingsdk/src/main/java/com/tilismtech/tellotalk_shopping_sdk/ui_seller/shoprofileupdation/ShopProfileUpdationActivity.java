package com.tilismtech.tellotalk_shopping_sdk.ui_seller.shoprofileupdation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ColorChooserAdapter;
import com.tilismtech.tellotalk_shopping_sdk.adapters.TimingnAdapter;
import com.tilismtech.tellotalk_shopping_sdk.customviews.CustomMapView;
import com.tilismtech.tellotalk_shopping_sdk.pojos.Citiespojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ColorChooserPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.CountriesPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.StatePojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopTiming;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopsetting.ShopSettingViewModel;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ShopProfileUpdationActivity extends AppCompatActivity {


    private final static int UPLOAD_IMAGE = 123;
    private final static int CAPTURE_IMAGE = 456;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private FrameLayout FL1;
    private RecyclerView recycler_timings;
    private TimingnAdapter timingnAdapter;
    private ProgressBar progressBar;
    private Button saveAccountbtn, upload, capture, updateTimingbtn;
    private NavController navController;
    private EditText area, et_OwnerName, et_OwnerNumber, et_OwnerShopUrl;
    private ImageView iv_websitetheme, iv_back, bannerImage, clr_choose, iv_tim, loader, iv_image;
    private Spinner province, city, country;
    private RelativeLayout outerRL;
    private LinearLayout iv_timings;
    private FrameLayout colortheme;
    private CardView iv;
    private RecyclerView recycler_colors;
    private ColorChooserAdapter colorChooserAdapter;
    private ColorChooserAdapter.OnColorChooserListener onColorChooserListener;
    private List<ColorChooserPojo> colorList;
    private List<Integer> timingsID;
    private Dialog dialogImage;
    private Uri imageUri;
    private TextView setColortext;
    private Spinner monday_OpenAt, tuesday_OpenAt, wednesday_OpenAt, thrusday_OpenAt, friday_OpenAt, saturday_OpenAt, sunday_OpenAt;
    private Spinner monday_CloseAt, tuesday_CloseAt, wednesday_CloseAt, thrusday_CloseAt, friday_CloseAt, saturday_CloseAt, sunday_CloseAt;
    public Activity activity;
    private boolean isMondayOpen, isTuedayOpen, isWednesdayOpen, isThrusdayOpen, isFridayOpen, isSaturdayOpen, isSundayOpen, mon, tue, wed, thrus, fri, sat, sun;
    private int isMondayOpen_ID, isTuedayOpen_ID, isWednesdayOpen_ID, isThrusdayOpen_ID, isFridayOpen_ID, isSaturdayOpen_ID, isSundayOpen_ID;
    private Switch mondaySwitch, tuesdaySwitch, wednesdaySwitch, thrusdaySwitch, fridaySwitch, saturdaySwitch, sundaySwitch;
    private ShopSettingViewModel shopSettingViewModel;
    private String filePath = "", Country, Province, City, Latitude, Longitude; //this file path either come from capture image or upload image
    private ShopTiming shopTiming; // request body for shop timing dialog and its related api...
    private List<ShopTiming.DaysSetting> daysSettingList;
    private List<String> ColorList;
    public ShopTiming.DaysSetting mondaySetting, tuesdaySetting, wednesdaySetting, thrusdaySetting, fridaySetting, saturdaySetting, sundaySetting;
    private String colorTheme = "";
    ShopBasicSetting shopBasicSetting;
    private LinearLayout cardView;
    private List<String> Countries, States, Cities;
    private int CountryId, StateId, CityId;
    StatePojo statePojo;
    CountriesPojo countriesPojo;
    Citiespojo citiespojo;
    String mondayOpenTimings, mondayCloseTimings, tuesdayOpenTimings, tuesdayCloseTimings, wednesdayOpenTimings, wednesdayCloseTimings, thrusdayOpenTimings, thrusdayCloseTimings, fridayOpenTimings, fridayCloseTimings, saturdayOpenTimings, saturdayCloseTimings, sundayOpenTimings, sundayCloseTimings;
    ArrayAdapter<String> openTimingAdapter;
    private GoogleMap mMap;
    private CustomMapView mapView;
    LocationManager mLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_profile_updation);
    }

    private void initViews(View view) {

        navController = Navigation.findNavController(view);
        saveAccountbtn = view.findViewById(R.id.saveAccountbtn);
        iv_timings = view.findViewById(R.id.iv_timings);
        iv_websitetheme = view.findViewById(R.id.iv_websitetheme);
        province = view.findViewById(R.id.province);
        country = view.findViewById(R.id.country);
        city = view.findViewById(R.id.city);
        area = view.findViewById(R.id.area);
        iv_back = view.findViewById(R.id.iv_back);
        iv = view.findViewById(R.id.iv);
        bannerImage = view.findViewById(R.id.bannerImage);
        setColortext = view.findViewById(R.id.setColortext);
        clr_choose = view.findViewById(R.id.clr_choose);
        colortheme = view.findViewById(R.id.colortheme);
        progressBar = view.findViewById(R.id.progressNBar);
        recycler_timings = view.findViewById(R.id.recycler_timings);
        FL1 = view.findViewById(R.id.FL1);
        iv_tim = view.findViewById(R.id.iv_tim);
        loader = view.findViewById(R.id.loader);
        et_OwnerName = view.findViewById(R.id.et_OwnerName);
        et_OwnerShopUrl = view.findViewById(R.id.et_OwnerShopUrl);
        et_OwnerNumber = view.findViewById(R.id.et_OwnerNumber);
        iv_image = view.findViewById(R.id.iv_image);
        mapView = (CustomMapView) view.findViewById(R.id.mapView);
        shopBasicSetting = new ShopBasicSetting();
        shopTiming = new ShopTiming();
        shopTiming.setProfileId(Constant.PROFILE_ID);
        daysSettingList = new ArrayList<>();
        ColorList = new ArrayList<>();
        cardView = view.findViewById(R.id.cardColor);
        Countries = new ArrayList<>();
        States = new ArrayList<>();
        Cities = new ArrayList<>();


        mondaySetting = new ShopTiming().new DaysSetting();
        tuesdaySetting = new ShopTiming().new DaysSetting();
        wednesdaySetting = new ShopTiming().new DaysSetting();
        thrusdaySetting = new ShopTiming().new DaysSetting();
        fridaySetting = new ShopTiming().new DaysSetting();
        saturdaySetting = new ShopTiming().new DaysSetting();
        sundaySetting = new ShopTiming().new DaysSetting();

        //initially timings toggle status is not active
        mondaySetting.setShopStatusDaywise("N");
        tuesdaySetting.setShopStatusDaywise("N");
        wednesdaySetting.setShopStatusDaywise("N");
        thrusdaySetting.setShopStatusDaywise("N");
        fridaySetting.setShopStatusDaywise("N");
        saturdaySetting.setShopStatusDaywise("N");
        sundaySetting.setShopStatusDaywise("N");


    }

}