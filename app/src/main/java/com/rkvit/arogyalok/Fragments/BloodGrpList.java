package com.rkvit.arogyalok.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rkvit.arogyalok.Adapter.AmbDetailsAdaptor;
import com.rkvit.arogyalok.Adapter.BldListAdaptor;
import com.rkvit.arogyalok.Adapter.DataModel;
import com.rkvit.arogyalok.Filter.BloodFilter;
import com.rkvit.arogyalok.Filter.BloodFilterAdapter;
import com.rkvit.arogyalok.Filter.BloodFilterPreferences;
import com.rkvit.arogyalok.Model.BloodDetailsModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.TestMasterModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.Utils.HelperActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodGrpList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView bloodGrp, Title, LocalPrice, PerKmPrice, StandPerHr, Qual, Spec, Exp, Fees, Timing, Book, Address, Mobile, Email;
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
    private List<BloodDetailsModel> ambCostList = new ArrayList<>();
    private String AmbId;
    private RecyclerView ambListRv;
    private AmbDetailsAdaptor ambAdaptor;
    private List<BloodDetailsModel> bloodList = new ArrayList<>();
    private String BldGrp;
    private BldListAdaptor bldListAdaptor;
    private RecyclerView filterRV, filterValuesRV;
    private List<CityModel> cityList = new ArrayList<>();
    private HashMap<String, String> hashcityList = new HashMap<>();
    private List<String> bloodCityList = new ArrayList<>();
    private BloodFilterAdapter bloodFilterAdapter;
    private TextView title, filter;
    private List<BloodDetailsModel> filteredItems;
    private List<BloodDetailsModel> filteredList;

    public BloodGrpList() {
        // Required empty public constructor
    }

    public static BloodGrpList newInstance(String param1, String param2) {
        BloodGrpList fragment = new BloodGrpList();
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
        View v = inflater.inflate(R.layout.fragment_blood_grp_list, container, false);

        apiInterface = APIClient.getClient().create(ApiInterface.class);
        getPlaceList();

        title = v.findViewById(R.id.title);
        filter = v.findViewById(R.id.filter);

        bloodGrp = v.findViewById(R.id.blood_grp);
        Book = v.findViewById(R.id.book);
        Address = v.findViewById(R.id.address);
        Mobile = v.findViewById(R.id.mobile);

        ambListRv = v.findViewById(R.id.blood_list_rv);

        filter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                openFilterMenu();
            }
        });

        getBldDetails();
        setBldDetails();

        bindData();

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                getBook();
            }
        });


        Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HelperActivity(getActivity()).makeCall(Mobile.getText().toString());
            }
        });


        return v;
    }

    public void getPlaceList() {
        String serializedObject = MyApplication.getCityList();
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CityModel>>() {
            }.getType();
            cityList = gson.fromJson(serializedObject, type);
        }

        for (int i = 0; i < cityList.size(); i++) {

            hashcityList.put(cityList.get(i).getId(), cityList.get(i).getName());

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void bindData() {
        SharedPreferences pref = getActivity().getSharedPreferences("BldDetails", Context.MODE_PRIVATE);
        AmbId = pref.getString("id", null);
        BldGrp = pref.getString("bld_grp", null);
//        Title.setText(pref.getString("bld_grp", null));

    }

    private void getBldDetails() {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        bloodList.clear();
        apiInterface.getBldList().enqueue(new Callback<List<BloodDetailsModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<BloodDetailsModel>> call, Response<List<BloodDetailsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    progress.cancel();

                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getBloodGroup().equals(BldGrp)) {

                            BloodDetailsModel model = new BloodDetailsModel();

                            model.setId(response.body().get(i).getId());
                            model.setBloodGroup(response.body().get(i).getBloodGroup());
                            model.setName(response.body().get(i).getName());
                            model.setCityName(hashcityList.get(response.body().get(i).getCityId()));
                            bloodList.add(model);

                            bloodCityList.add(hashcityList.get(response.body().get(i).getCityId()));

                        }
                    }

                    bldListAdaptor.setDataList(bloodList);

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

    private void setBldDetails() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        ambListRv.setLayoutManager(layoutManager);
        bldListAdaptor = new BldListAdaptor(getActivity(), bloodList);
        ambListRv.setAdapter(bldListAdaptor);
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


    // <---------------------------------------------Blood Filter Data----------------------------------------------------------------------------------------------------------------------------------------->

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openFilterMenu() {

        dialog = new BottomSheetDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_filter, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        initDoCFiltControls(dialogView);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    private void initDoCFiltControls(View v) {
        filterRV = v.findViewById(R.id.filterRV);
        filterValuesRV = v.findViewById(R.id.filterValuesRV);
        filterRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        filterValuesRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<String> city = bloodCityList.stream().distinct().collect(Collectors.toList());
        if (!BloodFilterPreferences.filters.containsKey(BloodFilter.INDEX_City)) {
            BloodFilterPreferences.filters.put(BloodFilter.INDEX_City, new BloodFilter("City", city, new ArrayList()));
        }

        bloodFilterAdapter = new BloodFilterAdapter(getActivity(), BloodFilterPreferences.filters, filterValuesRV);
        filterRV.setAdapter(bloodFilterAdapter);

        Button clearB = v.findViewById(R.id.clearB);
        clearB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodFilterPreferences.filters.get(BloodFilter.INDEX_City).setSelected(new ArrayList());
                bldListAdaptor.setFilter(bloodList);

                title.setText(bloodList.size() + " Donor");
                Toast.makeText(getActivity(), bloodList.size() + " Donor", Toast.LENGTH_SHORT).show();

                dialog.hide();

            }
        });

        Button applyB = v.findViewById(R.id.applyB);
        applyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBloodFilteredData();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateBloodFilteredData() {
        if (!BloodFilterPreferences.filters.isEmpty()) {
            filteredItems = new ArrayList<BloodDetailsModel>();
            List<String> City = BloodFilterPreferences.filters.get(BloodFilter.INDEX_City).getSelected();

            for (BloodDetailsModel item : bloodList) {

                boolean cityMatched = true;
                if (City.size() > 0 && !City.contains(item.getCityName())) {
                    cityMatched = false;
                }
//
                if (cityMatched) {
                    filteredItems.add(item);
                }
            }
            filteredList = filteredItems;
        }

        title.setText(filteredList.size() + " Donor");
        Toast.makeText(getActivity(), filteredList.size() + " Donor", Toast.LENGTH_SHORT).show();
        bldListAdaptor.setFilter(filteredList);
        dialog.hide();
    }
}