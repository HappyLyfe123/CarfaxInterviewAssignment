package com.carfax.feature_vehicle_listing.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VehicleListingDto(
    @SerializedName("listings") val listings: List<VehicleDetail>
)

data class VehicleDetail(
    @SerializedName("id") val id: String,
    @SerializedName("year") val year: Int,
    @SerializedName("make") val make: String,
    @SerializedName("model") val model: String,
    @SerializedName("trim") val trim: String,
    @SerializedName("listPrice") val price: Int,
    @SerializedName("mileage") val mileage: Int,
    @SerializedName("exteriorColor") val exteriorColor: String,
    @SerializedName("interiorColor") val interiorColor: String,
    @SerializedName("drivetype") val driveType: String,
    @SerializedName("transmission") val transmission: String,
    @SerializedName("engine") val engine: String,
    @SerializedName("fuel") val fuel: String,
    @SerializedName("dealer") val dealerInfo: DealerDto,
    @SerializedName("images") val images: Images
)

data class Images(
    @SerializedName("baseUrl") val baseURL: String,
    @SerializedName("firstPhoto") val firstPhoto: ImagesType
)

data class ImagesType(
    @SerializedName("small") val small: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("large") val large: String
)

data class DealerDto(
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("phone") val phone: String
)