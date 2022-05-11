package com.carfax.feature_vehicle_listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.carfax.feature_vehicle_listing.databinding.ViewHolderVehicleInfoBinding
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail

class VehicleListingAdapter : ListAdapter<VehicleDetail, VehicleListingAdapter.VehicleListingVehicleDetail>(DiffCallback()) {

    var onVehicleClick: ((Int) -> Unit)? = null

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

        init {
            itemView.setOnClickListener {
                onVehicleClick?.invoke(absoluteAdapterPosition)
            }
        }

        fun bind(itemView: VehicleDetail) {
            with(binding) {
                vehiclePhotoImageView.load(itemView.images.smallImage)
                vehicleYearMakeModelTrimTextView.text = itemView.formatYearMakeModelTrim()
                vehiclePriceTextView.text = itemView.formatPrice()
                vehicleMileageTextView.text = itemView.formatMileage()
                vehicleLocationTextView.text = itemView.formatLocation()
            }
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<VehicleDetail>() {
    override fun areItemsTheSame(oldItem: VehicleDetail, newItem: VehicleDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(@NonNull oldItem: VehicleDetail, @NonNull newItem: VehicleDetail): Boolean {
        return true
    }
}