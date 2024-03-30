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
import com.rkvit.arogyalok.Fragments.DocDetails;
import com.rkvit.arogyalok.Model.DocModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;

import java.util.HashMap;
import java.util.List;


public class DocAdaptor extends RecyclerView.Adapter<DocAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<DocModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;

    private String Id;
    private String ItemName;
    private String ItemType;
    private String ImageUrl;
    private String SpclPrice;

    private HashMap<String, String> hashSpecList = new HashMap<String, String>();

    public DocAdaptor(Activity activity, List<DocModel> list, HashMap<String, String> hashSpecList, String viewLayout) {
        this.activity = activity;
        this.list = list;
        this.viewLayout = viewLayout;
        this.hashSpecList = hashSpecList;
    }

    public void setDataList(List<DocModel> dataList, HashMap<String, String> hashSpecList) {
        list = dataList;
        this.hashSpecList = hashSpecList;
        notifyDataSetChanged();
    }

    public void setFilter(List<DocModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doc_list_horizontl, viewGroup, false);
        mv = new MyViewHlder(view);
        return mv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        myViewHlder.Title.setText(list.get(i).getDocName());
        myViewHlder.Qual.setText(list.get(i).getDrEdu());
        myViewHlder.Spec.setText(list.get(i).getSpecName());
        myViewHlder.Exp.setText(list.get(i).getDrExp() + " exp.");
        myViewHlder.City.setText(list.get(i).getCityName());

        Glide.with(activity).
                load(Constant.ImgRoot + list.get(i).getImage())
                .placeholder(R.drawable.doctor_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .dontTransform()
                .into(myViewHlder.Image);

        myViewHlder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = activity.getSharedPreferences("DocDetails", Context.MODE_PRIVATE);
                pref.edit().putString("id", list.get(i).getId())
                        .putString("name", list.get(i).getDocName())
                        .putString("img", list.get(i).getImage())
                        .putString("edu", list.get(i).getDrEdu())
                        .putString("spec", hashSpecList.get(list.get(i).getSpecId()))
                        .putString("exp", list.get(i).getDrExp())
                        .putString("fee", list.get(i).getFee())
                        .putString("time_from", list.get(i).getTimeFrom())
                        .putString("time_to", list.get(i).getTimeTo())
                        .putString("work_days", list.get(i).getWorkDay())
                        .putString("address", list.get(i).getFullAddress())
                        .putString("mobile", list.get(i).getMobile())
                        .putString("email", list.get(i).getEmail())
                        .apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new DocDetails();
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

        private TextView Title, Qual, Spec, Exp, City;
        private ImageView Image, addFav, addedFav;
        private CardView cardview;
        private Button bookNow, callNow;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.image);
            Title = itemView.findViewById(R.id.title);
            Qual = itemView.findViewById(R.id.qual);
            Spec = itemView.findViewById(R.id.spec);
            Exp = itemView.findViewById(R.id.exp);
            City = itemView.findViewById(R.id.city);
            view = itemView;

        }
    }
}