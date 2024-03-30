package com.rkvit.arogyalok;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.GridLayout;

import com.google.gson.Gson;
import com.rkvit.arogyalok.Model.TestMasterModel;

import java.util.List;

public class MyApplication extends Application {

    private static final String MY_PREFS_NAME = "Arogyalok";
    private static final String ISLOGIN = "ISLOGIN";
    private static final String LoginStatus = "LoginStatus";
    private static final String IsFirstTime = "IsFirstTime";

    public static SharedPreferences prefs;
    public static SharedPreferences.Editor editor;
    private static String userEmail = "Email";

    private static final String UserToken = "UserToken";
    private static final String UserName = "UserName";
    private static final String UserMobile = "UserMobile";
    private static final String UserEmail = "UserEmail";
    private static final String UserId = "UserId";

    private static final String CityList = "CityList";
    private static final String AllTestList = "AllTestList";
    private static final String SchemeList = "Scheme";
    private static final String Specification = "Specification";
    private static final String Tests = "Tests";
    private static final String LabTests ="LabTests";

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = prefs.edit();
//        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
//        appSignatureHelper.getAppSignatures();
    }

    public MyApplication() {
    }


    public static void clearPref() {
//        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().apply();
//        editor.apply();
    }


    public static <T> void setCityList(List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(CityList, json);
        editor.apply();
    }

    public static String getCityList(){
        return prefs.getString(CityList, "null");
    }



    public static <T> void setLabTestsList(List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(LabTests, json);
        editor.apply();
    }

    public static String getLabTestsList(){
        return prefs.getString(LabTests, "null");
    }

    public static <T> void setTestDetailsList(List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(AllTestList, json);
        editor.apply();
    }

    public static String getAllTestList(){
        return prefs.getString(AllTestList, "null");
    }

    public static <T> void setScheme(List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(SchemeList, json);
        editor.apply();
    }

    public static String getScheme(){
        return prefs.getString(SchemeList, "null");
    }

    public static <T> void setSpecification(List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(Specification, json);
        editor.apply();
    }

    public static String getSpecification(){
        return prefs.getString(Specification, "null");
    }

    public static <T> void setTestsList(List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(Tests, json);
        editor.apply();
    }

    public static String getTestsList(){
        return prefs.getString(Tests, "null");
    }


    public static String getEmail() {
        return prefs.getString(userEmail, "Jobpoint");
    }

    public static void setEmail(String email) {
        editor.putString(userEmail, email);
        editor.commit();
    }

    public static String getLoginStatus() {
        return prefs.getString(LoginStatus, "Jobpoint");
    }

    public static void setLoginStatus(String loginStatus) {
        editor.putString(LoginStatus, loginStatus);
        editor.commit();
    }

    public static void logout() {
        editor.clear();
        editor.commit();
    }

    public static void setFirstTime(boolean isFirstTime) {
        editor.putBoolean(IsFirstTime, isFirstTime);
        editor.commit();
    }
    public static boolean getFirstTime() {
        return prefs.getBoolean(IsFirstTime, false);
    }

    public static void setIsLogin(boolean isLogin) {
        editor.putBoolean(ISLOGIN, isLogin);
        editor.commit();
    }
    public static boolean isLogin() {
        return prefs.getBoolean(ISLOGIN, false);
    }

    public static String getUserToken() {
        return prefs.getString(UserToken, "");
    }

    public static void setUserToken(String token) {
        editor.putString(UserToken, token);
        editor.commit();
    }

    public static String getUserName() {
        return prefs.getString(UserName, "");
    }

    public static void setUserName(String name) {
        editor.putString(UserName, name);
        editor.commit();
    }

    public static String getUserEmail() {
        return prefs.getString(UserEmail, "");
    }

    public static void setUserEmail(String email) {
        editor.putString(UserEmail, email);
        editor.commit();
    }

    public static String getUserId() {
        return prefs.getString(UserId, "");
    }

    public static void setUserId(String userId) {
        editor.putString(UserId, userId);
        editor.commit();
    }

    public static String getMobile() {
        return prefs.getString(UserMobile, "");
    }

    public static void setMobile(String mobile) {
        editor.putString(UserMobile, mobile);
        editor.commit();
    }

//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(LocaleManager.setLocale(base));
//    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        LocaleManager.setLocale(this);
//    }
}
