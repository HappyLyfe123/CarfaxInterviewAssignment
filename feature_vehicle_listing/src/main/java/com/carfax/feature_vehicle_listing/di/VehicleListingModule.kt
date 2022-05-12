package com.carfax.feature_vehicle_listing.di

import android.app.Application
import androidx.room.Room
import com.carfax.feature_vehicle_listing.data.local.VehicleListingDatabase
import com.carfax.feature_vehicle_listing.data.remote.api.VehicleListingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VehicleListingModule {

    @Provides
    fun provideVehicleListingApi(retrofit: Retrofit): VehicleListingApi{
        return retrofit.create(VehicleListingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVehicleListingDatabase(app: Application): VehicleListingDatabase {
        return Room.databaseBuilder(
            app,
            VehicleListingDatabase::class.java,
            "vehicleListingDB.db"
        ).build()
    }
}