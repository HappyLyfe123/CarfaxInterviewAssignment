package com.carfax.feature_vehicle_listing

import com.carfax.feature_vehicle_listing.di.DispatcherFacade
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestDispatcherFacade : DispatcherFacade {
    override val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    override val ioDispatcher: CoroutineDispatcher = Dispatchers.Main
    override val defaultDispatcher: CoroutineDispatcher = Dispatchers.Main
}