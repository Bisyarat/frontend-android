package com.dicoding.picodiploma.loginwithanimation.view.addStory

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityAddStoryBinding

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding

    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            Toast.makeText(this@AddStoryActivity, "Menjalankan Gallery", Toast.LENGTH_SHORT).show()
            startGallery()
        }
    }

    private fun startGallery(){
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {uri: Uri? ->
        if (uri != null){
            currentImageUri = uri
            showImage()
        } else{
            Log.d("Photo Picker", "No Media Selected")
        }
    }

    private fun showImage(){
        currentImageUri.let {
            Log.d("Image uri", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }
}