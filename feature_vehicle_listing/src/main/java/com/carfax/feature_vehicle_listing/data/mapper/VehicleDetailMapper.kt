package com.carfax.feature_vehicle_listing.data.mapper

import com.carfax.feature_vehicle_listing.data.remote.dto.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.model.Images
import com.carfax.feature_vehicle_listing.domain.model.LocationInfo

fun VehicleDetail.toVehicleDetailListing(): com.carfax.feature_vehicle_listing.domain.model.VehicleDetail {
    return com.carfax.feature_vehicle_listing.domain.model.VehicleDetail(
        id = id,
        year = year,
        make = make,
        model = model,
        trim = trim,
        price = price,
        mileage = mileage,
        phoneNumber = dealerInfo.phone,
        exteriorColor = exteriorColor,
        interiorColor = interiorColor,
        driveType = driveType,
        transmission = transmission,
        engine = engine,
        fuel = fuel,
        location = LocationInfo(
            dealerInfo.city,
            dealerInfo.state
        ),
        images = Images(
            baseURL = images.baseURL,
            smallImage = images.firstPhoto.small,
            mediumImage = images.firstPhoto.medium,
            largeImage = images.firstPhoto.large
        )
    )
}