package com.carfax.feature_vehicle_listing.domain.repository

import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.library_network.Async
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import java.util.*

interface VehicleListingRepository {
    suspend fun getVehicleListing(): Flow<Async<List<VehicleDetail>>>
    suspend fun getVehicleDetail(id: String): Flow<Async<VehicleDetail>>
}