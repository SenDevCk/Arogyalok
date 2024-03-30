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

import com.rkvit.arogyalok.Fragments.BloodHisDetails;
import com.rkvit.arogyalok.Fragments.SchemeHisDetails;
import com.rkvit.arogyalok.Model.BloodHistoryModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;

import java.util.HashMap;
import java.util.List;


public class BloodAptAdaptor extends RecyclerView.Adapter<BloodAptAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<BloodHistoryModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;

    private String Id;
    private String ItemName;
    private String ItemType;
    private String ImageUrl;
    private String SpclPrice;

    private HashMap<String, String> hashSpecList = new HashMap<String, String>();

    public BloodAptAdaptor(Activity activity, List<BloodHistoryModel> list, HashMap<String, String> hashSpecList, String viewLayout) {
        this.activity = activity;
        this.list = list;
        this.viewLayout = viewLayout;
        this.hashSpecList = hashSpecList;
    }

    public void setDataList(List<BloodHistoryModel> dataList, HashMap<String, String> hashSpecList) {
        list = dataList;
        this.hashSpecList = hashSpecList;
        notifyDataSetChanged();

//        Toast.makeText(activity, String.valueOf(list.size()) + " Hotel Found", Toast.LENGTH_SHORT).show();
    }

    public void setFilter(List<BloodHistoryModel> FilteredDataList) {
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

        myViewHlder.Date.setText(list.get(i).getRequestDate());
        myViewHlder.BookingId.setVisibility(View.GONE);

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

                SharedPreferences pref = activity.getSharedPreferences("BloodHisDetails", Context.MODE_PRIVATE);
                pref.edit().putString("id", list.get(i).getId())
                        .putString("donor_id", list.get(i).getDonorid())
                        .putString("name", list.get(i).getPatName())
                        .putString("mobile", list.get(i).getMobileNo())
                        .putString("blood", list.get(i).getPatBlood())
                        .putString("pres", list.get(i).getPrescription())
                        .putString("cityId", list.get(i).getCityId())
                        .putString("secDate", list.get(i).getRequestDate())
                        .putString("pincode", list.get(i).getPinCode())
                        .putString("address", list.get(i).getLocation())
                        .putString("bookingNo", list.get(i).getOrderNo())
                        .putString("status", myViewHlder.Status.getText().toString())
                        .apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new BloodHisDetails();
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

        private TextView Date, BookingId,BookingTxt, Status;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.date);
            BookingId = itemView.findViewById(R.id.booking_id);
            BookingId.setVisibility(View.GONE);
            BookingTxt = itemView.findViewById(R.id.booking_txt);
            BookingTxt.setVisibility(View.GONE);
            Status = itemView.findViewById(R.id.status);
            view = itemView;

        }
    }
}