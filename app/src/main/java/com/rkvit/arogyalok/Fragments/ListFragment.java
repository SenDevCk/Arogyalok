package com.rkvit.arogyalok.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.model.Model;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.rkvit.arogyalok.Adapter.MedAptAdaptor;
import com.rkvit.arogyalok.Adapter.SchemeAptAdaptor;
import com.rkvit.arogyalok.Filter.DocFilter;
import com.rkvit.arogyalok.Filter.DocFilterAdapter;
import com.rkvit.arogyalok.Filter.DocFilterPreferences;
import com.rkvit.arogyalok.Filter.LabFilter;
import com.rkvit.arogyalok.Filter.LabFilterAdapter;
import com.rkvit.arogyalok.Filter.LabFilterPreferences;
import com.rkvit.arogyalok.Model.AmbHistoryModel;
import com.rkvit.arogyalok.Model.AmbListModel;
import com.rkvit.arogyalok.Model.BldTypeModel;
import com.rkvit.arogyalok.Model.BloodHistoryModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.DocHistoryModel;
import com.rkvit.arogyalok.Model.DocModel;
import com.rkvit.arogyalok.Model.LabHistoryModel;
import com.rkvit.arogyalok.Model.LabModel;
import com.rkvit.arogyalok.Model.LabTestModel;
import com.rkvit.arogyalok.Model.MedCatModel;
import com.rkvit.arogyalok.Model.MedHistoryModel;
import com.rkvit.arogyalok.Model.SchemeHistoryModel;
import com.rkvit.arogyalok.Model.SpecificationModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rkvit.arogyalok.RetrofitUtil.Constant.SHARED_FILE_NAME;

//import com.rkvit.arogyalok.Adapter.MedCatAdaptor;

public class ListFragment extends Fragment {

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
    private List<DocModel> docUptList = new ArrayList<>();


    private DocAdaptor docAdaptor;

    private HashMap<String, String> hashSpecList = new HashMap<String, String>();
    private List<SpecificationModel> specList = new ArrayList<>();

    private HashMap<String, String> hashcityList = new HashMap<String, String>();
    private List<CityModel> cityList = new ArrayList<>();

    private LinearLayout doc, ambulance, lab;
    private SharedPreferences sharedPreferences;
    private List<LabModel> labList = new ArrayList<>();
    private List<LabModel> labUpdtList = new ArrayList<>();
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
    private TextView title, filter;
    private LinearLayout filterLinear;
    private BottomSheetDialog dialog;
    private RecyclerView filterRV, filterValuesRV;
    private ArrayList<String> docFeesList = new ArrayList<>();
    private ArrayList<String> docExpList = new ArrayList<>();
    private ArrayList<String> docCityList = new ArrayList<>();
    private ArrayList<String> labCityList = new ArrayList<>();
    private ArrayList<String> labTestList = new ArrayList<>();
    private ArrayList<String> docSpecfList = new ArrayList<>();
    private DocFilterAdapter docFilterAdapter;
    private List<DocModel> docfilteredItems;
    private List<DocModel> docFilteredList;
    private List<LabTestModel> labTestsList = new ArrayList();
    private LabFilterAdapter labFilterAdapter;
    private List<LabModel> labfilteredItems;
    private List<LabModel> labFilteredList;


    public ListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        apiInterface = APIClient.getClient().create(ApiInterface.class);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences pre = getActivity().getSharedPreferences("menu", Context.MODE_PRIVATE);
        String loadList = pre.getString("loadList", null);

        title = v.findViewById(R.id.title);
        filter = v.findViewById(R.id.filter);
        filterLinear = v.findViewById(R.id.filter_linear);
        rv = v.findViewById(R.id.rv);
        avi = v.findViewById(R.id.avi);

