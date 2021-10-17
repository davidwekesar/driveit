package com.android.driveit_xml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.driveit_xml.data.source.network.PhotoResult
import com.android.driveit_xml.databinding.FragmentCarListBinding
import com.android.driveit_xml.ui.adapters.CarListAdapter
import com.android.driveit_xml.viewmodels.CarListViewModel

class CarListFragment : Fragment() {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CarListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)

        viewModel.photoResult.observe(viewLifecycleOwner, { photoResult ->
            photoResult?.let {
                addDataToRecyclerView(it)
            }
        })

        val backButton: ImageButton = binding.backImageButton
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun addDataToRecyclerView(photoResult: PhotoResult) {
        val carListAdapter = CarListAdapter(photoResult.results)
        val recyclerView = binding.verticalCarList
        recyclerView.adapter = carListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}