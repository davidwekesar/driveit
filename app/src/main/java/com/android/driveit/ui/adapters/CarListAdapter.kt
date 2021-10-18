package com.android.driveit.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.driveit.R
import com.android.driveit.data.source.network.Car
import com.android.driveit.databinding.ListItemCarBinding
import com.android.driveit.utils.mapRateToDuration
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class CarListAdapter(
    private val cars: List<Car>,
    private val context: Context
) : RecyclerView.Adapter<CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCarBinding.inflate(inflater, parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(cars[position], context)
    }

    override fun getItemCount(): Int = cars.size
}

class CarViewHolder(binding: ListItemCarBinding) : RecyclerView.ViewHolder(binding.root) {

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
        textCarPrice.text = String.format(context.getString(R.string.currency), price)
        textDuration.text = car.duration
        textRate.text = mapRateToDuration(car.duration)
    }
}