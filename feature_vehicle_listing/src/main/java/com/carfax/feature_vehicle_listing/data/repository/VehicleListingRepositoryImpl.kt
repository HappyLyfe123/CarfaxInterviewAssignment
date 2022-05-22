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
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleListingRepositoryImpl @Inject constructor(
    private val api: VehicleListingApi,
    private val db: VehicleListingDatabase
): VehicleListingRepository {

    private val dao = db.doa

    override suspend fun getVehicleListing(): Async<List<VehicleDetail>> {
        try {
            api.getVehicleListing().run {
                if (!isSuccessful) {
                    return Fail(Throwable("Could load data"), emptyList())
                }

                val vehicleListing = this.body()

                if (vehicleListing != null) {
                    dao.clearVehicleListing()
                    dao.insertVehicleListing(
                        vehicleListing.listings.map { it.toVehicleDetailEntity() }
                    )
                }
            }
            return Success(dao.getVehicleListing().map {
                it.toVehicleDetail()
            })
        } catch (e: IOException) {
            return Fail(e, value = dao.getVehicleListing().map {
                it.toVehicleDetail()
            })
        } catch (e: HttpException) {
            return Fail(e, value = dao.getVehicleListing().map {
                it.toVehicleDetail()
            })
        }
    }

    override suspend fun getVehicleDetail(id: String): Async<VehicleDetail> {
        return Success(dao.getVehicleDetail(id).run {
            if (this == null) {
                return@run VehicleDetail()
            }
            this.toVehicleDetail()
        })
    }
}