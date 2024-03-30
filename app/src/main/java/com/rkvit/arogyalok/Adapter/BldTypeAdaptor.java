package com.rkvit.arogyalok.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkvit.arogyalok.Fragments.AmbDetails;
import com.rkvit.arogyalok.Fragments.BloodGrpList;
import com.rkvit.arogyalok.Model.BldTypeModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.rkvit.arogyalok.Utils.HelperActivity;

import java.util.HashMap;
import java.util.List;


public class BldTypeAdaptor extends RecyclerView.Adapter<BldTypeAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<BldTypeModel> list;
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


    public BldTypeAdaptor(Activity activity, List<BldTypeModel> list) {
        this.activity = activity;
        this.list = list;
//        this.viewLayout = viewLayout;
//        this.cityList = cityList;

    }

    public void setDataList(List<BldTypeModel> dataList) {
        list = dataList;
//        this.cityList = cityList;
        notifyDataSetChanged();
    }

    public void setFilter(List<BldTypeModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bldtype_list_layout, viewGroup, false);
        mv = new MyViewHlder(view);

        return mv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        myViewHlder.BloodGrp.setText(list.get(i).getName());

        myViewHlder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = activity.getSharedPreferences("BldDetails", Context.MODE_PRIVATE);
                pref.edit().putString("id", list.get(i).getId())
                        .putString("bld_grp", list.get(i).getName())
                        .apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new BloodGrpList();
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

            return list.size();
    }

    class MyViewHlder extends RecyclerView.ViewHolder {

        private TextView BloodGrp;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            BloodGrp = itemView.findViewById(R.id.blood_grp);
            view = itemView;

        }
    }
}