package com.carfax.feature_vehicle_listing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface KotlinModule {
    /**
     * Allows us to change all of the dispatchers to test dispatchers during testing
     */
    @Binds
    fun bindDispatcherFacade(dispatcherFacadeImpl: DispatcherFacadeImpl): DispatcherFacade
}