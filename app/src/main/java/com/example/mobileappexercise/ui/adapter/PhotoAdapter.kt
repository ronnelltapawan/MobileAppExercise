package com.example.mobileappexercise.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mobileappexercise.data.Photo
import com.example.mobileappexercise.databinding.ItemPhotoBinding
import com.example.mobileappexercise.util.PAGE_SIZE
import javax.inject.Inject

class PhotoAdapter @Inject constructor(
    private val customCallback: CustomCallback
) : ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemBinding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))

        // this enables the pagination feature
        // the condition is to simply request for more items
        // when the adapter decides to load the
        // second to the last item of the current list
        // also, the items must be greater than the initial size first
        if (itemCount >= PAGE_SIZE && position > (itemCount - 2)) {
            customCallback.onLoadMore()
        }
    }

    inner class PhotoViewHolder(private val itemBinding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(photo: Photo) {
            itemBinding.imgPhoto
                .load("https://farm${photo.farm}.static.flickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg") {
                    crossfade(true)
                    placeholder(ColorDrawable(Color.GRAY))
                }
        }
    }

    // this is the DiffUtil class used by the ListAdapter and
    // is responsible for the background computation
    // between two lists
    private class DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    // this is the interface that needs to be implemented
    // by the components that will use this adapter
    // right now it only has the pagination callback
    // but I can add an onClick event here in case it's needed
    interface CustomCallback {
        fun onLoadMore()
    }
}