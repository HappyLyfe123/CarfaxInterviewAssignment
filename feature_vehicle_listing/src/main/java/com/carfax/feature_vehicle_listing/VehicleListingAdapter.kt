package com.carfax.feature_vehicle_listing

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carfax.feature_vehicle_listing.domain.model.VehicleDetail

class VehicleListingAdapter: RecyclerView.Adapter<VehicleListingVehicleInfoVH>() {

    private val vehicleListing = ArrayList<VehicleDetail>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListingVehicleInfoVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_vehicle_info, parent, false)
        return VehicleListingVehicleInfoVH(view)
    }

    override fun onBindViewHolder(holder: VehicleListingVehicleInfoVH, position: Int) {
        holder.bind(vehicleListing[position])
    }

    override fun getItemCount(): Int = vehicleListing.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateListing(newListing: List<VehicleDetail>){
        with(vehicleListing){
            clear()
            addAll(newListing)
        }
        notifyDataSetChanged()
    }

}