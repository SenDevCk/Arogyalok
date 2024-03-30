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
import com.rkvit.arogyalok.Fragments.AmbDetails;
import com.rkvit.arogyalok.Model.AmbListModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.rkvit.arogyalok.Utils.HelperActivity;

import java.util.HashMap;
import java.util.List;


public class AmbAdaptor extends RecyclerView.Adapter<AmbAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<AmbListModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;

    private String Id;
    private String ItemName;
    private String ItemType;
    private String ImageUrl;
    private String SpclPrice;
    private HashMap<String, String> cityList = new HashMap<String, String>();


    public AmbAdaptor(Activity activity, List<AmbListModel> list, HashMap<String, String> cityList, String viewLayout) {
        this.activity = activity;
        this.list = list;
        this.viewLayout = viewLayout;
        this.cityList = cityList;

    }

    public void setDataList(List<AmbListModel> dataList, HashMap<String, String> cityList) {
        list = dataList;
        this.cityList = cityList;
        notifyDataSetChanged();
    }

    public void setFilter(List<AmbListModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.amb_list_layout, viewGroup, false);
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

        myViewHlder.Title.setText(list.get(i).getName());
        myViewHlder.Address.setText(cityList.get(list.get(i).getCityId()));


        myViewHlder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HelperActivity(activity).makeCall(Constant.ContactNo);
            }
        });

        Glide.with(activity).
                load(Constant.ImgRoot + list.get(i).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ambulance_placeholder)
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .dontTransform()
                .into(myViewHlder.Image);

        myViewHlder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences pref = activity.getSharedPreferences("AmbListDetails", Context.MODE_PRIVATE);
                pref.edit().putString("id", list.get(i).getId())
                        .putString("name", list.get(i).getName())
                        .putString("img", list.get(i).getImage())
//                        .putString("edu", list.get(i).getDrEdu())
//                        .putString("spec", hashSpecList.get(list.get(i).getSpecId()))
//                        .putString("exp", list.get(i).getDrExp())
//                        .putString("fee", list.get(i).getFee())
//                        .putString("time_from", list.get(i).getTimeFrom())
//                        .putString("time_to", list.get(i).getTimeTo())
//                        .putString("work_days", list.get(i).getWorkDay())
                        .putString("address", list.get(i).getFullAddress())
//                        .putString("mobile", list.get(i).getMobile())
//                        .putString("email", list.get(i).getEmailId())
                        .apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new AmbDetails();
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

        private TextView Title, Address, SpclPrice, MainPrice, Offer, contact;
        private ImageView Image, addFav, addedFav;
        private CardView cardview;
        private Button bookNow, callNow;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.image);
            Title = itemView.findViewById(R.id.title);
            Address = itemView.findViewById(R.id.address);
            cardview = itemView.findViewById(R.id.cardview);
            contact = itemView.findViewById(R.id.contact);
            view = itemView;

        }
    }
}