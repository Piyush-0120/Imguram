package com.example.imguram.ui.home

import android.media.ImageReader
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.ViewSizeResolver
import com.example.imguram.R
import com.example.imguram.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val storiesViewModel by viewModels<HomeViewModel>()
    private val storiesRecyclerAdapter = StoriesRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.storiesRecylerView.apply {
            layoutManager = LinearLayoutManager(
                    context,RecyclerView.HORIZONTAL,false)
            adapter = storiesRecyclerAdapter
        }

        setUpNav()
        storiesViewModel.fetchTags()
    }

    private fun setUpNav() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        /*
        NOTE : Not using Action Bar
        -------------------ACTION BAR CODE------------------
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_hot, R.id.navigation_top
            )
        )
        // setupActionBarWithNavController(navController, appBarConfiguration)
        -------------------ACTION BAR CODE------------------
        */

        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        // we can also use in onCreate
        storiesViewModel.tags.observe(this){
            it?.forEach { tag->
                val request = ImageRequest.Builder(this)
                    .data("https://i.imgur.com/${tag?.backgroundHash}.jpg")
                    .size(resources.getDimensionPixelSize(R.dimen.story_head_image_size))
                    .build()
                Coil.imageLoader(this).enqueue(request)
            }
            storiesRecyclerAdapter.submitList(it)
        }
    }
}