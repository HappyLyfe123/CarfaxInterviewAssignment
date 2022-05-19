package com.carfax.feature_vehicle_listing.di

import com.carfax.feature_vehicle_listing.data.remote.api.VehicleListingApi
import com.carfax.feature_vehicle_listing.data.repository.VehicleListingRepositoryImpl
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideVehicleListingRepository(vehicleListingRepositoryImpl: VehicleListingRepositoryImpl): VehicleListingRepository


}