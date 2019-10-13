package com.example.myapplication

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.bridgefy.sdk.client.*
import com.example.myapplication.BridegfyVictim.model.DisasterResources
import com.google.gson.JsonParser
import com.bridgefy.sdk.client.Bridgefy
import com.bridgefy.sdk.framework.exceptions.MessageException
import java.util.*
import android.telephony.SmsManager
import com.example.myapplication.BridegfyVictim.*
import com.example.myapplication.BridegfyVictim.Dao.FoodServiceDao
import com.example.myapplication.BridegfyVictim.Dao.FoodServiceDatabase
import com.example.myapplication.BridegfyVictim.Dao.FoodServiceEntity

class MyApplication : Application() {

    val availableDevices: MutableList<Device?> = mutableListOf()
    lateinit var foodServiceDao: FoodServiceDao

    val messageListener = object : MessageListener() {
        override fun onMessageSent(messageId: String?) {
            super.onMessageSent(messageId)
            Log.v("bridgefy", messageId)
        }

        override fun onBroadcastMessageReceived(message: Message?) {
            super.onBroadcastMessageReceived(message)
            Log.v("Broadcast message", message?.toString())
            handleMessage(message?.content?.get("data").toString())
        }

        override fun onMessageDataProgress(message: UUID?, progress: Long, fullSize: Long) {
            super.onMessageDataProgress(message, progress, fullSize)
            Log.v("progress", progress.toString())
        }

        override fun onMessageFailed(message: Message?, e: MessageException?) {
            super.onMessageFailed(message, e)
            Log.v("failed", e.toString())
        }
    }

    val stateListener = object : StateListener() {

        override fun onStarted() {
                super.onStarted()
            Log.v("bridgefy", "started")
        }

        override fun onStartError(message: String?, errorCode: Int) {
            super.onStartError(message, errorCode)
            Log.v("bridgefy", "started_error")
        }
        override fun onDeviceConnected(device: Device?, session: Session?) {
            super.onDeviceConnected(device, session)
            Toast.makeText(applicationContext, "Device connected", Toast.LENGTH_SHORT).show()
            availableDevices.add(device)
        }

        override fun onDeviceLost(device: Device?) {
            super.onDeviceLost(device)
            availableDevices.removeAll {
                it?.userId == device?.userId
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        foodServiceDao = FoodServiceDatabase.getDb(applicationContext).foodServiceDao()
        Bridgefy.initialize(applicationContext, "610fd8bb-54e6-4a21-9554-00740a7a1ba8", object : RegistrationListener() {

            override fun onRegistrationSuccessful(bridgefyClient: BridgefyClient) {
                // BridgefyActivity is ready to start
                val builder = Config.Builder()
                builder.setEnergyProfile(BFEnergyProfile.HIGH_PERFORMANCE)
                builder.setEncryption(false)

                Bridgefy.start(messageListener, stateListener, builder.build())
            }

            override fun onRegistrationFailed(errorCode: Int, message: String) {
                // Something went wrong: handle error code, maybe print the message
                Log.e("error", message)
            }
        }
        )
    }

    private fun handleMessage(json: String) {
        val messageTag = JsonParser().parse(json).asJsonObject.getAsJsonPrimitive("tag").asString
        when (messageTag) {
            DisasterResources.MEDICATION -> {
                val medicationResources = NetworkUtil.gson.fromJson<DisasterResources.Medication>(json, DisasterResources.Medication::class.java)
                handleMedicationRequest(medicationResources)
            }

            DisasterResources.FOOD_REQUEST -> {
                val foodResources = NetworkUtil.gson.fromJson<DisasterResources.FoodRequest>(json, DisasterResources.FoodRequest::class.java)
                handleFoodRequest(foodResources)
            }

            DisasterResources.EMERGENCY_CONTACT_TAG -> {
                val sendMessageResources = NetworkUtil.gson.fromJson<DisasterResources.SendEmergencyMessage>(json, DisasterResources.SendEmergencyMessage::class.java)
                handleEmergencyMessages(sendMessageResources)
            }

            DisasterResources.FOOD_SERVICE -> {
                val foodService = NetworkUtil.gson.fromJson<DisasterResources.FoodService>(json, DisasterResources.FoodService::class.java)
                handleFoodService(foodService)
            }
        }
    }

    private fun handleFoodService(foodService: DisasterResources.FoodService) {
        Toast.makeText(applicationContext, "$foodService", Toast.LENGTH_SHORT).show()
        foodServiceDao.insertFoodService(
                FoodServiceEntity(
                        longitude =  foodService.longitude ,
                        latitude = foodService.latitude,
                        message = foodService.numOfPeople.toString()
                )
        )
    }

    private fun handleFoodRequest(foodResources: DisasterResources.FoodRequest) {
        Toast.makeText(this, foodResources.toString(), Toast.LENGTH_LONG).show()
        if (doesProvideFood()) {
            showNotification(applicationContext, foodResources)
        } else {
            if (isNetworkAvailable(applicationContext)) {
                //Send Api Request
            } else {
                if (hasConnectedDevices() and (foodResources.userDeviceId != getDeviceId(applicationContext))) {
                    CommonViewModel.broadcastMessage(foodResources)
                } else {
                    enqueueTask(foodResources)
                }
            }
        }
    }

    private fun handleMedicationRequest(medication: DisasterResources.Medication) {
        Toast.makeText(this, medication.toString(), Toast.LENGTH_LONG).show()
        if (doesProvideMedication()) {
            showNotification(applicationContext, medication)
        } else {
            if (isNetworkAvailable(applicationContext) and false) {
                //Send Api Request
            } else {
                if (hasConnectedDevices() and (medication.userDeviceId != getDeviceId(applicationContext))) {
                    CommonViewModel.broadcastMessage(medication)
                } else {
                    enqueueTask(medication)
                }
            }
        }
    }

    private fun enqueueTask(disasterResoource: DisasterResources) {
        // try to send it later
    }


    private fun handleEmergencyMessages(emergencyRequest: DisasterResources.SendEmergencyMessage) {
        Toast.makeText(this, emergencyRequest.toString(), Toast.LENGTH_LONG).show()
        smsSendMessage(emergencyRequest)
    }

    private fun smsSendMessage(emergencyRequest: DisasterResources.SendEmergencyMessage) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(emergencyRequest.emergencyContact1.toString() , null, emergencyRequest.message, null, null)
            Toast.makeText(applicationContext, "Message Sent",
                    Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Toast.makeText(applicationContext, ex.message.toString(),
                    Toast.LENGTH_LONG).show()
            ex.printStackTrace()
        }
    }

    private fun doesProvideFood(): Boolean {
        return getSharedPreferences("Settings", Context.MODE_PRIVATE)
                .getBoolean(SettingsFragment.FOOD_AVAILABLE, false)
    }

    private fun doesProvideMedication(): Boolean {
        return getSharedPreferences("Settings", Context.MODE_PRIVATE)
                .getBoolean(SettingsFragment.MEDICINE_AVAILABLE, false)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("100", name, importance)
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun hasConnectedDevices(): Boolean {
        return availableDevices.size > 0
    }
}