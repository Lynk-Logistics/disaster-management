package com.example.myapplication.BridegfyVictim.Repo

import com.twilio.Twilio

import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber

class SmsRepo {

}

object SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    val ACCOUNT_SID = "AC455f51a42329ffcb2e4f6b438b9082df"
    val AUTH_TOKEN = "00cea12e845806de3578cd5390c7015d"

    fun sendSms() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN)

        val message = Message
                .creator(PhoneNumber("+917010065028"), // to
                        PhoneNumber("+12568040107"), // from
                        "Where's Wallace?")
                .create()

        println(message.status)
    }
}