        filter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if (loadList.equalsIgnoreCase("doc")) {
                    openDocFilterMenu();
                } else if (loadList.equalsIgnoreCase("lab")) {
                    openLabFilterMenu();
                }
            }
        });

        getPlaceList();

        getSpecs();

        if (loadList.equalsIgnoreCase("doc")) {
            getDoc();
            setDoc();
            filterLinear.setVisibility(View.VISIBLE);
        } else if (loadList.equalsIgnoreCase("amb")) {
            getAmb();
            setAmb();
//            filterLinear.setVisibility(View.VISIBLE);
        } else if (loadList.equalsIgnoreCase("lab")) {
            getLab();
            setLab();
            filterLinear.setVisibility(View.VISIBLE);
        } else if (loadList.equalsIgnoreCase("blood_bank")) {
            getBloodType();
            setBloodType();
//            filterLinear.setVisibility(View.VISIBLE);
        } else if (loadList.equalsIgnoreCase("medicine")) {
            getMedCat();
            setMedCat();
            filterLinear.setVisibility(View.VISIBLE);
        } else if (loadList.equalsIgnoreCase("doc_apt")) {
            getDocApt();
            setDocApt();
            title.setText("Appointments");
        } else if (loadList.equalsIgnoreCase("amb_apt")) {
            getAmbApt();
            setAmbApt();
            title.setText("Ambulance Booking");
        } else if (loadList.equalsIgnoreCase("lab_apt")) {
            getLabApt();
            setLabApt();
            title.setText("Lab Booking");
        } else if (loadList.equalsIgnoreCase("blood_apt")) {
            getBloodApt();
            setBloodApt();
            title.setText("Blood Requests");
        } else if (loadList.equalsIgnoreCase("medicine_apt")) {
            getMedHis();
            setMedHis();
            title.setText("Order History");
        } else if (loadList.equalsIgnoreCase("lab_scheme_apt")) {
            getLabSchemeApt();
            setLabSchemeApt();
            title.setText("Scheme Booking");
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

    public void getLabTestsList() {
        String serializedObject = MyApplication.getLabTestsList();
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<LabTestModel>>() {
            }.getType();
            labTestsList = gson.fromJson(serializedObject, type);
        }

        for (int i = 0; i < labTestsList.size(); i++) {

            hashSpecList.put(specList.get(i).getId(), specList.get(i).getName());

        }
    }

    public void getTestDetailsList() {
        String serializedObject = MyApplication.getAllTestList();
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
        apiInterface.getDoc().enqueue(new Callback<List<DocModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<DocModel>> call, Response<List<DocModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    docList = response.body();
                    title.setText(docList.size() + " Doctors");

                    if (docList.size() != 0) {
                        for (int i = 0; i < docList.size(); i++) {
                            DocModel model = new DocModel();
                            model.setId(docList.get(i).getId());
                            model.setDocName(docList.get(i).getDocName());
                            model.setImage(docList.get(i).getImage());
                            model.setSpecId(docList.get(i).getSpecId());
                            model.setSpecName(hashSpecList.get(docList.get(i).getSpecId()));
                            model.setCityId(docList.get(i).getCityId());
                            model.setCityName(hashcityList.get(docList.get(i).getCityId()));
                            model.setDrEdu(docList.get(i).getDrEdu());
                            model.setDrExp(docList.get(i).getDrExp());
                            model.setHospName(docList.get(i).getHospName());
                            model.setFee(docList.get(i).getFee());
                            model.setTimeFrom(docList.get(i).getTimeFrom());
                            model.setTimeTo(docList.get(i).getTimeTo());
                            model.setWorkDay(docList.get(i).getWorkDay());
                            model.setEmail(docList.get(i).getEmail());
                            model.setMobile(docList.get(i).getMobile());
                            model.setBloodGroup(docList.get(i).getBloodGroup());
                            model.setFullAddress(docList.get(i).getFullAddress());
                            model.setCuid(docList.get(i).getCuid());
                            model.setMuid(docList.get(i).getMuid());
                            model.setCtime(docList.get(i).getCtime());
                            model.setMtime(docList.get(i).getMtime());

                            docUptList.add(model);

                            docFeesList.add(docList.get(i).getFee());
                            docExpList.add(docList.get(i).getDrExp());
                            docCityList.add(hashcityList.get(docList.get(i).getCityId()));
                            docSpecfList.add(hashSpecList.get(docList.get(i).getSpecId()));
                        }

                        docAdaptor.setDataList(docUptList, hashSpecList);

                    }

                } else {
                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
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
        docAdaptor = new DocAdaptor(getActivity(), docUptList, hashSpecList, "Vertical");
        rv.setAdapter(docAdaptor);
    }

    private void getLab() {

        labList.clear();
        apiInterface.getLab().enqueue(new Callback<List<LabModel>>() {
            @Override
            public void onResponse(Call<List<LabModel>> call, Response<List<LabModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    labList = response.body();
                    title.setText(labList.size() + " Labs");

                    if (labList.size() != 0) {
                        for (int i = 0; i < labList.size(); i++) {
                            LabModel model = new LabModel();
                            model.setId(labList.get(i).getId());
                            model.setLabName(labList.get(i).getLabName());
                            model.setImage(labList.get(i).getImage());
                            model.setMobile(labList.get(i).getMobile());
                            model.setTechName(labList.get(i).getTechName());
                            model.setTechMobile(labList.get(i).getTechMobile());
                            model.setTechDegree(labList.get(i).getTechDegree());
                            model.setBloodGroup(labList.get(i).getBloodGroup());
                            model.setEmailId(labList.get(i).getEmailId());
                            model.setTimeFrom(labList.get(i).getTimeFrom());
                            model.setTimeTo(labList.get(i).getTimeTo());
                            model.setCityId(labList.get(i).getCityId());
                            model.setCityName(hashcityList.get(labList.get(i).getCityId()));
                            model.setFullAddress(labList.get(i).getFullAddress());
                            model.setWorkDay(labList.get(i).getWorkDay());
                            model.setDetails(labList.get(i).getDetails());
                            model.setCuid(labList.get(i).getCuid());
                            model.setMuid(labList.get(i).getMuid());
                            model.setCtime(labList.get(i).getCtime());
                            model.setMtime(labList.get(i).getMtime());
                            labUpdtList.add(model);

                            labCityList.add(hashcityList.get(labList.get(i).getCityId()));
                        }

                        labAdaptor.setDataList(labUpdtList, hashcityList);

                    }

                } else {
                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
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
        apiInterface.getAmb().enqueue(new Callback<List<AmbListModel>>() {
            @Override
            public void onResponse(Call<List<AmbListModel>> call, Response<List<AmbListModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    ambList = response.body();
                    title.setText(ambList.size() + " Ambulance");
                    ambAdaptor.setDataList(ambList, hashcityList);

                } else {
                    avi.hide();

                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
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

    private void getBloodType() {

        bldTypeList.clear();
        apiInterface.getBldType().enqueue(new Callback<List<BldTypeModel>>() {
            @Override
            public void onResponse(Call<List<BldTypeModel>> call, Response<List<BldTypeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    bldTypeList = response.body();
                    title.setText("Choose Blood Type");
                    bldTypeAdaptor.setDataList(bldTypeList);

                } else {

                    avi.hide();
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<BldTypeModel>> call, Throwable t) {
                avi.hide();
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setBloodType() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        bldTypeAdaptor = new BldTypeAdaptor(getActivity(), bldTypeList);
        rv.setAdapter(bldTypeAdaptor);
    }

    private void getMedCat() {

        medCatList.clear();
        apiInterface.getMedCat().enqueue(new Callback<List<MedCatModel>>() {
            @Override
            public void onResponse(Call<List<MedCatModel>> call, Response<List<MedCatModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    medCatList = response.body();
//                    medCatAdaptor.setDataList(medCatList);

                } else {

                    avi.hide();
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<MedCatModel>> call, Throwable t) {
                avi.hide();
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setMedCat() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
//        medCatAdaptor = new MedCatAdaptor(getActivity(), medCatList);
//        rv.setAdapter(medCatAdaptor);
    }

    private void getDocApt() {

        docAptList.clear();
        apiInterface.getDocApt(Constant.DocHis + MyApplication.getUserId()).enqueue(new Callback<List<DocHistoryModel>>() {
            @Override
            public void onResponse(Call<List<DocHistoryModel>> call, Response<List<DocHistoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    docAptList = response.body();
                    docAptAdaptor.setDataList(docAptList, hashSpecList);

                } else {
                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<DocHistoryModel>> call, Throwable t) {
                avi.hide();
                rv.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setDocApt() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true);
        layoutManager.setStackFromEnd(true);

//        rv.smoothScrollToPosition(rv.getBottom());
        rv.setLayoutManager(layoutManager);
        docAptAdaptor = new DocAptAdaptor(getActivity(), docAptList, hashSpecList, "Vertical");
        rv.setAdapter(docAptAdaptor);
    }

    private void getAmbApt() {

        ambAptList.clear();
        apiInterface.getAmbHis(Constant.AmbHis + MyApplication.getUserId()).enqueue(new Callback<List<AmbHistoryModel>>() {
            @Override
            public void onResponse(Call<List<AmbHistoryModel>> call, Response<List<AmbHistoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    ambAptList = response.body();
                    ambAptAdaptor.setDataList(ambAptList, hashSpecList);

                } else {
                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AmbHistoryModel>> call, Throwable t) {
                avi.hide();
                rv.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setAmbApt() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);
        ambAptAdaptor = new AmbAptAdaptor(getActivity(), ambAptList, hashSpecList, "Vertical");
        rv.setAdapter(ambAptAdaptor);
    }

    private void getLabApt() {

        labAptList.clear();
        apiInterface.getLabHis(Constant.LabHis + MyApplication.getUserId()).enqueue(new Callback<List<LabHistoryModel>>() {
            @Override
            public void onResponse(Call<List<LabHistoryModel>> call, Response<List<LabHistoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    labAptList = response.body();
                    labAptAdaptor.setDataList(labAptList, hashSpecList);

                } else {
                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LabHistoryModel>> call, Throwable t) {
                avi.hide();
                rv.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setLabApt() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);
        labAptAdaptor = new LabAptAdaptor(getActivity(), labAptList, hashSpecList, "Vertical");
        rv.setAdapter(labAptAdaptor);
    }

    private void getBloodApt() {

        bloodAptList.clear();
        apiInterface.getBloodHis(Constant.BloodHis + MyApplication.getUserId()).enqueue(new Callback<List<BloodHistoryModel>>() {
            @Override
            public void onResponse(Call<List<BloodHistoryModel>> call, Response<List<BloodHistoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    bloodAptList = response.body();
                    bloodAptAdaptor.setDataList(bloodAptList, hashSpecList);

                } else {
                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BloodHistoryModel>> call, Throwable t) {
                avi.hide();
                rv.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setBloodApt() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);
        bloodAptAdaptor = new BloodAptAdaptor(getActivity(), bloodAptList, hashSpecList, "Vertical");
        rv.setAdapter(bloodAptAdaptor);
    }

    private void getMedHis() {

        medHisList.clear();
        apiInterface.getMedHis(Constant.MedHis + MyApplication.getUserId()).enqueue(new Callback<List<MedHistoryModel>>() {
            @Override
            public void onResponse(Call<List<MedHistoryModel>> call, Response<List<MedHistoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    medHisList = response.body();
                    medAptAdaptor.setDataList(medHisList, hashSpecList);

                } else {
                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MedHistoryModel>> call, Throwable t) {
                avi.hide();
                rv.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setMedHis() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);
        medAptAdaptor = new MedAptAdaptor(getActivity(), medHisList, hashSpecList, "Vertical");
        rv.setAdapter(medAptAdaptor);
    }

    private void getLabSchemeApt() {

        labSchemeHisList.clear();
        apiInterface.getSchemeHis(Constant.SchemeHis + MyApplication.getUserId()).enqueue(new Callback<List<SchemeHistoryModel>>() {
            @Override
            public void onResponse(Call<List<SchemeHistoryModel>> call, Response<List<SchemeHistoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    labSchemeHisList = response.body();
                    schemeAptAdaptor.setDataList(labSchemeHisList, hashSpecList);

                } else {
                    avi.hide();
                    rv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SchemeHistoryModel>> call, Throwable t) {
                avi.hide();
                rv.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setLabSchemeApt() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);
        schemeAptAdaptor = new SchemeAptAdaptor(getActivity(), labSchemeHisList, hashSpecList, "Vertical");
        rv.setAdapter(schemeAptAdaptor);
    }


    // <---------------------------------------------Filter Data------------------------------------------------------------------------------------------------------------------------------------->

    // <---------------------------------------------Doc Filter Data----------------------------------------------------------------------------------------------------------------------------------------->

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openDocFilterMenu() {

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

        List<String> fees = docFeesList.stream().distinct().collect(Collectors.toList());
        if (!DocFilterPreferences.filters.containsKey(DocFilter.INDEX_Fees)) {
            DocFilterPreferences.filters.put(DocFilter.INDEX_Fees, new DocFilter("Fees", fees, new ArrayList()));
        }

        List<String> expr = docExpList.stream().distinct().collect(Collectors.toList());
        if (!DocFilterPreferences.filters.containsKey(DocFilter.INDEX_Exper)) {
            DocFilterPreferences.filters.put(DocFilter.INDEX_Exper, new DocFilter("Expr.", expr, new ArrayList()));
        }

        List<String> city = docCityList.stream().distinct().collect(Collectors.toList());
        if (!DocFilterPreferences.filters.containsKey(DocFilter.INDEX_City)) {
            DocFilterPreferences.filters.put(DocFilter.INDEX_City, new DocFilter("City", city, new ArrayList()));
        }

        List<String> specific = docSpecfList.stream().distinct().collect(Collectors.toList());
        if (!DocFilterPreferences.filters.containsKey(DocFilter.INDEX_Spec)) {
            DocFilterPreferences.filters.put(DocFilter.INDEX_Spec, new DocFilter("Specification", specific, new ArrayList()));
        }

        docFilterAdapter = new DocFilterAdapter(getActivity(), DocFilterPreferences.filters, filterValuesRV);
        filterRV.setAdapter(docFilterAdapter);

        Button clearB = v.findViewById(R.id.clearB);
        clearB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocFilterPreferences.filters.get(DocFilter.INDEX_Fees).setSelected(new ArrayList());
                DocFilterPreferences.filters.get(DocFilter.INDEX_Exper).setSelected(new ArrayList());
                DocFilterPreferences.filters.get(DocFilter.INDEX_City).setSelected(new ArrayList());
                DocFilterPreferences.filters.get(DocFilter.INDEX_Spec).setSelected(new ArrayList());

                docAdaptor.setFilter(docUptList);

                title.setText(docList.size() + " Doctor");
                Toast.makeText(getActivity(), docList.size() + " Doctor", Toast.LENGTH_SHORT).show();

                dialog.hide();

            }
        });

        Button applyB = v.findViewById(R.id.applyB);
        applyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDocFilteredData();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateDocFilteredData() {
        if (!DocFilterPreferences.filters.isEmpty()) {
            docfilteredItems = new ArrayList<DocModel>();
            List<String> Fees = DocFilterPreferences.filters.get(DocFilter.INDEX_Fees).getSelected();
            List<String> Exprnc = DocFilterPreferences.filters.get(DocFilter.INDEX_Exper).getSelected();
            List<String> City = DocFilterPreferences.filters.get(DocFilter.INDEX_City).getSelected();
            List<String> Specfic = DocFilterPreferences.filters.get(DocFilter.INDEX_Spec).getSelected();

            for (DocModel item : docUptList) {

                boolean feesMatched = true;
                if (Fees.size() > 0 && !Fees.contains(item.getFee())) {
                    feesMatched = false;
                }

                boolean exprMatched = true;
                if (Exprnc.size() > 0 && !Exprnc.contains(item.getDrExp())) {
                    exprMatched = false;
                }

                boolean cityMatched = true;
                if (City.size() > 0 && !City.contains(item.getCityName())) {
                    cityMatched = false;
                }
                boolean specificMatched = true;
                if (Specfic.size() > 0 && !Specfic.contains(item.getSpecName())) {
                    specificMatched = false;
                }
//
                if (feesMatched && exprMatched && cityMatched && specificMatched) {
                    docfilteredItems.add(item);
                }
            }
            docFilteredList = docfilteredItems;
        }

        title.setText(docFilteredList.size() + " Doctor");
        Toast.makeText(getActivity(), docFilteredList.size() + " Doctor", Toast.LENGTH_SHORT).show();
        docAdaptor.setFilter(docFilteredList);
        dialog.hide();
    }


    // <---------------------------------------------Lab Filter Data----------------------------------------------------------------------------------------------------------------------------------------->

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openLabFilterMenu() {

        dialog = new BottomSheetDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_filter, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        initLabFiltControls(dialogView);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    private void initLabFiltControls(View v) {
        filterRV = v.findViewById(R.id.filterRV);
        filterValuesRV = v.findViewById(R.id.filterValuesRV);
        filterRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        filterValuesRV.setLayoutManager(new LinearLayoutManager(getActivity()));

            List<String> city = labCityList.stream().distinct().collect(Collectors.toList());


        if (!LabFilterPreferences.filters.containsKey(LabFilter.INDEX_City)) {
            LabFilterPreferences.filters.put(LabFilter.INDEX_City, new LabFilter("City", city, new ArrayList()));
        }

//        List<String> expr = docExpList.stream().distinct().collect(Collectors.toList());
//        if (!DocFilterPreferences.filters.containsKey(DocFilter.INDEX_Exper)) {
//            DocFilterPreferences.filters.put(DocFilter.INDEX_Exper, new DocFilter("Expr.", expr, new ArrayList()));
//        }

        labFilterAdapter = new LabFilterAdapter(getActivity(), LabFilterPreferences.filters, filterValuesRV);
        filterRV.setAdapter(labFilterAdapter);

        Button clearB = v.findViewById(R.id.clearB);
        clearB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                LabFilterPreferences.filters.get(LabFilter.INDEX_City).setSelected(new ArrayList());
//                DocFilterPreferences.filters.get(DocFilter.INDEX_Exper).setSelected(new ArrayList());

                labAdaptor.setFilter(labUpdtList);

                title.setText(labUpdtList.size() + " Lab");
                Toast.makeText(getActivity(), labUpdtList.size() + " Lab", Toast.LENGTH_SHORT).show();

                dialog.hide();

            }
        });

        Button applyB = v.findViewById(R.id.applyB);
        applyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLabFilteredData();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateLabFilteredData() {
        if (!LabFilterPreferences.filters.isEmpty()) {
            labfilteredItems = new ArrayList<LabModel>();
            List<String> City = LabFilterPreferences.filters.get(LabFilter.INDEX_City).getSelected();
//            List<String> Exprnc = DocFilterPreferences.filters.get(DocFilter.INDEX_Exper).getSelected();

            for (LabModel item : labUpdtList) {

                boolean cityMatched = true;
                if (City.size() > 0 && !City.contains(item.getCityName())) {
                    cityMatched = false;
                }
//                boolean exprMatched = true;
//                if (Exprnc.size() > 0 && !Exprnc.contains(item.getDrExp())) {
//                    exprMatched = false;
//                }
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
                if (cityMatched) {
                    labfilteredItems.add(item);
                }
            }
            labFilteredList = labfilteredItems;
        }

        title.setText(labFilteredList.size() + " Lab");
        Toast.makeText(getActivity(), labFilteredList.size() + " Lab", Toast.LENGTH_SHORT).show();
        labAdaptor.setFilter(labFilteredList);
        dialog.hide();
    }


}