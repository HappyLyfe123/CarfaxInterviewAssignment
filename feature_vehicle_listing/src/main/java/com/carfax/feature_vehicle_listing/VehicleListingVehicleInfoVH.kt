package com.carfax.feature_vehicle_listing

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.carfax.feature_vehicle_listing.databinding.ViewHolderVehicleInfoBinding
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail

class VehicleListingVehicleInfoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ViewHolderVehicleInfoBinding.bind(itemView)

    fun bind(itemView: VehicleDetail) {
        with(binding) {
            vehiclePhotoImageView.load(itemView.images.smallImage)
            vehicleYearMakeModelTrimTextView.text = itemView.formatYearMakeModelTrim()
            vehiclePriceTextView.text = itemView.formatPrice()
            vehicleMileageTextView.text = itemView.formatMileage()
            vehicleLocationTextView.text = itemView.formatLocation()
        }
    }

}