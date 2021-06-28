package com.alurwa.animerisuto.ui.animedetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.adapter.RecommendationAdapter
import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.databinding.ActivityAnimeDetailBinding
import com.alurwa.animerisuto.model.AnimeDetail
import com.alurwa.animerisuto.utils.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailActivity : AppCompatActivity() {

    private val binding: ActivityAnimeDetailBinding by lazy {
        ActivityAnimeDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: AnimeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar()
        setupRecyclerView()
        getAnimeDetail()
    }

    private fun getAnimeDetail() {
        viewModel.animeDetail.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val data = it.data
                    if (data != null) {
                        setupView(data)
                    }
                }
            }
        }
    }

    private fun setupView(animeDetail: AnimeDetail) {
        binding.animeDetail = animeDetail
        binding.listRecommendations.adapter = RecommendationAdapter(animeDetail.recommendations)
    }

    private fun setupRecyclerView() {
        with(binding) {

            listRecommendations.layoutManager = LinearLayoutManager(
                applicationContext,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            listRecommendations.addItemDecoration(
                SpacingDecoration(
                    16,
                    8,
                    RecyclerView.HORIZONTAL
                )
            )
            listRecommendations.setHasFixedSize(true)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
