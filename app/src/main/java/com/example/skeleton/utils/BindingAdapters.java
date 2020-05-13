package com.example.skeleton.utils;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageUrl == null) {

        } else {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .into(imageView);
        }
    }
}
