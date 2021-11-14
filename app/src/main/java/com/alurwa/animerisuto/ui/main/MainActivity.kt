package com.alurwa.animerisuto.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alurwa.animerisuto.R
import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.databinding.ActivityMainBinding
import com.alurwa.animerisuto.ui.login.LoginActivity
import com.alurwa.animerisuto.ui.search.SearchActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        navHostFragment.navController
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!viewModel.isLogged()) {
            Intent(this, LoginActivity::class.java)
                .also { startActivity(it) }
            finish()
        }

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigationDrawer()
        getUser()
    }

    private fun setupNavigationDrawer() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.animeFragment, R.id.mangaFragment),

            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            // Tampilkan icon hamburger destinasi tidak di userAnimeListFragment
            if (destination.id != R.id.userAnimeListFragment) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_round_menu_24)
            }
        }
    }

    private fun getUser() {
        viewModel.user.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val user = it.data
                    //  val txtName = findViewById<TextView>(R.id.txt_name)

                    // txtName.text = user?.name
                }
            }
        }
    }

    private fun doLogOut() {
        MaterialAlertDialogBuilder(this)
            .setMessage("Anda ingin logout?")
            .setPositiveButton("LogOut") { dialog, _ ->
                dialog.dismiss()

                viewModel.logout()

                Intent(this, LoginActivity::class.java)
                    .also { startActivity(it) }

                finish()
            }
            .setNegativeButton(R.string.btn_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()

        // finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                doLogOut()
                true
            }

            R.id.search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }

            else -> item.onNavDestinationSelected(navController) ||
                    super.onOptionsItemSelected(item)
        }
    }
}
