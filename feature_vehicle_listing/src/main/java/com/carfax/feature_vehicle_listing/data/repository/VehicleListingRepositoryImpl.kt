package com.carfax.feature_vehicle_listing.data.repository

import com.carfax.feature_vehicle_listing.data.mapper.toVehicleDetailListing
import com.carfax.feature_vehicle_listing.data.remote.api.VehicleListingApi
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.repository.VehicleListingRepository
import com.carfax.library_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


/*
* @param api:
*
* @return
* */

class VehicleListingRepositoryImpl @Inject constructor(
    private val api: VehicleListingApi
): VehicleListingRepository {
    override suspend fun getVehicleListing(fetchFromRemote: Boolean): Flow<Resource<List<VehicleDetail>>> {
        return flow {
            emit(Resource.Loading(true))

//            // Check if we want
//            if(!fetchFromRemote){
//
//            }

            try {
                val response = api.getVehicleListing()
                if (!response.isSuccessful){
                    emit(Resource.Error("Couldn't load data"))
                    return@flow
                }

                val vehicleList = mutableListOf<VehicleDetail>()
                response.body()?.let { body ->
                    body.listings.forEach {
                        vehicleList.add(it.toVehicleDetailListing())
                    }
                }
                emit(Resource.Success(vehicleList))

            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } finally {
                emit(Resource.Loading(false))
            }

        }
    }
}