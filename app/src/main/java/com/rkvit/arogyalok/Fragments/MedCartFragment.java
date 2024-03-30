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
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.razorpay.Checkout;
import com.rkvit.arogyalok.Adapter.CartItemAdaptor;
import com.rkvit.arogyalok.Adapter.DataModel;
import com.rkvit.arogyalok.Adapter.MedAdaptor;
import com.rkvit.arogyalok.Filter.MedFilterAdapter;
import com.rkvit.arogyalok.LocalDB.CartDatabase;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.Model.CartItemModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.LabTestModel;
import com.rkvit.arogyalok.Model.MedCatModel;
import com.rkvit.arogyalok.Model.MedListModel;
import com.rkvit.arogyalok.Model.MedSubcatModel;
import com.rkvit.arogyalok.Model.StateModel;
import com.rkvit.arogyalok.Model.TestDetailsModel;
import com.rkvit.arogyalok.Model.TestMasterModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.rkvit.arogyalok.Utils.GPSTracker;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

public class MedCartFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    public TextView cartItem;
    int TotalSpclPrice = 0;
    int TotalMainPrice = 0;
    int TotalSaving;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rv;
    private ApiInterface apiInterface;
    private AVLoadingIndicatorView avi;
    private HashMap<String, String> hashcityList = new HashMap<String, String>();
    private List<CityModel> cityList = new ArrayList<>();
    private LinearLayout doc, ambulance, lab;
    private SharedPreferences sharedPreferences;
    private MedAdaptor medAdaptor;
    private List<MedListModel> medList = new ArrayList<>();
    private List<MedListModel> medUpdtList = new ArrayList<>();
    private TextView txtMed, clearCart, cart;
    private BottomSheetDialog dialog;
    private RecyclerView filterRV, filterValuesRV;
    private List<MedSubcatModel> medSubCat = new ArrayList<>();
    private List<MedCatModel> medCat = new ArrayList<>();
    private HashMap<String, String> hashCatList = new HashMap<String, String>();
    private HashMap<String, String> hashSubCatList = new HashMap<String, String>();
    private MedFilterAdapter medFilterAdapter;
    private List<MedListModel> filteredItems;
    private List<MedListModel> filteredList;
    private List<String> catList = new ArrayList<>();
    private List<String> subCatList = new ArrayList<>();
    private int SpclPrice, MainPrice, TaxPer, DiscountPerc, DiscountVal, AmountAfterDisc, Total;
    private Button checkOut, shopMore;
    private TextView grandTTl, itemTTl, savedTxt;
    private String TotalAmountToSend;
    private CartItemAdaptor adapter;
    private LinearLayout emptyCardLayout;
    private Button startShopping;
    private RelativeLayout viewLayout;
    private List<CartItemModel> cartList = new ArrayList<>();
    private CartDatabase db;
    private int Count;
    private List<CartItemModel> list;
    private int Qty;
    private TextView Title, Desc, composition, marktBy, packBy, mrp, salesMrp, pres, refund, Qual, Spec, Exp, Fees, Timing, Book, Address, Mobile, Email, ID;
    private ImageView Image, addFav, addedFav;
    private RecyclerView Days;
    private String[] WorkDays;
    private List<DataModel> daysList = new ArrayList<>();
    private LinearLayout testLinear;
    private RecyclerView testsList;
    private List<TestMasterModel> allTestList = new ArrayList<>();
    private HashMap<String, String> hashTestList = new HashMap<String, String>();
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
    private String Id;
    private List<LabTestModel> labTest = new ArrayList<>();
    private String testId, testFee, testName;
    private List<TestDetailsModel> labTestDetails = new ArrayList<>();
    private SharedPreferences pref;
    private MultipartBody.Part body;
    // location last updated time
    private String mLastUpdateTime;
    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    private GPSTracker gps;
    private double latitude, longitude;
    private TextView amt;
    private Spinner paymentMode;
    private List<String> idList = new ArrayList<>();
    private List<String> qtyList = new ArrayList<>();

    public MedCartFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MedCartFragment newInstance(String param1, String param2) {
        MedCartFragment fragment = new MedCartFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_med_cart, container, false);

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        initLocation();

        avi = v.findViewById(R.id.avi);

        rv = v.findViewById(R.id.rv);
        txtMed = v.findViewById(R.id.txt_med);

        clearCart = v.findViewById(R.id.clear_cart);
        clearCart.setOnClickListener(this);

        cart = v.findViewById(R.id.cart_icon);

        checkOut = v.findViewById(R.id.check_out);
        checkOut.setOnClickListener(this);

        shopMore = v.findViewById(R.id.shop_more);
        shopMore.setOnClickListener(this);

        emptyCardLayout = v.findViewById(R.id.empty_card_layout);

        startShopping = v.findViewById(R.id.startShopping);
        startShopping.setOnClickListener(this);

        viewLayout = v.findViewById(R.id.view_layout);

        grandTTl = v.findViewById(R.id.grandTTl);
        itemTTl = v.findViewById(R.id.itemTTl);
        savedTxt = v.findViewById(R.id.savedTxt);

        getCartData();

        return v;
    }


    public void getCartData() {
        db = new CartDatabase(getActivity());
        Count = db.getItemCount();
        if (Count > 0) {
            viewLayout.setVisibility(View.VISIBLE);
            emptyCardLayout.setVisibility(View.GONE);
            list = new ArrayList<>(db.getAllProduct());
            Log.v("cartlist", list.toString());

            for (int i = 0; i < list.size(); i++) {
                Qty = Integer.parseInt(list.get(i).getQuantity());
                SpclPrice = Integer.parseInt(list.get(i).getSaleMrp());
                MainPrice = Integer.parseInt(list.get(i).getMrp());
                TotalSpclPrice += Qty * SpclPrice;
                TotalMainPrice += Qty * MainPrice;
                TotalSaving = TotalMainPrice - TotalSpclPrice;
            }

            adapter = new CartItemAdaptor(getActivity(), list, MedCartFragment.this);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            rv.addItemDecoration(new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL));
            rv.setHasFixedSize(true);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv.setNestedScrollingEnabled(false);

            UpdateTtl(String.valueOf(TotalSpclPrice), String.valueOf(Count));
            UpdateTtlSaving(String.valueOf(TotalSaving));

        } else {
            viewLayout.setVisibility(View.GONE);
            emptyCardLayout.setVisibility(View.VISIBLE);

        }
    }


    @SuppressLint("SetTextI18n")
    public void UpdateTtl(String ttlAmt, String ttlCount) {

        TotalAmountToSend = ttlAmt;
        grandTTl.setText("Total Price: " + "\u20B9" + ttlAmt);
        itemTTl.setText("Total Item: " + ttlCount);

        txtMed.setText(ttlCount + " Medicine");
    }

    @SuppressLint("SetTextI18n")
    public void UpdateTtlSaving(String ttlSaved) {
        if (Integer.valueOf(ttlSaved) != 0) {
            savedTxt.setVisibility(View.VISIBLE);
            savedTxt.setText("You saved " + "\u20B9" + ttlSaved);
        } else {
            savedTxt.setText("You saved " + "\u20B9" + ttlSaved);
            savedTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.check_out:
                getLatLong();
                getBook();
                break;

            case R.id.shop_more:

            case R.id.startShopping:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFrame, new MedicineFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case R.id.clear_cart:

                new FancyAlertDialog.Builder(getActivity())
                        .setTitle("Do you really want to clear your cart?")
                        .setPositiveBtnText("Yes")
                        .OnPositiveClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                clearCart();
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

                break;

            default:

        }

    }

    private void clearCart() {
        if (getTtlCount() != 0) {

            //clear local db
            new CartDatabase(getActivity()).DeleteAll();

            cartList.clear();
            adapter.notifyDataSetChanged();

            showEmptyMsg();

            FancyToast.makeText(getActivity(), "Cart is empty now", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

            grandTTl.setText("Total price: " + "\u20B9 0");
            itemTTl.setText("Total item: 0");

        } else
            FancyToast.makeText(getActivity(), "Cart is already empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

    }

    public void showEmptyMsg() {
        viewLayout.setVisibility(View.GONE);
        emptyCardLayout.setVisibility(View.VISIBLE);
    }

    private int getTtlCount() {
        CartDatabase db = new CartDatabase(getActivity());
        return db.getItemCount();
    }


    private void getBook() {

        dialog = new BottomSheetDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.med_order_layout, null);
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

        age = dialogView.findViewById(R.id.age);
        patientMobile = dialogView.findViewById(R.id.mobile);
        patientMobile.setText(MyApplication.getMobile());
//        userCity = dialogView.findViewById(R.id.place);
        recommended = dialogView.findViewById(R.id.recommended);
        bloodGroup = dialogView.findViewById(R.id.blood_group);

        pincode = dialogView.findViewById(R.id.pincode);

        aptDate = dialogView.findViewById(R.id.date);
        addrress = dialogView.findViewById(R.id.address);

        amt = dialogView.findViewById(R.id.amt);
        amt.setText("Pay " + "\u20B9" + TotalAmountToSend + " via");
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
            @Override
            public void onClick(View v) {
                if (validData())
                    sendQuery();
            }
        });
    }

    private boolean validData() {


//        if (pres.getText().toString().contains("Prescription:\nRequired"))

        UserName = patientName.getText().toString();
        UserMobile = patientMobile.getText().toString();
//        RecommendedBy = recommended.getText().toString();
        AptDate = aptDate.getText().toString();
        Pincode = pincode.getText().toString();
//        PickupPoint = pickupPoint.getText().toString();
//        DropPoint = dropPoint.getText().toString();
        UserAddress = addrress.getText().toString();

        valid = true;

//        if (pres.getText().toString().contains("Prescription:\nRequired") && reportName.getText().toString().isEmpty()) {
//            reportName.setError("required");
//            reportName.requestFocus();
//            valid = false;
//        }

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

//        if (AptDate.isEmpty()) {
//            aptDate.setError("required");
//            aptDate.requestFocus();
//            valid = false;
//        }
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

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Sending... Please wait");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        for (int i = 0; i < list.size(); i++) {
//            Qty = Integer.parseInt(list.get(i).getQuantity());
//            SpclPrice = Integer.parseInt(list.get(i).getSaleMrp());
//            MainPrice = Integer.parseInt(list.get(i).getMrp());
//            TotalSpclPrice += Qty * SpclPrice;
//            TotalMainPrice += Qty * MainPrice;
//            TotalSaving = TotalMainPrice - TotalSpclPrice;
            idList.add(list.get(i).getId());
            qtyList.add(list.get(i).getQuantity());

        }

        Log.v("idList", idList.toString());


        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.orderMed(UserName, UserMobile, bloodGroup.getSelectedItem().toString(),
                addrress.getText().toString(), String.valueOf(latitude), String.valueOf(longitude), StateId, CityId, Pincode,
                addrress.getText().toString(), idList.toString().replace("[","").trim(), qtyList.toString().replace("[","").trim(), Constant.ExtraCharge, MyApplication.getUserId(), body)
                .enqueue(new Callback<BookingMsgModel>() {
                    @Override
                    public void onResponse(Call<BookingMsgModel> call, Response<BookingMsgModel> response) {
                        progress.cancel();

                        if (response.isSuccessful() && response.body().getStatus()) {

//                            saveUserData();

                            progress.cancel();
                            dialog.cancel();

                            new CartDatabase(getActivity()).DeleteAll();

                            cartList.clear();
                            adapter.notifyDataSetChanged();

                            showEmptyMsg();

                            goToOrders();;

                            if (paymentMode.getSelectedItem().toString().equalsIgnoreCase("Online")) {
                                payOnline();
//                                getActivity().onBackPressed();
//                                FancyToast.makeText(getActivity(), "Booking Successful.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            } else
                                Toast.makeText(getActivity(), "Order Sent.", Toast.LENGTH_LONG).show();

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

    private void goToOrders(){

        new FancyAlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.logo_round, Icon.Visible)
                .isCancellable(false)
                .setMessage("Check Your Order Status")
                .setPositiveBtnText("Check")
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        SharedPreferences pre = getActivity().getSharedPreferences("menu", Context.MODE_PRIVATE);
                        pre.edit().putString("loadList", "medicine_apt").apply();

                        FragmentTransaction transaction = getActivity()
                                .getSupportFragmentManager().beginTransaction();
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

        double amt = Double.parseDouble(TotalAmountToSend) * 100;

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
                progress.cancel();
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


    //---------------------------------Location-------------------------

    private void initLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mSettingsClient = LocationServices.getSettingsClient(getActivity());

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
                .addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i("TAG", "All location settings are satisfied.");

//                        Toast.makeText(getActivity(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        getLatLong();
                    }
                })
                .addOnFailureListener(getActivity(), new OnFailureListener() {
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
                                    rae.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i("TAG", "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e("TAG", errorMessage);

                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                        }

//                        getLatLong();
                    }
                });
    }

    private void getLatLong() {

        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
//            Toast.makeText(getActivity(), "Your Location is - \nLat: "
//                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }
    }

    public void startLocationTracking() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(getActivity())
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