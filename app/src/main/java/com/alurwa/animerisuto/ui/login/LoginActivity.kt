package com.alurwa.animerisuto.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alurwa.animerisuto.BuildConfig
import com.alurwa.animerisuto.constant.OAUTH_BASE_URL
import com.alurwa.animerisuto.constant.REDIRECT_URI
import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.databinding.ActivityLoginBinding
import com.alurwa.animerisuto.ui.main.MainActivity
import com.alurwa.animerisuto.utils.PkceGenerator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels()

    private val codeVerifier = PkceGenerator.generateVerifier(128)
 //   val codeChallenge = codeVerifier


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupLogin()
    }

    private fun setupLogin() {

        val loginUrl = Uri.parse(
            OAUTH_BASE_URL + "v1/oauth2/authorize" + "?response_type=code" +
                "&client_id=" + BuildConfig.CLIENT_ID + "&code_challenge=" + codeVerifier +
                "&state=" + STATE + "&redirect_uri=" + REDIRECT_URI
        )

        val mIntent = Intent(Intent.ACTION_VIEW, loginUrl)

        binding.btnLogin.setOnClickListener {
            startActivity(mIntent)
        }
    }

    private fun getLoginData(uri: Uri) {
        val code = uri.getQueryParameter("code")
        val receivedState = uri.getQueryParameter("state")

        if (code != null && receivedState == STATE) {
            lifecycleScope.launch {
                viewModel.getAccesToken(code, codeVerifier)
                    .collectLatest {
                        when (it) {
                            is Resource.Success -> {

                                Snackbar.make(
                                    binding.root, "Login Success",Snackbar.LENGTH_SHORT
                                ).show()

                                navigateToMain()
                            }
                            is Resource.Error -> {
                                Toast.makeText(
                                    applicationContext,
                                    it.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is Resource.Loading -> {

                            }
                        }
                }
            }
        }
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }

        finish()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.data

        Timber.d(uri?.getQueryParameter("code"))
        Timber.d(uri?.getQueryParameter("state"))
        Timber.d(uri?.toString())

        Toast.makeText(this, uri?.getQueryParameter("code"), Toast.LENGTH_SHORT).show()
        Toast.makeText(this, uri?.getQueryParameter("state"), Toast.LENGTH_SHORT).show()

        if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
            getLoginData(uri)
        }
    }

    companion object {
        const val STATE = "AnimeRisuto"
    }
}
