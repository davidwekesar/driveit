package com.android.driveit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.driveit.data.source.network.PhotoResult
import com.android.driveit.data.source.network.UserInfo
import com.android.driveit.repository.DriveItRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {

    private val repository: DriveItRepository = DriveItRepository()
    private val _photoResult = MutableLiveData<PhotoResult>()
    val photoResult: LiveData<PhotoResult> get() = _photoResult

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo

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
                _photoResult.value = repository.getCarImages()
            } catch (e: Exception) {
                Timber.e("Error getting images: ${e.message}")
            }
        }
    }
}