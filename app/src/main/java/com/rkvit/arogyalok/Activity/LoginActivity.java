package com.rkvit.arogyalok.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.LoginModel;
import com.rkvit.arogyalok.Model.OtpModel;
import com.rkvit.arogyalok.Model.SignupModel;
import com.rkvit.arogyalok.Model.StateModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.APIClient;
import com.rkvit.arogyalok.RetrofitUtil.ApiInterface;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView loginLayout, signupLayout, loginBtn, signupBtn;
    private LinearLayout signupNameLayout, signupBtnLayout, loginBtnLayout;
    private EditText name, email, mobile, password;
    private ImageView showPas, hidePas;
    private boolean valid;
    private String UserName, UserMobile, UserEmail, UserPas, UserId, UserToken;
    private ApiInterface apiInterface;
    private AutoCompleteTextView stateDropdown, cityDropdown, pincode;
    private List<CityModel> cityList = new ArrayList<>();
    private List<String> cityName = new ArrayList<>();
    private List<String> cityId = new ArrayList<>();
    private List<String> stateName = new ArrayList<>();
    private List<String> stateId = new ArrayList<>();
    private HashMap<String, String> hashcityList = new HashMap<String, String>();
    private String CitytName, CityId;
    private Spinner bloodGrp;
    private List<StateModel> stateList = new ArrayList<>();
    private String StateId;
    private TextView associateLogin, termCond, associateLoginTwo, forgetPassword;
    private BottomSheetDialog dialog;
    private LinearLayout doc, amb, lab, med;
    private TextView close, resendOtp, otpBtn;
    private EditText otpMobileNo, otpEnter, newPassword;
    private Dialog passwordDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        apiInterface = APIClient.getClient().create(ApiInterface.class);

        getState();

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

        signupBtn = findViewById(R.id.signup_btn);
        signupBtn.setOnClickListener(this);

        loginLayout = findViewById(R.id.Show_login_layout);
        loginLayout.setOnClickListener(this);

        signupLayout = findViewById(R.id.Show_signup_layout);
        signupLayout.setOnClickListener(this);

        showPas = findViewById(R.id.show_password);
        showPas.setOnClickListener(this);

        hidePas = findViewById(R.id.hide_password);
        hidePas.setOnClickListener(this);

        signupNameLayout = findViewById(R.id.signup__name_layout);

        signupBtnLayout = findViewById(R.id.signup_btn_layout);
        loginBtnLayout = findViewById(R.id.login_btn_layout);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);

        //weblink
        termCond = findViewById(R.id.term_cond);
        termCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.TermCond));
                startActivity(browser);
            }
        });

        associateLogin = findViewById(R.id.associate_login);
        associateLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMenu();
            }
        });

        associateLoginTwo = findViewById(R.id.associate_login_two);
        associateLoginTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMenu();
            }
        });

        forgetPassword = findViewById(R.id.forget_password);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgetPasMenu();
            }
        });


        bloodGrp = findViewById(R.id.blood_group);

        stateDropdown = findViewById(R.id.state_dropdown);
        stateDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, stateName);
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

        pincode = findViewById(R.id.pincode);

        cityDropdown = findViewById(R.id.city_dropdown);
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

                CitytName = parent.getItemAtPosition(position).toString();
                CityId = cityId.get(position);
                cityDropdown.setText(CitytName);
            }
        });

    }


    private void forgetPasMenu() {

        passwordDialog = new Dialog(LoginActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.forget_pas_layout, null);
        passwordDialog.setContentView(dialogView);

        if (passwordDialog.getWindow() != null) {
            passwordDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            passwordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        passwordDialog.show();
        passwordDialog.setCancelable(false);


        close = dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordDialog.cancel();
            }
        });

        otpMobileNo = dialogView.findViewById(R.id.otpMobileNo);
        otpMobileNo.setText(mobile.getText().toString());

        if (otpMobileNo.getText().toString().isEmpty())
            Toast.makeText(this, "Enter registered mobile number.", Toast.LENGTH_SHORT).show();
        else
            getOtp(otpMobileNo.getText().toString());

        otpMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 10)
                    getOtp(otpMobileNo.getText().toString());

            }
        });

        resendOtp = dialogView.findViewById(R.id.resend_otp);
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!otpMobileNo.getText().toString().isEmpty())
                    getOtp(otpMobileNo.getText().toString());
                else
                    Toast.makeText(LoginActivity.this, "Enter registered mobile number first.", Toast.LENGTH_SHORT).show();
            }
        });

        otpEnter = dialogView.findViewById(R.id.otp);

        newPassword = dialogView.findViewById(R.id.new_pas);

        otpBtn = dialogView.findViewById(R.id.change_btn);
        otpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changePassValid()) {
                    if (otpEnter.getText().toString().equals(MyApplication.getUserToken())) {
                        changePassword(otpMobileNo.getText().toString(), newPassword.getText().toString());
                    } else
                        Toast.makeText(LoginActivity.this, "OTP didn't matched", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getOtp(String mobileNo) {

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        //Retrofit Call
        Call<BookingMsgModel> call = apiInterface.getOtp(mobileNo);
        call.enqueue(new Callback<BookingMsgModel>() {
            @Override
            public void onResponse(Call<BookingMsgModel> call, retrofit2.Response<BookingMsgModel> response) {
                progress.cancel();

                if (response.isSuccessful() && response.body() != null && response.body().getStatus().toString().equals("true")) {

                    Toast.makeText(LoginActivity.this, "OTP sent to registered mobile number", Toast.LENGTH_SHORT).show();

                    MyApplication.setUserToken(String.valueOf(response.body().getOtp()));

//                    otpVerify();
//                    Login();

                } else
                    Toast.makeText(LoginActivity.this, "Mobile no doesn't exits, Please Check Mobile no", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BookingMsgModel> call, Throwable t) {
                call.cancel();
                progress.cancel();
                FancyToast.makeText(LoginActivity.this, "Something went wrong.\n Try again later", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });


    }

    private void changePassword(String mobileNo, String newPassword) {

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        //Retrofit Call
        Call<BookingMsgModel> call = apiInterface.changePassword(mobileNo, newPassword);
        call.enqueue(new Callback<BookingMsgModel>() {
            @Override
            public void onResponse(Call<BookingMsgModel> call, retrofit2.Response<BookingMsgModel> response) {
                progress.cancel();

                if (response.isSuccessful() && response.body() != null && response.body().getStatus().toString().equals("true")) {

                    passwordDialog.cancel();

                    Toast.makeText(LoginActivity.this, "Password changed", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(LoginActivity.this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BookingMsgModel> call, Throwable t) {
                call.cancel();
                passwordDialog.cancel();
                progress.cancel();
                FancyToast.makeText(LoginActivity.this, "Something went wrong.\n Try again later", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });


    }

    private void otpVerify() {
        Dialog alertDialog = new Dialog(LoginActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.verify_otp_layout, null);
        alertDialog.setContentView(dialogView);

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
        alertDialog.setCancelable(false);


        close = dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        resendOtp = dialogView.findViewById(R.id.resend_otp);
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
            }
        });

        otpMobileNo = dialogView.findViewById(R.id.otpMobileNo);
        otpMobileNo.setText(UserMobile);
        otpEnter = dialogView.findViewById(R.id.otp);

        otpBtn = dialogView.findViewById(R.id.change_btn);
        otpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!otpEnter.getText().toString().isEmpty()) {
                    if (otpEnter.getText().toString().equals(MyApplication.getUserToken())) {
                        alertDialog.cancel();
                        VerifyOtp();
                    }
                    else
                        FancyToast.makeText(LoginActivity.this, "OTP didn't matched", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                } else
                    Toast.makeText(LoginActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void resendOtp() {

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Processing...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        //Retrofit Call
        Call<OtpModel> call = apiInterface.resendOtp(UserMobile);
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, retrofit2.Response<OtpModel> response) {
                progress.cancel();

                if (response.isSuccessful() && response.body().isStatus()) {

                    Toast.makeText(LoginActivity.this, "Otp Received", Toast.LENGTH_SHORT).show();
                    MyApplication.setUserToken(String.valueOf(response.body().getOtp()));

                }
                else
                    FancyToast.makeText(LoginActivity.this, "Something went wrong.\n Try again later", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }

            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {
                call.cancel();
                progress.cancel();
                FancyToast.makeText(LoginActivity.this, "Something went wrong.\n Try again later", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });


    }

    private void VerifyOtp() {

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Verifying...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        //Retrofit Call
        Call<BookingMsgModel> call = apiInterface.otpVerify(UserMobile);
        call.enqueue(new Callback<BookingMsgModel>() {
            @Override
            public void onResponse(Call<BookingMsgModel> call, retrofit2.Response<BookingMsgModel> response) {
                progress.cancel();

                if (response.isSuccessful() && response.body() != null && response.body().getStatus().toString().equals("true")) {

                    FancyToast.makeText(LoginActivity.this, "OTP Verified", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    Login();

                }
                else
                    FancyToast.makeText(LoginActivity.this, "OTP didn't matched", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }

            @Override
            public void onFailure(Call<BookingMsgModel> call, Throwable t) {
                call.cancel();
                progress.cancel();
                FancyToast.makeText(LoginActivity.this, "Something went wrong.\n Try again later", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });


    }

    private boolean changePassValid() {

        valid = true;
        if (otpMobileNo.getText().toString().isEmpty()) {
            otpMobileNo.setError("required");
            otpMobileNo.requestFocus();
            valid = false;
        }

        if (otpEnter.getText().toString().isEmpty()) {
            otpEnter.setError("required");
            otpEnter.requestFocus();
            valid = false;
        }

        if (newPassword.getText().toString().isEmpty()) {
            newPassword.setError("required");
            newPassword.requestFocus();
            valid = false;
        }
        return valid;
    }

    private void openMenu() {

        dialog = new BottomSheetDialog(LoginActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.login_type_layout, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        doc = dialogView.findViewById(R.id.doc_apt);
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                SharedPreferences pref = getSharedPreferences("Weblogin", MODE_PRIVATE);
                pref.edit().putString("type", Constant.DocLogin)
                        .apply();

                Intent i = new Intent(LoginActivity.this, MainHome.class);
                i.putExtra("weblogin", "doc");
                startActivity(i);
            }
        });

        amb = dialogView.findViewById(R.id.ambulance_apt);
        amb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                SharedPreferences pref = getSharedPreferences("Weblogin", MODE_PRIVATE);
                pref.edit().putString("type", Constant.AmbLogin)
                        .apply();

                Intent i = new Intent(LoginActivity.this, MainHome.class);
                i.putExtra("weblogin", "doc");
                startActivity(i);
            }
        });

        lab = dialogView.findViewById(R.id.lab__apt);
        lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                SharedPreferences pref = getSharedPreferences("Weblogin", MODE_PRIVATE);
                pref.edit().putString("type", Constant.LabLogin)
                        .apply();

                Intent i = new Intent(LoginActivity.this, MainHome.class);
                i.putExtra("weblogin", "doc");
                startActivity(i);
            }
        });
        med = dialogView.findViewById(R.id.medicine__apt);
        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                SharedPreferences pref = getSharedPreferences("Weblogin", MODE_PRIVATE);
                pref.edit().putString("type", Constant.MedLogin)
                        .apply();

                Intent i = new Intent(LoginActivity.this, MainHome.class);
                i.putExtra("weblogin", "doc");
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Show_login_layout:

                signupNameLayout.setVisibility(View.GONE);
                signupBtnLayout.setVisibility(View.GONE);
                loginBtnLayout.setVisibility(View.VISIBLE);

                break;

            case R.id.Show_signup_layout:

                signupNameLayout.setVisibility(View.VISIBLE);
                signupBtnLayout.setVisibility(View.VISIBLE);
                loginBtnLayout.setVisibility(View.GONE);
                break;

            case R.id.signup_btn:
                if (signupValid())
                    Signup();
                break;

            case R.id.login_btn:

                if (loginValid())
                    Login();
                break;

            case R.id.show_password:
                password.setTransformationMethod(null);
                showPas.setVisibility(View.GONE);
                hidePas.setVisibility(View.VISIBLE);
                break;

            case R.id.hide_password:
                password.setTransformationMethod(new PasswordTransformationMethod());
                showPas.setVisibility(View.VISIBLE);
                hidePas.setVisibility(View.GONE);
                break;

            default:
        }
    }

    private boolean signupValid() {
        UserName = name.getText().toString();
        UserMobile = mobile.getText().toString();
        UserEmail = email.getText().toString();
        UserPas = password.getText().toString();

        valid = true;
        if (UserName.isEmpty()) {
            name.setError("required");
            name.requestFocus();
            valid = false;
        }

        if (UserMobile.isEmpty()) {
            mobile.setError("required");
            mobile.requestFocus();
            valid = false;
        }

        if (UserPas.isEmpty()) {
            password.setError("required");
            password.requestFocus();
            valid = false;
        }

        if (pincode.getText().toString().isEmpty()) {
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
//
//        if (!cityName.contains(cityDropdown.getText().toString())) {
//            cityDropdown.setError("required");
//            cityDropdown.requestFocus();
//            valid = false;
//        }


        return valid;
    }


    private void Signup() {

        String BloodGrp = bloodGrp.getSelectedItem().toString();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        //Retrofit Call
        Call<SignupModel> call = apiInterface.userSignup(UserName, UserMobile, UserPas, CityId, StateId, pincode.getText().toString(), BloodGrp);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                progress.cancel();

                if (response.isSuccessful() && response.body() != null && response.body().getStatus().toString().equals("true")) {

                    MyApplication.setUserToken(String.valueOf(response.body().getOtp()));

                    otpVerify();

                } else
                    FancyToast.makeText(LoginActivity.this, "Already Registered\n Please Login", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                call.cancel();
                progress.cancel();
                FancyToast.makeText(LoginActivity.this, "Something went wrong.\n Try again later", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });


    }

    private boolean loginValid() {
        UserMobile = mobile.getText().toString();
        UserPas = password.getText().toString();

        valid = true;

        if (UserMobile.isEmpty()) {
            mobile.setError("required");
            mobile.requestFocus();
            valid = false;
        }

        if (UserPas.isEmpty()) {
            password.setError("required");
            password.requestFocus();
            valid = false;
        }
        return valid;
    }

    private void Login() {

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Wait a moment...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        //Retrofit Call
        Call<LoginModel> call = apiInterface.userLogin(UserMobile, UserPas);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                progress.cancel();

                if (response.isSuccessful() && response.body() != null) {
                    UserId = response.body().getId();
                    UserToken = response.body().getToken();

                    MyApplication.setUserId(String.valueOf(response.body().getId()));
                    MyApplication.setUserName(response.body().getName());
                    MyApplication.setUserEmail(response.body().getEmail());
                    MyApplication.setMobile(response.body().getMobile());
                    MyApplication.setUserToken(response.body().getToken());


                    if (response.body().getIs_verify().equals("1")) {
                        MyApplication.setLoginStatus(response.body().getIs_verify());
                        startActivity(new Intent(LoginActivity.this, MainHome.class));
                        finish();
                    } else {
                        MyApplication.setLoginStatus(response.body().getIs_verify());
                        otpVerify();
                    }
                } else
                    FancyToast.makeText(LoginActivity.this, "Please Check Id & Password", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                call.cancel();
                progress.cancel();
                FancyToast.makeText(LoginActivity.this, "Can't Login. Something went wrong.\n Try again later", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });

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

//                    MyApplication.setCityList(cityList);

//                    setList(FILE_NAME, cityList);

                } else {
//                    avi.hide();
//                    layoutView.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "response Unsuccessful", Toast.LENGTH_LONG).show();
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

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading City... ");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        cityList.clear();
        cityName.clear();
        cityId.clear();
        apiInterface.getCity().enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    progress.cancel();

                    cityList = response.body();
                    for (int i = 0; i < cityList.size(); i++) {
                        hashcityList.put(cityList.get(i).getId(), cityList.get(i).getName());
                        if (cityList.get(i).getStateId().equals(stateId)) {
                            cityName.add(cityList.get(i).getName());
                            cityId.add(cityList.get(i).getId());
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, cityName);
                    cityDropdown.setAdapter(adapter);

                    MyApplication.setCityList(cityList);

//                    setList(FILE_NAME, cityList);

                } else {
                    progress.cancel();
                    Toast.makeText(LoginActivity.this, "slow internet, try later", Toast.LENGTH_LONG).show();
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
}