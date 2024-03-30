package com.rkvit.arogyalok.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rkvit.arogyalok.Fragments.AmbBooking;
import com.rkvit.arogyalok.Fragments.AmbDetails;
import com.rkvit.arogyalok.Model.AmbCostModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.StateModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.rkvit.arogyalok.Utils.HelperActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.MediaRecorder.VideoSource.CAMERA;


public class AmbDetailsAdaptor extends RecyclerView.Adapter<AmbDetailsAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<AmbCostModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;
//    FavDatabase db;

    private String Id;
    private String ItemName;
    private String ItemType;
    private String ImageUrl;
    private String SpclPrice;
    private HashMap<String, String> cityList = new HashMap<String, String>();
    private BottomSheetDialog dialog;
    private int itemPosition;

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


    public AmbDetailsAdaptor(Activity activity, List<AmbCostModel> list, String viewLayout) {
        this.activity = activity;
        this.list = list;
        this.viewLayout = viewLayout;
//        this.cityList = cityList;

    }

    public void setDataList(List<AmbCostModel> dataList) {
        list = dataList;
//        this.cityList = cityList;
        notifyDataSetChanged();
    }

    public void setFilter(List<AmbCostModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.amb_details_list_layout, viewGroup, false);

        mv = new MyViewHlder(view);

        return mv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        SharedPreferences pref = activity.getSharedPreferences("AmbListDetails", Context.MODE_PRIVATE);
        String AmbId = pref.getString("id", null);
        String Img = pref.getString("img", null);
        String title = pref.getString("name", null);
        String Adrs = pref.getString("address", null);

        myViewHlder.Title.setText(title);

        if (list.get(i).getAmbCat().equals("ALS"))
            myViewHlder.LocalPrice.setText("Advance Life Supplier Availability: " + list.get(i).getLocalPrice());

        else if (list.get(i).getAmbCat().equals("BLS"))
            myViewHlder.LocalPrice.setText("Base Life Supplier Availability: " + list.get(i).getLocalPrice());

        myViewHlder.PerKmPrice.setText("Price per km: \u20B9" + list.get(i).getPriceKm());
        myViewHlder.StandPerHr.setText("Waiting price(per hour) : \u20B9" + list.get(i).getStandCharge());

        Glide.with(activity).
                load(Constant.ImgRoot + Img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ambulance_placeholder)
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .dontTransform()
                .into(myViewHlder.Image);

//        else if (list.get(i).getAgencyId().equals("BLS"))
//            myViewHlder.LocalPrice.setText("Advance Life Supplier Availability: " + list.get(i).getLocalPrice());

        myViewHlder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HelperActivity(activity).makeCall(Constant.ContactNo);
            }
        });

        myViewHlder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = activity.getSharedPreferences("AmbDetails", Context.MODE_PRIVATE);
                pref.edit().putString("AmbId", list.get(i).getId())
                        .putString("AgencyId", list.get(i).getAgencyId())
                        .putString("Fee", list.get(i).getLocalPrice())
                        .apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new AmbBooking();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrame, myFragment)
                        .addToBackStack(null)
                        .commit();

//                Intent intent = new Intent(activity, AmbBookingActivity.class);
//                intent.putExtra("AmbId", list.get(i).getId());
//                intent.putExtra("AgencyId", list.get(i).getAgencyId());
//                activity.startActivity(intent);
//                itemPosition = i;
//                getAppointment();
//
//                list.get(itemPosition).getId(), list.get(itemPosition).getAgencyId()


            }
        });

    }


    private void getAppointment() {

        dialog = new BottomSheetDialog(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.amb_apt_layout, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        initControls(dialogView);
    }

    private void initControls(View dialogView) {

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        getState();

        patientName = dialogView.findViewById(R.id.name);
        patientName.setText(MyApplication.getUserName());

        age = dialogView.findViewById(R.id.age);
        patientMobile = dialogView.findViewById(R.id.mobile);
        patientMobile.setText(MyApplication.getMobile());
//        userCity = dialogView.findViewById(R.id.place);
        condition = dialogView.findViewById(R.id.condition);
        bloodGroup = dialogView.findViewById(R.id.blood_group);

        pincode = dialogView.findViewById(R.id.pincode);

        aptDate = dialogView.findViewById(R.id.date);
        addrress = dialogView.findViewById(R.id.address);

        addReport = dialogView.findViewById(R.id.add_report);
        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPic();
            }
        });


        stateDropdown = dialogView.findViewById(R.id.state_dropdown);
        stateDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, stateName);
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

        pincode = dialogView.findViewById(R.id.pincode);

        cityDropdown = dialogView.findViewById(R.id.city_dropdown);
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

                        String myFormat = "dd/MM/yyyy"; //In which you need put here  //game_date=2020/12/12
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                        aptDate.setText(sdf.format(myCalendar.getTime()));

                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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

        Button bookNow = dialogView.findViewById(R.id.book_now);
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (validData())
//                    sendQuery();
            }
        });


