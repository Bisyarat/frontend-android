package com.dicoding.picodiploma.loginwithanimation.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemRowStoryBinding

//class StoryAdapter : ListAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK){
class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataStory = getItem(position)
        holder.bind(dataStory!!)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(dataStory)
        }
    }

    class MyViewHolder(private val binding: ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(listStoryItem: ListStoryItem){
            binding.apply {
                tvTitle.text = listStoryItem.name
                tvDescription.text = listStoryItem.description
                Glide.with(itemView.context)
                    .load(listStoryItem.photoUrl)
                    .into(photoStory)
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }

        private lateinit var onItemClickCallback: OnItemClickCallback

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
            this.onItemClickCallback = onItemClickCallback
        }

    }

    interface OnItemClickCallback{
        fun onItemClicked(item: ListStoryItem)
    }
}