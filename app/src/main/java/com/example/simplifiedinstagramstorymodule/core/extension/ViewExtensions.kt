package com.example.simplifiedinstagramstorymodule.core.extension

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()
        .into(this)

fun ImageView.loadFromUrlWithPlaceholder(url: String, placeholder: Int) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .error(placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
