package com.hossam.emergency.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.R;
import com.hossam.emergency.process.UploadProcess;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    Activity activity;
    ArrayList<Uri> path;
    ArrayList<String> urlList;
    UploadProcess uploadProcess;

    public ImageAdapter(Activity activity, ArrayList<Uri> path) {
        this.activity = activity;
        this.path = path;

        uploadProcess = new UploadProcess(activity);
    }


    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, final int position) {
        final Uri imagePath = path.get(position);

        // Glide.with(activity).load(imagePath).into(holder.image);
        // Picasso.with(activity).load(imagePath).into(holder.image);

        try {

            Bitmap img = UploadProcess.scaleDown(MediaStore.Images.Media.getBitmap(activity.getContentResolver(),
                    path.get(position)), 1000, true);

            holder.image.setImageBitmap(img);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return path.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_items)
        ImageView image;

//        @BindView(R.id.defalut_image_case)
//        CheckBox defalut_image;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }

    }

}
