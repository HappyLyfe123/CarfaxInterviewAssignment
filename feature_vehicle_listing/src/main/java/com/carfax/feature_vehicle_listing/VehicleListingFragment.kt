package com.carfax.feature_vehicle_listing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.carfax.feature_vehicle_listing.databinding.FragmentVehicleListingBinding
import com.carfax.library_ui.PermissionRequestCode
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
        if (isGranted) {
            Timber.d("I got permission")
            return@registerForActivityResult
        } else {
            showPermissionError()
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
        vehicleListingAdapter.onVehicleClick = { id ->
            navController.navigate(
                VehicleListingFragmentDirections.actionVehicleListingFragmentToVehicleDetailFragment(id)
            )
        }

        vehicleListingAdapter.onCallDealerClick = { phoneNumber ->
            Timber.d("Call dealer button clicked")
            callDealer(phoneNumber)
        }
    }

    private fun initializeObservable() {
        viewModel.viewState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach(this::handleState)
            .launchIn(lifecycleScope)
    }

    private fun asForCallPhonePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CALL_PHONE),
            PermissionRequestCode.CallPhone
        )
    }

    private fun callDealer(phoneNumber: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED -> {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                startActivity(callIntent)
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {
                Timber.d("I got call in shouldShowRequestPermissionRationale")
                asForCallPhonePermission()
            }
            else -> {
                Timber.d("I got call in else")
                requestPermissionLauncher.launch(
                    Manifest.permission.CALL_PHONE
                )
            }
        }
    }

    private fun showPermissionError() {
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

    private fun handleState(viewState: VehicleListingState) {
        with(viewState) {
            Timber.d("${viewState.vehicleDetailListing.complete}")
            vehicleListingAdapter.submitList(viewState.vehicleDetailListing.invoke())
        }
    }

}