package com.carfax.feature_vehicle_listing

import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_network.Fail
import com.carfax.library_network.Loading
import com.carfax.library_network.Success
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
@ExperimentalCoroutinesApi
internal class VehicleListingViewModelTest {
    private val testVehicleListingRepository : VehicleListingRepository = mockk()

    /**
     * Given that the user open the app and we are fetching vehicle list
     *
     * When the user doesn't have internet connection
     *
     * Then we output an IOException error
     */
    @Test
    fun getViewStateOnRepositoryFail() = runTest {
        val error = Fail<List<VehicleDetail>>(IOException(), null)
        coEvery {
            testVehicleListingRepository.getVehicleListing()
        } returns error

        val viewModel = genViewMode()

        assertEquals(error, viewModel.viewState.value.vehicleDetailListing)

        coVerify(exactly = 1) {
            testVehicleListingRepository.getVehicleListing()
        }

    }

    /**
     * Given that the user open the app and we are fetching vehicle list
     *
     * When the user doesn't have internet connection but have data cache
     *
     * Then we output an IOException error and the cache data
     */
    @Test
    fun getViewStateOnRepositoryFailButHaveCache() = runTest {
        val error = Fail(
            IOException(), listOf(
                VehicleDetail(
                    id = "1234"
                )
            )
        )
        coEvery {
            testVehicleListingRepository.getVehicleListing()
        } returns error

        val viewModel = genViewMode()

        assertEquals(error, viewModel.viewState.value.vehicleDetailListing)

        coVerify(exactly = 1) {
            testVehicleListingRepository.getVehicleListing()
        }

    }


    /**
     * Given that the user open the app and we are fetching vehicle list
     *
     * When the user have internet connection
     *
     * Then we output the vehicle detail list to the ViewModel
     * */
    @Test
    fun getViewStateOnRepositorySuccess() {
        val success = Success<List<VehicleDetail>>(emptyList())

        coEvery {
            testVehicleListingRepository.getVehicleListing()
        } returns success


        val viewModel = genViewMode()

        viewModel.viewState.value.vehicleDetailListing.invoke()?.let { assertEquals(true, it.isEmpty()) }
    }


    /**
     * Given that we need to fetch the vehicle list from the server
     *
     * When the repository is fetching the list from the server
     *
     * Then we output an loading from the ViewModel
     * */
    @Test
    fun getViewStateLoading() = runTest {

        // Set a delay so that the flow able to emit the state
        coEvery { testVehicleListingRepository.getVehicleListing() } coAnswers {
            delay(5000)
            Success(listOf(VehicleDetail(id = "123"), VehicleDetail(id = "456")))
        }

        val viewModel = genViewMode()

        assertEquals(Loading<List<VehicleDetail>>(emptyList()), viewModel.viewState.value.vehicleDetailListing)
    }

    private fun genViewMode() = VehicleListingViewModel(
        testVehicleListingRepository,
        TestDispatcherFacade(),
    )
}