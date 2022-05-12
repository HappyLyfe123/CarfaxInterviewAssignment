package com.carfax.feature_vehicle_listing.data.mapper

import com.carfax.feature_vehicle_listing.data.local.VehicleDetailEntity
import com.carfax.feature_vehicle_listing.data.remote.dto.RemoteVehicleDetail
import com.carfax.feature_vehicle_listing.domain.model.LocationInfo
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail
import com.carfax.feature_vehicle_listing.domain.model.VehicleImage

fun RemoteVehicleDetail.toVehicleDetail(): VehicleDetail {
    return VehicleDetail(
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
        bodyStyle = bodyType,
        fuel = fuel,
        location = LocationInfo(
            dealerInfo.city,
            dealerInfo.state
        ),
        vehicleImage = VehicleImage(
            baseURL = images.baseURL,
            smallImage = images.firstPhoto.small,
            mediumImage = images.firstPhoto.medium,
            largeImage = images.firstPhoto.large
        )
    )
}

fun VehicleDetail.toVehicleDetailEntity(): VehicleDetailEntity{
    return VehicleDetailEntity(
        id = id,
        year = year,
        make = make,
        model = model,
        trim = trim,
        price = price,
        mileage = mileage,
        phone = phoneNumber,
        exteriorColor = exteriorColor,
        interiorColor = interiorColor,
        driveType = driveType,
        transmission = transmission,
        engine = engine,
        bodyStyle = bodyStyle,
        fuel = fuel,
        location = LocationInfo(
            city = location.city,
            state = location.state
        ),
        vehicleImage = VehicleImage(
            baseURL = vehicleImage.baseURL,
            smallImage = vehicleImage.smallImage,
            mediumImage = vehicleImage.mediumImage,
            largeImage = vehicleImage.largeImage
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
        phoneNumber = phone,
        exteriorColor = exteriorColor,
        interiorColor = interiorColor,
        driveType = driveType,
        transmission = transmission,
        engine = engine,
        bodyStyle = bodyStyle,
        fuel = fuel,
        location = LocationInfo(
            city = location.city,
            state = location.state
        ),
        vehicleImage = VehicleImage(
            baseURL = vehicleImage.baseURL,
            smallImage = vehicleImage.smallImage,
            mediumImage = vehicleImage.mediumImage,
            largeImage = vehicleImage.largeImage
        )
    )
}