package com.rkvit.arogyalok.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rkvit.arogyalok.Adapter.AmbAdaptor;
import com.rkvit.arogyalok.Adapter.AmbAptAdaptor;
import com.rkvit.arogyalok.Adapter.BldTypeAdaptor;
import com.rkvit.arogyalok.Adapter.BloodAptAdaptor;
import com.rkvit.arogyalok.Adapter.DocAdaptor;
import com.rkvit.arogyalok.Adapter.DocAptAdaptor;
import com.rkvit.arogyalok.Adapter.LabAdaptor;
import com.rkvit.arogyalok.Adapter.LabAptAdaptor;
import com.rkvit.arogyalok.Adapter.MedAdaptor;
import com.rkvit.arogyalok.Adapter.MedAptAdaptor;
import com.rkvit.arogyalok.Adapter.SchemeAptAdaptor;
import com.rkvit.arogyalok.Model.AmbHistoryModel;
import com.rkvit.arogyalok.Model.AmbListModel;
import com.rkvit.arogyalok.Model.BldTypeModel;
import com.rkvit.arogyalok.Model.BloodHistoryModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.DocHistoryModel;
import com.rkvit.arogyalok.Model.DocModel;
import com.rkvit.arogyalok.Model.LabHistoryModel;
import com.rkvit.arogyalok.Model.LabModel;
import com.rkvit.arogyalok.Model.MedCatModel;
import com.rkvit.arogyalok.Model.MedHistoryModel;
import com.rkvit.arogyalok.Model.MedListModel;
import com.rkvit.arogyalok.Model.MedSubcatModel;
import com.rkvit.arogyalok.Model.SchemeHistoryModel;
import com.rkvit.arogyalok.Model.SpecificationModel;
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

