package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.BridegfyVictim.CommonViewModel
import com.example.myapplication.BridegfyVictim.CommonViewModel.Companion.broadcastMessage
import com.example.myapplication.BridegfyVictim.getDeviceId
import com.example.myapplication.BridegfyVictim.model.DisasterResources
import com.example.myapplication.BridegfyVictim.model.NativeLocationFragment
import com.example.myapplication.BridegfyVictim.model.OneOf
import kotlinx.android.synthetic.main.activity_food.*

class FoodFragment : NativeLocationFragment() {

    private lateinit var viewModel: CommonViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = activity?.run {
            ViewModelProviders.of(this)[CommonViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.activity_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        send_food_request.setOnClickListener {
            detectLocation()
        }
    }

    private fun detectLocation() {
        updateLocation()
        locationLiveData.observe(this@FoodFragment, Observer { location ->
            when (location) {
                is OneOf.Success -> {
                    val area = area_text_box.editableText.toString().trim()
                    val numberOfPeople = no_of_people_text_box.editableText.toString().trim()
                    val message = message_box.editableText.toString().trim()
                    val sendRequest = DisasterResources.FoodRequest(
                            latitude = location.a.latitude,
                            longitude = location.a.longitude,
                            area = area,
                            numOfPeople = numberOfPeople.toInt(),
                            requiredFood = message,
                            deviceId = getDeviceId(requireContext())
                    )
                    broadcastMessage(sendRequest)
                    locationLiveData.removeObservers(this)
                }
                is OneOf.Failure -> {
                    Toast.makeText(requireContext(), "failed : ${location.b}", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        })
    }


}
