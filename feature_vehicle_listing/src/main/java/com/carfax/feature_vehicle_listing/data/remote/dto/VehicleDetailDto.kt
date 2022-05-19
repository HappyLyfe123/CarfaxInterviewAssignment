package com.carfax.feature_vehicle_listing.data.remote.dto

import com.squareup.moshi.Json

data class VehicleListingDto(
    @field:Json(name = "listings") val listings: List<RemoteVehicleDetail>
)

data class RemoteVehicleDetail(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "year") val year: Int,
    @field:Json(name = "make") val make: String,
    @field:Json(name = "model") val model: String,
    @field:Json(name = "trim") val trim: String,
    @field:Json(name = "listPrice") val price: Int,
    @field:Json(name = "mileage") val mileage: Int,
    @field:Json(name = "exteriorColor") val exteriorColor: String,
    @field:Json(name = "interiorColor") val interiorColor: String,
    @field:Json(name = "drivetype") val driveType: String,
    @field:Json(name = "bodytype") val bodyType: String,
    @field:Json(name = "transmission") val transmission: String,
    @field:Json(name = "engine") val engine: String,
    @field:Json(name = "fuel") val fuel: String,
    @field:Json(name = "dealer") val dealerInfo: RemoteDealerInfo,
    @field:Json(name = "images") val images: RemoteImages
)

data class RemoteImages(
    @field:Json(name = "baseUrl") val baseURL: String,
    @field:Json(name = "firstPhoto") val firstPhoto: RemoteImagesType
)

data class RemoteImagesType(
    @field:Json(name = "small") val small: String,
    @field:Json(name = "medium") val medium: String,
    @field:Json(name = "large") val large: String
)

data class RemoteDealerInfo(
    @field:Json(name= "city") val city: String,
    @field:Json(name= "state") val state: String,
    @field:Json(name= "phone") val phone: String
)