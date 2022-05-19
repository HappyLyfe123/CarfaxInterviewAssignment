package com.carfax.feature_vehicle_listing

import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_network.Fail
import com.carfax.library_network.Loading
import com.carfax.library_network.Success
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class VehicleListingViewModelTest {

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
        val error = Fail<List<VehicleDetail>>(IllegalStateException())
        coEvery {
            vehicleListingRepository.getVehicleListing()
        } returns error

        val viewModel = genViewMode()

        val viewState = viewModel.viewState.value.copy(
            vehicleDetailListing = error
        )
        assert((viewState.vehicleDetailListing as Fail).error is java.lang.IllegalStateException)

        coVerify(exactly = 1){
            vehicleListingRepository.getVehicleListing()
        }

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
        val success =  Success<List<VehicleDetail>>(emptyList())

        coEvery {
            vehicleListingRepository.getVehicleListing()
        } returns success

        // Given
        val viewModel = genViewMode()

        val viewState = viewModel.viewState.value.copy(
            vehicleDetailListing = success
        )

        viewState.vehicleDetailListing.invoke()?.let { assert(it.isEmpty()) }
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
        val loading = Loading<List<VehicleDetail>>(null)

        val viewModel = genViewMode()

        val viewState = viewModel.viewState.value.copy(
            vehicleDetailListing = loading
        )

        assert(!viewState.vehicleDetailListing.shouldLoad)
    }

    private fun genViewMode() = VehicleListingViewModel(
        vehicleListingRepository
    )
}