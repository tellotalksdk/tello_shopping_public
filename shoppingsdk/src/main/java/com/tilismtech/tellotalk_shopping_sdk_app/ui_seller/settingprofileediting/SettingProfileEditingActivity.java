package com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.settingprofileediting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.tabs.TabLayout;
import com.tilismtech.tellotalk_shopping_sdk_app.R;
import com.tilismtech.tellotalk_shopping_sdk_app.gallery.Gallery;
import com.tilismtech.tellotalk_shopping_sdk_app.gallery.MediaAttachment;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.requestbody.UpdateUserAndImage;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.pojos.responsebody.UpdateUserAndImageResponse;
import com.tilismtech.tellotalk_shopping_sdk_app.receiver.NetworkReceiver;
import com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.shopregistration.ShopRegistrationViewModel;
import com.tilismtech.tellotalk_shopping_sdk_app.ui_seller.storesetting.StoreSettingViewModel;
import com.tilismtech.tellotalk_shopping_sdk_app.utils.ApplicationUtils;
import com.tilismtech.tellotalk_shopping_sdk_app.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk_app.utils.LoadingDialog;
import com.tilismtech.tellotalk_shopping_sdk_app.utils.NoInternetDetection;

import java.io.ByteArrayOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SettingProfileEditingActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout personalInfoRL, storeSettingRL, bankRL;
    NavHostFragment navHostFragment;
    NavController navController;
    View horizontalLine1, horizontalLine2, horizontalLine3;
    TextView tv_storesettings, tv_bank, tv_personal, rating_number, tv_edit;
    com.google.android.material.tabs.TabLayout tab1;
    ImageView iv_imageedit, iv_profile;
    ShopRegistrationViewModel shopRegistrationViewModel;
    StoreSettingViewModel storeSettingViewModel;
    Dialog dialogImage;
    private final static int UPLOAD_IMAGE = 123;
    private final static int CAPTURE_IMAGE = 456;
    String filePath = "";
    Uri imageUri;
    EditText shopOwnername;
    NetworkReceiver networkReceiver;
    LoadingDialog loadingDialog1;
    boolean toggle;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile_editing);
        initViews();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    public void initViews() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        loadingDialog1 = new LoadingDialog(SettingProfileEditingActivity.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Fresco.initialize(this);
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(5.0f);

        personalInfoRL = findViewById(R.id.personalInfoRL);
        storeSettingRL = findViewById(R.id.storeSettingRL);
        bankRL = findViewById(R.id.bankRL);
        shopOwnername = findViewById(R.id.user_name);
        rating_number = findViewById(R.id.rating_number);
        tv_edit = findViewById(R.id.tv_edit);
        iv_profile = findViewById(R.id.iv_profile);

        shopOwnername.setEnabled(false);
        //  iv_profile.setEnabled(false);

        NoInternetDetection loadingDialog = new NoInternetDetection(this);
        networkReceiver = new NetworkReceiver(loadingDialog);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);


        shopRegistrationViewModel = new ViewModelProvider(this).get(ShopRegistrationViewModel.class);
        storeSettingViewModel = new ViewModelProvider(this).get(StoreSettingViewModel.class);

        horizontalLine1 = findViewById(R.id.horizontalLine1);
        horizontalLine2 = findViewById(R.id.horizontalLine2);
        horizontalLine3 = findViewById(R.id.horizontalLine3);

        tv_storesettings = findViewById(R.id.tv_storesettings);
        tv_bank = findViewById(R.id.tv_bank);
        tv_personal = findViewById(R.id.tv_personal);

        personalInfoRL.setOnClickListener(this);
        storeSettingRL.setOnClickListener(this);
        bankRL.setOnClickListener(this);

        horizontalLine1.setOnClickListener(this);
        horizontalLine2.setOnClickListener(this);
        horizontalLine3.setOnClickListener(this);

        tv_storesettings.setOnClickListener(this);
        storeSettingRL.setOnClickListener(this);
        bankRL.setOnClickListener(this);
        iv_imageedit = findViewById(R.id.iv_edit);

  /*      AlphaAnimation blinkanimation= new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        blinkanimation.setDuration(300); // duration - half a second
        blinkanimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        blinkanimation.setRepeatCount(1000); // Repeat animation infinitely
        blinkanimation.setRepeatMode(Animation.RESTART);

        iv_profile.setAnimation(blinkanimation);*/

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle) {


                    if (TextUtils.isEmpty(shopOwnername.getText().toString())) {
                        Toast.makeText(SettingProfileEditingActivity.this, "User name can not be empty...", Toast.LENGTH_SHORT).show();
                        return;
                    } else {


                        if (!ApplicationUtils.isNetworkConnected(SettingProfileEditingActivity.this)) {
                            Toast.makeText(SettingProfileEditingActivity.this, "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            return;
                        }


                        UpdateUserAndImage updateUserAndImage = new UpdateUserAndImage();
                        updateUserAndImage.setFirstName(shopOwnername.getText().toString());
                        updateUserAndImage.setMiddleName(" ");
                        updateUserAndImage.setLastName(" ");
                        updateUserAndImage.setProfileId(Constant.PROFILE_ID);
                        updateUserAndImage.setProfilePic(TextUtils.isEmpty(filePath) ? "" : filePath);
                        //  iv_imageedit.setVisibility(View.GONE);
                        tv_edit.setVisibility(View.VISIBLE);


                        if (TextUtils.isEmpty(filePath)) { //run when only name is given........

                            shopRegistrationViewModel.userName(updateUserAndImage, SettingProfileEditingActivity.this);
                            shopRegistrationViewModel.getUserName().observe(SettingProfileEditingActivity.this, new Observer<UpdateUserAndImageResponse>() {
                                @Override
                                public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                                    if (updateUserAndImageResponse != null) {
                                        Toast.makeText(SettingProfileEditingActivity.this, "Profile updated successfully...", Toast.LENGTH_SHORT).show();
                                    } else {

                                    }
                                }
                            });
                        } else {


                            shopRegistrationViewModel.userImageandName(updateUserAndImage, SettingProfileEditingActivity.this);
                            shopRegistrationViewModel.getUpdateUserImageResponse().observe(SettingProfileEditingActivity.this, new Observer<UpdateUserAndImageResponse>() {
                                @Override
                                public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                                    if (updateUserAndImageResponse != null) {
                                        Toast.makeText(SettingProfileEditingActivity.this, "Profile updated successfully...", Toast.LENGTH_SHORT).show();
                                    } else {

                                    }
                                }
                            });
                        }


                    }


                    tv_edit.setVisibility(View.VISIBLE);
                    //  iv_imageedit.setVisibility(View.GONE);
                    shopOwnername.setEnabled(false);
                    // iv_profile.setEnabled(false);
                    tv_edit.setText("Edit");
                    toggle = false;
                } else {
                    tv_edit.setVisibility(View.VISIBLE);
                    //  iv_imageedit.setVisibility(View.GONE);
                    shopOwnername.setEnabled(true);
                    //  iv_profile.setEnabled(true);
                    tv_edit.setText("Save");
                    toggle = true;
                }

            }
        });


        iv_imageedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ApplicationUtils.isNetworkConnected(SettingProfileEditingActivity.this)) {
                    Toast.makeText(SettingProfileEditingActivity.this, "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    return;
                }


                Dialog dialog = new Dialog(SettingProfileEditingActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_update_name);

                EditText userName = dialog.findViewById(R.id.etRiderName);
                Button button = dialog.findViewById(R.id.confirmRiderbtn);
                userName.setText(shopOwnername.getText().toString());


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (!ApplicationUtils.isNetworkConnected(SettingProfileEditingActivity.this)) {
                            Toast.makeText(SettingProfileEditingActivity.this, "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        LoadingDialog loadingDialog1 = new LoadingDialog(SettingProfileEditingActivity.this);
                        UpdateUserAndImage updateUserAndImage = new UpdateUserAndImage();
                        updateUserAndImage.setFirstName(userName.getText().toString());
                        updateUserAndImage.setMiddleName(" ");
                        updateUserAndImage.setLastName(" ");
                        updateUserAndImage.setProfileId(Constant.PROFILE_ID);
                        updateUserAndImage.setProfilePic(TextUtils.isEmpty(filePath) ? "" : filePath);


                        if (TextUtils.isEmpty(filePath)) { //run when only name is given........

                            shopRegistrationViewModel.userName(updateUserAndImage, SettingProfileEditingActivity.this);
                            shopRegistrationViewModel.getUserName().observe(SettingProfileEditingActivity.this, new Observer<UpdateUserAndImageResponse>() {
                                @Override
                                public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                                    if (updateUserAndImageResponse != null) {
                                        Toast.makeText(SettingProfileEditingActivity.this, "Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                                        loadingDialog1.dismissDialog();
                                        shopOwnername.setText(userName.getText().toString());
                                        shopRegistrationViewModel.getUserName().removeObservers(SettingProfileEditingActivity.this);
                                    } else {

                                    }
                                }

                            });
                        }


                        dialog.dismiss();


                    }
                });

                dialog.show();



              /*  if (TextUtils.isEmpty(shopOwnername.getText().toString())) {
                    Toast.makeText(SettingProfileEditingActivity.this, "User name can not be empty...", Toast.LENGTH_SHORT).show();
                    return;
                } else {


                    UpdateUserAndImage updateUserAndImage = new UpdateUserAndImage();
                    updateUserAndImage.setFirstName(shopOwnername.getText().toString());
                    updateUserAndImage.setMiddleName(" ");
                    updateUserAndImage.setLastName(" ")
                    updateUserAndImage.setProfileId(Constant.PROFILE_ID);
                    updateUserAndImage.setProfilePic(TextUtils.isEmpty(filePath) ? "" : filePath);
                    iv_imageedit.setVisibility(View.GONE);
                    tv_edit.setVisibility(View.VISIBLE);
                    toggle = true;
.

                    if (TextUtils.isEmpty(filePath)) { //run when only name is given........

                        shopRegistrationViewModel.userName(updateUserAndImage, SettingProfileEditingActivity.this);
                        shopRegistrationViewModel.getUserName().observe(SettingProfileEditingActivity.this, new Observer<UpdateUserAndImageResponse>() {
                            @Override
                            public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                                if (updateUserAndImageResponse != null) {
                                    Toast.makeText(SettingProfileEditingActivity.this, "Profile updated successfully...", Toast.LENGTH_SHORT).show();
                                } else {

                                }
                            }
                        });
                    } else {


                        shopRegistrationViewModel.userImageandName(updateUserAndImage, SettingProfileEditingActivity.this);
                        shopRegistrationViewModel.getUpdateUserImageResponse().observe(SettingProfileEditingActivity.this, new Observer<UpdateUserAndImageResponse>() {
                            @Override
                            public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                                if (updateUserAndImageResponse != null) {
                                    Toast.makeText(SettingProfileEditingActivity.this, "Profile updated successfully...", Toast.LENGTH_SHORT).show();
                                } else {

                                }
                            }
                        });
                    }
                      }

*/

            }
        });

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });

        tab1 = findViewById(R.id.tab1);
        tab1.getTabAt(0).select();
        if (!ApplicationUtils.isNetworkConnected(SettingProfileEditingActivity.this)) {
            Toast.makeText(SettingProfileEditingActivity.this, "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

            return;
        } else {
            // tab1.getTabAt(0).select();

            tab1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    switch (tab.getPosition()) {
                        case 0:
                            if (!ApplicationUtils.isNetworkConnected(SettingProfileEditingActivity.this)) {
                                Toast.makeText(SettingProfileEditingActivity.this, "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            navController.navigate(R.id.storeSettingFragment);
                            break;
                        case 1:
                            if (!ApplicationUtils.isNetworkConnected(SettingProfileEditingActivity.this)) {
                                Toast.makeText(SettingProfileEditingActivity.this, "" + getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            navController.navigate(R.id.bankSettingFragment);
                            break;
                    }
              /*  if (tab.getPosition() == 0) {
                    navController.navigate(R.id.storeSettingFragment);
                } else if (tab.getPosition() == 1) {
                    navController.navigate(R.id.bankSettingFragment);
                }*/
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GetShopDetail getShopDetail = new GetShopDetail();
        getShopDetail.setProfileId(Constant.PROFILE_ID);
        storeSettingViewModel.postShopDetail(getShopDetail, SettingProfileEditingActivity.this);
        storeSettingViewModel.getShopDetail().observe(SettingProfileEditingActivity.this, new Observer<GetShopDetailResponse>() {
            @Override
            public void onChanged(GetShopDetailResponse getShopDetailResponse) {
                if (getShopDetailResponse != null) {
                    // Toast.makeText(getActivity(), "" + getShopDetailResponse.getStatusDetail() + getShopDetailResponse.getData().getRequestList().getBranchAddress().size(), Toast.LENGTH_SHORT).show();

                    shopOwnername.setText(getShopDetailResponse.getData().getRequestList().getShopOwnerName());

                    if (shopOwnername.getText().toString().equals(" ")) {
                        shopOwnername.setHint("Your Name Required...");
                    }

                    if (!getShopDetailResponse.getData().getRequestList().getShopRating().equals("")) {
                        DecimalFormat df = new DecimalFormat("#.#");
                        df.setRoundingMode(RoundingMode.CEILING);
                        rating_number.setText(df.format(Double.parseDouble(getShopDetailResponse.getData().getRequestList().getShopRating())));
                    } else
                        rating_number.setText("No Rating");

                    RequestOptions myOptions = new RequestOptions()
                            .override(100, 100);


                    if (!getShopDetailResponse.getData().getRequestList().getShopOwnerImage().equals("")) {
                        Glide.with(SettingProfileEditingActivity.this)
                                .asBitmap()
                                .apply(myOptions)
                                .load(getShopDetailResponse.getData().getRequestList().getShopOwnerImage())
                                .into(iv_profile);
                    }

                  /*  Glide.with(SettingProfileEditingActivity.this).
                            load(getShopDetailResponse.getData().getRequestList().getShopOwnerImage()).
                            thumbnail(0.05f).
                            placeholder(R.drawable.ic_avatar)
                            .into(iv_profile);*/

                }
            }
        });

    }

    private void checkPermissions() {
        dialogImage = new Dialog(SettingProfileEditingActivity.this);
        dialogImage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogImage.setContentView(R.layout.dialog_upload_capture);

        Button upload = dialogImage.findViewById(R.id.uploadImage);
        Button capture = dialogImage.findViewById(R.id.captureImage);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SettingProfileEditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) +
                        ContextCompat.checkSelfPermission(SettingProfileEditingActivity.this, Manifest.permission.CAMERA) +
                        ContextCompat.checkSelfPermission(SettingProfileEditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(SettingProfileEditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(SettingProfileEditingActivity.this, Manifest.permission.CAMERA) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(SettingProfileEditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingProfileEditingActivity.this);
                        builder.setTitle("Grant those permissions...");
                        builder.setMessage("Camera and External Storage Permission Required...");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
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
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else { //ye lines for the very first time chalein gy jab app start hongy
                        ActivityCompat.requestPermissions(SettingProfileEditingActivity.this, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                if (ContextCompat.checkSelfPermission(SettingProfileEditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) +
                        ContextCompat.checkSelfPermission(SettingProfileEditingActivity.this, Manifest.permission.CAMERA) +
                        ContextCompat.checkSelfPermission(SettingProfileEditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(SettingProfileEditingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(SettingProfileEditingActivity.this, Manifest.permission.CAMERA) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(SettingProfileEditingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingProfileEditingActivity.this);
                        builder.setTitle("Grant those permissions...");
                        builder.setMessage("Camera External Storage Permission Required...");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(SettingProfileEditingActivity.this, new String[]{
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
                        ActivityCompat.requestPermissions(SettingProfileEditingActivity.this, new String[]{
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

    private void openGallery() {
        //  Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //  startActivityForResult(gallery, UPLOAD_IMAGE);
        Intent intent = new Intent(SettingProfileEditingActivity.this, Gallery.class);
        intent.putExtra("title", "Select media");
        intent.putExtra("mode", 1); //try on 1 and 3
        intent.putExtra("maxSelection", 1);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, UPLOAD_IMAGE);
    }

    private void openCamera() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, CAPTURE_IMAGE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UPLOAD_IMAGE) { //Upload image from gallery

            List<Uri> uris = null;
            ArrayList<MediaAttachment> attachments = null;
            if (uris == null) {
                uris = new ArrayList<>();
            }
            if (attachments == null) {
                attachments = new ArrayList<>();
            }
            attachments = data.getParcelableArrayListExtra("result");

            iv_profile.setImageURI(attachments.get(0).getFileUri());
            //filePath = getRealPathFromURI(attachments.get(0).getFileUri());
            filePath = attachments.get(0).getFileUri().getPath();
            Log.i("TAG", "onActivityResult: Capture Capture Path" + filePath);

            UpdateUserAndImage updateUserAndImage = new UpdateUserAndImage();
            updateUserAndImage.setFirstName(shopOwnername.getText().toString());
            updateUserAndImage.setMiddleName(" ");
            updateUserAndImage.setLastName(" ");
            updateUserAndImage.setProfileId(Constant.PROFILE_ID);
            updateUserAndImage.setProfilePic(TextUtils.isEmpty(filePath) ? "" : filePath);

            shopRegistrationViewModel.userImageandName(updateUserAndImage, this);
            shopRegistrationViewModel.getUpdateUserImageResponse().observe(this, new Observer<UpdateUserAndImageResponse>() {
                @Override
                public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                    if (updateUserAndImageResponse != null) {
                        Toast.makeText(SettingProfileEditingActivity.this, "Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                        shopRegistrationViewModel.getUpdateUserImageResponse().removeObservers(SettingProfileEditingActivity.this);
                    } else {

                    }
                }
            });

            /*        imageUri = data.getData();
            iv_profile.setImageURI(imageUri);
            filePath = getPath(SettingProfileEditingActivity.this, imageUri);
            Log.i("TAG", "onActivityResult: Gallery Upload Path" + filePath);

            //  if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
            iv_profile.setImageBitmap(resized);
            // }*/

        } else if (resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE) {

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
        /*        Uri selectedImage = data.getData();
                iv_profile.setImageURI(selectedImage);
                // filePath = getRealPathFromURI(selectedImage);
                filePath = getPath(SettingProfileEditingActivity.this, selectedImage);

                Log.i("TAG", "onActivityResult: Capture Capture Path" + filePath);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                iv_profile.setImageBitmap(resized);*/
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                iv_profile.setImageBitmap(photo);
                imageUri = getImageUri(SettingProfileEditingActivity.this, photo);
                filePath = getRealPathFromURI(imageUri);
                Log.i("TAG", "onActivityResult: Capture Path : " + filePath);
                iv_profile.setImageURI(imageUri);

                UpdateUserAndImage updateUserAndImage = new UpdateUserAndImage();
                updateUserAndImage.setFirstName(shopOwnername.getText().toString());
                updateUserAndImage.setMiddleName(" ");
                updateUserAndImage.setLastName(" ");
                updateUserAndImage.setProfileId(Constant.PROFILE_ID);
                updateUserAndImage.setProfilePic(TextUtils.isEmpty(filePath) ? "" : filePath);

                shopRegistrationViewModel.userImageandName(updateUserAndImage, this);
                shopRegistrationViewModel.getUpdateUserImageResponse().observe(this, new Observer<UpdateUserAndImageResponse>() {
                    @Override
                    public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                        if (updateUserAndImageResponse != null) {
                            Toast.makeText(SettingProfileEditingActivity.this, "Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                            shopRegistrationViewModel.getUpdateUserImageResponse().removeObservers(SettingProfileEditingActivity.this);
                        } else {

                        }
                    }
                });

            } else { //other than marshmallow
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                iv_profile.setImageBitmap(photo);
                imageUri = getImageUri(SettingProfileEditingActivity.this, photo);
                filePath = getRealPathFromURI(imageUri);
                Log.i("TAG", "onActivityResult: Capture Path : " + filePath);
                iv_profile.setImageURI(imageUri);
               /* imageUri = data.getData();

                filePath = getPath(SettingProfileEditingActivity.this, imageUri);*/

                UpdateUserAndImage updateUserAndImage = new UpdateUserAndImage();
                updateUserAndImage.setFirstName(shopOwnername.getText().toString());
                updateUserAndImage.setMiddleName(" ");
                updateUserAndImage.setLastName(" ");
                updateUserAndImage.setProfileId(Constant.PROFILE_ID);
                updateUserAndImage.setProfilePic(TextUtils.isEmpty(filePath) ? "" : filePath);

                shopRegistrationViewModel.userImageandName(updateUserAndImage, this);
                shopRegistrationViewModel.getUpdateUserImageResponse().observe(this, new Observer<UpdateUserAndImageResponse>() {
                    @Override
                    public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                        if (updateUserAndImageResponse != null) {
                            Toast.makeText(SettingProfileEditingActivity.this, "Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                            shopRegistrationViewModel.getUpdateUserImageResponse().removeObservers(SettingProfileEditingActivity.this);
                        } else {

                        }
                    }
                });
            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.personalInfoRL) {
            // navController.navigate(R.id.editingProfileFragment);

            tv_personal.setTextColor(Color.parseColor("#50D4BF"));
            tv_storesettings.setTextColor(Color.parseColor("#000000"));
            tv_bank.setTextColor(Color.parseColor("#000000"));

            horizontalLine2.setVisibility(View.GONE);
            horizontalLine1.setVisibility(View.VISIBLE);
            horizontalLine3.setVisibility(View.GONE);

        } else if (v.getId() == R.id.storeSettingRL) {
            navController.navigate(R.id.storeSettingFragment);
            Toast.makeText(this, "clicked...", Toast.LENGTH_SHORT).show();

            tv_personal.setTextColor(Color.parseColor("#000000"));
            tv_storesettings.setTextColor(Color.parseColor("#50D4BF"));
            tv_bank.setTextColor(Color.parseColor("#000000"));

            horizontalLine2.setVisibility(View.VISIBLE);
            horizontalLine1.setVisibility(View.GONE);
            horizontalLine3.setVisibility(View.GONE);

        } else if (v.getId() == R.id.bankRL) {
            navController.navigate(R.id.bankSettingFragment);

            tv_personal.setTextColor(Color.parseColor("#000000"));
            tv_storesettings.setTextColor(Color.parseColor("#000000"));
            tv_bank.setTextColor(Color.parseColor("#50D4BF"));

            horizontalLine2.setVisibility(View.GONE);
            horizontalLine1.setVisibility(View.GONE);
            horizontalLine3.setVisibility(View.VISIBLE);
        }
    }

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
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}