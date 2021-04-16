package com.tilismtech.tellotalk_shopping_sdk.ui.shopsetting;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAssignedNumbers;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.TelloApplication;
import com.tilismtech.tellotalk_shopping_sdk.adapters.ColorChooserAdapter;
import com.tilismtech.tellotalk_shopping_sdk.adapters.TimingnAdapter;
import com.tilismtech.tellotalk_shopping_sdk.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk.pojos.ColorChooserPojo;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetTimings;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopTiming;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetTimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.ShopTimingResponse;
import com.tilismtech.tellotalk_shopping_sdk.ui.shoplandingpage.ShopLandingActivity;
import com.tilismtech.tellotalk_shopping_sdk.ui.shopregistration.ShopRegistrationViewModel;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.cert.CertPathBuilderSpi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.tilismtech.tellotalk_shopping_sdk.api.RetrofitClient.getRetrofitClient;

public class ShopSettingFragment extends Fragment implements ColorChooserAdapter.OnColorChooserListener {

    private final static int UPLOAD_IMAGE = 123;
    private final static int CAPTURE_IMAGE = 456;
    private FrameLayout FL1;
    private RecyclerView recycler_timings;
    private TimingnAdapter timingnAdapter;
    private ProgressBar progressBar;
    private Button saveAccountbtn, upload, capture, updateTimingbtn;
    private NavController navController;
    private EditText area, et_OwnerName, et_OwnerNumber, et_OwnerShopUrl;
    private ImageView iv_websitetheme, iv_back, bannerImage, clr_choose, iv_tim, loader;
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
    private boolean isMondayOpen, isTuedayOpen, isWednesdayOpen, isThrusdayOpen, isFridayOpen, isSaturdayOpen, isSundayOpen;
    private int isMondayOpen_ID, isTuedayOpen_ID, isWednesdayOpen_ID, isThrusdayOpen_ID, isFridayOpen_ID, isSaturdayOpen_ID, isSundayOpen_ID;
    private Switch mondaySwitch, tuesdaySwitch, wednesdaySwitch, thrusdaySwitch, fridaySwitch, saturdaySwitch, sundaySwitch;
    private ShopSettingViewModel shopSettingViewModel;
    private String filePath = "", Country, Province, City; //this file path either come from capture image or upload image
    private ShopTiming shopTiming; // request body for shop timing dialog and its related api...
    private List<ShopTiming.DaysSetting> daysSettingList;
    public ShopTiming.DaysSetting mondaySetting, tuesdaySetting, wednesdaySetting, thrusdaySetting, fridaySetting, saturdaySetting, sundaySetting;

    ShopBasicSetting shopBasicSetting;

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
        initViews(view);
        activity = getActivity();
        shopSettingViewModel = new ViewModelProvider(this).get(ShopSettingViewModel.class);

        et_OwnerName.setText(TelloPreferenceManager.getInstance(getActivity()).getOwnerName());
        et_OwnerNumber.setText(TelloPreferenceManager.getInstance(getActivity()).getRegisteredNumber());
        et_OwnerShopUrl.setText(TelloPreferenceManager.getInstance(getActivity()).getShopUri());


        //by default timings to show inside recycler view...
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.showDialog();
        GetTimings getTimings = new GetTimings();
        getTimings.setProfileId(Constant.PROFILE_ID);
        getTimings.setShopId("7");
        shopSettingViewModel.posttogetTimings(getTimings);
        shopSettingViewModel.gettimings().observe(getActivity(), new Observer<GetTimingsResponse>() {
            @Override
            public void onChanged(GetTimingsResponse getTimingsResponse) {
                if (getTimingsResponse != null) {
                    timingnAdapter = new TimingnAdapter(getActivity(), getTimingsResponse.getData().getRequestList());
                    recycler_timings.setAdapter(timingnAdapter);
                    timingsID = new ArrayList<>();
                    for (int i = 0; i < getTimingsResponse.getData().getRequestList().size(); i++) {
                        timingsID.add(getTimingsResponse.getData().getRequestList().get(i).getSettingId());
                    }

                    loadingDialog.dismissDialog();
                }
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage = new Dialog(getActivity());
                dialogImage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogImage.setContentView(R.layout.dialog_upload_capture);

                upload = dialogImage.findViewById(R.id.uploadImage);
                capture = dialogImage.findViewById(R.id.captureImage);

                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) +
                                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {

                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Grant those permissions...");
                                builder.setMessage("Camera External Storage Permission Required...");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        requestPermissions(new String[]{
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA
                                        }, 1);

                                    }
                                });

