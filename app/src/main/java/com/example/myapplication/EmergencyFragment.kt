package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.BridegfyVictim.CommonViewModel
import com.example.myapplication.BridegfyVictim.CommonViewModel.Companion.broadcastMessage
import com.example.myapplication.BridegfyVictim.getDeviceId
import com.example.myapplication.BridegfyVictim.model.DisasterResources
import kotlinx.android.synthetic.main.activity_emergencyactivity.*

class EmergencyFragment : Fragment() {

    lateinit var viewModel: CommonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = activity?.run {
            ViewModelProviders.of(this)[CommonViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.activity_emergencyactivity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        send_emergency_message_button.setOnClickListener {
            val message = message.editableText.toString().trim()
            val disasterResources = DisasterResources.SendEmergencyMessage(
                    emergencyContact1 = 7010065028,
                    emergencyContact2 = 7010065028,
                    emergencyContact3 = 7010065028,
                    message = message,
                    deviceId = getDeviceId(requireContext())
            )
            broadcastMessage(disasterResources)
        }
    }
}
