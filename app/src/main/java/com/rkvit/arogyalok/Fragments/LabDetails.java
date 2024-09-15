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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.razorpay.Checkout;
import com.rkvit.arogyalok.Adapter.DataModel;
import com.rkvit.arogyalok.Adapter.DaysAdaptor;
import com.rkvit.arogyalok.Adapter.LabSchemAdaptor;
import com.rkvit.arogyalok.Adapter.LabTestAdaptor;
import com.rkvit.arogyalok.Adapter.LabTestSelctAdaptor;
import com.rkvit.arogyalok.Adapter.LabTestSelctedAdaptor;
import com.rkvit.arogyalok.LocalDB.TestDatabase;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.LabModel;
import com.rkvit.arogyalok.Model.LabSchemModel;
import com.rkvit.arogyalok.Model.LabTestModel;
import com.rkvit.arogyalok.Model.SlctTestModel;
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
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
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

public class LabDetails extends Fragment implements MultiSpinner.MultiSpinnerListener {

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
    private TextView call, book, addTest;
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
    private String Id;
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
    private int ttlFees;
    private SharedPreferences pref;
    private TextView costing;
    private AlertDialog.Builder alertDialog;
    private RecyclerView testRv;
    private LabTestSelctAdaptor labTestSlctApt;
    private AlertDialog testDialogue;
    private TextView itemTTl, grandTTl;
    private EditText selectedTestEdittxt;
    private TestDatabase db;
    private int count;
    private ArrayList<SlctTestModel> updatedList;
    private List<String> slctTestListName = new ArrayList<>();
    private List<String> slctTestListId = new ArrayList<>();
    private LabTestSelctedAdaptor labTestSlctedApt;
    private int TotalMainPrice;

    public LabDetails() {
        // Required empty public constructor
    }

