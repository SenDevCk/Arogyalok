package com.rkvit.arogyalok.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Utils.GPSTracker;
import com.rkvit.arogyalok.Model.StateModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.BuildConfig;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class AmbBookingActivity extends AppCompatActivity{

    private EditText patientName, age, condition, userCity, pickupPoint, dropPoint, aptDate, addrress, patientMobile;
    private Spinner bloodGroup;
    private AutoCompleteTextView pincode, stateDropdown, cityDropdown;
    private TextView addReport, reportName;
    private List<StateModel> stateList = new ArrayList<>();
    private List<String> stateName = new ArrayList<>();
    private List<String> stateId = new ArrayList<>();

    private ApiInterface apiInterface;
    private List<CityModel> CityList = new ArrayList<>();
    private List<String> cityName = new ArrayList<>();
    private List<String> cityId = new ArrayList<>();
    private String StateId, CityId;
    private boolean valid;
    private String PatCondition, AptDate, UserAddress, Pincode, PickupPoint, DropPoint, UserName, UserMobile, UserAge;
    private LocationManager locManager;
    private Criteria criteria;
    private String provider;
    private LocationListener mylistener;
    private int GALLERY = 100;
    private Uri contentURI;
    private Bitmap bitmap;
    private String tempUri;
    private String currentDateTimeString;
    private File finalFile;
    private ProgressDialog locationProgress;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private double Lat, Longi;
    private List<Address> addresses;
    private String address;
    private MultipartBody.Part body;

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
//    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    private GPSTracker gps;
    private double latitude, longitude;
    private String mLastUpdateTime;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amb_booking);

//        final LocationManager manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
////            onGps();

        initLocation();

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        getState();

        patientName = findViewById(R.id.name);
        patientName.setText(MyApplication.getUserName());

        age = findViewById(R.id.age);
        patientMobile = findViewById(R.id.mobile);
        patientMobile.setText(MyApplication.getMobile());
//        userCity = findViewById(R.id.place);
        condition = findViewById(R.id.condition);
        bloodGroup = findViewById(R.id.blood_group);

        pickupPoint = findViewById(R.id.pickupPoint);
        dropPoint = findViewById(R.id.dropPoint);

        pincode = findViewById(R.id.pincode);

        aptDate = findViewById(R.id.date);
        addrress = findViewById(R.id.address);

        addReport = findViewById(R.id.add_report);
        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPic();
            }
        });

        reportName = findViewById(R.id.report_name);


        stateDropdown = findViewById(R.id.state_dropdown);
        stateDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AmbBookingActivity.this, android.R.layout.simple_dropdown_item_1line, stateName);
                stateDropdown.setAdapter(adapter);
                stateDropdown.showDropDown();
                stateDropdown.setFocusable(false);
                stateDropdown.setCursorVisible(false);

            }
        });
        stateDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                String StateName = parent.getItemAtPosition(position).toString();
                StateId = stateId.get(position);
                stateDropdown.setText(StateName);
                getCity(StateId);
            }
        });

        pincode = findViewById(R.id.pincode);

        cityDropdown = findViewById(R.id.city_dropdown);
        cityDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityDropdown.showDropDown();
