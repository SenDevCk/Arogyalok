package com.rkvit.arogyalok.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkvit.arogyalok.Fragments.MedDetails;
import com.rkvit.arogyalok.Fragments.MedSearchFragment;
import com.rkvit.arogyalok.Fragments.MedicineFragment;
import com.rkvit.arogyalok.LocalDB.CartDatabase;
import com.rkvit.arogyalok.Model.CartItemModel;
import com.rkvit.arogyalok.Model.MedListModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;
import java.util.List;


public class MedSearchAdaptor extends RecyclerView.Adapter<MedSearchAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<MedListModel> list;
    private View view;
    private MyViewHlder mv;
    private String viewLayout;

    private String Id;
    private String ItemName;
    private String ItemType;
    private String ImageUrl;
    private String SpclPrice;
    private HashMap<String, String> cityList = new HashMap<String, String>();
    private int itemPosition;
    private CartDatabase db;
    private MedSearchFragment fragment;


    public MedSearchAdaptor(Activity activity, List<MedListModel> list, MedSearchFragment fragment) {
        this.activity = activity;
        this.list = list;
        this.fragment = fragment;
    }

    public void setDataList(List<MedListModel> dataList) {
        list = dataList;
        notifyDataSetChanged();
    }

    public void setFilter(List<MedListModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medcat_list_layout, viewGroup, false);
        mv = new MyViewHlder(view);

        return mv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        double MRP = Double.parseDouble(list.get(i).getMrp());
        double SP = Double.parseDouble(list.get(i).getSaleMrp());
        double diff = MRP - SP;

        int perct = (int) ((diff / MRP) * 100);

        myViewHlder.Title.setText(list.get(i).getName());
//        + "\n" + list.get(i).getCatName() + "\n" + list.get(i).getSubCatName()

        myViewHlder.Mrp.setText("\u20B9" + list.get(i).getMrp());
        myViewHlder.Mrp.setPaintFlags(myViewHlder.Mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        myViewHlder.SalePrice.setText("\u20B9" + list.get(i).getSaleMrp());
        myViewHlder.Discnt.setText(perct + "%");

        Glide.with(activity).
                load(Constant.ImgRoot + list.get(i).getImage())
                .placeholder(R.drawable.medicine_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .thumbnail(0.05f)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .dontTransform()
                .into(myViewHlder.img);

        myViewHlder.BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = activity.getSharedPreferences("MedDetails", Context.MODE_PRIVATE);
                pref.edit().putString("id", list.get(i).getId())
                        .putString("name", list.get(i).getName())
                        .putString("img", list.get(i).getImage())
                        .putString("mrp", list.get(i).getMrp())
                        .putString("salesMrp", list.get(i).getSaleMrp())
                        .putString("pres", list.get(i).getPrescription())
                        .putString("des", list.get(i).getDescription())
                        .putString("refund", list.get(i).getRefundPolicy())
                        .putString("brandID", list.get(i).getBrandid())
                        .putString("paID", list.get(i).getPaId())
                        .putString("marID", list.get(i).getMarId())
                        .putString("CatName", list.get(i).getCatName())
                        .putString("SubCatName", list.get(i).getSubCatName())
                        .putString("CatId", list.get(i).getCatId())
                        .putString("SubCatId", list.get(i).getSubcatId())
                        .apply();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new MedDetails();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrame, myFragment)
                        .addToBackStack(null)
                        .commit();


            }
        });

        myViewHlder.AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemPosition = i;

                FancyToast.makeText(activity, "Added To Cart", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                InsertProduct();

                int count = new CartDatabase(activity).getItemCount();
                fragment.cartItem.setText(String.valueOf(count));

            }
        });


    }


    public void InsertProduct() {

        db = new CartDatabase(activity);
        db.insertRecord(new CartItemModel(list.get(itemPosition).getId(), list.get(itemPosition).getBrandid(),
                list.get(itemPosition).getCatId(), list.get(itemPosition).getSubcatId(),
                list.get(itemPosition).getName(), list.get(itemPosition).getMarId(),
                list.get(itemPosition).getPaId(), list.get(itemPosition).getRefundPolicy(),
                list.get(itemPosition).getPrescription(), list.get(itemPosition).getImage(),
                list.get(itemPosition).getMrp(), list.get(itemPosition).getSaleMrp(),
                list.get(itemPosition).getDescription(), list.get(itemPosition).getCatName(),
                list.get(itemPosition).getSubCatName(), "1"));
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class MyViewHlder extends RecyclerView.ViewHolder {

        private TextView Title, Mrp, SalePrice, Discnt, BuyNow, AddCart;
        private ImageView img;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            Mrp = itemView.findViewById(R.id.mrp);
            SalePrice = itemView.findViewById(R.id.sale_price);
            Discnt = itemView.findViewById(R.id.discnt);
            BuyNow = itemView.findViewById(R.id.buy_now);
            AddCart = itemView.findViewById(R.id.add_cart);
            img = itemView.findViewById(R.id.img);
            view = itemView;

        }
    }
}