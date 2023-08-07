package com.example.filmes

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.filmes.databinding.ActivityMainBinding
import com.example.filmes.ui.filmes.MovieFragment
import com.example.filmes.ui.login.LoggedInViewModel
import com.example.filmes.ui.login.LoginActivity
import com.example.filmes.ui.perfil.FavoriteFragment
import com.example.filmes.ui.search.SearchMoviesFragment
import com.example.filmes.ui.series.SeriesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val loggedInViewModel: LoggedInViewModel by viewModels()
    private var actual = "Filmes"

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(MovieFragment())


        binding.imageViewLogoutM.setOnClickListener{
            loggedInViewModel.logOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

           binding.navView.setOnItemSelectedListener {

               when( it.itemId ) {
                   R.id.navigation_movie -> { actual = "Filmes"
                       replaceFragment(MovieFragment())
                       binding.searchMovies.visibility = View.VISIBLE
                       binding.imageViewLogoutM.visibility = View.GONE }
                   R.id.navigation_serie -> { actual = "Séries"
                       replaceFragment(SeriesFragment())
                       binding.searchMovies.visibility = View.VISIBLE
                       binding.imageViewLogoutM.visibility = View.GONE }
                   R.id.navigation_favorites -> { actual = "Favoritos"
                       replaceFragment(FavoriteFragment())
                   binding.searchMovies.visibility = View.GONE
                   binding.imageViewLogoutM.visibility = View.VISIBLE }
                   else -> {}
               }
               true
           }

            binding.searchMovies.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if ( newText.isNullOrEmpty() ) {
                        if ( actual == "Filmes" ) {
                            replaceFragment(MovieFragment())
                            hideKeyboard()
                        } else {
                            replaceFragment(SeriesFragment())
                            hideKeyboard() } }
                    else {
                        if (actual == "Filmes") {
                            replaceFragment(SearchMoviesFragment(newText, "movies"))
                        }else {
                            replaceFragment(SearchMoviesFragment(newText, "series")) } }
                    return true
                }
            })


        binding.searchMovies.setOnCloseListener {

            if (actual == "Filmes") {
                replaceFragment(MovieFragment())
                hideKeyboard()
            }else {
                replaceFragment(SeriesFragment())
                hideKeyboard()
            }

            hideKeyboard()
            true
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchMovies.getWindowToken(), 0)
    }

    private fun replaceFragment( fragment: Fragment ){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}