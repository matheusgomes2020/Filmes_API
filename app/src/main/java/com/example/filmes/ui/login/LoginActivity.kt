package com.example.filmes.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmes.adapter.LoginAdapter
import com.example.filmes.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Login"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Cadastro"))
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL)

        setupViewPager()
    }

    private fun setupViewPager() {
        val viewPager = binding.viewPager
        viewPager.adapter = LoginAdapter(supportFragmentManager)

        binding.fabFb.setTranslationY(300F)
        binding.fabGoogle.setTranslationY(300F)
        binding.fabTwitter.setTranslationY(300F)
        binding.tabLayout.setTranslationY(300F)

        binding.fabFb.setAlpha(0)
        binding.fabGoogle.setAlpha(0)
        binding.fabTwitter.setAlpha(0)
        binding.tabLayout.setAlpha(0F)

        binding.fabFb.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(400).start()
        binding.fabGoogle.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(400).start()
        binding.fabTwitter.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(400).start()
        binding.tabLayout.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(400).start()
    }

}