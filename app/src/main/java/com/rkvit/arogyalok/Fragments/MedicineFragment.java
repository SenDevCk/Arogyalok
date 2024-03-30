package com.rkvit.arogyalok.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rkvit.arogyalok.Adapter.MedAdaptor;
import com.rkvit.arogyalok.Filter.MedFilter;
import com.rkvit.arogyalok.Filter.MedFilterAdapter;
import com.rkvit.arogyalok.Filter.MedFilterPreferences;
import com.rkvit.arogyalok.LocalDB.CartDatabase;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.MedCatModel;
import com.rkvit.arogyalok.Model.MedListModel;
import com.rkvit.arogyalok.Model.MedSubcatModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.rkvit.arogyalok.Utils.HelperActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rkvit.arogyalok.RetrofitUtil.Constant.SHARED_FILE_NAME;

public class MedicineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public TextView cartItem;
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
    private TextView txtMed, filter, cart;
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
    private SharedPreferences pre;
    private String key;
    private LinearLayout dataLayout, noDataLayout;
    private TextView noDataMsg, callBtn;

    public MedicineFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MedicineFragment newInstance(String param1, String param2) {
        MedicineFragment fragment = new MedicineFragment();
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
        View v = inflater.inflate(R.layout.fragment_medicine, container, false);

        apiInterface = APIClient.getClient().create(ApiInterface.class);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);

        pre = getActivity().getSharedPreferences("menu", Context.MODE_PRIVATE);
        String loadList = pre.getString("loadList", null);
        key = pre.getString("key", null);

        rv = v.findViewById(R.id.rv);
        txtMed = v.findViewById(R.id.txt_med);
        filter = v.findViewById(R.id.filter);
        cart = v.findViewById(R.id.cart_icon);

        dataLayout = v.findViewById(R.id.recyclerview_layout);
        noDataLayout = v.findViewById(R.id.no_data_layout);
        noDataMsg = v.findViewById(R.id.no_data_msg);
        callBtn = v.findViewById(R.id.call);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HelperActivity(getActivity()).makeCall(Constant.ContactNo);
            }
        });

        cartItem = v.findViewById(R.id.cart_badge);
        cartItem.setText(String.valueOf(new CartDatabase(getActivity()).getItemCount()));

        avi = v.findViewById(R.id.avi);

        getMedCat();
        getMedSubCat();

        getPlaceList();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFilterMenu();

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AppCompatActi/vity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new MedCartFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrame, myFragment)
                        .addToBackStack(null)
                        .commit();


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


    private void getMedSearchList() {

        medList.clear();
        medUpdtList.clear();
        apiInterface.MedSearch(key, MyApplication.getUserId()).enqueue(new Callback<List<MedListModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<MedListModel>> call, Response<List<MedListModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();

                    dataLayout.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);

                    medList = response.body();
                    txtMed.setText(medList.size() + " Medicine");

                    if (medList.size() != 0) {

                        for (int i = 0; i < medList.size(); i++) {
                            MedListModel model = new MedListModel();

                            model.setId(medList.get(i).getId());
                            model.setName(medList.get(i).getName());
                            model.setImage(medList.get(i).getImage());
                            model.setMrp(medList.get(i).getMrp());
                            model.setSaleMrp(medList.get(i).getSaleMrp());
                            model.setPrescription(medList.get(i).getPrescription());
                            model.setDescription(medList.get(i).getDescription());
                            model.setRefundPolicy(medList.get(i).getRefundPolicy());
                            model.setBrandid(medList.get(i).getBrandid());
                            model.setMarId(medList.get(i).getMarId());
                            model.setPaId(medList.get(i).getPaId());
                            model.setCatName(hashCatList.get(medList.get(i).getCatId()));
                            model.setSubCatName(hashSubCatList.get(medList.get(i).getSubcatId()));
                            model.setCatId(medList.get(i).getCatId());
                            model.setSubcatId(medList.get(i).getSubcatId());

                            medUpdtList.add(model);

                        }
                        medAdaptor.setDataList(medUpdtList);
                    }

                } else {

                    avi.hide();
                    noDataMsg.setText(getResources().getString(R.string.search_txt_one) + " " + key + " " + getResources().getString(R.string.search_txt_two));
                    dataLayout.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<List<MedListModel>> call, Throwable t) {
                avi.hide();
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void getMedList() {

        medList.clear();
        medUpdtList.clear();
        apiInterface.getMedList().enqueue(new Callback<List<MedListModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<MedListModel>> call, Response<List<MedListModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    dataLayout.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    medList = response.body();
                    txtMed.setText(medList.size() + " Medicine");

                    if (medList.size() != 0) {

                        for (int i = 0; i < medList.size(); i++) {
                            MedListModel model = new MedListModel();

                            model.setId(medList.get(i).getId());
                            model.setName(medList.get(i).getName());
                            model.setImage(medList.get(i).getImage());
                            model.setMrp(medList.get(i).getMrp());
                            model.setSaleMrp(medList.get(i).getSaleMrp());
                            model.setPrescription(medList.get(i).getPrescription());
                            model.setDescription(medList.get(i).getDescription());
                            model.setRefundPolicy(medList.get(i).getRefundPolicy());
                            model.setBrandid(medList.get(i).getBrandid());
                            model.setMarId(medList.get(i).getMarId());
                            model.setPaId(medList.get(i).getPaId());
                            model.setCatName(hashCatList.get(medList.get(i).getCatId()));
                            model.setSubCatName(hashSubCatList.get(medList.get(i).getSubcatId()));
                            model.setCatId(medList.get(i).getCatId());
                            model.setSubcatId(medList.get(i).getSubcatId());

                            medUpdtList.add(model);

                        }
                        medAdaptor.setDataList(medUpdtList);
                    }

                } else {

                    avi.hide();
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<MedListModel>> call, Throwable t) {
                avi.hide();
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setMedList() {
        MedicineFragment fragment;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        medAdaptor = new MedAdaptor(getActivity(), medUpdtList, MedicineFragment.this);
        rv.setAdapter(medAdaptor);
    }

    private void getMedCat() {

        medCat.clear();
        apiInterface.getMedCat().enqueue(new Callback<List<MedCatModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<MedCatModel>> call, Response<List<MedCatModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    medCat = response.body();

                    for (int i = 0; i < medCat.size(); i++) {
                        hashCatList.put(medCat.get(i).getId(), medCat.get(i).getName());
                        catList.add(medCat.get(i).getName());
                    }

                } else
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<MedCatModel>> call, Throwable t) {
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void getMedSubCat() {

        medSubCat.clear();
        apiInterface.getMedSubCat().enqueue(new Callback<List<MedSubcatModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<MedSubcatModel>> call, Response<List<MedSubcatModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    medSubCat = response.body();
                    if (medSubCat.size() != 0) {
                        for (int i = 0; i < medSubCat.size(); i++) {
                            hashSubCatList.put(medSubCat.get(i).getId(), medSubCat.get(i).getName());
                            subCatList.add(medSubCat.get(i).getName());
                        }

                        getMedList();
                        setMedList();
                    }

                } else {

                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<MedSubcatModel>> call, Throwable t) {
                Log.e("error ", t.getMessage());

            }
        });

    }


    // <---------------------------------------------Filter Data------------------------------------------------------------------------------------>
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

        initControls(dialogView);

    }

    @SuppressLint("ResourceType")
    private void initControls(View v) {
        filterRV = v.findViewById(R.id.filterRV);
        filterValuesRV = v.findViewById(R.id.filterValuesRV);
        filterRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        filterValuesRV.setLayoutManager(new LinearLayoutManager(getActivity()));


//        List<String> category = Arrays.asList(getResources().getStringArray(R.array.call_code_data));
//        List<String> category = (List<String>) hashCatList.keySet();
        List<String> category = catList;

        if (!MedFilterPreferences.filters.containsKey(MedFilter.INDEX_MedCat)) {
            MedFilterPreferences.filters.put(MedFilter.INDEX_MedCat, new MedFilter("Category", category, new ArrayList()));
        }

//        List<String> source = Arrays.asList(getResources().getStringArray(R.array.source));
//        List<String> subCat = (List<String>) hashSubCatList.keySet();

        List<String> SubCat = subCatList;
        if (!MedFilterPreferences.filters.containsKey(MedFilter.INDEX_MedSubCat)) {
            MedFilterPreferences.filters.put(MedFilter.INDEX_MedSubCat, new MedFilter("Sub Category", SubCat, new ArrayList()));
        }

        medFilterAdapter = new MedFilterAdapter(getActivity(), MedFilterPreferences.filters, filterValuesRV);
        filterRV.setAdapter(medFilterAdapter);

        Button clearB = v.findViewById(R.id.clearB);
        clearB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedFilterPreferences.filters.get(MedFilter.INDEX_MedCat).setSelected(new ArrayList());
                MedFilterPreferences.filters.get(MedFilter.INDEX_MedSubCat).setSelected(new ArrayList());

                medAdaptor.setFilter(medUpdtList);

                txtMed.setText(medUpdtList.size() + " Medicine");
                Toast.makeText(getActivity(), medUpdtList.size() + " Medicine", Toast.LENGTH_SHORT).show();

                dialog.hide();

            }
        });

        Button applyB = v.findViewById(R.id.applyB);
        applyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFilteredData();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateFilteredData() {
        if (!MedFilterPreferences.filters.isEmpty()) {
            filteredItems = new ArrayList<MedListModel>();
            List<String> Cat = MedFilterPreferences.filters.get(MedFilter.INDEX_MedCat).getSelected();
            List<String> SubCat = MedFilterPreferences.filters.get(MedFilter.INDEX_MedSubCat).getSelected();

            for (MedListModel item : medUpdtList) {

                boolean catMatched = true;
                if (Cat.size() > 0 && !Cat.contains(item.getCatName())) {
                    catMatched = false;
                }
                boolean subCatMatched = true;
                if (SubCat.size() > 0 && !SubCat.contains(item.getSubCatName())) {
                    subCatMatched = false;
                }
//
//                boolean statusMatched = true;
//                if (status.size() > 0 && !status.contains(item.getStatus())) {
//                    statusMatched = false;
//                }
//                boolean clientTypeMatched = true;
//                if (clientType.size() > 0 && !clientType.contains(item.getCType())) {
//                    clientTypeMatched = false;
//                }
//
                if (catMatched && subCatMatched) {
                    filteredItems.add(item);
                }
            }
            filteredList = filteredItems;
        }

        txtMed.setText(filteredList.size() + " Medicine");
        Toast.makeText(getActivity(), filteredList.size() + " Medicine", Toast.LENGTH_SHORT).show();
        medAdaptor.setFilter(filteredList);
        dialog.hide();
    }

}