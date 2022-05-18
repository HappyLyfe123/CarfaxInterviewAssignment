package com.carfax.feature_vehicle_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_network.Async
import com.carfax.library_network.Uninitialized
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VehicleDetailState(
    val vehicleDetail: Async<VehicleDetail> = Uninitialized
)

@HiltViewModel
class VehicleDetailViewModel @Inject constructor(
    private val repository: VehicleListingRepository
) : ViewModel() {

    private val _state = MutableStateFlow(VehicleDetailState())
    val state = _state.asStateFlow()

    fun getVehicleDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getVehicleDetail(id).collect { result ->
                _state.value = _state.value.copy(
                    vehicleDetail = result
                )
            }
        }
    }

    fun getDealerPhoneNumber(): String {
        return _state.value.vehicleDetail.invoke()?.phoneNumber ?: ""
    }
}