    public static LabDetails newInstance(String param1, String param2) {
        LabDetails fragment = new LabDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View v = inflater.inflate(R.layout.fragment_lab_details, container, false);

        pref = getActivity().getSharedPreferences("LabDetails", Context.MODE_PRIVATE);

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        Image = v.findViewById(R.id.image);
        Title = v.findViewById(R.id.title);

        call = v.findViewById(R.id.call);
        book = v.findViewById(R.id.book);

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

        bindData();

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new HelperActivity(getActivity()).makeCall(Constant.ContactNo);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBook();
            }
        });

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void bindData() {

        Id = pref.getString("id", null);

        LabName = pref.getString("name", null);

        Title.setText(LabName);
        Timing.setText("Timing: " + pref.getString("time_from", null) + " - " + pref.getString("time_to", null));
        Address.setText(pref.getString("address", null));
        Mobile.setText(pref.getString("mobile", null));
        Email.setText(pref.getString("email", null));

        if (!pref.getString("work_days", "").isEmpty())
            WorkDays = pref.getString("work_days", null).split(",");

//        getLabDetails();
        getLabTest();

        Glide.with(this).
                load(Constant.ImgRoot + pref.getString("img", null))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .dontTransform()
                .into(Image);

        if (WorkDays != null)
            getDays();

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
////            Log.v("timearray", setInterval(inTime, outTime).toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        getLabSchem();
        setLabSchm();

    }

    private void getLabDetails() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getLabDetails(Constant.Lab + "/" + pref.getString("lab_id", null))
                .enqueue(new Callback<LabModel>() {
                    @Override
                    public void onResponse(Call<LabModel> call, Response<LabModel> response) {
                        progress.cancel();

                        if (response.isSuccessful()) {


                        }
                    }

                    @Override
                    public void onFailure(Call<LabModel> call, Throwable t) {
                        progress.cancel();
                        labSchemLinear.setVisibility(View.GONE);
                        Log.e("error ", t.getMessage());

                    }
                });

    }

    private void getLabSchem() {

        //getting all test
        String serializedObject = MyApplication.getAllTestList();
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<TestMasterModel>>() {
            }.getType();
            allTestList = gson.fromJson(serializedObject, type);
        }

        for (int i = 0; i < allTestList.size(); i++) {

            hashTestList.put(allTestList.get(i).getId(), allTestList.get(i).getName());

        }

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getLabSchem().enqueue(new Callback<List<LabSchemModel>>() {
            @Override
            public void onResponse(Call<List<LabSchemModel>> call, Response<List<LabSchemModel>> response) {
                progress.cancel();

                if (response.isSuccessful() && response.body().size() != 0) {

                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getLabId().equals(Id)) {

                            LabSchemModel model = new LabSchemModel();
                            model.setId(response.body().get(i).getId());
                            model.setSkimName(response.body().get(i).getSkimName() + "\n(" + LabName + ")");
                            model.setLabId(response.body().get(i).getLabId());
                            model.setImage(response.body().get(i).getImage());
                            model.setTotalPrice(response.body().get(i).getTotalPrice());
                            model.setDiscountPrice(response.body().get(i).getDiscountPrice());
                            model.setFinalPrice(response.body().get(i).getFinalPrice());
                            model.setSkimValidity(response.body().get(i).getSkimValidity());
                            model.setDescription(response.body().get(i).getDescription());
                            model.setTestId(response.body().get(i).getTestId());

                            labShcmList.add(model);
                        }

                    }

                    labSchemLinear.setVisibility(View.VISIBLE);

                    labSchemAdaptor.setDataList(labShcmList, hashTestList);

                    progress.cancel();

                } else {
                    progress.cancel();
                    labSchemLinear.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LabSchemModel>> call, Throwable t) {
                progress.cancel();
                labSchemLinear.setVisibility(View.GONE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setLabSchm() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        LabSchm.setLayoutManager(layoutManager);
        labSchemAdaptor = new LabSchemAdaptor(getActivity(), labShcmList, hashTestList);
        LabSchm.setAdapter(labSchemAdaptor);
    }

    private void getLabTest() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();


        String URL = Constant.LabTest + Id;
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getLabTest(URL)
                .enqueue(new Callback<List<LabTestModel>>() {
                    @Override
                    public void onResponse(Call<List<LabTestModel>> call, Response<List<LabTestModel>> response) {
                        progress.cancel();

                        if (response.isSuccessful()) {

                            labTest = response.body();

                            for (int i = 0; i < labTest.size(); i++) {
                                testId = labTest.get(i).getTestId();
                                testFee = labTest.get(i).getTestFee();

                                hashTestIDFee.put(labTest.get(i).getTestId(), labTest.get(i).getTestFee());
                                Log.v("lab_test_id", hashTestIDFee.toString());
                            }

                            getTestDetails();
                            setLabTest();

//                            saveUserData();

                            progress.cancel();

//                            if (paymentMode.getSelectedItem().toString().equalsIgnoreCase("Online")) {
////                                Intent i = new Intent(getActivity(), PaymentActivity.class);
////                                i.putExtra("amount", pref.getString("fee", null));
////                                startActivity(i);
////                                payOnline();
//                            } else
//                            Toast.makeText(getActivity(), "Appointment Done.", Toast.LENGTH_LONG).show();

                        } else {
                            progress.cancel();
                            Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LabTestModel>> call, Throwable t) {
                        progress.cancel();
                        progress.cancel();
                        Log.e("error ", t.getMessage());

                    }
                });

    }

    private void getTestDetails() {

        labTestDetails.clear();

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        String URL = Constant.AllTest;
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getLabTestDetails(URL)
                .enqueue(new Callback<List<TestDetailsModel>>() {
                    @Override
                    public void onResponse(Call<List<TestDetailsModel>> call, Response<List<TestDetailsModel>> response) {
                        progress.cancel();

                        if (response.isSuccessful()) {

                            response.body();
//                            hashTestList.get(response.body().get(i).getId()) != null &&
                            for (int i = 0; i < response.body().size(); i++) {
                                if (hashTestIDFee.containsKey(response.body().get(i).getId())) {
                                    TestDetailsModel model = new TestDetailsModel();
                                    model.setId(response.body().get(i).getId());
                                    model.setName(response.body().get(i).getName());
                                    model.setIcon(response.body().get(i).getIcon());
                                    model.setMandTest(response.body().get(i).getMandTest());
                                    model.setTestReport(response.body().get(i).getTestReport());
                                    model.setCoverdParamerter(response.body().get(i).getCoverdParamerter());
                                    model.setTestMode(response.body().get(i).getTestMode());
                                    model.setDescription(response.body().get(i).getDescription());
                                    model.setTestFees(hashTestIDFee.get(response.body().get(i).getId()));

                                    hashTestIDName.put(response.body().get(i).getId(), response.body().get(i).getName());
                                    hashTestNameId.put(response.body().get(i).getName(), response.body().get(i).getId());

                                    hashTestFeeName.put(hashTestIDFee.get(response.body().get(i).getId()), response.body().get(i).getName());
                                    hashTestNameFee.put(response.body().get(i).getName(), hashTestIDFee.get(response.body().get(i).getId()));

                                    labTestDetails.add(model);

                                    Log.v("testname", hashTestIDName.toString());

                                }
                            }
                            labTestAdaptor.setDataList(labTestDetails, hashTestList);

                        } else {
                            progress.cancel();
                            Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TestDetailsModel>> call, Throwable t) {
                        progress.cancel();
                        Log.e("error ", t.getMessage());

                    }
                });

    }

    private void setLabTest() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        testsList.setLayoutManager(layoutManager);
        labTestAdaptor = new LabTestAdaptor(getActivity(), labTestDetails, hashTestList);
        testsList.setAdapter(labTestAdaptor);
    }

    @SuppressLint("SimpleDateFormat")
    private String getTime(String input) {

//        String input = "23/12/2014 10:22:12 PM";
        //Format of the date defined in the input String
        DateFormat df = new SimpleDateFormat("hh:mm aa");
        //Desired format: 24 hour format: Change the pattern as per the need
        DateFormat outputformat = new SimpleDateFormat("HH:mm");
        Date date = null;
        String output = null;
        try {
            //Converting the input String to Date
            date = df.parse(input);
            //Changing the format of date and storing it in String
            output = outputformat.format(date);
            //Displaying the date
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return output;
    }

    //    @SuppressLint("DefaultLocale")
//    public static void interval(int begin, int end, int interval) {
//
//        Log.v("outTime", begin + ">>>>" + interval);
//
//
//        for (int time = begin; time <= end; time += interval) {
//            System.out.println(String.format("%02d:%02d", time / 60, time % 60));
//        }
//    }

    private ArrayList<String> setInterval(String start, String stop) throws ParseException {

//        Date startTime = ...//start
//        Date endTime = ../end


        String strStart;
        String strEnd;
//        ArrayList<String> arrayList = new     ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date dStart = df.parse(start);
        Date dStop = df.parse(stop);

        ArrayList<String> times = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(dStart);
        while (calendar.getTime().before(dStop)) {
            calendar.add(Calendar.MINUTE, 30);
            times.add(sdf.format(calendar.getTime()));
            System.out.println(">>>>>" + dStart + ">>>" + dStop + ">>>" + sdf.format(calendar.getTime()));

            System.out.println(sdf.format(calendar.getTime()));
        }

        return times;
    }

    private void getDays() {

        for (String workDay : WorkDays) {
            DataModel model = new DataModel();
            model.setDay(workDay);
            daysList.add(model);
        }
        Log.v("days", daysList.toString());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        Days.setLayoutManager(layoutManager);
        DaysAdaptor daysAdaptor = new DaysAdaptor(getActivity(), daysList);
        Days.setAdapter(daysAdaptor);
    }

    private void getBook() {

        dialog = new BottomSheetDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.lab_apt_layout, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        initControls(dialogView);

    }

    private void initControls(View dialogView) {

        getState();

        patientName = dialogView.findViewById(R.id.name);
        patientName.setText(MyApplication.getUserName());

        costing = dialogView.findViewById(R.id.costing);

        age = dialogView.findViewById(R.id.age);
        patientMobile = dialogView.findViewById(R.id.mobile);
        patientMobile.setText(MyApplication.getMobile());

        addTest = dialogView.findViewById(R.id.add_test);
        selectedTestEdittxt = dialogView.findViewById(R.id.selected_test);

        recommended = dialogView.findViewById(R.id.recommended);
        bloodGroup = dialogView.findViewById(R.id.blood_group);
        reportName = dialogView.findViewById(R.id.report_name);

        pincode = dialogView.findViewById(R.id.pincode);

        aptDate = dialogView.findViewById(R.id.date);
        addrress = dialogView.findViewById(R.id.address);

        payOption = dialogView.findViewById(R.id.pay_option);

//        testSpinner =dialogView.findViewById(R.id.test_spinner);
        selectedTest = new boolean[hashTestIDName.size()];

        ArrayList<String> listName = new ArrayList<>(hashTestIDName.values());

        addTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.lab_tests_layout, null);
                alertDialog.setView(dialogView);
                testDialogue = alertDialog.create();
                testDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                testDialogue.show();
                testDialogue.setCancelable(false);

                grandTTl = dialogView.findViewById(R.id.grandTTl);
                itemTTl = dialogView.findViewById(R.id.itemTTl);

                ImageView close = dialogView.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        testDialogue.cancel();
                    }
                });

                testRv = dialogView.findViewById(R.id.rv);

                TextView done = dialogView.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        testDialogue.cancel();
                        getCartData();

                    }
                });

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                testRv.setLayoutManager(layoutManager);
                labTestSlctApt = new LabTestSelctAdaptor(getActivity(), labTestDetails, hashTestList, LabDetails.this);
                testRv.setAdapter(labTestSlctApt);

                labTestSlctApt.setDataList(labTestDetails, hashTestList);

            }
        });

        //showing selected test
        selectedTestEdittxt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                getCartData();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.lab_tests_layout, null);
                alertDialog.setView(dialogView);
                testDialogue = alertDialog.create();
                testDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                testDialogue.show();
                testDialogue.setCancelable(false);

                grandTTl = dialogView.findViewById(R.id.grandTTl);
                itemTTl = dialogView.findViewById(R.id.itemTTl);

                grandTTl.setText("Total Price: " + "\u20B9"+ String.valueOf(TotalMainPrice));
                itemTTl.setText("Total Test: " +String.valueOf(count));

                ImageView close = dialogView.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        testDialogue.cancel();
                    }
                });

                testRv = dialogView.findViewById(R.id.rv);

                TextView done = dialogView.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        testDialogue.cancel();
                        getCartData();

                    }
                });

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                testRv.setLayoutManager(layoutManager);
                labTestSlctedApt = new LabTestSelctedAdaptor(getActivity(), updatedList, LabDetails.this);
                testRv.setAdapter(labTestSlctedApt);

                labTestSlctedApt.setDataList(updatedList);


            }
        });

