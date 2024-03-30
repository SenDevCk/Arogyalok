package com.rkvit.arogyalok.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rkvit.arogyalok.Activity.IOnBackPressed;
import com.rkvit.arogyalok.Activity.ImgWebView;
import com.rkvit.arogyalok.Adapter.DataModel;
import com.rkvit.arogyalok.Model.AmbCostModel;
import com.rkvit.arogyalok.Model.BloodDetailsModel;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.DocSchdModel;
import com.rkvit.arogyalok.Model.LabModel;
import com.rkvit.arogyalok.Model.LabSchemModel;
import com.rkvit.arogyalok.Model.SchdDetailsModel;
import com.rkvit.arogyalok.Model.StateModel;
import com.rkvit.arogyalok.Model.TestDetailsModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class BloodHisDetails extends Fragment implements IOnBackPressed {

    private static final String TAG = BloodHisDetails.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView Title, LabName, Spec, Exp, Test, Fees, BookingId, BookingDateTime, BookingStatus, PatName, PatMobile, PatEmail, PatBlood, CancelBtn,
            PatPres, PatCondition, paymentTyp, PatTiming, Book, Address, Mobile, Email, donorName, donorBld;
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
    private List<LabModel> labList = new ArrayList<>();
    private List<TestDetailsModel> testList = new ArrayList<>();
    private HashMap<String, String> hashTestList = new HashMap<>();
    private String Id;
    private List<AmbCostModel> ambDetailsList = new ArrayList<>();


    public BloodHisDetails() {
        // Required empty public constructor
    }

    public static BloodHisDetails newInstance(String param1, String param2) {
        BloodHisDetails fragment = new BloodHisDetails();
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
        View v = inflater.inflate(R.layout.fragment_blood_his_details, container, false);

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        pref = getActivity().getSharedPreferences("BloodHisDetails", MODE_PRIVATE);
        Id = pref.getString("id", null);

        Image = v.findViewById(R.id.image);
        Title = v.findViewById(R.id.title);
        LabName = v.findViewById(R.id.lab_name);
        Spec = v.findViewById(R.id.spec);
        Exp = v.findViewById(R.id.exp);
        Fees = v.findViewById(R.id.fee);

        donorName = v.findViewById(R.id.donor_name);
        donorBld = v.findViewById(R.id.donor_bld);

        BookingId = v.findViewById(R.id.booking_id);
        BookingId.setText("Order No.\n" + pref.getString("bookingNo", null));

        BookingDateTime = v.findViewById(R.id.booking_date_time);
        BookingDateTime.setText("Request Date:\n" + pref.getString("secDate", null));

        BookingStatus = v.findViewById(R.id.booking_sts);
        BookingStatus.setText("Status: " + pref.getString("status", null));

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

        Test = v.findViewById(R.id.test_name);

        Fees = v.findViewById(R.id.fees);
        Fees.setText("Test Cost: " + pref.getString("amount", null));

        PatName = v.findViewById(R.id.pat_name);
        PatName.setText("Patient Name: " + pref.getString("name", null));

        PatMobile = v.findViewById(R.id.pat_mobile);
        PatMobile.setText("Patient Mobile: " + pref.getString("mobile", null));

        PatEmail = v.findViewById(R.id.pat_email);
        PatEmail.setText("Patient Email: " + pref.getString("email", ""));

        PatBlood = v.findViewById(R.id.pat_blood);
        PatBlood.setText("Patient Blood Group: " + pref.getString("blood", null));

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
        PatCondition.setText("Patient Condition: " + pref.getString("Condition", ""));

        Address = v.findViewById(R.id.address);
        Address.setText("Address: " + pref.getString("address", null));

        paymentTyp = v.findViewById(R.id.payment_typ);
        paymentTyp.setText("Payment Type: " + pref.getString("paymentType", null));

        getBldDetails();

        return v;
    }

    private void getBldDetails() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        apiInterface.getBldList().enqueue(new Callback<List<BloodDetailsModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<BloodDetailsModel>> call, Response<List<BloodDetailsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    progress.cancel();

                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getId().equals(pref.getString("donor_id", null))) {

                            donorName.setText("Donor Name: " + response.body().get(i).getName());
                            donorBld.setText("Donor Blood Group: " + response.body().get(i).getBloodGroup());
                        }
                    }

                } else {
                    progress.cancel();

                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BloodDetailsModel>> call, Throwable t) {
                progress.cancel();
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void cancelApt() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Cancelling...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        apiInterface.cancelLab(Id, Id, MyApplication.getUserId()).enqueue(new Callback<BookingMsgModel>() {
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

    public void getTests() {
        String serializedObject = MyApplication.getAllTestList();
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<TestDetailsModel>>() {
            }.getType();
            testList = gson.fromJson(serializedObject, type);
        }

        for (int i = 0; i < testList.size(); i++) {

            hashTestList.put(testList.get(i).getId(), testList.get(i).getName());
            Log.v("TestList", hashTestList.toString());

        }
    }

    public void getScheme() {
        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getLabSchem()
                .enqueue(new Callback<List<LabSchemModel>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<List<LabSchemModel>> call, Response<List<LabSchemModel>> response) {
                        progress.cancel();

                        ArrayList testIds = new ArrayList();

                        if (response.isSuccessful()) {

                            for (int i = 0; i < response.body().size(); i++) {
                                if (response.body().get(i).getId().equals(pref.getString("sc_id", null))) {

                                    Title.setText(response.body().get(i).getSkimName());
                                    Fees.setText("Test Costing: " + "\u20B9" + response.body().get(i).getFinalPrice());
                                    Test.setText("Test Name: " + hashTestList.get(response.body().get(i).getTestId()));
                                    Glide.with(getActivity()).
                                            load(Constant.ImgRoot + response.body().get(i).getImage())
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
                    public void onFailure(Call<List<LabSchemModel>> call, Throwable t) {
                        progress.cancel();
                        progress.cancel();
                        Log.e("error ", t.getMessage());

                    }
                });
    }

    private void getLabDetails() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();

        labList.clear();
        apiInterface.getLab().enqueue(new Callback<List<LabModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<LabModel>> call, Response<List<LabModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    labList = response.body();
                    for (int i = 0; i < labList.size(); i++) {
                        if (labList.get(i).getId().equals(pref.getString("lab_id", null))) {
                            LabName.setText("(By " + labList.get(i).getLabName() + ")");
//                            Qual.setText(ambAgencyList.get(i).getDrEdu());
//                            Spec.setText(hashSpecList.get(ambAgencyList.get(i).getSpecId()));
//                            Exp.setText(ambAgencyList.get(i).getDrExp() + " Experience");
//                            Fees.setText("Fees: " + "\u20B9" + ambAgencyList.get(i).getFee());

                            Glide.with(getActivity()).
                                    load(Constant.ImgRoot + labList.get(i).getImage())
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
            public void onFailure(Call<List<LabModel>> call, Throwable t) {
                progress.cancel();
                Log.e("error ", t.getMessage());

            }
        });

    }
}