package com.carfax.carfaxinterviewassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.commit
import com.carfax.feature_vehicle_listing.VehicleListingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            val vehicleListFragment = VehicleListingFragment()
            add(R.id.activity_main_fragment_container, vehicleListFragment)
            setReorderingAllowed(true)
        }
    }
}