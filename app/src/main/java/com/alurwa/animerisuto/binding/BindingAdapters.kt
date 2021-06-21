package com.alurwa.animerisuto.binding

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import com.bumptech.glide.Glide

/**
 * Created by Purwa Shadr Al 'urwa on 17/05/2021
 */

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imagePoster")
    fun imagePoster(imageView: ImageView, posterPath: String?) {
        if (!posterPath.isNullOrEmpty()) {
            Glide.with(imageView.context)
                .load(Uri.parse("$posterPath"))
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("txtGenre")
    fun txtGenre(txtView: TextView, genres: List<String>?) {
        if (!genres.isNullOrEmpty()) {
            val str = StringBuffer()
            genres.forEachIndexed { index, it ->
                if (index < genres.lastIndex - 1) {
                    str.append(it + ", ")
                } else if (index == genres.lastIndex - 1) {
                    val andSeparator = if (genres.size > 2) ", And " else " And "
                    str.append(it + andSeparator)
                } else {
                    str.append(it)
                }
            }
            txtView.text = str.toString()
        }
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, loadState: LoadState) {
    }

    @JvmStatic
    @BindingAdapter("txtDuration")
    fun txtDuration(txtView: TextView, duration: Int?) {
        if (duration != null) {
            val ba = (duration / 60)
            val result = if (duration % 60 == 0 && duration >= 60) {
                "$ba j "
            } else if (duration < 60) {
                "$duration min"
            } else {
                "$ba j " + (duration % 60) + " min"
            }

            txtView.text = result
        }
    }
}
