package com.carfax.feature_vehicle_listing

import com.carfax.feature_vehicle_listing.domain.model.DealerInfo
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_network.Fail
import com.carfax.library_network.Loading
import com.carfax.library_network.Success
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class VehicleDetailViewModelTest {

    private val vehicleListingRepository : VehicleListingRepository = mockk()

    /*
    * Given that we need to fetch the vehicle list
    *
    * When the repository fail
    *
    * Then we output an Error state from the View Model
    * */
    @Test
    fun getViewStateOnRepositoryFail() {
        val error = Fail<VehicleDetail>(IllegalStateException())

        coEvery {
            vehicleListingRepository.getVehicleDetail(FAKE_VEHICLE_ID)
        } returns error

        val viewModel = genViewMode()

        val viewState = viewModel.viewState.value.copy(
            vehicleDetail = error
        )
        assert((viewState.vehicleDetail as Fail).error is java.lang.IllegalStateException)

    }

    /*
    * Given that we need to fetch the vehicle list
    *
    * When the repository success
    *
    * Then we output an success from the ViewModel
    * */
    @Test
    fun getViewStateOnRepositorySuccess(){
        val success =  Success(VehicleDetail())

        coEvery {
            vehicleListingRepository.getVehicleDetail(FAKE_VEHICLE_ID)
        } returns success

        // Given
        val viewModel = genViewMode()

        val viewState = viewModel.viewState.value.copy(
            vehicleDetail = success
        )

        assertEquals(viewState.vehicleDetail, success)

    }

    /*
    * Given that we need to fetch the vehicle list
    *
    * When is loading
    *
    * Then we output an loading from the ViewModel
    * */
    @Test
    fun getViewStateLoading(){
        val loading = Loading<VehicleDetail>(null)

        val viewModel = genViewMode()

        val viewState = viewModel.viewState.value.copy(
            vehicleDetail = loading
        )

        assert(!viewState.vehicleDetail.shouldLoad)
    }

    @Test
    fun getDealerPhoneNumber() {

        val success = Loading(
            VehicleDetail(
                dealerInfo = DealerInfo(
                    phoneNumber = "123456"
                ),

        )
        )

        val viewModel = genViewMode()

        val viewState = viewModel.viewState.value.copy(
            vehicleDetail = success
        )

        assertEquals(viewState.vehicleDetail.invoke()?.dealerInfo?.phoneNumber ?: "", "123456")
    }

    private fun genViewMode() = VehicleDetailViewModel(
        vehicleListingRepository
    )

    companion object{
        private const val FAKE_VEHICLE_ID = "123245"
    }
}