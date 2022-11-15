package com.hossam.emergency.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.R;
import com.hossam.emergency.models.ImageModel;
import com.hossam.emergency.process.ImageProcess;
import com.hossam.emergency.utils.DisplayImage;
import com.hossam.emergency.utils.utils_activity.DisplayImageActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageCaseDetalisAdapter extends RecyclerView.Adapter<ImageCaseDetalisAdapter.ViewHolder> {

    Activity activity;
    ArrayList<ImageModel> urlList;
    ImageProcess imageProcess = ImageProcess.getInstance();
    DisplayImage displayImage = DisplayImage.getInstance();

    public ImageCaseDetalisAdapter(Activity activity, ArrayList<ImageModel> urlList) {
        this.activity = activity;
        this.urlList = urlList;
    }

    @Override
    public ImageCaseDetalisAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_case_details_items, parent, false);

        ImageCaseDetalisAdapter.ViewHolder viewHolder = new ImageCaseDetalisAdapter.ViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageCaseDetalisAdapter.ViewHolder holder, final int position) {

        if (urlList != null) {
            imageProcess.loadImage(holder.image, urlList.get(position).getUrl(), activity);
        }


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                displayImage.displayMultiImage(activity, urlList);
                Intent intent = new Intent(activity, DisplayImageActivity.class);
                intent.putExtra("image_url", urlList.get(position).getUrl());
                // intent.putExtra("pos_image", position);

                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_case_details)
        ImageView image;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }

    }

}
