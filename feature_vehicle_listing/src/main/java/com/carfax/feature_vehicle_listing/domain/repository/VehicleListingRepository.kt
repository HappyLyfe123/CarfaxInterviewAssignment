package com.carfax.feature_vehicle_listing.domain.repository

import com.carfax.library_utils.Resource
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import kotlinx.coroutines.flow.Flow

interface VehicleListingRepository {
    suspend fun getVehicleListing(fetchFromRemote: Boolean): Flow<Resource<List<VehicleDetail>>>
}