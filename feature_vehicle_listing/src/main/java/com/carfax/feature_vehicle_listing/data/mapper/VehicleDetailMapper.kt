package com.carfax.feature_vehicle_listing.data.mapper

import com.carfax.feature_vehicle_listing.data.local.VehicleDetailEntity
import com.carfax.feature_vehicle_listing.data.remote.dto.RemoteVehicleDetail
import com.carfax.feature_vehicle_listing.domain.model.DealerInfo
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.model.VehicleImage

fun RemoteVehicleDetail.toVehicleDetailEntity(): VehicleDetailEntity {
    return VehicleDetailEntity(
        id = id,
        year = year,
        make = make,
        model = model,
        trim = trim,
        price = price,
        mileage = mileage,
        exteriorColor = exteriorColor,
        interiorColor = interiorColor,
        driveType = driveType,
        transmission = transmission,
        engine = engine,
        bodyStyle = bodyType,
        fuel = fuel,
        dealerInfo = DealerInfo(
            city = dealerInfo.city,
            state = dealerInfo.state,
            phoneNumber = dealerInfo.phone,
        ),
        vehicleImage = VehicleImage(
            baseURL = images.baseURL,
            smallImage = images.firstPhoto.small,
            mediumImage = images.firstPhoto.medium,
            largeImage = images.firstPhoto.large
        )
    )
}

fun VehicleDetailEntity.toVehicleDetail(): VehicleDetail {
    return VehicleDetail(
        id = id,
        year = year,
        make = make,
        model = model,
        trim = trim,
        price = price,
        mileage = mileage,
        exteriorColor = exteriorColor,
        interiorColor = interiorColor,
        driveType = driveType,
        transmission = transmission,
        engine = engine,
        bodyStyle = bodyStyle,
        fuel = fuel,
        dealerInfo = DealerInfo(
            city = dealerInfo.city,
            state = dealerInfo.state,
            phoneNumber = dealerInfo.phoneNumber,
        ),
        vehicleImage = VehicleImage(
            baseURL = vehicleImage.baseURL,
            smallImage = vehicleImage.smallImage,
            mediumImage = vehicleImage.mediumImage,
            largeImage = vehicleImage.largeImage
        )
    )
}