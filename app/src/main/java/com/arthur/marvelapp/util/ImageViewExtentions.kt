package com.arthur.marvelapp.util

import android.widget.ImageView
import com.arthur.marvelapp.R
import com.bumptech.glide.Glide

fun ImageView.load(url: String,img: ImageView) {
    Glide.with(context)
        .load(url)
        .dontAnimate()
        .into(img);
}