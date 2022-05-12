package com.carfax.feature_vehicle_listing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.carfax.feature_vehicle_listing.VehicleDetailFragment.Companion.SELECTED_VEHICLE_POSITION_BUNDLE_KEY
import com.carfax.feature_vehicle_listing.databinding.FragmentVehicleListingBinding
import com.carfax.feature_vehicle_listing.model.VehicleListingState
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                initializeObservable()
            }
        }
    }

    private fun initializeViews() {
        binding.carListingRecyclerView.adapter = vehicleListingAdapter
        vehicleListingAdapter.onVehicleClick = { position ->
            launchVehicleDetailFragment(position)
        }

        vehicleListingAdapter.onCallDealerClick = { phoneNumber ->
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callIntent)
        }
    }

    /*
    * Initialize all the observable for this fragment
    *
    * */
    private fun initializeObservable() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach(this::handleState)
            .launchIn(lifecycleScope)
    }


    private fun launchVehicleDetailFragment(vehiclePosition: Int) {
        navController.navigate(
            R.id.action_vehicleListingFragment_to_vehicleDetailFragment,
            bundleOf(SELECTED_VEHICLE_POSITION_BUNDLE_KEY to vehiclePosition)
        )
    }

    override fun onStop() {
        super.onStop()
    }

    /*
        * Handle the state change of the screen
        * @param state the current view state of the fragment
        * */
    private fun handleState(state: VehicleListingState) {
        vehicleListingAdapter.submitList(state.vehicleDetailListing)
    }

}