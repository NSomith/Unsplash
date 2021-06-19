package com.example.unsplash.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.modal.UrlModal

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
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}