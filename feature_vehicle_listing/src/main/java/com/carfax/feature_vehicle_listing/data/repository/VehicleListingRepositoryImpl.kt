package com.carfax.feature_vehicle_listing.data.repository

import com.carfax.feature_vehicle_listing.data.local.VehicleListingDatabase
import com.carfax.feature_vehicle_listing.data.mapper.toVehicleDetail
import com.carfax.feature_vehicle_listing.data.mapper.toVehicleDetailEntity
import com.carfax.feature_vehicle_listing.data.remote.api.VehicleListingApi
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_network.Async
import com.carfax.library_network.Fail
import com.carfax.library_network.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton


/*
* @param api the api the
* @param db the caching database
*
* @return
* */
@Singleton
class VehicleListingRepositoryImpl @Inject constructor(
    private val api: VehicleListingApi,
    private val db: VehicleListingDatabase
): VehicleListingRepository {

    private val dao = db.doa

    override suspend fun getVehicleListing(): Flow<Async<List<VehicleDetail>>> {
        return flow {

            try {
                api.getVehicleListing().run {
                    if (!isSuccessful) {
                        //emit(Fail("Couldn't load data"))
                        return@flow
                    }

                    val vehicleListing = this.body()?.listings?.map {
                        it.toVehicleDetail()
                    }

                    if (vehicleListing != null) {
                        // Clear the previous cache and then save the new data into cache
                        dao.clearVehicleListing()
                        dao.insertVehicleListing(
                            vehicleListing.map { it.toVehicleDetailEntity() }
                        )
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Fail(Throwable("Couldn't load data")))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Fail(Throwable("Couldn't load data")))
            } finally {
                emit(Success(dao.getVehicleListing().map {
                    it.toVehicleDetail()
                }))
            }
        }
    }

    override suspend fun getVehicleDetail(id: String): Flow<Async<VehicleDetail>> {
        return flow{
            dao.getVehicleDetail(id).run {
                emit(Success(this.toVehicleDetail()))
            }
        }
    }
}