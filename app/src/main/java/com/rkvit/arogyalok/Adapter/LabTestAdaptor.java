package com.rkvit.arogyalok.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rkvit.arogyalok.Fragments.MedicineFragment;
import com.rkvit.arogyalok.LocalDB.CartDatabase;
import com.rkvit.arogyalok.Model.TestDetailsModel;
import com.rkvit.arogyalok.MyApplication;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class LabTestAdaptor extends RecyclerView.Adapter<LabTestAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<TestDetailsModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;

    private String Id;
    private String ItemName;
    private String ItemType;
    private String ImageUrl;
    private String SpclPrice;
    private HashMap<String, String> cityList = new HashMap<String, String>();
    private int itemPosition;
    private CartDatabase db;
    private MedicineFragment fragment;
    private HashMap<String, String> hashTestList = new HashMap<String, String>();
    private BottomSheetDialog dialog;
    private EditText patientName, age, recommended, userCity, pickupPoint, dropPoint, aptDate, addrress, patientMobile;
    private Spinner bloodGroup;
    private AutoCompleteTextView pincode, stateDropdown, cityDropdown;
    private String UserName, UserMobile, UserAddress, Pincode, AptDate;
    private boolean valid;
    private Spinner paymentMode;


    public LabTestAdaptor(Activity activity, List<TestDetailsModel> list, HashMap<String, String> hashTestList) {
        this.activity = activity;
        this.list = list;
        this.fragment = fragment;
        this.hashTestList = hashTestList;
    }

    public void setDataList(List<TestDetailsModel> dataList, HashMap<String, String> hashTestList) {
        list = dataList;
        this.hashTestList = hashTestList;
        notifyDataSetChanged();
    }

    public void setFilter(List<TestDetailsModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lab_test_list_layout, viewGroup, false);
        mv = new MyViewHlder(view);

        return mv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        myViewHlder.Title.setText(list.get(i).getName());
        myViewHlder.Mrp.setText("\u20B9" + list.get(i).getTestFees());
        myViewHlder.TestMand.setText(list.get(i).getMandTest());
        myViewHlder.TestReport.setText(list.get(i).getTestReport());
        myViewHlder.TestPara.setText(list.get(i).getCoverdParamerter() +" Parameter(s) covered");

        Glide.with(activity).
                load(Constant.ImgRoot + list.get(i).getIcon())
                .placeholder(R.drawable.lab_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .dontTransform()
                .into(myViewHlder.img);
    }


    private void openBookingForm() {

        dialog = new BottomSheetDialog(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.lab_scheme_apt_layout, null);
        dialog.setContentView(dialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();

        initControls(dialogView);

    }


    private void initControls(View dialogView) {

        patientName = dialogView.findViewById(R.id.name);
        patientName.setText(MyApplication.getUserName());

        age = dialogView.findViewById(R.id.age);
        patientMobile = dialogView.findViewById(R.id.mobile);
        patientMobile.setText(MyApplication.getMobile());
        recommended = dialogView.findViewById(R.id.recommended);
        bloodGroup = dialogView.findViewById(R.id.blood_group);
        paymentMode = dialogView.findViewById(R.id.pay_option);

        pincode = dialogView.findViewById(R.id.pincode);

        aptDate = dialogView.findViewById(R.id.date);
        addrress = dialogView.findViewById(R.id.address);


        aptDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar myCalendar = Calendar.getInstance();

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String myFormat = "dd/MM/yyyy"; //In which you need put here  //game_date=2020/12/12
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                        aptDate.setText(sdf.format(myCalendar.getTime()));

                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                //Max Date
                Calendar maxCal = Calendar.getInstance();
                maxCal.set(myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH) + 15);
                datePickerDialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());

                //Min Date
                Calendar minCal = Calendar.getInstance();
                minCal.set(myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(minCal.getTimeInMillis());

                datePickerDialog.show();


            }
        });

        Button bookNow = dialogView.findViewById(R.id.book_now);
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (validData())
//                    sendQuery();
            }
        });
    }

    private boolean validData() {

        UserName = patientName.getText().toString();
        UserMobile = patientMobile.getText().toString();
        AptDate = aptDate.getText().toString();
        Pincode = pincode.getText().toString();
        UserAddress = addrress.getText().toString();

        valid = true;

        if (UserName.isEmpty()) {
            patientName.setError("required");
            patientName.requestFocus();
            valid = false;
        }

        if (UserMobile.isEmpty()) {
            patientMobile.setError("required");
            patientMobile.requestFocus();
            valid = false;
        }

        if (Pincode.isEmpty()) {
            pincode.setError("required");
            pincode.requestFocus();
            valid = false;
        }

        if (UserAddress.isEmpty()) {
            addrress.setError("required");
            addrress.requestFocus();
            valid = false;
        }

        if (AptDate.isEmpty()) {
            aptDate.setError("required");
            aptDate.requestFocus();
            valid = false;
        }
        return valid;
    }

