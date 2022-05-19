package com.carfax.feature_vehicle_listing.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicleListing(
        vehicleListingEntities: List<VehicleDetailEntity>
    )

    @Query("DELETE FROM vehicleListing")
    suspend fun clearVehicleListing()

    @Query("SELECT * FROM vehicleListing WHERE id = :id")
    suspend fun getVehicleDetail(id: String): VehicleDetailEntity

    @Query("SELECT * FROM vehicleListing")
    suspend fun getVehicleListing(): List<VehicleDetailEntity>
}