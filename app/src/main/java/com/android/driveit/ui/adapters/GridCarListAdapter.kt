package com.android.driveit.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.driveit.data.source.network.Photo
import com.android.driveit.databinding.GridListItemCarBinding
import com.squareup.picasso.Picasso

class GridCarListAdapter(private val photos: List<Photo>) : RecyclerView.Adapter<GridCarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridCarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GridListItemCarBinding.inflate(inflater, parent, false)
        return GridCarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridCarViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size
}

class GridCarViewHolder(binding: GridListItemCarBinding) : RecyclerView.ViewHolder(binding.root) {

    private val carImageView: ImageView = binding.carImageView

    fun bind(photo: Photo) {
        Picasso.get()
            .load(photo.urls.small)
            .into(carImageView)
    }
}