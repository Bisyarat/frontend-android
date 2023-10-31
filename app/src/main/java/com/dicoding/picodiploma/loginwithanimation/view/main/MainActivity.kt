package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.adapter.LoadingStateAdapter
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.addStory.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.detailStory.DetailStory
import com.dicoding.picodiploma.loginwithanimation.view.detailStory.DetailStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.map.MapsActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else{
                setupView()
//                setListStory(user.token)
                setAdapterListStories(user.token)
                fabOnClick()
                swipeRefreshLayout(user.token)
                Log.d(TAG, "Token saved: ${user.token}")
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_map ->  {
                showToast("Menjalankan Map")
                val intent = Intent(this@MainActivity, MapsActivity::class.java)
                startActivity(intent)
            }
            R.id.action_logout ->  {
                viewModel.logout()
            }
        }

        return super.onOptionsItemSelected(item)
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

//    private fun setListStory(token: String) {
//        viewModel.getStories(token).observe(this) { result ->
//            if (result != null) {
//                when (result) {
//                    is ResultState.Loading -> {
//                        showLoading(true)
//                    }
//                    is ResultState.Success -> {
//                        showToast("Berhasil Update Story")
//                        setAdapterListStories(result.data.listStory)
//                        showLoading(false)
//                    }
//
//                    is ResultState.Error -> {
//                        showToast(result.error)
//                        showLoading(false)
//                    }
//                }
//            }
//        }
//    }

    private fun setAdapterListStories(token: String){
        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStories.addItemDecoration(itemDecoration)

        val adapter = StoryAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.getStories(token).observe(this){
            adapter.submitData(lifecycle, it)
        }

        StoryAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(item: ListStoryItem) {
                showSelectedStory(item)
            }
        })
    }

    private fun showToast(message: String?) {
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
    }

    private fun fabOnClick(){
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddStoryActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun swipeRefreshLayout(token: String){
        binding.swipeRefresh.setOnRefreshListener( object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                binding.swipeRefresh.isRefreshing = true
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.swipeRefresh.isRefreshing = false
//                    setListStory(token)
                    setAdapterListStories(token)
                    Log.d(TAG, "Token : $token")
                }, 1000)
            }

        })
    }

}