package com.karyadika.storyapp.ui.main.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karyadika.storyapp.R
import com.karyadika.storyapp.data.local.room.StoryModel
import com.karyadika.storyapp.databinding.ItemStoryBinding
import com.karyadika.storyapp.ui.main.detail.DetailActivity


class StoryAdapter : PagingDataAdapter<StoryModel, StoryAdapter.MyViewHolder>(DIFF_CALLBACK){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }


    inner class MyViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoryModel) {
            binding.apply {
                Glide.with(itemView).load(data.photoUrl).into(imgStories)
                tvName.text = data.name
                tvDesc.text = data.description
                tvCreateDate.text = binding.root.resources.getString(R.string.created_add, data.createdAt)
                //data.createdAt.withDateFormat()

                imgStories.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, data)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryModel>() {
            override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}

/*
class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val storiesData = ArrayList<ListStoryItem>()

    fun setData(items: List<ListStoryItem>){
        storiesData.clear()
        storiesData.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem){
            binding.apply {
                Glide.with(itemView).load(data.photoUrl).into(imgStories)
                tvName.text = data.name
                tvDesc.text = data.description
                tvCreateDate.text = binding.root.resources.getString(R.string.created_add, data.createdAt)

                imgStories.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, data)
                    it.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storiesData[position])
    }

    override fun getItemCount() = storiesData.size


}*/
