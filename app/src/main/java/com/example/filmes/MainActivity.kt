package com.example.filmes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.filmes.databinding.ActivityMainBinding
import com.example.filmes.ui.search.SearchActivity
import com.example.filmes.ui.searchSeries.SerieSearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var atual = "Movie"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movie, R.id.navigation_serie, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)




    }

    private fun setupToolbar(){
        setSupportActionBar(binding.myToolbar2)
        binding.myToolbar2.setTitleTextColor(resources.getColor(R.color.white))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //Toast.makeText(applicationContext, atual, Toast.LENGTH_SHORT).show()

        when (item.itemId) {
            R.id.search -> {

                if ( atual == "Movie" ) {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity( intent )
                    return true
                }

                if ( atual == "Serie" ) {
                    val intent = Intent(this, SerieSearchActivity::class.java)
                    startActivity( intent )
                    return true
                }


            }
        }

        return super.onOptionsItemSelected(item)
    }

}