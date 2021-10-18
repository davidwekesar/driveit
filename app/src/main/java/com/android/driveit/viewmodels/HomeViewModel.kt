package com.android.driveit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.driveit.data.source.network.Car
import com.android.driveit.data.source.network.Photo
import com.android.driveit.data.source.network.PhotoResult
import com.android.driveit.data.source.network.UserInfo
import com.android.driveit.repository.DriveItRepository
import com.android.driveit.utils.getCarName
import com.android.driveit.utils.getCarPrice
import com.android.driveit.utils.getDuration
import com.android.driveit.utils.getListOfCars
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {

    private val repository: DriveItRepository = DriveItRepository()

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo

    private val _carList = MutableLiveData<List<Car>>()
    val carList: LiveData<List<Car>> get() = _carList

    init {
        getUserInfo()
        getCarImages()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            try {
                _userInfo.value = repository.getUserInfo()
            } catch (e: Exception) {
                Timber.e("Error getting user info: ${e.message}")
            }
        }
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