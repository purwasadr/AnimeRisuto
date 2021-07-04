package com.alurwa.animerisuto.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Purwa Shadr Al 'urwa on 28/06/2021
 */

object Utility {
    fun hideKeyboard(context: Context, focus: View?) {
        if (focus != null) {
            (context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(focus.windowToken, 0)
        }
    }
}
