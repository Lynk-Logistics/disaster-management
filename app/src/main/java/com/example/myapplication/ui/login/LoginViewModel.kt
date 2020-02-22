package com.example.myapplication.ui.login

import androidx.lifecycle.ViewModel
import com.example.myapplication.BridegfyVictim.Dao.user.UserDetail

class LoginViewModel : ViewModel() {

    var userDetail: UserDetail = UserDetail(
            0, "", 0, emergencyContact = UserDetail.EmergencyContact(0, 0)
    )
}
