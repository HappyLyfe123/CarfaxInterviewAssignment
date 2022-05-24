package com.carfax.feature_vehicle_listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoaderFactory
import coil.load
import coil.request.ImageRequest
import coil.size.ViewSizeResolver
import com.carfax.feature_vehicle_listing.databinding.ViewHolderVehicleInfoBinding
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail

class VehicleListingAdapter : ListAdapter<VehicleDetail, VehicleListingAdapter.VehicleListingVehicleDetail>(DiffCallback()) {

    var onVehicleClick: ((String) -> Unit)? = null
    var onCallDealerClick: ((String) -> Unit)? = null

    private lateinit var view: View


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListingVehicleDetail {
        val inflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.view_holder_vehicle_info, parent, false)
        return VehicleListingVehicleDetail(view)
    }

    override fun onBindViewHolder(holder: VehicleListingVehicleDetail, position: Int) {
        //test(getItem(position).vehicleImage.mediumImage)
        holder.bind(getItem(position))
    }

    private fun test(url: String) {
        val request = ImageRequest.Builder(view.context)
            .data(url)
            // Optional, but setting a ViewSizeResolver will conserve memory by limiting the size the image should be preloaded into memory at.
            .size(ViewSizeResolver(view.findViewById(R.id.vehicle_photo_image_view)))
            .build()
        Coil.imageLoader(view.context).enqueue(request)
    }

    inner class VehicleListingVehicleDetail(vehicleDetailView: View) : RecyclerView.ViewHolder(vehicleDetailView) {
        private val binding = ViewHolderVehicleInfoBinding.bind(vehicleDetailView)

        fun bind(vehicleDetail: VehicleDetail) {
            with(binding) {
                vehiclePhotoImageView.load(vehicleDetail.vehicleImage.mediumImage)
                vehicleYearMakeModelTrimTextView.text = vehicleDetail.formatYearMakeModelTrim()
                vehiclePriceTextView.text = vehicleDetail.formatPrice()
                vehicleMileageTextView.text = vehicleDetail.formatMileage()
                vehicleLocationTextView.text = vehicleDetail.formatLocation()

                binding.root.setOnClickListener {
                    onVehicleClick?.invoke(vehicleDetail.id)
                }

                binding.callDealerButton.setOnClickListener {
                    onCallDealerClick?.invoke(vehicleDetail.dealerInfo.phoneNumber)
                }
            }
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<VehicleDetail>() {
    override fun areItemsTheSame(oldItem: VehicleDetail, newItem: VehicleDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(@NonNull oldItem: VehicleDetail, @NonNull newItem: VehicleDetail): Boolean {
        return oldItem == newItem
    }
}