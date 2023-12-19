package com.dicoding.picodiploma.loginwithanimation.view.DetailExercise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailExerciseBinding
import com.dicoding.picodiploma.loginwithanimation.utils.getImageUri
import com.dicoding.picodiploma.loginwithanimation.view.DetailSignLanguage.DetailSignLanguageActivity
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity


class DetailExerciseActivity : AppCompatActivity() {
    var exoPlayer: SimpleExoPlayer? = null

    companion object {
        const val TOKEN_KEY = "token_key"
        const val ID_KEY = "id_key"
        const val STATUS_KATEGORI = "status_kategori"
        const val STATUS_SUB_KATEGORI = "status_sub_kategori"
    }

    private val viewModel by viewModels<DetailExerciseViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDetailExerciseBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val statusKategori = intent.getBooleanExtra(STATUS_KATEGORI, false)
        val statusSubKategori = intent.getBooleanExtra(STATUS_SUB_KATEGORI, false)

        var id = intent.getIntExtra(ID_KEY, 0)

        val token = intent.getStringExtra(TOKEN_KEY)

        if (id != null) {
            runDetailCourse(id)
        }

        binding.galleryButton.setOnClickListener {
            showToast("Menjalankan Gallery")
            startGallery()
        }

        binding.cameraButton.setOnClickListener {
            showToast("Menjalankan Camera")
            startCamera()
        }

        binding.btnNextCourse.setOnClickListener {
            id = id + 1
            if (statusKategori) {
                if (id <= 9) {
                    if (id != null) {
                        runDetailCourse(id)
                    }
                } else {
                    id = 9
                }
            } else if (statusSubKategori) {
                if (id <= 15) {
                    if (id != null) {
                        runDetailCourse(id)
                    }
                } else {
                    id = 15
                }
            }
        }

        binding.btnPreviousCourse.setOnClickListener {
            id = id - 1
            if (statusKategori) {
                if (id > 0) {
                    if (id != null) {
                        runDetailCourse(id)
                    }
                } else {
                    id = 1
                }
            } else if (statusSubKategori) {
                if (id > 9) {
                    if (id != null) {
                        runDetailCourse(id)
                    }
                } else {
                    id = 10
                }
            }
        }

        binding.checkButton.setOnClickListener {
            var link = "https://youtu.be/8FxecFFolfo?si=Xjvrj8vWqV3ScDwt"
            if (token != null) {
                viewModel.createRiwayatBelajar(token, true, link, id).observe(this) { result ->
                    run {
                        if (result != null) {
                            when (result) {
                                is ResultState.Loading -> {
                                    showLoading(true)
                                }

                                is ResultState.Success -> {
                                    val status = result.data.riwayatBelajar!!.status
                                    showToast("Berhasil membuat riwayat belajar {$status}")
                                }

                                is ResultState.Error -> {
                                    showToast(result.error)
                                    showLoading(false)
                                }
                            }
                        }
                    }
                }
            }

        }


    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

//    private fun uploadImage(token: String){
//        currentImageUri?.let { uri ->
//            val imageFile = uriToFile(uri, this).reduceFileImage()
//            Log.d("Image File", "showImage: ${imageFile.path}")
//
//            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "photo",
//                imageFile.name,
//                requestImageFile
//            )
//
//            lifecycleScope.launch {
//                viewModel.addNewStory(token,multipartBody, requestBody).observe(this@DetailExerciseActivity){ result ->
//                    if (result != null) {
//                        when (result) {
//                            is ResultState.Loading -> {
//                                showLoading(true)
//                            }
//                            is ResultState.Success -> {
//                                showToast(result.data.message)
//                                showLoading(false)
//                                alertBerhasil()
//                            }
//
//                            is ResultState.Error -> {
//                                showToast(result.error)
//                                showLoading(false)
//                            }
//                        }
//                    }
//                }
//            }
//        } ?: showToast(getString(R.string.empty_image_warning))
//    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
//            showImage()
        } else {
            Log.d("Photo Picker", "No Media Selected")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.CaptureVideo()
    ) { isSuccess ->
        if (isSuccess) {
            showVideo()
        }
    }

//    private fun showImage() {
//        currentImageUri.let {
//            Log.d("Image uri", "showImage: $it")
//            binding.previewImageView.setImageURI(it)
//        }
//    }

    private fun showVideo() {
        currentImageUri.let {
            Log.d("Video uri", "showVideo: $it")
            val videoItem = MediaItem.fromUri(it!!)

            val player = ExoPlayer.Builder(this).build().also { exoPlayer ->
                exoPlayer.setMediaItem(videoItem)
                exoPlayer.prepare()
            }

            binding.playerView.player = player
        }
    }

    private fun alertBerhasil() {
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Horee!! Story berhasil dibuat!")
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    //    private fun showLoading(isLoading: Boolean) {
//        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun runDetailCourse(id: Int) {
        if (id != 0) {
            viewModel.getKataById(id).observe(this) { result ->
                run {
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                val detailKata = result.data.kataByIdItem

                                //putar video
                                val videoItem = MediaItem.fromUri(detailKata.urlVideo!!)
                                val player = ExoPlayer.Builder(this).build().also { exoPlayer ->
                                    exoPlayer.setMediaItem(videoItem)
                                    exoPlayer.playWhenReady = true
                                    exoPlayer.prepare()
                                }

                                binding.playerViewCourse.player = player
                                //End putar video

                                binding.tvTitleExercise.text = detailKata.kata
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
        }
    }
}