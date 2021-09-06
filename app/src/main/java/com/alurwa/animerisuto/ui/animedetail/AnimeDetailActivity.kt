package com.alurwa.animerisuto.ui.animedetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.R
import com.alurwa.animerisuto.adapter.RecommendationAdapter
import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.databinding.ActivityAnimeDetailBinding
import com.alurwa.animerisuto.model.AnimeDetail
import com.alurwa.animerisuto.ui.addedituseranime.AddEditUserAnimeActivity
import com.alurwa.animerisuto.utils.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailActivity : AppCompatActivity() {

    private val binding: ActivityAnimeDetailBinding by lazy {
        ActivityAnimeDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: AnimeDetailViewModel by viewModels()

    private var isSynopsisCollapsed: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar()
        setupSynopsis()
        setupRecyclerView()
        getAnimeDetail()
    }

    private fun getAnimeDetail() {
        lifecycleScope.launch {
            viewModel.animeDetail3.collectLatest {
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
    }

    private fun setupSynopsis() {
        binding.txtSynopsis.setOnClickListener { toogleSynopsisCollapse() }
    }

    private fun toogleSynopsisCollapse() {
        if (isSynopsisCollapsed) {
            binding.txtSynopsis.maxLines = Integer.MAX_VALUE
        } else {
            binding.txtSynopsis.maxLines = 4
        }

        isSynopsisCollapsed = !isSynopsisCollapsed
    }

    private fun setupView(animeDetail: AnimeDetail) {
        binding.animeDetail = animeDetail
        binding.listRecommendations.adapter = RecommendationAdapter(
            animeDetail.recommendations
        ) {
            navigateToAnimeDetail(it)
        }

        supportActionBar?.run {
            title = animeDetail.title
        }
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

    private fun navigateToAnimeDetail(id: Int) {
        Intent(applicationContext, AnimeDetailActivity::class.java)
            .putExtra(EXTRA_ID, id)
            .also {
                startActivity(it)
            }
    }

    private fun navigateToUpdateUserAnimeList() {
        Intent(applicationContext, AddEditUserAnimeActivity::class.java)
            .putExtra(AddEditUserAnimeActivity.EXTRA_ANIME_ID, binding.animeDetail!!.id)
            .putExtra(AddEditUserAnimeActivity.EXTRA_EPISODE_COUNT, binding.animeDetail!!.numEpisodes)
            .also {
                startActivity(it)
            }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.anime_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.change_status -> {
                navigateToUpdateUserAnimeList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
