package com.carfax.feature_vehicle_listing.domain.repository

import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.library_network.Async

interface VehicleListingRepository {
    suspend fun getVehicleListing(): Async<List<VehicleDetail>>
    suspend fun getVehicleDetail(id: String): Async<VehicleDetail>
}