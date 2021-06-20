package com.example.unsplash.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.modal.UrlModal
import kotlinx.android.synthetic.main.image_url_item.view.*

class UnsplashAdapter:RecyclerView.Adapter<UnsplashAdapter.ImageViewHolder>() {
    inner class ImageViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<UrlModal>(){
        override fun areItemsTheSame(oldItem: UrlModal, newItem: UrlModal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UrlModal, newItem: UrlModal): Boolean {
            return oldItem == newItem
        }
    }
    val differ =AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_url_item,parent,false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image =differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(image.urls.regular).into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}