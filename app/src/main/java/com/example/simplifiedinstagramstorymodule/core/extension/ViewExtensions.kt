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

/*
fun ImageView.loadUrlAndPostponeEnterTransition(url: String, activity: FragmentActivity) {
    val target: Target<Drawable> = ImageViewBaseTarget(
        this,
        activity
    )
    Glide.with(context.applicationContext).load(url).into(target)
}

fun ImageView.createRoundedImage(
    url: String, radius: Int, margin: Int,
    cornerType: RoundedCornersTransformation.CornerType,
    placeholder: Int
) {
    Glide.with(this.context.applicationContext)
        .load(url)
        .placeholder(placeholder)
        .transforms(FitCenter(), RoundedCornersTransformation(radius, margin, cornerType))
        .into(this)
}
*/

fun ImageView.createRoundImageFromUri(uri: Uri) {
    Glide.with(this.context)
        .load(uri)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun ImageView.createRoundImageFromBase64(base64String: String) {
    val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
    Glide.with(this.context)
        .load(BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size))
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}