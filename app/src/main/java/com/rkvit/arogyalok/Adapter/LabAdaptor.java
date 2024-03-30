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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkvit.arogyalok.Fragments.LabDetails;
import com.rkvit.arogyalok.Model.LabModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;

import java.util.HashMap;
import java.util.List;


public class LabAdaptor extends RecyclerView.Adapter<LabAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<LabModel> list;
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

    public LabAdaptor(Activity activity, List<LabModel> list, HashMap<String, String> cityList, String viewLayout) {
        this.activity = activity;
        this.list = list;
        this.viewLayout = viewLayout;
        this.cityList = cityList;
    }

    public void setDataList(List<LabModel> dataList, HashMap<String, String> cityList) {
        list = dataList;
        this.cityList = cityList;
        notifyDataSetChanged();
    }

    public void setFilter(List<LabModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

//        if (viewLayout.contentEquals("Horizontal")) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hospital_list_layout, viewGroup, false);
//        }
//        else {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_list__vertical_layout, viewGroup, false);
//        }
        mv = new MyViewHlder(view);

        return mv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        myViewHlder.Title.setText(list.get(i).getLabName());
        myViewHlder.Address.setText(list.get(i).getCityName());

        Glide.with(activity).
                load(Constant.ImgRoot + list.get(i).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.lab_placeholder)
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .dontTransform()
                .into(myViewHlder.Image);

        myViewHlder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = activity.getSharedPreferences("LabDetails", Context.MODE_PRIVATE);
                pref.edit().putString("name", list.get(i).getLabName())
                        .putString("id", list.get(i).getId())
                        .putString("img", list.get(i).getImage())
                        .putString("time_from", list.get(i).getTimeFrom())
                        .putString("time_to", list.get(i).getTimeTo())
                        .putString("work_days", list.get(i).getWorkDay())
                        .putString("address", list.get(i).getFullAddress())
                        .putString("mobile", list.get(i).getMobile())
                        .putString("email", list.get(i).getEmailId())
                        .apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new LabDetails();
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

        private TextView Title, Address, SpclPrice, MainPrice, Offer;
        private ImageView Image, addFav, addedFav;
        private CardView cardview;
        private Button bookNow, callNow;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.image);
            Title = itemView.findViewById(R.id.title);
            Address = itemView.findViewById(R.id.address);
//            bookNow = itemView.findViewById(R.id.book_now);
//            callNow = itemView.findViewById(R.id.call_now);
            cardview = itemView.findViewById(R.id.cardview);
            SpclPrice = itemView.findViewById(R.id.spcl_price);
            MainPrice = itemView.findViewById(R.id.main_price);
            Offer = itemView.findViewById(R.id.offer);

            addFav = itemView.findViewById(R.id.add_fav);
            addedFav = itemView.findViewById(R.id.added_fav);

            view = itemView;

        }
    }
}