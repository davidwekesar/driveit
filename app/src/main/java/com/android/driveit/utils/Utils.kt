package com.android.driveit.utils

import com.android.driveit.data.source.network.Car
import com.android.driveit.data.source.network.Photo
import kotlin.random.Random


fun getDuration(): String {
    val listOfDurations = listOf("Daily", "Weekly", "Monthly")
    return listOfDurations.random()
}

fun getCarName(): String {
    val listOfCarNames = listOf(
        "Lamborghini Diablo",
        "Ford F-150 Raptor",
        "Ferrari Testarossa",
        "Porsche 911 Carrera",
        "Jensen Interceptor",
        "Lamborghini Huracán",
        "Ferrari 812 Superfast",
        "Jeep Gladiator",
        "Land Rover Defender",
        "Rolls-Royce Wraith",
    )
    return listOfCarNames.random()
}

fun getCarPrice(): Int {
    return Random.nextInt(1000, 5000)
}

fun mapRateToDuration(duration: String): String {
    return when (duration) {
        "Daily" -> {
            "Per day"
        }
        "Weekly" -> {
            "Per week"
        }
        else -> {
            "Per month"
        }
    }
}

fun getListOfCars(photos: List<Photo>): List<Car> {
    val listOfCars = mutableListOf<Car>()
    for (photo in photos) {
        val name = getCarName()
        val price = getCarPrice()
        val url = photo.urls.small
        val duration = getDuration()
        val car = Car(name, price, url, duration)
        listOfCars.add(car)
    }
    return listOfCars
}