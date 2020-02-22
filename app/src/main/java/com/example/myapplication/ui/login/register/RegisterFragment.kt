package com.example.myapplication.ui.login.register

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myapplication.BridegfyVictim.Dao.AppDatabaseInstance
import com.example.myapplication.BridegfyVictim.Dao.user.UserDetail

import com.example.myapplication.R
import com.example.myapplication.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this.requireActivity()).get(LoginViewModel::class.java)

        save.setOnClickListener {
            val emergencyContact1 = number_one.editableText.toString().toLong()
            val emergencyContact2 = number_two.editableText.toString().toLong()

            CoroutineScope(Dispatchers.IO).launch {
                val userDao = AppDatabaseInstance.getDb(requireContext()).userDao()
                userDao.updateUser(
                        viewModel.userDetail.copy(
                                emergencyContact = UserDetail.EmergencyContact(
                                        emergencyContact1, emergencyContact2
                                )
                        )
                )
                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
            }
        }
    }
}
