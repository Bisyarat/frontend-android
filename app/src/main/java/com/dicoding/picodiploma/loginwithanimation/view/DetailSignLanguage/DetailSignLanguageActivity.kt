package com.dicoding.picodiploma.loginwithanimation.view.DetailSignLanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.data.SignCategory
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailSignWordCategoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory

class DetailSignLanguageActivity : AppCompatActivity() {
    private val detailSignLanguageViewModel: DetailSignLanguageViewModel by viewModels() {
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
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvCategory.addItemDecoration(itemDecoration)

        //set recycler view
        val listCourseSignCategory = detailSignLanguageViewModel.getListCourseSignCategory()
        setSignCategoryData(listCourseSignCategory)

        DetailSignLanguageAdapter.setOnItemClickCallback(object: DetailSignLanguageAdapter.OnItemClickCallback{
            override fun onItemClicked(data: SignCategory) {
                showSelected(data)
            }
        })

    }

    private fun setSignCategoryData(signCategory: List<SignCategory>) {
        val adapter = DetailSignLanguageAdapter()
        adapter.submitList(signCategory)
        binding.rvCategory.adapter = adapter
    }

    private fun showSelected(signCategory: SignCategory){
        Toast.makeText(this, "Kamu memilih " + signCategory.titleCategory, Toast.LENGTH_SHORT).show()
    }
}