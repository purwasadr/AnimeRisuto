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
import com.alurwa.animerisuto.databinding.ActivityAnimeDetailBinding
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
        setupBinding()
        setupSynopsis()
        setupRecyclerView()
        setupTranslateToggle()
        setupView()
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupSynopsis() {
        binding.tvSynopsis.setOnClickListener { toggleSynopsisCollapse() }
    }

    private fun toggleSynopsisCollapse() {
        if (isSynopsisCollapsed) {
            binding.tvSynopsis.maxLines = Integer.MAX_VALUE
        } else {
            binding.tvSynopsis.maxLines = 4
        }

        isSynopsisCollapsed = !isSynopsisCollapsed
    }

    private fun setupTranslateToggle() {
        binding.cbTranslate.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setIsTranslated(isChecked)

            if (isChecked) {
                binding.cbTranslate.text = "Lihat Asli"


            } else {
                binding.cbTranslate.text = "Terjemahkan Ke indo"
            }
        }
    }

    private fun setupView() {
        lifecycleScope.launch {
            viewModel.animeDetail.collectLatest { animeDetail ->
                if (animeDetail != null) {
                    binding.listRecommendations.adapter = RecommendationAdapter(
                        animeDetail.recommendations
                    ) {
                        navigateToAnimeDetail(it)
                    }
                }

                supportActionBar?.run {
                    title = animeDetail?.title.orEmpty()
                }
            }
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
            .putExtra(AddEditUserAnimeActivity.EXTRA_ANIME_ID, viewModel.animeDetail.value!!.id)
            .putExtra(AddEditUserAnimeActivity.EXTRA_EPISODE_COUNT, viewModel.animeDetail.value!!.numEpisodes)
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
