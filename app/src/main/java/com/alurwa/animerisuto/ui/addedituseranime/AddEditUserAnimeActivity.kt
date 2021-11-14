package com.alurwa.animerisuto.ui.addedituseranime

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.alurwa.animerisuto.R
import com.alurwa.animerisuto.data.Result
import com.alurwa.animerisuto.databinding.ActivityAddEditUserAnimeBinding
import com.alurwa.animerisuto.extensions.setupToolbar
import com.alurwa.animerisuto.model.UserAnimeListParam
import com.alurwa.animerisuto.utils.StatusType
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class AddEditUserAnimeActivity : AppCompatActivity() {
    private val binding: ActivityAddEditUserAnimeBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityAddEditUserAnimeBinding.inflate(layoutInflater)
    }

    private val viewModel: AddEditUserAnimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setupToolbar(this, "", true)

        setupInputView()
        setupInputEpisodeWatched()
    }

    private fun setupInputView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.actStatus.setOnClickListener {
            showDialogStatus()
        }

        binding.actScore.setOnClickListener {
            showDialogScore()
        }
    }

    private fun setupInputEpisodeWatched() {
        val totalEpisodes = intent.getIntExtra(EXTRA_EPISODE_COUNT, -1)

        binding.tilEpisodeWatched.suffixText = "/ $totalEpisodes"

        binding.edtEpisodeWatched.doOnTextChanged { text, start, before, count ->
            if (text.toString() == (10).toString()) {
                binding.tilEpisodeWatched.isErrorEnabled = true
                binding.tilEpisodeWatched.error = "Cannot"
            }
        }
    }

    private fun showDialogStatus() {
        val arrayItems = Array(StatusType.values().size) { index ->
            StatusType.values()[index].code.replace('_', ' ').replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        }

        val checkedItem = viewModel.status.value.ordinal

        MaterialAlertDialogBuilder(this)
            .setTitle("Status")
            .setSingleChoiceItems(arrayItems, checkedItem) { dialog, which ->
                viewModel.setStatus(StatusType.values()[which])
                dialog.dismiss()
            }
            .show()
    }

    private fun showDialogScore() {
        val arrayItems = Array(10) { index ->
            (index + 1).toString()
        }

        val checkedItem = viewModel.score.value

        MaterialAlertDialogBuilder(this)
            .setTitle("Score")
            .setSingleChoiceItems(arrayItems, checkedItem) { dialog, which ->
                viewModel.setScore(which + 1)
                dialog.dismiss()
            }
            .show()
    }

    private fun submitUserAnimeList() {
        val animeId = intent.getIntExtra(EXTRA_ANIME_ID, -1)
        val params = UserAnimeListParam(
            animeId = animeId,
            status = viewModel.status.value.code,
            score = viewModel.score.value,
            numWatchedEpisodes = binding.edtEpisodeWatched.text.toString().toInt(),
            comments = null,
            isRewatching = null,
            updatedAt = null,
            rewatchValue = null,
            priority = null,
            numTimesRewatched = null,
            tags = listOf()

        )

        lifecycleScope.launch {
            viewModel.updateUserAnimeList(params)
                .collectLatest {
                    when(it) {
                        is Result.Success -> {
                            finish()
                        }

                        is Result.Loading -> {
                           Timber.d("Loading")
                        }

                        is Result.Error -> {
                            Timber.d(it.exception.message)
                        }
                    }
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.update_user_anime_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.submit -> {
                submitUserAnimeList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    companion object {
        const val EXTRA_ANIME_ID = "extra_anime_id"
        const val EXTRA_EPISODE_COUNT = "extra_episode_count"
    }
}