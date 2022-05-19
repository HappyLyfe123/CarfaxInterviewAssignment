package com.carfax.feature_vehicle_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_network.Async
import com.carfax.library_network.Loading
import com.carfax.library_network.Uninitialized
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class VehicleListingState(
    val vehicleDetailListing: Async<List<VehicleDetail>> = Uninitialized,
)

@HiltViewModel
class VehicleListingViewModel @Inject constructor(
    private val repository: VehicleListingRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(VehicleListingState())
    val viewState = _viewState.asStateFlow()

    init {
        getVehicleListing()
    }

    private fun getVehicleListing() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = _viewState.value.copy(
                vehicleDetailListing = Loading<List<VehicleDetail>>(null)
            )
            repository.getVehicleListing().let {
                _viewState.value = _viewState.value.copy(
                    vehicleDetailListing = it
                )
            }
        }
    }
}
