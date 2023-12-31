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
            val progressCategory = ArrayList<Int>()

            var countStatus = 0
            var countKategori = 0
            var countProgressBar = 0
            var countKata = 0

            Log.d(TAG, token)
            detailSignWordCategoryViewModel.getAllKata(token!!, false, true)
                .observe(this) { result ->
                    run {
                        if (result != null) {
                            when (result) {
                                is ResultState.Loading -> {
                                    showLoading(true)
                                }

                                is ResultState.Success -> {
                                    val listKategori = result.data.kataItem
                                    val listChecked = ArrayList<Boolean?>()
                                    listKategori.forEach {
                                        if (it.riwayatBelajarItem.isEmpty()) {
                                            listChecked.add(false)
                                        } else {
                                            it.riwayatBelajarItem.forEach {
                                                listChecked.add(it.status)
                                            }
                                        }
                                    }

                                    val listTrueStatus =
                                        listChecked.filter { status ->
                                            status == true
                                        }


                                    countKategori = listKategori.size
                                    countStatus = listTrueStatus.size
                                    countProgressBar =
                                        (countStatus * 100) / countKategori
                                    countKata = countProgressBar
                                    progressCategory.add(countKata)

                                    //set recycler view
                                    detailSignWordCategoryViewModel.getAllSubKategori(token).observe(this) { result ->
                                        run {
                                            if (result != null) {
                                                when (result) {
                                                    is ResultState.Loading -> {
                                                        showLoading(true)
                                                    }

                                                    is ResultState.Success -> {
                                                        val listKategori = result.data.subKategoriItem
                                                        val signCategory = listKategori.mapIndexed { index, element ->
                                                            SignCategory(
                                                                null,
                                                                listGambar[index],
                                                                "",
                                                                element.namaKategori,
                                                                progressCategory[index]
                                                            )
                                                        }
                                                        setSignCategoryData(signCategory)
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

                                is ResultState.Error -> {
                                    showToast(result.error)
                                }
                            }
                        }
                    }
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
        val token = intent.getStringExtra(TOKEN_KEY)
        val intent = Intent(this, DetailSignLanguageActivity::class.java)
        intent.putExtra(DetailSignLanguageActivity.TOKEN_KEY, token)
        intent.putExtra(DetailSignLanguageActivity.STATUS_KATEGORI, false)
        intent.putExtra(DetailSignLanguageActivity.STATUS_SUB_KATEGORI, true)
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