//        multiSpinner = dialogView.findViewById(R.id.multi_spinner);
//        multiSpinner.setItems(listName, "Select Test", this);

//        multiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                selectedTestList.clear();
//                if (!parent.getSelectedItem().toString().equals("Select Test")) {
//                    Toast.makeText(getActivity(), "Selected Test:\n" + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
//                    selectedTestList.add(parent.getItemAtPosition(position).toString());
//                    for (int i = 0; i < selectedTestList.size(); i++) {
//                        Log.v("testlist", String.valueOf(selectedTestList.size()));
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


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

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, stateName);
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (validData())
                    sendQuery();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void getCartData() {

        slctTestListName.clear();
        slctTestListId.clear();
        selectedTestEdittxt.getText().clear();

        int TotalSpclPrice = 0;
        TotalMainPrice = 0;
        db = new TestDatabase(getActivity());
        count = db.getItemCount();
        Log.v("getCartData", String.valueOf(count));
        if (count > 0) {
            updatedList = new ArrayList<>(db.getAllProduct());
            Log.v("cartlist", updatedList.toString());

            for (int i = 0; i < updatedList.size(); i++) {

                slctTestListName.add(updatedList.get(i).getName());
                slctTestListId.add(updatedList.get(i).getId());

                int UpdatedMainPrice = Integer.parseInt(updatedList.get(i).getMrp());
                TotalMainPrice += UpdatedMainPrice;

                UpdateTtl(String.valueOf(TotalMainPrice), String.valueOf(count));


                Log.v("Ttl", String.valueOf(TotalSpclPrice));

                Log.v("slctTestListName", slctTestListName.toString());

                selectedTestEdittxt.setText(slctTestListName.toString().replace("[","").replace("]",""));

                costing.setText("Total Fees: \u20B9" + TotalMainPrice);



            }

//            labTestSlctedApt.setDataList(updatedList);
        }
        else {

//            fragment.UpdateTtl(String.valueOf(0), String.valueOf(0));
//            fragment.showEmptyMsg();
        }
    }

    @SuppressLint("SetTextI18n")
    public void UpdateTtl(String ttlAmt, String ttlCount) {

        grandTTl.setText("Total Price: " + "\u20B9" + ttlAmt);
        itemTTl.setText("Total Test: " + ttlCount);

        Log.v("ttl", ttlAmt +">>>>"+ ttlCount );
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

        Log.v("bool", selected.toString());

    }

    @SuppressLint("SetTextI18n")
    private void ttlCosting() {
        String[] SelectedTest = multiSpinner.getSelectedItem().toString().trim().split(",");

        Log.v("SelectedTest", SelectedTest.toString() + ">>>" + String.valueOf(SelectedTest.length));

        selectedTestList.clear();
//                    if (SelectedTest.length != 0) {
        for (String s : SelectedTest) {

            Log.v("test", s);

            selectedTestList.add(hashTestNameId.get(s));

            Log.v("SelectedTestList", selectedTestList.toString());

            ttlFees += Integer.parseInt(hashTestNameFee.get(s));
//                        ttlFees = price;

        }

        costing.setText("Total Costing: \u20B9" + ttlFees + "\n" + selectedTestList);
    }

    private boolean validData() {

        Log.v("spinner", multiSpinner.getSelectedItem().toString());

        UserName = patientName.getText().toString();
        UserMobile = patientMobile.getText().toString();
        RecommendedBy = recommended.getText().toString();
        AptDate = aptDate.getText().toString();
        Pincode = pincode.getText().toString();
        UserAddress = addrress.getText().toString();

        valid = true;

        if (UserName.isEmpty()) {
            patientName.setError("required");
            patientName.requestFocus();
            valid = false;
        }

        if (selectedTestEdittxt.getText().toString().isEmpty()) {
            selectedTestEdittxt.requestFocus();
            Toast.makeText(getActivity(), "Add Test", Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NewApi")
    private void sendQuery() {

        Log.v("testKey", selectedTestList.toString());

////        String[] SelectedTest = multiSpinner.getSelectedItem().toString().trim().split(",");
//
//        Log.v("SelectedTest", SelectedTest.toString() + ">>>" + String.valueOf(SelectedTest.length));
//
//        for (String s : SelectedTest) {
//
//            Log.v("test", s);
//
//            selectedTestList.add(hashTestNameId.get(s));
//
//            Log.v("SelectedTestList", selectedTestList.toString());
//
////            ttlFees += Integer.parseInt(hashTestNameFee.get(s));
////                        ttlFees = price;
//
//        }


        if (finalFile != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("//multipart/form-data"), finalFile);
            body = MultipartBody.Part.createFormData("prescription", finalFile.getName(), requestBody);
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            body = MultipartBody.Part.createFormData("prescription", "", requestBody);
        }

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Sending... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();


        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.labBooking(Id, UserName, UserMobile, bloodGroup.getSelectedItem().toString(),
                slctTestListId.toString().replace("[", "").replace("]", "").trim(), body, aptDate.getText().toString(), RecommendedBy, addrress.getText().toString(), MyApplication.getUserId())
                .enqueue(new Callback<BookingMsgModel>() {
                    @Override
                    public void onResponse(Call<BookingMsgModel> call, Response<BookingMsgModel> response) {
                        progress.cancel();

                        if (response.isSuccessful() && response.body().getStatus()) {

//                            saveUserData();

                            progress.cancel();

                            if (payOption.getSelectedItem().toString().equalsIgnoreCase("Online")) {
                                payOnline();
                            } else {
                                Toast.makeText(getActivity(), "Appointment Done.", Toast.LENGTH_LONG).show();
                                goToAppoinment();
                            }

                        } else {
                            progress.cancel();
                            Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingMsgModel> call, Throwable t) {
                        progress.cancel();
                        progress.cancel();
                        Log.e("error ", t.getMessage());

                    }
                });
    }

    private void goToAppoinment(){

        new FancyAlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.logo_round, Icon.Visible)
                .isCancellable(false)
                .setMessage("Check Your Appointment Status")
                .setPositiveBtnText("Check")
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        SharedPreferences pre = getActivity().getSharedPreferences("menu", Context.MODE_PRIVATE);
                        pre.edit().putString("loadList", "lab_apt").apply();

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainFrame, new ListFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                })
                .setNegativeBtnText("Cancel")
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        new FancyAlertDialog.Builder(getActivity()).isCancellable(true);
                    }
                }).build();

    }

    public void payOnline() {

        double amt = Double.parseDouble(String.valueOf(ttlFees)) * 100;

        final Activity activity = getActivity();

        //final Checkout checkout = new Checkout();


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

            //checkout.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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
                        stateName.add(stateList.get(i).getName());
                        stateId.add(stateList.get(i).getId());
                    }

                } else {
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
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

        ProgressDialog progress = new ProgressDialog(getActivity());
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, cityName);
                    cityDropdown.setAdapter(adapter);

