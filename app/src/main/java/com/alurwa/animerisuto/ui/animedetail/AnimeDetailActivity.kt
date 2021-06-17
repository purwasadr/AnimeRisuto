package com.alurwa.animerisuto.ui.animedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alurwa.animerisuto.databinding.ActivityAnimeDetailBinding

class AnimeDetailActivity : AppCompatActivity() {

    private val binding: ActivityAnimeDetailBinding by lazy {
        ActivityAnimeDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


    }
}