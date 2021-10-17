package com.android.driveit_xml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.driveit_xml.R
import com.android.driveit_xml.databinding.FragmentHomeBinding
import com.android.driveit_xml.ui.adapters.CarListAdapter
import com.android.driveit_xml.viewmodels.HomeViewModel
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

        val textMore: TextView = binding.textMore
        textMore.setOnClickListener {
            findNavController().navigate(R.id.carListFragment)
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}