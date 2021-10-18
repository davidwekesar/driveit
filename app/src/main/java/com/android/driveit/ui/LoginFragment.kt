package com.android.driveit.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.android.driveit.R
import com.android.driveit.databinding.FragmentLoginBinding
import com.android.driveit.viewmodels.FirebaseLoginStatus
import com.android.driveit.viewmodels.LoginViewModel
import com.android.driveit.viewmodels.ReqResApiStatus
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.button.MaterialButton

data class UserInput(val email: String, val password: String)

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var loginButton: MaterialButton
    private lateinit var fireLoginButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        initOnClickListeners()

        initObservers()

        loginButton = binding.loginButton
        fireLoginButton = binding.fireLoginButton
        bindProgressButton(loginButton)
        bindProgressButton(fireLoginButton)

        loginButton.attachTextChangeAnimator()
        fireLoginButton.attachTextChangeAnimator()

        return binding.root
    }

    private fun initOnClickListeners() {
        val loginButton: MaterialButton = binding.loginButton
        loginButton.setOnClickListener {
            loginUser()
        }

        val fireLoginButton: MaterialButton = binding.fireLoginButton
        fireLoginButton.setOnClickListener {
            loginFirebaseUser()
        }
    }

    private fun initObservers() {
        initLoginResponseObserver()

        initFirebaseLoginStatusObserver()

        initReqResApiStatusObserver()

        initLoginButtonProgressObserver()

        initFireLoginButtonProgressObserver()
    }

    private fun initLoginResponseObserver() {
        loginViewModel.loginResponse.observe(viewLifecycleOwner, {
            if (it != null) {
                navigateToHomeFragment()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter the correct email or password.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initFirebaseLoginStatusObserver() {
        loginViewModel.firebaseLoginStatus.observe(viewLifecycleOwner, { firebaseLoginStatus ->
            if (firebaseLoginStatus == FirebaseLoginStatus.SUCCESS) {
                navigateToHomeFragment()
            } else {
                binding.editTextEmail.error = "Please enter the correct email or password."
            }
        })
    }

    private fun initReqResApiStatusObserver() {
        loginViewModel.reqResApiStatus.observe(viewLifecycleOwner, { reqResApiStatus ->
            when (reqResApiStatus) {
                ReqResApiStatus.LOADING -> {
                    loginViewModel.showLoginButtonProgress()
                }
                ReqResApiStatus.DONE -> {
                    loginViewModel.hideLoginButtonProgress()
                }
                else -> {
                    loginViewModel.hideLoginButtonProgress()
                }
            }
        })
    }

    private fun initLoginButtonProgressObserver() {
        loginViewModel.showLoginButtonProgress.observe(viewLifecycleOwner, { showProgress ->
            if (showProgress) {
                loginButton.showProgress {
                    progressColor = Color.WHITE
                }
            } else {
                loginButton.hideProgress(getString(R.string.log_in))
            }
        })
    }

    private fun initFireLoginButtonProgressObserver() {
        loginViewModel.showFireLoginButtonProgress.observe(viewLifecycleOwner, { showProgress ->
            if (showProgress) {
                fireLoginButton.showProgress {
                    progressColor = Color.BLACK
                }
            } else {
                fireLoginButton.hideProgress(getString(R.string.fire_login))
            }
        })
    }

    private fun loginUser() {
        if (!isEmailAndPasswordEmpty()) {
            val (email, password) = getUserEmailAndPassword()
            loginViewModel.loginUser(email, password)
        }
    }

    private fun loginFirebaseUser() {
        if (!isEmailAndPasswordEmpty()) {
            val (email, password) = getUserEmailAndPassword()
            loginViewModel.fireLoginUser(email, password)
        }
    }

    private fun navigateToHomeFragment() {
        val navOptions = NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
        findNavController().navigate(R.id.homeFragment, null, navOptions)
    }

    private fun isEmailAndPasswordEmpty(): Boolean {
        val editTextEmail = binding.editTextEmail
        val editTextPassword = binding.editTextPassword
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        return when {
            email.isBlank() -> {
                editTextEmail.error = "Email Required."
                true
            }
            password.isBlank() -> {
                editTextPassword.error = "Password Required."
                true
            }
            else -> {
                false
            }
        }
    }

    private fun getUserEmailAndPassword(): UserInput {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        return UserInput(email, password)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}