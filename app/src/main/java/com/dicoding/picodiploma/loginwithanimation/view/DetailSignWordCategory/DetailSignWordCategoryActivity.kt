package com.dicoding.picodiploma.loginwithanimation.view.DetailSignWordCategory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.data.SignCategory
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityAddStoryBinding
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailSignWordCategoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.home.SignCategoryViewModel

class DetailSignWordCategoryActivity : AppCompatActivity() {
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
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvCategory.addItemDecoration(itemDecoration)

        //set recycler view
        val listCourseSignCategory = detailSignWordCategoryViewModel.getListCourseSignCategory()
        setSignCategoryData(listCourseSignCategory)

        DetailSignWordCategoryAdapter.setOnItemClickCallback(object: DetailSignWordCategoryAdapter.OnItemClickCallback{
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

    private fun showSelected(signCategory: SignCategory){
        Toast.makeText(this, "Kamu memilih " + signCategory.titleCategory, Toast.LENGTH_SHORT).show()
    }
}