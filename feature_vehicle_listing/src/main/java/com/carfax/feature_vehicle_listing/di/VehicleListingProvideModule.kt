package com.carfax.feature_vehicle_listing.di

import com.carfax.feature_vehicle_listing.data.remote.api.VehicleListingApi
import com.carfax.feature_vehicle_listing.data.repository.VehicleListingRepositoryImpl
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object VehicleListingProvideModule {

    @Provides
    fun provideVehicleListingApi(retrofit: Retrofit): VehicleListingApi{
        return retrofit.create(VehicleListingApi::class.java)
    }

    @Provides
    fun provideVehicleListingRepository(vehicleListingApi: VehicleListingApi): VehicleListingRepository{
        return VehicleListingRepositoryImpl(vehicleListingApi)
    }
}