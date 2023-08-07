package com.example.filmes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.filmes.MainActivity
import com.example.filmes.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        /*
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent( this, MainActivity::class.java )
            startActivity( intent )
            finish()
        },3000)


         */
        val backgroundImg: ImageView = findViewById(R.id.imageViewSplash)
        val slideAnimation = AnimationUtils.loadAnimation( this, R.anim.slide )
        backgroundImg.startAnimation( slideAnimation )

        Handler().postDelayed({
            val intent = Intent( this, MainActivity::class.java )
            startActivity( intent )
            finish()
        },3)


    }
}