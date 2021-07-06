package com.tilismtech.tellotalk_shopping_sdk.ui_seller.settingprofileediting;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.tilismtech.tellotalk_shopping_sdk.R;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.GetShopDetail;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.ShopBasicSetting;
import com.tilismtech.tellotalk_shopping_sdk.pojos.requestbody.UpdateUserAndImage;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.GetShopDetailResponse;
import com.tilismtech.tellotalk_shopping_sdk.pojos.responsebody.UpdateUserAndImageResponse;
import com.tilismtech.tellotalk_shopping_sdk.receiver.NetworkReceiver;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.shopregistration.ShopRegistrationViewModel;
import com.tilismtech.tellotalk_shopping_sdk.ui_seller.storesetting.StoreSettingViewModel;
import com.tilismtech.tellotalk_shopping_sdk.utils.Constant;
import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;
import com.tilismtech.tellotalk_shopping_sdk.utils.NoInternetDetection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SettingProfileEditingActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout personalInfoRL, storeSettingRL, bankRL;
    NavHostFragment navHostFragment;
    NavController navController;
    View horizontalLine1, horizontalLine2, horizontalLine3;
    TextView tv_storesettings, tv_bank, tv_personal, rating_number;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile_editing);
        initViews();
    }

    public void initViews() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        personalInfoRL = findViewById(R.id.personalInfoRL);
        storeSettingRL = findViewById(R.id.storeSettingRL);
        bankRL = findViewById(R.id.bankRL);
        shopOwnername = findViewById(R.id.user_name);
        rating_number = findViewById(R.id.rating_number);

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
        iv_imageedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(shopOwnername.getText().toString())) {
                    Toast.makeText(SettingProfileEditingActivity.this, "User name can not be empty...", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateUserAndImage updateUserAndImage = new UpdateUserAndImage();
                    updateUserAndImage.setFirstName(shopOwnername.getText().toString());
                    updateUserAndImage.setMiddleName(" ");
                    updateUserAndImage.setLastName(" ");
                    updateUserAndImage.setProfileId(Constant.PROFILE_ID);
                    updateUserAndImage.setProfilePic(filePath);
                    shopRegistrationViewModel.getUpdateUserImageResponse().removeObservers(SettingProfileEditingActivity.this); //need to remove observer here to stop multiple call of apis
                    LoadingDialog loadingDialog = new LoadingDialog(SettingProfileEditingActivity.this);
                    loadingDialog.showDialog();
                    shopRegistrationViewModel.userImageandName(updateUserAndImage);
                    shopRegistrationViewModel.getUpdateUserImageResponse().observe(SettingProfileEditingActivity.this, new Observer<UpdateUserAndImageResponse>() {
                        @Override
                        public void onChanged(UpdateUserAndImageResponse updateUserAndImageResponse) {
                            if (updateUserAndImage != null) {
                                Toast.makeText(SettingProfileEditingActivity.this, updateUserAndImageResponse.getStatusDetail(), Toast.LENGTH_SHORT);
                                loadingDialog.dismissDialog();
                            }
                            loadingDialog.dismissDialog();
                        }
                    });
                }
            }
        });

        iv_profile = findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });

        tab1 = findViewById(R.id.tab1);
        tab1.getTabAt(0).select();
        tab1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        navController.navigate(R.id.storeSettingFragment);
                        break;
                    case 1:
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

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GetShopDetail getShopDetail = new GetShopDetail();
        getShopDetail.setProfileId(Constant.PROFILE_ID);
        storeSettingViewModel.postShopDetail(getShopDetail);
        storeSettingViewModel.getShopDetail().observe(SettingProfileEditingActivity.this, new Observer<GetShopDetailResponse>() {
            @Override
            public void onChanged(GetShopDetailResponse getShopDetailResponse) {
                if (getShopDetailResponse != null) {
                    // Toast.makeText(getActivity(), "" + getShopDetailResponse.getStatusDetail() + getShopDetailResponse.getData().getRequestList().getBranchAddress().size(), Toast.LENGTH_SHORT).show();

                    shopOwnername.setText(getShopDetailResponse.getData().getRequestList().getShopOwnerName());

                    if (shopOwnername.getText().toString().equals(" ")) {
                        shopOwnername.setHint("Your Name Required...");
                    }
                    rating_number.setText(getShopDetailResponse.getData().getRequestList().getShopRating());

                    Glide.with(SettingProfileEditingActivity.this).
                            load(getShopDetailResponse.getData().getRequestList().getShopOwnerImage()).
                            thumbnail(0.05f).
                            into(iv_profile);

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
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, UPLOAD_IMAGE);
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
            imageUri = data.getData();
            iv_profile.setImageURI(imageUri);
            filePath = getPath(SettingProfileEditingActivity.this, imageUri);
            Log.i("TAG", "onActivityResult: Gallery Upload Path" + filePath);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                iv_profile.setImageBitmap(resized);
            }


        } else if (resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE) {

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                Uri selectedImage = data.getData();
                iv_profile.setImageURI(selectedImage);
                filePath = getRealPathFromURI(selectedImage);
                Log.i("TAG", "onActivityResult: Capture Capture Path" + filePath);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                iv_profile.setImageBitmap(resized);
            } else { //other than marshmallow
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                iv_profile.setImageBitmap(photo);
                imageUri = getImageUri(SettingProfileEditingActivity.this, photo);
                filePath = getRealPathFromURI(imageUri);
                Log.i("TAG", "onActivityResult: Capture Path : " + filePath);
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