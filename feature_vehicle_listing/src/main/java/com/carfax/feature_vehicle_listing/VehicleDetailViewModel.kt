package com.carfax.feature_vehicle_listing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carfax.feature_vehicle_listing.di.DispatcherFacade
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_network.Async
import com.carfax.library_network.Uninitialized
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VehicleDetailState(
    val vehicleDetail: Async<VehicleDetail> = Uninitialized
)

@HiltViewModel
class VehicleDetailViewModel @Inject constructor(
    private val repository: VehicleListingRepository,
    dispatcherFacade: DispatcherFacade,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val vehicleId: String = requireNotNull(savedStateHandle.get("vehicle_id")) {
        "Unexpected null vehicleId value."
    }

    private val _viewState = MutableStateFlow(VehicleDetailState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(dispatcherFacade.mainDispatcher) {
            _viewState.update { vehicleDetailState ->
                vehicleDetailState.copy(
                    vehicleDetail = repository.getVehicleDetail(vehicleId)
                )
            }
        }
    }

    fun getDealerPhoneNumber(): String {
        return _viewState.value.vehicleDetail.invoke()?.dealerInfo?.phoneNumber ?: ""
    }
}