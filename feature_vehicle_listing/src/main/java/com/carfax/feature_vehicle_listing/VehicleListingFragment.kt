package com.carfax.feature_vehicle_listing

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.carfax.feature_vehicle_listing.VehicleDetailFragment.Companion.SELECTED_VEHICLE_POSITION_BUNDLE_KEY
import com.carfax.feature_vehicle_listing.databinding.FragmentVehicleListingBinding
import com.carfax.feature_vehicle_listing.model.VehicleListingState
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class VehicleListingFragment: Fragment(R.layout.fragment_vehicle_listing){

    private val binding by viewBinding(FragmentVehicleListingBinding::bind)
    private val viewModel : VehicleListingViewModel by activityViewModels()
    private val vehicleListingAdapter = VehicleListingAdapter()
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initializeViews()
        initializeObservable()
    }

    private fun initializeViews(){
        binding.carListingRecyclerView.adapter = vehicleListingAdapter
        vehicleListingAdapter.onVehicleClick = {position ->
            launchVehicleDetailFragment(position)

        }
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


    private fun launchVehicleDetailFragment(vehiclePosition: Int){
        navController.navigate(R.id.action_vehicleListingFragment_to_vehicleDetailFragment,
            bundleOf(SELECTED_VEHICLE_POSITION_BUNDLE_KEY to vehiclePosition))
    }

    /*
    * Handle the state change of the screen
    * @param state the current view state of the fragment
    * */
    private fun handleState(state: VehicleListingState){
        vehicleListingAdapter.submitList(state.vehicleDetailListing)
    }

}