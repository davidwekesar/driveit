package com.android.driveit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.driveit.data.source.network.LoginResponse
import com.android.driveit.repository.DriveItRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import timber.log.Timber

enum class FirebaseLoginStatus { SUCCESS, FAIL }

enum class ReqResApiStatus {LOADING, DONE, ERROR}

class LoginViewModel : ViewModel() {

    private val repository = DriveItRepository()
    private var auth: FirebaseAuth = Firebase.auth

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    private val _firebaseLoginStatus = MutableLiveData<FirebaseLoginStatus>()
    val firebaseLoginStatus: LiveData<FirebaseLoginStatus> get() = _firebaseLoginStatus

    private val _reqResApiStatus = MutableLiveData<ReqResApiStatus>()
    val reqResApiStatus: LiveData<ReqResApiStatus> get() = _reqResApiStatus

    private val _showLoginButtonProgress = MutableLiveData<Boolean>()
    val showLoginButtonProgress: LiveData<Boolean> get() = _showLoginButtonProgress

    private val _showFireLoginButtonProgress = MutableLiveData<Boolean>()
    val showFireLoginButtonProgress: LiveData<Boolean> get() = _showFireLoginButtonProgress

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                _reqResApiStatus.value = ReqResApiStatus.LOADING
                _loginResponse.value = repository.loginUser(email, password)
                _reqResApiStatus.value = ReqResApiStatus.DONE
            } catch (e: Exception) {
                _reqResApiStatus.value = ReqResApiStatus.ERROR
                Timber.e("Error logging in user: ${e.message}")
            }
        }
    }

    fun fireLoginUser(email: String, password: String) {
        showFireLoginButtonProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                hideFireLoginButtonProgress()
                _firebaseLoginStatus.value = FirebaseLoginStatus.SUCCESS
            } else {
                hideFireLoginButtonProgress()
                _firebaseLoginStatus.value = FirebaseLoginStatus.FAIL
            }
        }
    }

    fun showLoginButtonProgress() {
        _showLoginButtonProgress.value = true
    }

    fun hideLoginButtonProgress() {
        _showLoginButtonProgress.value = false
    }

    private fun showFireLoginButtonProgress() {
        _showFireLoginButtonProgress.value = true
    }

    private fun hideFireLoginButtonProgress() {
        _showFireLoginButtonProgress.value = false
    }
}