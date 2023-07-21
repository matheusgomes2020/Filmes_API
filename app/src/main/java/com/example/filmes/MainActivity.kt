package com.example.filmes

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.filmes.databinding.ActivityMainBinding
import com.example.filmes.ui.filmes.MovieFragment
import com.example.filmes.ui.perfil.ProfileFragment
import com.example.filmes.ui.search.SearchActivity
import com.example.filmes.ui.searchSeries.SerieSearchActivity
import com.example.filmes.ui.series.SeriesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var actual = "Filmes"

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(MovieFragment())
        setupToolbar()
           binding.navView.setOnItemSelectedListener {

               when( it.itemId ) {
                   R.id.navigation_movie -> { actual = "Filmes"
                       replaceFragment(MovieFragment()) }
                   R.id.navigation_serie -> { actual = "Séries"
                       replaceFragment(SeriesFragment()) }
                   R.id.navigation_profile -> { actual = "Perfil"
                       replaceFragment(ProfileFragment()) }
                   else -> {}
               }
               true
           }
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.myToolbar2)
        binding.myToolbar2.setTitleTextColor(resources.getColor(R.color.white))
        binding.myToolbar2.setTitle(actual)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            Toast.makeText(applicationContext, actual, Toast.LENGTH_SHORT).show()

        when (item.itemId) {
            R.id.search -> {
                return if ( actual == "Filmes" ) {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity( intent )
                    true
                } else {
                    val intent = Intent(this, SerieSearchActivity::class.java)
                    startActivity( intent )
                    true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment( fragment: Fragment ){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        setupToolbar()
    }
}