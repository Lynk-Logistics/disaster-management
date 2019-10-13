package com.example.myapplication.BridegfyVictim

import androidx.lifecycle.ViewModel
import com.bridgefy.sdk.client.Bridgefy
import com.bridgefy.sdk.client.BridgefyClient
import com.example.myapplication.BridegfyVictim.model.DisasterResources

class CommonViewModel: ViewModel() {

    companion object {
        fun broadcastMessage(disasterResources: DisasterResources) {
            val json = NetworkUtil.gson.toJson(disasterResources)
            val data = HashMap<String, Any>()
            data["data"] = json
            Bridgefy.sendBroadcastMessage(data)
        }
    }
}