package com.carfax.feature_vehicle_listing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.carfax.feature_vehicle_listing.databinding.FragmentVehicleListingBinding
import com.carfax.feature_vehicle_listing.model.VehicleListingState
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class VehicleListingFragment: Fragment(R.layout.fragment_vehicle_listing){

    private val binding by viewBinding(FragmentVehicleListingBinding::bind)
    private val viewModel : VehicleListingViewModel by viewModels()
    private val adapter = VehicleListingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.test()
        initializeViews()
        initializeObservable()
    }

    private fun initializeViews(){
        binding.carListingRecyclerView.adapter = adapter
    }

    /*
    * Initialize all the observable for this fragment
    *
    * */
    private fun initializeObservable(){
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach(this::handleState)
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: VehicleListingState){
        adapter.updateListing(state.vehicleDetailListing)
    }
}