package com.rkvit.arogyalok.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.rkvit.arogyalok.Activity.AmbBookingActivity;
import com.rkvit.arogyalok.Activity.BldGrpBookingActivity;
import com.rkvit.arogyalok.Fragments.AmbDetails;
import com.rkvit.arogyalok.Model.BloodDetailsModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.rkvit.arogyalok.Utils.HelperActivity;

import java.util.HashMap;
import java.util.List;


public class BldListAdaptor extends RecyclerView.Adapter<BldListAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<BloodDetailsModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;
//    FavDatabase db;

    private String Id;
    private String ItemName;
    private String ItemType;
    private String ImageUrl;
    private String SpclPrice;
    private HashMap<String, String> cityList = new HashMap<String, String>();
    private int itemPostion;


    public BldListAdaptor(Activity activity, List<BloodDetailsModel> list) {
        this.activity = activity;
        this.list = list;

    }

    public void setDataList(List<BloodDetailsModel> dataList) {
        list = dataList;
//        this.cityList = cityList;
        notifyDataSetChanged();
    }

    public void setFilter(List<BloodDetailsModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bldt_list_layout, viewGroup, false);
        mv = new MyViewHlder(view);

        return mv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        myViewHlder.BloodGrp.setText(list.get(i).getBloodGroup());

        myViewHlder.title.setText(list.get(i).getName());

        myViewHlder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new  HelperActivity(activity).makeCall(Constant.ContactNo);
            }
        });

        myViewHlder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itemPostion = i;
//                bldRqstForm();

                Intent intent = new Intent(activity, BldGrpBookingActivity.class);
                intent.putExtra("Id", list.get(i).getId());
                activity.startActivity(intent);

            }
        });

    }

    private void bldRqstForm() {

    }

    @Override
    public int getItemCount() {

            return list.size();
    }

    class MyViewHlder extends RecyclerView.ViewHolder {

        private TextView BloodGrp, title, call, book;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            BloodGrp = itemView.findViewById(R.id.blood_grp);
            title = itemView.findViewById(R.id.title);
            call = itemView.findViewById(R.id.contact);
            book = itemView.findViewById(R.id.book);
            view = itemView;

        }
    }
}