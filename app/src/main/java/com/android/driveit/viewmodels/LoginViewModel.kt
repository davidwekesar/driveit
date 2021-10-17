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

class LoginViewModel : ViewModel() {

    private val repository = DriveItRepository()
    private var auth: FirebaseAuth = Firebase.auth

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    private val _firebaseLoginStatus = MutableLiveData<FirebaseLoginStatus>()
    val firebaseLoginStatus: LiveData<FirebaseLoginStatus> get() = _firebaseLoginStatus

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginResponse.value = repository.loginUser(email, password)
            } catch (e: Exception) {
                Timber.e("Error logging in user: ${e.message}")
            }
        }
    }

    fun fireLoginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _firebaseLoginStatus.value = FirebaseLoginStatus.SUCCESS
            } else {
                _firebaseLoginStatus.value = FirebaseLoginStatus.FAIL
            }
        }
    }
}