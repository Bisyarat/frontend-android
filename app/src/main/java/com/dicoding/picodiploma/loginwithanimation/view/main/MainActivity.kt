package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemRowStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.detailStory.DetailStory
import com.dicoding.picodiploma.loginwithanimation.view.detailStory.DetailStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
//    private lateinit var bindingDetailStory: ItemRowStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        bindingDetailStory = ItemRowStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        setupView()
        setListStory()
//        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.show()
    }

//    private fun setupAction() {
//        binding.logoutButton.setOnClickListener {
//            viewModel.logout()
//        }
//    }

    private fun setListStory() {
        viewModel.getStories().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }
                    is ResultState.Success -> {
                        showToast(result.data.message!!)
                        setAdapterListStories(result.data.listStory)
                        showLoading(false)
                    }

                    is ResultState.Error -> {
                        showToast(result.error)
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun setAdapterListStories(listItem: List<ListStoryItem>){
        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStories.addItemDecoration(itemDecoration)

        val adapter = StoryAdapter()
        adapter.submitList(listItem)
        binding.rvStories.adapter = adapter

        StoryAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(item: ListStoryItem) {
                showSelectedStory(item)
            }

        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showSelectedStory(item: ListStoryItem){
        Toast.makeText(this@MainActivity , item.name, Toast.LENGTH_SHORT).show()

        val detailStory = DetailStory(
            item.photoUrl!!,
            item.name!!,
            item.description!!
        )

        val intentWithParcelable = Intent(this@MainActivity, DetailStoryActivity::class.java)
        intentWithParcelable.putExtra(DetailStoryActivity.DATA_STORY, detailStory)

        this.startActivity(intentWithParcelable, ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity).toBundle())

//        val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this@MainActivity,
//            Pair(bindingDetailStory.photoStory, "thumbnail"),
//            Pair(bindingDetailStory.tvTitle, "title"),
//            Pair(bindingDetailStory.tvDescription, "description")
//        )
//
//        this.startActivity(intentWithParcelable, optionsCompat.toBundle())
    }

}