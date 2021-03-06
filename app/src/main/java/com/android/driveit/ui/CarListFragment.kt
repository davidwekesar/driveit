package com.android.driveit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.driveit.data.source.network.Car
import com.android.driveit.data.source.network.PhotoResult
import com.android.driveit.databinding.FragmentCarListBinding
import com.android.driveit.ui.adapters.GridCarListAdapter
import com.android.driveit.viewmodels.CarListViewModel

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

        viewModel.carList.observe(viewLifecycleOwner, { carList ->
            carList?.let {
                addDataToRecyclerView(carList)
            }
        })

        val backButton: ImageButton = binding.backImageButton
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun addDataToRecyclerView(cars: List<Car>) {
        val gridCarListAdapter = GridCarListAdapter(cars, requireContext())
        val recyclerView = binding.verticalCarList
        recyclerView.adapter = gridCarListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}