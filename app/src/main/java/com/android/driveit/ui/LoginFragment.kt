package com.android.driveit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.driveit.R
import com.android.driveit.databinding.FragmentLoginBinding
import com.android.driveit.viewmodels.LoginViewModel
import com.google.android.material.button.MaterialButton

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

        val loginButton: MaterialButton = binding.loginButton
        loginButton.setOnClickListener {
            checkInputFields()
        }

        loginViewModel.loginResponse.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(R.id.homeFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter the correct email or password.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        return binding.root
    }

    private fun checkInputFields() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        if (email.isNotBlank() && password.isNotBlank()) {
            loginViewModel.loginUser(email, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}