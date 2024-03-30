package com.rkvit.arogyalok.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.razorpay.PaymentResultListener;
import com.rkvit.arogyalok.Fragments.HomeFragment;
import com.rkvit.arogyalok.Fragments.ListFragment;
import com.rkvit.arogyalok.Fragments.MedCartFragment;
import com.rkvit.arogyalok.Fragments.MedSearchFragment;
import com.rkvit.arogyalok.Fragments.SearchFragment;
import com.rkvit.arogyalok.Fragments.WebLoginFragment;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.rkvit.arogyalok.Utils.HelperActivity;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class MainHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, PaymentResultListener {

    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    boolean isFABOpen = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private AutoCompleteTextView searchView;
    private String SearchPlace;
    private Fragment fragment;
    private List<String> cityList = new ArrayList<>();
    private List<String> cityId = new ArrayList<>();
    private BottomSheetDialog dialog;
    private LinearLayout docApt, ambulanceApt, labApt, medicineApt, bloodBankApt, labSchemeApt;
    private int GALLERY = 300;
    private Uri contentURI;
    private Bitmap bitmap;
    private String tempUri;
    private File finalFile;
    private String currentDateTimeString;
    private MultipartBody.Part body;

    public static String getImageUUID() {
        return UUID.randomUUID().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HelperActivity(this).UpdateApp();
    }

    @SuppressLint({"CommitPrefEdits", "NewApi", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headView = navigationView.getHeaderView(0);

        TextView name = headView.findViewById(R.id.user_name);

        if (!MyApplication.getUserName().isEmpty()) {
            name.setText("Welcome\n" + MyApplication.getUserName());
            fragment = new HomeFragment();
        } else {
            navigationView.getMenu().findItem(R.id.upld_prescription).setVisible(false);
            navigationView.getMenu().findItem(R.id.btn_home).setVisible(false);
            navigationView.getMenu().findItem(R.id.my_appoitment).setVisible(false);
            navigationView.getMenu().findItem(R.id.my_cart).setVisible(false);
            name.setText("Hey! Welcome");
            fragment = new WebLoginFragment();
        }

        loadFragment(fragment, "HomeFrag");

        FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getFragmentManager().getBackStackEntryCount() == 0) finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_home);
        MenuItemCompat.setActionView(menuItem, R.layout.actionbar_logo);
        View actionView = MenuItemCompat.getActionView(menuItem);

        Spinner searchType = actionView.findViewById(R.id.search_type);

        EditText search = actionView.findViewById(R.id.search_edittext);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //hide softkey
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }

                    String query = search.getText().toString();
                    if (!searchType.getSelectedItem().toString().equals("Select"))
                        searchFragment(query, searchType.getSelectedItem().toString());
                    else
                        Toast.makeText(MainHome.this, "Select search type", Toast.LENGTH_SHORT).show();

                    return true;
                }
                return false;
            }
        });

        return true;
    }

    //Location List Sharedpref
    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(key, json);
    }

    public void set(String key, String value) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void loadFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void searchFragment(String key, String tag) {
        // load fragment
        SharedPreferences pref = getSharedPreferences("menu", Context.MODE_PRIVATE);
        pref.edit().putString("key", key)
                .putString("loadList", tag)
                .apply();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!tag.equals("Medicine")) {

            transaction.replace(R.id.mainFrame, new SearchFragment(), tag);
        } else {
            transaction.replace(R.id.mainFrame, new MedSearchFragment(), tag);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_home) {
            loadFragment(fragment, "HomeFrag");

        } else if (id == R.id.upld_prescription) {

            uploadPres();

        } else if (id == R.id.my_appoitment) {

            openAppointment();

        } else if (id == R.id.my_cart) {

            Fragment fragment = new MedCartFragment();
            loadFragment(fragment, "CartFrag");

        } else if (id == R.id.nav_tnc) {

            Intent i = new Intent(MainHome.this, WebView.class);
            i.putExtra("Url", Constant.TnC_Url);
            startActivity(i);

        } else if (id == R.id.nav_privacy) {

//            Intent i = new Intent(MainActivity.this, TnCWebView.class);
//            i.putExtra("Url", Constant.Privacy_Url);
//            startActivity(i);

        } else if (id == R.id.nav_share) {
            new HelperActivity(this).shareApp();

        } else if (id == R.id.nav_rateus) {
            new HelperActivity((this)).rateUs();

        } else if (id == R.id.logout) {

//            SharedPreferences pref = getSharedPreferences("UserData", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = pref.edit();
//            editor.clear().apply();
            MyApplication.clearPref();
            FancyToast.makeText(this, "Logged Out!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            startActivity(new Intent(this, LoginActivity.class));

            SharedPreferences loginPref = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
            loginPref.edit().clear().apply();


        } else if (id == R.id.info) {

            Intent i = new Intent(MainHome.this, WebView.class);
            i.putExtra("Url", Constant.About_Us);
            startActivity(i);

//            startActivity(new Intent(this, AboutUs.class));

        } else if (id == R.id.blog) {

            Intent i = new Intent(MainHome.this, WebView.class);
            i.putExtra("Url", Constant.Blog);
            startActivity(i);
        } else if (id == R.id.news) {

            Intent i = new Intent(MainHome.this, WebView.class);
            i.putExtra("Url", Constant.News);
            startActivity(i);

        } else if (id == R.id.nav_contactus) {

            new FancyAlertDialog.Builder(MainHome.this)
                    .setIcon(R.mipmap.logo_round, Icon.Visible)
                    .isCancellable(true)
                    .setTitle("Contact us")
                    .setMessage(Constant.Address)
                    .setPositiveBtnText("Call")
                    .setPositiveBtnBackground(R.color.bg_screen2)
                    .OnPositiveClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            new HelperActivity(MainHome.this).makeCall(Constant.ContactNo);
                        }
                    })
                    .setNegativeBtnText("Mail")
                    .setNegativeBtnBackground(R.color.bg_screen4)
                    .OnNegativeClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            new HelperActivity(MainHome.this).sendEmail(Constant.MailId);
                        }
                    })
                    .build();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void openAppointment() {

        dialog = new BottomSheetDialog(MainHome.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.apt_his_layout, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        docApt = dialogView.findViewById(R.id.doc_apt);
        docApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("doc_apt");
            }
        });

        ambulanceApt = dialogView.findViewById(R.id.ambulance_apt);
        ambulanceApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("amb_apt");
            }
        });

        labApt = dialogView.findViewById(R.id.lab__apt);
        labApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("lab_apt");
            }
        });
        bloodBankApt = dialogView.findViewById(R.id.blood_bank__apt);
        bloodBankApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("blood_apt");
            }
        });

        medicineApt = dialogView.findViewById(R.id.medicine__apt);
        medicineApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("medicine_apt");
            }
        });

        labSchemeApt = dialogView.findViewById(R.id.lab_scheme_apt);
        labSchemeApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("lab_scheme_apt");
            }
        });

    }


    private void addFragment(String list) {

        SharedPreferences pre = getSharedPreferences("menu", Context.MODE_PRIVATE);
        pre.edit().putString("loadList", list).apply();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, new ListFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

//        if (getFragmentManager().getBackStackEntryCount() != 0) {
//            getFragmentManager().popBackStack();
//        }

//        FragmentManager fm = getSupportFragmentManager();
//        fm.popBackStack();


//        int count = getSupportFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getSupportFragmentManager().popBackStack();
//        }


    }


    @Override
    public void onPaymentSuccess(String s) {

//        Toast.makeText(this, "Your details has been sent.", Toast.LENGTH_LONG).show();

        Toast.makeText(this, "Online Payment Success>> " + s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, "Failure>> " + s, Toast.LENGTH_SHORT).show();

    }


    private void uploadPres() {
        new FancyAlertDialog.Builder(MainHome.this)
                .setTitle("Upload Prescription")
                .setMessage("Upload your prescription to get medicine delivered at door step")
                .setIcon(R.mipmap.logo_round, Icon.Visible)
                .setPositiveBtnText("Yes")
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        addPic();
                    }
                })
                .setNegativeBtnText("No")
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        new FancyAlertDialog.Builder(MainHome.this).isCancellable(true);
                    }
                })
                .build();
    }

