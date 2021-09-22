package com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.storesetting;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tilismtech.tellotalk_shopping_sdk_app.R;
import com.tilismtech.tellotalk_shopping_sdk_app.adapters.TimingnAdapter;
import com.tilismtech.tellotalk_shopping_sdk_app.gallery.Gallery;
import com.tilismtech.tellotalk_shopping_sdk_app.gallery.MediaAttachment;
import com.tilismtech.tellotalk_shopping_sdk_app.managers.TelloPreferenceManager;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.CountriesPojo;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.StatePojo;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.AddBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.DeleteBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.GetTimings;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.ShopTiming;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.UpdateBranchAddress;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.AddBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.CityListResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.DeleteBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetTimingsResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ShopBasicSettingResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.ShopTimingResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.UpdateBranchAddressResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.shopsetting.ShopSettingViewModel;
import com.tilismtech.tellotalk_shopping_sdk_app.utils.ApplicationUtils;
import com.tilismtech.tellotalk_shopping_sdk_app.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk_app.utils.LoadingDialog;
import com.tilismtech.tellotalk_shopping_sdk_app.utils.Utility;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.tilismtech.tellotalk_shopping_sdk_app.api.RetrofitClient.getRetrofitClient;

public class StoreSettingFragment extends Fragment {

    private final static int UPLOAD_IMAGE = 123;
    private final static int CAPTURE_IMAGE = 456;
    private ImageView ic_delete, ic_edit, addnewaddress, addbranchaddress, iv_top_image, iv_image;
    private EditText registeredShopNumber, et_street, et_city, et_province, et_country, et_shop_url;
    private LinearLayout addressRL;
    private RelativeLayout addedAddress;
    private String shopId, addressId;
    private List<String> branchIds; //we need to store branch id to update and delete these ids...
    private StoreSettingViewModel storeSettingViewModel;
    private ShopSettingViewModel shopSettingViewModel;
    private NavController navController;
    private LinearLayout linearAddBranch;
    private RecyclerView recycler_timings;
    private LinearLayout iv_timings;
    private FrameLayout FL1;
    private Activity activity;
    private TimingnAdapter timingnAdapter;
    private String mondayOpenTimings, mondayCloseTimings, tuesdayOpenTimings, tuesdayCloseTimings, wednesdayOpenTimings, wednesdayCloseTimings, thrusdayOpenTimings, thrusdayCloseTimings, fridayOpenTimings, fridayCloseTimings, saturdayOpenTimings, saturdayCloseTimings, sundayOpenTimings, sundayCloseTimings;
    private boolean isMondayOpen, isTuedayOpen, isWednesdayOpen, isThrusdayOpen, isFridayOpen, isSaturdayOpen, isSundayOpen, mon, tue, wed, thrus, fri, sat, sun;
    private List<Integer> timingsID;
    private Spinner monday_OpenAt, tuesday_OpenAt, wednesday_OpenAt, thrusday_OpenAt, friday_OpenAt, saturday_OpenAt, sunday_OpenAt;
    private Spinner monday_CloseAt, tuesday_CloseAt, wednesday_CloseAt, thrusday_CloseAt, friday_CloseAt, saturday_CloseAt, sunday_CloseAt;
    private Switch mondaySwitch, tuesdaySwitch, wednesdaySwitch, thrusdaySwitch, fridaySwitch, saturdaySwitch, sundaySwitch;
    private List<ShopTiming.DaysSetting> daysSettingList;
    private Button updateTimingbtn;
    private ShopTiming.DaysSetting mondaySetting, tuesdaySetting, wednesdaySetting, thrusdaySetting, fridaySetting, saturdaySetting, sundaySetting;
    private int isMondayOpen_ID, isTuedayOpen_ID, isWednesdayOpen_ID, isThrusdayOpen_ID, isFridayOpen_ID, isSaturdayOpen_ID, isSundayOpen_ID;
    private ShopTiming shopTiming; // request body for shop timing dialog and its related api...
    private RelativeLayout outerRL;
    private TextView tvMonday, tvTuesday, tvWednesday, tvThrusday, tvFriday, tvSaturday, tvSunday;
    private CardView uploadImage;
    private Uri imageUri;
    private String filePath = "", Country = "", Province = "", City = "", Street = "", Latitude = "", Longitude = "";
    private List<String> Countries, States, Cities;
    //uCrop
    private boolean lockAspectRatio = false, setBitmapMaxWidthHeight = false;
    private int ASPECT_RATIO_X = 16, ASPECT_RATIO_Y = 9, bitmapMaxWidth = 1000, bitmapMaxHeight = 1000;
    private int IMAGE_COMPRESSION = 80;

    com.toptoche.searchablespinnerlibrary.SearchableSpinner searchableCity, searchableProvince, searchableCountry;
    StatePojo statePojo;
    CountriesPojo countriesPojo;
    private int CountryId, StateId, CityId;

    private ArrayAdapter<String> cityAdapter;

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
        activity = getActivity();
        registeredShopNumber.setText(TelloPreferenceManager.getInstance(getActivity()).getRegisteredNumber());
        registeredShopNumber.setEnabled(false);
        navController = Navigation.findNavController(view);
        storeSettingViewModel = new ViewModelProvider(this).get(StoreSettingViewModel.class);
        shopSettingViewModel = new ViewModelProvider(this).get(ShopSettingViewModel.class);
        branchIds = new ArrayList<>();
        GetShopDetail getShopDetail = new GetShopDetail();
        getShopDetail.setProfileId(Constant.PROFILE_ID);