//        loadLocation();
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

        if (UserAddress.isEmpty()) {
            addrress.setError("required");
            addrress.requestFocus();
            valid = false;
        }

        if (AptDate.isEmpty()) {
            aptDate.setError("required");
            aptDate.requestFocus();
            valid = false;
        }
        return valid;
    }

//    private void sendQuery() {
//
//        ProgressDialog progress = new ProgressDialog(activity);
//        progress.setMessage("Sending... Please wait");
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();
//
////        String DocId = pref.getString("id", null);
////        String OrderId = getOrdrId();
//
////        String UserId = Userpref.getString("UserId", null);
////        doc_id,bookingdate,bookingplace,bookingtime,full_address,paymentoption,order_id,transactionid,paymentid,patient_id,pat_name,age,pat_mobile
//        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
//        apiInterface.ambBooking(list.get(itemPosition).getId(), list.get(itemPosition).getAgencyId(), UserName, UserMobile,"",bloodGroup.getSelectedItem().toString(),
//                PatCondition, "body","",PickupPoint,"0","0", DropPoint, addrress.getText().toString(),pincode.getText().toString(), MyApplication.getUserId())
//                .enqueue(new Callback<BookingMsgModel>() {
//                    @Override
//                    public void onResponse(Call<BookingMsgModel> call, Response<BookingMsgModel> response) {
//                        progress.cancel();
//
//                        if (response.isSuccessful() && response.body().getStatus()) {
//
//                            dialog.cancel();
//
////                            saveUserData();
////
////                            if (paymentMode.getSelectedItem().toString().equalsIgnoreCase("Online")) {
//////                                Intent i = new Intent(getActivity(), PaymentActivity.class);
//////                                i.putExtra("amount", pref.getString("fee", null));
//////                                startActivity(i);
//////                                payOnline();
////                            } else
//                                Toast.makeText(activity, "Your details has been sent.", Toast.LENGTH_LONG).show();
//
//                        } else {
//                            dialog.cancel();
//                            Toast.makeText(activity, "response Unsuccessful", Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BookingMsgModel> call, Throwable t) {
//                        dialog.cancel();
//                        progress.cancel();
//                        Log.e("error ", t.getMessage());
//
//                    }
//                });
//    }


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
                    Toast.makeText(activity, "response Unsuccessful", Toast.LENGTH_LONG).show();
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

        ProgressDialog progress = new ProgressDialog(activity);
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, cityName);
                    cityDropdown.setAdapter(adapter);

//                    MyApplication.setCityList(cityList);

//                    setList(FILE_NAME, cityList);

                } else {
//                    avi.hide();
//                    layoutView.setVisibility(View.VISIBLE);
                    Toast.makeText(activity, "response Unsuccessful", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
//                avi.hide();
//                layoutView.setVisibility(View.VISIBLE);
                progress.show();
                Log.e("error ", t.getMessage());

            }
        });
    }

    @Override
    public int getItemCount() {
//        if (viewLayout.contentEquals("Horizontal")) {
//            if (list.size() > limit) {
//                return limit;
//            } else {
//                return list.size();
//            }
//        } else
            return list.size();
    }

    @SuppressLint({"CheckResult", "MissingPermission"})
    private void loadLocation() {
        new RxPermissions((FragmentActivity) activity)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION) // ask single or multiple permission once
                .subscribe(granted -> {
                    if (granted) {
//                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                                .findFragmentById(R.id.my_map);
//                        mapFragment.getMapAsync((OnMapReadyCallback) this);


                        locManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                        // Define the criteria how to select the location provider
                        criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_COARSE);   //default

                        // user defines the criteria

                        criteria.setCostAllowed(false);
                        // get the best provider depending on the criteria
                        provider = locManager.getBestProvider(criteria, false);

                        // the last known location of this provider
                        Location location = locManager.getLastKnownLocation(provider);

                        mylistener = new MyLocationListener();

                        if (location != null) {
                            mylistener.onLocationChanged(location);
                        } else {
                            // leads to the settings because there is no last known location
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivity(intent);
                        }
                        // location updates: at least 1 meter and 200millsecs change
                        locManager.requestLocationUpdates(provider, 200, 1, mylistener);
                        String a = "" + location.getLatitude();
                        Toast.makeText(activity, a, Toast.LENGTH_SHORT).show();


                    } else {
//                        onGps();

                    }
                });
    }

