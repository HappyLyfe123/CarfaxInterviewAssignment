package com.carfax.feature_vehicle_listing.data.remote.api

import com.carfax.feature_vehicle_listing.data.remote.dto.VehicleListingDto
import retrofit2.Response
import retrofit2.http.GET

interface VehicleListingApi {
    @GET("/assignment.json")
    suspend fun getVehicleListing(): Response<VehicleListingDto>
}