        showTimings();
        Gson gson = new Gson();

        String countriesFileString = getJsonFromAssets(getActivity(), "countries.json");
        String stateFileString = getJsonFromAssets(getActivity(), "states.json");

        countriesPojo = gson.fromJson(countriesFileString, CountriesPojo.class);
        statePojo = gson.fromJson(stateFileString, StatePojo.class);

        for (int i = 0; i < countriesPojo.getCountries().size(); i++) {
            Countries.add(countriesPojo.getCountries().get(i).getName());
        }

        for (int i = 0; i < statePojo.getStates().size(); i++) {
            States.add(statePojo.getStates().get(i).getName());
        }

/*        getRetrofitClient().getAllCitiesByID(String.valueOf(StateId)).enqueue(new Callback<CityListResponse>() {
            @Override
            public void onResponse(Call<CityListResponse> call, Response<CityListResponse> response) {
                CityListResponse cityListResponse = response.body();

                for (int i = 1; i < cityListResponse.getData().getRequestList().size(); i++) {
                    Cities.add(cityListResponse.getData().getRequestList().get(i).getName());
                }

                cityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, Cities);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                searchableCity.setAdapter(cityAdapter);
                searchableCity.setOnItemSelectedListener(onItemSelectedListenerAddress);
            }

            @Override
            public void onFailure(Call<CityListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });*/


        storeSettingViewModel.postShopDetail(getShopDetail, getActivity());
        storeSettingViewModel.getShopDetail().observe(getActivity(), new Observer<GetShopDetailResponse>() {
            @Override
            public void onChanged(GetShopDetailResponse getShopDetailResponse) {
                if (getShopDetailResponse != null) {
                    // Toast.makeText(getActivity(), "" + getShopDetailResponse.getStatusDetail() + getShopDetailResponse.getData().getRequestList().getBranchAddress().size(), Toast.LENGTH_SHORT).show();
                    et_shop_url.setText(getShopDetailResponse.getData().getRequestList().getShopURl() + ".tellocast.com");
                    shopId = getShopDetailResponse.getData().getRequestList().getShopId();

                    if (getShopDetailResponse.getData().getRequestList().getShopProfile().equals("")) {
                        // iv_top_image.setImageDrawable(getResources().getDrawable(R.drawable.banner_img));
                    } else {
                        Glide.with(getActivity()).load(getShopDetailResponse.getData().getRequestList().getShopProfile()).into(iv_top_image);
                    }

                    addMainBranchAddress(getShopDetailResponse.getData().getRequestList());


                    if (getShopDetailResponse.getData().getRequestList().getBranchAddress() != null) {
                        for (int i = 0; i < getShopDetailResponse.getData().getRequestList().getBranchAddress().size(); i++) {
                            branchIds.add(getShopDetailResponse.getData().getRequestList().getBranchAddress().get(i).getAddressId());
                        }
                        addedAddress(getShopDetailResponse.getData().getRequestList().getBranchAddress().size(), getShopDetailResponse.getData().getRequestList());
                    }

                    Country = getShopDetailResponse.getData().getRequestList().getCountry();
                    Province = getShopDetailResponse.getData().getRequestList().getProvince();
                    City = getShopDetailResponse.getData().getRequestList().getCity();
                    Street = getShopDetailResponse.getData().getRequestList().getArea();
                    Latitude = getShopDetailResponse.getData().getRequestList().getLatitude();
                    Longitude = getShopDetailResponse.getData().getRequestList().getLongitude();

                }
            }
        });

        addnewaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ApplicationUtils.isNetworkConnected(getActivity())) {
                    Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    return;
                }


                View inflater = getLayoutInflater().inflate(R.layout.add_address_layout, null);
                addressRL.addView(inflater);

                et_street = inflater.findViewById(R.id.et_street);
                et_city = inflater.findViewById(R.id.et_city);
                et_province = inflater.findViewById(R.id.et_province);
                et_country = inflater.findViewById(R.id.et_country);
                ic_delete = inflater.findViewById(R.id.ic_delete);
                ic_edit = inflater.findViewById(R.id.ic_edit);
                addbranchaddress = inflater.findViewById(R.id.addbranchaddress);

                ic_delete.setVisibility(View.INVISIBLE);
                ic_edit.setVisibility(View.INVISIBLE);

                ic_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "CLICK", Toast.LENGTH_SHORT).show();
                    /*    DeleteBranchAddress deleteBranchAddress = new DeleteBranchAddress();
                        deleteBranchAddress.setId(addressId);
                        deleteBranchAddress.setProfileId(Constant.PROFILE_ID);

                        storeSettingViewModel.deleteBranchAddress(deleteBranchAddress);
                        storeSettingViewModel.getDeleteBranchAddress().observe(getActivity(), new Observer<DeleteBranchAddressResponse>() {
                            @Override
                            public void onChanged(DeleteBranchAddressResponse deleteBranchAddressResponse) {
                                if (deleteBranchAddressResponse != null) {
                                    Toast.makeText(getActivity(), " " + deleteBranchAddressResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/

                        //addressRL.removeView(inflater);
                    }
                });


                addbranchaddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(), "CLICKED", Toast.LENGTH_SHORT).show();
                        if (TextUtils.isEmpty(et_street.getText().toString()) ||
                                TextUtils.isEmpty(et_city.getText().toString()) ||
                                TextUtils.isEmpty(et_province.getText().toString()) ||
                                TextUtils.isEmpty(et_country.getText().toString())) {
                            Toast.makeText(getActivity(), "Some Fields are missing...", Toast.LENGTH_SHORT).show();
                        } else {

                            if (!ApplicationUtils.isNetworkConnected(getActivity())) {
                                Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            AddBranchAddress addBranchAddress = new AddBranchAddress();
                            addBranchAddress.setProfileId(Constant.PROFILE_ID);
                            addBranchAddress.setCity(et_city.getText().toString());
                            addBranchAddress.setProvince(et_province.getText().toString());
                            addBranchAddress.setCountry(et_country.getText().toString());
                            addBranchAddress.setLine1(et_street.getText().toString());

                            storeSettingViewModel.addBranchAddress(addBranchAddress, getActivity());
                            storeSettingViewModel.getAddBranchAddress().observe(getActivity(), new Observer<AddBranchAddressResponse>() {
                                @Override
                                public void onChanged(AddBranchAddressResponse addBranchAddressResponse) {
                                    if (addBranchAddressResponse != null) {
                                        if (addBranchAddressResponse.getStatus().equals("0")) {
                                            Toast.makeText(getActivity(), "Shop Address Has Been Added Successfully...", Toast.LENGTH_SHORT).show();
                                            navController.navigate(R.id.storeSettingFragment);
                                            Utility.hideKeyboard(getActivity(), view);
                                        }
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(activity, "Clicked...", Toast.LENGTH_SHORT).show();
                Dialog dialog = new Dialog(activity);
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

                mondaySwitch.setChecked(mon);
                tuesdaySwitch.setChecked(tue);
                wednesdaySwitch.setChecked(wed);
                thrusdaySwitch.setChecked(thrus);
                fridaySwitch.setChecked(fri);
                saturdaySwitch.setChecked(sat);
                sundaySwitch.setChecked(sun);

                tvMonday = dialog.findViewById(R.id.tvMonday);
                tvTuesday = dialog.findViewById(R.id.tvTueday);
                tvWednesday = dialog.findViewById(R.id.tvWednesday);
                tvThrusday = dialog.findViewById(R.id.tvThrusday);
                tvFriday = dialog.findViewById(R.id.tvFriday);
                tvSaturday = dialog.findViewById(R.id.tvSaturday);
                tvSunday = dialog.findViewById(R.id.tvSunday);


                ArrayAdapter<String> openTimingAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_text, activity.getResources().getStringArray(R.array.opens_at));
                openTimingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww

                monday_OpenAt.setAdapter(openTimingAdapter);
                int spinnerPosition = openTimingAdapter.getPosition(mondayOpenTimings);
                monday_OpenAt.setSelection(spinnerPosition);

                tuesday_OpenAt.setAdapter(openTimingAdapter);
                int sp1 = openTimingAdapter.getPosition(tuesdayOpenTimings);
                tuesday_OpenAt.setSelection(sp1);

                wednesday_OpenAt.setAdapter(openTimingAdapter);
                int sp2 = openTimingAdapter.getPosition(wednesdayOpenTimings);
                wednesday_OpenAt.setSelection(sp2);

                thrusday_OpenAt.setAdapter(openTimingAdapter);
                int sp3 = openTimingAdapter.getPosition(thrusdayOpenTimings);
                thrusday_OpenAt.setSelection(sp3);

                friday_OpenAt.setAdapter(openTimingAdapter);
                int sp4 = openTimingAdapter.getPosition(fridayOpenTimings);
                friday_OpenAt.setSelection(sp4);

                saturday_OpenAt.setAdapter(openTimingAdapter);
                int sp5 = openTimingAdapter.getPosition(saturdayOpenTimings);
                saturday_OpenAt.setSelection(sp5);

                sunday_OpenAt.setAdapter(openTimingAdapter);
                int sp6 = openTimingAdapter.getPosition(sundayOpenTimings);
                sunday_OpenAt.setSelection(sp6);

                ArrayAdapter<String> closeTimingAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_text, activity.getResources().getStringArray(R.array.close_at));
                closeTimingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww

                monday_CloseAt.setAdapter(closeTimingAdapter);
                int spinnerPosition1 = closeTimingAdapter.getPosition(mondayCloseTimings);
                monday_CloseAt.setSelection(spinnerPosition1);

                tuesday_CloseAt.setAdapter(closeTimingAdapter);
                int sp7 = closeTimingAdapter.getPosition(tuesdayCloseTimings);
                tuesday_CloseAt.setSelection(sp7);

                wednesday_CloseAt.setAdapter(closeTimingAdapter);
                int sp8 = closeTimingAdapter.getPosition(wednesdayCloseTimings);
                wednesday_CloseAt.setSelection(sp8);

                thrusday_CloseAt.setAdapter(closeTimingAdapter);
                int sp9 = closeTimingAdapter.getPosition(thrusdayCloseTimings);
                thrusday_CloseAt.setSelection(sp9);

                friday_CloseAt.setAdapter(closeTimingAdapter);
                int sp10 = closeTimingAdapter.getPosition(fridayCloseTimings);
                friday_CloseAt.setSelection(sp10);

                saturday_CloseAt.setAdapter(closeTimingAdapter);
                int sp11 = closeTimingAdapter.getPosition(saturdayCloseTimings);
                saturday_CloseAt.setSelection(sp11);

                sunday_CloseAt.setAdapter(closeTimingAdapter);
                int sp12 = closeTimingAdapter.getPosition(sundayCloseTimings);
                sunday_CloseAt.setSelection(sp12);

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

                if (!mon) {
                    tvMonday.setPaintFlags(tvMonday.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvMonday.setPaintFlags(0);
                }

                if (!tue) {
                    tvTuesday.setPaintFlags(tvTuesday.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvTuesday.setPaintFlags(0);
                }


                if (!wed) {
                    tvWednesday.setPaintFlags(tvWednesday.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvWednesday.setPaintFlags(0);
                }


                if (!thrus) {
                    tvThrusday.setPaintFlags(tvThrusday.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvThrusday.setPaintFlags(0);
                }


                if (!fri) {
                    tvFriday.setPaintFlags(tvFriday.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvFriday.setPaintFlags(0);
                }


                if (!sat) {
                    tvSaturday.setPaintFlags(tvSaturday.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvSaturday.setPaintFlags(0);
                }


                if (!sun) {
                    tvSunday.setPaintFlags(tvSunday.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvSunday.setPaintFlags(0);
                }


                updateTimingbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(), "clicked...", Toast.LENGTH_SHORT).show();

                        daysSettingList.add(0, mondaySetting);
                        daysSettingList.add(1, tuesdaySetting);
                        daysSettingList.add(2, wednesdaySetting);
                        daysSettingList.add(3, thrusdaySetting);
                        daysSettingList.add(4, fridaySetting);
                        daysSettingList.add(5, saturdaySetting);
                        daysSettingList.add(6, sundaySetting);

                        shopTiming.setProfileId(Constant.PROFILE_ID);
                        shopTiming.setDaysSetting(daysSettingList);

                        shopSettingViewModel.postTiming(shopTiming, activity);
                        shopSettingViewModel.getUpdateTiming().observe((LifecycleOwner) activity, new Observer<ShopTimingResponse>() {
                            @Override
                            public void onChanged(ShopTimingResponse shopTimingResponse) {
                                if (shopTimingResponse != null) {
                                    dialog.dismiss();
                                    daysSettingList.clear();
                                    showTimings();
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

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogImage = new Dialog(getActivity());
                dialogImage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogImage.setContentView(R.layout.dialog_upload_capture);

                Button upload = dialogImage.findViewById(R.id.uploadImage);
                Button capture = dialogImage.findViewById(R.id.captureImage);

                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) +
                                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) +
                                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA) ||
                                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                                builder.setTitle("Grant those permissions...");
                                builder.setMessage("Camera and External Storage Permission Required...");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        requestPermissions(new String[]{
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        }, 1);

                                    }
                                });

                                builder.setNegativeButton("Cancel", null);
                                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else { //ye lines for the very first time chalein gy jab app start hongy
                                requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                }, 1);
                            }
                        } else {
                            dialogImage.dismiss();
                            openGallery();
                            //openCropper();
                        }
                    }
                });
                capture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) +
                                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) +
                                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA) ||
                                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            ) {

                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                                builder.setTitle("Grant those permissions...");
                                builder.setMessage("Camera External Storage Permission Required...");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        requestPermissions(new String[]{
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        }, 2);

                                    }
                                });

                                builder.setNegativeButton("Cancel", null);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else { //ye lines for the very first time chalein gy jab app start hongy
                                requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
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
    }

    private void openGallery() {
        // Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        // startActivityForResult(gallery, UPLOAD_IMAGE);
        Intent intent = new Intent(getActivity(), Gallery.class);
        intent.putExtra("title", "Select media");
        intent.putExtra("mode", 1); //try on 1 and 3
        intent.putExtra("maxSelection", 1);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, UPLOAD_IMAGE);
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getActivity().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAPTURE_IMAGE);

       /* Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, CAPTURE_IMAGE);*/
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = UCrop.getOutput(data);
            Log.i("TAG", "onActivityResult: hello " + uri + "\n" + uri.getPath());
            iv_top_image.setImageURI(uri);
            updateShopImage(uri.getPath());
        }


        switch (requestCode) {
            case UPLOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    List<Uri> uris = null;
                    ArrayList<MediaAttachment> attachments = null;
                    if (uris == null) {
                        uris = new ArrayList<>();
                    }
                    if (attachments == null) {
                        attachments = new ArrayList<>();
                    }
                    attachments = data.getParcelableArrayListExtra("result");

                    // bannerImage.setImageURI(attachments.get(0).getFileUri());
                    //filePath = getRealPathFromURI(attachments.get(0).getFileUri());
                    filePath = attachments.get(0).getFileUri().getPath();
                    Log.i("TAG", "onActivityResult: Capture Capture Path" + filePath);


                    cropImage(attachments.get(0).getFileUri(), new File(attachments.get(0).getFileUri().getPath()));
                }
                break;
            case CAPTURE_IMAGE:
                if (resultCode == RESULT_OK) {
       /*             Bitmap photo = (Bitmap) data.getExtras().get("data");
                    iv_top_image.setImageBitmap(photo);
                    imageUri = getImageUri(getActivity(), photo);
                    filePath = getRealPathFromURI(imageUri);
                    cropImage(imageUri, new File(filePath));
*/
                    try {
                        Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                                getActivity().getContentResolver(), imageUri);
                        iv_top_image.setImageBitmap(thumbnail);
                        filePath = getRealPathFromURI(imageUri);
                        cropImage(imageUri, new File(filePath));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 22:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // get the returned data
                        Bundle extras = data.getExtras();
                        // get the cropped bitmap
                        Bitmap selectedBitmap = extras.getParcelable("data");
                        iv_top_image.setImageBitmap(selectedBitmap);
                    }
                }
                break;
            case UCrop.RESULT_ERROR:
                final Throwable cropError = UCrop.getError(data);
                Log.e("TAG", "Crop error: " + cropError);
                break;
            default:
                //do nothing
        }
    }

    private void updateShopImage(String filePath) {
        ShopBasicSetting shopBasicSetting = new ShopBasicSetting();
        shopBasicSetting.setProfileId(Constant.PROFILE_ID);
        shopBasicSetting.setShop_Theme("FFFFFF");
        // shopBasicSetting.setShopProfile(imageUri); //image
        shopBasicSetting.setShopProfile(filePath); //image
        shopBasicSetting.setTax("0");
        shopBasicSetting.setShippingFee("0");
        shopBasicSetting.setCountry(Country);
        shopBasicSetting.setProvince(Province);
        shopBasicSetting.setCity(City);
        shopBasicSetting.setArea(Street);
        shopBasicSetting.setLat(Latitude);
        shopBasicSetting.setLong(Longitude);
        shopBasicSetting.setSecondarylongitude(Longitude);
        shopBasicSetting.setSecondarylatitude(Latitude);

        shopSettingViewModel.postShopSettingDetails(shopBasicSetting, activity);
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.showDialog();
        // progressBar.setVisibility(View.VISIBLE);
        shopSettingViewModel.getShopSettingResponse().observe((LifecycleOwner) activity, new Observer<ShopBasicSettingResponse>() {
            @Override
            public void onChanged(ShopBasicSettingResponse shopBasicSettingResponse) {
                if (shopBasicSettingResponse != null) {
                    loadingDialog.dismissDialog();
                    TelloPreferenceManager.getInstance(activity).savecongratsStatus(false);
                    navController.navigate(R.id.storeSettingFragment);
                } else {
                    //  progressBar.setVisibility(View.GONE);
                    Toast.makeText(activity, "Some thing went wrong try again....", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }
            }
        });
    }

    private void cropImage(Uri sourceUri, File filename) {
        //new File(getActivity().getCacheDir(), queryName(getActivity().getContentResolver(), sourceUri))
        Uri destinationUri = Uri.fromFile(filename);
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(IMAGE_COMPRESSION);
        options.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

        lockAspectRatio = true;
        if (lockAspectRatio)
            options.withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y);

        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(getActivity(), StoreSettingFragment.this);
        //.start(getActivity(), ShopSettingFragment.this, 22);

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

    private void addMainBranchAddress(GetShopDetailResponse.RequestList requestList) {
        View inflater = getLayoutInflater().inflate(R.layout.add_main_branch_address, null);
        addressRL.addView(inflater);

        et_street = inflater.findViewById(R.id.et_street);
        et_city = inflater.findViewById(R.id.et_city);
        et_province = inflater.findViewById(R.id.et_province);
        et_country = inflater.findViewById(R.id.et_country);
        ic_delete = inflater.findViewById(R.id.ic_delete);
        ic_edit = inflater.findViewById(R.id.ic_edit);
        addbranchaddress = inflater.findViewById(R.id.addbranchaddress);

        ic_delete.setVisibility(View.GONE);
        ic_edit.setVisibility(View.VISIBLE);
        addbranchaddress.setVisibility(View.GONE);
        addbranchaddress.setVisibility(View.GONE);

        et_street.setText(requestList.getArea());
        et_city.setText(requestList.getCity());
        et_province.setText(requestList.getProvince());
        et_country.setText(requestList.getCountry());

        ic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.update_main_branch_address);


                searchableCity = dialog.findViewById(R.id.searchableCity);
                searchableProvince = dialog.findViewById(R.id.searchableProvince);
                searchableCountry = dialog.findViewById(R.id.searchableCountry);

                ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, Countries);
                countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                searchableCountry.setAdapter(countryAdapter);
                searchableCountry.setSelection(1);
                searchableCountry.setOnItemSelectedListener(onItemSelectedListenerAddress);

                ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, States);
                provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                searchableProvince.setAdapter(provinceAdapter);
                int searchableStatePosition = provinceAdapter.getPosition(Province);
                searchableProvince.setSelection(searchableStatePosition);
                searchableProvince.setOnItemSelectedListener(onItemSelectedListenerAddress);


                cityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, Cities);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                searchableCity.setAdapter(cityAdapter);
                searchableCity.setOnItemSelectedListener(onItemSelectedListenerAddress);

                EditText etStreet = dialog.findViewById(R.id.etStreet);
                etStreet.setText(requestList.getArea());


                Button done = dialog.findViewById(R.id.confirmRiderbtn);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopBasicSetting shopBasicSetting = new ShopBasicSetting();
                        shopBasicSetting.setProfileId(Constant.PROFILE_ID);
                        shopBasicSetting.setShop_Theme("FFFFFF");
                        // shopBasicSetting.setShopProfile(imageUri); //image
                        shopBasicSetting.setShopProfile(""); //image
                        shopBasicSetting.setTax("0");
                        shopBasicSetting.setShippingFee("0");
                        shopBasicSetting.setCountry(searchableCountry.getSelectedItem().toString());
                        shopBasicSetting.setProvince(searchableProvince.getSelectedItem().toString());
                        shopBasicSetting.setCity(searchableCity.getSelectedItem().toString());
                        shopBasicSetting.setArea(etStreet.getText().toString());
                        shopBasicSetting.setLat(Latitude);
                        shopBasicSetting.setLong(Longitude);
                        shopBasicSetting.setSecondarylatitude(Latitude);
                        shopBasicSetting.setSecondarylongitude(Longitude);

                        shopSettingViewModel.postShopSettingDetailsWithOutImage(shopBasicSetting, activity);
                        LoadingDialog loadingDialog = new LoadingDialog(activity);
                        loadingDialog.showDialog();
                        // progressBar.setVisibility(View.VISIBLE);
                        shopSettingViewModel.getShopSettingResponseWithOutImage().observe((LifecycleOwner) activity, new Observer<ShopBasicSettingResponse>() {
                            @Override
                            public void onChanged(ShopBasicSettingResponse shopBasicSettingResponse) {
                                if (shopBasicSettingResponse != null) {
                                    loadingDialog.dismissDialog();
                                    TelloPreferenceManager.getInstance(activity).savecongratsStatus(false);
                                    dialog.dismiss();
                                    navController.navigate(R.id.storeSettingFragment);
                                } else {
                                    //  progressBar.setVisibility(View.GONE);
                                    Toast.makeText(activity, "Some thing went wrong try again....", Toast.LENGTH_SHORT).show();
                                    loadingDialog.dismissDialog();
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });

                dialog.show();
            }
        });

    }

    private void addedAddress(int size, GetShopDetailResponse.RequestList requestList) {
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                View inflater = getLayoutInflater().inflate(R.layout.add_address_layout, null);
                // addressRL.addView(inflater);

                EditText et_street = inflater.findViewById(R.id.et_street);
                EditText et_city = inflater.findViewById(R.id.et_city);
                EditText et_province = inflater.findViewById(R.id.et_province);
                EditText et_country = inflater.findViewById(R.id.et_country);
                ImageView ic_delete = inflater.findViewById(R.id.ic_delete);
                ImageView ic_edit = inflater.findViewById(R.id.ic_edit);
                addbranchaddress = inflater.findViewById(R.id.addbranchaddress);

                et_street.setText(requestList.getBranchAddress().get(i).getLine1());
                et_city.setText(requestList.getBranchAddress().get(i).getCity());
                et_province.setText(requestList.getBranchAddress().get(i).getProvince());
                et_country.setText(requestList.getBranchAddress().get(i).getCountry());

                addbranchaddress.setVisibility(View.INVISIBLE);

                //delete address
                ic_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (!ApplicationUtils.isNetworkConnected(getActivity())) {
                            Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Delete Address...")
                                .setMessage("Are you sure you want to delete this branch address?")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        DeleteBranchAddress deleteBranchAddress = new DeleteBranchAddress();
                                        deleteBranchAddress.setId(branchIds.get(addressRL.indexOfChild(inflater) - 2));
                                        deleteBranchAddress.setProfileId(Constant.PROFILE_ID);

                                        storeSettingViewModel.deleteBranchAddress(deleteBranchAddress, getActivity());
                                        storeSettingViewModel.getDeleteBranchAddress().observe(getActivity(), new Observer<DeleteBranchAddressResponse>() {
                                            @Override
                                            public void onChanged(DeleteBranchAddressResponse deleteBranchAddressResponse) {
                                                if (deleteBranchAddressResponse != null) {
                                                    navController.navigate(R.id.storeSettingFragment);
                                                    //Toast.makeText(getActivity(), " " + deleteBranchAddressResponse.getStatusDetail(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton("No", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                });

                ic_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!ApplicationUtils.isNetworkConnected(getActivity())) {
                            Toast.makeText(getActivity(), "" + getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            return;
                        }


                        UpdateBranchAddress updateBranchAddress = new UpdateBranchAddress();
                        updateBranchAddress.setProfileId(Constant.PROFILE_ID);
                        updateBranchAddress.setCity(et_city.getText().toString());
                        updateBranchAddress.setProvince(et_province.getText().toString());
                        updateBranchAddress.setCountry(et_country.getText().toString());
                        updateBranchAddress.setLine1(et_street.getText().toString());
                        updateBranchAddress.setId(branchIds.get(addressRL.indexOfChild(inflater) - 2));


                        if (TextUtils.isEmpty(et_street.getText().toString()) ||
                                TextUtils.isEmpty(et_city.getText().toString()) ||
                                TextUtils.isEmpty(et_province.getText().toString()) ||
                                TextUtils.isEmpty(et_country.getText().toString())) {
                            Toast.makeText(getActivity(), "Some Fields are missing...", Toast.LENGTH_SHORT).show();
                        } else {
                            storeSettingViewModel.updateBranchAddress(updateBranchAddress, getActivity());
                            storeSettingViewModel.getUpdateBranchAddress().observe(getActivity(), new Observer<UpdateBranchAddressResponse>() {
                                @Override
                                public void onChanged(UpdateBranchAddressResponse updateBranchAddressResponse) {
                                    if (updateBranchAddressResponse != null) {
                                        if (updateBranchAddressResponse.getStatus().equals("0")) {
                                            Toast.makeText(getActivity(), "Shop Address Has Been Updated Successfully...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
                addressRL.addView(inflater);

            }

        }
    }

    private void initViews(View view) {

        //et_country = view.findViewById(R.id.et_country);
        addressRL = view.findViewById(R.id.addressRL);
        addnewaddress = view.findViewById(R.id.addnewaddress);
        addedAddress = view.findViewById(R.id.addedAddress);
        et_shop_url = view.findViewById(R.id.et_shop_url);
        registeredShopNumber = view.findViewById(R.id.registeredShopNumber);
        iv_top_image = view.findViewById(R.id.iv_top_image);
        linearAddBranch = view.findViewById(R.id.linearAddBranch);
        uploadImage = view.findViewById(R.id.uploadImage);

        //revamp flow
        outerRL = view.findViewById(R.id.outerRL);
        recycler_timings = view.findViewById(R.id.recycler_timings);
        iv_timings = view.findViewById(R.id.iv_timings);
        FL1 = view.findViewById(R.id.FL1);
        iv_image = view.findViewById(R.id.iv_image);
        daysSettingList = new ArrayList<>();
        shopTiming = new ShopTiming();
        shopTiming.setProfileId(Constant.PROFILE_ID);

        mondaySetting = new ShopTiming().new DaysSetting();
        tuesdaySetting = new ShopTiming().new DaysSetting();
        wednesdaySetting = new ShopTiming().new DaysSetting();
        thrusdaySetting = new ShopTiming().new DaysSetting();
        fridaySetting = new ShopTiming().new DaysSetting();
        saturdaySetting = new ShopTiming().new DaysSetting();
        sundaySetting = new ShopTiming().new DaysSetting();

        Countries = new ArrayList<>();
        Cities = new ArrayList<>();
        Cities.add("Select City");
        States = new ArrayList<>();

        //initially timings toggle status is not active
        mondaySetting.setShopStatusDaywise("N");
        tuesdaySetting.setShopStatusDaywise("N");
        wednesdaySetting.setShopStatusDaywise("N");
        thrusdaySetting.setShopStatusDaywise("N");
        fridaySetting.setShopStatusDaywise("N");
        saturdaySetting.setShopStatusDaywise("N");
        sundaySetting.setShopStatusDaywise("N");


    }

    private void showTimings() {
        //by default timings to show inside recycler view...
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.showDialog();
        GetTimings getTimings = new GetTimings();
        getTimings.setProfileId(Constant.PROFILE_ID);
        getTimings.setShopId("7");
        shopSettingViewModel.posttogetTimings(getTimings, activity);
        shopSettingViewModel.gettimings().observe((LifecycleOwner) activity, new Observer<GetTimingsResponse>() {
            @Override
            public void onChanged(GetTimingsResponse getTimingsResponse) {
                if (getTimingsResponse != null) {
                    timingnAdapter = new TimingnAdapter(activity, getTimingsResponse.getData().getRequestList());
                    recycler_timings.setAdapter(timingnAdapter);
                    timingsID = new ArrayList<>();
                    for (int i = 0; i < getTimingsResponse.getData().getRequestList().size(); i++) {
                        timingsID.add(getTimingsResponse.getData().getRequestList().get(i).getSettingId());
                    }
                    mondayOpenTimings = getTimingsResponse.getData().getRequestList().get(0).getShopStartTime();
                    mondayCloseTimings = getTimingsResponse.getData().getRequestList().get(0).getShopEndTime();
                    tuesdayOpenTimings = getTimingsResponse.getData().getRequestList().get(1).getShopStartTime();
                    tuesdayCloseTimings = getTimingsResponse.getData().getRequestList().get(1).getShopEndTime();
                    wednesdayOpenTimings = getTimingsResponse.getData().getRequestList().get(2).getShopStartTime();
                    wednesdayCloseTimings = getTimingsResponse.getData().getRequestList().get(2).getShopEndTime();
                    thrusdayOpenTimings = getTimingsResponse.getData().getRequestList().get(3).getShopStartTime();
                    thrusdayCloseTimings = getTimingsResponse.getData().getRequestList().get(3).getShopEndTime();
                    fridayOpenTimings = getTimingsResponse.getData().getRequestList().get(4).getShopStartTime();
                    fridayCloseTimings = getTimingsResponse.getData().getRequestList().get(4).getShopEndTime();
                    saturdayOpenTimings = getTimingsResponse.getData().getRequestList().get(5).getShopStartTime();
                    saturdayCloseTimings = getTimingsResponse.getData().getRequestList().get(5).getShopEndTime();
                    sundayOpenTimings = getTimingsResponse.getData().getRequestList().get(6).getShopStartTime();
                    sundayCloseTimings = getTimingsResponse.getData().getRequestList().get(6).getShopEndTime();
                    mon = getTimingsResponse.getData().getRequestList().get(0).getShopStatusDaywise().equals("Y") ? true : false;
                    tue = getTimingsResponse.getData().getRequestList().get(1).getShopStatusDaywise().equals("Y") ? true : false;
                    wed = getTimingsResponse.getData().getRequestList().get(2).getShopStatusDaywise().equals("Y") ? true : false;
                    thrus = getTimingsResponse.getData().getRequestList().get(3).getShopStatusDaywise().equals("Y") ? true : false;
                    fri = getTimingsResponse.getData().getRequestList().get(4).getShopStatusDaywise().equals("Y") ? true : false;
                    sat = getTimingsResponse.getData().getRequestList().get(5).getShopStatusDaywise().equals("Y") ? true : false;
                    sun = getTimingsResponse.getData().getRequestList().get(6).getShopStatusDaywise().equals("Y") ? true : false;
                    loadingDialog.dismissDialog();
                } else {
                    loadingDialog.dismissDialog();
                    Toast.makeText(activity, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
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
                //  Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

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
                        //  Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

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
                        // Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();


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
                        // Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

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
                        //Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
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
                //  Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

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
                        //Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
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
                //   Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

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
                        // Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

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
                        // Toast.makeText(activity, "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        sundaySetting.setShopEndTime(parent.getItemAtPosition(position).toString());
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

    //for switch button on timings dialog...
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == mondaySwitch.getId()) {
                mondaySwitch.setChecked(isChecked);
                mondaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                //  Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == tuesdaySwitch.getId()) {
                tuesdaySwitch.setChecked(isChecked);
                tuesdaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                // Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == wednesdaySwitch.getId()) {
                wednesdaySwitch.setChecked(isChecked);
                wednesdaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                // Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == thrusdaySwitch.getId()) {
                thrusdaySwitch.setChecked(isChecked);
                thrusdaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                // Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == fridaySwitch.getId()) {
                fridaySwitch.setChecked(isChecked);
                fridaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                //Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == saturdaySwitch.getId()) {
                saturdaySwitch.setChecked(isChecked);
                saturdaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                // Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }

            if (buttonView.getId() == sundaySwitch.getId()) {
                sundaySwitch.setChecked(isChecked);
                sundaySetting.setShopStatusDaywise(isChecked ? "Y" : "N");
                //Toast.makeText(activity, "" + isChecked, Toast.LENGTH_SHORT).show();
            }
        }
    };


    AdapterView.OnItemSelectedListener onItemSelectedListenerAddress = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //spinner for country
            if (parent.getId() == searchableCountry.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }
                Country = (String) parent.getItemAtPosition(position);
                CountryId = Integer.parseInt(countriesPojo.getCountries().get(position).getId());

                //here we set updated states
                States.clear();
                States.add(0, "Select State");
                for (int i = 1; i < statePojo.getStates().size(); i++) {
                    if (Integer.parseInt(statePojo.getStates().get(i).getCountryId()) == CountryId) {
                        States.add(statePojo.getStates().get(i).getName());
                    }
                }

/*                ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, States);
                provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                searchableProvince.setAdapter(provinceAdapter);
                searchableProvince.setOnItemSelectedListener(onItemSelectedListenerAddress);*/


            }

            //spinner for province
            if (parent.getId() == searchableProvince.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }

                Province = (String) parent.getItemAtPosition(position);
                StateId = (int) parent.getItemIdAtPosition(position);
                //  StateId = Integer.parseInt(statePojo.getStates().get(position).getId());
                //Toast.makeText(activity, "" + StateId, Toast.LENGTH_SHORT).show();
                Cities.clear();
                Cities.add(0, "Select City");


                getRetrofitClient().getAllCitiesByID(String.valueOf(StateId)).enqueue(new Callback<CityListResponse>() {
                    @Override
                    public void onResponse(Call<CityListResponse> call, Response<CityListResponse> response) {
                        CityListResponse cityListResponse = response.body();

                        for (int i = 1; i < cityListResponse.getData().getRequestList().size(); i++) {
                            Cities.add(cityListResponse.getData().getRequestList().get(i).getName());
                        }

                        cityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, Cities);
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                        searchableCity.setAdapter(cityAdapter);
                        int searchableCityPosition = cityAdapter.getPosition(City);
                        searchableCity.setSelection(searchableCityPosition);
                        searchableCity.setOnItemSelectedListener(onItemSelectedListenerAddress);
                    }

                    @Override
                    public void onFailure(Call<CityListResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


                //Toast.makeText(activity, "" + StateId, Toast.LENGTH_SHORT).show();
                // shopBasicSetting.setProvince((String) parent.getItemAtPosition(position));
            }

            //spinner for city

            if (parent.getId() == searchableCity.getId()) {
                if (parent.getItemIdAtPosition(position) == 0) {
                    return;
                }
                City = (String) parent.getItemAtPosition(position);
                CityId = (int) parent.getItemIdAtPosition(position);
                // Toast.makeText(activity, "" + CityId, Toast.LENGTH_SHORT).show();
                // shopBasicSetting.setCity((String) parent.getItemAtPosition(position));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

}
