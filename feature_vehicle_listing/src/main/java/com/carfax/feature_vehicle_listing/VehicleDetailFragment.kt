package com.carfax.feature_vehicle_listing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.carfax.feature_vehicle_listing.databinding.FragmentVehicleDetailBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class VehicleDetailFragment: Fragment(R.layout.fragment_vehicle_detail) {

    private val binding by viewBinding(FragmentVehicleDetailBinding::bind)
    private val viewModel: VehicleListingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(SELECTED_VEHICLE_POSITION_BUNDLE_KEY)?.let {
            viewModel.getVehicleDetail(it)
        }.let {
            if (it != null) {
                binding.vehiclePhotoImageView.load(it.vehicleImage.smallImage)
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

        binding.callDealerButton.setOnClickListener{
//            val callIntent = Intent(Intent.ACTION_CALL)
//            callIntent.data = Uri.parse("tel:"+"9783198783")
//            startActivity(callIntent)
        }
    }

    companion object {
        const val SELECTED_VEHICLE_POSITION_BUNDLE_KEY = "selectedVehiclePositionBundleKey"
    }
}