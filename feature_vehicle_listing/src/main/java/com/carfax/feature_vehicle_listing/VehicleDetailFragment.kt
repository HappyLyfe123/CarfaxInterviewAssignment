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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.size.ViewSizeResolver
import com.carfax.feature_vehicle_listing.databinding.FragmentVehicleDetailBinding
import com.carfax.library_ui.viewLifecycleScope
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class VehicleDetailFragment : Fragment(R.layout.fragment_vehicle_detail) {

    private val binding by viewBinding(FragmentVehicleDetailBinding::bind)
    private val viewModel: VehicleDetailViewModel by viewModels()

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
        initializeViews()
        viewLifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                initializeObservables()
            }
        }
    }

    private fun initializeViews() {
        binding.callDealerButton.setOnClickListener {
            callDealer()
        }
    }

    private fun initializeObservables() {
        viewModel.viewState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach(this::handleState)
            .launchIn(lifecycleScope)
    }

    private fun handleState(viewState: VehicleDetailState) {
        viewState.vehicleDetail.invoke().let {vehicleDetail ->
            if (vehicleDetail != null) {
                with(binding){
                    vehiclePhotoImageView.load(vehicleDetail.vehicleImage.mediumImage){
                        size(ViewSizeResolver(binding.vehiclePhotoImageView))
                    }
                    vehicleYearMakeModelTrimTextView.text = vehicleDetail.formatYearMakeModelTrim()
                    vehiclePriceTextView.text = vehicleDetail.formatPrice()
                    vehicleMileageTextView.text = vehicleDetail.formatMileage()
                    locationInfoTextView.text = vehicleDetail.formatLocation()
                    exteriorColorInfoTextView.text = vehicleDetail.exteriorColor
                    interiorColorInfoTextView.text = vehicleDetail.interiorColor
                    driveTypeInfoTextView.text = vehicleDetail.driveType
                    transmissionInfoTextView.text = vehicleDetail.transmission
                    bodyStyleInfoTextView.text = vehicleDetail.bodyStyle
                    engineInfoTextView.text = vehicleDetail.engine
                    fuelInfoTextView.text = vehicleDetail.fuel
                }

            }
        }
    }

    private fun callDealer() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) -> {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${viewModel.getDealerPhoneNumber()}")
                startActivity(callIntent)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }
}
