package com.rkvit.arogyalok.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkvit.arogyalok.Fragments.MedCartFragment;
import com.rkvit.arogyalok.LocalDB.CartDatabase;
import com.rkvit.arogyalok.Model.CartItemModel;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


public class CartItemAdaptor extends RecyclerView.Adapter<CartItemAdaptor.MyViewHlder> {
    String ID, ItemName, Img, MainPrice, SpclPrice, QtyStr, Weight;
    MedCartFragment fragment;
    private Activity context;
    private List<CartItemModel> list;
    private String CategoryTitle;
    private CartDatabase db;
    private ArrayList<CartItemModel> updatedList;
    private int TotalAmt;
    private int count;
    private int Qty;
    private int UpdatedSpclPrice, UpdatedMainPrice;
    private int TotalPrice;
    private int savedTTL = 0;
    private int TotalSaving;
    private String ProductId;
    private List<CartItemModel> productDetailsList;
    private int itemPosition;

    public CartItemAdaptor(Activity activity, List<CartItemModel> list, MedCartFragment fragment) {
        this.context = activity;
        this.list = list;
        this.fragment = fragment;
    }

    public void setPostList(List<CartItemModel> productDataList) {
        this.list = productDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MyViewHlder myViewHlder;

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list, viewGroup, false);
        myViewHlder = new MyViewHlder(view);
        return myViewHlder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, int i) {

        final CartItemModel model = list.get(i);

        myViewHlder.ProductTitle.setText(model.getName());
        String ttlMainPrice = String.valueOf(Integer.valueOf(model.getMrp()) * Integer.valueOf(model.getQuantity()));
        myViewHlder.ProductMainPrice.setText("\u20B9 " + ttlMainPrice);
        myViewHlder.ProductMainPrice.setPaintFlags(myViewHlder.ProductMainPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//        String ttlSpclPrice = String.valueOf(Integer.valueOf(list.get(i).getProductPrice()) * Integer.valueOf(list.get(i).getQuantity()));
        String ttlSpclPrice = String.valueOf(Integer.valueOf(model.getSaleMrp()) * Integer.valueOf(model.getQuantity()));
        myViewHlder.ProductSpclPrice.setText("\u20B9 " + ttlSpclPrice);

        myViewHlder.ProductTitle.setTag(list.get(i).getId());

        Glide.with(context)
                .load(Constant.ImgRoot + model.getImage())
                .placeholder(R.drawable.medicine_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .into(myViewHlder.ProductImg);

//        myViewHlder.qtyText.setText(list.get(i).getQuantity());
        myViewHlder.qtyText.setText(model.getQuantity());

        myViewHlder.dltItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = new CartDatabase(context);
                db.deleteRow(list.get(i).getId());
                FancyToast.makeText(context, "Item Deleted Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                list.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, list.size());
                getCartData();

            }
        });

        myViewHlder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemPosition = i;

                int qtyUpdate = Integer.parseInt(myViewHlder.qtyText.getText().toString()) + 1;
                myViewHlder.qtyText.setText(String.valueOf(qtyUpdate));

                //update list

                list.get(i).setQuantity(String.valueOf(qtyUpdate));

                String ttlMainPrice = String.valueOf(Integer.valueOf(model.getMrp()) * qtyUpdate);
                myViewHlder.ProductMainPrice.setText("\u20B9 " + ttlMainPrice);
                myViewHlder.ProductMainPrice.setPaintFlags(myViewHlder.ProductMainPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                String ttlSpclPrice = String.valueOf(Integer.valueOf(model.getSaleMrp()) * qtyUpdate);
                myViewHlder.ProductSpclPrice.setText("\u20B9 " + ttlSpclPrice);

//                ID = list.get(i).getId();
//                ItemName = list.get(i).getName();
//                Img = list.get(i).getImageUrl();
//                MainPrice = list.get(i).getMainPrice();
//                SpclPrice = list.get(i).getSpclPrice();
//                Weight = list.get(i).getWeight();
                QtyStr = String.valueOf(qtyUpdate);
//                Log.v("selectedWeight", Weight);

                InsertProduct();

//                addCartweb();

            }
        });

        myViewHlder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemPosition = i;

                int qtyUpdate = Integer.parseInt(myViewHlder.qtyText.getText().toString()) - 1;
                if (qtyUpdate > 0) {
                    myViewHlder.qtyText.setText(String.valueOf(qtyUpdate));

                    //update list
//                    list.get(i).setQuantity(String.valueOf(qtyUpdate));
                    list.get(i).setQuantity(String.valueOf(qtyUpdate));

//                    String ttlMainPrice = String.valueOf(Integer.valueOf(list.get(i).getBuyPrice()) * qtyUpdate);
                    String ttlMainPrice = String.valueOf(Integer.valueOf(model.getMrp()) * qtyUpdate);
                    myViewHlder.ProductMainPrice.setText("\u20B9 " + ttlMainPrice);
                    myViewHlder.ProductMainPrice.setPaintFlags(myViewHlder.ProductMainPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//                    String ttlSpclPrice = String.valueOf(Integer.valueOf(list.get(i).getProductPrice()) * qtyUpdate);
                    String ttlSpclPrice = String.valueOf(Integer.valueOf(model.getSaleMrp()) * qtyUpdate);
                    myViewHlder.ProductSpclPrice.setText("\u20B9 " + ttlSpclPrice);

//                    ProductId = list.get(i).getProductId();
                    ID = list.get(i).getId();
                    ItemName = myViewHlder.ProductTitle.getText().toString();
//                    Img = list.get(i).getFileUrl();
//                    MainPrice = list.get(i).getBuyPrice();
//                    SpclPrice = list.get(i).getProductPrice();
//                    Img = list.get(i).getImageUrl();
//                    MainPrice = list.get(i).getMainPrice();
//                    SpclPrice = list.get(i).getSpclPrice();
//                    Weight = list.get(i).getWeight();
                    QtyStr = String.valueOf(qtyUpdate);
                    Log.v("selectedWeight", SpclPrice + " " + QtyStr);

                    InsertProduct();

//                    addCartweb();

                } else {
                    ID = list.get(i).getId();
                    FancyToast.makeText(context, "Removed From Cart", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    db = new CartDatabase(context);
                    db.deleteRow(ID);
//                    Toast.makeText(context, "Item Deleted Succesfully", Toast.LENGTH_SHORT).show();
                    list.remove(i);
                    notifyItemRemoved(i);

                    getCartData();

//                    addCartweb();

                }
            }
        });
    }


    public void InsertProduct() {

        db = new CartDatabase(context);
//        db.insertRecord(new CartItemModel(ID, ItemName, Img, MainPrice, SpclPrice, QtyStr, Weight));

        db.insertRecord(new CartItemModel(list.get(itemPosition).getId(), list.get(itemPosition).getBrandid(),
                list.get(itemPosition).getCatId(), list.get(itemPosition).getSubcatId(),
                list.get(itemPosition).getName(), list.get(itemPosition).getMarId(),
                list.get(itemPosition).getPaId(), list.get(itemPosition).getRefundPolicy(),
                list.get(itemPosition).getPrescription(), list.get(itemPosition).getImage(),
                list.get(itemPosition).getMrp(), list.get(itemPosition).getSaleMrp(),
                list.get(itemPosition).getDescription(), list.get(itemPosition).getCatName(),
                list.get(itemPosition).getSubCatName(), QtyStr));

        getCartData();
    }

    private void getCartData() {
        int TotalSpclPrice = 0;
        int TotalMainPrice = 0;
        db = new CartDatabase(context);
        count = db.getItemCount();
        Log.v("getCartData", String.valueOf(count));
        if (count > 0) {
            updatedList = new ArrayList<>(db.getAllProduct());
            Log.v("cartlist", updatedList.toString());

            for (int i = 0; i < updatedList.size(); i++) {

                Qty = Integer.valueOf(updatedList.get(i).getQuantity());
                Log.v("qty", String.valueOf(Qty));
                UpdatedMainPrice = Integer.valueOf(updatedList.get(i).getMrp());
                UpdatedSpclPrice = Integer.valueOf(updatedList.get(i).getSaleMrp());
                Log.v("price", String.valueOf(UpdatedSpclPrice));
                TotalSpclPrice += Qty * UpdatedSpclPrice;
                TotalMainPrice += Qty * UpdatedMainPrice;


                Log.v("Ttl", String.valueOf(TotalSpclPrice));

                TotalSaving = TotalMainPrice - TotalSpclPrice;
                Log.v("saving", String.valueOf(TotalSaving));

                fragment.UpdateTtl(String.valueOf(TotalSpclPrice), String.valueOf(count));
                fragment.UpdateTtlSaving(String.valueOf(TotalSaving));

            }
        } else {

            fragment.UpdateTtl(String.valueOf(0), String.valueOf(0));
            fragment.showEmptyMsg();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHlder extends RecyclerView.ViewHolder {

        TextView sub_cat_name, sub_main_price, sub_spcl_price;
        ImageView dltItem;
        private TextView CatId, ProductTitle, ProductMainPrice, ProductSpclPrice, qtyText;
        private ImageView ProductImg, image;
        private Button addItem, removeItem;
        private CardView cardview_subcat;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);

            ProductTitle = itemView.findViewById(R.id.sub_cat_name);
            ProductImg = itemView.findViewById(R.id.image);
//            cardview_subcat = itemView.findViewById(R.id.cardview_subcat);
            ProductMainPrice = itemView.findViewById(R.id.sub_main_price);
            ProductSpclPrice = itemView.findViewById(R.id.sub_spcl_price);
//            editItem = itemView.findViewById(R.id.addItem);
            dltItem = itemView.findViewById(R.id.dltItem);
            addItem = itemView.findViewById(R.id.addItem);
            dltItem = itemView.findViewById(R.id.dltItem);
            removeItem = itemView.findViewById(R.id.removeItem);
            qtyText = itemView.findViewById(R.id.qtyText);

        }
    }
}