package com.carfax.feature_vehicle_listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.carfax.feature_vehicle_listing.databinding.ViewHolderVehicleInfoBinding
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail

class VehicleListingAdapter : ListAdapter<VehicleDetail, VehicleListingAdapter.VehicleListingVehicleDetail>(DiffCallback()) {

    var onVehicleClick: ((String) -> Unit)? = null
    var onCallDealerClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListingVehicleDetail {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_vehicle_info, parent, false)
        return VehicleListingVehicleDetail(view)
    }

    override fun onBindViewHolder(holder: VehicleListingVehicleDetail, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VehicleListingVehicleDetail(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewHolderVehicleInfoBinding.bind(itemView)

        fun bind(itemView: VehicleDetail) {
            with(binding) {
                Glide.with(binding.root.context).load(itemView.vehicleImage.mediumImage)
                    .placeholder(R.drawable.default_image_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.vehiclePhotoImageView)
                vehicleYearMakeModelTrimTextView.text = itemView.formatYearMakeModelTrim()
                vehiclePriceTextView.text = itemView.formatPrice()
                vehicleMileageTextView.text = itemView.formatMileage()
                vehicleLocationTextView.text = itemView.formatLocation()

                binding.root.setOnClickListener {
                    onVehicleClick?.invoke(itemView.id)
                }
                binding.callDealerButton.setOnClickListener {
                    onCallDealerClick?.invoke(itemView.dealerInfo.phoneNumber)
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