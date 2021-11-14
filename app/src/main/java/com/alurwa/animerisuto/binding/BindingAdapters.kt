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

    @JvmStatic
    @BindingAdapter("txtSentenceCase")
    fun txtSentenceCase(txtView: TextView, txt: String?) {
        if (txt != null) {
            txtView.text = txt.split('_').let {
                val stringBuilder = StringBuilder()
                it.forEachIndexed { index, txtList ->
                    if (index > 0) {
                        stringBuilder.append(' ')
                    }

                    stringBuilder.append(
                        txtList.replaceFirstChar { firstChat ->
                            firstChat.uppercase()
                        }
                    )
                }
                stringBuilder.toString()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("txtStringList")
    fun txtStringList(txtView: TextView, txtList: List<String>?) {
        if (!txtList.isNullOrEmpty()) {
            txtView.text = txtList.joinToString()
        } else {
            txtView.text = "-"
        }
    }

    /**
     * Makes the View [View.INVISIBLE] unless the condition is met.
     */
    @BindingAdapter("invisibleUnless")
    @JvmStatic
    fun invisibleUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    /**
     * Makes the View [View.GONE] unless the condition is met.
     */
    @BindingAdapter("goneUnless")
    @JvmStatic
    fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
