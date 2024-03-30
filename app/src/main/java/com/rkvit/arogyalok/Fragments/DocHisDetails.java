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
import com.rkvit.arogyalok.Activity.IOnBackPressed;
import com.rkvit.arogyalok.Activity.ImgWebView;
import com.rkvit.arogyalok.Adapter.DataModel;
import com.rkvit.arogyalok.Adapter.DaysAdaptor;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.DocModel;
import com.rkvit.arogyalok.Model.DocSchdModel;
import com.rkvit.arogyalok.Model.SchdDetailsModel;
import com.rkvit.arogyalok.Model.SpecificationModel;
import com.rkvit.arogyalok.Model.StateModel;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.media.MediaRecorder.VideoSource.CAMERA;


public class DocHisDetails extends Fragment implements IOnBackPressed {

    private static final String TAG = DocHisDetails.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView Title, Qual, Spec, Exp, Fees, BookingId, BookingDateTime, BookingStatus, PatName, PatMobile, PatEmail, PatBlood, CancelBtn,
            PatPres, PatCondition, paymentTyp, PatTiming, Book, Address, Mobile, Email;
    private ImageView Image, addFav, addedFav;
    private RecyclerView Days;
    private String[] WorkDays;
    private List<DataModel> daysList = new ArrayList<>();
    private BottomSheetDialog dialog;
    private EditText patientName, age, userCity, aptDate, addrress, patientMobile;
    private AutoCompleteTextView aptTime;
    private Spinner paymentMode;
    private boolean valid;
    private String UserCity, AptDate, AptTime, UserAddress, PaymentOpt, UserName, UserMobile, UserAge;
    private SharedPreferences pref;
    private String InTime, OutTime;
    private List<String> TimeList = new ArrayList<>();
    private SharedPreferences Userpref;
    private TextView call;
    private LinearLayout timeSlotLayout;
    private AutoCompleteTextView morningSlot, daySlot, evengSlot;
    private List<DocSchdModel> docSchdList = new ArrayList<>();
    private String schdId, mTimeFrom, mTimeTo, mTimeInterval, eTimeFrom, eTimeTo, eTimeInterval, aTimeFrom, aTimeTo, aTimeInterval;
    private Date slot;
    private List<String> mTimeSot = new ArrayList<>();
    private List<String> aTimeSot = new ArrayList<>();
    private List<String> eTimeSot = new ArrayList<>();

    private EditText recommended, condition, pickupPoint, dropPoint;
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

    private String RecommendedBy, Pincode, PickupPoint, DropPoint;
    private int GALLERY = 100;

    private Uri contentURI;
    private Bitmap bitmap;
    private String tempUri;
    private String currentDateTimeString;
    private File finalFile;
    private ApiInterface apiInterface;
    private String slotTime = null;
    private SimpleDateFormat outputformat;
    private List<SchdDetailsModel> schdDetailsList = new ArrayList<>();
    private String[] morngTime, dayTime, evngTime = null;
    private MultipartBody.Part body;
    private List<DocModel> docList = new ArrayList<>();
    private List<SpecificationModel> specList = new ArrayList<>();
    private HashMap<String, String> hashSpecList = new HashMap<>();
    private String Id;


    public DocHisDetails() {
        // Required empty public constructor
    }

