package com.rkvit.arogyalok.Filter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rkvit.arogyalok.Filter.MedFilter;
import com.rkvit.arogyalok.Filter.MedFilterPreferences;
import com.rkvit.arogyalok.R;

import java.util.List;

public class MedFilterValuesAdapter extends RecyclerView.Adapter<MedFilterValuesAdapter.MyViewHolder> {

    private Context context;
    private Integer filterIndex;

    public MedFilterValuesAdapter(Context context, Integer filterIndex) {
        this.context = context;
        this.filterIndex = filterIndex;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_value_item, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        final MedFilter tmpHotelFilter = MedFilterPreferences.filters.get(filterIndex);
        myViewHolder.value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selected = tmpHotelFilter.getSelected();
                if(myViewHolder.value.isChecked()) {
                    selected.add(tmpHotelFilter.getValues().get(position));
                    tmpHotelFilter.setSelected(selected);
                } else {
                    selected.remove(tmpHotelFilter.getValues().get(position));
                    tmpHotelFilter.setSelected(selected);
                }
                MedFilterPreferences.filters.put(filterIndex, tmpHotelFilter);

                Log.v("check", tmpHotelFilter.getValues().get(0));
            }
        });
        myViewHolder.value.setText(tmpHotelFilter.getValues().get(position));
        if(tmpHotelFilter.getSelected().contains(tmpHotelFilter.getValues().get(position))) {
            myViewHolder.value.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return MedFilterPreferences.filters.get(filterIndex).getValues().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View container;
        CheckBox value;

        public MyViewHolder(View view) {
            super(view);
            container = view;
            value = view.findViewById(R.id.value);
        }
    }

}
