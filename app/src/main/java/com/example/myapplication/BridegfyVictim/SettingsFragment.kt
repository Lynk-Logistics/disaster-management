package com.example.myapplication.BridegfyVictim

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.BridegfyVictim.model.DisasterResources
import com.example.myapplication.BridegfyVictim.model.NativeLocationFragment
import com.example.myapplication.BridegfyVictim.model.OneOf

import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_food.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : NativeLocationFragment()  {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var viewModel: CommonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        viewModel = activity?.run {
            ViewModelProviders.of(this)[CommonViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switch1.isChecked = sharedPreferences.getBoolean(FOOD_AVAILABLE, false)
        switch2.isChecked = sharedPreferences.getBoolean(MEDICINE_AVAILABLE, false)

        switch1.setOnClickListener {
            sharedPreferences.edit()
                    .putBoolean(FOOD_AVAILABLE, switch1.isChecked)
                    .apply()
            if (switch1.isChecked) {
                detectLocation()
            }
        }

        switch2.setOnClickListener {
            sharedPreferences.edit()
                    .putBoolean(FOOD_AVAILABLE, switch2.isChecked)
                    .apply()
        }
    }

    private fun detectLocation() {
        updateLocation()
        locationLiveData.observe(this@SettingsFragment, Observer { location ->
            when (location) {
                is OneOf.Success -> {
                    val sendRequest = DisasterResources.FoodService(
                            latitude = location.a.latitude,
                            longitude = location.a.longitude,
                            numOfPeople = 3,
                            deviceId = getDeviceId(requireContext())
                    )
                    CommonViewModel.broadcastMessage(sendRequest)
                    locationLiveData.removeObservers(this)
                }
                is OneOf.Failure -> {
                    Toast.makeText(requireContext(), "failed : ${location.b}", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        })
    }

    companion object {
        const val FOOD_AVAILABLE = "food_available"
        const val MEDICINE_AVAILABLE = "medicine_available"
    }
}
