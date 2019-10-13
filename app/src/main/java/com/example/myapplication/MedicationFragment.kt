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
import kotlinx.android.synthetic.main.activity_medication.*

class MedicationFragment : Fragment() {

    private lateinit var viewModel: CommonViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = activity?.run {
            ViewModelProviders.of(this)[CommonViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.activity_medication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        send_medication_button.setOnClickListener {
            val area = area_text_box.editableText.toString().trim()
            val noOfPeople = no_of_people_text_box.editableText.toString().trim()
            val message = message_box.editableText.toString().trim()

            val medicationRequest = DisasterResources.Medication(
                    latitude = null,
                    longitude = null,
                    area = area,
                    requiredMedication = message,
                    deviceId = getDeviceId(requireContext())
            )

            broadcastMessage(medicationRequest)
        }
    }
}