    public static DocHisDetails newInstance(String param1, String param2) {
        DocHisDetails fragment = new DocHisDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getOrdrId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean onBackPressed() {

        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
//
        if (count == 0) {
            return false;
            //additional code
        } else {
            getActivity().getSupportFragmentManager().popBackStack();
            return false;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doc_his_details, container, false);

        apiInterface = APIClient.getClient().create(ApiInterface.class);


//        Userpref = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);

        pref = getActivity().getSharedPreferences("DocHisDetails", MODE_PRIVATE);
        Id = pref.getString("id", null);
        getSpecs();

        Image = v.findViewById(R.id.image);
        Title = v.findViewById(R.id.title);
        Qual = v.findViewById(R.id.qual);
        Spec = v.findViewById(R.id.spec);
        Exp = v.findViewById(R.id.exp);
        Fees = v.findViewById(R.id.fee);


        BookingId = v.findViewById(R.id.booking_id);
        BookingId.setText("Booking No.\n"+pref.getString("bookingNo", null));

        BookingDateTime = v.findViewById(R.id.booking_date_time);
        BookingDateTime.setText("Booking Date-Time:\n"+pref.getString("secDate", null) +", "+ pref.getString("secTime", null) );

        BookingStatus = v.findViewById(R.id.booking_sts);
        BookingStatus.setText("Status: "+pref.getString("status", null));

        CancelBtn = v.findViewById(R.id.cancel_btn);
        if (pref.getString("status", null).equals("Pending"))
            CancelBtn.setVisibility(View.VISIBLE);

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new FancyAlertDialog.Builder(getActivity())
                        .setIcon(R.mipmap.logo_round, Icon.Visible)
                        .setTitle("Do you really want to cancel appointment?")
                        .setPositiveBtnText("Yes")
                        .OnPositiveClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                cancelApt();
                            }
                        })
                        .setNegativeBtnText("No")
                        .OnNegativeClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                new FancyAlertDialog.Builder(getActivity()).isCancellable(true);
                            }
                        })
                        .build();
            }
        });

        PatName = v.findViewById(R.id.pat_name);
        PatName.setText("Patient Name: "+pref.getString("name", null));

        PatMobile = v.findViewById(R.id.pat_mobile);
        PatMobile.setText("Patient Mobile: "+pref.getString("mobile", null));

        PatEmail = v.findViewById(R.id.pat_email);
        PatEmail.setText("Patient Email: "+pref.getString("email", null));

        PatBlood = v.findViewById(R.id.pat_blood);
        PatBlood.setText("Patient Blood Group: "+pref.getString("blood", null));

        PatPres = v.findViewById(R.id.prescription);
        PatPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String presUrl = pref.getString("pres", "null");
                if (presUrl != null) {

                    Intent i = new Intent(getActivity(), ImgWebView.class);
                    i.putExtra("Url", Constant.Root + pref.getString("pres", "null"));
                    startActivity(i);

                } else
                    Toast.makeText(getActivity(), "No prescription added", Toast.LENGTH_SHORT).show();
            }
        });

        PatCondition = v.findViewById(R.id.pat_condition);
        PatCondition.setText("Patient Condition: "+pref.getString("Condition", ""));

        Address = v.findViewById(R.id.address);
        Address.setText("Address: "+pref.getString("address", null));

        paymentTyp = v.findViewById(R.id.payment_typ);
        paymentTyp.setText("Payment Type: "+pref.getString("paymentType", null));

        getDocDetails();

        return v;
    }

    private void cancelApt() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Cancelling...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

