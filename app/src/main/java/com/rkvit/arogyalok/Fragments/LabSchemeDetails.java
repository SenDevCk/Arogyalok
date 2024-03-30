package com.rkvit.arogyalok.Fragments;

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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.rkvit.arogyalok.Adapter.DataModel;
import com.rkvit.arogyalok.Adapter.DaysAdaptor;
import com.rkvit.arogyalok.Adapter.LabSchemAdaptor;
import com.rkvit.arogyalok.Adapter.LabTestAdaptor;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.Model.BookingSchemModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.LabModel;
import com.rkvit.arogyalok.Model.LabSchemModel;
import com.rkvit.arogyalok.Model.LabTestModel;
import com.rkvit.arogyalok.Model.StateModel;
import com.rkvit.arogyalok.Model.TestDetailsModel;
import com.rkvit.arogyalok.Model.TestMasterModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.rkvit.arogyalok.Utils.HelperActivity;
import com.rkvit.arogyalok.Utils.MultiSpinner;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class LabSchemeDetails extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    boolean[] selectedTest;
    List<Integer> labTestList = new ArrayList<>();
    private String mParam1;
    private String mParam2;
    private TextView Title, Qual, Spec, Exp, Fees, Timing, Book, Address, Mobile, Email, ID;
    private ImageView Image, addFav, addedFav;
    private RecyclerView Days, LabSchm;
    private String[] WorkDays;
    private List<DataModel> daysList = new ArrayList<>();
    private List<String> selectedTestList = new ArrayList<>();
    private BottomSheetDialog dialog;
    private LinearLayout testLinear;
    private RecyclerView testsList;
    private List<TestMasterModel> allTestList = new ArrayList<>();
    private HashMap<String, String> hashTestList = new HashMap<String, String>();
    private HashMap<String, String> hashTestIDFee = new HashMap<String, String>();
    private ApiInterface apiInterface;
    private List<TestMasterModel> testList = new ArrayList<>();
    private TextView call, book;
    private EditText patientName, age, recommended, userCity, pickupPoint, dropPoint, aptDate, addrress, patientMobile;
    private Spinner bloodGroup;
    private AutoCompleteTextView pincode, stateDropdown, cityDropdown;
    private TextView addReport, reportName;
    private List<StateModel> stateList = new ArrayList<>();
    private List<String> stateName = new ArrayList<>();
    private List<String> stateId = new ArrayList<>();
    private List<CityModel> CityList = new ArrayList<>();
    private List<String> cityName = new ArrayList<>();
    private List<String> cityId = new ArrayList<>();
    private String StateId, CityId;
    private boolean valid;
    private String RecommendedBy, AptDate, UserAddress, Pincode, PickupPoint, DropPoint, UserName, UserMobile, UserAge;
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
    private String Id, LabId, schemeTitle, schemeImg, schemeTest, schemePrice, schemeDisct, schemeFinalPrice, schemeValidity, schemeDesc;
    private List<LabTestModel> labTest = new ArrayList<>();
    private String testId, testFee, testName;
    private List<TestDetailsModel> labTestDetails = new ArrayList<>();
    private List<LabSchemModel> labShcmList = new ArrayList<>();
    private LabSchemAdaptor labSchemAdaptor;
    private String LabName;
    private LabTestAdaptor labTestAdaptor;
    private LinearLayout labSchemLinear;
    private HashMap<String, String> hashTestIDName = new HashMap<>();
    private HashMap<String, String> hashTestNameId = new HashMap<>();
    private TextView testSpinner;
    private MultiSpinner multiSpinner;
    private MultipartBody.Part body;
    private Spinner payOption;
    private HashMap<String, String> hashTestNameFee = new HashMap<>();
    private HashMap<String, String> hashTestFeeName = new HashMap<>();
    private String ttlFees;
    private List<LabModel> labList = new ArrayList<>();
    private TextView SchemeTitle, Mrp, SalePrice, Discnt, CallUs, BookNow, TestName, testValidity;
    private ImageView img;
    private Spinner paymentMode;

    public LabSchemeDetails() {
        // Required empty public constructor
    }

    public static LabSchemeDetails newInstance(String param1, String param2) {
        LabSchemeDetails fragment = new LabSchemeDetails();
        Bundle args = new Bundle();
//        args = pref.getString(ARG_PARAM1, param1);
//        args = pref.getString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scheme_lab_details, container, false);

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        Image = v.findViewById(R.id.image);
        Title = v.findViewById(R.id.title);

        Fees = v.findViewById(R.id.fee);
        Timing = v.findViewById(R.id.timing);
        Book = v.findViewById(R.id.cancel_btn);
        Days = v.findViewById(R.id.days);
        LabSchm = v.findViewById(R.id.lab_schm_list);
        Address = v.findViewById(R.id.address);
        Mobile = v.findViewById(R.id.mobile);
        Email = v.findViewById(R.id.email);
        testLinear = v.findViewById(R.id.test_linear);
        testsList = v.findViewById(R.id.tests_list);
        labSchemLinear = v.findViewById(R.id.lab_schem_linear);

        SchemeTitle = v.findViewById(R.id.scheme_title);
        Mrp = v.findViewById(R.id.mrp);
        SalePrice = v.findViewById(R.id.sale_price);
        Discnt = v.findViewById(R.id.discnt);
        CallUs = v.findViewById(R.id.call);
        BookNow = v.findViewById(R.id.book_now);
        img = v.findViewById(R.id.img);
        TestName = v.findViewById(R.id.test_name);
        testValidity = v.findViewById(R.id.schme_validity);

        bindData();

        CallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new HelperActivity(getActivity()).makeCall(Constant.ContactNo);

            }
        });

        BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBookingForm();


            }
        });

        return v;
    }

    private void getLab() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        
        labList.clear();
        apiInterface.getLab().enqueue(new Callback<List<LabModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<LabModel>> call, Response<List<LabModel>> response) {

                progress.cancel();
                
                if (response.isSuccessful() && response.body() != null) {

                    labList = response.body();

                    for (int i = 0; i < labList.size(); i++) {

                        if (labList.get(i).getId().equals(LabId)) {

                            Title.setText("Offered By: " + labList.get(i).getLabName());

                            Glide.with(getActivity()).
                                    load(Constant.ImgRoot + labList.get(i).getImage())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.lab_placeholder)
                                    .thumbnail(0.05f)
                                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                                    .dontTransform()
                                    .into(Image);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LabModel>> call, Throwable t) {
                
                progress.cancel();
                Log.e("error ", t.getMessage());
                
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void bindData() {
        SharedPreferences pref = getActivity().getSharedPreferences("LabSchemeDetails", Context.MODE_PRIVATE);

        Id = pref.getString("id", null);
        LabId = pref.getString("lab_id", null);

        schemeTitle = pref.getString("name", null);
        SchemeTitle.setText(schemeTitle);

        schemeImg = pref.getString("img", null);
        Glide.with(getActivity()).
                load(Constant.ImgRoot + schemeImg)
                .placeholder(R.drawable.lab_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .dontTransform()
                .into(img);

        schemeTest = pref.getString("test_name", null);
        TestName.setText(schemeTest);

        schemePrice = pref.getString("total_price", null);
        Mrp.setText("\u20B9" + schemePrice);
        Mrp.setPaintFlags(Mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//        schemeDisct = pref.getString("discount_price", null);

        schemeFinalPrice = pref.getString("final_price", null);
        SalePrice.setText("\u20B9" + schemeFinalPrice);

        double MRP = Double.parseDouble(schemePrice);
        double SP = Double.parseDouble(schemeFinalPrice);
        double diff = MRP - SP;

        int perct = (int) ((diff / MRP) * 100);
        Discnt.setText(perct + "%");


        schemeValidity = pref.getString("skim_validity", null);
        testValidity.setText("Scheme Validity: " + schemeValidity + "days");

        schemeDesc = pref.getString("description", null);

        getLab();
//        Title.setText(LabName);
//        Timing.setText("Timing: " + pref.getString("time_from", null) + " - " + pref.getString("time_to", null));
//        Address.setText(pref.getString("address", null));
//        Mobile.setText(pref.getString("mobile", null));
//        Email.setText(pref.getString("email", null));

//        WorkDays = pref.getString("work_days", null).split(",");

//        getLabTest();

//        Glide.with(this).
//                load(Constant.ImgRoot + pref.getString("img", null))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerInside()
//                .thumbnail(0.05f)
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                .dontTransform()
//                .into(Image);

//        getDays();


//        String inTime = getTime(pref.getString("time_from", null));
//        String outTime = getTime(pref.getString("time_to", null));

//        String inTime = LocalTime.parse(pref.getString("time_from", null) , DateTimeFormatter.ofPattern("hh:mm a" , Locale.US))
//                .format( DateTimeFormatter.ofPattern("HH:mm"));

//        Log.v("inTime", inTime);

//        String outTime = LocalTime.parse(pref.getString("time_to", null) , DateTimeFormatter.ofPattern("hh:mm a" , Locale.US))
//                .format( DateTimeFormatter.ofPattern("HH:mm"));
//        Log.v("outTime", outTime);

//        interval(Integer.parseInt(inTime.replace(":", "").trim()) * 60, Integer.parseInt(outTime.replace(":", "").trim()) * 60, 30);


//        try {
//            Log.v("timearray", setInterval(inTime, outTime).toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        getLabSchem();
//        setLabSchm();

    }

    private void openBookingForm() {

        dialog = new BottomSheetDialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.lab_scheme_apt_layout, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        initControls(dialogView);

    }


    private void initControls(View dialogView) {

        patientName = dialogView.findViewById(R.id.name);
        patientName.setText(MyApplication.getUserName());

        age = dialogView.findViewById(R.id.age);
        patientMobile = dialogView.findViewById(R.id.mobile);
        patientMobile.setText(MyApplication.getMobile());
        recommended = dialogView.findViewById(R.id.recommended);
        bloodGroup = dialogView.findViewById(R.id.blood_group);
        paymentMode = dialogView.findViewById(R.id.pay_option);

        pincode = dialogView.findViewById(R.id.pincode);

        aptDate = dialogView.findViewById(R.id.date);
        addrress = dialogView.findViewById(R.id.address);


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

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
                if (validData())
                    sendQuery();
            }
        });
    }

    private boolean validData() {

        UserName = patientName.getText().toString();
        UserMobile = patientMobile.getText().toString();
        AptDate = aptDate.getText().toString();
        Pincode = pincode.getText().toString();
        UserAddress = addrress.getText().toString();

        valid = true;

        if (UserName.isEmpty()) {
            patientName.setError("required");
            patientName.requestFocus();
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

    private void sendQuery() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Sending... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();


        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.labSchemBooking(Id, LabId, UserName, UserMobile, "", aptDate.getText().toString(),
                addrress.getText().toString(), pincode.getText().toString(), bloodGroup.getSelectedItem().toString(), schemeValidity, MyApplication.getUserId())
                .enqueue(new Callback<BookingSchemModel>() {
                    @Override
                    public void onResponse(Call<BookingSchemModel> call, Response<BookingSchemModel> response) {
                        progress.cancel();

                        if (response.isSuccessful()) {

                            progress.cancel();
                            dialog.cancel();

                            if (paymentMode.getSelectedItem().toString().equalsIgnoreCase("Online")) {
                                payOnline();
                            } else
                                Toast.makeText(getActivity(), "Booking Done.", Toast.LENGTH_LONG).show();

                        } else {
                            dialog.cancel();
                            progress.cancel();
                            Toast.makeText(getActivity(), "Something went wrong, try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingSchemModel> call, Throwable t) {
                        progress.cancel();
                        dialog.cancel();
                        Log.e("error ", t.getMessage());

                    }
                });
    }

    public void payOnline() {

        double amt = Double.parseDouble(schemeFinalPrice) * 100;

        final Checkout checkout = new Checkout();


        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Payment");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
//            options.put("order_id", "646646");//from response of step 3.
            options.put("theme.color", R.color.colorPrimaryDark);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amt);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "pmm.etouchindia@gmail.com");
            preFill.put("contact", Constant.PaymentContactNo);
            preFill.put("enabled", true);
            preFill.put("max_count", 10);

            options.put("retry", preFill);

            checkout.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}