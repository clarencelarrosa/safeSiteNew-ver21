package com.example.safesite.search.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.safesite.R
import com.example.safesite.databinding.ItemPhotoListBinding
import com.example.safesite.search.data.BirdPhoto

@GlideModule
class BirdPhotoAdapter (private val listener: OnItemClickListener) :
    PagingDataAdapter<BirdPhoto, BirdPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
            val binding=
                ItemPhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item!=null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(photo: BirdPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    // .override(500,500)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
                textViewUserName.text = photo.user.username //print the creator
               // textViewLikes.text = photo.likes.toString()
            }

        }
    }
    interface OnItemClickListener {
        fun onItemClick(photo: BirdPhoto)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<BirdPhoto>() {
            override fun areItemsTheSame(oldItem: BirdPhoto, newItem: BirdPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BirdPhoto, newItem: BirdPhoto) =
                oldItem == newItem
        }
    }

}