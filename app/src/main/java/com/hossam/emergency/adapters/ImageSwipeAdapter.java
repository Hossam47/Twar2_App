package com.hossam.emergency.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.R;
import com.hossam.emergency.models.ImageModel;
import com.hossam.emergency.process.ImageProcess;
import com.hossam.emergency.utils.DisplayImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageSwipeAdapter extends RecyclerView.Adapter<ImageSwipeAdapter.ViewHolder> {

    Activity activity;
    ArrayList<ImageModel> urlList;
    ImageProcess imageProcess = ImageProcess.getInstance();
    DisplayImage displayImage = DisplayImage.getInstance();
    private final int pos;

    public ImageSwipeAdapter(Activity activity, ArrayList<ImageModel> urlList, int pos) {
        this.activity = activity;
        this.urlList = urlList;
        this.pos = pos;
    }

    @Override
    public ImageSwipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_adpater_image_item, parent, false);

        ImageSwipeAdapter.ViewHolder viewHolder = new ImageSwipeAdapter.ViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageSwipeAdapter.ViewHolder holder, final int position) {

        if (urlList != null) {
            imageProcess.loadImage(holder.image_swipe, urlList.get(position).getUrl(), activity);
        }
//
//
//        holder.image_swipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(activity, DisplayImageActivity.class);
//                intent.putExtra("image_list", urlList);
//                intent.putExtra("pos_image", position);
//
//
//                activity.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_swipe_adapter_itme)
        ImageView image_swipe;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }

    }

}