//                cityDropdown.setFocusable(false);
//                cityDropdown.setCursorVisible(false);

            }
        });
        cityDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                String CitytName = parent.getItemAtPosition(position).toString();
                CityId = cityId.get(position);
                cityDropdown.setText(CitytName);
            }
        });


        aptDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar myCalendar = Calendar.getInstance();

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String myFormat = "dd-MM-yyyy"; //In which you need put here  //game_date=2020/12/12
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                        aptDate.setText(sdf.format(myCalendar.getTime()));

                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(AmbBookingActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                //Max Date
                Calendar maxCal = Calendar.getInstance();
                maxCal.set(myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH) + 15);
                datePickerDialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());

                //Min Date
                Calendar minCal = Calendar.getInstance();
                minCal.set(myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(minCal.getTimeInMillis());

                datePickerDialog.show();


            }
        });

        Button bookNow = findViewById(R.id.book_now);
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validData()) {
                    getLatLong();
                    sendQuery();
                }
            }
        });

    }

    public void onGps() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(AmbBookingActivity.this,
                R.style.AppTheme));

        alertDialogBuilder.setTitle("Enable GPS");
        alertDialogBuilder.setMessage("To mark attendance please enable GPS");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }

    private boolean validData() {

        UserName = patientName.getText().toString();
        UserMobile = patientMobile.getText().toString();
        PatCondition = condition.getText().toString();
        AptDate = aptDate.getText().toString();
        Pincode = pincode.getText().toString();
        PickupPoint = pickupPoint.getText().toString();
        DropPoint = dropPoint.getText().toString();
//        stateDropdown.getText().toString();
        UserAddress = addrress.getText().toString();

        valid = true;

        if (UserName.isEmpty()) {
            patientName.setError("required");
            patientName.requestFocus();
            valid = false;
        }

        if (PatCondition.isEmpty()) {
            condition.setError("required");
            condition.requestFocus();
            valid = false;
        }

        if (UserMobile.isEmpty()) {
            patientMobile.setError("required");
            patientMobile.requestFocus();
            valid = false;
        }

        if (Pincode.isEmpty()) {
            pincode.setError("required");
            pincode.requestFocus();
            valid = false;
        }

        if (PickupPoint.isEmpty()) {
            pickupPoint.setError("required");
            pickupPoint.requestFocus();
            valid = false;
        }

        if (DropPoint.isEmpty()) {
            dropPoint.setError("required");
            dropPoint.requestFocus();
            valid = false;
        }

        if (stateDropdown.getText().toString().isEmpty()) {
            stateDropdown.setError("required");
            stateDropdown.requestFocus();
            valid = false;
        }

        if (cityDropdown.getText().toString().isEmpty() || !cityName.contains(cityDropdown.getText().toString())) {
            cityDropdown.setError("required");
            cityDropdown.requestFocus();
            valid = false;
        }

//        if (UserAddress.isEmpty()) {
//            addrress.setError("required");
//            addrress.requestFocus();
//            valid = false;
//        }

        if (AptDate.isEmpty()) {
            aptDate.setError("required");
            aptDate.requestFocus();
            valid = false;
        }
        return valid;
    }

    private void sendQuery() {

        if (finalFile != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("//multipart/form-data"), finalFile);
            body = MultipartBody.Part.createFormData("prescription", finalFile.getName(), requestBody);
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            body = MultipartBody.Part.createFormData("prescription", "", requestBody);
        }

        ProgressDialog progress = new ProgressDialog(AmbBookingActivity.this);
        progress.setMessage("Sending... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();


        String AmbId = getIntent().getStringExtra("AmbId");
        String AgencyId = getIntent().getStringExtra("AgencyId");

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.ambBooking(AmbId, AgencyId, UserName, UserMobile, "", bloodGroup.getSelectedItem().toString(),
                PatCondition, body, aptDate.getText().toString(), PickupPoint, String.valueOf(latitude), String.valueOf(longitude), DropPoint, addrress.getText().toString(), pincode.getText().toString(), MyApplication.getUserId())
                .enqueue(new Callback<BookingMsgModel>() {
                    @Override
                    public void onResponse(Call<BookingMsgModel> call, Response<BookingMsgModel> response) {
                        progress.cancel();

                        if (response.isSuccessful() && response.body().getStatus()) {

//                            saveUserData();

                            finish();

//                            if (paymentMode.getSelectedItem().toString().equalsIgnoreCase("Online")) {
////                                Intent i = new Intent(getActivity(), PaymentActivity.class);
////                                i.putExtra("amount", pref.getString("fee", null));
////                                startActivity(i);
////                                payOnline();
//                            } else
                            Toast.makeText(AmbBookingActivity.this, "Appointment Done.", Toast.LENGTH_LONG).show();

                        } else {
                            finish();
                            Toast.makeText(AmbBookingActivity.this, "response Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingMsgModel> call, Throwable t) {
                        finish();
                        progress.cancel();
                        Log.e("error ", t.getMessage());

                    }
                });
    }


    private void getState() {

        stateList.clear();
        stateName.clear();
        stateId.clear();
        apiInterface.getState().enqueue(new Callback<List<StateModel>>() {
            @Override
            public void onResponse(Call<List<StateModel>> call, Response<List<StateModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    stateList = response.body();
                    for (int i = 0; i < stateList.size(); i++) {
//                        hashcityList.put(cityList.get(i).getId(), cityList.get(i).getName());
                        stateName.add(stateList.get(i).getName());
                        stateId.add(stateList.get(i).getId());
                    }

                } else {
//                    avi.hide();
//                    layoutView.setVisibility(View.VISIBLE);
                    Toast.makeText(AmbBookingActivity.this, "response Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<StateModel>> call, Throwable t) {
//                avi.hide();
//                layoutView.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });
    }

    private void getCity(String stateId) {

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading City... ");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        CityList.clear();
        cityName.clear();
        cityId.clear();
        apiInterface.getCity().enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    progress.cancel();

                    CityList = response.body();
                    for (int i = 0; i < CityList.size(); i++) {
//                        hashcityList.put(cityList.get(i).getId(), cityList.get(i).getName());
                        if (CityList.get(i).getStateId().equals(stateId)) {
                            cityName.add(CityList.get(i).getName());
                            cityId.add(CityList.get(i).getId());
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AmbBookingActivity.this, android.R.layout.simple_dropdown_item_1line, cityName);
                    cityDropdown.setAdapter(adapter);


                } else {
//                    avi.hide();
//                    layoutView.setVisibility(View.VISIBLE);
                    Toast.makeText(AmbBookingActivity.this, "response Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
//                avi.hide();
//                layoutView.setVisibility(View.VISIBLE);
                progress.cancel();
                Log.e("error ", t.getMessage());

            }
        });
    }


    @SuppressLint("CheckResult")
    private void addPic() {

        new RxPermissions(AmbBookingActivity.this)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE) // ask single or multiple permission once
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                        showPictureDialog();
                    } else {
                        // At least one permission is denied
                    }
                });

//        Dexter.withActivity(this)
//                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if (report.areAllPermissionsGranted()) {
//                            showImagePickerOptions();
//                        } else {
//                            // TODO - handle permission denied case
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(AmbBookingActivity.this);
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

//        if (requestCode == REQUEST_IMAGE) {
//            if (resultCode == Activity.RESULT_OK) {
//                Uri uri = data.getParcelableExtra("path");
//                try {
//                    // You can update this bitmap to your server
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//
//                    //seting resolution 400x400
//                    compressedBitmap = getResizedBitmap(bitmap, 400, 400);
//
//                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
//                    tempUri = getImagePath(getApplicationContext(), compressedBitmap);
//
//                    // CALL THIS METHOD TO GET THE ACTUAL PATH
//                    finalFile = new File(tempUri);
//                    int length = (int) finalFile.length();
//                    System.out.println("length>>>>"+ length);
//
//                    addProfileImg();
//
//                    // loading profile image from local cache
////                    loadProfile(uri.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

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
                    tempUri = getImagePath(getApplicationContext(), bitmap);

                    reportName.setText(tempUri);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    finalFile = new File(tempUri);

//                        addProfileImg();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {

            bitmap = (Bitmap) data.getExtras().get("data");

//                profileImg.setImageBitmap(bitmap);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            tempUri = getImagePath(null, bitmap);
            reportName.setText(tempUri);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile = new File(tempUri);

//                addProfileImg();
        }
    }


    public String getImagePath(Context inContext, Bitmap inImage) {

        getCurrentTime();

        String fiePath = "";
        try {
//            String filePath = Environment.getExternalStorageDirectory() + "/" + "dhruv_iconic";
//            File dir = new File(filePath);

            String filePath = this.getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
            File dir = new File(filePath + this.getApplicationContext().getResources().getString(R.string.app_name));
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
//        status.setText("P");

        Log.d("CurrentDate", currentDateTimeString);
    }


    //---------------------------------Location-------------------------

    private void initLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(AmbBookingActivity.this);
        mSettingsClient = LocationServices.getSettingsClient(AmbBookingActivity.this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        startLocationTracking();
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(AmbBookingActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i("TAG", "All location settings are satisfied.");

                        Toast.makeText(AmbBookingActivity.this, "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        getLatLong();
                    }
                })
                .addOnFailureListener(AmbBookingActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i("TAG", "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(AmbBookingActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i("TAG", "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e("TAG", errorMessage);

                                Toast.makeText(AmbBookingActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

//                        getLatLong();
                    }
                });
    }

    private void getLatLong() {

        gps = new GPSTracker(AmbBookingActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            getAddress(latitude, longitude);
            // \n is for new line
//            Toast.makeText(AmbBookingActivity.this, "Your Location is - \nLat: "
//                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void getAddress(double latitude, double longitude) {

        Lat = latitude;
        Longi = longitude;

        System.out.print("LatLong>>>>" + Lat + ">>>>" + Longi);

        Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        String locality = addresses.get(0).getLocality();
        String subLocality = addresses.get(0).getSubLocality();
        String subsubLocality = String.valueOf(addresses.get(0).getSubAdminArea());
        Log.v("Area", locality);
        Log.v("Area", subLocality);
        Log.v("Area", subsubLocality);


        Log.v("Address", address);

        pickupPoint.setText(address);

//        locationProgress.cancel();
    }

    public void startLocationTracking() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(AmbBookingActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
//                        getLatLong();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}