//--------------------------Add Image-----------------------------------

    @SuppressLint("CheckResult")
    private void addPic() {

        new RxPermissions(this)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE) // ask single or multiple permission once
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                        showPictureDialog();
                    } else {
                        // At least one permission is denied
                    }
                });
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);

//        int preference = ScanConstants.OPEN_MEDIA;
//        Intent intent = new Intent(this, ScanActivity.class);
//        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
//        startActivityForResult(intent, GALLERY);

    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

//        int preference = ScanConstants.OPEN_CAMERA;
//        Intent intent = new Intent(this, ScanActivity.class);
//        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
//        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                contentURI = data.getData();

                Log.v("imgUri", contentURI.toString());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    Log.v("imgBitmap", bitmap.toString());

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    tempUri = getImagePath(this, bitmap);


                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    finalFile = new File(tempUri);

                    sendQuery();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {

            bitmap = (Bitmap) data.getExtras().get("data");

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            tempUri = getImagePath(null, bitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile = new File(tempUri);


            sendQuery();

        }
    }

    public String getImagePath(Context inContext, Bitmap inImage) {

        getCurrentTime();

        String fiePath = "";
        try {
//            String filePath = Environment.getExternalStorageDirectory() + "/" + "dhruv_iconic";
//            File dir = new File(filePath);

            String filePath = getExternalFilesDir(null).getAbsolutePath();
            File dir = new File(filePath + getResources().getString(R.string.app_name));
            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir, MyApplication.getUserId() + "_" + currentDateTimeString.trim() + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            inImage.compress(Bitmap.CompressFormat.PNG, 50, fOut);

            fiePath = file.getAbsolutePath();
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fiePath;
    }

    @SuppressLint("SimpleDateFormat")
    public void getCurrentTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

        currentDateTimeString = sdf.format(d);

        Log.d("CurrentDate", currentDateTimeString);
    }

    private void sendQuery() {

        if (finalFile != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("//multipart/form-data"), finalFile);
            body = MultipartBody.Part.createFormData("prescription", finalFile.getName(), requestBody);
        }

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Uploading Prescription... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();


        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.upldPriscription(MyApplication.getUserId(), body)
                .enqueue(new Callback<BookingMsgModel>() {
                    @Override
                    public void onResponse(Call<BookingMsgModel> call, Response<BookingMsgModel> response) {
                        progress.cancel();

                        if (response.isSuccessful()) {

                            FancyToast.makeText(MainHome.this, "Prescription Uploaded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong, try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingMsgModel> call, Throwable t) {
                        progress.cancel();
                        Log.e("error ", t.getMessage());

                    }
                });
    }
}