//        docList.clear();
        apiInterface.cancelDoc(Id, Id, MyApplication.getUserId()).enqueue(new Callback<BookingMsgModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<BookingMsgModel> call, Response<BookingMsgModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    BookingStatus.setText("Status: Cancelled");
                    CancelBtn.setVisibility(View.GONE);
                    progress.cancel();

                    Toast.makeText(getActivity(), "Appointment Cancelled", Toast.LENGTH_LONG).show();


                } else {
                    progress.cancel();
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BookingMsgModel> call, Throwable t) {
                progress.cancel();
                Log.e("error ", t.getMessage());

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void bindData() {


//        if (pref.getString("time_from", null) != null && pref.getString("time_to", null) != null)
//            Timing.setText("Timing: " + pref.getString("time_from", null) + " - " + pref.getString("time_to", null));
//
//        Address.setText(pref.getString("address", null));
//        Mobile.setText(pref.getString("mobile", null));
//        Email.setText(pref.getString("email", null));

//        WorkDays = pref.getString("work_days", null).split(",");


//        getDays();


//        InTime = pref.getString("time_from", null);
//        OutTime = pref.getString("time_to", null);
//
//        Log.v("Timinig", InTime + ">>>>" + OutTime);

//        String inTime = getTime(pref.getString("time_from", null));
//
//        String outTime = getTime(pref.getString("time_to", null));

//        getIntervals();

    }

    public void getSpecs() {
        String serializedObject = MyApplication.getSpecification();
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SpecificationModel>>() {
            }.getType();
            specList = gson.fromJson(serializedObject, type);
        }

        for (int i = 0; i < specList.size(); i++) {

            hashSpecList.put(specList.get(i).getId(), specList.get(i).getName());

        }
    }

    private void getDocDetails() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        docList.clear();
        apiInterface.getDoc().enqueue(new Callback<List<DocModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<DocModel>> call, Response<List<DocModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    docList = response.body();
                    for (int i = 0; i < docList.size(); i++) {
                        if (docList.get(i).getId().equals(pref.getString("doc_id", null))) {
                            Title.setText(docList.get(i).getDocName());
                            Qual.setText(docList.get(i).getDrEdu());
                            Spec.setText(hashSpecList.get(docList.get(i).getSpecId()));
                            Exp.setText(docList.get(i).getDrExp() + " Experience");
                            Fees.setText("Fees: " + "\u20B9" + docList.get(i).getFee());

                            Glide.with(getActivity()).
                                    load(Constant.ImgRoot + docList.get(i).getImage())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .centerInside()
                                    .thumbnail(0.05f)
                                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                                    .dontTransform()
                                    .into(Image);
                        }
                    }

                    progress.cancel();


                } else {
                    progress.cancel();
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<DocModel>> call, Throwable t) {
                progress.cancel();
                Log.e("error ", t.getMessage());

            }
        });

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
            if (input != null) {
                date = df.parse(input);
                //Changing the format of date and storing it in String
                output = outputformat.format(date);
                //Displaying the date
                System.out.println(output);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return output;
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

    private void getAppointment() {

        dialog = new BottomSheetDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.doc_apt_layout, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        initControls(dialogView);
    }

    @SuppressLint("SimpleDateFormat")
    private void initControls(View dialogView) {

        getState();

        String Username = Userpref.getString("UserName", null);
        String UserId = Userpref.getString("UserMobile", null);
        String UserEmail = Userpref.getString("UserEmail", null);

        patientName = dialogView.findViewById(R.id.name);
        patientName.setText(MyApplication.getUserName());

        patientMobile = dialogView.findViewById(R.id.mobile);
        patientMobile.setText(MyApplication.getMobile());

        condition = dialogView.findViewById(R.id.condition);
        bloodGroup = dialogView.findViewById(R.id.blood_group);
        aptDate = dialogView.findViewById(R.id.date);

        addrress = dialogView.findViewById(R.id.address);
        timeSlotLayout = dialogView.findViewById(R.id.time_slot);
        morningSlot = dialogView.findViewById(R.id.morning_slot);
        daySlot = dialogView.findViewById(R.id.day_slot);
        evengSlot = dialogView.findViewById(R.id.eveng_slot);
        recommended = dialogView.findViewById(R.id.recommended);

        pincode = dialogView.findViewById(R.id.pincode);

        paymentMode = dialogView.findViewById(R.id.pay_option);

        reportName = dialogView.findViewById(R.id.report_name);
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
                cityDropdown.setFocusable(false);
                cityDropdown.setCursorVisible(false);

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
                        timeSlotLayout.setVisibility(View.VISIBLE);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
                        String dayName = simpleDateFormat.format(myCalendar.getTime());
//                        Toast.makeText(getActivity(), dayName, Toast.LENGTH_SHORT).show();

                        getSchd(dayName);

                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
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

        morningSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (morngTime != null) {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, morngTime);
                    morningSlot.setAdapter(adapter);
                    morningSlot.showDropDown();
                    morningSlot.setFocusable(false);
                    morningSlot.setCursorVisible(false);
                }

            }
        });
        morningSlot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                slotTime = parent.getItemAtPosition(position).toString();
                morningSlot.setText(slotTime);
                daySlot.getText().clear();
                evengSlot.getText().clear();
            }
        });


        daySlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dayTime != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, dayTime);
                    daySlot.setAdapter(adapter);
                    daySlot.showDropDown();
                    daySlot.setFocusable(false);
                    daySlot.setCursorVisible(false);
                }
            }
        });
        daySlot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                slotTime = parent.getItemAtPosition(position).toString();
                daySlot.setText(slotTime);
                morningSlot.getText().clear();
                evengSlot.getText().clear();

            }
        });


        evengSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (evngTime != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, evngTime);
                    evengSlot.setAdapter(adapter);
                    evengSlot.showDropDown();
                    evengSlot.setFocusable(false);
                    evengSlot.setCursorVisible(false);
                }

            }
        });
        evengSlot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                slotTime = parent.getItemAtPosition(position).toString();
                evengSlot.setText(slotTime);
                daySlot.getText().clear();
                morningSlot.getText().clear();
            }
        });

        Button bookNow = dialogView.findViewById(R.id.book_now);
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validData()) {
                    if (slotTime != null)
                        sendQuery();
                    else
                        FancyToast.makeText(getActivity(), "Please Select Appointment Time", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }
        });
    }

    private boolean validData() {
//        UserCity = userCity.getText().toString();
        UserName = patientName.getText().toString();
        UserMobile = patientMobile.getText().toString();
//        UserAge = age.getText().toString();
        AptDate = aptDate.getText().toString();
//        AptTime = aptTime.getText().toString();
        UserAddress = addrress.getText().toString();
//        PaymentOpt = paymentMode.getSelectedItem().toString();

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

        if (AptDate.isEmpty()) {
            aptDate.setError("required");
            aptDate.requestFocus();
            valid = false;
        }

        if (stateDropdown.getText().toString().isEmpty()) {
            stateDropdown.setError("required");
            stateDropdown.requestFocus();
            valid = false;
        }

        if (cityDropdown.getText().toString().isEmpty()) {
            cityDropdown.setError("required");
            cityDropdown.requestFocus();
            valid = false;
        }

        if (pincode.getText().toString().isEmpty()) {
            pincode.setError("required");
            pincode.requestFocus();
            valid = false;
        }

        if (UserAddress.isEmpty()) {
            addrress.setError("required");
            addrress.requestFocus();
            valid = false;
        }

        return valid;
    }

    private void getSchd(String day) {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading... Time Slot");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        String DocId = pref.getString("id", null);

        String URL = Constant.DocSched + DocId;

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getDocShed(URL).enqueue(new Callback<List<DocSchdModel>>() {
            @Override
            public void onResponse(Call<List<DocSchdModel>> call, Response<List<DocSchdModel>> response) {
                progress.cancel();
                if (response.isSuccessful()) {

                    docSchdList = response.body();

                    for (int i = 0; i < docSchdList.size(); i++) {

                        if (docSchdList.get(i).getDay().equals(day)) {

                            schdId = docSchdList.get(i).getId();

                            getSchdDetails();

                        }

                    }

                } else {
                    Toast.makeText(getActivity(), "No Time Slot Available. Response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<DocSchdModel>> call, Throwable t) {
//                dialog.cancel();
                progress.cancel();
                Log.e("error ", t.getMessage());

            }
        });
    }

    private void getSchdDetails() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading... Time Slot");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        String DocId = pref.getString("id", null);

        String URL = Constant.DocSchedDetails + DocId;

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getShedDetails(URL).enqueue(new Callback<List<SchdDetailsModel>>() {
            @Override
            public void onResponse(Call<List<SchdDetailsModel>> call, Response<List<SchdDetailsModel>> response) {
                progress.cancel();
                if (response.isSuccessful()) {

                    schdDetailsList = response.body();

                    for (int i = 0; i < schdDetailsList.size(); i++) {

                        if (schdDetailsList.get(i).getScId().equals(schdId)) {

                            if (!schdDetailsList.get(i).getMtime().isEmpty())
                                morngTime = schdDetailsList.get(i).getMtime().split(",");
                            else
                                morningSlot.setText("Not Avail");

                            if (!schdDetailsList.get(i).getAtime().isEmpty())
                                dayTime = schdDetailsList.get(i).getAtime().split(",");
                            else
                                daySlot.setText("Not Avail");

                            if (!schdDetailsList.get(i).getEtime().isEmpty())
                                evngTime = schdDetailsList.get(i).getEtime().split(",");
                            else
                                evengSlot.setText("Not Avail");


                        }

                    }


                } else {
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SchdDetailsModel>> call, Throwable t) {
//                dialog.cancel();
                progress.cancel();
                Log.e("error ", t.getMessage());

            }
        });
    }


    private void sendQuery() {

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

        String DocId = pref.getString("id", null);
        String OrderId = getOrdrId();

        String UserId = Userpref.getString("UserId", null);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.docpAppoint(DocId, UserName, UserMobile, bloodGroup.getSelectedItem().toString(), CityId, body, aptDate.getText().toString(),
                slotTime, pincode.getText().toString(), condition.getText().toString(), UserAddress, MyApplication.getUserId())
                .enqueue(new Callback<BookingMsgModel>() {
                    @Override
                    public void onResponse(Call<BookingMsgModel> call, Response<BookingMsgModel> response) {
                        progress.cancel();

                        if (response.isSuccessful() && response.body().getStatus()) {

                            dialog.cancel();

                            saveUserData();

                            if (paymentMode.getSelectedItem().toString().equalsIgnoreCase("Online")) {
//                                Intent i = new Intent(getActivity(), PaymentActivity.class);
//                                i.putExtra("amount", pref.getString("fee", null));
//                                startActivity(i);
                                payOnline();
                            } else
                                FancyToast.makeText(getActivity(), "Your appointment has been done.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        } else {
                            dialog.cancel();
//                            Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                            FancyToast.makeText(getActivity(), "response Unsuccessful", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingMsgModel> call, Throwable t) {
                        dialog.cancel();
                        progress.cancel();
                        Log.e("error ", t.getMessage());

                    }
                });
    }


    public void payOnline() {

        double amt = Double.parseDouble(pref.getString("fee", null)) * 100;

        final Activity activity = getActivity();

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

    private void saveUserData() {

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

        CityList.clear();
        cityName.clear();
        cityId.clear();
        apiInterface.getCity().enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

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
                Log.e("error ", t.getMessage());

            }
        });
    }


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