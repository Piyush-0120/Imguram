package com.example.imguram.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.imguram.R
import com.example.imguram.databinding.ListItemGalleryImageBinding
import com.example.libimgur.models.Image

class FeedRecyclerAdapter():
    ListAdapter<Image, FeedRecyclerAdapter.FeedViewHolder>(FeedDiffCallback()) {

    class FeedViewHolder(val binding: ListItemGalleryImageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private  class FeedDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            // whether the reference are same
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
            // whether the dataclass are same
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = parent.context.getSystemService(LayoutInflater::class.java)
        val binding=ListItemGalleryImageBinding.inflate(inflater,parent,false)
        return FeedViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val image = getItem(position)
        holder.binding.textView.text = image.title
        holder.binding.imageView.load("https://i.imgur.com/${image.cover}.jpg"){
            placeholder(R.drawable.placeholder_image)
            error(R.drawable.placeholder_image)
        }

    }
}