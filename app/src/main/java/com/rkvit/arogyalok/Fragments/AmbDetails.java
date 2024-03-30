package com.rkvit.arogyalok.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.rkvit.arogyalok.Adapter.AmbAdaptor;
import com.rkvit.arogyalok.Adapter.AmbDetailsAdaptor;
import com.rkvit.arogyalok.Adapter.DataModel;
import com.rkvit.arogyalok.Adapter.DaysAdaptor;
import com.rkvit.arogyalok.Model.AmbCostModel;
import com.rkvit.arogyalok.Model.SpecificationModel;
import com.rkvit.arogyalok.Model.TestMasterModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.rkvit.arogyalok.Utils.HelperActivity;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbDetails extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView Title, LocalPrice, PerKmPrice, StandPerHr, Qual, Spec, Exp, Fees, Timing, Book, Address, Mobile, Email;
    private ImageView Image, addFav, addedFav;
    private RecyclerView Days;
    private String[] WorkDays;
    private List<DataModel> daysList = new ArrayList<>();
    private BottomSheetDialog dialog;
    private LinearLayout testLinear;
    private RecyclerView testsList;
    private List<TestMasterModel> allTestList = new ArrayList<>();
    private HashMap<String, String> hashTestList = new HashMap<String, String>();
    private ApiInterface apiInterface;
    private List<TestMasterModel> testList = new ArrayList<>();
    private List<AmbCostModel> ambCostList = new ArrayList<>();
    private String AmbId;
    private RecyclerView ambListRv;
    private AmbDetailsAdaptor ambAdaptor;

    public AmbDetails() {
        // Required empty public constructor
    }

    public static AmbDetails newInstance(String param1, String param2) {
        AmbDetails fragment = new AmbDetails();
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
        View v = inflater.inflate(R.layout.fragment_amb_details, container, false);

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        Image = v.findViewById(R.id.image);
        Title = v.findViewById(R.id.title);
        LocalPrice = v.findViewById(R.id.local_price);
        PerKmPrice = v.findViewById(R.id.per_km_price);
        StandPerHr = v.findViewById(R.id.stand_per_hour);
        Book = v.findViewById(R.id.book);
        Address = v.findViewById(R.id.address);
        Mobile = v.findViewById(R.id.mobile);
        Email = v.findViewById(R.id.email);

        ambListRv = v.findViewById(R.id.amb_list_rv);

        getAmbDetails();
        setAmbDetails();

        bindData();

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                getBook();
            }
        });




        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void bindData() {
        SharedPreferences pref = getActivity().getSharedPreferences("AmbListDetails", Context.MODE_PRIVATE);
        AmbId = pref.getString("id", null);
        Title.setText(pref.getString("name", null));
        Address.setText(pref.getString("address", null));
        Mobile.setText(pref.getString("mobile", null));
        Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HelperActivity(getActivity()).makeCall(Mobile.getText().toString());
            }
        });

        Glide.with(this).
                load(Constant.ImgRoot + pref.getString("img", null))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ambulance_placeholder)
                .centerInside()
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .dontTransform()
                .into(Image);

//        getAmbDetails();
    }

    private void getAmbDetails() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        ambCostList.clear();
        apiInterface.getAmbCost().enqueue(new Callback<List<AmbCostModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<AmbCostModel>> call, Response<List<AmbCostModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    progress.cancel();
//                    ambCostList = response.body();
//                    ambAdaptor.setDataList(ambCostList);

                    for (int i =0; i<response.body().size(); i++){
                        if (response.body().get(i).getAgencyId().equals(AmbId)){

                            AmbCostModel model = new AmbCostModel();

                            model.setId(response.body().get(i).getId());
                            model.setAgencyId(response.body().get(i).getAgencyId());
                            model.setLocalPrice(response.body().get(i).getLocalPrice());
                            model.setPriceKm(response.body().get(i).getPriceKm());
                            model.setStandCharge(response.body().get(i).getStandCharge());
                            model.setAmbCat(response.body().get(i).getAmbCat());

                            ambCostList.add(model);

                        }
                    }

                    ambAdaptor.setDataList(ambCostList);

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

    private void setAmbDetails(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        ambListRv.setLayoutManager(layoutManager);
        ambAdaptor = new AmbDetailsAdaptor(getActivity(), ambCostList, "VERTICAL");
        ambListRv.setAdapter(ambAdaptor);
    }



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
        while(calendar.getTime().before(dStop)) {
            calendar.add(Calendar.MINUTE, 30);
            times.add(sdf.format(calendar.getTime()));
                    System.out.println(">>>>>" + dStart + ">>>" + dStop + ">>>" +
                            sdf.format(calendar.getTime()));

            System.out.println(sdf.format(calendar.getTime()));
        }


//        Calendar cal = Calendar.getInstance();
//        assert dStart != null;
//        cal.setTime(dStart);
//
//        cal.add(Calendar.HOUR, 1/2); //minus number would decrement the days
//
//        System.out.println(">>>>>" + dStart + ">>>" + dStop + ">>>" + cal.getTime());
//
//        while (cal.getTime().before(dStop)) {
//            strStart = df.format(cal.getTime());
//            cal.add(Calendar.HOUR, 1);
//            strEnd = df.format(cal.getTime());
//            arrayList.add(strStart + " - " + strEnd);
//
//            Log.v("timearray", strStart + " - " + strEnd);
//        }
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
        View dialogView = inflater.inflate(R.layout.doc_apt_layout, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

//        initControls(dialogView);

    }

//    private void setTestList(){
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

}