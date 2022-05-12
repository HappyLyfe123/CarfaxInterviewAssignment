package com.carfax.feature_vehicle_listing.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.carfax.feature_vehicle_listing.domain.model.VehicleImage
import com.carfax.feature_vehicle_listing.domain.model.LocationInfo
import com.google.gson.Gson

@Entity(
    tableName = "vehicleListing"
)
data class VehicleDetailEntity(
    @PrimaryKey val id: String,
    val vehicleImage: VehicleImage,
    val year: Int,
    val make: String,
    val model: String,
    val trim: String,
    val price: Int,
    val mileage: Int,
    val location: LocationInfo,
    val phone: String,
    val exteriorColor: String,
    val interiorColor: String,
    val driveType: String,
    val bodyStyle: String,
    val transmission: String,
    val engine: String,
    val fuel: String,
)

class VehicleListingConverters{

    @TypeConverter
    fun fromLocation(location: LocationInfo): String{
        return Gson().toJson(location)
    }

    @TypeConverter
    fun toLocation(location: String): LocationInfo{
        return Gson().fromJson(location, LocationInfo::class.java)
    }

    @TypeConverter
    fun fromVehicleImage(vehicleImage: VehicleImage): String{
        return Gson().toJson(vehicleImage)
    }

    @TypeConverter
    fun toVehicleImage(vehicleImage: String): VehicleImage{
        return Gson().fromJson(vehicleImage, VehicleImage::class.java)
    }
}