//    --------- Location-----------------------------------

    @SuppressLint("CheckResult")
    private void addPic() {

        new RxPermissions((FragmentActivity) activity)
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
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(activity);
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


//    private void updateWithNewLocation(Location location) {
//        String latLongString = "";
//        if (location != null) {
//            double lat = location.getLatitude();
//            double lng = location.getLongitude();
//            latLongString = "Lat:" + lat + "\nLong:" + lng;
//        } else {
//            latLongString = "No location found";
//        }
////        pickupPoint.setText(latLongString);
//    }

//    private final LocationListener locationListener = new LocationListener() {
//
//        public void onLocationChanged(Location location) {
//            updateWithNewLocation(location);
//        }
//
//        public void onProviderDisabled(String provider) {
//            updateWithNewLocation(null);
//        }
//
//        public void onProviderEnabled(String provider) {}
//
//        public void onStatusChanged(String provider, int status, Bundle extras){}
//    };

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        ((Activity) activity).startActivityForResult(galleryIntent, GALLERY);

//        int preference = ScanConstants.OPEN_MEDIA;
//        Intent intent = new Intent(this, ScanActivity.class);
//        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
//        startActivityForResult(intent, GALLERY);

    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) activity).startActivityForResult(intent, CAMERA);

//        int preference = ScanConstants.OPEN_CAMERA;
//        Intent intent = new Intent(this, ScanActivity.class);
//        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
//        startActivityForResult(intent, CAMERA);
    }

    class MyViewHlder extends RecyclerView.ViewHolder {

        private TextView Title, Address, LocalPrice, PerKmPrice, StandPerHr, SpclPrice, MainPrice, Offer, contact, book;
        private ImageView Image, addFav, addedFav;
        private CardView cardview;
        private Button bookNow, callNow;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.image);
            Title = itemView.findViewById(R.id.title);
            Address = itemView.findViewById(R.id.address);
            cardview = itemView.findViewById(R.id.cardview);
            contact = itemView.findViewById(R.id.contact);
            book = itemView.findViewById(R.id.book);

            LocalPrice = itemView.findViewById(R.id.local_price);
            PerKmPrice = itemView.findViewById(R.id.per_km_price);
            StandPerHr = itemView.findViewById(R.id.stand_per_hour);

            view = itemView;

        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // Initialize the location fields


            Toast.makeText(activity, "" + location.getLatitude() + location.getLongitude(),
                    Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(activity, provider + "'s status changed to " + status + "!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(activity, "Provider " + provider + " enabled!",
                    Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(activity, "Provider " + provider + " disabled!",
                    Toast.LENGTH_SHORT).show();
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
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
//
////            if (resultCode == this.RESULT_CANCELED) {
////                return;
////            }
////            if (requestCode == GALLERY) {
////                if (data != null) {
////
////                    contentURI = data.getData();
////
////                    Log.v("imgUri", contentURI.toString());
////                    try {
////                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
////                        Log.v("imgBitmap", bitmap.toString());
////
////                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
////                        tempUri = getImagePath(getApplicationContext(), bitmap);
////
////                        // CALL THIS METHOD TO GET THE ACTUAL PATH
////                        finalFile = new File(tempUri);
////
////                        addProfileImg();
////
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                        Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
////                    }
////                }
////
////            } else if (requestCode == CAMERA) {
////
////                bitmap = (Bitmap) data.getExtras().get("data");
////
////                profileImg.setImageBitmap(bitmap);
////
////                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
////                tempUri = getImagePath(null, bitmap);
////
////                // CALL THIS METHOD TO GET THE ACTUAL PATH
////                finalFile = new File(tempUri);
////
////                addProfileImg();
////            }
//    }

}