package com.android.driveit.ui

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
import com.google.android.material.button.MaterialButton

data class UserInput(val email: String, val password: String)

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        initOnClickListeners()

        initObservers()

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

        loginViewModel.firebaseLoginStatus.observe(viewLifecycleOwner, { firebaseLoginStatus ->
            if (firebaseLoginStatus == FirebaseLoginStatus.SUCCESS) {
                navigateToHomeFragment()
            } else {
                binding.editTextEmail.error = "Please enter the correct email or password."
            }
        })
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
            email.isBlank() && password.isBlank() -> {
                editTextEmail.error = "Email Required."
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