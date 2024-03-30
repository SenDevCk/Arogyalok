package com.rkvit.arogyalok.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rkvit.arogyalok.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.rkvit.arogyalok.Model.ImgsModel;

import java.util.List;


public class ImgSliderAdapter extends SliderViewAdapter<ImgSliderAdapter.SliderAdapterVH> {

    private Activity activity;
    private List<ImgsModel> list;
    private String ImgRoot;

    public ImgSliderAdapter(Activity activity, List<ImgsModel> list) {
        this.activity = activity;
        this.list = list;
    }

    public void setSliderData(List<ImgsModel> dataList, String ImgRoot) {
        list = dataList;
        this.ImgRoot = ImgRoot;
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_img, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

//        final DataModel model = list.get(position);
//
//        String img = model.getSliderImage();
//        Log.v("IMG", img);
//        viewHolder.imageViewBackground.setAnimation(AnimationUtils.loadAnimation(context,R.anim.zoom_in));

        Glide.with(activity).load(ImgRoot   + list.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
//                .transition(GenericTransitionOptions.with(R.anim.zoom_in))
                .into(viewHolder.imageViewBackground);

//        viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(activity, WebView.class);
//                i.putExtra("Url", Constant.HotelImgRoot   + list.get(position).getImgurl());
//                activity.startActivity(i);
//            }
//        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return list.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image);
//            imageViewBackground.setAnimation(AnimationUtils.loadAnimation(context,R.anim.zoom_in));

            this.itemView = itemView;
        }
    }
}