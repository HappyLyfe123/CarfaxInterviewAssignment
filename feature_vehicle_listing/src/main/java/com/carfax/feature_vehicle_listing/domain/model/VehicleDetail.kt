package com.carfax.feature_vehicle_listing.domain.model

import java.text.NumberFormat
import java.util.*

data class VehicleDetail(
    val id: String,
    val images: Images,
    val year: Int,
    val make: String,
    val model: String,
    val trim: String,
    val price: Int,
    val mileage: Int,
    val location: LocationInfo,
    val phoneNumber: String,
    val exteriorColor: String,
    val interiorColor: String,
    val driveType: String,
    val transmission: String,
    val engine: String,
    val fuel: String,
){

    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
        currency = Currency.getInstance(Locale.US)
    }

    fun formatYearMakeModelTrim(): String{
        return String.format("%s %s %s %s",year, make, model, trim)
    }

    fun formatPrice(): String{
        return currencyFormat.format(price)
    }

    fun formatMileage(): String{
        return if (mileage > 10000){
            String.format("%.1fk mi", mileage.toDouble()/1000.0)
        }else{
            "$mileage mi"
        }
    }

    fun formatLocation(): String{
        return "${location.city}, ${location.state}"
    }
}

data class Images(
    val baseURL: String,
    val smallImage: String,
    val mediumImage: String,
    val largeImage: String
)

data class LocationInfo(
    val city: String,
    val state: String,
)
