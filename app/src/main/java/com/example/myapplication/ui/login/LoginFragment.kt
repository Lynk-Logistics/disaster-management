package com.example.myapplication.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.BridegfyVictim.Dao.AppDatabaseInstance
import com.example.myapplication.BridegfyVictim.Dao.user.UserDetail

import com.example.myapplication.R
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.requireActivity()).get(LoginViewModel::class.java)
        register.setOnClickListener {
            val name = name.editableText.toString()
            val phoneNumber = number.editableText.toString().toLong()
            val gender = UserDetail.Gender.NONE

            val user = UserDetail(
                    userName = name,
                    phoneNumber = phoneNumber,
                    gender = gender,
                    emergencyContact = UserDetail.EmergencyContact(
                            contact1 =  0,
                            contact2 = 0
                    )
            )
            viewModel.userDetail = user
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }
    }

}
