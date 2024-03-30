package com.rkvit.arogyalok.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rkvit.arogyalok.Fragments.LabDetails;
import com.rkvit.arogyalok.LocalDB.TestDatabase;
import com.rkvit.arogyalok.Model.SlctTestModel;
import com.rkvit.arogyalok.Model.SlctTestModel;
import com.rkvit.arogyalok.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LabTestSelctedAdaptor extends RecyclerView.Adapter<LabTestSelctedAdaptor.MyViewHlder> {
    private final int limit = 5;
    private Activity activity;
    private List<SlctTestModel> list;
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
    private TestDatabase db;
    private LabDetails fragment;
    private HashMap<String, String> hashTestList = new HashMap<String, String>();
    private BottomSheetDialog dialog;
    private EditText patientName, age, recommended, userCity, pickupPoint, dropPoint, aptDate, addrress, patientMobile;
    private Spinner bloodGroup;
    private AutoCompleteTextView pincode, stateDropdown, cityDropdown;
    private String UserName, UserMobile, UserAddress, Pincode, AptDate;
    private boolean valid;
    private Spinner paymentMode;
    private int count;
    private ArrayList<SlctTestModel> updatedList;
    private int UpdatedMainPrice;


    public LabTestSelctedAdaptor(Activity activity, List<SlctTestModel> list ,LabDetails fragment) {
        this.activity = activity;
        this.list = list;
        this.fragment = fragment;
        this.hashTestList = hashTestList;
    }

    public void setDataList(List<SlctTestModel> dataList) {
        list = dataList;
        this.hashTestList = hashTestList;
        notifyDataSetChanged();
    }

    public void setFilter(List<SlctTestModel> FilteredDataList) {
        list = FilteredDataList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_test_list_layout, viewGroup, false);
        mv = new MyViewHlder(view);

        return mv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHlder myViewHlder, final int i) {

        myViewHlder.Title.setText(list.get(i).getName());
        myViewHlder.Mrp.setText("Fees: " + "\u20B9" + list.get(i).getMrp());

        myViewHlder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemPosition = i;

                Toast.makeText(activity, "Test Added", Toast.LENGTH_SHORT).show();

                myViewHlder.remove.setVisibility(View.VISIBLE);
                myViewHlder.add.setVisibility(View.GONE);

                InsertProduct();

            }
        });

        myViewHlder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPosition = i;

                Toast.makeText(activity, "Test Removed", Toast.LENGTH_SHORT).show();

//                myViewHlder.remove.setVisibility(View.GONE);
//                myViewHlder.add.setVisibility(View.VISIBLE);

//                ID = list.get(i).getId();
//                FancyToast.makeText(context, "Removed From Cart", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                db = new TestDatabase(activity);
                db.deleteRow(list.get(i).getId());
                Toast.makeText(activity, "Test Removed", Toast.LENGTH_SHORT).show();

                list.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, list.size());

                getCartData();
            }
        });

    }

    private void InsertProduct() {
        db = new TestDatabase(activity);

        db.insertRecord(new SlctTestModel(list.get(itemPosition).getId(),
                list.get(itemPosition).getName(), list.get(itemPosition).getImage(),
                list.get(itemPosition).getMrp(), "1"));

        getCartData();

    }


    private void getCartData() {
        int TotalSpclPrice = 0;
        int TotalMainPrice = 0;
        db = new TestDatabase(activity);
        count = db.getItemCount();
        Log.v("getCartData", String.valueOf(count));
        if (count > 0) {
            updatedList = new ArrayList<>(db.getAllProduct());
            Log.v("cartlist", updatedList.toString());

            for (int i = 0; i < updatedList.size(); i++) {

//                Qty = Integer.valueOf(updatedList.get(i).getQuantity());
//                Log.v("qty", String.valueOf(Qty));
                UpdatedMainPrice = Integer.parseInt(updatedList.get(i).getMrp());
//                UpdatedSpclPrice = Integer.valueOf(updatedList.get(i).getSaleMrp());
//                Log.v("price", String.valueOf(UpdatedSpclPrice));
//                TotalSpclPrice += Qty * UpdatedSpclPrice;
                TotalMainPrice += UpdatedMainPrice;


                Log.v("Ttl", String.valueOf(TotalMainPrice));

//                TotalSaving = TotalMainPrice - TotalSpclPrice;
//                Log.v("saving", String.valueOf(TotalSaving));

                fragment.UpdateTtl(String.valueOf(TotalMainPrice), String.valueOf(count));
//                fragment.UpdateTtlSaving(String.valueOf(TotalSaving));

            }
        } else {

            fragment.UpdateTtl(String.valueOf(0), String.valueOf(0));
//            fragment.showEmptyMsg();
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class MyViewHlder extends RecyclerView.ViewHolder {

        private TextView Title, Mrp, TestMand, TestReport, TestPara, BookNow, testName, testValidity;
        private TextView add, remove;
        private ImageView img;
        private View view;

        public MyViewHlder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            Mrp = itemView.findViewById(R.id.mrp);

            add = itemView.findViewById(R.id.add);
            add.setVisibility(View.GONE);
            remove = itemView.findViewById(R.id.remove);
            remove.setVisibility(View.VISIBLE);

            view = itemView;

        }
    }
}