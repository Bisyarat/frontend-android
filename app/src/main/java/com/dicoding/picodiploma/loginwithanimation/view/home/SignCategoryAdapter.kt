package com.dicoding.picodiploma.loginwithanimation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.SignCategory
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListKategoriItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemRowCategoryBinding


class SignCategoryAdapter : ListAdapter<SignCategory, SignCategoryAdapter.MyViewHolder>(DIFF_CALLBACK){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(review)
        }
    }

    class MyViewHolder(private val binding: ItemRowCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listKategoriItem: SignCategory) {
            binding.tvTitleCategory.text = listKategoriItem.titleCategory
            binding.tvProgressCategory.text = "Selesai ${listKategoriItem.progressCategory}%"
            binding.idLinearIndicator.progress = listKategoriItem.progressCategory
//            Glide.with(itemView.context)
//                .load(signCategory.photoUrl)
//                .into(binding.imgItemPhoto)
            binding.imgItemPhoto.setImageResource(listKategoriItem.idPhoto)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SignCategory>() {
            override fun areItemsTheSame(oldItem: SignCategory, newItem: SignCategory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SignCategory, newItem: SignCategory): Boolean {
                return oldItem == newItem
            }
        }

        private lateinit var onItemClickCallback: OnItemClickCallback

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SignCategory)
    }


}