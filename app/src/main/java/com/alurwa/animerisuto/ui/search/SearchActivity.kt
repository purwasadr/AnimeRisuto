package com.alurwa.animerisuto.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.R
import com.alurwa.animerisuto.adapter.AnimeAdapter
import com.alurwa.animerisuto.databinding.ActivitySearchBinding
import com.alurwa.animerisuto.ui.animedetail.AnimeDetailActivity
import com.alurwa.animerisuto.utils.SpacingDecoration
import com.alurwa.animerisuto.utils.Utility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val viewModel: SearchViewModel by viewModels()

    private val adapter: AnimeAdapter by lazy {
        AnimeAdapter() { navigateToAnimeDetail(it) }
    }

    private var searchJob: Job? = null

    private var currentQueryString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupRecyclerView()
        setupLoadingBar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupLoadingBar() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.pi.isVisible = loadStates.refresh is LoadState.Loading
                binding.list.isVisible = loadStates.refresh is LoadState.NotLoading
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    binding.list.scrollToPosition(0)
                }
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            list.layoutManager = LinearLayoutManager(applicationContext)
            list.addItemDecoration(
                SpacingDecoration(
                    16,
                    16,
                    RecyclerView.VERTICAL
                )
            )
            list.setHasFixedSize(true)
            list.adapter = adapter
        }
    }

    private fun getAnimeSearch(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            if (query.isNotEmpty()) {
                viewModel.searchAnime(query).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun navigateToAnimeDetail(id: Int) {
        Intent(this, AnimeDetailActivity::class.java)
            .putExtra(AnimeDetailActivity.EXTRA_ID, id)
            .also {
                startActivity(it)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        setupSearchInput(menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        updateQuery(menu ?: return false)
        return true
    }

    private fun setupSearchInput(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenu = menu.findItem(R.id.search)
        val searchView = searchMenu?.actionView as SearchView

        if (currentQueryString.isEmpty()) {
            searchMenu.expandActionView()
        }

        with(searchView) {
            maxWidth = 10000
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
           // isIconified = false
            //   queryHint = "Search Anime"
          //  isIconifiedByDefault = false
            inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    Utility.hideKeyboard(applicationContext, currentFocus)
                    currentQueryString = query
                    getAnimeSearch(query)
                    supportActionBar?.title = query
                    searchMenu.collapseActionView()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchView.setOnSearchClickListener {
            searchView.setQuery(currentQueryString, false)
        }

        searchMenu.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return if (currentQueryString == "") {
                    finish()
                    false
                } else {
                    true
                }
            }
        })
    }

    private fun updateQuery(menu: Menu) {
        val searchMenu = menu.findItem(R.id.search)
        val searchView = searchMenu?.actionView as SearchView

        searchView.setQuery(currentQueryString, true)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also {
                currentQueryString = it
                invalidateOptionsMenu()
            }
        }

        Timber.d("onNewIntent")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
    }
}
