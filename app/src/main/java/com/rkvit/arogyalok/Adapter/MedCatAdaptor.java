//package com.rkvit.arogyalok.Adapter;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.rkvit.arogyalok.Fragments.BloodGrpList;
//import com.rkvit.arogyalok.Fragments.DocDetails;
//import com.rkvit.arogyalok.Model.MedCatModel;
//import com.rkvit.arogyalok.R;
//import com.rkvit.arogyalok.RetrofitUtil.Constant;
//
//import java.util.HashMap;
//import java.util.List;
//
//
//public class MedCatAdaptor extends RecyclerView.Adapter<MedCatAdaptor.MyViewHlder> {
//    private final int limit = 5;
//    private Activity activity;
//    private List<MedCatModel> list;
//    private View view;
//    private MyViewHlder mv;
//    private String viewLayout;
////    FavDatabase db;
//
//    private String Id;
//    private String ItemName;
//    private String ItemType;
//    private String ImageUrl;
//    private String SpclPrice;
//    private HashMap<String, String> cityList = new HashMap<String, String>();
//
//
//    public MedCatAdaptor(Activity activity, List<MedCatModel> list) {
//        this.activity = activity;
//        this.list = list;
////        this.viewLayout = viewLayout;
////        this.cityList = cityList;
//
//    }
//
//    public void setDataList(List<MedCatModel> dataList) {
//        list = dataList;
////        this.cityList = cityList;
//        notifyDataSetChanged();
//    }
//
//    public void setFilter(List<MedCatModel> FilteredDataList) {
//        list = FilteredDataList;
//        notifyDataSetChanged();
//
//    }
//
//    @NonNull
//    @Override
//    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medcat_list_layout, viewGroup, false);
//        mv = new MyViewHlder(view);
//
//        return mv;
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {
//
//        myViewHlder.Title.setText(list.get(i).getName());
//
//        Glide.with(activity).
//                load(Constant.ImgRoot + list.get(i).getIcon())
//                .placeholder(R.drawable.medicine_placeholder)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerInside()
//                .thumbnail(0.05f)
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                .dontTransform()
//                .into(myViewHlder.img);
//
//        myViewHlder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SharedPreferences pref = activity.getSharedPreferences("MedCat", Context.MODE_PRIVATE);
//                pref.edit().putString("id", list.get(i).getId())
//                        .putString("title", list.get(i).getName())
//                        .apply();
//
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new BloodGrpList();
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.mainFrame, myFragment)
//                        .addToBackStack(null)
//                        .commit();
//
//            }
//        });
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//
//            return list.size();
//    }
//
//    class MyViewHlder extends RecyclerView.ViewHolder {
//
//        private TextView Title;
//        private ImageView img;
//        private View view;
//
//        public MyViewHlder(@NonNull View itemView) {
//            super(itemView);
//            Title = itemView.findViewById(R.id.title);
//            img = itemView.findViewById(R.id.img);
//            view = itemView;
//
//        }
//    }
//}