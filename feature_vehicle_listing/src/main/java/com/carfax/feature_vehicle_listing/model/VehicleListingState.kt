package com.carfax.feature_vehicle_listing.model

import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail

data class VehicleListingState(
    val vehicleDetailListing: List<VehicleDetail> = emptyList(),
    val isLoading: Boolean = false
)