package com.carfax.feature_vehicle_listing.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.carfax.feature_vehicle_listing.domain.model.DealerInfo
import com.carfax.feature_vehicle_listing.domain.model.VehicleImage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

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
    val dealerInfo: DealerInfo,
    val exteriorColor: String,
    val interiorColor: String,
    val driveType: String,
    val bodyStyle: String,
    val transmission: String,
    val engine: String,
    val fuel: String,
)

class VehicleListingConverters {

    private val locationJsonAdapter: JsonAdapter<DealerInfo> = Moshi.Builder().build().adapter(DealerInfo::class.java)
    private val vehicleImageJsonAdapter: JsonAdapter<VehicleImage> = Moshi.Builder().build().adapter(VehicleImage::class.java)

    @TypeConverter
    fun fromLocation(location: DealerInfo): String {
        return locationJsonAdapter.toJson(location)
    }

    @TypeConverter
    fun toLocation(location: String): DealerInfo? {
        return locationJsonAdapter.fromJson(location)
    }

    @TypeConverter
    fun fromVehicleImage(vehicleImage: VehicleImage): String{
        return vehicleImageJsonAdapter.toJson(vehicleImage)
    }

    @TypeConverter
    fun toVehicleImage(vehicleImage: String): VehicleImage? {
        return vehicleImageJsonAdapter.fromJson(vehicleImage)
    }
}