package com.dicoding.picodiploma.loginwithanimation.view.detailStory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    companion object{
        const val DATA_STORY = "data_story"
    }

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataStory = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra<DetailStory>(DATA_STORY, DetailStory::class.java)
        } else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<DetailStory>(DATA_STORY)
        }

        if (dataStory != null){
            binding.tvTitle.text = dataStory.name
            binding.tvDescription.text = dataStory.description
            Glide.with(this@DetailStoryActivity)
                .load(dataStory.photoUrl)
                .into(binding.photoStory)
        }
    }
}