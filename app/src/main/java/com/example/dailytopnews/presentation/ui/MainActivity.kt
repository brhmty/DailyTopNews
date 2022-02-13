package com.example.dailytopnews.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dailytopnews.R
import com.example.dailytopnews.databinding.ActivityMainBinding
import com.example.dailytopnews.presentation.adapter.NewsAdapter
import com.example.dailytopnews.presentation.adapter.SavedNewsAdapter
import com.example.dailytopnews.presentation.adapter.SearchedNewsAdapter
import com.example.dailytopnews.presentation.viewmodel.NewsViewModel
import com.example.dailytopnews.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: NewsViewModelFactory
    @Inject
    lateinit var newsAdapter: NewsAdapter
    @Inject
    lateinit var searchedNewsAdapter: SearchedNewsAdapter
    @Inject
    lateinit var savedNewsAdapter: SavedNewsAdapter
    lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var preferences: SharedPreferences
    private lateinit var newsMenuItem: MenuItem
    private lateinit var savedMenuItem: MenuItem
    private lateinit var infoMenuItem: MenuItem
    private lateinit var infoFragment: Fragment
    private var checked: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewNews) as NavHostFragment
        navController = navHostFragment.navController

        //To navigate when menuitems get clicked --setupWithNavController(navController) is buggy
        newsMenuItem = binding.bnvNews.menu.findItem(R.id.newsFragment)
        newsNavigation()
        savedMenuItem = binding.bnvNews.menu.findItem(R.id.savedNewsFragment)
        savedNewsNavigation()
        infoMenuItem = binding.bnvNews.menu.findItem(R.id.infoFragment)

        preferences = this.getPreferences(Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        preferences.edit().putString(R.string.shared_preference_country.toString() ,item.titleCondensed.toString()).apply()
        navController.navigate(R.id.newsFragment)
        return super.onOptionsItemSelected(item)
    }

    private fun savedNewsNavigation(){
        savedMenuItem.setOnMenuItemClickListener {
            savedMenuItem.isChecked = checked
            if(navController.currentDestination!!.id != savedMenuItem.itemId){
                navController.navigate(R.id.savedNewsFragment)
            }
            true
        }
    }

    private fun newsNavigation(){
        newsMenuItem.setOnMenuItemClickListener {
            newsMenuItem.isChecked = checked
            if(navController.currentDestination!!.id != newsMenuItem.itemId){
                navController.navigate(R.id.newsFragment)}
            true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        when(navController.currentDestination!!.id){
                (newsMenuItem.itemId) ->
                    newsMenuItem.isChecked = checked
                (savedMenuItem.itemId) ->
                    savedMenuItem.isChecked = checked
                (infoMenuItem.itemId) ->{
                    navController.popBackStack()
                    when(viewModel.fragmentSelector){
                        (1) -> newsMenuItem.isChecked = checked
                        (0) -> savedMenuItem.isChecked = checked
                    }
                }
        }
    }

}