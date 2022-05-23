package com.carfax.feature_vehicle_listing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.carfax.feature_vehicle_listing.databinding.FragmentVehicleListingBinding
import com.carfax.library_network.Fail
import com.carfax.library_network.Loading
import com.carfax.library_network.Success
import com.carfax.library_network.Uninitialized
import com.carfax.library_ui.viewLifecycleScope
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class VehicleListingFragment : Fragment(R.layout.fragment_vehicle_listing) {

    private val binding by viewBinding(FragmentVehicleListingBinding::bind)
    private val viewModel: VehicleListingViewModel by viewModels()
    private val vehicleListingAdapter = VehicleListingAdapter()
    private lateinit var navController: NavController

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (!isGranted) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Permission to access call is required for this app to make a phone calls.")
                .setTitle("Permission required")
                .setPositiveButton("Ok")
                { dialog, _ ->
                    Timber.d("Clicked")
                    dialog.dismiss()
                }
            val dialog = builder.create()
            dialog.show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initializeViews()
        viewLifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                initializeObservable()
            }
        }
    }

    private fun initializeViews() {
        binding.carListingRecyclerView.adapter = vehicleListingAdapter
        vehicleListingAdapter.onVehicleClick = { vehicleId ->
            navController.navigate(
                VehicleListingFragmentDirections.actionVehicleListingFragmentToVehicleDetailFragment(vehicleId)
            )
        }

        vehicleListingAdapter.onCallDealerClick = { phoneNumber ->
            Timber.d("Calling dealer at $phoneNumber")
            callDealer(phoneNumber)
        }

        binding.errorView.setOnClickListener {
            viewModel.refreshVehicleListing()
        }
    }

    private fun initializeObservable() {
        viewModel.viewState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach(this::handleState)
            .launchIn(lifecycleScope)
    }

    private fun callDealer(phoneNumber: String) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) -> {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                startActivity(callIntent)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }

    private fun handleState(viewState: VehicleListingState) {

        when (viewState.vehicleDetailListing) {
            is Success -> {
                binding.vehicleListingProgressCircle.isVisible = viewState.vehicleDetailListing.isLoading
                vehicleListingAdapter.submitList(viewState.vehicleDetailListing.invoke())
                binding.errorView.isVisible = viewState.vehicleDetailListing.isError
            }
            is Loading -> {
                binding.vehicleListingProgressCircle.isVisible = viewState.vehicleDetailListing.isLoading
                binding.errorView.isVisible = viewState.vehicleDetailListing.isError
            }
            is Fail -> {
                binding.vehicleListingProgressCircle.isVisible = viewState.vehicleDetailListing.isLoading
                binding.errorView.isVisible = viewState.vehicleDetailListing.isError
                binding.errorView.setText(getString(R.string.error_loading))
                vehicleListingAdapter.submitList(viewState.vehicleDetailListing.invoke())
            }
            is Uninitialized -> {
                // If it uninitialized just do nothing
                return
            }
        }
    }

}