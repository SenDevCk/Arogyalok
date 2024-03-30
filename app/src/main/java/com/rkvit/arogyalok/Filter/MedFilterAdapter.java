package com.rkvit.arogyalok.Filter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rkvit.arogyalok.R;

import java.util.HashMap;

public class MedFilterAdapter extends RecyclerView.Adapter<MedFilterAdapter.MyViewHolder> {

    private Context context;
    private HashMap<Integer, MedFilter> filters;
    private RecyclerView filterValuesRV;
    private int selectedPostion = 0;

    public MedFilterAdapter(Context context, HashMap<Integer, MedFilter> filters, RecyclerView filterValuesRV) {
        this.context = context;
        this.filters = filters;
        this.filterValuesRV = filterValuesRV;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_item, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterValuesRV.setAdapter(new MedFilterValuesAdapter(context, position));
                selectedPostion = position;
                notifyDataSetChanged();
            }
        });

        filterValuesRV.setAdapter(new MedFilterValuesAdapter(context, selectedPostion));
        myViewHolder.container.setBackgroundColor(selectedPostion == position ? Color.WHITE : Color.TRANSPARENT);
        myViewHolder.title.setText(filters.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView title;

        public MyViewHolder(View view) {
            super(view);
            container = view;
            title = view.findViewById(R.id.title);
        }
    }

}
