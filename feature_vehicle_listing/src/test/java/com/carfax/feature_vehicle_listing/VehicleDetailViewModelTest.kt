package com.carfax.feature_vehicle_listing

import androidx.lifecycle.SavedStateHandle
import com.carfax.feature_vehicle_listing.domain.model.DealerInfo
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_network.Success
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
@ExperimentalCoroutinesApi
internal class VehicleDetailViewModelTest {

    private val testVehicleListingRepository: VehicleListingRepository = mockk()
    private val testSavedStateHandle: SavedStateHandle = SavedStateHandle()

    /**
     * Given that the user click on a vehicle from the list
     *
     * When we go to get the vehicle and the vehicle id is not valid
     *
     * Then we output an default vehicle detail
     * */
    @Test
    fun getViewStateOnRepositoryFail() = runTest {

        coEvery {
            testVehicleListingRepository.getVehicleDetail(FAKE_VEHICLE_ID)
        } returns Success(VehicleDetail())

        val viewModel = genViewModel()

        assertEquals(viewModel.viewState.value.vehicleDetail, Success(VehicleDetail()))

    }

    /**
     * Given that we need to fetch the vehicle list
     *
     * When the source of truth able to result back the vehical detail
     *
     * Then we output an success from the ViewModel
     * */
    @Test
    fun getViewStateOnRepositorySuccess() {
        val success = Success(VehicleDetail(id = "123"))

        coEvery {
            testVehicleListingRepository.getVehicleDetail(FAKE_VEHICLE_ID)
        } returns success

        // Given
        val viewModel = genViewModel()

        assertEquals(viewModel.viewState.value.vehicleDetail, success)

    }

    @Test
    fun getDealerPhoneNumber() {

        val success = Success(
            VehicleDetail(
                id = "123",
                dealerInfo = DealerInfo(
                    phoneNumber = "123456"
                )
            )
        )

        coEvery {
            testVehicleListingRepository.getVehicleDetail(FAKE_VEHICLE_ID)
        } returns success

        val viewModel = genViewModel()

        assertEquals(viewModel.viewState.value.vehicleDetail.invoke()?.dealerInfo?.phoneNumber ?: "", "123456")
    }

    private fun genViewModel() = VehicleDetailViewModel(
        testVehicleListingRepository,
        TestDispatcherFacade(),
        testSavedStateHandle.apply {
            set("vehicle_id", FAKE_VEHICLE_ID)
        }
    )

    companion object {
        private const val FAKE_VEHICLE_ID = "123245"
    }
}