package com.carfax.feature_vehicle_listing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.feature_vehicle_listing.model.VehicleListingState
import com.carfax.library_utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VehicleListingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: VehicleListingRepository
) : ViewModel() {

    private val _state = MutableStateFlow(VehicleListingState())
    val state = _state.asStateFlow()

    init {
        Timber.d("Hello")
        viewModelScope.launch {
            repository.getVehicleListing(fetchFromRemote = false).collect { result ->
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

    fun test() {
        Timber.d("Test")
    }


}