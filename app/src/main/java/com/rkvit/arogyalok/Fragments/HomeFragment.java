package com.rkvit.arogyalok.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rkvit.arogyalok.Adapter.AmbAdaptor;
import com.rkvit.arogyalok.Adapter.DocAdaptor;
import com.rkvit.arogyalok.Adapter.ImgSliderAdapter;
import com.rkvit.arogyalok.Adapter.LabAdaptor;
import com.rkvit.arogyalok.Adapter.MedAdaptor;
import com.rkvit.arogyalok.Adapter.MedHomeAdaptor;
import com.rkvit.arogyalok.Adapter.SchemHomeAdaptor;
import com.rkvit.arogyalok.Model.AmbListModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.DocModel;
import com.rkvit.arogyalok.Model.ImgsModel;
import com.rkvit.arogyalok.Model.LabModel;
import com.rkvit.arogyalok.Model.LabSchemModel;
import com.rkvit.arogyalok.Model.LabTestModel;
import com.rkvit.arogyalok.Model.MedListModel;
import com.rkvit.arogyalok.Model.SpecificationModel;
import com.rkvit.arogyalok.Model.TestDetailsModel;
import com.rkvit.arogyalok.Model.TestMasterModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rkvit.arogyalok.RetrofitUtil.Constant.SHARED_FILE_NAME;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private RecyclerView catRv, docsRv, hospitalRv, labsRv, ambRv,labSchemeRv, medRv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private ApiInterface apiInterface;
    private LinearLayout listView;

    private ImgSliderAdapter sliderAdapter;

    private AVLoadingIndicatorView avi;
    private List<SpecificationModel> specList = new ArrayList<>();
    private List<Parcelable> placeList = new ArrayList();
    private List<CityModel> cityList = new ArrayList<>();

    private HashMap<String, String> hashSpecList = new HashMap<String, String>();
    private HashMap<String, String> hashcityList = new HashMap<String, String>();
    private LinearLayout doc, ambulance, lab, medicine, bloodBank,aptHistory;
    private LinearLayout docApt, ambulanceApt, labApt, medicineApt, bloodBankApt , labSchemeApt;

    private HashMap<String, String> hashsubCatList = new HashMap<String, String>();
    private List<ImgsModel> sliderList = new ArrayList<>();
    private SliderView sliderView;
    private List<DocModel> docList = new ArrayList<>();
    private DocAdaptor docAdaptor;

    private LabAdaptor labAdaptor;
    private List<AmbListModel> ambList = new ArrayList<>();
    private List<LabModel> labList = new ArrayList<>();
    private AmbAdaptor ambAdaptor;
    private List<TestMasterModel> testList = new ArrayList<>();
    private List<TestDetailsModel> labTestDetails = new ArrayList<>();
    private BottomSheetDialog dialog;
    private List<TestMasterModel> allTestList = new ArrayList<>();
    private HashMap<String, String> hashTestList = new HashMap<String, String>();
    private SchemHomeAdaptor schemHomeAdaptor;
    private List<LabSchemModel> labSchmList = new ArrayList<>();
    private List<DocModel> docUptList = new ArrayList<>();
    private List<LabTestModel> labTest = new ArrayList<>();
    private List<MedListModel> medList = new ArrayList<>();
    private List<MedListModel> medUpdtList = new ArrayList<>();;
    private MedHomeAdaptor medAdaptor;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(ApiInterface.class);

        getPlaceList();
        getSpecification();

        avi = v.findViewById(R.id.avi);
        listView = v.findViewById(R.id.list_view);

        doc = v.findViewById(R.id.doc);
        doc.setOnClickListener(this);

        ambulance = v.findViewById(R.id.ambulance);
        ambulance.setOnClickListener(this);

        lab = v.findViewById(R.id.lab);
        lab.setOnClickListener(this);

        medicine = v.findViewById(R.id.medicine);
        medicine.setOnClickListener(this);

        bloodBank = v.findViewById(R.id.blood_bank);
        bloodBank.setOnClickListener(this);

        aptHistory = v.findViewById(R.id.apt_history);
        aptHistory.setOnClickListener(this);


        sliderView = v.findViewById(R.id.imageSlider);

        docsRv = v.findViewById(R.id.get_docs);
        labsRv = v.findViewById(R.id.get_labs);
        ambRv = v.findViewById(R.id.get_amb);
        medRv = v.findViewById(R.id.get_med);
        labSchemeRv = v.findViewById(R.id.get_lab_scheme);

        getSlider();
        setSlider();

        getDoc();
        setDoc();

        getLab();
        setLab();

        getAmb();
        setAmb();

        getMedList();
        setMedList();

        getLabSchem();
        setLabSchm();

