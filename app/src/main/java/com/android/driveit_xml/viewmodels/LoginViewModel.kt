package com.android.driveit_xml.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.driveit_xml.data.source.network.LoginResponse
import com.android.driveit_xml.repository.Repository
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel : ViewModel() {

    private val repository = Repository()

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginResponse.value = repository.loginUser(email, password)
            } catch (e: Exception) {
                Timber.e("Error logging in user: ${e.message}")
            }
        }
    }
}