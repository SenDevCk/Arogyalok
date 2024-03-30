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
import com.rkvit.arogyalok.Model.AmbCostModel;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.AmbListModel;
import com.rkvit.arogyalok.Model.DocSchdModel;
import com.rkvit.arogyalok.Model.SchdDetailsModel;
import com.rkvit.arogyalok.Model.SpecificationModel;
import com.rkvit.arogyalok.Model.StateModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
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


public class AmbHisDetails extends Fragment implements IOnBackPressed {

    private static final String TAG = AmbHisDetails.class.getSimpleName();

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
    private List<AmbListModel> ambAgencyList = new ArrayList<>();
    private List<SpecificationModel> specList = new ArrayList<>();
    private HashMap<String, String> hashSpecList = new HashMap<>();
    private String Id;
    private List<AmbCostModel> ambDetailsList = new ArrayList<>();


    public AmbHisDetails() {
        // Required empty public constructor
    }

    public static AmbHisDetails newInstance(String param1, String param2) {
        AmbHisDetails fragment = new AmbHisDetails();
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
        View v = inflater.inflate(R.layout.fragment_amb_his_details, container, false);

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        pref = getActivity().getSharedPreferences("AmbHisDetails", MODE_PRIVATE);
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
        BookingDateTime.setText("Booking Date:\n"+pref.getString("secDate", null));

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
                        .setTitle("Do you really want to cancel?")
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

        getAmbAgency();
        getAmbDetails();

        return v;
    }

    private void cancelApt() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Cancelling...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

//        ambAgencyList.clear();
        apiInterface.cancelAmb(Id, Id, MyApplication.getUserId()).enqueue(new Callback<BookingMsgModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<BookingMsgModel> call, Response<BookingMsgModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    BookingStatus.setText("Status: Cancelled");
                    CancelBtn.setVisibility(View.GONE);
                    progress.cancel();

                    Toast.makeText(getActivity(), "Booking Cancelled", Toast.LENGTH_LONG).show();


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

    private void getAmbAgency() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();

        ambAgencyList.clear();
        apiInterface.getAmb().enqueue(new Callback<List<AmbListModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<AmbListModel>> call, Response<List<AmbListModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ambAgencyList = response.body();
                    for (int i = 0; i < ambAgencyList.size(); i++) {
                        if (ambAgencyList.get(i).getId().equals(pref.getString("agency_id", null))) {
                            Title.setText(ambAgencyList.get(i).getName());
//                            Qual.setText(ambAgencyList.get(i).getDrEdu());
//                            Spec.setText(hashSpecList.get(ambAgencyList.get(i).getSpecId()));
//                            Exp.setText(ambAgencyList.get(i).getDrExp() + " Experience");
//                            Fees.setText("Fees: " + "\u20B9" + ambAgencyList.get(i).getFee());

//                            Glide.with(getActivity()).
//                                    load(Constant.ImgRoot + ambAgencyList.get(i).getImage())
//                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                    .centerInside()
//                                    .thumbnail(0.05f)
//                                    .diskCacheStrategy(DiskCacheStrategy.DATA)
//                                    .dontTransform()
//                                    .into(Image);
                        }
                    }

                    progress.cancel();


                } else {
                    progress.cancel();
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AmbListModel>> call, Throwable t) {
                progress.cancel();
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void getAmbDetails() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();

        ambDetailsList.clear();
        apiInterface.getAmbCost().enqueue(new Callback<List<AmbCostModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<AmbCostModel>> call, Response<List<AmbCostModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ambDetailsList = response.body();
                    for (int i = 0; i < ambDetailsList.size(); i++) {
                        if (ambDetailsList.get(i).getAgencyId().equals(pref.getString("agency_id", null)) && ambDetailsList.get(i).getId().equals(pref.getString("amb_id", null)) ) {
                            Fees.setText("Ambulance Fair: " + "\u20B9" + ambDetailsList.get(i).getLocalPrice());
                            Glide.with(getActivity()).
                                    load(Constant.ImgRoot + ambDetailsList.get(i).getImage())
                                    .placeholder(R.drawable.ambulance_placeholder)
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
            public void onFailure(Call<List<AmbCostModel>> call, Throwable t) {
                progress.cancel();
                Log.e("error ", t.getMessage());

            }
        });

    }
}