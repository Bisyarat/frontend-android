package com.dicoding.picodiploma.loginwithanimation.view.DetailSignWordCategory

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.data.SignCategory
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityAddStoryBinding
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailSignWordCategoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.DetailSignLanguage.DetailSignLanguageActivity
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.home.SignCategoryViewModel
import kotlin.math.log

class DetailSignWordCategoryActivity : AppCompatActivity() {
    companion object {
        const val TOKEN_KEY = "token_key"
    }

    val listGambar = listOf(R.drawable.abc_logo)

    private val detailSignWordCategoryViewModel: DetailSignWordCategoryViewModel by viewModels() {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDetailSignWordCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailSignWordCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvCategory.layoutManager = layoutManager

        //hilangkan garis pemisah
//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvCategory.addItemDecoration(itemDecoration)


        val token = intent.getStringExtra(TOKEN_KEY)


        if (token != null) {
            Log.d(TAG, token)

            //set recycler view
            detailSignWordCategoryViewModel.getAllSubKategori(token).observe(this) { result ->
                run {
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                val message = "Berhasil Perbarui Data"
                                val listKategori = result.data.subKategoriItem
                                val signCategory = listKategori.mapIndexed { index, element ->
                                    SignCategory(listGambar[index], "", element.namaKategori, 10)
                                }
                                setSignCategoryData(signCategory)
                                showToast(message)
                                showLoading(false)
                            }

                            is ResultState.Error -> {
                                showToast(result.error)
                                showLoading(false)
                            }
                        }
                    }
                }

//        setSignCategoryData(listCourseSignCategory)
            }
        }


        DetailSignWordCategoryAdapter.setOnItemClickCallback(object :
            DetailSignWordCategoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: SignCategory) {
                showSelected(data)
            }
        })

    }

    private fun setSignCategoryData(signCategory: List<SignCategory>) {
        val adapter = DetailSignWordCategoryAdapter()
        adapter.submitList(signCategory)
        binding.rvCategory.adapter = adapter
    }

    private fun showSelected(signCategory: SignCategory) {
        val intent = Intent(this, DetailSignLanguageActivity::class.java)
        this.startActivity(intent)

        Toast.makeText(this, "Kamu memilih " + signCategory.titleCategory, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}