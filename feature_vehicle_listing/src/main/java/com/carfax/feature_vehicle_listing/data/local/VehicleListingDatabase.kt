package com.carfax.feature_vehicle_listing.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [VehicleDetailEntity::class],
    version = 1
)
@TypeConverters(VehicleListingConverters::class)
abstract class VehicleListingDatabase: RoomDatabase() {
    abstract val doa: VehicleDao
}