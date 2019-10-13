package com.example.myapplication.BridegfyVictim

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.BridegfyVictim.model.DisasterResources
import com.example.myapplication.R
import com.google.gson.Gson

fun isNetworkAvailable(context: Context): Boolean {
    val tel = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return !(tel.networkOperator != null && tel.networkOperator == "")
}

fun isInternetAvailable(context: Context): Boolean {
    val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isAvailable
}

fun showNotification(context: Context, disasterResources: DisasterResources) {

    val builder = NotificationCompat.Builder(context, "100")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(
                    getTitle(disasterResources)
            )
            .setContentText(getContent(disasterResources))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define
        notify(1, builder.build())
    }
}

fun getContent(disasterResources: DisasterResources): String {
    return when (disasterResources) {
        is DisasterResources.SendEmergencyMessage -> "Send sms"
        is DisasterResources.FoodRequest -> "Required ${disasterResources.requiredFood} at ${disasterResources.area}"
        is DisasterResources.Medication -> " Medication Required ${disasterResources.requiredMedication} at ${disasterResources.area}"
    }
}

fun getTitle(disasterResources: DisasterResources): String {
    return when (disasterResources) {
        is DisasterResources.SendEmergencyMessage -> "Send emergency contact"
        is DisasterResources.FoodRequest -> "Food Request"
        is DisasterResources.Medication -> " Medication Request"
    }
}


fun getDeviceId(context: Context): String {
    return Settings.Secure.getString(context.contentResolver,
            Settings.Secure.ANDROID_ID)
}

object NetworkUtil {

    val gson = Gson()
}