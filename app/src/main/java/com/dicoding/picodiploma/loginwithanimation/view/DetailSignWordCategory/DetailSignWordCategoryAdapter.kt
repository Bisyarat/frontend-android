package com.dicoding.picodiploma.loginwithanimation.view.DetailSignWordCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.SignCategory
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemRowCategoryBinding


class DetailSignWordCategoryAdapter : ListAdapter<SignCategory, DetailSignWordCategoryAdapter.MyViewHolder>(DIFF_CALLBACK){


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
        fun bind(signCategory: SignCategory) {
            binding.tvTitleCategory.text = signCategory.titleCategory
            binding.tvProgressCategory.text = "Selesai ${signCategory.progressCategory}%"
            binding.idLinearIndicator.progress = signCategory.progressCategory
//            Glide.with(itemView.context)
//                .load(signCategory.photoUrl)
//                .into(binding.imgItemPhoto)
            signCategory.idPhoto?.let { binding.imgItemPhoto.setImageResource(it) }
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