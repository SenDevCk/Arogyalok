package com.rkvit.arogyalok.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rkvit.arogyalok.R;

import java.util.List;


public class DaysAdaptor extends RecyclerView.Adapter<DaysAdaptor.MyViewHlder> {
    private Activity activity;
    private List<DataModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;

    public DaysAdaptor(Activity activity, List<DataModel> list) {
        this.activity = activity;
        this.list = list;
    }

    public void setDataList(List<DataModel> dataList) {
        list = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.days_list_layout, viewGroup, false);
        mv = new MyViewHlder(view);

        return mv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        myViewHlder.Title.setText(list.get(i).getDay());

    }

    @Override
    public int getItemCount() {

            return list.size();
    }

    class MyViewHlder extends RecyclerView.ViewHolder {

        private TextView Title, Address, SpclPrice, MainPrice, Offer;
        private ImageView Image, addFav, addedFav;
        private CardView cardview;
        private Button bookNow, callNow;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.image);

        }
    }
}