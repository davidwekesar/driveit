package com.android.driveit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.driveit.data.source.network.Car
import com.android.driveit.repository.DriveItRepository
import com.android.driveit.utils.getListOfCars
import kotlinx.coroutines.launch
import timber.log.Timber

class CarListViewModel: ViewModel() {

    private val repository: DriveItRepository = DriveItRepository()

    private val _carList = MutableLiveData<List<Car>>()
    val carList: LiveData<List<Car>> get() = _carList

    init {
        getCarImages()
    }

    private fun getCarImages() {
        viewModelScope.launch {
            try {
                val photos = repository.getCarImages().results
                _carList.value = getListOfCars(photos)
            } catch (e: Exception) {
                Timber.e("Error getting images: ${e.message}")
            }
        }
    }
}