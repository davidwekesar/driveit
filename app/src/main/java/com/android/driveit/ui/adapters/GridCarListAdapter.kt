package com.android.driveit.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.driveit.R
import com.android.driveit.data.source.network.Car
import com.android.driveit.databinding.GridListItemCarBinding
import com.android.driveit.utils.mapRateToDuration
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class GridCarListAdapter(
    private val cars: List<Car>,
    private val context: Context
) : RecyclerView.Adapter<GridCarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridCarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GridListItemCarBinding.inflate(inflater, parent, false)
        return GridCarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridCarViewHolder, position: Int) {
        holder.bind(cars[position], context)
    }

    override fun getItemCount(): Int = cars.size
}

class GridCarViewHolder(binding: GridListItemCarBinding) : RecyclerView.ViewHolder(binding.root) {

    private val carImageView: ImageView = binding.carImageView
    private val textCarName: TextView = binding.textCarName
    private val textCarPrice: TextView = binding.textCarPrice
    private val textDuration: TextView = binding.textDuration
    private val textRate: TextView = binding.textRate

    fun bind(car: Car, context: Context) {
        Picasso.get()
            .load(car.url)
            .into(carImageView)

        textCarName.text = car.name
        val price = NumberFormat.getNumberInstance(Locale.getDefault()).format(car.price)
        textCarPrice.text = String.format(context.getString(R.string.currency_format), price)
        textDuration.text = car.duration
        textRate.text = mapRateToDuration(car.duration)
    }
}