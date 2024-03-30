package com.rkvit.arogyalok.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.LabTestModel;
import com.rkvit.arogyalok.Model.SpecificationModel;
import com.rkvit.arogyalok.Model.TestDetailsModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rkvit.arogyalok.RetrofitUtil.Constant.SHARED_FILE_NAME;

public class Splashscreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    RelativeLayout layout;
    private ImageView imgLogo;
    private String UserId, LoggedIn;
    private SharedPreferences sharedPreferences;
    private ApiInterface apiInterface;
    private List<SpecificationModel> specList = new ArrayList<>();
    private List<CityModel> cityList = new ArrayList<>();
    private List<LabTestModel> labTest = new ArrayList<>();
    private List<TestDetailsModel> labTestDetails = new ArrayList<>();
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreferences = getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(ApiInterface.class);

        avi = findViewById(R.id.loader);

        layout = findViewById(R.id.splash);
        getCity();
        getSpec();

        getLabTests();
        getTestDetails();

        Log.v("USERID", MyApplication.getUserId());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences("IntroScreen", MODE_PRIVATE);
                boolean intro = preferences.getBoolean("First Time", false);

                avi.hide();

                if (intro) {

                    if (MyApplication.getLoginStatus().equals("1")) {
                        startActivity(new Intent(Splashscreen.this, MainHome.class));
                    }
                    else {
                        startActivity(new Intent(Splashscreen.this, LoginActivity.class));
                    }
                } else
                    startActivity(new Intent(Splashscreen.this, WelcomeActivity.class));

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }


    private void getSpec() {

        specList.clear();
        apiInterface.getSpecilization().enqueue(new Callback<List<SpecificationModel>>() {
            @Override
            public void onResponse(Call<List<SpecificationModel>> call, Response<List<SpecificationModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    specList = response.body();
//                    for (int i = 0; i < specList.size(); i++) {
//                        hashSpecList.put(specList.get(i).getId(), specList.get(i).getName());
//                    }

                    MyApplication.setSpecification(specList);


                } else {

//                    Toast.makeText(getActivity(), "slow internet, try again later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SpecificationModel>> call, Throwable t) {
                Log.e("error ", t.getMessage());

            }
        });

    }

    private void getCity() {

        cityList.clear();
        apiInterface.getCity().enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    cityList = response.body();
//                    for (int i = 0; i < cityList.size(); i++) {
//                        hashcityList.put(cityList.get(i).getId(), cityList.get(i).getName());
//                    }

                    MyApplication.setCityList(cityList);

//                    setList(FILE_NAME, cityList);

                } else {
//                    avi.hide();
//                    layoutView.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), "slow internet, try again later", Toast.LENGTH_LONG).show();
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

    private void getLabTests() {

//        ProgressDialog progress = new ProgressDialog(getActivity());
//        progress.setMessage("Loading... Please wait");
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();


        String URL = Constant.LabTest;
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getLabTest(URL)
                .enqueue(new Callback<List<LabTestModel>>() {
                    @Override
                    public void onResponse(Call<List<LabTestModel>> call, Response<List<LabTestModel>> response) {
//                        progress.cancel();

                        if (response.isSuccessful()) {

                            labTest = response.body();
                            MyApplication.setLabTestsList(labTest);

//                            progress.cancel();

                        } else {
//                            progress.cancel();
                            Toast.makeText(Splashscreen.this, "slow internet, try again later", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LabTestModel>> call, Throwable t) {
//                        progress.cancel();

                        Log.e("error ", t.getMessage());

                    }
                });

    }

    private void getTestDetails() {

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        apiInterface.getLabTestDetails(Constant.AllTest)
                .enqueue(new Callback<List<TestDetailsModel>>() {
                    @Override
                    public void onResponse(Call<List<TestDetailsModel>> call, Response<List<TestDetailsModel>> response) {

                        if (response.isSuccessful()) {

                            labTestDetails = response.body();

                            MyApplication.setTestDetailsList(labTestDetails);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<TestDetailsModel>> call, Throwable t) {

                        Log.e("error ", t.getMessage());

                    }
                });

    }

}