                                builder.setNegativeButton("Cancel", null);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else { //ye lines for the very first time chalein gy jab app start hongy
                                requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA
                                }, 1);
                            }
                        } else {
                            dialogImage.dismiss();
                            openGallery();
                        }
                    }
                });
                capture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) +
                                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {

                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Grant those permissions...");
                                builder.setMessage("Camera External Storage Permission Required...");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        requestPermissions(new String[]{
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA
                                        }, 2);

                                    }
                                });

                                builder.setNegativeButton("Cancel", null);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else { //ye lines for the very first time chalein gy jab app start hongy
                                requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA
                                }, 2);
                            }
                        } else {
                            dialogImage.dismiss();
                            openCamera();
                        }
                    }
                });

                Window window = dialogImage.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                window.setAttributes(wlp);
                dialogImage.show();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        iv_tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_shop_settings);

                updateTimingbtn = dialog.findViewById(R.id.updateTimingbtn);

                monday_OpenAt = dialog.findViewById(R.id.monday_open_at);
                tuesday_OpenAt = dialog.findViewById(R.id.tuesday_open_at);
                wednesday_OpenAt = dialog.findViewById(R.id.wednesday_open_at);
                thrusday_OpenAt = dialog.findViewById(R.id.thrusday_open_at);
                friday_OpenAt = dialog.findViewById(R.id.friday_open_at);
                saturday_OpenAt = dialog.findViewById(R.id.saturday_open_at);
                sunday_OpenAt = dialog.findViewById(R.id.sunday_open_at);
                monday_CloseAt = dialog.findViewById(R.id.monday_closed_at);
                tuesday_CloseAt = dialog.findViewById(R.id.tuesday_closed_at);
                wednesday_CloseAt = dialog.findViewById(R.id.wednesday_closed_at);
                thrusday_CloseAt = dialog.findViewById(R.id.thrusday_closed_at);
                friday_CloseAt = dialog.findViewById(R.id.friday_closed_at);
                saturday_CloseAt = dialog.findViewById(R.id.saturday_closed_at);
                sunday_CloseAt = dialog.findViewById(R.id.sunday_closed_at);

                mondaySwitch = dialog.findViewById(R.id.monday_toggle);
                tuesdaySwitch = dialog.findViewById(R.id.tuesday_toggle);
                wednesdaySwitch = dialog.findViewById(R.id.wednesday_toggle);
                thrusdaySwitch = dialog.findViewById(R.id.thrusday_toggle);
                fridaySwitch = dialog.findViewById(R.id.friday_toggle);
                saturdaySwitch = dialog.findViewById(R.id.saturday_toggle);
                sundaySwitch = dialog.findViewById(R.id.sunday_toggle);

                mondaySwitch.setOnCheckedChangeListener(onCheckedChangeListener);
                tuesdaySwitch.setOnCheckedChangeListener(onCheckedChangeListener);
                wednesdaySwitch.setOnCheckedChangeListener(onCheckedChangeListener);
                thrusdaySwitch.setOnCheckedChangeListener(onCheckedChangeListener);
                fridaySwitch.setOnCheckedChangeListener(onCheckedChangeListener);
                saturdaySwitch.setOnCheckedChangeListener(onCheckedChangeListener);
                sundaySwitch.setOnCheckedChangeListener(onCheckedChangeListener);

                ArrayAdapter<String> openTimingAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, getActivity().getResources().getStringArray(R.array.opens_at));
                openTimingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                monday_OpenAt.setAdapter(openTimingAdapter);
                tuesday_OpenAt.setAdapter(openTimingAdapter);
                wednesday_OpenAt.setAdapter(openTimingAdapter);
                thrusday_OpenAt.setAdapter(openTimingAdapter);
                friday_OpenAt.setAdapter(openTimingAdapter);
                saturday_OpenAt.setAdapter(openTimingAdapter);
                sunday_OpenAt.setAdapter(openTimingAdapter);

                ArrayAdapter<String> closeTimingAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, getActivity().getResources().getStringArray(R.array.close_at));
                closeTimingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                monday_CloseAt.setAdapter(closeTimingAdapter);
                tuesday_CloseAt.setAdapter(closeTimingAdapter);
                wednesday_CloseAt.setAdapter(closeTimingAdapter);
                thrusday_CloseAt.setAdapter(closeTimingAdapter);
                friday_CloseAt.setAdapter(closeTimingAdapter);
                saturday_CloseAt.setAdapter(closeTimingAdapter);
                sunday_CloseAt.setAdapter(closeTimingAdapter);

                /*monday_CloseAt.setEnabled(false);
                tuesday_CloseAt.setEnabled(false);
                wednesday_CloseAt.setEnabled(false);
                thrusday_CloseAt.setEnabled(false);
                friday_CloseAt.setEnabled(false);
                saturday_CloseAt.setEnabled(false);
                sunday_CloseAt.setEnabled(false);*/


                monday_OpenAt.setOnItemSelectedListener(onItemSelectedListener);
                monday_CloseAt.setOnItemSelectedListener(onItemSelectedListener);

                tuesday_OpenAt.setOnItemSelectedListener(onItemSelectedListener);
                tuesday_CloseAt.setOnItemSelectedListener(onItemSelectedListener);

                wednesday_OpenAt.setOnItemSelectedListener(onItemSelectedListener);
                wednesday_CloseAt.setOnItemSelectedListener(onItemSelectedListener);

                thrusday_OpenAt.setOnItemSelectedListener(onItemSelectedListener);
                thrusday_CloseAt.setOnItemSelectedListener(onItemSelectedListener);

                friday_OpenAt.setOnItemSelectedListener(onItemSelectedListener);
                friday_CloseAt.setOnItemSelectedListener(onItemSelectedListener);

                saturday_OpenAt.setOnItemSelectedListener(onItemSelectedListener);
                saturday_CloseAt.setOnItemSelectedListener(onItemSelectedListener);

                sunday_OpenAt.setOnItemSelectedListener(onItemSelectedListener);
                sunday_CloseAt.setOnItemSelectedListener(onItemSelectedListener);

                outerRL = dialog.findViewById(R.id.outerRL);
                outerRL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                updateTimingbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "clicked...", Toast.LENGTH_SHORT).show();

                        daysSettingList.add(0, mondaySetting);
                        daysSettingList.add(1, tuesdaySetting);
                        daysSettingList.add(2, wednesdaySetting);
                        daysSettingList.add(3, thrusdaySetting);
                        daysSettingList.add(4, fridaySetting);
                        daysSettingList.add(5, saturdaySetting);
                        daysSettingList.add(6, sundaySetting);

                        shopTiming.setProfileId(Constant.PROFILE_ID);
                        shopTiming.setDaysSetting(daysSettingList);

                        shopSettingViewModel.postTiming(shopTiming);
                        shopSettingViewModel.getUpdateTiming().observe(getActivity(), new Observer<ShopTimingResponse>() {
                            @Override
                            public void onChanged(ShopTimingResponse shopTimingResponse) {
                                if (shopTimingResponse != null) {
                                    Toast.makeText(getActivity(), " " + shopTimingResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getActivity(), " " + shopTimingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

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

        colortheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dialog dialog = new Dialog(getActivity(), R.style.DialogSlideAnim);
                Dialog dialog = new Dialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_setting_color);

                Button btnConfirmColor = dialog.findViewById(R.id.confirmColor);
                btnConfirmColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

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
                if (!TextUtils.isEmpty(area.getText().toString()) &&
                        !TextUtils.isEmpty(Country) &&
                        !TextUtils.isEmpty(Province) &&
                        !TextUtils.isEmpty(City) &&
                        !TextUtils.isEmpty(filePath.toString())
                ) {

                    shopBasicSetting.setProfileId(Constant.PROFILE_ID); //47 0for testing
                    shopBasicSetting.setShop_Theme("#e31616");
                    // shopBasicSetting.setShopProfile(imageUri); //image
                    shopBasicSetting.setShopProfile(filePath); //image
                    shopBasicSetting.setTax("0");
                    shopBasicSetting.setShippingFee("0");
                    shopBasicSetting.setCountry(Country);
                    shopBasicSetting.setProvince(Province);
                    shopBasicSetting.setCity(City);
                    shopBasicSetting.setArea(area.getText().toString());

                    shopSettingViewModel.postShopSettingDetails(shopBasicSetting, getActivity());
                    LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                    loadingDialog.showDialog();
                    // progressBar.setVisibility(View.VISIBLE);
                    shopSettingViewModel.getShopSettingResponse().observe(getActivity(), new Observer<ShopBasicSettingResponse>() {
                        @Override
                        public void onChanged(ShopBasicSettingResponse shopBasicSettingResponse) {
                            if (shopBasicSettingResponse != null) {
                                //Toast.makeText(activity, "Hurray ... Your Shop has been created successfully" + shopBasicSettingResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(activity, "You Shop Has Been Set Successfully...", Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                                loadingDialog.dismissDialog();
                                getActivity().finish();
                                startActivity(new Intent(getActivity(), ShopLandingActivity.class));

                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(activity, "Some thing went wrong try again....", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(activity, "Some fields are missing...", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getActivity(), ShopLandingActivity.class));
                }
                // startActivity(new Intent(getActivity(), ShopLandingActivity.class));
            }
        });


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
        shopBasicSetting = new ShopBasicSetting();
        shopTiming = new ShopTiming();
        shopTiming.setProfileId(Constant.PROFILE_ID);
        daysSettingList = new ArrayList<>();

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


        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, getActivity().getResources().getStringArray(R.array.countries));
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
        country.setAdapter(countryAdapter);

        country.setOnItemSelectedListener(onItemSelectedListenerAddress);

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, getActivity().getResources().getStringArray(R.array.province));
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
        province.setAdapter(provinceAdapter);

        province.setOnItemSelectedListener(onItemSelectedListenerAddress);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, getActivity().getResources().getStringArray(R.array.city));
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
        city.setAdapter(cityAdapter);

        city.setOnItemSelectedListener(onItemSelectedListenerAddress);


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

        clr_choose.setVisibility(View.VISIBLE);
        setColortext.setText("");
    }

    public ColorChooserAdapter.OnColorChooserListener getReference() {
        return this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: //pick and set image from gallery
                if (grantResults.length > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    dialogImage.dismiss();
                    openGallery();
                }
                break;
            case 2: //capture image and set from camera
                if (grantResults.length > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    dialogImage.dismiss();
                    openCamera();
                }
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, UPLOAD_IMAGE);
    }

    private void openCamera() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, CAPTURE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == UPLOAD_IMAGE) { //Upload image from gallery
            imageUri = data.getData();
            bannerImage.setImageURI(imageUri);
            filePath = getPath(getActivity(), imageUri);
            Log.i("TAG", "onActivityResult: Gallery Upload Path" + filePath);
        } else if (resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            bannerImage.setImageBitmap(photo);
            imageUri = getImageUri(getActivity(), photo);
            filePath = getRealPathFromURI(imageUri);
            Log.i("TAG", "onActivityResult: Capture Capture Path" + filePath);
        }
    }

    //for spinner selection...
    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            //logic for monday timing
            if (parent.getId() == monday_OpenAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                isMondayOpen = true;

                isMondayOpen_ID = (int) parent.getItemIdAtPosition(position);
                Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                mondaySetting.setSettingId(timingsID.get(0));
                mondaySetting.setShopDayName("Monday");
                mondaySetting.setShopStartTime(parent.getItemAtPosition(position).toString());
                // mondaySetting.setShopEndTime(22);
                //   mondaySetting.setShopStatusDaywise(22);
            }

            if (parent.getId() == monday_CloseAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                if (isMondayOpen) {
                    if (parent.getItemIdAtPosition(position) > isMondayOpen_ID) {
                        Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        mondaySetting.setShopEndTime(parent.getItemAtPosition(position).toString());
                    } else {
                        Toast.makeText(activity, "Shop Closing time must be ahead of shop opening time...", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(activity, "Select Shop Opening Timing First...", Toast.LENGTH_SHORT).show();
                    monday_CloseAt.setSelection(0);
                }
            }

            //logic for tuesday timings
            if (parent.getId() == tuesday_OpenAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                isTuedayOpen = true;

                isTuedayOpen_ID = (int) parent.getItemIdAtPosition(position);
                Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                tuesdaySetting.setSettingId(timingsID.get(1));
                tuesdaySetting.setShopDayName("Tuesday");
                tuesdaySetting.setShopStartTime(parent.getItemAtPosition(position).toString());

            }

            if (parent.getId() == tuesday_CloseAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                if (isTuedayOpen) {
                    if (parent.getItemIdAtPosition(position) > isTuedayOpen_ID) {
                        Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        tuesdaySetting.setShopEndTime(parent.getItemAtPosition(position).toString());
                    } else {
                        Toast.makeText(activity, "Shop Closing time must be ahead of shop opening time...", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(activity, "Select Shop Opening Timing First...", Toast.LENGTH_SHORT).show();
                    tuesday_CloseAt.setSelection(0);
                }
            }

            //logic for wednesday timings
            if (parent.getId() == wednesday_OpenAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                isWednesdayOpen = true;

                isWednesdayOpen_ID = (int) parent.getItemIdAtPosition(position);
                Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();


                wednesdaySetting.setSettingId(timingsID.get(2));
                wednesdaySetting.setShopDayName("Wednesday");
                wednesdaySetting.setShopStartTime(parent.getItemAtPosition(position).toString());
            }

            if (parent.getId() == wednesday_CloseAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                if (isWednesdayOpen) {
                    if (parent.getItemIdAtPosition(position) > isWednesdayOpen_ID) {
                        Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        wednesdaySetting.setShopEndTime(parent.getItemAtPosition(position).toString());
                    } else {
                        Toast.makeText(activity, "Shop Closing time must be ahead of shop opening time...", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(activity, "Select Shop Opening Timing First...", Toast.LENGTH_SHORT).show();
                    wednesday_CloseAt.setSelection(0);
                }
            }

            //logic for thrusday timings
            if (parent.getId() == thrusday_OpenAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }


                isThrusdayOpen = true;


                isThrusdayOpen_ID = (int) parent.getItemIdAtPosition(position);
                Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                thrusdaySetting.setSettingId(timingsID.get(3));
                thrusdaySetting.setShopDayName("Thursday");
                thrusdaySetting.setShopStartTime(parent.getItemAtPosition(position).toString());
            }

            if (parent.getId() == thrusday_CloseAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                if (isThrusdayOpen) {
                    if (parent.getItemIdAtPosition(position) > isThrusdayOpen_ID) {
                        Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        thrusdaySetting.setShopEndTime(parent.getItemAtPosition(position).toString());
                    } else {
                        Toast.makeText(activity, "Shop Closing time must be ahead of shop opening time...", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(activity, "Select Shop Opening Timing First...", Toast.LENGTH_SHORT).show();
                    thrusday_CloseAt.setSelection(0);
                }
            }

            //logic for friday timings
            if (parent.getId() == friday_OpenAt.getId()) {

                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }
                isFridayOpen = true;
                isFridayOpen_ID = (int) parent.getItemIdAtPosition(position);
                Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                fridaySetting.setSettingId(timingsID.get(4));
                fridaySetting.setShopDayName("Friday");
                fridaySetting.setShopStartTime(parent.getItemAtPosition(position).toString());
            }

            if (parent.getId() == friday_CloseAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                if (isFridayOpen) {
                    if (parent.getItemIdAtPosition(position) > isFridayOpen_ID) {
                        Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        fridaySetting.setShopEndTime(parent.getItemAtPosition(position).toString());
                    } else {
                        Toast.makeText(activity, "Shop Closing time must be ahead of shop opening time...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activity, "Select Shop Opening Timing First...", Toast.LENGTH_SHORT).show();
                    friday_CloseAt.setSelection(0);
                }
            }

            //logic for saturday timings
            if (parent.getId() == saturday_OpenAt.getId()) {

                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }
                isSaturdayOpen = true;
                isSaturdayOpen_ID = (int) parent.getItemIdAtPosition(position);
                Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                saturdaySetting.setSettingId(timingsID.get(5));
                saturdaySetting.setShopDayName("Saturday");
                saturdaySetting.setShopStartTime(parent.getItemAtPosition(position).toString());
            }

            if (parent.getId() == saturday_CloseAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                if (isSaturdayOpen) {
                    if (parent.getItemIdAtPosition(position) > isSaturdayOpen_ID) {
                        Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        saturdaySetting.setShopEndTime(parent.getItemAtPosition(position).toString());
                    } else {
                        Toast.makeText(activity, "Shop Closing time must be ahead of shop opening time...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activity, "Select Shop Opening Timing First...", Toast.LENGTH_SHORT).show();
                    saturday_CloseAt.setSelection(0);
                }
            }

            //logic for sunday timings
            if (parent.getId() == sunday_OpenAt.getId()) {

                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }
                isSundayOpen = true;
                isSundayOpen_ID = (int) parent.getItemIdAtPosition(position);
                Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                sundaySetting.setSettingId(timingsID.get(6));
                sundaySetting.setShopDayName("Sunday");
                sundaySetting.setShopStartTime(parent.getItemAtPosition(position).toString());
            }

            if (parent.getId() == sunday_CloseAt.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                if (isSundayOpen) {
                    if (parent.getItemIdAtPosition(position) > isSundayOpen_ID) {
                        Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "Shop Closing time must be ahead of shop opening time...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activity, "Select Shop Opening Timing First...", Toast.LENGTH_SHORT).show();
                    sunday_CloseAt.setSelection(0);
                }
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener onItemSelectedListenerAddress = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //spinner for country
            if (parent.getId() == country.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }
                Country = (String) parent.getItemAtPosition(position);
                //shopBasicSetting.setCountry((String) parent.getItemAtPosition(position));
            }

            //spinner for province
            if (parent.getId() == province.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                Province = (String) parent.getItemAtPosition(position);
                // shopBasicSetting.setProvince((String) parent.getItemAtPosition(position));

            }

            //spinner for city

            if (parent.getId() == city.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }
                City = (String) parent.getItemAtPosition(position);
                // shopBasicSetting.setCity((String) parent.getItemAtPosition(position));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //for switch button on timings dialog...
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == mondaySwitch.getId()) {
                mondaySwitch.setChecked(isChecked);
                mondaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == tuesdaySwitch.getId()) {
                tuesdaySwitch.setChecked(isChecked);
                tuesdaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == wednesdaySwitch.getId()) {
                wednesdaySwitch.setChecked(isChecked);
                wednesdaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == thrusdaySwitch.getId()) {
                thrusdaySwitch.setChecked(isChecked);
                thrusdaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == fridaySwitch.getId()) {
                fridaySwitch.setChecked(isChecked);
                fridaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == saturdaySwitch.getId()) {
                saturdaySwitch.setChecked(isChecked);
                saturdaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == sundaySwitch.getId()) {
                sundaySwitch.setChecked(isChecked);
                sundaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }
        }
    };


    //This method return file path when we choose image from gallery
    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    //this method is used to get image uri , after capturing image from camera
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //this method is used to get image path when user capture image from camera
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


}
