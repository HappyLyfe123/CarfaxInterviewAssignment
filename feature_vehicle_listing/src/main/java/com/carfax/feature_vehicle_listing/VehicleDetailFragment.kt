package com.carfax.feature_vehicle_listing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.carfax.feature_vehicle_listing.databinding.FragmentVehicleDetailBinding
import com.carfax.library_ui.PermissionRequestCode
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
        if (isGranted) {
            Timber.d("Got call permission")
            return@registerForActivityResult
        } else {
            Timber.d("Don't have permission, showing error message")
            showPermissionError()
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
        viewState.vehicleDetail.invoke().let {
            if (it != null) {
                Glide.with(requireContext()).load(it.vehicleImage.mediumImage)
                    .placeholder(R.drawable.default_image_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.vehiclePhotoImageView)
                binding.vehicleYearMakeModelTrimTextView.text = it.formatYearMakeModelTrim()
                binding.vehiclePriceTextView.text = it.formatPrice()
                binding.vehicleMileageTextView.text = it.formatMileage()
                binding.locationInfoTextView.text = it.formatLocation()
                binding.exteriorColorInfoTextView.text = it.exteriorColor
                binding.interiorColorInfoTextView.text = it.interiorColor
                binding.driveTypeInfoTextView.text = it.driveType
                binding.transmissionInfoTextView.text = it.transmission
                binding.bodyStyleInfoTextView.text = it.bodyStyle
                binding.engineInfoTextView.text = it.engine
                binding.bodyStyleInfoTextView.text
                binding.fuelInfoTextView.text = it.fuel
            }
        }
    }

    private fun asForCallPhonePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CALL_PHONE),
            PermissionRequestCode.CallPhone
        )
    }

    private fun callDealer() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED -> {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${viewModel.getDealerPhoneNumber()}")
                startActivity(callIntent)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {
                Timber.d("shouldShowRequestPermissionRationale got call")
                asForCallPhonePermission()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
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
}