//                    MyApplication.setCityList(cityList);

//                    setList(FILE_NAME, cityList);

                } else {
//                    avi.hide();
//                    layoutView.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
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

//    private void setTestList() {
//
//        String serializedObject = MyApplication.getTestsList();
//        if (serializedObject != null) {
//            Gson gson = new Gson();
//            Type type = new TypeToken<List<SpecificationModel>>() {
//            }.getType();
//            allTestList = gson.fromJson(serializedObject, type);
//        }
//
//        for (int i = 0; i < allTestList.size(); i++) {
//
//            hashTestList.put(allTestList.get(i).getId(), allTestList.get(i).getName());
//
//        }
//
////        testList.clear();
//        apiInterface.getAllTest().enqueue(new Callback<List<TestMasterModel>>() {
//            @Override
//            public void onResponse(Call<List<TestMasterModel>> call, Response<List<TestMasterModel>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//
//                    testList = response.body();
//
////                    for (int i = 0; i < cityList.size(); i++) {
////                        hashcityList.put(cityList.get(i).getId(), cityList.get(i).getName());
////                    }
//
//                    MyApplication.setTestsList(testList);
//
////                    setList(FILE_NAME, cityList);
//
//                } else {
////                    avi.hide();
////                    layoutView.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<TestMasterModel>> call, Throwable t) {
////                avi.hide();
////                layoutView.setVisibility(View.VISIBLE);
//                Log.e("error ", t.getMessage());
//
//            }
//        });
//
//    }

    @SuppressLint("CheckResult")
    private void addPic() {

        new RxPermissions(getActivity())
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
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
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


        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                contentURI = data.getData();

                Log.v("imgUri", contentURI.toString());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    Log.v("imgBitmap", bitmap.toString());

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    tempUri = getImagePath(getActivity(), bitmap);

                    reportName.setText(tempUri);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    finalFile = new File(tempUri);

//                        addProfileImg();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
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

            String filePath = getActivity().getExternalFilesDir(null).getAbsolutePath();
            File dir = new File(filePath + getActivity().getResources().getString(R.string.app_name));
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
}