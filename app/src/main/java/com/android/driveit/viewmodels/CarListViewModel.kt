package com.android.driveit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.driveit.data.source.network.PhotoResult
import com.android.driveit.repository.DriveItRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class CarListViewModel: ViewModel() {

    private val repository: DriveItRepository = DriveItRepository()
    private val _photoResult = MutableLiveData<PhotoResult>()
    val photoResult: LiveData<PhotoResult> get() = _photoResult

    init {
        getCarImages()
    }

    private fun getCarImages() {
        viewModelScope.launch {
            try {
                _photoResult.value = repository.getCarImages()
            } catch (e: Exception) {
                Timber.e("Error getting images: ${e.message}")
            }
        }
    }
}