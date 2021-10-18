package com.android.driveit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.driveit.R
import com.android.driveit.databinding.FragmentHomeBinding
import com.android.driveit.ui.adapters.CarListAdapter
import com.android.driveit.viewmodels.HomeViewModel
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupOnClickListeners()

        viewModel.userInfo.observe(viewLifecycleOwner, { userInfo ->
            userInfo?.let {
                binding.textFirstName.text = it.data.firstName
                setProfileImage(it.data.avatar)
            }
        })

        viewModel.photoResult.observe(viewLifecycleOwner, { photoResult ->
            photoResult?.let {
                val mainCarImageView: ImageView = binding.mainCarImageView
                Picasso.get().load(it.results[0].urls.small).into(mainCarImageView)
                val carListAdapter = CarListAdapter(it.results)
                val recyclerView = binding.horizontalCarList
                recyclerView.adapter = carListAdapter
            }
        })

        return binding.root
    }

    private fun setupOnClickListeners() {
        val textMyGarage: TextView = binding.textMyGarage
        textMyGarage.setOnClickListener {
            navigateToCarListFragment()
        }

        val btnAvailableCars: ImageButton = binding.btnAvailableCars
        btnAvailableCars.setOnClickListener {
            navigateToCarListFragment()
        }

        val textMore: TextView = binding.textMore
        textMore.setOnClickListener {
            navigateToCarListFragment()
        }
    }

    private fun setProfileImage(url: String) {
        val profileImage: ImageView = binding.profileImage
        Picasso.get().load(url).into(profileImage)
    }

    private fun navigateToCarListFragment() {
        findNavController().navigate(R.id.carListFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}