package com.rkvit.arogyalok.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.rkvit.arogyalok.Fragments.AmbHisDetails;
import com.rkvit.arogyalok.Fragments.DocHisDetails;
import com.rkvit.arogyalok.Model.AmbHistoryModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;

import java.util.HashMap;
import java.util.List;


public class AmbAptAdaptor extends RecyclerView.Adapter<AmbAptAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<AmbHistoryModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;

    private String Id;
    private String ItemName;
    private String ItemType;
    private String ImageUrl;
    private String SpclPrice;

    private HashMap<String, String> hashSpecList = new HashMap<String, String>();

    public AmbAptAdaptor(Activity activity, List<AmbHistoryModel> list, HashMap<String, String> hashSpecList, String viewLayout) {
        this.activity = activity;
        this.list = list;
        this.viewLayout = viewLayout;
        this.hashSpecList = hashSpecList;
    }

    public void setDataList(List<AmbHistoryModel> dataList, HashMap<String, String> hashSpecList) {
        list = dataList;
        this.hashSpecList = hashSpecList;
        notifyDataSetChanged();

//        Toast.makeText(activity, String.valueOf(list.size()) + " Hotel Found", Toast.LENGTH_SHORT).show();
    }

    public void setFilter(List<AmbHistoryModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();

        Toast.makeText(activity, String.valueOf(list.size()) + " Hotel Found", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.apt_history_list, viewGroup, false);
        mv = new MyViewHlder(view);

        return mv;

    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        myViewHlder.Date.setText(list.get(i).getDob());
        myViewHlder.BookingId.setText(list.get(i).getBookingNo());

        switch (list.get(i).getStatus()) {
            case "0":
                myViewHlder.Status.setText(Constant.StsPending);
                myViewHlder.Status.setTextColor(R.color.pending);
                break;
            case "1":
                myViewHlder.Status.setText(Constant.StsProcess);
                myViewHlder.Status.setTextColor(R.color.Process);
                break;
            case "2":
                myViewHlder.Status.setText(Constant.StsCompleted);
                myViewHlder.Status.setTextColor(R.color.Completed);
                break;
            case "3":
                myViewHlder.Status.setText(Constant.StsCancel);
                myViewHlder.Status.setTextColor(R.color.cancelled);
                break;
        }

        myViewHlder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = activity.getSharedPreferences("AmbHisDetails", Context.MODE_PRIVATE);
                pref.edit().putString("id", list.get(i).getId())
                        .putString("amb_id", list.get(i).getAmbId())
                        .putString("agency_id", list.get(i).getAgencyId())
                        .putString("name", list.get(i).getPatName())
                        .putString("mobile", list.get(i).getMobNo())
                        .putString("email", list.get(i).getEmailId())
                        .putString("pres", list.get(i).getPrescription())
                        .putString("blood", list.get(i).getPatBlood())
                        .putString("secDate", list.get(i).getDob())
                        .putString("arrival", list.get(i).getArrival())
                        .putString("destination", list.get(i).getDestination())
                        .putString("pincode", list.get(i).getPinCode())
                        .putString("condition", list.get(i).getPatientcodition())
                        .putString("address", list.get(i).getFullAddress())
                        .putString("bookingNo", list.get(i).getBookingNo())
                        .putString("paymentType", list.get(i).getPaymentOption())
                        .putString("status", myViewHlder.Status.getText().toString())
                        .apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new AmbHisDetails();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrame, myFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
//        if (viewLayout.contentEquals("Horizontal")) {
//            if (list.size() > limit) {
//                return limit;
//            } else {
//                return list.size();
//            }
//        } else
            return list.size();
    }

    class MyViewHlder extends RecyclerView.ViewHolder {

        private TextView Date, BookingId, Status;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.date);
            BookingId = itemView.findViewById(R.id.booking_id);
            Status = itemView.findViewById(R.id.status);
            view = itemView;

        }
    }
}