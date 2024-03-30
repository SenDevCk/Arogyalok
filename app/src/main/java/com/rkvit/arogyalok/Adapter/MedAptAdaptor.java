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
import com.rkvit.arogyalok.Fragments.MedHisDetails;
import com.rkvit.arogyalok.Model.MedHistoryModel;
import com.rkvit.arogyalok.Model.MedHistoryModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;

import java.util.HashMap;
import java.util.List;


public class MedAptAdaptor extends RecyclerView.Adapter<MedAptAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<MedHistoryModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;

    private String Id;
    private String ItemName;
    private String ItemType;
    private String ImageUrl;
    private String SpclPrice;

    private HashMap<String, String> hashSpecList = new HashMap<String, String>();

    public MedAptAdaptor(Activity activity, List<MedHistoryModel> list, HashMap<String, String> hashSpecList, String viewLayout) {
        this.activity = activity;
        this.list = list;
        this.viewLayout = viewLayout;
        this.hashSpecList = hashSpecList;
    }

    public void setDataList(List<MedHistoryModel> dataList, HashMap<String, String> hashSpecList) {
        list = dataList;
        this.hashSpecList = hashSpecList;
        notifyDataSetChanged();

//        Toast.makeText(activity, String.valueOf(list.size()) + " Hotel Found", Toast.LENGTH_SHORT).show();
    }

    public void setFilter(List<MedHistoryModel> FilteredDataList) {
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

        myViewHlder.Date.setText(list.get(i).getBookingDate());
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
                SharedPreferences pref = activity.getSharedPreferences("MedHisDetails", Context.MODE_PRIVATE);
                pref.edit().putString("id", list.get(i).getId())
                        .putString("state_id", list.get(i).getStateId())
                        .putString("city_id", list.get(i).getCityId())
                        .putString("name", list.get(i).getCustName())
                        .putString("mobile", list.get(i).getMobNo())
                        .putString("pres", list.get(i).getPrescription())
                        .putString("blood", list.get(i).getBloodGroup())
                        .putString("secDate", list.get(i).getBookingDate())
                        .putString("proId", list.get(i).getProId())
                        .putString("qty", list.get(i).getQty())
                        .putString("pincode", list.get(i).getPinCode())
                        .putString("orderId", list.get(i).getOrderId())
                        .putString("address", list.get(i).getLocation())
                        .putString("bookingNo", list.get(i).getBookingNo())
                        .putString("paymentType", list.get(i).getPaymentOption())
                        .putString("totalAmt", list.get(i).getTotalmedBill())
                        .putString("status", myViewHlder.Status.getText().toString())
                        .apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new MedHisDetails();
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

        private TextView Date, BookingId, Status, BookingTxt;
        private View view;

        @SuppressLint("SetTextI18n")
        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.date);
            BookingId = itemView.findViewById(R.id.booking_id);
            BookingTxt = itemView.findViewById(R.id.booking_txt);
            BookingTxt.setText("Order Id");
            Status = itemView.findViewById(R.id.status);
            view = itemView;

        }
    }
}