package com.rkvit.arogyalok.RetrofitUtil;


import com.rkvit.arogyalok.Model.AmbCostModel;
import com.rkvit.arogyalok.Model.AmbHistoryModel;
import com.rkvit.arogyalok.Model.AmbListModel;
import com.rkvit.arogyalok.Model.BldTypeModel;
import com.rkvit.arogyalok.Model.BloodDetailsModel;
import com.rkvit.arogyalok.Model.BloodHistoryModel;
import com.rkvit.arogyalok.Model.BookingMsgModel;
import com.rkvit.arogyalok.Model.BookingSchemModel;
import com.rkvit.arogyalok.Model.CityModel;
import com.rkvit.arogyalok.Model.DocHistoryModel;
import com.rkvit.arogyalok.Model.DocModel;
import com.rkvit.arogyalok.Model.DocSchdModel;
import com.rkvit.arogyalok.Model.ImgsModel;
import com.rkvit.arogyalok.Model.LabHistoryModel;
import com.rkvit.arogyalok.Model.LabModel;
import com.rkvit.arogyalok.Model.LabSchemModel;
import com.rkvit.arogyalok.Model.LabTestModel;
import com.rkvit.arogyalok.Model.LoginModel;
import com.rkvit.arogyalok.Model.MedCatModel;
import com.rkvit.arogyalok.Model.MedHistoryModel;
import com.rkvit.arogyalok.Model.MedListModel;
import com.rkvit.arogyalok.Model.MedMarktModel;
import com.rkvit.arogyalok.Model.MedPackModel;
import com.rkvit.arogyalok.Model.MedSubcatModel;
import com.rkvit.arogyalok.Model.OtpModel;
import com.rkvit.arogyalok.Model.SchdDetailsModel;
import com.rkvit.arogyalok.Model.SchemeHistoryModel;
import com.rkvit.arogyalok.Model.SignupModel;
import com.rkvit.arogyalok.Model.SpecificationModel;
import com.rkvit.arogyalok.Model.StateModel;
import com.rkvit.arogyalok.Model.TestDetailsModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST(Constant.Registrion)
    Call<SignupModel> userSignup(@Field("name") String name,
                                 @Field("mobile") String mobile,
                                 @Field("password") String password,
                                 @Field("city_id") String city_id,
                                 @Field("state_id") String state_id,
                                 @Field("pin_code") String pincode,
                                 @Field("blood_group") String blood_group);

    @FormUrlEncoded
    @POST(Constant.LabSchemBook)
    Call<BookingSchemModel> labSchemBooking(@Field("sc_id") String sc_id,
                                            @Field("lab_id") String lab_id,
                                            @Field("full_name") String full_name,
                                            @Field("mob_no") String mob_no,
                                            @Field("email_id") String email_id,
                                            @Field("dob") String dob,
                                            @Field("full_address") String full_address,
                                            @Field("pin_code") String pincode,
                                            @Field("pat_blood") String pat_blood,
                                            @Field("Validity") String Validity,
                                            @Field("cuid") String cuid);

    @FormUrlEncoded
    @POST(Constant.Login)
    Call<LoginModel> userLogin(@Field("mobile") String mobile,
                               @Field("password") String password);
    @FormUrlEncoded
    @POST(Constant.OtpVerify)
    Call<BookingMsgModel> otpVerify(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(Constant.ResendOtp)
    Call<OtpModel> resendOtp(@Field("mobile") String mobile);

//    "status": "Invalid Login"

    @FormUrlEncoded
    @POST(Constant.ChangePassword)
    Call<BookingMsgModel> changePassword(@Field("mobile") String mobile,
                                         @Field("password") String password);
    @FormUrlEncoded
    @POST(Constant.GetOtp)
    Call<BookingMsgModel> getOtp(@Field("mobile") String mobil);

    @FormUrlEncoded
    @POST(Constant.MedSearch)
    Call<List<MedListModel>> MedSearch(@Field("keys") String keys,
                                       @Field("cuid") String cuid);

    @FormUrlEncoded
    @POST(Constant.LabSearch)
    Call<List<LabModel>> LabSearch(@Field("keys") String keys,
                                   @Field("cuid") String cuid);

    @FormUrlEncoded
    @POST(Constant.DocSearch)
    Call<List<DocModel>> DocSearch(@Field("keys") String keys,
                                   @Field("cuid") String cuid);

    @FormUrlEncoded
    @POST(Constant.AmbSearch)
    Call<List<AmbListModel>> AmbSearch(@Field("keys") String keys,
                                       @Field("cuid") String cuid);

    @Multipart
    @POST(Constant.UpldPrescription)
    Call<BookingMsgModel> upldPriscription(@Part("user_id") String user_id,
                                           @Part MultipartBody.Part image);


    @GET(Constant.Slider)
    Call<List<ImgsModel>> getSlider();

    @GET(Constant.BldType)
    Call<List<BldTypeModel>> getBldType();

    @GET(Constant.BldList)
    Call<List<BloodDetailsModel>> getBldList();

    @Multipart
    @POST(Constant.BldList)
    Call<BookingMsgModel> sendBldRequest(@Part("donorid") String donorid,
                                         @Part("pat_name") String patientName,
                                         @Part("mobile_no") String mobileNo,
                                         @Part("pat_blood") String pat_blood,
                                         @Part("location") String location,
                                         @Part("alatitute") String alatitute,
                                         @Part("alongitute") String alongitute,
                                         @Part("state_id") String state_id,
                                         @Part("city_id") String city_id,
                                         @Part("pin_code") String pin_code,
                                         @Part("cuid") String cuid,
                                         @Part MultipartBody.Part image);

    @Multipart
    @POST(Constant.OrderMed)
    Call<BookingMsgModel> orderMed(@Part("cust_name") String patientName,
                                   @Part("mob_no") String mobileNo,
                                   @Part("blood_group") String pat_blood,
                                   @Part("location") String location,
                                   @Part("alatitute") String alatitute,
                                   @Part("alongitute") String alongitute,
                                   @Part("state_id") String state_id,
                                   @Part("city_id") String city_id,
                                   @Part("pin_code") String pin_code,
                                   @Part("fulladdress") String fulladdress,
                                   @Part("pro_id") String productId,
                                   @Part("qty") String quantity,
                                   @Part("extra") String extra,
                                   @Part("cuid") String cuid,
                                   @Part MultipartBody.Part image);

    @GET(Constant.Doctor)
    Call<List<DocModel>> getDoc();

    @GET(Constant.Lab)
    Call<List<LabModel>> getLab();

    @GET(Constant.LabSchem)
    Call<List<LabSchemModel>> getLabSchem();

    @GET(Constant.Ambulance)
    Call<List<AmbListModel>> getAmb();

    @GET(Constant.AmbulanceCost)
    Call<List<AmbCostModel>> getAmbCost();

    @GET(Constant.Specilization)
    Call<List<SpecificationModel>> getSpecilization();

    @GET(Constant.MedicineCat)
    Call<List<MedCatModel>> getMedCat();

    @GET(Constant.MedicineSubCat)
    Call<List<MedSubcatModel>> getMedSubCat();

    @GET(Constant.MedicineList)
    Call<List<MedListModel>> getMedList();

    @GET()
    Call<List<MedPackModel>> getMedPack(@Url String url);

    @GET()
    Call<MedListModel> getMedDetaild(@Url String url);

    @GET()
    Call<List<MedMarktModel>> getMedMarkt(@Url String url);

    @FormUrlEncoded
    @POST(Constant.DocCancel)
    Call<BookingMsgModel> cancelDoc(@Field("orderid") String orderid,
                                    @Field("orderno") String orderno,
                                    @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST(Constant.AmbCancel)
    Call<BookingMsgModel> cancelAmb(@Field("orderid") String orderid,
                                    @Field("orderno") String orderno,
                                    @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST(Constant.MedCancel)
    Call<BookingMsgModel> cancelMedOrder(@Field("orderid") String orderid,
                                         @Field("orderno") String orderno,
                                         @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST(Constant.MedRefund)
    Call<BookingMsgModel> refundMed(@Field("orderid") String orderid,
                                    @Field("pro_id") String pro_id,
                                    @Field("orderno") String orderno,
                                    @Field("cust_id") String cust_id);

    @FormUrlEncoded
    @POST(Constant.LabCancel)
    Call<BookingMsgModel> cancelLab(@Field("orderid") String orderid,
                                    @Field("orderno") String orderno,
                                    @Field("cust_id") String cust_id);

    @GET(Constant.City)
    Call<List<CityModel>> getCity();

    @GET(Constant.State)
    Call<List<StateModel>> getState();

//    @GET(Constant.AllTest)
//    Call<List<TestMasterModel>> getAllTest();

    @GET()
    Call<LabModel> getLabDetails(@Url String url);

    @GET()
    Call<List<LabTestModel>> getLabTest(@Url String url);

    @GET()
    Call<List<TestDetailsModel>> getLabTestDetails(@Url String url);

    @GET()
    Call<List<DocSchdModel>> getDocShed(@Url String url);

    @GET()
    Call<List<SchdDetailsModel>> getShedDetails(@Url String url);

    @GET()
    Call<List<DocHistoryModel>> getDocApt(@Url String url);

    @GET()
    Call<List<DocModel>> getDocDetails(@Url String url);

    @GET()
    Call<List<AmbHistoryModel>> getAmbHis(@Url String url);

    @GET()
    Call<List<LabHistoryModel>> getLabHis(@Url String url);

    @GET()
    Call<List<BloodHistoryModel>> getBloodHis(@Url String url);

    @GET()
    Call<List<MedHistoryModel>> getMedHis(@Url String url);

    @GET()
    Call<List<SchemeHistoryModel>> getSchemeHis(@Url String url);

//    @GET()
//    Call<List<SchemeHistoryModel>> getSchemeHis(@Url String url);


    @Multipart
    @POST(Constant.AmbBook)
    Call<BookingMsgModel> ambBooking(@Part("amb_id") String amb_id,
                                     @Part("agency_id") String agency_id,
                                     @Part("pat_name") String patientName,
                                     @Part("mob_no") String mobileNo,
                                     @Part("email_id") String emilId,
                                     @Part("pat_blood") String pat_blood,
                                     @Part("patientcodition") String patientcodition,
                                     @Part MultipartBody.Part image,
                                     @Part("dob") String dob,
                                     @Part("arrival") String arrival,
                                     @Part("alatitute") String alatitute,
                                     @Part("alongitute") String alongitute,
                                     @Part("destination") String destination,
                                     @Part("full_address") String full_address,
                                     @Part("pin_code") String pin_code,
                                     @Part("cuid") String cuid);

    @Multipart
    @POST(Constant.LabBook)
    Call<BookingMsgModel> labBooking(@Part("lab_id") String lab_id,
                                     @Part("pat_name") String patientName,
                                     @Part("mob_no") String mobileNo,
                                     @Part("pat_blood") String pat_blood,
                                     @Part("test_id") String test_id,
                                     @Part MultipartBody.Part image,
                                     @Part("dop") String dob,
                                     @Part("recom_by") String recom_by,
                                     @Part("full_address") String full_address,
                                     @Part("cuid") String cuid);

    @Multipart
    @POST(Constant.DocAppoint)
    Call<BookingMsgModel> docpAppoint(@Part("doc_id") String doc_id,
                                      @Part("pat_name") String patientName,
                                      @Part("mobile") String mobileNo,
                                      @Part("blood_group") String pat_blood,
                                      @Part("city_id") String city_id,
                                      @Part MultipartBody.Part image,
                                      @Part("sec_date") String sec_date,
                                      @Part("sec_time") String sec_time,
                                      @Part("pin_code") String pin_code,
                                      @Part("pat_condition") String pat_condition,
                                      @Part("full_address") String full_address,
                                      @Part("cuid") String cuid);

//    @GET()
//    Call<List<DocHistModel>> docHistory(@Url String url);

//    @GET()
//    Call<List<HospHisModel>> hospHistory(@Url String url);

//    @GET()
//    Call<DocModel> docById(@Url String url);

//    @GET()
//    Call<HospitalModel> hospById(@Url String url);

//    @FormUrlEncoded
//    @POST(Constant.HospitalAppoint)
//    Call<BookingMsgModel> hospAppoint(@Field("hosp_id") String hosp_id,
//                                      @Field("pat_name") String pat_name,
//                                      @Field("mob_no") String mob_no,
//                                      @Field("email_id") String email_id,
//                                      @Field("city") String city,
//                                      @Field("bookingdate") String bookingdate,
//                                      @Field("specilization") String specilization,
//                                      @Field("enquiry") String enquiry,
//                                      @Field("cuid") String cuid);

}