public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rv;

    private ApiInterface apiInterface;
    private AVLoadingIndicatorView avi;
    private List<Parcelable> placeList = new ArrayList();

    private List<DocModel> docList = new ArrayList<>();
    private DocAdaptor docAdaptor;

    private HashMap<String, String> hashSpecList = new HashMap<String, String>();
    private List<SpecificationModel> specList = new ArrayList<>();

    private HashMap<String, String> hashcityList = new HashMap<String, String>();
    private List<CityModel> cityList = new ArrayList<>();

    private LinearLayout doc, ambulance, lab;
    private SharedPreferences sharedPreferences;
    private List<LabModel> labList = new ArrayList<>();
    private LabAdaptor labAdaptor;
    private List<AmbListModel> ambList = new ArrayList<>();
    private AmbAdaptor ambAdaptor;
    private List<BldTypeModel> bldTypeList = new ArrayList<>();
    //    private MedCatAdaptor medCatAdaptor;
    private List<MedCatModel> medCatList = new ArrayList<>();
    private BldTypeAdaptor bldTypeAdaptor;
    private List<DocHistoryModel> docAptList = new ArrayList<>();
    private DocAptAdaptor docAptAdaptor;
    private AmbAptAdaptor ambAptAdaptor;
    private List<AmbHistoryModel> ambAptList = new ArrayList<>();
    private List<LabHistoryModel> labAptList = new ArrayList<>();
    private LabAptAdaptor labAptAdaptor;
    private BloodAptAdaptor bloodAptAdaptor;
    private List<BloodHistoryModel> bloodAptList = new ArrayList<>();
    private List<MedHistoryModel> medHisList = new ArrayList<>();
    private MedAptAdaptor medAptAdaptor;
    private List<SchemeHistoryModel> labSchemeHisList = new ArrayList<>();
    private SchemeAptAdaptor schemeAptAdaptor;
    private SharedPreferences pre;
    private String key;
    private LinearLayout data, noData;
    private TextView noDataMsg, callBtn;
    private List<MedListModel> medList = new ArrayList<>();
    private List<MedCatModel> medCat = new ArrayList<>();
    private HashMap<String, String> hashCatList = new HashMap<>();
    private HashMap<String, String> hashSubCatList = new HashMap<>();
    private List<String > catList = new ArrayList<>();
    private List<MedSubcatModel> medSubCat = new ArrayList<>();
    private List<String> subCatList = new ArrayList<>();
    private List<MedListModel> medUpdtList = new ArrayList<>();


    public SearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        apiInterface = APIClient.getClient().create(ApiInterface.class);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);

        pre = getActivity().getSharedPreferences("menu", Context.MODE_PRIVATE);
        String loadList = pre.getString("loadList", null);
        key = pre.getString("key", null);

        rv = v.findViewById(R.id.rv);
        avi = v.findViewById(R.id.avi);

        data = v.findViewById(R.id.data_layout);
        noData = v.findViewById(R.id.no_data_layout);
        noDataMsg = v.findViewById(R.id.no_data_msg);
        noDataMsg.setText(getResources().getString(R.string.search_txt_one) + " " + key + " " + getResources().getString(R.string.search_txt_two));
        callBtn = v.findViewById(R.id.call);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HelperActivity(getActivity()).makeCall(Constant.ContactNo);
            }
        });

        getPlaceList();

        getSpecs();

        if (loadList.equalsIgnoreCase("Medicine")) {

        } else if (loadList.equalsIgnoreCase("Doctor")) {
            getDoc();
            setDoc();
        } else if (loadList.equalsIgnoreCase("Lab")) {
            getLab();
            setLab();
        } else if (loadList.equalsIgnoreCase("Ambulance")) {
            getAmb();
            setAmb();
        }


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

    private void getDoc() {

        docList.clear();
        apiInterface.DocSearch(key, MyApplication.getUserId()).enqueue(new Callback<List<DocModel>>() {
            @Override
            public void onResponse(Call<List<DocModel>> call, Response<List<DocModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    data.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    docList = response.body();
                    docAdaptor.setDataList(docList, hashSpecList);

                } else {
                    avi.hide();
                    data.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<DocModel>> call, Throwable t) {
                avi.hide();
                rv.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setDoc() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        docAdaptor = new DocAdaptor(getActivity(), docList, hashSpecList, "Vertical");
        rv.setAdapter(docAdaptor);
    }

    private void getLab() {

        labList.clear();
        apiInterface.LabSearch(key, MyApplication.getUserId()).enqueue(new Callback<List<LabModel>>() {
            @Override
            public void onResponse(Call<List<LabModel>> call, Response<List<LabModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    data.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    labList = response.body();
                    labAdaptor.setDataList(labList, hashcityList);

                } else {
                    avi.hide();
                    data.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<LabModel>> call, Throwable t) {
                avi.hide();
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setLab() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        labAdaptor = new LabAdaptor(getActivity(), labList, hashcityList, "Vertical");
        rv.setAdapter(labAdaptor);
    }

    private void getAmb() {

        ambList.clear();
        apiInterface.AmbSearch(key, MyApplication.getUserId()).enqueue(new Callback<List<AmbListModel>>() {
            @Override
            public void onResponse(Call<List<AmbListModel>> call, Response<List<AmbListModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    data.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    ambList = response.body();
                    ambAdaptor.setDataList(ambList, hashcityList);


                } else {
                    avi.hide();
                    data.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<AmbListModel>> call, Throwable t) {
                avi.hide();
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setAmb() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        ambAdaptor = new AmbAdaptor(getActivity(), ambList, hashcityList, "Horizontal");
        rv.setAdapter(ambAdaptor);
    }


//    private void getMedCat() {
//
//        medCat.clear();
//        apiInterface.getMedCat().enqueue(new Callback<List<MedCatModel>>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onResponse(Call<List<MedCatModel>> call, Response<List<MedCatModel>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    medCat = response.body();
//
//                    for (int i = 0; i < medCat.size(); i++) {
//                        hashCatList.put(medCat.get(i).getId(), medCat.get(i).getName());
//                        catList.add(medCat.get(i).getName());
//                    }
//
//                } else
//                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<List<MedCatModel>> call, Throwable t) {
//                Log.e("error ", t.getMessage());
//
//            }
//        });
//
//    }
//
//    private void getMedSubCat() {
//
//        medSubCat.clear();
//        apiInterface.getMedSubCat().enqueue(new Callback<List<MedSubcatModel>>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onResponse(Call<List<MedSubcatModel>> call, Response<List<MedSubcatModel>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//
//                    medSubCat = response.body();
//                    if (medSubCat.size() != 0) {
//                        for (int i = 0; i < medSubCat.size(); i++) {
//                            hashSubCatList.put(medSubCat.get(i).getId(), medSubCat.get(i).getName());
//                            subCatList.add(medSubCat.get(i).getName());
//                        }
//
//                        getMedList();
//                        setMedList();
//                    }
//
//                } else {
//
//                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<MedSubcatModel>> call, Throwable t) {
//                Log.e("error ", t.getMessage());
//
//            }
//        });
//
//    }
//
//    private void getMedList() {
//
//        medList.clear();
//        medUpdtList.clear();
//        apiInterface.getMedList().enqueue(new Callback<List<MedListModel>>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onResponse(Call<List<MedListModel>> call, Response<List<MedListModel>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//
//                    avi.hide();
//                    rv.setVisibility(View.VISIBLE);
//                    medList = response.body();
////                    txtMed.setText(medList.size() + " Medicine");
//
//                    if (medList.size() != 0) {
//
//                        for (int i = 0; i < medList.size(); i++) {
//                            MedListModel model = new MedListModel();
//
//                            model.setId(medList.get(i).getId());
//                            model.setName(medList.get(i).getName());
//                            model.setImage(medList.get(i).getImage());
//                            model.setMrp(medList.get(i).getMrp());
//                            model.setSaleMrp(medList.get(i).getSaleMrp());
//                            model.setPrescription(medList.get(i).getPrescription());
//                            model.setDescription(medList.get(i).getDescription());
//                            model.setRefundPolicy(medList.get(i).getRefundPolicy());
//                            model.setBrandid(medList.get(i).getBrandid());
//                            model.setMarId(medList.get(i).getMarId());
//                            model.setPaId(medList.get(i).getPaId());
//                            model.setCatName(hashCatList.get(medList.get(i).getCatId()));
//                            model.setSubCatName(hashSubCatList.get(medList.get(i).getSubcatId()));
//                            model.setCatId(medList.get(i).getCatId());
//                            model.setSubcatId(medList.get(i).getSubcatId());
//
//                            medUpdtList.add(model);
//
//                        }
//                        medAdaptor.setDataList(medUpdtList);
//                    }
//
//                } else {
//
//                    avi.hide();
//                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<MedListModel>> call, Throwable t) {
//                avi.hide();
//                Log.e("error ", t.getMessage());
//
//            }
//        });
//
//    }
//
//    private void setMedList() {
//        MedicineFragment fragment;
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
//        rv.setLayoutManager(layoutManager);
//        medAdaptor = new MedAdaptor(getActivity(), medUpdtList, SearchFragment.this);
//        rv.setAdapter(medAdaptor);
//    }

}