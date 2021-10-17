package com.android.driveit.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.driveit.data.source.network.Photo
import com.android.driveit.databinding.ListItemCarBinding
import com.squareup.picasso.Picasso

class CarListAdapter(private val photos: List<Photo>) : RecyclerView.Adapter<CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCarBinding.inflate(inflater, parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size
}

class CarViewHolder(binding: ListItemCarBinding) : RecyclerView.ViewHolder(binding.root) {

    private val carImageView: ImageView = binding.carImageView

    fun bind(photo: Photo) {
        Picasso.get()
            .load(photo.urls.small)
            .into(carImageView)
    }
}