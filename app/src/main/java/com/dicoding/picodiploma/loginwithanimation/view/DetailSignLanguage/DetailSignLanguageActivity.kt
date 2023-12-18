package com.dicoding.picodiploma.loginwithanimation.view.DetailSignLanguage

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.data.SignCategory
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailSignWordCategoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.DetailExercise.DetailExerciseActivity
import com.dicoding.picodiploma.loginwithanimation.view.DetailSignWordCategory.DetailSignWordCategoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory

class DetailSignLanguageActivity : AppCompatActivity() {
    companion object {
        const val TOKEN_KEY = "token_key"
        const val STATUS_KATEGORI = "status_kategori"
        const val STATUS_SUB_KATEGORI = "status_sub_kategori"
    }

    private val detailSignLanguageViewModel: DetailSignLanguageViewModel by viewModels() {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDetailSignWordCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listIdKata = ArrayList<Int>()

        binding = ActivityDetailSignWordCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvCategory.layoutManager = layoutManager

        //hilangkan garis pemisah
//        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
//        binding.rvCategory.addItemDecoration(itemDecoration)

        //set recycler view

        val token = intent.getStringExtra(TOKEN_KEY)
        val statusKategori = intent.getBooleanExtra(STATUS_KATEGORI, false)
        val statusSubKategori = intent.getBooleanExtra(STATUS_SUB_KATEGORI, false)


        if (token != null) {
            Log.d(ContentValues.TAG, token)

            //set recycler view
            detailSignLanguageViewModel.getAllKata(token, statusKategori, statusSubKategori)
                .observe(this) { result ->
                    run {
                        if (result != null) {
                            when (result) {
                                is ResultState.Loading -> {
                                    showLoading(true)
                                }

                                is ResultState.Success -> {
                                    val message = "Berhasil Perbarui Data"
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
                                    val signCategory = listKategori.mapIndexed { index, element ->
                                        listIdKata.add(element.id!!)
                                        SignCategory(
                                            element.id,
                                            null,
                                            "",
                                            element.kata!!,
                                            10,
                                            listChecked[index]
                                        )
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

        DetailSignLanguageAdapter.setOnItemClickCallback(object :
            DetailSignLanguageAdapter.OnItemClickCallback {
            override fun onItemClicked(data: SignCategory) {
                showSelected(data, statusKategori, statusSubKategori)
            }
        })

    }

    private fun setSignCategoryData(signCategory: List<SignCategory>) {
        val adapter = DetailSignLanguageAdapter()
        adapter.submitList(signCategory)
        binding.rvCategory.adapter = adapter
    }

    private fun showSelected(signCategory: SignCategory, namaKategori:Boolean = false, namaSubKategori:Boolean = false) {
        val intent = Intent(this, DetailExerciseActivity::class.java)
        intent.putExtra(DetailExerciseActivity.ID_KEY, signCategory.idKata)
        intent.putExtra(DetailExerciseActivity.STATUS_KATEGORI, namaKategori)
        intent.putExtra(DetailExerciseActivity.STATUS_SUB_KATEGORI, namaSubKategori)
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