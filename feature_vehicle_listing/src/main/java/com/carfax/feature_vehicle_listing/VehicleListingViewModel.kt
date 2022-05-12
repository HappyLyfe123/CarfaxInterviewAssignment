package com.carfax.feature_vehicle_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.feature_vehicle_listing.model.VehicleListingState
import com.carfax.library_utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListingViewModel @Inject constructor(
    private val repository: VehicleListingRepository
) : ViewModel() {

    private val _state = MutableStateFlow(VehicleListingState())
    val state = _state.asStateFlow()

    init {
        getVehicleListing()
    }

    private fun getVehicleListing() {
        viewModelScope.launch {
            repository.getVehicleListing(fetchFromRemote = true).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            vehicleDetailListing = result.data!!
                        )
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    fun getVehicleDetail(position: Int): VehicleDetail {
        return _state.value.vehicleDetailListing[position]
    }
}