//    private void sendQuery() {
//
//        ProgressDialog progress = new ProgressDialog(activity);
//        progress.setMessage("Sending... Please wait");
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();
//
//
//        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
//        apiInterface.labSchemBooking(list.get(itemPosition).getId(), list.get(itemPosition).getLabId(), UserName, UserMobile, "", aptDate.getText().toString(),
//                addrress.getText().toString(), pincode.getText().toString(), bloodGroup.getSelectedItem().toString(), list.get(itemPosition).getSkimValidity(), MyApplication.getUserId())
//                .enqueue(new Callback<BookingSchemModel>() {
//                    @Override
//                    public void onResponse(Call<BookingSchemModel> call, Response<BookingSchemModel> response) {
//                        progress.cancel();
//
//                        if (response.isSuccessful()) {
//
//                            progress.cancel();
//                            dialog.cancel();
//
//                            if (paymentMode.getSelectedItem().toString().equalsIgnoreCase("Online")) {
//                                payOnline();
//                            } else
//                            Toast.makeText(activity, "Booking Done.", Toast.LENGTH_LONG).show();
//
//                        } else {
//                            dialog.cancel();
//                            progress.cancel();
//                            Toast.makeText(activity, "response Unsuccessful", Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BookingSchemModel> call, Throwable t) {
//                        progress.cancel();
//                        dialog.cancel();
//                        Log.e("error ", t.getMessage());
//
//                    }
//                });
//    }
//
//    public void payOnline() {
//
//        double amt = Double.parseDouble(list.get(itemPosition).getFinalPrice()) * 100;
//
//        final Checkout checkout = new Checkout();
//
//
//        try {
//            JSONObject options = new JSONObject();
//            options.put("name", "Razorpay Corp");
//            options.put("description", "Payment");
//            options.put("send_sms_hash", true);
//            options.put("allow_rotation", true);
////            options.put("order_id", "646646");//from response of step 3.
//            options.put("theme.color", R.color.colorPrimaryDark);
//            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("currency", "INR");
//            options.put("amount", amt);
//
//            JSONObject preFill = new JSONObject();
//            preFill.put("email", "pmm.etouchindia@gmail.com");
//            preFill.put("contact", Constant.PaymentContactNo);
//            preFill.put("enabled", true);
//            preFill.put("max_count", 10);
//
//            options.put("retry", preFill);
//
//            checkout.open(activity, options);
//        } catch (Exception e) {
//            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class MyViewHlder extends RecyclerView.ViewHolder {

        private TextView Title, Mrp, TestMand, TestReport, TestPara, BookNow, testName, testValidity;
        private ImageView img;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            Mrp = itemView.findViewById(R.id.mrp);
            TestMand = itemView.findViewById(R.id.test_mand);
            TestReport = itemView.findViewById(R.id.test_report);
            TestPara = itemView.findViewById(R.id.test_para);
            BookNow = itemView.findViewById(R.id.book_now);
            img = itemView.findViewById(R.id.img);
            testName = itemView.findViewById(R.id.test_name);
            testValidity = itemView.findViewById(R.id.schme_validity);
            view = itemView;

        }
    }
}