package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myapplication.BridegfyVictim.CommonViewModel
import com.example.myapplication.BridegfyVictim.CommonViewModel.Companion.broadcastMessage
import com.example.myapplication.BridegfyVictim.Dao.AppDatabaseInstance
import com.example.myapplication.BridegfyVictim.getDeviceId
import com.example.myapplication.BridegfyVictim.model.DisasterResources
import kotlinx.android.synthetic.main.activity_emergencyactivity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        default_button.setOnClickListener {
            message_button.isChecked = false

        }

        message_button.setOnClickListener {
            default_button.isChecked = false
        }

        back.setOnClickListener {
            findNavController().popBackStack()
        }

        broadcast.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val userDao = AppDatabaseInstance.getDb(requireContext()).userDao()
                val message = if (default_button.isActivated) {
                    "SOS, I am stuck and need help. I am here at (Location)"
                } else {
                    message.editableText.toString().trim()
                }

                val contact1 = DisasterResources.SendEmergencyMessage(
                        emergencyContact = userDao.getCurrentUser()?.emergencyContact?.contact1 ?: 8788043980,
                        message = message,
                        deviceId = getDeviceId(requireContext())
                )
                val contact2 = DisasterResources.SendEmergencyMessage(
                        emergencyContact = userDao.getCurrentUser()?.emergencyContact?.contact2 ?: 7010065028,
                        message = message,
                        deviceId = getDeviceId(requireContext())
                )
                broadcastMessage(contact1)
                broadcastMessage(contact2)

                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(), "Message sent to nearby" +
                            " ${(this@EmergencyFragment.activity?.application as MyApplication).availableDevices.size}",
                            Toast.LENGTH_SHORT).show()
                    this@EmergencyFragment.findNavController().popBackStack()
                }
            }
        }
    }
}
