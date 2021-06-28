package com.alurwa.animerisuto.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alurwa.animerisuto.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}