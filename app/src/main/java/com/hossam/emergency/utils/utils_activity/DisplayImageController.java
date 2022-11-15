package com.hossam.emergency.utils.utils_activity;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.adapters.ImageSwipeAdapter;
import com.hossam.emergency.models.ImageModel;

import java.util.ArrayList;

public class DisplayImageController implements ProviderDisplayImage {

    private final Activity activity;
    private final RecyclerView recyclerViewSwipe;
    private ImageSwipeAdapter imageAdapter;

    public DisplayImageController(Activity activity, RecyclerView recyclerViewSwipe) {
        this.activity = activity;
        this.recyclerViewSwipe = recyclerViewSwipe;
    }

    @Override
    public void initRecyclerView(ArrayList<ImageModel> imageModels, int pos) {

        imageAdapter = new ImageSwipeAdapter(activity, imageModels, pos);
        recyclerViewSwipe.setAdapter(imageAdapter);

    }
}