//        getTestDetails();
//        getLabTests();


        swipeRefreshLayout = v.findViewById(R.id.swipeContainer_lead);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                getPlaceList();
                getSpecification();

                getSlider();
                setSlider();

                getDoc();
                setDoc();

                getLab();
                setLab();

                getAmb();
                setAmb();

                getMedList();
                setMedList();

                getLabSchem();
                setLabSchm();

            }
        });

        return v;

    }

    public void getPlaceList() {
        cityList.clear();
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

    public void getSpecification() {
        specList.clear();
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

    private void getSlider() {

        sliderList.clear();
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getSlider().enqueue(new Callback<List<ImgsModel>>() {
            @Override
            public void onResponse(Call<List<ImgsModel>> call, Response<List<ImgsModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {

                        sliderList = response.body();
                        sliderAdapter.setSliderData(sliderList, Constant.ImgRoot);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<ImgsModel>> call, Throwable t) {
//                avi.hide();
//                layoutView.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setSlider() {
        sliderAdapter = new ImgSliderAdapter(getActivity(), sliderList);

        sliderView.setSliderAdapter(sliderAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();
    }


    private void getMedList() {

        medList.clear();
        medUpdtList.clear();
        apiInterface.getMedList().enqueue(new Callback<List<MedListModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<MedListModel>> call, Response<List<MedListModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    medList = response.body();

                    if (medList.size() != 0) {

                        for (int i = 0; i < medList.size(); i++) {

                            if (medList.get(i).getHotSale().equals("Yes")) {

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
//                            model.setCatName(hashCatList.get(medList.get(i).getCatId()));
//                            model.setSubCatName(hashSubCatList.get(medList.get(i).getSubcatId()));
                                model.setCatId(medList.get(i).getCatId());
                                model.setSubcatId(medList.get(i).getSubcatId());

                                medUpdtList.add(model);
                            }

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        medRv.setLayoutManager(layoutManager);
        medAdaptor = new MedHomeAdaptor(getActivity(), medUpdtList);
        medRv.setAdapter(medAdaptor);
    }

    private void getDoc() {

        docList.clear();
        docUptList.clear();
        apiInterface.getDoc().enqueue(new Callback<List<DocModel>>() {
            @Override
            public void onResponse(Call<List<DocModel>> call, Response<List<DocModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    listView.setVisibility(View.VISIBLE);
                    docList = response.body();

                    if (docList.size()!=0) {
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

                        }

                        docAdaptor.setDataList(docUptList, hashSpecList);

                    }

                } else {
                    avi.hide();
//                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<DocModel>> call, Throwable t) {
                avi.hide();
                docsRv.setVisibility(View.VISIBLE);
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void setDoc() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        docsRv.setLayoutManager(layoutManager);
        docAdaptor = new DocAdaptor(getActivity(), docUptList, hashSpecList, "Vertical");
        docsRv.setAdapter(docAdaptor);
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(key, json);
    }

    public void set(String key, String value) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void getLab() {

        labList.clear();
        apiInterface.getLab().enqueue(new Callback<List<LabModel>>() {
            @Override
            public void onResponse(Call<List<LabModel>> call, Response<List<LabModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    labList = response.body();
                    labAdaptor.setDataList(labList, hashcityList);

                } else {

//                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        labsRv.setLayoutManager(layoutManager);
        labAdaptor = new LabAdaptor(getActivity(), labList, hashcityList, "Horizontal");
        labsRv.setAdapter(labAdaptor);
    }

    private void getAmb() {

        ambList.clear();
        apiInterface.getAmb().enqueue(new Callback<List<AmbListModel>>() {
            @Override
            public void onResponse(Call<List<AmbListModel>> call, Response<List<AmbListModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    avi.hide();
                    ambList = response.body();
                    ambAdaptor.setDataList(ambList, hashcityList);

                } else {
                    avi.hide();

//                    Toast.makeText(getActivity(), "response Unsuccessful", Toast.LENGTH_LONG).show();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        ambRv.setLayoutManager(layoutManager);
        ambAdaptor = new AmbAdaptor(getActivity(), ambList, hashcityList, "Horizontal");
        ambRv.setAdapter(ambAdaptor);
    }


    private void getLabSchem() {

        allTestList.clear();
        labSchmList.clear();
        //getting all test
        String serializedObject = MyApplication.getAllTestList();
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<TestMasterModel>>() {
            }.getType();
            allTestList = gson.fromJson(serializedObject, type);
        }

        if (allTestList!=null) {
            for (int i = 0; i < allTestList.size(); i++) {

                hashTestList.put(allTestList.get(i).getId(), allTestList.get(i).getName());

            }
        }

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getLabSchem()
                .enqueue(new Callback<List<LabSchemModel>>() {
                    @Override
                    public void onResponse(Call<List<LabSchemModel>> call, Response<List<LabSchemModel>> response) {
//                        progress.cancel();

                        if (response.isSuccessful() && response.body().size() != 0) {

                            labSchmList = response.body();
                            schemHomeAdaptor.setDataList(labSchmList, hashTestList);
//                            progress.cancel();

                        } else {
//                            progress.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LabSchemModel>> call, Throwable t) {
//                        progress.cancel();
//                        labSchemLinear.setVisibility(View.GONE);
                        Log.e("error ", t.getMessage());

                    }
                });

    }

    private void setLabSchm() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        labSchemeRv.setLayoutManager(layoutManager);
        schemHomeAdaptor = new SchemHomeAdaptor(getActivity(), labSchmList, hashTestList);
        labSchemeRv.setAdapter(schemHomeAdaptor);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.doc:
                addFragment("doc");
                break;

            case R.id.ambulance:
                addFragment("amb");
                break;

            case R.id.lab:
                addFragment("lab");
                break;

            case R.id.blood_bank:
                addFragment("blood_bank");
                break;

            case R.id.medicine:
                medFragment("medicine");
                break;

            case R.id.apt_history:
                openAppointment();

                break;

            default:
        }
    }

    private void openAppointment() {

        dialog = new BottomSheetDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.apt_his_layout, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        docApt = dialogView.findViewById(R.id.doc_apt);
        docApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("doc_apt");
            }
        });

        ambulanceApt = dialogView.findViewById(R.id.ambulance_apt);
        ambulanceApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("amb_apt");
            }
        });

        labApt = dialogView.findViewById(R.id.lab__apt);
        labApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("lab_apt");
            }
        });
        bloodBankApt = dialogView.findViewById(R.id.blood_bank__apt);
        bloodBankApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("blood_apt");
            }
        });

        medicineApt = dialogView.findViewById(R.id.medicine__apt);
        medicineApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("medicine_apt");
            }
        });

        labSchemeApt = dialogView.findViewById(R.id.lab_scheme_apt);
        labSchemeApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                addFragment("lab_scheme_apt");
            }
        });

    }

    private void addFragment(String list) {

        SharedPreferences pre = getActivity().getSharedPreferences("menu", Context.MODE_PRIVATE);
        pre.edit().putString("loadList", list).apply();

        FragmentTransaction transaction = getActivity()
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, new ListFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void medFragment(String list) {

//        SharedPreferences pre = getActivity().getSharedPreferences("menu", Context.MODE_PRIVATE);
//        pre.edit().putString("loadList", list).apply();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, new MedicineFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}