package com.example.filmes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import com.example.filmes.MainActivity
import com.example.filmes.R
import com.example.filmes.databinding.ActivitySplashScreenBinding
import com.example.filmes.ui.login.AuthViewModel
import com.example.filmes.ui.login.LoginActivity
import com.example.filmes.ui.login.LoginnActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView( binding.root )

        viewModel.loggedUser()

        val backgroundImg: ImageView = findViewById(R.id.imageViewSplash)
        val slideAnimation = AnimationUtils.loadAnimation( this, R.anim.slide )
        backgroundImg.startAnimation( slideAnimation )

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.user.collect {
                if (it.isLoading) {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    Handler().postDelayed({
                        binding.progressCircular.visibility = View.GONE
                        startActivity(Intent(this@SplashScreen, LoginnActivity::class.java))
                    }, 3000)
                }
                it.data?.let {
                    Handler().postDelayed({
                        binding.progressCircular.visibility = View.GONE
                        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    }, 3000)

                }
            }
        }



    }
}