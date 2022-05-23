package com.carfax.feature_vehicle_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carfax.feature_vehicle_listing.di.DispatcherFacade
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_network.Async
import com.carfax.library_network.Loading
import com.carfax.library_network.Uninitialized
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class VehicleListingState(
    val vehicleDetailListing: Async<List<VehicleDetail>> = Uninitialized,
)

@HiltViewModel
class VehicleListingViewModel @Inject constructor(
    private val repository: VehicleListingRepository,
    private val dispatcherFacade: DispatcherFacade,
) : ViewModel() {

    private val _viewState = MutableStateFlow(VehicleListingState())
    val viewState = _viewState.asStateFlow()

    init {
        getVehicleListing()
    }

    fun refreshVehicleListing(){
        getVehicleListing()
    }

    private fun getVehicleListing() {
        viewModelScope.launch(dispatcherFacade.mainDispatcher) {

            _viewState.update { vehicleListingState ->
                vehicleListingState.copy(
                    vehicleDetailListing = Loading(emptyList())
                )
            }

            repository.getVehicleListing().let {vehicleListing ->
                _viewState.update { vehicleListingState ->
                    vehicleListingState.copy(
                        vehicleDetailListing = vehicleListing
                    )
                }
            }
        }
    }
}
